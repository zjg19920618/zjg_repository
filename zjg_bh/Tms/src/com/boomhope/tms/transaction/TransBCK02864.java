package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK02864ReqBean;
import com.boomhope.tms.message.account.in.BCK02864ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK02864ResBean;
import com.boomhope.tms.message.account.in.BCK02864ResBodyBean;
import com.boomhope.tms.message.account.out.Account02864ReqBean;
import com.boomhope.tms.message.account.out.Account02864ReqBodyBean;
import com.boomhope.tms.message.account.out.Account02864ResBean;
import com.boomhope.tms.message.account.out.Account02864ResBodyBean;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
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

public class TransBCK02864 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK02864.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK02864ReqBean inReqBean;	// 渠道请求Bean
	BCK02864ResBean inResBean;	// 渠道响应Bean
	Account02864ReqBean	outReqBean;	// 前置请求Bean
	Account02864ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	TransFlow transFlow;	// 交易流水Bean
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK02864.getBean("TransFlowService");
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
			/* 记录流水信息 */
			addTransFlow();
			
			//生成前置请求报文 
			byte[] outReqMsg = makeOutReqMsg();
			logger.info("向前置发送02864请求"+outReqMsg);
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			logger.info("接刷前置响应02864请求"+outReqMsg);
			
			//解析前置响应报文 
			outResBean = makeOutResBean(outResMsg);
			
			/* 更新流水信息 */
			updateTransFlow();
			
			//然后将Account02864ResBean类中的数据放入BCK02864ResBean中，返回
			//返回信息
			return makeResMsg();
		} catch (Exception e) {
			return makeResErrorMsg(e.getMessage(),null);
		}
		
	}

	private void updateTransFlow() throws Exception {
		try {
			transFlow.setBusinessStatus(outResBean.getResponseBean().getRetCode());
			transFlow.setTransStatus("S");
			transFlow.setTransStatusDesc("交易成功");
			transFlowService.updateTransFlow(transFlow);
		} catch (Exception e) {
			logger.error("前置返回交易入库失败", e);
			throw new Exception("前置返回交易入库失败", e);
		}
	}

	private void addTransFlow() throws Exception {
		try {
			transFlow = new TransFlow();
			transFlow.setTransCode("BCK_02864");
			transFlow.setTransName("回单机-产品利率信息查询");
			transFlow.setProductCode("BCK-回单机");
			transFlow.setMachineType(inReqBean.getHeadBean().getMachineType());
			transFlow.setBranchNo(inReqBean.getHeadBean().getBranchNo());
			transFlow.setMachineId(inReqBean.getHeadBean().getMachineNo());
			transFlow.setBusinessStatus("0");
			transFlow.setTransStatus("1");
			transFlow.setTransStatusDesc("接收交易请求");
			transFlow.setTransDate(DateUtil.getDateTime("yyyyMMdd"));
			transFlow.setTransTime(DateUtil.getDateTime("HHmmss"));
			
			transFlowService.addTransFlow(transFlow);
		} catch (Exception e) {
			logger.error("接收交易请求入库失败", e);
			throw new Exception("接收交易请求入库失败", e);
		}
	}

	private String makeResErrorMsg(String msg,String service) {
		if(null == service){
			//修改入库失败操作
			transFlow.setTransStatus("E");
			transFlow.setTransStatusDesc("交易失败");
			transFlowService.updateTransFlow(transFlow);
		}
		
		inResBean = new BCK02864ResBean();
		BCK02864ResBodyBean BCK02864ResBodyBean = new BCK02864ResBodyBean();
		
		inResBean.setBody(BCK02864ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK02864ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK02864ResBean();
			BCK02864ResBodyBean BCK02864ResBodyBean = new BCK02864ResBodyBean();
			BCK02864ResBodyBean.setFILE_NAME(outResBean.getBODY().getFILE_NAME());
			BCK02864ResBodyBean.setSVR_DATE(outResBean.getBODY().getSVR_DATE());
			BCK02864ResBodyBean.setSVR_JRNL_NO(outResBean.getBODY().getSVR_JRNL_NO());
			BCK02864ResBodyBean.setBIRTHDAY_FLOAT(outResBean.getBODY().getBIRTHDAY_FLOAT());
			BCK02864ResBodyBean.setCHANNEL_FLOAT(outResBean.getBODY().getCHANNEL_FLOAT());
			BCK02864ResBodyBean.setCOMBINE_FLOAT(outResBean.getBODY().getCOMBINE_FLOAT());
			BCK02864ResBodyBean.setFLOAT_SUM(outResBean.getBODY().getFLOAT_SUM());
			BCK02864ResBodyBean.setINST_FLOAT(outResBean.getBODY().getINST_FLOAT());
			BCK02864ResBodyBean.setTIME_FLOAT(outResBean.getBODY().getTIME_FLOAT());
			
			inResBean.setBody(BCK02864ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK02864ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}

	private Account02864ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Account02864ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Account02864ResBodyBean.class);
			
			return (Account02864ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Account02864ReqBean();
			Account02864ReqBodyBean AccountBody = new Account02864ReqBodyBean();
			AccountBody.setPROD_CODE(inReqBean.getBody().getPROD_CODE());//产品代码
			AccountBody.setRATE_DATE(inReqBean.getBody().getRATE_DATE());//利率日期
			AccountBody.setFLOAT_FLAG(inReqBean.getBody().getFLOAT_FLAG());//浮动标志
			AccountBody.setCUST_NO(inReqBean.getBody().getCUST_NO());//客户号
			AccountBody.setCHL_ID(inReqBean.getBody().getCHL_ID());//渠道模块标识
			AccountBody.setCUST_LEVEL(inReqBean.getBody().getCUST_LEVEL());//客户评级
			AccountBody.setACCT_NO(inReqBean.getBody().getACCT_NO());//账号
			AccountBody.setSUB_ACCT_NO(inReqBean.getBody().getSUB_ACCT_NO());//子账号
			
			outReqBean.setBODY(AccountBody);
			OutHeadBean headBean = new OutHeadBean();
			headBean.setTRAN_CODE("02864");
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
			
			xs.alias("ROOT", Account02864ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Account02864ReqBodyBean.class);
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

	private BCK02864ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK02864ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK02864ReqBodyBean.class);
		
		return (BCK02864ReqBean)reqXs.fromXML(msg);
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
