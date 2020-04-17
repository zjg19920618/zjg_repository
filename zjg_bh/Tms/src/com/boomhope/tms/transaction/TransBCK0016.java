package com.boomhope.tms.transaction;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK0016ReqBean;
import com.boomhope.tms.message.in.bck.BCK0016ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0016ResBean;
import com.boomhope.tms.message.in.bck.BCK0016ResBodyBean;
import com.boomhope.tms.message.out.Out03514ReqBean;
import com.boomhope.tms.message.out.Out03514ReqBodyBean;
import com.boomhope.tms.message.out.Out03514ResBean;
import com.boomhope.tms.message.out.Out03514ResBodyBean;
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

public class TransBCK0016 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK0016.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK0016ReqBean inReqBean;	// 渠道请求Bean
	BCK0016ResBean inResBean;	// 渠道响应Bean
	Out03514ReqBean	outReqBean;	// 前置请求Bean
	Out03514ResBean	outResBean;	// 前置响应Bean
	TransFlow transFlow;	// 交易流水Bean
	DeployMachine mac = null;
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK0016.getBean("TransFlowService");
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
			logger.info("向前置发送03514请求"+outReqMsg);
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			logger.info("接收前置03514响应"+outResMsg);
			
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
			transFlow.setTransCode("BCK_0016");
			transFlow.setTransName("回单机-存单打印");
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
				
		inResBean = new BCK0016ResBean();
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK0016ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK0016ResBean();
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			BCK0016ResBodyBean bck0016ResBodyBean = new BCK0016ResBodyBean();
			
			bck0016ResBodyBean.setDATE(outResBean.getBODY().getDATE());//日期
			bck0016ResBodyBean.setSVR_JRNL_NO(outResBean.getBODY().getSVR_JRNL_NO());//流水号
			bck0016ResBodyBean.setOPEN_RATE(outResBean.getBODY().getOPEN_RATE());//开户利率
			bck0016ResBodyBean.setOPEN_INST(outResBean.getBODY().getOPEN_INST());//开户机构
			bck0016ResBodyBean.setSTART_DATE(outResBean.getBODY().getSTART_DATE());//起息日期
			bck0016ResBodyBean.setEND_DATE(outResBean.getBODY().getEND_DATE());//到期日期
			bck0016ResBodyBean.setBALANCE(outResBean.getBODY().getBALANCE());//余额
			bck0016ResBodyBean.setCERT_NO(outResBean.getBODY().getCERT_NO());//凭证号
			bck0016ResBodyBean.setDEP_TERM(outResBean.getBODY().getDEP_TERM());//存期
			bck0016ResBodyBean.setINTE(outResBean.getBODY().getINTE());//预计利息
			bck0016ResBodyBean.setEXCH_FLAG(outResBean.getBODY().getEXCH_FLAG());//自动转存标志
			bck0016ResBodyBean.setPRO_CODE(outResBean.getBODY().getPRO_CODE());//产品代码
			bck0016ResBodyBean.setPROD_NAME(outResBean.getBODY().getPROD_NAME());//产品名称
			bck0016ResBodyBean.setACCT_NAME(outResBean.getBODY().getACCT_NAME());//户名
			bck0016ResBodyBean.setOPEN_DATE(outResBean.getBODY().getOPEN_DATE());//开户日期
			bck0016ResBodyBean.setITEM_NO(outResBean.getBODY().getITEM_NO());//科目号
			bck0016ResBodyBean.setTYPE(outResBean.getBODY().getTYPE());//类型
			bck0016ResBodyBean.setPAY_COND(outResBean.getBODY().getPAY_COND());//支付条件
			bck0016ResBodyBean.setAREA_FLOAT_RET(outResBean.getBODY().getAREA_FLOAT_RET());//区域浮动利率
			bck0016ResBodyBean.setCHL_FLOAT_RET(outResBean.getBODY().getCHL_FLOAT_RET());//渠道浮动利率
			bck0016ResBodyBean.setBIRTH_FLOAT_RET(outResBean.getBODY().getBIRTH_FLOAT_RET());//生日浮动利率
			bck0016ResBodyBean.setTIME_FLOAT_RET(outResBean.getBODY().getTIME_FLOAT_RET());//时间段浮动利率
			bck0016ResBodyBean.setCOMB_FLOT_RET(outResBean.getBODY().getCOMB_FLOT_RET());//组合浮动利率
			bck0016ResBodyBean.setFILE_NAME_RET(outResBean.getBODY().getFILE_NAME_RET());//浮动利率组合文件
			inResBean.setBody(bck0016ResBodyBean);
			
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			reqXs.alias("Root", BCK0016ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}

	private Out03514ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Out03514ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out03514ResBodyBean.class);
			
			return (Out03514ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out03514ReqBean();
			Out03514ReqBodyBean out03514ReqBodyBean = new Out03514ReqBodyBean();
			out03514ReqBodyBean.setACCT_NO(inReqBean.getBody().getACCT_NO()); //卡号
			out03514ReqBodyBean.setSUB_ACCT_NO(inReqBean.getBody().getSUB_ACCT_NO()); //子账号
			out03514ReqBodyBean.setCERT_NO_ADD(inReqBean.getBody().getCERT_NO_ADD()); //凭证号
			
			outReqBean.setBODY(out03514ReqBodyBean);
			OutHeadBean outHeadBean = new OutHeadBean();
			outHeadBean.setTRAN_CODE("03514");
			outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));
			outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));
			outHeadBean.setCARD_ACCP_TERM_ID(inReqBean.getHeadBean().getMachineNo());//自助设备编号
			outHeadBean.setMER_NO(mac.getMerNo());//商户号
			outHeadBean.setINST_NO(inReqBean.getHeadBean().getBranchNo());//机构号
			outHeadBean.setTELLER_NO(mac.getTellerNo());// 柜员号
			outHeadBean.setCHANNEL(mac.getChannel());// 渠道号
			outReqBean.setHeadBean(outHeadBean);
			XStream xs = new XStream(new XppDriver(new XStreamNameCoder()));
			xs.autodetectAnnotations(true);
			
			xs.alias("ROOT", Out03514ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out03514ReqBodyBean.class);
			String xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xs.toXML(outReqBean);
			logger.info("发送前置报文：\n"+xml);
			byte[] bbb = xml.getBytes("GBK");
			return MACProtocolUtils.toRequest(mac.getMackeyflag(), bbb);
		} catch (Exception e) {
			logger.error("生成前置请求报文异常",e);
			throw new Exception("生成前置请求报文异常", e);
		}
		
	}

	private BCK0016ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK0016ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK0016ReqBodyBean.class);
		
		return (BCK0016ReqBean)reqXs.fromXML(msg);
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

