package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK07499ReqBean;
import com.boomhope.tms.message.in.bck.BCK07499ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07499ResBean;
import com.boomhope.tms.message.in.bck.BCK07499ResBodyBean;
import com.boomhope.tms.message.out.Out07499ReqBean;
import com.boomhope.tms.message.out.Out07499ReqBodyBean;
import com.boomhope.tms.message.out.Out07499ResBean;
import com.boomhope.tms.message.out.Out07499ResBodyBean;
import com.boomhope.tms.message.out.OutHeadBean;
import com.boomhope.tms.message.out.OutResponseBean;
import com.boomhope.tms.service.IDeployMachineService;
import com.boomhope.tms.service.ITransFlowService;
import com.boomhope.tms.socket.SocketClient;
import com.boomhope.tms.util.DateUtil;
import com.boomhope.tms.util.EncryptUtils;
import com.boomhope.tms.util.MACProtocolUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;
/**
 * 唐宝x号转入【79100】-前置07499
 * @author hk
 *
 */
public class TransBCK07499 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK07499.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK07499ReqBean inReqBean;	// 渠道请求Bean
	BCK07499ResBean inResBean;	// 渠道响应Bean
	Out07499ReqBean	outReqBean;	// 前置请求Bean
	Out07499ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	TransFlow transFlow;	// 交易流水Bean
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK07499.getBean("TransFlowService");
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
			logger.info("向前置发送07499请求"+outReqMsg);
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendNewServiceMsg(outReqMsg);
			logger.info("接刷前置响应07499请求"+outReqMsg);
			
			//解析前置响应报文 
			outResBean = makeOutResBean(outResMsg);
			
			//然后将Account07499ResBean类中的数据放入BCK07499ResBean中，返回
			//返回信息
			return makeResMsg();
		} catch (Exception e) {
			return makeResErrorMsg(e.getMessage(),null);
		}
		
	}

	private String makeResErrorMsg(String msg,String service) {
		inResBean = new BCK07499ResBean();
		BCK07499ResBodyBean BCK07499ResBodyBean = new BCK07499ResBodyBean();
		
		inResBean.setBody(BCK07499ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07499ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK07499ResBean();
			BCK07499ResBodyBean BCK07499ResBodyBean = new BCK07499ResBodyBean();
			BCK07499ResBodyBean.setSVR_DATE(outResBean.getBody().getSVR_DATE());//核心日期
			BCK07499ResBodyBean.setSVR_JRNL_NO(outResBean.getBody().getSVR_JRNL_NO());//流水号

			inResBean.setBody(BCK07499ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK07499ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}

	private Out07499ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Out07499ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out07499ResBodyBean.class);
			
			return (Out07499ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out07499ReqBean();
			Out07499ReqBodyBean accountBody = new Out07499ReqBodyBean();
			accountBody.setCARD_NO(inReqBean.getBody().getCARD_NO());//卡号
			accountBody.setCUST_NO(inReqBean.getBody().getCUST_NO());//客户号
			accountBody.setID_NO(inReqBean.getBody().getID_NO());//证件号码
			accountBody.setID_TYPE(inReqBean.getBody().getID_TYPE());//证件类别
			
			/*密码加密处理*/
			String pwd = inReqBean.getBody().getPASSWORD();//获取终端密文密码
			logger.info("加密前：   "+pwd);
			String tranPwd=EncryptUtils.tranPin310(mac.getWorkkeyflag(), mac.getKeyflag(), inReqBean.getBody().getCARD_NO(), pwd);
			logger.info("加密后：   "+tranPwd);
			logger.debug("*******转加密密文："+tranPwd);
			accountBody.setPASSWORD(tranPwd);//密码
			
			accountBody.setPROD_CODE(inReqBean.getBody().getPROD_CODE());//产品代码
			accountBody.setSUB_ACCT_NO(inReqBean.getBody().getSUB_ACCT_NO());//子账号
			accountBody.setTRAN_AMT(inReqBean.getBody().getTRAN_AMT());//转入金额
			
			
			outReqBean.setBody(accountBody);
			OutHeadBean headBean = new OutHeadBean();
			headBean.setTRAN_CODE("07499");
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
			
			xs.alias("ROOT", Out07499ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out07499ReqBodyBean.class);
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

	private BCK07499ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK07499ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK07499ReqBodyBean.class);
		
		return (BCK07499ReqBean)reqXs.fromXML(msg);
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
