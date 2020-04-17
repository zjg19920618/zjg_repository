package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK07622ReqBean;
import com.boomhope.tms.message.in.bck.BCK07622ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07622ResBean;
import com.boomhope.tms.message.in.bck.BCK07622ResBodyBean;
import com.boomhope.tms.message.out.Out02210ReqBean;
import com.boomhope.tms.message.out.Out02210ReqBodyBean;
import com.boomhope.tms.message.out.Out02210ResBean;
import com.boomhope.tms.message.out.Out02210ResBodyBean;
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

public class TransBCK07622 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK07622.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK07622ReqBean inReqBean;	// 渠道请求Bean
	BCK07622ResBean inResBean;	// 渠道响应Bean
	Out02210ReqBean	outReqBean;	// 前置请求Bean
	Out02210ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK07622.getBean("TransFlowService");
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
			logger.info("向前置发送07622请求"+outReqMsg);
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			logger.info("接刷前置响应07622请求"+outReqMsg);
			
			//解析前置响应报文 
			outResBean = makeOutResBean(outResMsg);
			
			//然后将Out02210ResBean类中的数据放入BCK07622ResBean中，返回
			//返回信息
			return makeResMsg();
		} catch (Exception e) {
			return makeResErrorMsg(e.getMessage(),null);
		}
		
	}

	private String makeResErrorMsg(String msg,String service) {
		
		inResBean = new BCK07622ResBean();
		BCK07622ResBodyBean bck07622ResBodyBean = new BCK07622ResBodyBean();
		
		inResBean.setBody(bck07622ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07622ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK07622ResBean();
			BCK07622ResBodyBean bck07622ResBodyBean = new BCK07622ResBodyBean();
			bck07622ResBodyBean.setBack_Count(outResBean.getBODY().getBACK_COUNT());
			bck07622ResBodyBean.setBack_Exchange_Amt(outResBean.getBODY().getBACK_EXCHANGE_AMT());
			bck07622ResBodyBean.setCurBal(outResBean.getBODY().getCUR_BAL());
			bck07622ResBodyBean.setCustName(outResBean.getBODY().getCUST_NAME());
			bck07622ResBodyBean.setCustNo(outResBean.getBODY().getCUST_NO());
			bck07622ResBodyBean.setDepTerm(outResBean.getBODY().getDEP_TERM());
			bck07622ResBodyBean.setExchangeMode(outResBean.getBODY().getEXCHANGE_MODE());
			bck07622ResBodyBean.setOpenDate(outResBean.getBODY().getOPEN_DATE());
			bck07622ResBodyBean.setOrgExchangeMode(outResBean.getBODY().getORG_EXCHANGE_MODE());
			bck07622ResBodyBean.setOrgExchangeProp(outResBean.getBODY().getORG_EXCHANGE_PROP());
			bck07622ResBodyBean.setPayAmt(outResBean.getBODY().getPAY_AMT());
			bck07622ResBodyBean.setProductCode(outResBean.getBODY().getPRODUCT_CODE());
			bck07622ResBodyBean.setTermCode(outResBean.getBODY().getTERM_CODE());
			inResBean.setBody(bck07622ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK07622ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}

	private Out02210ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Out02210ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out02210ResBodyBean.class);
			
			return (Out02210ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out02210ReqBean();
			Out02210ReqBodyBean outBody = new Out02210ReqBodyBean();
			outBody.setACCT_NO(inReqBean.getBody().getAcctNo());//账号
			outBody.setSUB_ACCT_NO(inReqBean.getBody().getSubAcctNo());//子账号（一本通时必输）
			outBody.setPAY_DATE(inReqBean.getBody().getPayDate());//日期
			outBody.setPAY_JRNL(inReqBean.getBody().getPayJrnl());//流水号
			outReqBean.setBODY(outBody);
			OutHeadBean headBean = new OutHeadBean();
			headBean.setTRAN_CODE("07622");
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
			
			xs.alias("ROOT", Out02210ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out02210ReqBodyBean.class);
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

	private BCK07622ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK07622ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK07622ReqBodyBean.class);
		
		return (BCK07622ReqBean)reqXs.fromXML(msg);
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
