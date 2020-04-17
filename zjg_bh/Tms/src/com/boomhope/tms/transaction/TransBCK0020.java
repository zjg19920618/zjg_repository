package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK0020ReqBean;
import com.boomhope.tms.message.in.bck.BCK0020ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0020ResBean;
import com.boomhope.tms.message.in.bck.BCK0020ResBodyBean;
import com.boomhope.tms.message.out.Out02864ReqBean;
import com.boomhope.tms.message.out.Out02864ReqBodyBean;
import com.boomhope.tms.message.out.Out02864ResBean;
import com.boomhope.tms.message.out.Out02864ResBodyBean;
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



public class TransBCK0020 extends BaseTransaction implements ServletContextListener{

	Logger logger = Logger.getLogger(TransBCK0020.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK0020ReqBean inReqBean;	// 渠道请求Bean
	BCK0020ResBean inResBean;	// 渠道响应Bean
	Out02864ReqBean	outReqBean;	// 前置请求Bean
	Out02864ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	TransFlow transFlow;	// 交易流水Bean
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK0020.getBean("TransFlowService");
			deployMachineService = (IDeployMachineService) TransTms0001.getBean("deployMachineService");
		} catch (Exception e) {
			logger.error("调用交易流水Service失败",e);
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
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			
			//解析前置响应报文 
			outResBean = makeOutResBean(outResMsg);
			
			/* 更新流水信息 */
			updateTransFlow();
			
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
			transFlow.setTransCode("BCK_0020");
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
		//返回渠道错误信息
		inResBean = new BCK0020ResBean();
		BCK0020ResBodyBean bck0020ResBodyBean = new BCK0020ResBodyBean();
		
		inResBean.setBody(bck0020ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK0020ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK0020ResBean();
			//然后将Out02864ResBean类中的数据放入BCK0020ResBean中，返回
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			BCK0020ResBodyBean bck0020ResBodyBean = new BCK0020ResBodyBean();
			
			bck0020ResBodyBean.setSVR_DATE(outResBean.getBODY().getSVR_DATE());//核心日期
			bck0020ResBodyBean.setSVR_JRNL_NO(outResBean.getBODY().getSVR_JRNL_NO());//流水号
			bck0020ResBodyBean.setFILE_NAME(outResBean.getBODY().getFILE_NAME());//文件名称
			bck0020ResBodyBean.setBIRTHDAY_FLOAT(outResBean.getBODY().getBIRTHDAY_FLOAT());
			bck0020ResBodyBean.setCHANNEL_FLOAT(outResBean.getBODY().getCHANNEL_FLOAT());
			bck0020ResBodyBean.setCOMBINE_FLOAT(outResBean.getBODY().getCOMBINE_FLOAT());
			bck0020ResBodyBean.setFLOAT_SUM(outResBean.getBODY().getFLOAT_SUM());
			bck0020ResBodyBean.setINST_FLOAT(outResBean.getBODY().getINST_FLOAT());
			bck0020ResBodyBean.setTIME_FLOAT(outResBean.getBODY().getTIME_FLOAT());
			inResBean.setBody(bck0020ResBodyBean);
			InResHeadBean headBean = new InResHeadBean();
			headBean.setResCode(outResBean.getResponseBean().getRetCode());
			headBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(headBean);
			reqXs.alias("Root", BCK0020ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
	}

	private Out02864ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Out02864ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out02864ResBodyBean.class);
			
			return (Out02864ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out02864ReqBean();
			/* 写入数据 */
			//将BCK0020ReqBean里面的数据放入Out02864ReqBean中，并解析成String字符串
			Out02864ReqBodyBean out02864ReqBodyBean = new Out02864ReqBodyBean();
			out02864ReqBodyBean.setPROD_CODE(inReqBean.getBody().getPROD_CODE());
			out02864ReqBodyBean.setRATE_DATE(inReqBean.getBody().getRATE_DATE());
			out02864ReqBodyBean.setFLOAT_FLAG(inReqBean.getBody().getFLOAT_FLAG());
			out02864ReqBodyBean.setACCT_NO(inReqBean.getBody().getACCT_NO());
			out02864ReqBodyBean.setSUB_ACCT_NO(inReqBean.getBody().getSUB_ACCT_NO());
			out02864ReqBodyBean.setCUST_LEVEL(inReqBean.getBody().getCUST_LEVEL());
			out02864ReqBodyBean.setCHL_ID(inReqBean.getBody().getCHL_ID());
			out02864ReqBodyBean.setCUST_NO(inReqBean.getBody().getCUST_NO());
			outReqBean.setBODY(out02864ReqBodyBean);
			OutHeadBean outHeadBean = new OutHeadBean();
			outHeadBean.setTRAN_CODE("02864");
			outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));
			outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));
			outHeadBean.setCARD_ACCP_TERM_ID(inReqBean.getHeadBean().getMachineNo());//自助设备编号
			outHeadBean.setMER_NO(mac.getMerNo());//商户号
			outHeadBean.setINST_NO(inReqBean.getHeadBean().getBranchNo());//机构号
			outHeadBean.setTELLER_NO(mac.getTellerNo());// 柜员号
			outHeadBean.setCHANNEL(mac.getChannel());// 渠道号
			outReqBean.setHeadBean(outHeadBean);// 
			XStream xs = new XStream(new XppDriver(new XStreamNameCoder()));
			xs.autodetectAnnotations(true);
			
			xs.alias("ROOT", Out02864ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out02864ReqBodyBean.class);
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

	private BCK0020ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK0020ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK0020ReqBodyBean.class);
		
		return (BCK0020ReqBean)reqXs.fromXML(msg);
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
