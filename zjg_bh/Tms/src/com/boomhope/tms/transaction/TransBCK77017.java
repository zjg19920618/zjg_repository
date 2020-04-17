package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK77017ReqBean;
import com.boomhope.tms.message.in.bck.BCK77017ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK77017ResBean;
import com.boomhope.tms.message.in.bck.BCK77017ResBodyBean;
import com.boomhope.tms.message.in.bck.BCK77017ReqBean;
import com.boomhope.tms.message.in.bck.BCK77017ResBean;
import com.boomhope.tms.message.in.bck.BCK77017ResBodyBean;
import com.boomhope.tms.message.out.Out77017ReqBean;
import com.boomhope.tms.message.out.Out77017ReqBodyBean;
import com.boomhope.tms.message.out.Out77017ResBean;
import com.boomhope.tms.message.out.Out77017ResBodyBean;
import com.boomhope.tms.message.out.Out77017ReqBean;
import com.boomhope.tms.message.out.Out77017ResBean;
import com.boomhope.tms.message.out.OutHeadBean;
import com.boomhope.tms.message.out.OutResponseBean;
import com.boomhope.tms.service.IDeployMachineService;
import com.boomhope.tms.service.ITransFlowService;
import com.boomhope.tms.socket.SocketClient;
import com.boomhope.tms.util.DateUtil;
import com.boomhope.tms.util.Encrypt;
import com.boomhope.tms.util.MACProtocolUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;
/**
 * 按交易授权前置77017
 * @author Administrator
 *
 */
public class TransBCK77017 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK77017.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK77017ReqBean inReqBean;	// 渠道请求Bean
	BCK77017ResBean inResBean;	// 渠道响应Bean
	Out77017ReqBean	outReqBean;	// 前置请求Bean
	Out77017ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	TransFlow transFlow;	// 交易流水Bean
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK77017.getBean("TransFlowService");
			deployMachineService = (IDeployMachineService) TransTms0001.getBean("deployMachineService");
		} catch (Exception e) {
			logger.error("调用交易流水Service失败", e);
			return makeResErrorMsg("调用交易流水Service失败","service");
		}
		
		try {
			try {
				//解析渠道报文
				logger.info("终端发送请求报文到p端"+msg);
				inReqBean = makeInReqBean(msg);
			} catch (Exception e) {
				logger.error("解析渠道请求报文异常", e);
				return makeResErrorMsg("解析渠道请求报文异常","service");
			}
			// 获取机器部署信息
			mac = deployMachineService.getDeployMachineById(inReqBean.getHeadBean().getMachineNo());
			
			//生成前置请求报文 
			byte[] outReqMsg = makeOutReqMsg();
			logger.info("向前置发送77017请求"+outReqMsg);
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			logger.info("接刷前置响应77017请求"+outReqMsg);
			
			//解析前置响应报文 
			outResBean = makeOutResBean(outResMsg);
			
			//然后将Out77017ResBean类中的数据放入BCK77017ResBean中，返回
			//返回信息
			String resMsg=makeResMsg();
			logger.info("前置返回响应报文"+resMsg);
			return resMsg;
		} catch (Exception e) {
			logger.error("前置返回响应报文异常",e);
			String resErrorMsg=makeResErrorMsg(e.getMessage(),null);
			logger.info(resErrorMsg);
			return resErrorMsg;
		}
		
	}

	private String makeResErrorMsg(String msg,String service) {
		
		inResBean = new BCK77017ResBean();
		BCK77017ResBodyBean bck77017ResBodyBean = new BCK77017ResBodyBean();
		
		inResBean.setBody(bck77017ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK77017ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK77017ResBean();
			BCK77017ResBodyBean BCK77017ResBodyBean = new BCK77017ResBodyBean();
			inResBean.setBody(BCK77017ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK77017ResBean.class);
			return reqXs.toXML(inResBean);
			
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
		
	}

	private Out77017ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("ROOT", Out77017ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out77017ResBodyBean.class);
			return (Out77017ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			//拼接实体类
			outReqBean = new Out77017ReqBean();
			Out77017ReqBodyBean accountBody = new Out77017ReqBodyBean();
			accountBody.setAUTH_TRAN_CODE(inReqBean.getBody().getAUTH_TRAN_CODE());//授权主交易码
			accountBody.setFIN_PRI_LEN(inReqBean.getBody().getFIN_PRI_LEN());//指纹长度
			accountBody.setFIN_PRI_VAL(inReqBean.getBody().getFIN_PRI_VAL());//指纹值
			accountBody.setSUP_TELLER_NO(inReqBean.getBody().getSUP_TELLER_NO());//授权柜员号
			accountBody.setSUP_TELLER_PWD(inReqBean.getBody().getSUP_TELLER_PWD());//授权密码
			/*授权柜员密码加密处理*/
			if(inReqBean.getBody().getSUP_TELLER_PWD() != null){
				String supTellerPwd = Encrypt.getPinValue(inReqBean.getBody().getSUP_TELLER_PWD());
				accountBody.setSUP_TELLER_PWD(supTellerPwd);
			}
			outReqBean.setBODY(accountBody);
			OutHeadBean headBean = new OutHeadBean();
			headBean.setTRAN_CODE("77017");
			headBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));
			headBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));
			headBean.setCARD_ACCP_TERM_ID(inReqBean.getHeadBean().getMachineNo());//自助设备编号
			headBean.setMER_NO(mac.getMerNo());//商户号
			headBean.setINST_NO(inReqBean.getHeadBean().getBranchNo());//机构号
			headBean.setTELLER_NO(mac.getTellerNo());// 柜员号
			headBean.setCHANNEL(mac.getChannel());// 渠道号
			headBean.setSUP_TELLER_NO(inReqBean.getBody().getSUP_TELLER_NO());
			outReqBean.setHeadBean(headBean);
			//转换xml
			XStream xs = new XStream(new XppDriver(new XStreamNameCoder()));
			xs.autodetectAnnotations(true);
			xs.alias("ROOT", Out77017ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out77017ReqBodyBean.class);
			String xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xs.toXML(outReqBean);
			logger.info("发送前置报文：\n"+xml);
			byte[] bbb = xml.getBytes("GBK");
			return MACProtocolUtils.toRequest(mac.getMackeyflag(), bbb);
		} catch (Exception e) {
			logger.error("生成前置请求报文异常",e);
			throw new Exception("生成前置请求报文异常", e);
		}
		
	}

	private BCK77017ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK77017ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK77017ReqBodyBean.class);
		
		return (BCK77017ReqBean)reqXs.fromXML(msg);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
	}
	
}
