package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK03870ReqBean;
import com.boomhope.tms.message.account.in.BCK03870ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK03870ResBean;
import com.boomhope.tms.message.account.in.BCK03870ResBodyBean;
import com.boomhope.tms.message.account.out.Account03870ReqAgentInfBean;
import com.boomhope.tms.message.account.out.Account03870ReqBean;
import com.boomhope.tms.message.account.out.Account03870ReqBodyBean;
import com.boomhope.tms.message.account.out.Account03870ResBean;
import com.boomhope.tms.message.account.out.Account03870ResBodyBean;
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

public class TransBCK03870 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK03870.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK03870ReqBean inReqBean;	// 渠道请求Bean
	BCK03870ResBean inResBean;	// 渠道响应Bean
	Account03870ReqBean	outReqBean;	// 前置请求Bean
	Account03870ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	TransFlow transFlow;	// 交易流水Bean
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK03870.getBean("TransFlowService");
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
			logger.info("向前置发送03870请求"+outReqMsg);
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			logger.info("接刷前置响应03870请求"+outReqMsg);
			
			//解析前置响应报文 
			outResBean = makeOutResBean(outResMsg);
			
			/* 更新流水信息 */
			updateTransFlow();
			
			//然后将Account03870ResBean类中的数据放入BCK03870ResBean中，返回
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
			transFlow.setTransCode("BCK_03870");
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
		
		inResBean = new BCK03870ResBean();
		BCK03870ResBodyBean BCK03870ResBodyBean = new BCK03870ResBodyBean();
		
		inResBean.setBody(BCK03870ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03870ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK03870ResBean();
			BCK03870ResBodyBean BCK03870ResBodyBean = new BCK03870ResBodyBean();
			BCK03870ResBodyBean.setSVR_DATE(outResBean.getBODY().getSVR_DATE());//核心日期
			BCK03870ResBodyBean.setCARD_DATE(outResBean.getBODY().getCARD_DATE());//卡日期
			BCK03870ResBodyBean.setCARD_JRNL_NO(outResBean.getBODY().getCARD_JRNL_NO());//卡流水号
			BCK03870ResBodyBean.setSVR_JRNL_NO(outResBean.getBODY().getSVR_JRNL_NO());//核心流水号
			BCK03870ResBodyBean.setACCT_NO(outResBean.getBODY().getACCT_NO());//账号
			BCK03870ResBodyBean.setTRN_RATE(outResBean.getBODY().getTRN_RATE());//开户利率
			BCK03870ResBodyBean.setSTART_DATE(outResBean.getBODY().getSTART_DATE());//起息日期
			BCK03870ResBodyBean.setEND_DATE(outResBean.getBODY().getEND_DATE());//到期日
			BCK03870ResBodyBean.setSUB_ACCT_NO2(outResBean.getBODY().getSUB_ACCT_NO2());//子账户
			BCK03870ResBodyBean.setITEM_NO(outResBean.getBODY().getITEM_NO());//科目
			BCK03870ResBodyBean.setDEP_TERM(outResBean.getBODY().getDEP_TERM());//存期
			BCK03870ResBodyBean.setOPEN_INST(outResBean.getBODY().getOPEN_INST());//开户机构
			BCK03870ResBodyBean.setCR_DB_FLAG(outResBean.getBODY().getCR_DB_FLAG());//借贷标志
			BCK03870ResBodyBean.setAREA_FLOAT_RET(outResBean.getBODY().getAREA_FLOAT_RET());//区域浮动利率
			BCK03870ResBodyBean.setCHL_FLOAT_RET(outResBean.getBODY().getCHL_FLOAT_RET());//渠道浮动利率
			BCK03870ResBodyBean.setBIRTH_FLOAT_RET(outResBean.getBODY().getBIRTH_FLOAT_RET());//生日浮动利率
			BCK03870ResBodyBean.setTIME_FLOAT_RET(outResBean.getBODY().getTIME_FLOAT_RET());//时间段浮动利率
			BCK03870ResBodyBean.setCOMB_FLOT_RET(outResBean.getBODY().getCOMB_FLOT_RET());//组合浮动利率
			BCK03870ResBodyBean.setFILE_NAME_RET(outResBean.getBODY().getFILE_NAME_RET());//浮动利率组合文件
			inResBean.setBody(BCK03870ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK03870ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}

	private Account03870ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Account03870ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Account03870ResBodyBean.class);
			
			return (Account03870ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Account03870ReqBean();
			Account03870ReqBodyBean accountBody = new Account03870ReqBodyBean();
			accountBody.setTRAN_CHANNEL(inReqBean.getBody().getTRAN_CHANNEL());//渠道号
			accountBody.setCARD_NO(inReqBean.getBody().getCARD_NO());//卡号
			accountBody.setID_TYPE(inReqBean.getBody().getID_TYPE());//证件代号
			accountBody.setID_NO(inReqBean.getBody().getID_NO());//证件号码
			accountBody.setCUST_NO(inReqBean.getBody().getCUST_NO());//客户号
			accountBody.setCURRENCY(inReqBean.getBody().getCURRENCY());//币种
			accountBody.setPRO_CODE(inReqBean.getBody().getPRO_CODE());//产品代码
			accountBody.setDEP_TERM(inReqBean.getBody().getDEP_TERM());//存期
			accountBody.setDEP_UNIT(inReqBean.getBody().getDEP_UNIT());//存期单位
			accountBody.setCUST_LEVEL(inReqBean.getBody().getCUST_LEVEL());//客户级别
			String password = inReqBean.getBody().getPASSWORD();
			//密码加密
			if(StringUtils.isNotBlank(password)){
				/*凭证密码加密处理*/
				String acctNo = inReqBean.getBody().getCARD_NO();//获取存单账号
//				logger.debug("*******存单账号："+acctNo);
				logger.info("加密前：   "+password);
				String tranPwd=EncryptUtils.tranPin310(mac.getWorkkeyflag(), mac.getKeyflag(), acctNo, password);
				logger.info("加密后：   "+tranPwd);
				logger.debug("*******转加密密文："+tranPwd);
				accountBody.setPASSWORD(tranPwd);
			}else{
				accountBody.setPASSWORD(password);
			}
			accountBody.setCAL_MODE(inReqBean.getBody().getCAL_MODE());//结算方式
			accountBody.setCASH_ANALY_NO(inReqBean.getBody().getCASH_ANALY_NO());//现金分析号
			accountBody.setOPPO_ACCT_NO(inReqBean.getBody().getOPPO_ACCT_NO());//对方账号
			accountBody.setAUTO_REDP_FLAG(inReqBean.getBody().getAUTO_REDP_FLAG());//自动转存标志
			accountBody.setLOAD_BAL(inReqBean.getBody().getLOAD_BAL());//存款金额
			accountBody.setHAV_AGENT_FLAG(inReqBean.getBody().getHAV_AGENT_FLAG());//是否有代理人标志
			accountBody.setRELA_ACCT_NO(inReqBean.getBody().getRELA_ACCT_NO());//关联账号、收益账号
			accountBody.setSUB_RELA_ACCT_NO(inReqBean.getBody().getSUB_RELA_ACCT_NO());//关联子账号
			accountBody.setCERT_PRINT(inReqBean.getBody().getCERT_PRINT());//是否打印字段
			accountBody.setCHL_ID(inReqBean.getBody().getCHL_ID());//渠道模块标识
			accountBody.setRELA_ACCT_NO(inReqBean.getBody().getRELA_ACCT_NO());//关联账号/收益账号
			if("0".equals(inReqBean.getBody().getHAV_AGENT_FLAG())){
				Account03870ReqAgentInfBean agentInfo = new Account03870ReqAgentInfBean();//代理人信息
				agentInfo.setCUST_NAME(inReqBean.getBody().getAGENT_INF().getCUST_NAME());//客户姓名
				agentInfo.setSEX(inReqBean.getBody().getAGENT_INF().getSEX());//性别
				agentInfo.setID_TYPE(inReqBean.getBody().getAGENT_INF().getID_TYPE());//证件类别
				agentInfo.setID_NO(inReqBean.getBody().getAGENT_INF().getID_NO());//证件号码
				agentInfo.setISSUE_DATE(inReqBean.getBody().getAGENT_INF().getISSUE_DATE());//签发日期
				agentInfo.setDUE_DATE(inReqBean.getBody().getAGENT_INF().getDUE_DATE());//到期日期
				agentInfo.setISSUE_INST(inReqBean.getBody().getAGENT_INF().getISSUE_INST());//签发机关
				agentInfo.setNATION(inReqBean.getBody().getAGENT_INF().getNATION());//国籍
				agentInfo.setOCCUPATION(inReqBean.getBody().getAGENT_INF().getOCCUPATION());//职业
				agentInfo.setREGIS_PER_RES(inReqBean.getBody().getAGENT_INF().getREGIS_PER_RES());//户口所在地
				agentInfo.setJ_C_ADD(inReqBean.getBody().getAGENT_INF().getJ_C_ADD());//经常居住地
				agentInfo.setTELEPHONE(inReqBean.getBody().getAGENT_INF().getTELEPHONE());//固定电话
				agentInfo.setMOB_PHONE(inReqBean.getBody().getAGENT_INF().getMOB_PHONE());//移动电话
				accountBody.setAGENT_INF(agentInfo);
			}
			outReqBean.setBODY(accountBody);
			OutHeadBean headBean = new OutHeadBean();
			headBean.setTRAN_CODE("03870");
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
			
			xs.alias("ROOT", Account03870ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Account03870ReqBodyBean.class);
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

	private BCK03870ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK03870ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK03870ReqBodyBean.class);
		
		return (BCK03870ReqBean)reqXs.fromXML(msg);
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
