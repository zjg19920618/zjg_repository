package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK07696ReqBean;
import com.boomhope.tms.message.in.bck.BCK07696ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07696ResBean;
import com.boomhope.tms.message.in.bck.BCK07696ResBodyBean;
import com.boomhope.tms.message.out.Out02944ReqBean;
import com.boomhope.tms.message.out.Out02944ReqBodyBean;
import com.boomhope.tms.message.out.Out02944ResBean;
import com.boomhope.tms.message.out.Out02944ResBodyBean;
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

public class TransBCK07696 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK07696.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK07696ReqBean inReqBean;	// 渠道请求Bean
	BCK07696ResBean inResBean;	// 渠道响应Bean
	Out02944ReqBean	outReqBean;	// 前置请求Bean
	Out02944ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK07696.getBean("TransFlowService");
			deployMachineService = (IDeployMachineService) TransTms0001.getBean("deployMachineService");
		} catch (Exception e) {
			logger.error("调用交易流水Service失败", e);
			return makeResErrorMsg("调用交易流水Service失败","service");
		}
		try {
			try {
				//解析渠道请求报文
				inReqBean = makeInReqBean(msg);
			} catch (Exception e) {
				logger.error("解析渠道请求报文异常", e);
				return makeResErrorMsg("解析渠道请求报文异常","service");
			}
			// 获取机器部署信息
			mac = deployMachineService.getDeployMachineById(inReqBean.getHeadBean().getMachineNo());
			
			//生成请求前置报文
			byte[] outReqMsg = makeOutReqMsg();
			
			/* 发送前置交易请求 */
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			
			/* 解析前置响应报文 */
			outResBean = makeOutResBean(outResMsg);
			
			//返回信息
			return makeResMsg();
		} catch (Exception e) {
			return makeResErrorMsg(e.getMessage(),null);
		}
	}
	
	private String makeResErrorMsg(String msg,String service) {
				
		inResBean = new BCK07696ResBean();
		BCK07696ResBodyBean bck07696ResBodyBean = new BCK07696ResBodyBean();
		
		inResBean.setBody(bck07696ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07696ResBean.class);
		return reqXs.toXML(inResBean);
	}
	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK07696ResBean();
			//然后将Out02944ResBean类中的数据放入BCK07696ResBean中，返回
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			BCK07696ResBodyBean bck07696ResBodyBean = new BCK07696ResBodyBean();
			bck07696ResBodyBean.setInte(outResBean.getBODY().getINTE());
			bck07696ResBodyBean.setSvrDate(outResBean.getBODY().getSVR_DATE());
			bck07696ResBodyBean.setSvrJrnlNo(outResBean.getBODY().getSVR_JRNL_NO());
			bck07696ResBodyBean.setADVN_INIT(outResBean.getBODY().getADVN_INIT());
			bck07696ResBodyBean.setPAYOFF_INTEREST(outResBean.getBODY().getPAYOFF_INTEREST());
			inResBean.setBody(bck07696ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			reqXs.alias("Root", BCK07696ResBean.class);
			
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out02944ReqBean();
			/* 写入数据 */
			//将BCK07696ReqBean里面的数据放入Out02944ReqBean中，并解析成String字符串
			Out02944ReqBodyBean outBody = new Out02944ReqBodyBean();
			outBody.setACCT_NO(inReqBean.getBody().getAcctNO());//帐号
			OutHeadBean outHeadBean = new OutHeadBean();
			outHeadBean.setTRAN_CODE("07696");//前置交易码
			outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
			outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));//交易时间
			outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));
			outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));
			outHeadBean.setCARD_ACCP_TERM_ID(inReqBean.getHeadBean().getMachineNo());//自助设备编号
			outHeadBean.setMER_NO(mac.getMerNo());//商户号
			outHeadBean.setINST_NO(inReqBean.getHeadBean().getBranchNo());//机构号
			outHeadBean.setTELLER_NO(mac.getTellerNo());// 柜员号
			outHeadBean.setCHANNEL(mac.getChannel());// 渠道号
			outReqBean.setBODY(outBody);
			outReqBean.setHeadBean(outHeadBean);
			XStream xs = new XStream(new XppDriver(new XStreamNameCoder()));
			xs.autodetectAnnotations(true);
			xs.alias("ROOT", Out02944ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out02944ReqBodyBean.class);
			String xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xs.toXML(outReqBean);
			logger.info("发送前置报文：\n"+xml);
			byte[] bbb = xml.getBytes("GBK");
			return MACProtocolUtils.toRequest(mac.getMackeyflag(), bbb);
		} catch (Exception e) {
			logger.error("生成前置请求报文异常",e);
			throw new Exception("生成前置请求报文异常", e);
		}
		
	}

	private Out02944ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8"));
			
			reqXs.alias("ROOT", Out02944ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out02944ResBodyBean.class);
			
			return (Out02944ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	/***
	 * 解析渠道报文并生成请求Bean
	 * @param inReqMsg
	 * @return
	 */
	private BCK07696ReqBean makeInReqBean(String inReqMsg)throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8"));
		
		reqXs.alias("Root", BCK07696ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK07696ReqBodyBean.class);
		
		return (BCK07696ReqBean)reqXs.fromXML(inReqMsg);
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
