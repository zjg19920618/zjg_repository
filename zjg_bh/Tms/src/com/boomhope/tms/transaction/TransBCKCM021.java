package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;


import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCKCM021ReqBean;
import com.boomhope.tms.message.in.bck.BCKCM021ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCKCM021ResBean;
import com.boomhope.tms.message.in.bck.BCKCM021ResBodyBean;
import com.boomhope.tms.message.out.OutCM021ReqBean;
import com.boomhope.tms.message.out.OutCM021ReqBodyBean;
import com.boomhope.tms.message.out.OutCM021ResBean;
import com.boomhope.tms.message.out.OutCM021ResBodyBean;
import com.boomhope.tms.message.out.OutHeadBean;
import com.boomhope.tms.message.out.OutResponseBean;
import com.boomhope.tms.service.IDeployMachineService;
import com.boomhope.tms.service.ITransFlowService;
import com.boomhope.tms.socket.SocketClient;
import com.boomhope.tms.util.DateUtil;
import com.boomhope.tms.util.MACProtocolUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 大小额系统参数查询CM021
 * @author hk
 *
 */

public class TransBCKCM021 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCKCM021.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCKCM021ReqBean inReqBean;	// 渠道请求Bean
	BCKCM021ResBean inResBean;	// 渠道响应Bean
	OutCM021ReqBean	outReqBean;	// 前置请求Bean
	OutCM021ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	TransFlow transFlow;	// 交易流水Bean
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCKCM021.getBean("TransFlowService");
			deployMachineService = (IDeployMachineService) TransTms0001.getBean("deployMachineService");
		} catch (Exception e) {
			logger.error("调用交易流水Service失败", e);
			return makeResErrorMsg("调用交易流水Service失败","service");
		}
		
		try {
			try {
				//解析渠道报文
				inReqBean = makeInReqBean(msg);
			} catch (Exception e) {
				logger.error("解析渠道请求报文异常", e);
				return makeResErrorMsg("解析渠道请求报文异常","service");
			}
			// 获取机器部署信息
			mac = deployMachineService.getDeployMachineById(inReqBean.getHeadBean().getMachineNo());
			
			//生成前置请求报文 
			byte[] outReqMsg = makeOutReqMsg();
			logger.info("向前置发送CM021请求"+outReqMsg);
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendNewServiceMsg(outReqMsg);
			logger.info("接刷前置响应CM021请求"+outReqMsg);
			
			//解析前置响应报文 
			outResBean = makeOutResBean(outResMsg);
			
			//然后将AccountCM021ResBean类中的数据放入BCKCM021ResBean中，返回
			//返回信息
			return makeResMsg();
		} catch (Exception e) {
			return makeResErrorMsg(e.getMessage(),null);
		}
		
	}


	private String makeResErrorMsg(String msg,String service) {
		
		inResBean = new BCKCM021ResBean();
		BCKCM021ResBodyBean BCKCM021ResBodyBean = new BCKCM021ResBodyBean();
		
		inResBean.setBody(BCKCM021ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCKCM021ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCKCM021ResBean();
			BCKCM021ResBodyBean BCKCM021ResBodyBean = new BCKCM021ResBodyBean();
			BCKCM021ResBodyBean.setPARNAME(outResBean.getBODY().getPARNAME());//参数名称
			BCKCM021ResBodyBean.setPARVALUE(outResBean.getBODY().getPARVALUE());//参数值
			inResBean.setBody(BCKCM021ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCKCM021ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}

	private OutCM021ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", OutCM021ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", OutCM021ResBodyBean.class);
			
			return (OutCM021ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new OutCM021ReqBean();
			OutCM021ReqBodyBean accountBody = new OutCM021ReqBodyBean();
			accountBody.setPARCODE(inReqBean.getBody().getPARCODE());//参数代码
			accountBody.setSYSCODE(inReqBean.getBody().getSYSCODE());//系统代码
			outReqBean.setBODY(accountBody);
			OutHeadBean headBean = new OutHeadBean();
			headBean.setTRAN_CODE("CM021");
			headBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));
			headBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));
			headBean.setCARD_ACCP_TERM_ID(inReqBean.getHeadBean().getMachineNo());//自助设备编号
			headBean.setMER_NO(mac.getMerNo());//商户号
			headBean.setINST_NO(inReqBean.getHeadBean().getBranchNo());//机构号
			headBean.setTELLER_NO(mac.getTellerNo());// 柜员号
			headBean.setCHANNEL(mac.getChannel());// 渠道号
			outReqBean.setHeadBean(headBean);
			//转换xml
			XStream xs = new XStream(new XppDriver(new XStreamNameCoder()));
			xs.autodetectAnnotations(true);
			
			xs.alias("ROOT", OutCM021ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", OutCM021ReqBodyBean.class);
			String xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xs.toXML(outReqBean);
			logger.info("发送前置报文：\n"+xml);
			byte[] bbb = xml.getBytes("GBK");
			//String mac = "ZZQZ.00000001.zak";
			return MACProtocolUtils.toRequest(mac.getMackeyflag(), bbb);
		} catch (Exception e) {
			logger.error("生成前置请求报文异常",e);
			throw new Exception("生成前置请求报文异常", e);
		}
		
	}

	private BCKCM021ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCKCM021ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCKCM021ReqBodyBean.class);
		
		return (BCKCM021ReqBean)reqXs.fromXML(msg);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
