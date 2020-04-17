package com.boomhope.tms.transaction;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.BusBillMan;
import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.tms.Tms0005ReqBean;
import com.boomhope.tms.message.in.tms.Tms0005ReqBodyBean;
import com.boomhope.tms.message.in.tms.Tms0005ResBean;
import com.boomhope.tms.message.in.tms.Tms0005ResBodyBean;
import com.boomhope.tms.service.IFlowService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * @Description: 凭证号信息修改
 * @author zjg   
 * @date 2016年11月16日 下午12:17:32
 */
public class TransTms0006 extends BaseTransaction{
	Logger logger = Logger.getLogger(TransTms0006.class);
	private Tms0005ReqBean reqBean;	// 渠道请求Bean
	private Tms0005ResBean resBean;	// 渠道响应Bean
	/***
	 * 交易处理
	 * @param inReqMsg
	 * @return
	 */
	public String handle(String inReqMsg){
		
		/**
		 * 1. 解析渠道请求报文
		 * 2. 修改凭证信息
		 * 3. 生成渠道响应报文
		 */
		IFlowService busBillManService = null; 
		try {
			busBillManService  = (IFlowService)TransTms0005.getBean("flowService");
		} catch (Exception e) {
			logger.error("调用凭证信息service失败",e);
			return makeFailMsg("999999", "调用凭证信息Service失败:" + e.getMessage());
		}
		try {
			/* 解析请求报文并生成请求报文Bean */
			XStream reqXs = new XStream(new DomDriver("UTF-8"));
			reqXs.alias("Root", Tms0005ReqBean.class);
			reqXs.alias("Head", InReqHeadBean.class);
			reqXs.alias("Body", Tms0005ReqBodyBean.class);
			reqBean = (Tms0005ReqBean)reqXs.fromXML(inReqMsg);
			
			try {
				/* 修改凭证信息 */
				BusBillMan busBillMan = new BusBillMan();	
				DeployMachine de =new DeployMachine();
				if("".equals(reqBean.getBodyBean().getISNo())){
					busBillMan.setId(Integer.parseInt(reqBean.getBodyBean().getID())); //修改的id
					DeployMachine deployMachine = new DeployMachine();
					deployMachine.setMachineNo(reqBean.getBodyBean().getMACHINE_NO());
					busBillMan.setDeployMachine(deployMachine); //修改的id
					busBillMan.setStartBno(reqBean.getBodyBean().getSTART_NO()); //开始凭证号
					busBillMan.setEndBno(reqBean.getBodyBean().getEND_NO()); //结束凭证号
					busBillMan.setNowBno(reqBean.getBodyBean().getNOW_BO()); //修改当前凭证号
					busBillMan.setCreateDate(reqBean.getBodyBean().getCREATE_DATE()); //创建时间
					busBillMan.setUpdateDate(reqBean.getBodyBean().getUPDATE_DATE()); //更改时间
					busBillManService.editBusBill(busBillMan);
					
				}else if("0".equals(reqBean.getBodyBean().getISNo())){
					busBillMan.setId(Integer.parseInt(reqBean.getBodyBean().getID())); //修改的id
					busBillMan.setStartBno(reqBean.getBodyBean().getSTART_NO()); //修改
					busBillMan.setEndBno(reqBean.getBodyBean().getEND_NO());
					busBillMan.setNowBno(reqBean.getBodyBean().getNOW_BO());
					de.setMachineNo(reqBean.getBodyBean().getMACHINE_NO());
					busBillMan.setDeployMachine(de);
					busBillMan.setCreateDate(reqBean.getBodyBean().getCREATE_DATE());
					busBillMan.setUpdateDate(reqBean.getBodyBean().getUPDATE_DATE());
					//修改
					busBillManService.editBusBill(busBillMan);
				}else if("1".equals(reqBean.getBodyBean().getISNo())){
					busBillMan.setStartBno(reqBean.getBodyBean().getSTART_NO()); //添加
					busBillMan.setEndBno(reqBean.getBodyBean().getEND_NO());
					busBillMan.setNowBno(reqBean.getBodyBean().getNOW_BO());
					de.setMachineNo(reqBean.getBodyBean().getMACHINE_NO());
					busBillMan.setDeployMachine(de);
					busBillMan.setCreateDate(reqBean.getBodyBean().getCREATE_DATE());
					busBillMan.setUpdateDate(reqBean.getBodyBean().getUPDATE_DATE());
					//添加
					busBillManService.saveBusBill(busBillMan);
				}
			} catch (Exception e) {
				logger.error("连接数据库异常", e);
				return makeFailMsg("999999", "连接数据库异常");
			}
			
			/* 生成渠道响应报文Bean */
			resBean = new Tms0005ResBean(); 
			resBean.getHeadBean().setResCode("000000");
			resBean.getHeadBean().setResMsg("交易成功");
			
			/* 生成渠道响应报文*/
			XStream resXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			resXs.alias("Root", Tms0005ResBean.class);
			resXs.alias("Head", InResHeadBean.class);
			resXs.alias("Body", Tms0005ResBodyBean.class);
			return resXs.toXML(resBean);
		} catch (Exception e) {
			logger.error("生成响应报文异常", e);
			return makeFailMsg("999999", "生成响应报文异常");
		}
	}
}
