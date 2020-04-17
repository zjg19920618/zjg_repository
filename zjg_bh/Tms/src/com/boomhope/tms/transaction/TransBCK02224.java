package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK02224ReqBean;
import com.boomhope.tms.message.in.bck.BCK02224ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK02224ResBean;
import com.boomhope.tms.message.in.bck.BCK02224ResBodyBean;
import com.boomhope.tms.message.out.Out02224ReqBean;
import com.boomhope.tms.message.out.Out02224ReqBodyBean;
import com.boomhope.tms.message.out.Out02224ReqBodyDetailBean;
import com.boomhope.tms.message.out.Out02224ResBean;
import com.boomhope.tms.message.out.Out02224ResBodyBean;
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
 * 小额普通贷记往帐录入（通用）02224
 * @author hk
 *
 */
public class TransBCK02224 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK02224.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	ISequenceManageService sequenceManageService=null;
	BCK02224ReqBean inReqBean;	// 渠道请求Bean
	BCK02224ResBean inResBean;	// 渠道响应Bean
	Out02224ReqBean	outReqBean;	// 前置请求Bean
	Out02224ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	TransFlow transFlow;	// 交易流水Bean
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK02224.getBean("TransFlowService");
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
			logger.info("向前置发送02224请求"+outReqMsg);
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendNewServiceMsg(outReqMsg);
			logger.info("接刷前置响应02224请求"+outReqMsg);
			
			//解析前置响应报文 
			outResBean = makeOutResBean(outResMsg);
			
			/* 更新流水信息 */
			updateTransFlow();
			
			//然后将Account02224ResBean类中的数据放入BCK02224ResBean中，返回
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
			transFlow.setTransCode("BCK_02224");
			transFlow.setTransName("小额普通贷记往帐录入（通用）");
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
		
		inResBean = new BCK02224ResBean();
		BCK02224ResBodyBean BCK02224ResBodyBean = new BCK02224ResBodyBean();
		
		inResBean.setBody(BCK02224ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK02224ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK02224ResBean();
			BCK02224ResBodyBean BCK02224ResBodyBean = new BCK02224ResBodyBean();
			BCK02224ResBodyBean.setSVR_DATE(outResBean.getBODY().getSVR_DATE());//核心日期
			BCK02224ResBodyBean.setSVR_JRNL_NO(outResBean.getBODY().getSVR_JRNL_NO());//流水号
			BCK02224ResBodyBean.setTRAN_NO(outResBean.getBODY().getTRAN_NO());//支付交易序号
			BCK02224ResBodyBean.setTRAN_TIME(outResBean.getBODY().getTRAN_TIME());//交易时间
			BCK02224ResBodyBean.setTASK_ID(outReqBean.getBODY().getTASK_ID());//任务号

			inResBean.setBody(BCK02224ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK02224ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}

	private Out02224ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Out02224ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out02224ResBodyBean.class);
			
			return (Out02224ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out02224ReqBean();
			Out02224ReqBodyBean accountBody = new Out02224ReqBodyBean();
			accountBody.setBUSI_OPTION(inReqBean.getBody().getBUSI_OPTION());//业务选择
			accountBody.setBUSI_CLASS(inReqBean.getBody().getBUSI_CLASS());//业务类型
			accountBody.setCURRENCY(inReqBean.getBody().getCURRENCY());//币种
			accountBody.setBOOK_CARD(inReqBean.getBody().getBOOK_CARD());//折卡选择
			accountBody.setPAY_ACCT_NO(inReqBean.getBody().getPAY_ACCT_NO());//付款人帐号
			accountBody.setPAY_CARD_NO(inReqBean.getBody().getPAY_CARD_NO());//付款人卡号
			accountBody.setPAY_NAME(inReqBean.getBody().getPAY_NAME());//付款人名称
			accountBody.setPAY_ADDR(inReqBean.getBody().getPAY_ADDR());//付款人地址
			accountBody.setPAY_HBR_NO(inReqBean.getBody().getPAY_HBR_NO());//付款人开户行号
			accountBody.setPAY_HBR_NAME(inReqBean.getBody().getPAY_HBR_NAME());//付款人开户行名
			accountBody.setPAY_HBR_INST_NO(inReqBean.getBody().getPAY_HBR_INST_NO());//付款人开户机构号
			accountBody.setDRAW_COND(inReqBean.getBody().getDRAW_COND());//支付条件
			accountBody.setBOOK_NO(inReqBean.getBody().getBOOK_NO());//存折号码
			accountBody.setPASSWORD(inReqBean.getBody().getPASSWORD());//密码
			accountBody.setBALANCE(inReqBean.getBody().getBALANCE());//余额
			accountBody.setCERT_TYPE(inReqBean.getBody().getCERT_TYPE());//凭证种类
			accountBody.setCERT_NO(inReqBean.getBody().getCERT_NO());//凭证号码
			accountBody.setSUMM_CODE(inReqBean.getBody().getSUMM_CODE());//摘要
			accountBody.setSUMM_TEXT(inReqBean.getBody().getSUMM_TEXT());//摘要内容
			accountBody.setREMIT_AMT(inReqBean.getBody().getREMIT_AMT());//汇款金额
			accountBody.setFEE_TYPE(inReqBean.getBody().getFEE_TYPE());//手续费类型
			accountBody.setFEE(inReqBean.getBody().getFEE());//手续费 	
			accountBody.setPOST_FEE(inReqBean.getBody().getPOST_FEE());//邮电费
			accountBody.setBUSI_TYPE(inReqBean.getBody().getBUSI_TYPE());//业务种类
			accountBody.setITEM_NUM(inReqBean.getBody().getITEM_NUM());//笔数
			
			Out02224ReqBodyDetailBean out02224rbdb=new Out02224ReqBodyDetailBean();//创建收款方明细 
			out02224rbdb.setPAYEE_BANK_NO(inReqBean.getBody().getDETAIL().getPAYEE_BANK_NO());//收款人行号
			out02224rbdb.setPAYEE_BANK_NAME(inReqBean.getBody().getDETAIL().getPAYEE_BANK_NAME());//收款人行名
			out02224rbdb.setPAYEE_HBR_NO(inReqBean.getBody().getDETAIL().getPAYEE_HBR_NO());//收款开户行号
			out02224rbdb.setPAYEE_HBR_NAME(inReqBean.getBody().getDETAIL().getPAYEE_HBR_NAME());//收款开户行名
			out02224rbdb.setPAYEE_ACCT_NO(inReqBean.getBody().getDETAIL().getPAYEE_ACCT_NO());//收款人帐号
			out02224rbdb.setPAYEE_NAME(inReqBean.getBody().getDETAIL().getPAYEE_NAME());//收款人户名
			out02224rbdb.setPAYEE_ADDR(inReqBean.getBody().getDETAIL().getPAYEE_ADDR());//收款人地址
			out02224rbdb.setPAY_AMT(inReqBean.getBody().getDETAIL().getPAY_AMT());//支付金额
			out02224rbdb.setAPPD_TEXT(inReqBean.getBody().getDETAIL().getAPPD_TEXT());//附言
			accountBody.setDETAIL(out02224rbdb);//设置收款方明细 
			
			accountBody.setTRAN_AMT(inReqBean.getBody().getTRAN_AMT());//交易金额
			accountBody.setPAY_ACCT_TYPE(inReqBean.getBody().getPAY_ACCT_TYPE());//付款类型
			accountBody.setPAYEE_ACCT_TYPE(inReqBean.getBody().getPAYEE_ACCT_TYPE());//收款类型
			accountBody.setSETT_TYPE(inReqBean.getBody().getSETT_TYPE());//业务模式
			String date=inReqBean.getBody().getTASK_ID().substring(4,10);
			accountBody.setTASK_ID(inReqBean.getBody().getTASK_ID()+sequenceManageService.getTaskNum("task_num", 5, date));//任务号
			accountBody.setCORE_PRJ_NO(inReqBean.getBody().getCORE_PRJ_NO());//核心项目编号
			accountBody.setCORE_PRO_CODE(inReqBean.getBody().getCORE_PRO_CODE());//核心产品代码
			accountBody.setREMARK1(inReqBean.getBody().getREMARK1());//备注1
			accountBody.setREMARK2(inReqBean.getBody().getREMARK2());//备注2
			accountBody.setTRANSFER_FLAG(inReqBean.getBody().getTRANSFER_FLAG());//转账标志
			accountBody.setNEXT_DAY_SEND_FLAG(inReqBean.getBody().getNEXT_DAY_SEND_FLAG());//次日发送标志
			accountBody.setTIMED_SEND_TIME(inReqBean.getBody().getTIMED_SEND_TIME());//定时发送时间
			accountBody.setTEL_NO(inReqBean.getBody().getTEL_NO());//手机号
			
			outReqBean.setBODY(accountBody);
			OutHeadBean headBean = new OutHeadBean();
			headBean.setTRAN_CODE("02224");
			headBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));
			headBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));
			headBean.setCARD_ACCP_TERM_ID(inReqBean.getHeadBean().getMachineNo());//自助设备编号
			headBean.setMER_NO(mac.getMerNo());//商户号
			headBean.setINST_NO(inReqBean.getHeadBean().getBranchNo());//机构号
			headBean.setTELLER_NO(mac.getTellerNo());// 柜员号
			headBean.setCHANNEL(mac.getChannel());// 渠道号
			headBean.setSUP_TELLER_NO(inReqBean.getHeadBean().getSupTellerNo());//授权柜员号
			outReqBean.setHeadBean(headBean);
			//转换xml
			XStream xs = new XStream(new XppDriver(new XStreamNameCoder()));
			xs.autodetectAnnotations(true);
			
			xs.alias("ROOT", Out02224ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out02224ReqBodyBean.class);
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

	private BCK02224ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK02224ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK02224ReqBodyBean.class);
		
		return (BCK02224ReqBean)reqXs.fromXML(msg);
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
