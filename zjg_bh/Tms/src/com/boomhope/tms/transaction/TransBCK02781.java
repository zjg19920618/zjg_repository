package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK02781ReqBean;
import com.boomhope.tms.message.in.bck.BCK02781ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK02781ResBean;
import com.boomhope.tms.message.in.bck.BCK02781ResBodyBean;
import com.boomhope.tms.message.out.Out02781ReqBean;
import com.boomhope.tms.message.out.Out02781ReqBodyBean;
import com.boomhope.tms.message.out.Out02781ResBean;
import com.boomhope.tms.message.out.Out02781ResBodyBean;
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
 * 账户限额查询【02879】-前置02781(2类帐限额)
 * @author zjg
 *
 */

public class TransBCK02781 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK02781.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK02781ReqBean inReqBean;	// 渠道请求Bean
	BCK02781ResBean inResBean;	// 渠道响应Bean
	Out02781ReqBean	outReqBean;	// 前置请求Bean
	Out02781ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	TransFlow transFlow;	// 交易流水Bean
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK02781.getBean("TransFlowService");
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
			logger.info("向前置发送02781请求"+outReqMsg);
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendNewServiceMsg(outReqMsg);
			logger.info("接刷前置响应02781请求"+outReqMsg);
			
			//解析前置响应报文 
			outResBean = makeOutResBean(outResMsg);
			
			//然后将AccountQRY00ResBean类中的数据放入BCKQRY00ResBean中，返回
			//返回信息
			return makeResMsg();
		} catch (Exception e) {
			return makeResErrorMsg(e.getMessage(),null);
		}
		
	}


	private String makeResErrorMsg(String msg,String service) {
		
		inResBean = new BCK02781ResBean();
		BCK02781ResBodyBean BCK02781ResBodyBean = new BCK02781ResBodyBean();
		
		inResBean.setBODY(BCK02781ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK02781ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK02781ResBean();
			BCK02781ResBodyBean BCK02781ResBodyBean = new BCK02781ResBodyBean();
			BCK02781ResBodyBean.setSVR_DATE(outResBean.getBODY().getSVR_DATE());//核心日期
			BCK02781ResBodyBean.setSVR_JRNL_NO(outResBean.getBODY().getSVR_JRNL_NO());//流水号
			inResBean.setBODY(BCK02781ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK02781ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}

	private Out02781ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Out02781ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out02781ResBodyBean.class);
			
			return (Out02781ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out02781ReqBean();
			Out02781ReqBodyBean accountBody = new Out02781ReqBodyBean();
			accountBody.setACCT_NO(inReqBean.getBody().getACCT_NO());//账号
			accountBody.setDB_CR_FLAG(inReqBean.getBody().getDB_CR_FLAG());//借贷标志
			accountBody.setOPPO_ACCT_NO(inReqBean.getBody().getOPPO_ACCT_NO());//对方账户
			accountBody.setTRAN_AMT(inReqBean.getBody().getTRAN_AMT());//发生额
			//转换xml
			XStream xs = new XStream(new XppDriver(new XStreamNameCoder()));
			xs.autodetectAnnotations(true);
			
			outReqBean.setBODY(accountBody);
			OutHeadBean outHeadBean = new OutHeadBean();
			outHeadBean.setTRAN_CODE("02781");
			outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));
			outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));
			outHeadBean.setCARD_ACCP_TERM_ID(inReqBean.getHeadBean().getMachineNo());//自助设备编号
			outHeadBean.setMER_NO(mac.getMerNo());//商户号
			outHeadBean.setINST_NO(inReqBean.getHeadBean().getBranchNo());//机构号
			outHeadBean.setTELLER_NO(mac.getTellerNo());// 柜员号
			outHeadBean.setCHANNEL(mac.getChannel());// 渠道号
			outReqBean.setHeadBean(outHeadBean);
			
			xs.alias("ROOT", Out02781ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out02781ReqBodyBean.class);
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

	private BCK02781ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK02781ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK02781ReqBodyBean.class);
		
		return (BCK02781ReqBean)reqXs.fromXML(msg);
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
