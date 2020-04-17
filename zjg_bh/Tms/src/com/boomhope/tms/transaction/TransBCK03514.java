package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK03514ReqBean;
import com.boomhope.tms.message.in.bck.BCK03514ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03514ResBean;
import com.boomhope.tms.message.in.bck.BCK03514ResBodyBean;
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

public class TransBCK03514 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK03514.class);
	ITransFlowService transFlowService;					// 交易流水服务
	IDeployMachineService deployMachineService;			// 部署机逻辑处理
	BCK03514ReqBean inReqBean;							// 渠道请求Bean
	BCK03514ResBean inResBean;							// 渠道响应Bean
	Out03514ReqBean	outReqBean;							// 前置请求Bean
	Out03514ResBean	outResBean;							// 前置响应Bean
	DeployMachine mac;									// 机器部署信息
	TransFlow transFlow;								// 交易流水Bean
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK03514.getBean("TransFlowService");
			deployMachineService = (IDeployMachineService) TransTms0001.getBean("deployMachineService");
		} catch (Exception e) {
			logger.error("调用交易流水Service失败", e);
			return makeResErrorMsg("调用交易流水Service失败","service");
		}
		
		try {
			try {
				logger.info("终端请求报文:");
				//解析渠道报文
				inReqBean = makeInReqBean(msg);
				logger.info("解析渠道报文："+inReqBean);
			} catch (Exception e) {
				logger.error("解析渠道请求报文异常", e);
				return makeResErrorMsg("解析渠道请求报文异常","service");
			}
			// 获取机器部署信息
			mac = deployMachineService.getDeployMachineById(inReqBean.getHeadBean().getMachineNo());
			logger.info("机器部署信息："+mac);
			/* 记录流水信息 */
			addTransFlow();
			
			//生成前置请求报文 
			byte[] outReqMsg = makeOutReqMsg();
			logger.info("向前置发送03514请求"+outReqMsg);
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			logger.info("接刷前置响应03514请求"+outReqMsg);
			
			//解析前置响应报文 
			outResBean = makeOutResBean(outResMsg);
			logger.info("解析前置响应报文："+outResBean);
			
			/* 更新流水信息 */
			updateTransFlow();
			
			//然后将Account03512ResBean类中的数据放入BCK03512ResBean中，返回
			//返回信息
			String makeResMsg = makeResMsg();
			logger.info("返回信息："+makeResMsg);
			logger.info("终端响应报文：");
			return makeResMsg;
		} catch (Exception e) {
			logger.error("异常信息："+e);
			String getMessage = e.getMessage();
			logger.info("终端响应报文异常信息："+getMessage);
			return makeResErrorMsg(getMessage,null);
		}
		
	}
	
	/*
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
	
	/*
	 * 记录流水信息
	 */
	private void addTransFlow() throws Exception {
		try {
			transFlow = new TransFlow();
			transFlow.setTransCode("BCK_03514");
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
	
	/*
	 * 解析渠道请求报文异常
	 */
	private String makeResErrorMsg(String msg,String service) {
		if(null == service){
			//修改入库失败操作
			transFlow.setTransStatus("E");
			transFlow.setTransStatusDesc("交易失败");
			transFlowService.updateTransFlow(transFlow);
		}
		
		inResBean = new BCK03514ResBean();
		BCK03514ResBodyBean BCK03514ResBodyBean = new BCK03514ResBodyBean();
		
		inResBean.setBody(BCK03514ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03514ResBean.class);
		return reqXs.toXML(inResBean);
	}
	
	/*
	 * 生成响应信息
	 */
	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK03514ResBean();
			BCK03514ResBodyBean BCK03514ResBodyBean = new BCK03514ResBodyBean();
			BCK03514ResBodyBean.setDATE(outResBean.getBODY().getDATE());//日期
			BCK03514ResBodyBean.setSVR_JRNL_NO(outResBean.getBODY().getSVR_JRNL_NO());//流水号
			BCK03514ResBodyBean.setOPEN_RATE(outResBean.getBODY().getOPEN_RATE());//开户利率 
			BCK03514ResBodyBean.setOPEN_INST(outResBean.getBODY().getOPEN_INST());//开户机构
			BCK03514ResBodyBean.setSTART_DATE(outResBean.getBODY().getSTART_DATE());//起息日期
			BCK03514ResBodyBean.setEND_DATE(outResBean.getBODY().getEND_DATE());//到期日期
			BCK03514ResBodyBean.setBALANCE(outResBean.getBODY().getBALANCE());//余额
			BCK03514ResBodyBean.setCERT_NO(outResBean.getBODY().getCERT_NO());//凭证号
			BCK03514ResBodyBean.setDEP_TERM(outResBean.getBODY().getDEP_TERM());//存期
			BCK03514ResBodyBean.setINTE(outResBean.getBODY().getINTE());//预计利息
			BCK03514ResBodyBean.setEXCH_FLAG(outResBean.getBODY().getEXCH_FLAG());//自动转存标志
			BCK03514ResBodyBean.setPRO_CODE(outResBean.getBODY().getPRO_CODE());//产品代码
			BCK03514ResBodyBean.setPROD_NAME(outResBean.getBODY().getPROD_NAME());//产品名称
			BCK03514ResBodyBean.setACCT_NAME(outResBean.getBODY().getACCT_NAME());//户名
			BCK03514ResBodyBean.setOPEN_DATE(outResBean.getBODY().getOPEN_DATE());//开户日期
			BCK03514ResBodyBean.setITEM_NO(outResBean.getBODY().getITEM_NO());//科目号
			BCK03514ResBodyBean.setTYPE(outResBean.getBODY().getTYPE());//类型
			BCK03514ResBodyBean.setPAY_COND(outResBean.getBODY().getPAY_COND());//支付条件
			BCK03514ResBodyBean.setAREA_FLOAT_RET(outResBean.getBODY().getAREA_FLOAT_RET());//区域浮动利率 
			BCK03514ResBodyBean.setCHL_FLOAT_RET(outResBean.getBODY().getCHL_FLOAT_RET());//渠道浮动利率 
			BCK03514ResBodyBean.setBIRTH_FLOAT_RET(outResBean.getBODY().getBIRTH_FLOAT_RET());//生日浮动利率 
			BCK03514ResBodyBean.setTIME_FLOAT_RET(outResBean.getBODY().getTIME_FLOAT_RET());//时间段浮动利率 
			BCK03514ResBodyBean.setCOMB_FLOT_RET(outResBean.getBODY().getCOMB_FLOT_RET());//组合浮动利率 
			BCK03514ResBodyBean.setFILE_NAME_RET(outResBean.getBODY().getFILE_NAME_RET());//浮动利率 组合文件 
			inResBean.setBody(BCK03514ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK03514ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}
	
	/*
	 * 生成前置响应报文
	 */
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
	
	/*
	 * 生成前置报文
	 */
	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out03514ReqBean();
			Out03514ReqBodyBean outBody = new Out03514ReqBodyBean();
			outBody.setACCT_NO(inReqBean.getBody().getACCT_NO());//卡号
			outBody.setSUB_ACCT_NO(inReqBean.getBody().getSUB_ACCT_NO());//子账号
			outBody.setCERT_NO_ADD(inReqBean.getBody().getCERT_NO_ADD());//凭证号
			outReqBean.setBODY(outBody);
			OutHeadBean headBean = new OutHeadBean();
			headBean.setTRAN_CODE("03514");//交易代码
			headBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
			headBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
			headBean.setCARD_ACCP_TERM_ID(inReqBean.getHeadBean().getMachineNo());//自助设备编号
			headBean.setMER_NO(mac.getMerNo());//商户号
			headBean.setINST_NO(inReqBean.getHeadBean().getBranchNo());//机构号
			headBean.setTELLER_NO(mac.getTellerNo());// 柜员号
			headBean.setCHANNEL(mac.getChannel());// 渠道号
			outReqBean.setHeadBean(headBean);
			//转换xml
			XStream xs = new XStream(new XppDriver(new XStreamNameCoder()));
			xs.autodetectAnnotations(true);
			
			xs.alias("ROOT", Out03514ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out03514ReqBodyBean.class);
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
	
	/*
	 * 解析渠道报文
	 */
	private BCK03514ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK03514ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK03514ReqBodyBean.class);
		
		return (BCK03514ReqBean)reqXs.fromXML(msg);
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
