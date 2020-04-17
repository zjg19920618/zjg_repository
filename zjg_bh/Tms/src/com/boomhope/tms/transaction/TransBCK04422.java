package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK04422ReqBean;
import com.boomhope.tms.message.in.bck.BCK04422ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK04422ResBean;
import com.boomhope.tms.message.in.bck.BCK04422ResBodyBean;
import com.boomhope.tms.message.out.Out04422ReqBean;
import com.boomhope.tms.message.out.Out04422ReqBodyBean;
import com.boomhope.tms.message.out.Out04422ResBean;
import com.boomhope.tms.message.out.Out04422ResBodyBean;
import com.boomhope.tms.message.out.OutHeadBean;
import com.boomhope.tms.message.out.OutResponseBean;
import com.boomhope.tms.service.IDeployMachineService;
import com.boomhope.tms.service.ISequenceManageService;
import com.boomhope.tms.service.ITransFlowService;
import com.boomhope.tms.socket.SocketClient;
import com.boomhope.tms.util.DateUtil;
import com.boomhope.tms.util.MACProtocolUtils;
import com.boomhope.tms.util.SequenceUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class TransBCK04422 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK04422.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	ISequenceManageService sequenceManageService=null;
	BCK04422ReqBean inReqBean;	// 渠道请求Bean
	BCK04422ResBean inResBean;	// 渠道响应Bean
	Out04422ReqBean	outReqBean;	// 前置请求Bean
	Out04422ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	TransFlow transFlow;	// 交易流水Bean
	static int i=1;
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK04422.getBean("TransFlowService");
			deployMachineService = (IDeployMachineService) TransTms0001.getBean("deployMachineService");
			sequenceManageService=(ISequenceManageService)TransTms0001.getBean("SequenceManageImpl");
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
			logger.info("向前置发送04422请求"+outReqMsg);
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			logger.info("接刷前置响应04422请求"+outReqMsg);
			
			//解析前置响应报文 
			outResBean = makeOutResBean(outResMsg);
			
			/* 更新流水信息 */
			updateTransFlow();
			
			//然后将Out04422ResBean类中的数据放入BCK04422ResBean中，返回
			//返回信息
			return makeResMsg();
		} catch (Exception e) {
			return makeResErrorMsg(e.getMessage(),null);
		}
		
	}
	/**
	 * 
	 * 更新流水信息
	 */
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
			transFlow.setTransCode("BCK_04422");
			transFlow.setTransName("回单机-客户基本信息查询");
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
		
		inResBean = new BCK04422ResBean();
		BCK04422ResBodyBean BCK04422ResBodyBean = new BCK04422ResBodyBean();
		
		inResBean.setBody(BCK04422ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK04422ResBean.class);
		return reqXs.toXML(inResBean);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK04422ResBean();
			BCK04422ResBodyBean BCK04422ResBodyBean = new BCK04422ResBodyBean();
			BCK04422ResBodyBean.setADDR_LINE((outResBean.getBODY().getADDR_LINE()));
			BCK04422ResBodyBean.setBIRTH_DATE(outResBean.getBODY().getBIRTH_DATE());
			BCK04422ResBodyBean.setCERT_EXPD_DATE(outResBean.getBODY().getCERT_EXPD_DATE());
			BCK04422ResBodyBean.setCERT_NO(outResBean.getBODY().getCERT_NO());
			BCK04422ResBodyBean.setCERT_TYPE(outResBean.getBODY().getCERT_TYPE());
			BCK04422ResBodyBean.setCERT_VALID_DATE(outResBean.getBODY().getCERT_VALID_DATE());
			BCK04422ResBodyBean.setCERTNO_LEGAL_FALG(outResBean.getBODY().getCERTNO_LEGAL_FALG());
			BCK04422ResBodyBean.setCHN_NAME(outResBean.getBODY().getCHN_NAME());
			BCK04422ResBodyBean.setCOUNTRY_CODE(outResBean.getBODY().getCOUNTRY_CODE());
			BCK04422ResBodyBean.setCUST_LEVEL(outResBean.getBODY().getCUST_LEVEL());
			BCK04422ResBodyBean.setECIF_CUST_NO(outResBean.getBODY().getECIF_CUST_NO());
			BCK04422ResBodyBean.setFUNDS_PROVIDED(outResBean.getBODY().getFUNDS_PROVIDED());
			BCK04422ResBodyBean.setGENDER(outResBean.getBODY().getGENDER());
			BCK04422ResBodyBean.setINFO_QUALITY_FLAG(outResBean.getBODY().getINFO_QUALITY_FLAG());
			BCK04422ResBodyBean.setIS_EXT_MSG(outResBean.getBODY().getIS_EXT_MSG());
			BCK04422ResBodyBean.setIS_LOCBANKEMP(outResBean.getBODY().getIS_LOCBANKEMP());
			BCK04422ResBodyBean.setIS_NARCOTICS(outResBean.getBODY().getIS_NARCOTICS());
			BCK04422ResBodyBean.setISSUED_INS(outResBean.getBODY().getISSUED_INS());
			BCK04422ResBodyBean.setJOB_PRFSSN(outResBean.getBODY().getJOB_PRFSSN());
			BCK04422ResBodyBean.setJOB_PRFSSN_DES(outResBean.getBODY().getJOB_PRFSSN_DES());
			BCK04422ResBodyBean.setLOC_BANKS_HOLDER_FLAG(outResBean.getBODY().getLOC_BANKS_HOLDER_FLAG());
			BCK04422ResBodyBean.setMAIN_ECON_RESUR_DES(outResBean.getBODY().getMAIN_ECON_RESUR_DES());
			BCK04422ResBodyBean.setMOBILE_PHONE(outResBean.getBODY().getMOBILE_PHONE());
			BCK04422ResBodyBean.setMONTH_INCOME(outResBean.getBODY().getMONTH_INCOME());
			BCK04422ResBodyBean.setNATION_CODE(outResBean.getBODY().getNATION_CODE());
			BCK04422ResBodyBean.setNEGATIVE_NEWS(outResBean.getBODY().getNEGATIVE_NEWS());
			BCK04422ResBodyBean.setNINE_ITEM_COMPLETED_FLAG(outResBean.getBODY().getNINE_ITEM_COMPLETED_FLAG());
			BCK04422ResBodyBean.setOPEN_PURPOSE(outResBean.getBODY().getOPEN_PURPOSE());
			BCK04422ResBodyBean.setPARTY_NAME(outResBean.getBODY().getPARTY_NAME());
			BCK04422ResBodyBean.setPHONE_NO(outResBean.getBODY().getPHONE_NO());
			BCK04422ResBodyBean.setPRODUCTS_SERVICES(outResBean.getBODY().getPRODUCTS_SERVICES());
			BCK04422ResBodyBean.setREC_PRODUCT(outResBean.getBODY().getREC_PRODUCT());
			BCK04422ResBodyBean.setRELATION_COUNTRY(outResBean.getBODY().getRELATION_COUNTRY());
			BCK04422ResBodyBean.setSIMILAR_INFO_FLAG(outResBean.getBODY().getSIMILAR_INFO_FLAG());
			BCK04422ResBodyBean.setSOCIAL_CONTEXT(outResBean.getBODY().getSOCIAL_CONTEXT());
			BCK04422ResBodyBean.setTRANSACTION_SCALE(outResBean.getBODY().getTRANSACTION_SCALE());
			BCK04422ResBodyBean.setVISA_EXPD_DATE(outResBean.getBODY().getVISA_EXPD_DATE());
			BCK04422ResBodyBean.setVISA_TYPE(outResBean.getBODY().getVISA_TYPE());
			inResBean.setBody(BCK04422ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK04422ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}
	/**
	 * 解析前置响应报文 
	 * @param outResMsg
	 * @return
	 * @throws Exception
	 */
	private Out04422ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Out04422ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out04422ResBodyBean.class);
			
			return (Out04422ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}
	/**
	 * 生成前置请求报文 
	 * @return
	 * @throws Exception
	 */
	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out04422ReqBean();
			Out04422ReqBodyBean accountBody = new Out04422ReqBodyBean();
			accountBody.setACCT_NO(inReqBean.getBody().getACCT_NO());//客户识别账号
			accountBody.setCERT_NO(inReqBean.getBody().getCERT_NO());//证件号码
			accountBody.setCERT_TYPE(inReqBean.getBody().getCERT_TYPE());//证件类型
			accountBody.setECIF_CUST_NO(inReqBean.getBody().getECIF_CUST_NO());//客户编号
			accountBody.setPARTY_NAME(inReqBean.getBody().getPARTY_NAME());//客户姓名
			accountBody.setRESOLVE_TYPE(inReqBean.getBody().getRESOLVE_TYPE());//识别方式
			
			
			outReqBean.setBODY(accountBody);
			OutHeadBean headBean = new OutHeadBean();
			headBean.setTRAN_CODE("04422");
			headBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));
			headBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));
			headBean.setCARD_ACCP_TERM_ID(inReqBean.getHeadBean().getMachineNo());//自助设备编号
			headBean.setMER_NO(mac.getMerNo());//商户号
			headBean.setINST_NO(inReqBean.getHeadBean().getBranchNo());//机构号
			headBean.setTELLER_NO(mac.getTellerNo());// 柜员号
			headBean.setCHANNEL(mac.getChannel());// 渠道号
//			headBean.setTERM_JRNL_NO(DateUtil.getDateTime("yyyyMMdd")+SequenceUtil.getSequencId());//报文流水号
			headBean.setTERM_JRNL_NO(DateUtil.getDateTime("yyyyMMdd")+sequenceManageService.getSequenceNom("task_seq", 5));//报文流水号
			outReqBean.setHeadBean(headBean);
			//转换xml
			XStream xs = new XStream(new XppDriver(new XStreamNameCoder()));
			xs.autodetectAnnotations(true);
			
			xs.alias("ROOT", Out04422ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out04422ReqBodyBean.class);
			String xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xs.toXML(outReqBean);
			logger.info("发送前置报文：\n"+xml);
			byte[] bbb = xml.getBytes("GBK");
			return MACProtocolUtils.toRequest(mac.getMackeyflag(), bbb);
		} catch (Exception e) {
			logger.error("生成前置请求报文异常",e);
			throw new Exception("生成前置请求报文异常", e);
		}
		
	}

	private BCK04422ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK04422ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK04422ReqBodyBean.class);
		
		return (BCK04422ReqBean)reqXs.fromXML(msg);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml"});
		ISequenceManageService sequenceManageService=sequenceManageService=(ISequenceManageService)context.getBean("SequenceManageImpl");
		try {
			System.out.println(sequenceManageService.getSequenceNom("task_seq", 5));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
