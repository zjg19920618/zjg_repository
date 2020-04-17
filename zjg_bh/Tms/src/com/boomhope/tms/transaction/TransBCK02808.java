package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK02808ReqBean;
import com.boomhope.tms.message.account.in.BCK02808ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK02808ResBean;
import com.boomhope.tms.message.account.in.BCK02808ResBodyBean;
import com.boomhope.tms.message.account.out.Account02808ReqBean;
import com.boomhope.tms.message.account.out.Account02808ReqBodyBean;
import com.boomhope.tms.message.account.out.Account02808ResBean;
import com.boomhope.tms.message.account.out.Account02808ResBodyBean;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.out.OutHeadBean;
import com.boomhope.tms.message.out.OutResponseBean;
import com.boomhope.tms.service.IDeployMachineService;
import com.boomhope.tms.service.ITransFlowService;
import com.boomhope.tms.socket.SocketClient;
import com.boomhope.tms.util.DateUtil;
import com.boomhope.tms.util.EncryptUtils;
import com.boomhope.tms.util.MACProtocolUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class TransBCK02808 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK02808.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK02808ReqBean inReqBean;	// 渠道请求Bean
	BCK02808ResBean inResBean;	// 渠道响应Bean
	Account02808ReqBean	outReqBean;	// 前置请求Bean
	Account02808ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	TransFlow transFlow;	// 交易流水Bean
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK02808.getBean("TransFlowService");
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
			logger.info("向前置发送02808请求"+outReqMsg);
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			logger.info("接刷前置响应02808请求"+outReqMsg);
			
			//解析前置响应报文 
			outResBean = makeOutResBean(outResMsg);
			
			/* 更新流水信息 */
			updateTransFlow();
			
			//然后将Out02210ResBean类中的数据放入BCK0005ResBean中，返回
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
			transFlow.setTransCode("BCK_02808");
			transFlow.setTransName("回单机-个人存款开户");
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
		
		inResBean = new BCK02808ResBean();
		BCK02808ResBodyBean bck02808ResBodyBean = new BCK02808ResBodyBean();
		
		inResBean.setBody(bck02808ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK02808ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK02808ResBean();
			BCK02808ResBodyBean bck02808ResBodyBean = new BCK02808ResBodyBean();
			bck02808ResBodyBean.setSVR_DATE(outResBean.getBODY().getSVR_DATE());//核心日期
			bck02808ResBodyBean.setSVR_JRNL_NO(outResBean.getBODY().getSVR_JRNL_NO());//流水号
			bck02808ResBodyBean.setACCT_NO(outResBean.getBODY().getACCT_NO());//账 号
			bck02808ResBodyBean.setCERT_NO(outResBean.getBODY().getCERT_NO());//凭证号
			bck02808ResBodyBean.setRATE(outResBean.getBODY().getRATE());//开户利率
			bck02808ResBodyBean.setINTE_DATE(outResBean.getBODY().getINTE_DATE());//起息日期
			bck02808ResBodyBean.setEND_DATE(outResBean.getBODY().getEND_DATE());//到期日
			bck02808ResBodyBean.setDEP_TERM(outResBean.getBODY().getDEP_TERM());//存 期
			bck02808ResBodyBean.setFILE_NAME(outResBean.getBODY().getFILE_NAME());//存折文件
			bck02808ResBodyBean.setITEM_NO(outResBean.getBODY().getITEM_NO());//科目
			bck02808ResBodyBean.setOPEN_INST(outResBean.getBODY().getOPEN_INST());//开户机构
			bck02808ResBodyBean.setOPP_ACCT_NO(outResBean.getBODY().getOPP_ACCT_NO());//对方账号
			bck02808ResBodyBean.setOPP_ACCT_NAME(outResBean.getBODY().getOPP_ACCT_NAME());//对方户名
			bck02808ResBodyBean.setOPP_ITEM_NO(outResBean.getBODY().getOPP_ITEM_NO());//对方科目
			bck02808ResBodyBean.setOPP_OPEN_INST(outResBean.getBODY().getOPP_OPEN_INST());//对方开户机构
			bck02808ResBodyBean.setCR_DB_FLAG(outResBean.getBODY().getCR_DB_FLAG());//借贷标志
			bck02808ResBodyBean.setINTE(outResBean.getBODY().getINTE());//预计利息
			bck02808ResBodyBean.setAREA_FLOAT_RET(outResBean.getBODY().getAREA_FLOAT_RET());//区域浮动利率
			bck02808ResBodyBean.setCHL_FLOAT_RET(outResBean.getBODY().getCHL_FLOAT_RET());//渠道浮动利率
			bck02808ResBodyBean.setBIRTH_FLOAT_RET(outResBean.getBODY().getBIRTH_FLOAT_RET());//生日浮动利率
			bck02808ResBodyBean.setTIME_FLOAT_RET(outResBean.getBODY().getTIME_FLOAT_RET());//时间段浮动利率
			bck02808ResBodyBean.setCOMB_FLOT_RET(outResBean.getBODY().getCOMB_FLOT_RET());//组合浮动利率
			bck02808ResBodyBean.setFILE_NAME_RET(outResBean.getBODY().getFILE_NAME_RET());//文件
			inResBean.setBody(bck02808ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK02808ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}

	private Account02808ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Account02808ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Account02808ResBodyBean.class);
			
			return (Account02808ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Account02808ReqBean();
			Account02808ReqBodyBean accountBody = new Account02808ReqBodyBean();
			accountBody.setID_TYPE(inReqBean.getBody().getID_TYPE());//证件代号
			accountBody.setID_NO(inReqBean.getBody().getID_NO());//证件号码
			accountBody.setCUST_NO(inReqBean.getBody().getCUST_NO());//客户号
			accountBody.setISSUE_BRANCH(inReqBean.getBody().getISSUE_BRANCH());//发证机关
			accountBody.setCUST_NAME(inReqBean.getBody().getCUST_NAME());//客户名称
			accountBody.setCUST_ADDRESS(inReqBean.getBody().getCUST_ADDRESS());//客户地址
			accountBody.setCUST_TELE(inReqBean.getBody().getCUST_TELE());//客户电话
			accountBody.setCURRENCY(inReqBean.getBody().getCURRENCY());//币  种
			accountBody.setPROD_CODE(inReqBean.getBody().getPROD_CODE());//产品代码
			accountBody.setACCT_TYPE(inReqBean.getBody().getACCT_TYPE());//账号种类
			//存单密码加密
			String passWord = inReqBean.getBody().getPASSWORD().trim();
			if(StringUtils.isNotBlank(passWord)){
				String cardNo="0000000000000000";
				logger.info("02808存单加密前：   "+passWord);
				String tranPwd = EncryptUtils.tranPin310(mac.getWorkkeyflag(), mac.getKeyflag(), cardNo, passWord);
				logger.info("02808存单加密后：   "+tranPwd);
				logger.debug("*******转加密密文："+tranPwd);
				accountBody.setPASSWORD(tranPwd);
				accountBody.setLIMIT("1");//支付条件
			}else{
				accountBody.setPASSWORD(passWord);//密码
				accountBody.setLIMIT("0");//支付条件
			}
			accountBody.setSTART_INT_DATE(inReqBean.getBody().getSTART_INT_DATE());//起息日期
			accountBody.setNOTE_TERM(inReqBean.getBody().getNOTE_TERM());//通知期限
			accountBody.setUNIT_FLAG(inReqBean.getBody().getUNIT_FLAG());//存期单位
			accountBody.setDEP_TERM(inReqBean.getBody().getDEP_TERM());//存期
			accountBody.setDRAW_AMT_TERM(inReqBean.getBody().getDRAW_AMT_TERM());//取款期
			accountBody.setDRAW_INTE_TERM(inReqBean.getBody().getDRAW_INTE_TERM());//取息期
			accountBody.setSETT_TYPE(inReqBean.getBody().getSETT_TYPE());//结算方式
			accountBody.setSUB_ACCT_NO(inReqBean.getBody().getSUB_ACCT_NO());//子账号
			accountBody.setOPPO_DRAW_COND(inReqBean.getBody().getOPPO_DRAW_COND());//对方户支取条件
			
			String oppo_ACCT_NO = inReqBean.getBody().getOPPO_ACCT_NO();
			accountBody.setOPPO_ACCT_NO(oppo_ACCT_NO);//对方账号
			//银行卡号密码加密
			String draw_PASSWORD = inReqBean.getBody().getDRAW_PASSWORD();
			logger.info("02808银行卡加密前：   "+draw_PASSWORD);
			String tranPwd = EncryptUtils.tranPin310(mac.getWorkkeyflag(), mac.getKeyflag(), oppo_ACCT_NO, draw_PASSWORD);
			logger.info("02808银行卡加密后：   "+tranPwd);
			logger.debug("*******转加密密文："+tranPwd);
			accountBody.setDRAW_PASSWORD(tranPwd);//密码
			
			accountBody.setCERT_TYPE(inReqBean.getBody().getCERT_TYPE());//凭证种类
			accountBody.setCERT_NO(inReqBean.getBody().getCERT_NO());//凭证号码
			accountBody.setCERT_DATE(inReqBean.getBody().getCERT_DATE());//票据日期
			accountBody.setAMT(inReqBean.getBody().getAMT());//存款金额
			accountBody.setPAY_PASSWD(inReqBean.getBody().getPAY_PASSWD());//支付密码
			accountBody.setNEW_CERT_NO(inReqBean.getBody().getNEW_CERT_NO());//凭证号
			accountBody.setEXCH_FLAG(inReqBean.getBody().getEXCH_FLAG());//自动转存次shu
			accountBody.setANALY_NO(inReqBean.getBody().getANALY_NO());//分析号
			accountBody.setCORRECT_FLAG(inReqBean.getBody().getCORRECT_FLAG());//打印户名
			accountBody.setHAV_AGENT_FLAG(inReqBean.getBody().getHAV_AGENT_FLAG());//是否有代理人标志
			accountBody.setDETAIL(inReqBean.getBody().getDETAIL());//代理人信息
			accountBody.setPUT_INT_ACCT(inReqBean.getBody().getPUT_INT_ACCT());//收益帐号
			accountBody.setSUB_ACCT_NO1(inReqBean.getBody().getSUB_ACCT_NO1());//子账号
			accountBody.setCHL_ID(inReqBean.getBody().getCHL_ID());//渠道模块标识
			outReqBean.setBODY(accountBody);
			OutHeadBean headBean = new OutHeadBean();
			headBean.setTRAN_CODE("02808");
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
			
			xs.alias("ROOT", Account02808ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Account02808ReqBodyBean.class);
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

	private BCK02808ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK02808ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK02808ReqBodyBean.class);
		
		return (BCK02808ReqBean)reqXs.fromXML(msg);
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
