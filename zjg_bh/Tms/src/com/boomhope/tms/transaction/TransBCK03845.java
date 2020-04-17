package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK03845ReqBean;
import com.boomhope.tms.message.account.in.BCK03845ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK03845ResBean;
import com.boomhope.tms.message.account.in.BCK03845ResBodyBean;
import com.boomhope.tms.message.account.out.Account03845ReqBean;
import com.boomhope.tms.message.account.out.Account03845ReqBodyBean;
import com.boomhope.tms.message.account.out.Account03845ResBean;
import com.boomhope.tms.message.account.out.Account03845ResBodyBean;
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

public class TransBCK03845 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK03845.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK03845ReqBean inReqBean;	// 渠道请求Bean
	BCK03845ResBean inResBean;	// 渠道响应Bean
	Account03845ReqBean	outReqBean;	// 前置请求Bean
	Account03845ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	TransFlow transFlow;	// 交易流水Bean
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK03845.getBean("TransFlowService");
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
			logger.info("向前置发送03845请求"+outReqMsg);
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			logger.info("接刷前置响应03845请求"+outReqMsg);
			
			//解析前置响应报文 
			outResBean = makeOutResBean(outResMsg);
			
			/* 更新流水信息 */
			updateTransFlow();
			
			//然后将Account03845ResBean类中的数据放入BCK03845ResBean中，返回
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
			transFlow.setTransCode("BCK_03845");
			transFlow.setTransName("回单机-卡信息查询");
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
		
		inResBean = new BCK03845ResBean();
		BCK03845ResBodyBean BCK03845ResBodyBean = new BCK03845ResBodyBean();
		
		inResBean.setBody(BCK03845ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03845ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK03845ResBean();
			BCK03845ResBodyBean BCK03845ResBodyBean = new BCK03845ResBodyBean();
			BCK03845ResBodyBean.setSVR_DATE(outResBean.getBODY().getSVR_DATE());//核心日期
			BCK03845ResBodyBean.setSVR_JRNL(outResBean.getBODY().getSVR_JRNL());//核心流水号
			BCK03845ResBodyBean.setITEM_NO(outResBean.getBODY().getITEM_NO());//科目号
			BCK03845ResBodyBean.setCARD_NO(outResBean.getBODY().getCARD_NO());//卡号
			BCK03845ResBodyBean.setCUST_NO(outResBean.getBODY().getCUST_NO());//客户号
			BCK03845ResBodyBean.setPRO_CODE(outResBean.getBODY().getPRO_CODE());//产品代码 
			BCK03845ResBodyBean.setCURREN(outResBean.getBODY().getCURREN());//币种
			BCK03845ResBodyBean.setOPEN_INST(outResBean.getBODY().getOPEN_INST());//开户机构
			BCK03845ResBodyBean.setOPEN_DATE(outResBean.getBODY().getOPEN_DATE());//开户日
			BCK03845ResBodyBean.setSTART_DATE(outResBean.getBODY().getSTART_DATE());//起息日
			BCK03845ResBodyBean.setEND_DATE(outResBean.getBODY().getEND_DATE());//到期日
			BCK03845ResBodyBean.setPAY_COND(outResBean.getBODY().getPAY_COND());//支付条件
			BCK03845ResBodyBean.setOPEN_TELLER(outResBean.getBODY().getOPEN_TELLER());//开户柜员
			BCK03845ResBodyBean.setBALANCE(outResBean.getBODY().getBALANCE());//结存额（主账户余额）
			BCK03845ResBodyBean.setPURSE(outResBean.getBODY().getPURSE());//钱包余额
			BCK03845ResBodyBean.setINTE_COLLECT(outResBean.getBODY().getINTE_COLLECT());//利息积数
			BCK03845ResBodyBean.setSTOP_AMT(outResBean.getBODY().getSTOP_AMT());//止付金额
			BCK03845ResBodyBean.setAVAL_BAL(outResBean.getBODY().getAVAL_BAL());//可用余额
			BCK03845ResBodyBean.setACCT_STAT(outResBean.getBODY().getACCT_STAT());//账号状态
			BCK03845ResBodyBean.setCARD_STAT(outResBean.getBODY().getCARD_STAT());//卡状态
			BCK03845ResBodyBean.setCOLD_AMT(outResBean.getBODY().getCOLD_AMT());//冻结金额
			BCK03845ResBodyBean.setID_TYPE(outResBean.getBODY().getID_TYPE());//证件类型
			BCK03845ResBodyBean.setID_NO(outResBean.getBODY().getID_NO());//证件号码
			BCK03845ResBodyBean.setACCT_NAME(outResBean.getBODY().getACCT_NAME());//户名
			BCK03845ResBodyBean.setCLEAN_TELLER(outResBean.getBODY().getCLEAN_TELLER());//清户柜员号
			BCK03845ResBodyBean.setCLEAN_INST(outResBean.getBODY().getCLEAN_INST());//清户机构
			BCK03845ResBodyBean.setCLEAN_RES(outResBean.getBODY().getCLEAN_RES());//清户原因
			BCK03845ResBodyBean.setID_INST(outResBean.getBODY().getID_INST());//发证机构
			BCK03845ResBodyBean.setADD_AMT(outResBean.getBODY().getADD_AMT());//现金累计金额
			BCK03845ResBodyBean.setZZ_AMT(outResBean.getBODY().getZZ_AMT());//转账累计金额
			BCK03845ResBodyBean.setCK_TOTAL_AMT(outResBean.getBODY().getCK_TOTAL_AMT());//存款累计金额
			BCK03845ResBodyBean.setOPEN_FLAG(outResBean.getBODY().getOPEN_FLAG());//约定转存标志
			BCK03845ResBodyBean.setSUM_BALANCE(outResBean.getBODY().getSUM_BALANCE());//总结存额
			BCK03845ResBodyBean.setDEP_TERM(outResBean.getBODY().getDEP_TERM());//存期
			BCK03845ResBodyBean.setOPEN_RATE(outResBean.getBODY().getOPEN_RATE());//开户利率
			BCK03845ResBodyBean.setBIND_ID(outResBean.getBODY().getBIND_ID());//绑定账户
			BCK03845ResBodyBean.setPRO_NAME(outResBean.getBODY().getPRO_NAME());//产品名称
			inResBean.setBody(BCK03845ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK03845ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}

	private Account03845ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Account03845ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Account03845ResBodyBean.class);
			
			return (Account03845ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Account03845ReqBean();
			Account03845ReqBodyBean accountBody = new Account03845ReqBodyBean();
			String card_NO = inReqBean.getBody().getCARD_NO();
			accountBody.setCARD_NO(card_NO);//账号
			String ispin = inReqBean.getBody().getISPIN();
			accountBody.setISPIN(ispin);//是否验密
			if("1".equals(ispin.trim())){
				/*凭证密码加密处理*/
				String pwd = inReqBean.getBody().getPASSWD();//获取终端密文密码
//				logger.debug("*******终端传送密文："+pwd);
				logger.info("加密前：   "+pwd);
				String tranPwd=EncryptUtils.tranPin310(mac.getWorkkeyflag(), mac.getKeyflag(), card_NO, pwd);
				logger.info("加密后：   "+tranPwd);
				logger.debug("*******转加密密文："+tranPwd);
				accountBody.setPASSWD(tranPwd);
			}else{
				accountBody.setPASSWD(inReqBean.getBody().getPASSWD());//密码
			}
			accountBody.setSUB_ACCT_NO(inReqBean.getBody().getSUB_ACCT_NO());//子账号
			outReqBean.setBODY(accountBody);
			OutHeadBean headBean = new OutHeadBean();
			headBean.setTRAN_CODE("03845");
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
			
			xs.alias("ROOT", Account03845ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Account03845ReqBodyBean.class);
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

	private BCK03845ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK03845ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK03845ReqBodyBean.class);
		
		return (BCK03845ReqBean)reqXs.fromXML(msg);
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
