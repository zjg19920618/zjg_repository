package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK03532ReqBean;
import com.boomhope.tms.message.in.bck.BCK03532ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03532ResBean;
import com.boomhope.tms.message.in.bck.BCK03532ResBodyBean;
import com.boomhope.tms.message.out.OutHeadBean;
import com.boomhope.tms.message.out.Out03532ReqBean;
import com.boomhope.tms.message.out.Out03532ReqBodyBean;
import com.boomhope.tms.message.out.Out03532ResBean;
import com.boomhope.tms.message.out.Out03532ResBodyBean;
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
 * 推荐人奖励领取【17030】前置-03532
 * @author ly
 *
 */

public class TransBCK03532 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK03532.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK03532ReqBean inReqBean;	// 渠道请求Bean
	BCK03532ResBean inResBean;	// 渠道响应Bean
	Out03532ReqBean	outReqBean;	// 前置请求Bean
	Out03532ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	TransFlow transFlow;	// 交易流水Bean
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK03532.getBean("TransFlowService");
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
			logger.info("向前置发送03532请求"+outReqMsg);
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			logger.info("接刷前置响应03532请求"+outReqMsg);
			
			//解析前置响应报文 
			outResBean = makeOutResBean(outResMsg);
			
			//然后将Account03532ResBean类中的数据放入BCK03532ResBean中，返回
			//返回信息
			return makeResMsg();
		} catch (Exception e) {
			return makeResErrorMsg(e.getMessage(),null);
		}
		
	}


	private String makeResErrorMsg(String msg,String service) {
		
		inResBean = new BCK03532ResBean();
		BCK03532ResBodyBean BCK03532ResBodyBean = new BCK03532ResBodyBean();
		
		inResBean.setBody(BCK03532ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03532ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK03532ResBean();
			BCK03532ResBodyBean BCK03532ResBodyBean = new BCK03532ResBodyBean();
			BCK03532ResBodyBean.setSVR_DATE(outResBean.getBody().getSVR_DATE());//核心日期
			BCK03532ResBodyBean.setSVR_JRNL_NO(outResBean.getBody().getSVR_JRNL_NO());//核心流水号
			
			inResBean.setBody(BCK03532ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK03532ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}

	private Out03532ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Out03532ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out03532ResBodyBean.class);
			
			return (Out03532ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out03532ReqBean();
			Out03532ReqBodyBean accountBody = new Out03532ReqBodyBean();
			accountBody.setACCESS_CODE(inReqBean.getBody().getACCESS_CODE());//领取码
			accountBody.setCUST_NO(inReqBean.getBody().getCUST_NO());//客户号
			
			outReqBean.setBody(accountBody);
			OutHeadBean headBean = new OutHeadBean();
			headBean.setTRAN_CODE("03532");
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
			
			xs.alias("ROOT", Out03532ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out03532ReqBodyBean.class);
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

	private BCK03532ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK03532ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK03532ReqBodyBean.class);
		
		return (BCK03532ReqBean)reqXs.fromXML(msg);
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
