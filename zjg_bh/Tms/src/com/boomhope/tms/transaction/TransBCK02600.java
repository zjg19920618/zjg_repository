package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK02600ReqBean;
import com.boomhope.tms.message.in.bck.BCK02600ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK02600ResBean;
import com.boomhope.tms.message.in.bck.BCK02600ResBodyBean;
import com.boomhope.tms.message.out.Out02600ReqBean;
import com.boomhope.tms.message.out.Out02600ReqBodyBean;
import com.boomhope.tms.message.out.Out02600ReqBodyDetailBean;
import com.boomhope.tms.message.out.Out02600ResBean;
import com.boomhope.tms.message.out.Out02600ResBodyBean;
import com.boomhope.tms.message.out.OutHeadBean;
import com.boomhope.tms.message.out.OutResponseBean;
import com.boomhope.tms.service.IDeployMachineService;
import com.boomhope.tms.service.ISequenceManageService;
import com.boomhope.tms.service.ITransFlowService;
import com.boomhope.tms.socket.SocketClient;
import com.boomhope.tms.util.DateUtil;
import com.boomhope.tms.util.MACProtocolUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;
/**
 * 行内汇划-前置02600
 * @author hk
 *
 */
public class TransBCK02600 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK02600.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	ISequenceManageService sequenceManageService=null;
	BCK02600ReqBean inReqBean;	// 渠道请求Bean
	BCK02600ResBean inResBean;	// 渠道响应Bean
	Out02600ReqBean	outReqBean;	// 前置请求Bean
	Out02600ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	TransFlow transFlow;	// 交易流水Bean
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK02600.getBean("TransFlowService");
			deployMachineService = (IDeployMachineService) TransTms0001.getBean("deployMachineService");
			sequenceManageService=(ISequenceManageService) TransBCK02600.getBean("SequenceManageImpl");
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
			logger.info("向前置发送02600请求"+outReqMsg);
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendNewServiceMsg(outReqMsg);
			logger.info("接刷前置响应02600请求"+outReqMsg);
			
			//解析前置响应报文 
			outResBean = makeOutResBean(outResMsg);
			
			/* 更新流水信息 */
			updateTransFlow();
			
			//然后将Account02600ResBean类中的数据放入BCK02600ResBean中，返回
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
			transFlow.setTransCode("BCK_02600");
			transFlow.setTransName("行内汇划");
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
		
		inResBean = new BCK02600ResBean();
		BCK02600ResBodyBean BCK02600ResBodyBean = new BCK02600ResBodyBean();
		
		inResBean.setBody(BCK02600ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK02600ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK02600ResBean();
			BCK02600ResBodyBean BCK02600ResBodyBean = new BCK02600ResBodyBean();
			BCK02600ResBodyBean.setSVR_JRNL_NO(outResBean.getBODY().getSVR_JRNL_NO());//核心流水号
			BCK02600ResBodyBean.setSVR_DATE(outResBean.getBODY().getSVR_DATE());//核心日期
			BCK02600ResBodyBean.setBUSI_JRNL_NO(outResBean.getBODY().getBUSI_JRNL_NO());//前置流水号
			BCK02600ResBodyBean.setVAT_SUB(outResBean.getBODY().getVAT_SUB());//增值税科目
			BCK02600ResBodyBean.setVAT_AMT(outResBean.getBODY().getVAT_AMT());//增值税
			BCK02600ResBodyBean.setCTF_ATM(outResBean.getBODY().getCTF_ATM());//税后手续费
			BCK02600ResBodyBean.setVAT_SUB(outResBean.getBODY().getVAT_SUB());//手续费科目
			BCK02600ResBodyBean.setTRAN_TIME(outResBean.getBODY().getTRAN_TIME());//交易时间
			BCK02600ResBodyBean.setTASK_ID(outReqBean.getBODY().getTASK_IDTRNS());//返回任务号

			inResBean.setBody(BCK02600ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK02600ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}

	private Out02600ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Out02600ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out02600ResBodyBean.class);
			
			return (Out02600ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out02600ReqBean();
			Out02600ReqBodyBean accountBody = new Out02600ReqBodyBean();
			accountBody.setBUSI_TYPE(inReqBean.getBody().getBUSI_TYPE());//业务类型
			accountBody.setDB_CR_FLAG(inReqBean.getBody().getDB_CR_FLAG());//借贷标志
			accountBody.setAGENT_INST_NO(inReqBean.getBody().getAGENT_INST_NO());//被代理机构号
			accountBody.setSEND_BANK_NO(inReqBean.getBody().getSEND_BANK_NO());//提出行行号
			accountBody.setSEND_BANK_NAME(inReqBean.getBody().getSEND_BANK_NAME());//提出行名称
			accountBody.setPAY_ACCT_NO(inReqBean.getBody().getPAY_ACCT_NO());//付款人账号
			accountBody.setPAY_NAME(inReqBean.getBody().getPAY_NAME());//付款人名称
			accountBody.setDRAW_COND(inReqBean.getBody().getDRAW_COND());//支取条件
			accountBody.setRECV_BANK_NO(inReqBean.getBody().getRECV_BANK_NO());//提入行行号
			accountBody.setRECV_BANK_NAME(inReqBean.getBody().getRECV_BANK_NAME());//提入行名称
			accountBody.setPAYEE_ACCT_NO(inReqBean.getBody().getPAYEE_ACCT_NO());//收款人账号
			accountBody.setPAYEE_NAME(inReqBean.getBody().getPAYEE_NAME());//收款人名称
			accountBody.setBILL_TYPE(inReqBean.getBody().getBILL_TYPE());//票据种类
			accountBody.setBILL_NO(inReqBean.getBody().getBILL_NO());//票据号码
			accountBody.setPIN(inReqBean.getBody().getPIN());//支付密码
			accountBody.setAVAL_BAL(inReqBean.getBody().getAVAL_BAL());//可用余额
			accountBody.setCUEERNCY(inReqBean.getBody().getCUEERNCY());//币种
			accountBody.setAMT(inReqBean.getBody().getAMT());//金    额
			accountBody.setBILL_DATE(inReqBean.getBody().getBILL_DATE());//出票日期
			accountBody.setNOTE_DATE(inReqBean.getBody().getNOTE_DATE());//提示付款日期
			accountBody.setENDOR_NUM(inReqBean.getBody().getENDOR_NUM());//背书次数
			accountBody.setREMARK(inReqBean.getBody().getREMARK());//备注
			accountBody.setPURPOS(inReqBean.getBody().getPURPOS());//用途
			accountBody.setTRN_REASON(inReqBean.getBody().getTRN_REASON());//转账原因
			accountBody.setORIG_CERT_TYPE(inReqBean.getBody().getORIG_CERT_TYPE());//原始凭证种类
			accountBody.setATTACH_NUM(inReqBean.getBody().getATTACH_NUM());//张数
			accountBody.setAPPD_TEXT(inReqBean.getBody().getAPPD_TEXT());//附加信息
			accountBody.setSUMM_CODE(inReqBean.getBody().getSUMM_CODE());//摘要代码
			accountBody.setSUMM_TEXT(inReqBean.getBody().getSUMM_TEXT());//摘要内容
			accountBody.setNEXT_DAY_SEND_FLAG(inReqBean.getBody().getNEXT_DAY_SEND_FLAG());//次日发送标志
			accountBody.setTIMED_SEND_TIME(inReqBean.getBody().getTIMED_SEND_TIME());//定时发送时间
			String date=inReqBean.getBody().getTASK_IDTRNS().substring(4,10);
			accountBody.setTASK_IDTRNS(inReqBean.getBody().getTASK_IDTRNS()+sequenceManageService.getTaskNum("task_num", 5, date));//任务号
			Out02600ReqBodyDetailBean out02600rbdb=new Out02600ReqBodyDetailBean();//创建背书人清单
			out02600rbdb.setENDOR_NAME(inReqBean.getBody().getDETAIL().getENDOR_NAME());//背书人清单
			accountBody.setDETAIL(out02600rbdb);// 设置背书人清单
			accountBody.setTEL_NO(inReqBean.getBody().getTEL_NO());//手机号
		
			outReqBean.setBODY(accountBody);
			OutHeadBean headBean = new OutHeadBean();
			headBean.setTRAN_CODE("02600");
			headBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));
			headBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));
			headBean.setCARD_ACCP_TERM_ID(inReqBean.getHeadBean().getMachineNo());//自助设备编号
			headBean.setMER_NO(mac.getMerNo());//商户号
			headBean.setINST_NO(inReqBean.getHeadBean().getBranchNo());//机构号
			headBean.setTELLER_NO(mac.getTellerNo());// 柜员号
			headBean.setCHANNEL(mac.getChannel());// 渠道号
			headBean.setSUP_TELLER_NO((inReqBean.getHeadBean().getSupTellerNo()));// 授权柜员号
			outReqBean.setHeadBean(headBean);
			//转换xml
			XStream xs = new XStream(new XppDriver(new XStreamNameCoder()));
			xs.autodetectAnnotations(true);
			
			xs.alias("ROOT", Out02600ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out02600ReqBodyBean.class);
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

	private BCK02600ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK02600ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK02600ReqBodyBean.class);
		
		return (BCK02600ReqBean)reqXs.fromXML(msg);
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
