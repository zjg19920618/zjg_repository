package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK01597ReqBean;
import com.boomhope.tms.message.in.bck.BCK01597ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK01597ResBean;
import com.boomhope.tms.message.in.bck.BCK01597ResBodyBean;
import com.boomhope.tms.message.in.bck.BCK01597ResBodyList;
import com.boomhope.tms.message.out.Out01597ReqBean;
import com.boomhope.tms.message.out.Out01597ReqBodyBean;
import com.boomhope.tms.message.out.Out01597ResBean;
import com.boomhope.tms.message.out.Out01597ResBodyBean;
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



public class TransBCK01597 extends BaseTransaction implements ServletContextListener{

	Logger logger = Logger.getLogger(TransBCK01597.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK01597ReqBean inReqBean;	// 渠道请求Bean
	BCK01597ResBean inResBean;	// 渠道响应Bean
	Out01597ReqBean	outReqBean;	// 前置请求Bean
	Out01597ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	TransFlow transFlow;	// 交易流水Bean
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK01597.getBean("TransFlowService");
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
			transFlow.setTransCode("BCK_01597");
			transFlow.setTransName("回单机-白名单查询");
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
		inResBean = new BCK01597ResBean();
		BCK01597ResBodyBean bck0002ResBodyBean = new BCK01597ResBodyBean();
		
		inResBean.setBody(bck0002ResBodyBean);
//		inResBean.setBODY(bODY);(bck0002ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK01597ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK01597ResBean();
			//然后将Out02880ResBean类中的数据放入BCK0002ResBean中，返回
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			BCK01597ResBodyBean bck0002ResBodyBean = new BCK01597ResBodyBean();
//			bck0002ResBodyBean.setSvrDate(outResBean.getBODY().getSVR_DATE());//核心日期
//			bck0002ResBodyBean.ESB_LIST.setSOURCE_ID(outResBean.getBODY().getESB_LIST().getSOURCE_ID());
//			bck0002ResBodyBean.ESB_LIST.setSTTN(outResBean.getBODY().getESB_LIST().getSTTN());
			bck0002ResBodyBean.setESB_LISTCount(outResBean.getBODY().getESB_LISTCount());
			BCK01597ResBodyList bCK01597ResBodyList = new BCK01597ResBodyList();
			bCK01597ResBodyList.setSOURCE_ID(outResBean.getBODY().getESB_LIST().getSOURCE_ID());
			bCK01597ResBodyList.setSTTN(outResBean.getBODY().getESB_LIST().getSTTN());
			bck0002ResBodyBean.setESB_LIST(bCK01597ResBodyList);
			inResBean.setBody(bck0002ResBodyBean);
					
			
//			inResBean.setBODY(bck0002ResBodyBean);
			InResHeadBean headBean = new InResHeadBean();
			headBean.setResCode(outResBean.getResponseBean().getRetCode());
			headBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(headBean);
			reqXs.alias("Root", BCK01597ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
	}

	private Out01597ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Out01597ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out01597ResBodyBean.class);
			
			return (Out01597ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out01597ReqBean();
			/* 写入数据 */
			//将BCK0002ReqBean里面的数据放入Out02880ReqBean中，并解析成String字符串
			Out01597ReqBodyBean out01597ReqBodyBean = new Out01597ReqBodyBean();
			out01597ReqBodyBean.setACC_NAME1(inReqBean.getBody().getACC_NAME1());
			out01597ReqBodyBean.setACC_NAME2(inReqBean.getBody().getACC_NAME2());
			out01597ReqBodyBean.setACC_NO1(inReqBean.getBody().getACC_NO1());
			out01597ReqBodyBean.setACC_NO2(inReqBean.getBody().getACC_NO2());
			out01597ReqBodyBean.setBANK_ID1(inReqBean.getBody().getBANK_ID1());
			out01597ReqBodyBean.setBANK_ID2(inReqBean.getBody().getBANK_ID2());
			out01597ReqBodyBean.setCARD_NO1(inReqBean.getBody().getCARD_NO1());
			out01597ReqBodyBean.setCARD_NO2(inReqBean.getBody().getCARD_NO2());
			out01597ReqBodyBean.setCCY(inReqBean.getBody().getCCY());
			out01597ReqBodyBean.setID_NAME1(inReqBean.getBody().getID_NAME1());
			out01597ReqBodyBean.setID_NAME2(inReqBean.getBody().getID_NAME2());
			out01597ReqBodyBean.setID_NUMBER1(inReqBean.getBody().getID_NUMBER1());
			out01597ReqBodyBean.setID_NUMBER2(inReqBean.getBody().getID_NUMBER2());
			out01597ReqBodyBean.setID_TYPE1(inReqBean.getBody().getID_TYPE1());
			out01597ReqBodyBean.setID_TYPE2(inReqBean.getBody().getID_TYPE2());
			out01597ReqBodyBean.setMEMO1(inReqBean.getBody().getMEMO1());
			out01597ReqBodyBean.setMEMO2(inReqBean.getBody().getMEMO2());
			out01597ReqBodyBean.setMEMO3(inReqBean.getBody().getMEMO3());
			out01597ReqBodyBean.setMEMO4(inReqBean.getBody().getMEMO4());
			out01597ReqBodyBean.setPROGRAM_FLAG(inReqBean.getBody().getPROGRAM_FLAG());
			out01597ReqBodyBean.setTRAN_AMT(inReqBean.getBody().getTRAN_AMT());
			outReqBean.setBODY(out01597ReqBodyBean);
			OutHeadBean outHeadBean = new OutHeadBean();
			outHeadBean.setTRAN_CODE("01597");
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
			
			xs.alias("ROOT", Out01597ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out01597ReqBodyBean.class);
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

	private BCK01597ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK01597ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK01597ReqBodyBean.class);
		
		return (BCK01597ReqBean)reqXs.fromXML(msg);
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
