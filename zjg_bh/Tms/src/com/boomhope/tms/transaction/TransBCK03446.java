package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK03446ReqBean;
import com.boomhope.tms.message.in.bck.BCK03446ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03446ResBean;
import com.boomhope.tms.message.in.bck.BCK03446ResBodyBean;
import com.boomhope.tms.message.out.Out03446ReqBean;
import com.boomhope.tms.message.out.Out03446ReqBodyBean;
import com.boomhope.tms.message.out.Out03446ResBean;
import com.boomhope.tms.message.out.Out03446ResBodyBean;
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

public class TransBCK03446 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK03446.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK03446ReqBean inReqBean;	// 渠道请求Bean
	BCK03446ResBean inResBean;	// 渠道响应Bean
	Out03446ReqBean	outReqBean;	// 前置请求Bean
	Out03446ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	TransFlow transFlow;	// 交易流水Bean
	
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK03446.getBean("TransFlowService");
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
			
			/* 更新流水信息 */
			updateTransFlow();
			
			//返回信息
			return makeResMsg();
		} catch (Exception e) {
			return makeResErrorMsg(e.getMessage(),null);
		}
	}
	private void updateTransFlow() throws Exception{
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
			transFlow.setTransCode("BCK_03446");
			transFlow.setTransName("回单机-个人客户建立");
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
				
		inResBean = new BCK03446ResBean();
		BCK03446ResBodyBean bck03446ResBodyBean = new BCK03446ResBodyBean();
		
		inResBean.setBody(bck03446ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03446ResBean.class);
		return reqXs.toXML(inResBean);
	}
	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK03446ResBean();
			//然后将Out03446ResBean类中的数据放入BCK03446ResBean中，返回
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			BCK03446ResBodyBean bck03446ResBodyBean = new BCK03446ResBodyBean();
			bck03446ResBodyBean.setCUST_NO(outResBean.getBODY().getCUST_NO());
			bck03446ResBodyBean.setSVR_DATE(outResBean.getBODY().getSVR_DATE());
			bck03446ResBodyBean.setSVR_JRNL_NO(outResBean.getBODY().getSVR_JRNL_NO());
			inResBean.setBody(bck03446ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			reqXs.alias("Root", BCK03446ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out03446ReqBean();
			/* 写入数据 */
			//将BCK03446ReqBean里面的数据放入Out03446ReqBean中，并解析成String字符串
			Out03446ReqBodyBean outBody = new Out03446ReqBodyBean();
			outBody.setBANK_PART(inReqBean.getBody().getBANK_PART());//
			outBody.setBANK_SER_LEV(inReqBean.getBody().getBANK_SER_LEV());//
			outBody.setBANK_STAFF(inReqBean.getBody().getBANK_STAFF());//
			outBody.setBIRTH_DATE(inReqBean.getBody().getBIRTH_DATE());//
			outBody.setBIRTH_RES(inReqBean.getBody().getBIRTH_RES());//
			outBody.setBUS_STATUS(inReqBean.getBody().getBUS_STATUS());//
			outBody.setCOUNTRY(inReqBean.getBody().getCOUNTRY());//
			outBody.setCUST_NAME(inReqBean.getBody().getCUST_NAME());//
			outBody.setCUST_SEX(inReqBean.getBody().getCUST_SEX());//
			outBody.setCUST_SHORT(inReqBean.getBody().getCUST_SHORT());//
			outBody.setCUST_TYPE(inReqBean.getBody().getCUST_TYPE());//
			outBody.setCUSTOM_MANAGER_NO(inReqBean.getBody().getCUSTOM_MANAGER_NO());//
			outBody.setDOMICILE_PLACE(inReqBean.getBody().getDOMICILE_PLACE());//
			outBody.setDUE_DATE(inReqBean.getBody().getDUE_DATE());//
			outBody.setEDU_GRADE(inReqBean.getBody().getEDU_GRADE());//
			outBody.setEN_NAME_1(inReqBean.getBody().getEN_NAME_1());//
			outBody.setEN_NAME_2(inReqBean.getBody().getEN_NAME_2());//
			outBody.setHEA_STATUS(inReqBean.getBody().getHEA_STATUS());//
			outBody.setID_NO(inReqBean.getBody().getID_NO());//
			outBody.setID_TYPE(inReqBean.getBody().getID_TYPE());//
			outBody.setISSUE_DATE(inReqBean.getBody().getISSUE_DATE());//
			outBody.setISSUE_INST(inReqBean.getBody().getISSUE_INST());//
			outBody.setJ_C_ADD(inReqBean.getBody().getJ_C_ADD());//
			outBody.setMAR_STATUS(inReqBean.getBody().getMAR_STATUS());//
			outBody.setNATION(inReqBean.getBody().getNATION());//
			outBody.setTEL_NO(inReqBean.getBody().getTEL_NO());//
			OutHeadBean outHeadBean = new OutHeadBean();
			outHeadBean.setTRAN_CODE("03446");//前置交易码
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
			xs.alias("ROOT", Out03446ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out03446ReqBodyBean.class);
			String xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xs.toXML(outReqBean);
			logger.info("发送前置报文：\n"+xml);
			byte[] bbb = xml.getBytes("GBK");
			return MACProtocolUtils.toRequest(mac.getMackeyflag(), bbb);
		} catch (Exception e) {
			logger.error("生成前置请求报文异常",e);
			throw new Exception("生成前置请求报文异常", e);
		}
		
	}

	private Out03446ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8"));
			
			reqXs.alias("ROOT", Out03446ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out03446ResBodyBean.class);
			
			return (Out03446ResBean)reqXs.fromXML(outResMsg);
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
	private BCK03446ReqBean makeInReqBean(String inReqMsg)throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8"));
		
		reqXs.alias("Root", BCK03446ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK03446ReqBodyBean.class);
		
		return (BCK03446ReqBean)reqXs.fromXML(inReqMsg);
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
