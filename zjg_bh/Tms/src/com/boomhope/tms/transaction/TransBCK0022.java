package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.BaseUnit;
import com.boomhope.tms.entity.BusFlow;
import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK0022ReqBean;
import com.boomhope.tms.message.in.bck.BCK0022ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0022ResBean;
import com.boomhope.tms.message.in.bck.BCK0022ResBodyBean;
import com.boomhope.tms.message.out.Out03522ReqAgentInfBean;
import com.boomhope.tms.message.out.Out03522ReqBean;
import com.boomhope.tms.message.out.Out03522ReqBodyBean;
import com.boomhope.tms.message.out.Out03522ResBean;
import com.boomhope.tms.message.out.Out03522ResBodyBean;
import com.boomhope.tms.message.out.OutHeadBean;
import com.boomhope.tms.message.out.OutResponseBean;
import com.boomhope.tms.service.IDeployMachineService;
import com.boomhope.tms.service.IFlowService;
import com.boomhope.tms.service.ITransFlowService;
import com.boomhope.tms.socket.SocketClient;
import com.boomhope.tms.util.DateUtil;
import com.boomhope.tms.util.EncryptUtils;
import com.boomhope.tms.util.MACProtocolUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
/**
 * 
 * @title 电子账户 子账户销户【35104】-前置03522
 *
 */
public class TransBCK0022 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK0022.class);
	ITransFlowService transFlowService;	// 交易流水服务

	IFlowService flowService;   //业务流水服务

	IDeployMachineService deployMachineService = null;

	BCK0022ReqBean inReqBean;	// 渠道请求Bean
	BCK0022ResBean inResBean;	// 渠道响应Bean
	Out03522ReqBean	outReqBean;	// 前置请求Bean
	Out03522ResBean	outResBean;	// 前置响应Bean

	BusFlow busFlow;        //业务流水Bean

	

	DeployMachine mac = null;
	TransFlow transFlow;	// 交易流水Bean
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK0022.getBean("TransFlowService");
			deployMachineService = (IDeployMachineService) TransTms0001.getBean("deployMachineService");
			flowService = (IFlowService) TransBCK0021.getBean("flowService");
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
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			logger.info("向前置发送03522请求"+outReqMsg);
			//解析前置响应报文 
			outResBean = makeOutResBean(outResMsg);
			logger.info("接收置响应03522请求"+outReqMsg);
			
			/* 更新流水信息 */
			updateTransFlow();

			String resMsg = makeResMsg();
			String resCode = inResBean.getHeadBean().getResCode();
			if("000000".equals(resCode)){
				/*记录业务流水信息 */
				addBusFlow();
			}
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
			transFlow.setTransCode("BCK_0022");
			transFlow.setTransName("回单机-电子账户 子账户销户");
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
	//增加业务流水信息
	private void addBusFlow() throws Exception {
		try {
			busFlow = new BusFlow();
			DeployMachine deployMachine = new DeployMachine();
			deployMachine.setMachineNo(mac.getMachineNo().trim());//终端编号
			busFlow.setDeployMachine(deployMachine);
			BaseUnit baseUnit = new BaseUnit();
			baseUnit.setUnitCode(mac.getBaseUnit().getUnitCode().trim());//机构代码
			busFlow.setBaseUnit(baseUnit);
			busFlow.setProjectName(inReqBean.getBody().getPRONAME().trim());//产品名称
			busFlow.setProjectCode(inReqBean.getBody().getPROCODE().trim());//产品编号
			busFlow.setBillNo(inReqBean.getBody().getACCT_NO().trim());//存单账号
			busFlow.setCertNo(inReqBean.getBody().getCERT_NO().trim());//凭证号
			busFlow.setRealPri(outResBean.getBODY().getPAID_PRINCIPAL().trim());//实付本金
			busFlow.setRealInte(outResBean.getBODY().getPAID_INTEREST().trim());//实付利息
			busFlow.setCanelType(inReqBean.getBody().getCANEL_TYPE().trim());//销户类型
			busFlow.setBankCardNo(inReqBean.getBody().getCARDNO().trim());//转入卡号
			busFlow.setBankCardName(inReqBean.getBody().getCARDNAME().trim());//转入卡姓名
			busFlow.setCreateDate(DateUtil.getDateTime("yyyyMMddHHmmss"));//创建时间
			busFlow.setCheckStatus(inReqBean.getBody().getCHECKSTATUS().trim());//标记存单是否自动识别
			
			flowService.addBusFlow(busFlow);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception("接收业务请求入库失败",e);
		}
	}	
	private String makeResErrorMsg(String msg,String service) {
		if(null == service){
			//修改入库失败操作
			transFlow.setTransStatus("E");
			transFlow.setTransStatusDesc("交易失败");
			transFlowService.updateTransFlow(transFlow);
		}
				
		inResBean = new BCK0022ResBean();
		BCK0022ResBodyBean bck0022ResBodyBean = new BCK0022ResBodyBean();
		
		inResBean.setBody(bck0022ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK0022ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK0022ResBean();
			BCK0022ResBodyBean bck0022ResBodyBean = new BCK0022ResBodyBean();
			bck0022ResBodyBean.setSVR_DATE(outResBean.getBODY().getSVR_DATE());
			bck0022ResBodyBean.setCARD_DATE(outResBean.getBODY().getCARD_DATE());
			bck0022ResBodyBean.setCARD_JRNL_NO(outResBean.getBODY().getCARD_JRNL_NO());
			bck0022ResBodyBean.setSVR_JRNL_NO(outResBean.getBODY().getSVR_JRNL_NO());
			bck0022ResBodyBean.setRATE(outResBean.getBODY().getRATE());
			bck0022ResBodyBean.setRED_INTEREST(outResBean.getBODY().getRED_INTEREST());
			bck0022ResBodyBean.setTAX_RATE(outResBean.getBODY().getTAX_RATE());
			bck0022ResBodyBean.setINTEREST_TAX(outResBean.getBODY().getINTEREST_TAX());
			bck0022ResBodyBean.setINVEST_INCOME(outResBean.getBODY().getINVEST_INCOME());
			bck0022ResBodyBean.setPAID_PRINCIPAL(outResBean.getBODY().getPAID_PRINCIPAL());
			bck0022ResBodyBean.setPAID_INTEREST(outResBean.getBODY().getPAID_INTEREST());
			bck0022ResBodyBean.setPAYOFF_INTEREST(outResBean.getBODY().getPAYOFF_INTEREST());
			bck0022ResBodyBean.setADVN_INIT(outResBean.getBODY().getADVN_INIT());
			bck0022ResBodyBean.setXFD_COUNT(outResBean.getBODY().getXFD_COUNT());//扣回消费豆数量
			bck0022ResBodyBean.setXFD_AMT(outResBean.getBODY().getXFD_AMT());//扣回消费豆金额
			inResBean.setBody(bck0022ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK0022ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}

	private Out03522ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Out03522ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out03522ResBodyBean.class);
		
			return (Out03522ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out03522ReqBean();
			Out03522ReqBodyBean outBody = new Out03522ReqBodyBean();
			outBody.setTRAN_CHANNEL(inReqBean.getBody().getTRAN_CHANNEL());//渠道号
			outBody.setCARD_NO(inReqBean.getBody().getCARD_NO());//卡号
			outBody.setSUB_ACCT_NO(inReqBean.getBody().getSUB_ACCT_NO());//子账号
			outBody.setCAL_MODE(inReqBean.getBody().getCAL_MODE());//结算方式
			outBody.setCASH_ANALY_NO(inReqBean.getBody().getCASH_ANALY_NO());//现金分析号
			outBody.setOPPO_ACCT_NO(inReqBean.getBody().getOPPO_ACCT_NO());//对方账号
			outBody.setID_TYPE(inReqBean.getBody().getID_TYPE());//证件类型
			outBody.setID_NO(inReqBean.getBody().getID_NO());//证件号码
			outBody.setHAV_AGENT_FLAG(inReqBean.getBody().getHAV_AGENT_FLAG());//代理人标志
			outBody.setPIN_VAL_FLAG(inReqBean.getBody().getPIN_VAL_FLAG());//验密标志
			outBody.setCERT_NO(inReqBean.getBody().getCERT_NO());//凭证号
			
			
			if("0".equals(inReqBean.getBody().getHAV_AGENT_FLAG())){//存在代理人信息
				Out03522ReqAgentInfBean outAgentInf = new Out03522ReqAgentInfBean();
				outAgentInf.setCUST_NAME(inReqBean.getBody().getAGENT_INF().getCUST_NAME());
				outAgentInf.setSEX(inReqBean.getBody().getAGENT_INF().getSEX());
				outAgentInf.setID_TYPE(inReqBean.getBody().getAGENT_INF().getID_TYPE());
				outAgentInf.setID_NO(inReqBean.getBody().getAGENT_INF().getID_NO());
				outAgentInf.setISSUE_DATE(inReqBean.getBody().getAGENT_INF().getISSUE_DATE());
				outAgentInf.setDUE_DATE(inReqBean.getBody().getAGENT_INF().getDUE_DATE());
				outAgentInf.setISSUE_INST(inReqBean.getBody().getAGENT_INF().getISSUE_INST());
				outAgentInf.setNATION(inReqBean.getBody().getAGENT_INF().getNATION());
				outAgentInf.setOCCUPATION(inReqBean.getBody().getAGENT_INF().getOCCUPATION());
				outAgentInf.setREGIS_PER_RES(inReqBean.getBody().getAGENT_INF().getREGIS_PER_RES());
				outAgentInf.setJ_C_ADD(inReqBean.getBody().getAGENT_INF().getJ_C_ADD());
				outAgentInf.setTELEPHONE(inReqBean.getBody().getAGENT_INF().getTELEPHONE());
				outAgentInf.setMOB_PHONE(inReqBean.getBody().getAGENT_INF().getMOB_PHONE());
				
				outBody.setAGENT_INF(outAgentInf);//代理人信息
			}
			
			if(!("N").equals(inReqBean.getBody().getPIN_VAL_FLAG())){
				/*凭证密码加密处理*/
				String pwd = inReqBean.getBody().getPASSWORD();//获取终端密文密码
//				logger.debug("*******终端传送密文："+pwd);
				String acctNo = inReqBean.getBody().getCARD_NO();//获取存单账号
//				logger.debug("*******存单账号："+acctNo);
				logger.info("加密前：   "+pwd);
				String tranPwd=EncryptUtils.tranPin310(mac.getWorkkeyflag(), mac.getKeyflag(), acctNo, pwd);
				logger.info("加密后：   "+tranPwd);
				logger.debug("*******转加密密文："+tranPwd);
				outBody.setPASSWORD(tranPwd);//有凭证密码
			}else{
				outBody.setPASSWORD(inReqBean.getBody().getPASSWORD());//无凭证密码
			}
			
			//拼接输出的head
			OutHeadBean outHeadBean = new OutHeadBean();
			outHeadBean.setTRAN_CODE("03522");//前置交易码
			outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
			outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));//交易时间
			outHeadBean.setCARD_ACCP_TERM_ID(inReqBean.getHeadBean().getMachineNo());//自助设备编号
			outHeadBean.setMER_NO(mac.getMerNo());//商户号
			outHeadBean.setINST_NO(inReqBean.getHeadBean().getBranchNo());//机构号
			outHeadBean.setTELLER_NO(mac.getTellerNo());// 柜员号
			outHeadBean.setCHANNEL(mac.getChannel());// 渠道号
			outReqBean.setBODY(outBody);
			outReqBean.setHeadBean(outHeadBean);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("ROOT", Out03522ReqBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("BODY", Out03522ReqBodyBean.class);
			
			String xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+reqXs.toXML(outReqBean);
			logger.info("发送前置报文：\n"+xml);
			byte[] bbb = xml.getBytes("GBK");
			return MACProtocolUtils.toRequest(mac.getMackeyflag(), bbb);
		} catch (Exception e) {
			logger.error("生成前置请求报文异常",e);
			throw new Exception("生成前置请求报文异常", e);
		}
		
	}

	private BCK0022ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK0022ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK0022ReqBodyBean.class);
		
		return (BCK0022ReqBean)reqXs.fromXML(msg);
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
