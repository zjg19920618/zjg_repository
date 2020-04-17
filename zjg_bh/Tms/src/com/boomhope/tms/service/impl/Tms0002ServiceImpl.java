package com.boomhope.tms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.boomhope.tms.dao.IDeployMachineDao;
import com.boomhope.tms.dao.IMacControlPeriDao;
import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.tms.Tms0002PeriBean;
import com.boomhope.tms.message.in.tms.Tms0002ReqBean;
import com.boomhope.tms.message.in.tms.Tms0002ResBean;
import com.boomhope.tms.service.ITms0002Service;
import com.boomhope.tms.util.DateUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

/***
 * 交易Tms0002(机器状态监控)Service实现类
 * @author shaopeng
 *
 */
@Service("Tms0002Service")
public class Tms0002ServiceImpl implements ITms0002Service {
	
	private static final Log log = LogFactory.getLog(Tms0002ServiceImpl.class);

	// 部署机器Dao
	IDeployMachineDao deployMachineDao;
	@Resource(name="deployMachineDao")
	public void setDeployMachineDao(IDeployMachineDao deployMachineDao) {
		this.deployMachineDao = deployMachineDao;
	}
	
	// 部署外设Dao
	private IMacControlPeriDao macControlPeriDao ;
	@Resource(name="macControlPeriDao")
	public void setMacControlPeriDao(IMacControlPeriDao macControlPeriDao) {
		this.macControlPeriDao = macControlPeriDao;
	}

	@Override
	public String doSuccess(Tms0002ReqBean reqBean) throws Exception{
		try {
			/* 获取部署机器信息 */
			String machineNo = reqBean.getHeadBean().getMachineNo();
			log.debug("machineNo:" + machineNo);
			DeployMachine machine = deployMachineDao.findOne(machineNo);
			if (machine == null){
				return makeResMsg("999999", "无此机器编号:'" + machineNo + "'!");
			}

			/* 更新部署机器信息 */
			machine.setRepStatus("1");
			machine.setRepTime(DateUtil.getDateTime("yyyyMMddHHmmss"));
			machine.setRepDesc("机器正常");
			deployMachineDao.saveOrUpdate(machine);
			
			/* 更新部署外设状态 */
			String hql = "update MacControlPeri set periStatus = '1' where deployMachine.machineNo = '" + machineNo + "'";
			macControlPeriDao.executeHql(hql);

			return makeResMsg("000000", "交易成功");
		} catch (Exception e) {
			throw new Exception("机器状态成功--入库失败",e );
		}
		
	}

	@Override
	public String doFail(Tms0002ReqBean reqBean) throws Exception{
		try {
			log.info("机器状态异常");
			log.debug("机器状态异常" );
			
			/* 获取部署机器信息 */
			String machineNo = reqBean.getHeadBean().getMachineNo();
			DeployMachine machine = deployMachineDao.findOne(machineNo);
			if (machine == null){
				return makeResMsg("999999", "无此机器编号:'" + machineNo + "'!");
			}

			/* 更新部署机器信息 */
			machine.setRepStatus("2");
			machine.setRepTime(DateUtil.getDateTime("yyyyMMddHHmmss"));
			
			
			
			/* 更新异常外设状态 */
			List<Tms0002PeriBean> periList = reqBean.getBodyBean().getPeriList(); 
				if(periList != null){
				
				log.debug("hql=" +periList.size() );
				String errmsg = "";
				for(int i=0; i<periList.size(); i++){
					log.info("i=" + i);
					
					Tms0002PeriBean periBean = periList.get(i);
					errmsg  = errmsg+periBean.getPeriStatusDesc();
					String str = periBean.getPeriId();
					String perName = periBean.getPeriStatus();
	//				String hql = "update MacControlPeri set periStatus = '" + periBean.getPeriStatus() + 
	//						"' where deployMachine.machineNo = '" + machineNo + "' and periId = '" + periBean.getPeriId() + "'";
	//				logger.debug("hql=" +hql );
	//				macControlPeriDao.executeHql(hql);
				}
				machine.setRepDesc(errmsg);
			}else{
				machine.setRepDesc("状态未检测到，可能为接口平台故障");
			}
			
			deployMachineDao.saveOrUpdate(machine);
			return makeResMsg("000000", "交易成功");
		} catch (Exception e) {
			throw new Exception("机器状态异常--入库失败", e);
		}
		
	}
	
	/***
	 * 生成返回报文
	 * @param resCode
	 * @param resMsg
	 * @return
	 */
	private String makeResMsg(String resCode, String resMsg){
		
		XStream resXs = new XStream(new XppDriver(new XStreamNameCoder()));
		resXs.autodetectAnnotations(true);

		Tms0002ResBean resBean = new Tms0002ResBean();
		resBean.getHeadBean().setResCode(resCode);
		resBean.getHeadBean().setResMsg(resMsg);
		
		return resXs.toXML(resBean);
	}


}
