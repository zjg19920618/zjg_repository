package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK07665ReqBean;
import com.boomhope.tms.message.in.bck.BCK07665ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07665ResBean;
import com.boomhope.tms.message.in.bck.BCK07665ResBodyBean;
import com.boomhope.tms.message.out.Out02209ReqBean;
import com.boomhope.tms.message.out.Out02209ReqBodyBean;
import com.boomhope.tms.message.out.Out02209ResBean;
import com.boomhope.tms.message.out.Out02209ResBodyBean;
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

public class TransBCK07665 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK07665.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK07665ReqBean inReqBean;	// 渠道请求Bean
	BCK07665ResBean inResBean;	// 渠道响应Bean
	Out02209ReqBean	outReqBean;	// 前置请求Bean
	Out02209ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK07665.getBean("TransFlowService");
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
			logger.info("向前置发送07665请求"+outReqMsg);
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			logger.info("接收前置07665响应"+outResMsg);
			
			//解析前置响应报文 
			outResBean = makeOutResBean(outResMsg);
			
			//返回信息
			return makeResMsg();
		} catch (Exception e) {
			return makeResErrorMsg(e.getMessage(),null);
		}
		
	}


	private String makeResErrorMsg(String msg,String service) {
				
		inResBean = new BCK07665ResBean();
		BCK07665ResBodyBean bck07665ResBodyBean = new BCK07665ResBodyBean();
		
		inResBean.setBody(bck07665ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07665ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK07665ResBean();
			BCK07665ResBodyBean bck07665ResBodyBean = new BCK07665ResBodyBean();
			bck07665ResBodyBean.setAMT_ACCT_NO(outResBean.getBODY().getAMT_ACCT_NO());
			bck07665ResBodyBean.setCUST_NAME(outResBean.getBODY().getCUST_NAME());
			bck07665ResBodyBean.setCUST_NO(outResBean.getBODY().getCUST_NO());
			bck07665ResBodyBean.setINT_FROM_ACCT(outResBean.getBODY().getINT_FROM_ACCT());
			bck07665ResBodyBean.setPUT_INT_ACCT(outResBean.getBODY().getPUT_INT_ACCT());
			bck07665ResBodyBean.setR_BACK_COUNT(outResBean.getBODY().getR_BACK_COUNT());
			bck07665ResBodyBean.setR_BACK_EXCHANGE_AMT(outResBean.getBODY().getR_BACK_EXCHANGE_AMT());
			bck07665ResBodyBean.setSVR_DATE(outResBean.getBODY().getSVR_DATE());
			bck07665ResBodyBean.setSVR_JRNL_NO(outResBean.getBODY().getSVR_JRNL_NO());
			inResBean.setBody(bck07665ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK07665ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}

	private Out02209ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Out02209ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out02209ResBodyBean.class);
			
			return (Out02209ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out02209ReqBean();
			//拼接输出的body
			Out02209ReqBodyBean outBody = new Out02209ReqBodyBean();
			outBody.setACCT_NO(inReqBean.getBody().getACCT_NO());//账号
			outBody.setBACK_COUNT(inReqBean.getBody().getBACK_COUNT());//收回唐豆数量
			outBody.setBACK_EXCHANGE_AMT(inReqBean.getBody().getBACK_EXCHANGE_AMT());//收回兑现金额
			outBody.setBACK_PROP(inReqBean.getBody().getBACK_PROP());//收回比例
			outBody.setOPPO_ACCT_NO(inReqBean.getBody().getOPPO_ACCT_NO());//对方账号
			outBody.setORG_EXCHANGE_MODE(inReqBean.getBody().getORG_EXCHANGE_MODE());//原唐豆兑换方式(0-现金，1-转账，2-兑物)
			outBody.setORG_EXCHANGE_PROP(inReqBean.getBody().getORG_EXCHANGE_PROP());//原唐豆兑现比例
			outBody.setPASSWORD(inReqBean.getBody().getPASSWORD());//对方账号密码
			outBody.setPAY_AMT(inReqBean.getBody().getPAY_AMT());//支取金额
			outBody.setPAY_DATE(inReqBean.getBody().getPAY_DATE());//支取日期
			outBody.setPAY_JRNL(inReqBean.getBody().getPAY_JRNL());//支取流水
			outBody.setRECOV_TYPE(inReqBean.getBody().getRECOV_TYPE());//唐豆收回方式(0-现金 1-转账 2-兑物)
			outBody.setSUB_ACCT_NO(inReqBean.getBody().getSUB_ACCT_NO());//子账号
			outReqBean.setBODY(outBody);
			OutHeadBean outHeadBean = new OutHeadBean();
			outHeadBean.setTRAN_CODE("07665");
			outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
			outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));//交易时间
			outHeadBean.setCARD_ACCP_TERM_ID(inReqBean.getHeadBean().getMachineNo());//自助设备编号
			outHeadBean.setMER_NO(mac.getMerNo());//商户号
			outHeadBean.setINST_NO(inReqBean.getHeadBean().getBranchNo());//机构号
			outHeadBean.setTELLER_NO(mac.getTellerNo());// 柜员号
			outHeadBean.setCHANNEL(mac.getChannel());// 渠道号
			outReqBean.setHeadBean(outHeadBean);
			//转换xml
			XStream xs = new XStream(new XppDriver(new XStreamNameCoder()));
			xs.autodetectAnnotations(true);
			
			xs.alias("ROOT", Out02209ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out02209ReqBodyBean.class);
			String xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xs.toXML(outReqBean);
			logger.info("发送前置报文：\n"+xml);
			byte[] bbb = xml.getBytes("GBK");
			return MACProtocolUtils.toRequest(mac.getMackeyflag(), bbb);
		} catch (Exception e) {
			logger.error("生成前置请求报文异常",e);
			throw new Exception("生成前置请求报文异常", e);
		}
		
	}

	private BCK07665ReqBean makeInReqBean(String msg)throws Exception {
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07665ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK07665ReqBodyBean.class);
		
		return (BCK07665ReqBean)reqXs.fromXML(msg);
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
