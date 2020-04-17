package com.boomhope.tms.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boomhope.tms.entity.BaseParameter;
import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.DeployMachineView;
import com.boomhope.tms.entity.MacControlPeri;
import com.boomhope.tms.entity.MacWarning;
import com.boomhope.tms.entity.MacWarningView;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.pojo.ControlPojo;
import com.boomhope.tms.pojo.DeployControlPojo;
import com.boomhope.tms.service.IDeployMachineService;
import com.boomhope.tms.service.IMacControlPeriService;
import com.boomhope.tms.util.DateUtil;
import com.boomhope.tms.util.Dict;

/**
 *终端
 * 
 * @author gyw
 *
 */
@Controller
@Scope("prototype")
public class MacControlPeriAction extends BaseAction{
	
private static final Log log = LogFactory.getLog(SystemAction.class);  
	
   IMacControlPeriService macControlPeriService;

   IDeployMachineService deployMachineService;
   
   @Resource(name = "macControlPeriService")
   public void setMacControlPeriService(IMacControlPeriService macControlPeriService) {
	this.macControlPeriService = macControlPeriService;
   }
   @Resource(name = "deployMachineService")
	public void setDeployMachineService(IDeployMachineService deployMachineService) {
		this.deployMachineService = deployMachineService;
	}
	
	/**
	 * 终端信息查询详细
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findMacControlPeriList")
	public @ResponseBody Object findMacControlPeriList(HttpServletRequest request,Model model, Integer page,Integer rows,MacControlPeri macControlPeri)  {
		
		Map<String,String> selmap = new HashMap<String,String>();
		
		selmap.put("machineNo", macControlPeri.getDeployMachine().getMachineNo());
		
		List<MacControlPeri> list =  macControlPeriService.findMacControlPeriList( selmap);
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("rows", JSONArray.fromObject(list));
		return returnSucess(map);
	}
	
	/**
	 * 预警查询
	 * @param request
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/findMacWarningViewList")
	public @ResponseBody Object findMacWarningViewList(HttpServletRequest request,Model model, Integer page,Integer rows)  {
		
		JSONObject json = getReqData(request);
		Map<String,String> map = new HashMap<String,String>();
		if(json.get("machineName") != null){
			map.put("machineName", String.valueOf(json.get("machineName")));
		}
		if(json.get("unitName") != null){
			map.put("unitName", String.valueOf(json.get("unitName")));
		}
		if(json.get("proStatus") != null){
			map.put("proStatus", String.valueOf(json.get("proStatus")));
		}
		Page pageInfo = getPageInfo(page,rows);
		
		List<MacWarningView> list = macControlPeriService.findMacWarningViewList(pageInfo, map);
		
		return returnResult(pageInfo, list);
	}
	
	/**
	 * 保存
	 * @param request
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/saveMacWarning")
	public @ResponseBody Object saveMacWarning(HttpServletRequest request)  {
		JSONObject json = getReqData(request);
		MacWarning m = new MacWarning();
		// 主键
		if(json.get("id") != null && !"".equals(json.get("id")+"")){
			m.setId(Integer.parseInt(json.get("id")+""));
		}
		// 描述
		if(json.get("proDesc") != null && !"".equals(json.get("proDesc") +"")){
			m.setProDesc(json.get("proDesc")+"");
		}
		// 处理日期
		m.setProDate(DateUtil.formatDate(new Date(), "yyyyMMddHHmmss"));
		// 处理状态
		m.setProStatus(Dict.MAC_WAR_TWO);
		macControlPeriService.updateMacWaring(m);
		return this.returnSucess();
	}
	
	/**
	 *預警參數显示
	 */
	@RequestMapping(value = "/findParameter1")
	public @ResponseBody Object findMacWar(HttpServletRequest request)  {
		
		BaseParameter seq = macControlPeriService.findMacWarByType();
		String parameter = seq.getParameterValue();
		return parameter;
		
	}
	
	/**
	 *预警参数管理
	 */
	@RequestMapping(value = "saveParameter")
	public @ResponseBody Object saveParameter(HttpServletRequest request)  {
		JSONObject json = getReqData(request);
		log.debug(json.toString());
		String value = json.toString();	
		List<BaseParameter> list = macControlPeriService.findBaseParameterList();
		BaseParameter baseParameter = new BaseParameter();
		baseParameter.setId(1);
		baseParameter.setParameterName("预警配置");
		baseParameter.setParameterType("war_type");
		baseParameter.setParameterValue(value);
		if (list.isEmpty()) {
			//执行添加操作
			macControlPeriService.saveBaseParameter(baseParameter);
		}else{
			//执行修改操作
			macControlPeriService.updateBaseParameter(baseParameter);
		}
		return this.returnSucess();
	}
	
	/**
	 * 终端查询--不要删
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping(value = "/findDeployMachineList1")
	public @ResponseBody Object findDeployMachineList(HttpServletRequest request,Model model, Integer page,Integer rows)  {
		JSONObject json = getReqData(request);
		Map<String,String> map = new HashMap<String,String>();
		// 机器编号
		if(json.get("machineNo") != null){
			map.put("machineNo", String.valueOf(json.get("machineNo")));
		}
		// 机器名称
		if(json.get("machineName") != null){
			map.put("machineName", String.valueOf(json.get("machineName")));
		}
		// 机器类型
		if(json.get("machineTypeName") != null){
			map.put("machineType", String.valueOf(json.get("machineTypeName")));
		}
		// 所属厂商
		if(json.get("manuName")  != null){
			map.put("manuName", String.valueOf(json.get("manuName")));
		}
		// 所属机构
		if(json.get("unitName")  != null){
			map.put("unitName", String.valueOf(json.get("unitName")));
		}
		// 上报状态
		if(json.get("repStatus")  != null){
			map.put("repStatus", String.valueOf(json.get("repStatus")));
		}
		Page pageInfo = getPageInfo(page,rows);

		List<DeployControlPojo> list1 = deployMachineService.findDeployMachineList1(pageInfo, map);
		List<DeployControlPojo> list = new ArrayList<>();;
		for (DeployControlPojo pojo : list1) {
			DeployControlPojo pojo2 = new DeployControlPojo();
			List<MacControlMachine> lm = deployMachineService.findDeployMachineList2(pojo.getMachineNo());
			if (lm.size()>0) {
				pojo2.setMachineStatus("2");
			}else{
				pojo2.setMachineStatus("1");
			}
			Long count = deployMachineService.findDeployMachineList3(pojo.getMachineNo());
			pojo2.setErrorCount(count);
			pojo2.setMachineNo(pojo.getMachineNo());
			pojo2.setMachineCode(pojo.getMachineCode());
			pojo2.setMachineName(pojo.getMachineName());
			pojo2.setMachineType(pojo.getMachineType());
			pojo2.setManuName(pojo.getManuName());
			pojo2.setUnitName(pojo.getUnitName());
			pojo2.setReportDate(pojo.getReportDate());
			list.add(pojo2);
		}
		return returnResult(pageInfo, list);
	}*/
	
	
	/**
	 * 终端查询
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findDeployMachineList1")
	public @ResponseBody Object findDeployMachineList(HttpServletRequest request,Model model, Integer page,Integer rows)  {
		JSONObject json = getReqData(request);
		Map<String,String> map = new HashMap<String,String>();
		// 机器编号
		if(json.get("machineNo") != null){
			map.put("machineNo", String.valueOf(json.get("machineNo")));
		}
		// 机器名称
		if(json.get("machineName") != null){
			map.put("machineName", String.valueOf(json.get("machineName")));
		}
		// 机器类型
		if(json.get("machineTypeName") != null){
			map.put("machineType", String.valueOf(json.get("machineTypeName")));
		}
		// 所属厂商
		if(json.get("manuName")  != null){
			map.put("manuName", String.valueOf(json.get("manuName")));
		}
		// 所属机构
		if(json.get("unitName")  != null){
			map.put("unitName", String.valueOf(json.get("unitName")));
		}
		// 上报状态
		if(json.get("repStatus")  != null){
			map.put("repStatus", String.valueOf(json.get("repStatus")));
		}
		Page pageInfo = getPageInfo(page,rows);

		List<DeployControlPojo> list1 = deployMachineService.findDeployMachineList1(pageInfo, map);
		List<DeployControlPojo> list = new ArrayList<>();
			if (list1.isEmpty()) {
				return list1;
			}else{
				for (DeployControlPojo dmv : list1) {
					DeployControlPojo pojo2 = new DeployControlPojo();
				/*//查询机器异常原因
				//1.查询机器最大时间
				//2.根据机器部署编号和机器时间查询机器当前状态描述
				String time = deployMachineService.findDeployMachineList4(dmv.getMachineNo());
				String Desc = deployMachineService.findDeployMachineList5(dmv.getMachineNo(),time);*/
				//查询机器异常次数
					Long count = deployMachineService.findDeployMachineList3(dmv.getMachineNo());
					pojo2.setErrorCount(count);
					
					pojo2.setRepStatus(dmv.getRepStatus());
					pojo2.setRepDesc(dmv.getRepDesc());
					pojo2.setRepTime(dmv.getRepTime());
					pojo2.setMachineNo(dmv.getMachineNo());
					pojo2.setMachineCode(dmv.getMachineCode());
					pojo2.setMachineName(dmv.getMachineName());
					pojo2.setMachineType(dmv.getMachineType());
					pojo2.setManuName(dmv.getManuName());
					pojo2.setUnitName(dmv.getUnitName());
					list.add(pojo2);
				}
			}
		return returnResult(pageInfo, list);
	}

	
	/**
	 * 终端查询  - 异常处理 
	 * @param request
	 */
	@RequestMapping(value = "/updateMachineStatus")
	public @ResponseBody Object updateMachineStatus (HttpServletRequest request,Model model, Integer page,Integer rows)  {
		JSONObject json = getReqData(request);
		String machineNo = String.valueOf(json.get("machineNo"));
		String repDesc = String.valueOf(json.get("repDesc"));
		String createDate = deployMachineService.findMaxCreateDate(machineNo);
		
		deployMachineService.updateMachineStatus1(machineNo,repDesc,createDate);
		
		return returnSucess();
	}
	
	/**
	 * 终端查询  - 异常处理 - 根据时间条件查询当前机器每个月份的异常次数
	 * @param request
	 */
	@RequestMapping(value = "/findeStatusEveryMouth")
//	@RequestMapping(value = "/findExceptionList")
	public @ResponseBody Object findeStatusEveryMouth (HttpServletRequest request,Model model, Integer page,Integer rows)  {
		JSONObject json = getReqData(request);
		Map<String,String> map = new HashMap<String,String>();
		// 机器编号
		if(json.get("machineNo") != null){
			map.put("machineNo", String.valueOf(json.get("machineNo")));
		}
		if (json.get("startTime") !=null&&!"".equals(json.get("startTime"))) {
			String startTime1 = String.valueOf(json.get("startTime"));
			String startTime = startTime1+"000000";
			map.put("startTime", startTime);
		}
		if (json.get("endTime") !=null&&!"".equals(json.get("endTime"))) {    
			String endTime1 = String.valueOf(json.get("endTime"));
			String endTime = endTime1+"235959";
			map.put("endTime", endTime);
		}
		List<ControlPojo> list = deployMachineService.findeStatusEveryMouth(map);
		return list;
	}
	
	@RequestMapping(value = "/findExceptionList")
	public @ResponseBody Object findExceptionList(HttpServletRequest request,Model model, Integer page,Integer rows)  {
		JSONObject json = getReqData(request);
		String machineNo = String.valueOf(json.get("machineNo"));
		List<ControlPojo> list = deployMachineService.findMacControlMachineList(machineNo);
		return list;
	}
	
	/**
	 * 终端查询  - 异常处理 - 编辑异常描述（异常次数进入）
	 * @param request
	 */
	@RequestMapping(value = "/updateMachineStatus1")
	public @ResponseBody Object updateMachineStatus1 (HttpServletRequest request,Model model, Integer page,Integer rows)  {
		JSONObject json = getReqData(request);
		String machineNo = String.valueOf(json.get("machineNo"));
		String createDate = String.valueOf(json.get("createDate"));
		String repDesc = String.valueOf(json.get("repDesc"));
		deployMachineService.updateMachineStatus1(machineNo,repDesc,createDate);
		
		return returnSucess();
	}
	
}
