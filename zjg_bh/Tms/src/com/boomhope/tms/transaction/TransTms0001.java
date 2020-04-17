package com.boomhope.tms.transaction;

import java.util.List;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.tms.Tms0001ReqBean;
import com.boomhope.tms.message.in.tms.Tms0001ReqBodyBean;
import com.boomhope.tms.message.in.tms.Tms0001ResBean;
import com.boomhope.tms.message.in.tms.Tms0001ResBodyBean;
import com.boomhope.tms.service.IDeployMachineService;
import com.boomhope.tms.util.EncryptUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/***
 * 机器登录(Tms0001)交易处理
 * @author shaopeng
 *
 */
public class TransTms0001 extends BaseTransaction {
	Logger logger = Logger.getLogger(TransTms0001.class);
	private Tms0001ReqBean reqBean;	// 渠道请求Bean
	private Tms0001ResBean resBean;	// 渠道响应Bean
	
	/***
	 * 交易处理
	 * @param inReqMsg
	 * @return
	 */
	public String handle(String inReqMsg){
		
		/**
		 * 1. 解析渠道请求报文
		 * 2. 查询机器部署信息(IP地址+状态<>'2')
		 * 3. 生成渠道响应报文
		 */
		
		// 部署机器Service
		IDeployMachineService deployMachineService = null;
		try {
			deployMachineService = (IDeployMachineService) TransTms0001.getBean("deployMachineService");
		} catch (Exception e) {
			logger.error("调用部署机器Service失败", e);
			return makeFailMsg("999999", "调用部署机器Service失败:" + e.getMessage());
		}
		try {
			/* 解析请求报文并生成请求报文Bean */
			XStream reqXs = new XStream(new DomDriver("UTF-8"));
			reqXs.alias("Root", Tms0001ReqBean.class);
			reqXs.alias("Head", InReqHeadBean.class);
			reqXs.alias("Body", Tms0001ReqBodyBean.class);
			reqBean = (Tms0001ReqBean)reqXs.fromXML(inReqMsg);
			
			/* 查询部署机器信息 */
			List<DeployMachine> machineList = deployMachineService.findDeployMachineMC1(reqBean.getBodyBean().getMachineIp());
			if (machineList.size() == 0){
				return makeFailMsg("999999", "IP不存在或该机器已下线");
			}
			
			DeployMachine machine = machineList.get(0);
			
			String[] workKey = EncryptUtils.fetchNewWorkKey(machine.getMajorkeyflag(), machine.getWorkkeyflag());
			String keys = "";
			if(workKey!=null){
				keys = workKey[0];
			}else{
				return makeFailMsg("999999", "获取密钥失败");
			}
			/* 生成渠道响应报文Bean */
			resBean = new Tms0001ResBean(); 
			resBean.getHeadBean().setResCode("000000");
			resBean.getHeadBean().setResMsg("交易成功");
			
			resBean.getBodyBean().setMachineNo(machine.getMachineNo());
			resBean.getBodyBean().setKeys(keys);
			resBean.getBodyBean().setBranchNo(machine.getBaseUnit().getUnitCode());
			resBean.getBodyBean().setTellerNo(machine.getTellerNo());
			resBean.getBodyBean().setMacFacNum(machine.getMacFacNum());
			resBean.getBodyBean().setUnitName(machine.getBaseUnit().getUnitName());
			
			/* 生成渠道响应报文*/
			XStream resXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			resXs.alias("Root", Tms0001ResBean.class);
			resXs.alias("Head", InResHeadBean.class);
			resXs.alias("Body", Tms0001ResBodyBean.class);
			
			return resXs.toXML(resBean);
		} catch (Exception e) {
			logger.error("生成响应报文异常", e);
			return makeFailMsg("999999", "生成响应报文异常");
		}
		
		
	}

}
