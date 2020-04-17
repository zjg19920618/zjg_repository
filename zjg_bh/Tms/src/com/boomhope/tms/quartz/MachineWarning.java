package com.boomhope.tms.quartz;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

import com.boomhope.tms.entity.BaseParameter;
import com.boomhope.tms.entity.BaseUnit;
import com.boomhope.tms.entity.DeployMachineView;
import com.boomhope.tms.entity.MacWarning;
import com.boomhope.tms.model.Email;
import com.boomhope.tms.service.IDeployMachineService;
import com.boomhope.tms.service.IMacControlPeriService;
import com.boomhope.tms.service.IMailService;
import com.boomhope.tms.service.ISystemService;
import com.boomhope.tms.util.DateUtil;
import com.boomhope.tms.util.Dict;

/**
 * 预警信息登记
 * @author zy
 *
 */
public class MachineWarning {
	
	private static final Log log = LogFactory.getLog(MachineWarning.class);
	
	@Value("#{configProperties['email_from']}")
	private String emailFrom;
	
	@Value("#{configProperties['email_subject']}")
	private String emailSubject;
	
	IDeployMachineService deployMachineService;
	
	IMacControlPeriService macControlPeriService;
	
	ISystemService systemService;
	
	IMailService mailService;
	

	@Resource(name = "mailService")
	public void setMailService(IMailService mailService) {
		this.mailService = mailService;
	}

	@Resource(name = "systemService")
	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	@Resource(name = "macControlPeriService")
	public void setMacControlPeriService(
			IMacControlPeriService macControlPeriService) {
		this.macControlPeriService = macControlPeriService;
	}

	@Resource(name = "deployMachineService")
	public void setDeployMachineService(IDeployMachineService deployMachineService) {
		this.deployMachineService = deployMachineService;
	}
	
	public void work() {
		// 获取预警参数
		Map map = null;
		try {
			map = getParamete();
		} catch (Exception e) {
			log.error("定时任务获取预警参数发生错误！！");
			log.error(e);
			return;
		}
		 // 获取全部投产设备
		 List<DeployMachineView> list = getAllMach();
		 // 比较是否存在超期，如果有则登记预警信息，并通知机构管理
		 analyse(list,Long.parseLong(map.get("time")+""),map);
	 }
	 
	 /**
	  * 获取已投产且运行正常的数据
	  */
	 public List<DeployMachineView> getAllMach(){
		 Map<String,String> map = new HashMap<String,String>();
		 map.put("status", Dict.DEPLOY_USE);
		 map.put("repStatus", Dict.REP_STATUS_NORMAL);
		 List<DeployMachineView> list = deployMachineService.findDeployMachineList(map);
		 return list;
	 }
	 
	 /**
	  * 登记预警信息
	  */
	 public void analyse(List<DeployMachineView> list,long warTime,Map<String,String> map){
		 for (DeployMachineView deployMachineView : list) {
			 String repTime = deployMachineView.getRepTime();
			 if(count(repTime) > warTime){
				 // 判断该设备是否已登记，如登记则不在进行重复登记
				 boolean flag = macControlPeriService.ifRegiter(deployMachineView.getId().getMachineNo());
				 if(flag){
					 // 已经登记的且未处理的，则不做登记
					 continue;
				 }
				 // 大于预警时间则登记
				 register(deployMachineView);
				 // 另起线程发送通知
				 sendInform(deployMachineView.getId().getMachineNo(), map);
			 }
		}
	 }
	 
	 /**
	  * 发送通知
	  */
	 public void sendInform(String machineNo,Map<String,String> map){
		 String isEmail = map.get("email");
		 String isTel = map.get("tel");
		 BaseUnit baseUnit = null;
		 if(isEmail.equals("Y") || isTel.equals("Y") ){
			 baseUnit = systemService.getBaseUintByMachineNo(machineNo);
		 }
		
		// 判断是否需要发送邮件,如果是则获取设备所在机构对应的邮件发送
		if(isEmail.equals("Y")){
			// 组织发送邮件参数，发送预警邮件
			Email email = new Email();
			email.setContent("设备编号:"+machineNo+"，超期未上报状态，特发此预警！");
			email.setSubject(emailSubject);
			email.setToAddress(baseUnit.getEmail());
			email.setFromAddress(emailFrom);
			try {
				mailService.sendMail(email);
			} catch (MessagingException e) {
				log.error("发送邮箱失败1："+e);
			} catch (IOException e) {
				log.error("发送邮箱失败2："+e);
			}
		}
		
	 }
	 
	 /**
	  * 登记预警信息
	  */
	 public void register(DeployMachineView view){
		 // 登记机器编号
		 String machineNo = view.getId().getMachineNo();
		 MacWarning m = new MacWarning();
		 m.getDeployMachine().setMachineNo(machineNo);
		 m.setCreateDate(DateUtil.getDateTime("yyyyMMddHHmmss"));
		 m.setProStatus(Dict.MAC_WAR_ON);
		 macControlPeriService.saveMacWaring(m);
	 }
	 
	 /**
	  * 计算当前时间与上报时间差额
	  * @param repTime
	  * @return
	  */
	 public long count(String repTime){
		// 最近一次上报时间
		 long l1 = DateUtil.convertlong(repTime, "yyyyMMddHHmmss");
		 // 当前日期
		 long l2 = System.currentTimeMillis();
		 // 计算差值（分）
		 long count = (l2 - l1)/60000; 
		 return count;
	 }
	 
	 /**
	  * 获取预警参数
	  * @return
	  * @throws Exception
	  */
	 public Map getParamete() throws Exception{
		 List<BaseParameter> list = systemService.findBaseParameterByParentType(Dict.PARAMETE_WAR_TYPE);
		 if(list == null || list.size() == 0){
			  throw new Exception("基础数据异常！");
		 }
		 BaseParameter bp = (BaseParameter) list.get(0);
		 return JSONObject.fromObject(bp.getParameterValue());
	 }
	 
	 
	 public static void main(String args[]){
		 JSONObject json = new JSONObject();
		 json.put("time", "30");
		 json.put("email", "Y");
		 json.put("tel", "Y");
		 System.out.println(json.toString());
		 
		System.out.println(JSONObject.fromObject(json.toString()).get("time")); 
		 
	 }
	 
}
