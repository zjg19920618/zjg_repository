package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK07670ReqBean;
import com.boomhope.tms.message.in.bck.BCK07670ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07670ResBean;
import com.boomhope.tms.message.in.bck.BCK07670ResBodyBean;
import com.boomhope.tms.message.out.Out07670ReqBean;
import com.boomhope.tms.message.out.Out07670ReqBodyBean;
import com.boomhope.tms.message.out.Out07670ResBean;
import com.boomhope.tms.message.out.Out07670ResBodyBean;
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

public class TransBCK07670 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK07670.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK07670ReqBean inReqBean;	// 渠道请求Bean
	BCK07670ResBean inResBean;	// 渠道响应Bean
	Out07670ReqBean	outReqBean;	// 前置请求Bean
	Out07670ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	TransFlow transFlow;	// 交易流水Bean
	
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK07670.getBean("TransFlowService");
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
			/* 记录流水信息 */
			addTransFlow();
			
			//生成请求前置报文
			byte[] outReqMsg = makeOutReqMsg();
			
			/* 发送前置交易请求 */
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			
			/* 解析前置响应报文 */
			outResBean = makeOutResBean(outResMsg);
			
			/*更新流水信息 */
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

	private String makeResErrorMsg(String msg,String service) {
		if(null == service){
			//修改入库失败操作
			transFlow.setTransStatus("E");
			transFlow.setTransStatusDesc("交易失败");
			transFlowService.updateTransFlow(transFlow);
		}
				
		inResBean = new BCK07670ResBean();
		BCK07670ResBodyBean bck07670ResBodyBean = new BCK07670ResBodyBean();
		inResBean.setBody(bck07670ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07670ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK07670ResBean();
			//然后将Out07670ResBean类中的数据放入BCK07670ResBean中，返回
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			BCK07670ResBodyBean bck07670ResBodyBean = new BCK07670ResBodyBean();
			bck07670ResBodyBean.setCORE_RET_CODE(outResBean.getBODY().getCORE_RET_CODE());
			bck07670ResBodyBean.setCORE_RET_MSG(outResBean.getBODY().getCORE_RET_MSG());
			bck07670ResBodyBean.setISSUE_AUTH(outResBean.getBODY().getISSUE_AUTH());
			bck07670ResBodyBean.setFILE_NAME(outResBean.getBODY().getFILE_NAME());
			
			inResBean.setBody(bck07670ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			reqXs.alias("Root", BCK07670ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}

	private Out07670ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Out07670ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out07670ResBodyBean.class);
			return (Out07670ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	/***
	 * 新增交易流水
	 * @throws Exception 
	 */
	private void addTransFlow() throws Exception{
		try {
			transFlow = new TransFlow();
			transFlow.setTransCode("BCK_07670");
			transFlow.setTransName("回单机-身份核查");
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
	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out07670ReqBean();
			/* 写入数据 */
			//将BCK07670ReqBean里面的数据放入Out023448ReqBean中，并解析成String字符串
			
			Out07670ReqBodyBean outBody = new Out07670ReqBodyBean();
			outBody.setID(inReqBean.getBody().getID());//证件号码
			outBody.setNAME(inReqBean.getBody().getNAME());//姓名
			OutHeadBean outHeadBean = new OutHeadBean();
			outHeadBean.setTRAN_CODE("07670");//前置交易码
			outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
			outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));//交易时间
			outHeadBean.setCARD_ACCP_TERM_ID(inReqBean.getHeadBean().getMachineNo());//自助设备编号
			outHeadBean.setMER_NO(mac.getMerNo());//商户号
			outHeadBean.setINST_NO(inReqBean.getHeadBean().getBranchNo());//机构号
			outHeadBean.setTELLER_NO(mac.getTellerNo());// 柜员号
			outHeadBean.setCHANNEL(mac.getChannel());// 渠道号
			
			outReqBean.setBODY(outBody);
			outReqBean.setHeadBean(outHeadBean);
			XStream xs = new XStream(new XppDriver(new XStreamNameCoder()));
			xs.autodetectAnnotations(true);

			xs.alias("ROOT", Out07670ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out07670ReqBodyBean.class);
			String xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xs.toXML(outReqBean);
			logger.info("发送前置报文：\n"+xml);
			byte[] bbb = xml.getBytes("GBK");
			return MACProtocolUtils.toRequest(mac.getMackeyflag(), bbb);
		} catch (Exception e) {
			logger.error("生成前置请求报文异常",e);
			throw new Exception("生成前置请求报文异常", e);
		}
		
	}

	private BCK07670ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK07670ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK07670ReqBodyBean.class);
		
		return (BCK07670ReqBean)reqXs.fromXML(msg);
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
