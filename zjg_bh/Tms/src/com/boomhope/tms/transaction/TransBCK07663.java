package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK07663ReqBean;
import com.boomhope.tms.message.in.bck.BCK07663ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07663ResBean;
import com.boomhope.tms.message.in.bck.BCK07663ResBodyBean;
import com.boomhope.tms.message.out.Out07663ReqAgentInfBean;
import com.boomhope.tms.message.out.Out07663ReqBean;
import com.boomhope.tms.message.out.Out07663ReqBodyBean;
import com.boomhope.tms.message.out.Out07663ResBean;
import com.boomhope.tms.message.out.Out07663ResBodyBean;
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

public class TransBCK07663 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK07663.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK07663ReqBean inReqBean;	// 渠道请求Bean
	BCK07663ResBean inResBean;	// 渠道响应Bean
	Out07663ReqBean	outReqBean;	// 前置请求Bean
	Out07663ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	TransFlow transFlow;	// 交易流水Bean
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK07663.getBean("TransFlowService");
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
			logger.info("向前置发送07663请求"+outReqMsg);
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			logger.info("接刷前置响应07663请求"+outReqMsg);
			
			//解析前置响应报文 
			outResBean = makeOutResBean(outResMsg);
			
			/* 更新流水信息 */
			updateTransFlow();
			
			//然后将Account07663ResBean类中的数据放入BCK07663ResBean中，返回
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
			transFlow.setTransCode("BCK_07663");
			transFlow.setTransName("回单机-唐豆兑付");
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
		
		inResBean = new BCK07663ResBean();
		BCK07663ResBodyBean BCK07663ResBodyBean = new BCK07663ResBodyBean();
		
		inResBean.setBody(BCK07663ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07663ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK07663ResBean();
			BCK07663ResBodyBean BCK07663ResBodyBean = new BCK07663ResBodyBean();
			BCK07663ResBodyBean.setAMT_ACCT_NO(outResBean.getBODY().getAMT_ACCT_NO());
			BCK07663ResBodyBean.setCUST_NAME(outResBean.getBODY().getCUST_NAME());
			BCK07663ResBodyBean.setCUST_NO(outResBean.getBODY().getCUST_NO());
			BCK07663ResBodyBean.setINT_FROM_ACCT(outResBean.getBODY().getINT_FROM_ACCT());
			BCK07663ResBodyBean.setPUT_INT_ACCT(outResBean.getBODY().getPUT_INT_ACCT());
			BCK07663ResBodyBean.setR_COUNT(outResBean.getBODY().getR_COUNT());
			BCK07663ResBodyBean.setR_EXCHANGE_AMT(outResBean.getBODY().getR_EXCHANGE_AMT());
			BCK07663ResBodyBean.setR_PRODUCT_CODE(outResBean.getBODY().getR_PRODUCT_CODE());
			BCK07663ResBodyBean.setSVR_DATE(outResBean.getBODY().getSVR_DATE());
			BCK07663ResBodyBean.setSVR_JRNL_NO(outResBean.getBODY().getSVR_JRNL_NO());
			inResBean.setBody(BCK07663ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK07663ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}

	private Out07663ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Out07663ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out07663ResBodyBean.class);
			
			return (Out07663ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out07663ReqBean();
			Out07663ReqBodyBean accountBody = new Out07663ReqBodyBean();
			accountBody.setPRODUCT_CODE(inReqBean.getBody().getPRODUCT_CODE());//产品代码
			accountBody.setDEP_TERM(inReqBean.getBody().getDEP_TERM());//存期
			accountBody.setACCEPT_DATE(inReqBean.getBody().getACCEPT_DATE());//兑付日
			accountBody.setTOTAL_BAL(inReqBean.getBody().getTOTAL_BAL());//账户总余额
			accountBody.setTERM_CODE(inReqBean.getBody().getTERM_CODE());//唐豆期次代码
			accountBody.setCOUNT(inReqBean.getBody().getCOUNT());//唐豆数量
			accountBody.setEXCHANGE_MODE(inReqBean.getBody().getEXCHANGE_MODE());//唐豆兑换方式
			accountBody.setEXCHANGE_PROP(inReqBean.getBody().getEXCHANGE_PROP());//唐豆兑现比例
			accountBody.setEXCHANGE_AMT(inReqBean.getBody().getEXCHANGE_AMT());//兑现金额
			accountBody.setCUSTOM_MANAGER_NO(inReqBean.getBody().getCUSTOM_MANAGER_NO());//客户经理号
			accountBody.setCUSTOM_MANAGER_NAME(inReqBean.getBody().getCUSTOM_MANAGER_NAME());//客户经理名称
			accountBody.setOPPO_ACCT_NO(inReqBean.getBody().getOPPO_ACCT_NO());//对方账号
			accountBody.setACT_CHNL(inReqBean.getBody().getACT_CHNL());//活动渠道
			accountBody.setDEAL_TYPE(inReqBean.getBody().getDEAL_TYPE());//历史唐豆处理标志
			accountBody.setAMT_CHL_FLAG(inReqBean.getBody().getAMT_CHL_FLAG());//
			
			Out07663ReqAgentInfBean account07663AcctInfo = new Out07663ReqAgentInfBean();
			account07663AcctInfo.setACCT_BAL(inReqBean.getBody().getACCT_INFO().getACCT_BAL());
			account07663AcctInfo.setACCT_NO(inReqBean.getBody().getACCT_INFO().getACCT_NO());
			account07663AcctInfo.setSUB_ACCT_NO(inReqBean.getBody().getACCT_INFO().getSUB_ACCT_NO());
			accountBody.setACCT_INFO(account07663AcctInfo);
			outReqBean.setBODY(accountBody);
			OutHeadBean headBean = new OutHeadBean();
			headBean.setTRAN_CODE("07663");
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
			
			xs.alias("ROOT", Out07663ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out07663ReqBodyBean.class);
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

	private BCK07663ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK07663ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK07663ReqBodyBean.class);
		
		return (BCK07663ReqBean)reqXs.fromXML(msg);
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
