package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK0014ReqBean;
import com.boomhope.tms.message.in.bck.BCK0014ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0014ResBean;
import com.boomhope.tms.message.in.bck.BCK0014ResBodyBean;
import com.boomhope.tms.message.out.Out02882ReqBean;
import com.boomhope.tms.message.out.Out02882ReqBodyBean;
import com.boomhope.tms.message.out.Out02882ResBean;
import com.boomhope.tms.message.out.Out02882ResBodyBean;
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

public class TransBCK0014 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK0014.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK0014ReqBean inReqBean;	// 渠道请求Bean
	BCK0014ResBean inResBean;	// 渠道响应Bean
	Out02882ReqBean	outReqBean;	// 前置请求Bean
	Out02882ResBean	outResBean;	// 前置响应Bean
	TransFlow transFlow;	// 交易流水Bean
	DeployMachine mac = null;
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK0014.getBean("TransFlowService");
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
			logger.info("向前置发送02791请求"+outReqMsg);
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			logger.info("接收前置02791响应"+outResMsg);
			
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
			transFlow.setTransCode("BCK_0014");
			transFlow.setTransName("回单机-凭证信息综合查询");
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
				
		inResBean = new BCK0014ResBean();
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK0014ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK0014ResBean();
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			BCK0014ResBodyBean bck0014ResBodyBean = new BCK0014ResBodyBean();
			/*bck0014ResBodyBean.setCustName(outResBean.getBODY().getCUST_NAME());
			bck0014ResBodyBean.setAcctNo(outResBean.getBODY().getACCT_NO());
			bck0014ResBodyBean.setCertNo(outResBean.getBODY().getCERT_NO());
			bck0014ResBodyBean.setProdName(outResBean.getBODY().getPROD_NAME());
			bck0014ResBodyBean.setOpenAmt(outResBean.getBODY().getOPEN_AMT());
			bck0014ResBodyBean.setDrawCond(outResBean.getBODY().getDRAW_COND());
			bck0014ResBodyBean.setOpenDate(outResBean.getBODY().getOPEN_DATE());
			bck0014ResBodyBean.setStartIntDate(outResBean.getBODY().getSTART_INT_DATE());
			bck0014ResBodyBean.setAcctStat(outResBean.getBODY().getACCT_STAT());
			bck0014ResBodyBean.setEndDate(outResBean.getBODY().getEND_DATE());
			bck0014ResBodyBean.setIdType(outResBean.getBODY().getID_TYPE());
			bck0014ResBodyBean.setIdNo(outResBean.getBODY().getID_NO());
			bck0014ResBodyBean.setExchFlag(outResBean.getBODY().getEXCH_FLAG());
			bck0014ResBodyBean.setSvrDate(outResBean.getBODY().getSVR_DATE());
			bck0014ResBodyBean.setOpenQlt(outResBean.getBODY().getOPEN_QLT());*/
			
			bck0014ResBodyBean.setSvrDate(outResBean.getBODY().getSVR_DATE());//核心日期
			bck0014ResBodyBean.setSvrJrnlNo(outResBean.getBODY().getSVR_JRNL_NO());//核心流水号
			bck0014ResBodyBean.setAcctType(outResBean.getBODY().getACCT_TYPE());//账号种类
			bck0014ResBodyBean.setCustType(outResBean.getBODY().getCUST_TYPE());//客户种类
			bck0014ResBodyBean.setTermFlag(outResBean.getBODY().getTERM_FLAG());//定活性质
			bck0014ResBodyBean.setItemNo(outResBean.getBODY().getITEM_NO());//科目号
			bck0014ResBodyBean.setAcctNo(outResBean.getBODY().getACCT_NO());//账号 
			bck0014ResBodyBean.setProdCode(outResBean.getBODY().getPROD_CODE());//产品代码 
			bck0014ResBodyBean.setCurrency(outResBean.getBODY().getCURRENCY());//币种
			bck0014ResBodyBean.setCertNo(outResBean.getBODY().getCERT_NO());//凭证号
			bck0014ResBodyBean.setCertUseDate(outResBean.getBODY().getCERT_USE_DATE());//凭证使用日期
			bck0014ResBodyBean.setOpenInst_No(outResBean.getBODY().getOPEN_INST_NO());//开户机构
			bck0014ResBodyBean.setOpenAmt(outResBean.getBODY().getOPEN_AMT());//开户金额
			bck0014ResBodyBean.setOpenDate(outResBean.getBODY().getOPEN_DATE());//开户日
			bck0014ResBodyBean.setStartIntDate(outResBean.getBODY().getSTART_INT_DATE());//起息日
			bck0014ResBodyBean.setDepTerm(outResBean.getBODY().getDEP_TERM());//存期
			bck0014ResBodyBean.setEndDate(outResBean.getBODY().getEND_DATE());//到期日
			bck0014ResBodyBean.setRate(outResBean.getBODY().getRATE());//开户利率
			bck0014ResBodyBean.setOpenQlt(outResBean.getBODY().getOPEN_QLT());//开户性质
			bck0014ResBodyBean.setOrigAcctNo(outResBean.getBODY().getORIG_ACCT_NO());//原账号
			bck0014ResBodyBean.setExchFlag(outResBean.getBODY().getEXCH_FLAG());//自动转存标志
			bck0014ResBodyBean.setDrawCond(outResBean.getBODY().getDRAW_COND());//支付条件
			bck0014ResBodyBean.setOpenTeller(outResBean.getBODY().getOPEN_TELLER());//开户柜员
			bck0014ResBodyBean.setEndAmt(outResBean.getBODY().getEND_AMT());//结存额
			bck0014ResBodyBean.setUseAmt(outResBean.getBODY().getUSE_AMT());//可用余额
			bck0014ResBodyBean.setTotalAmt(outResBean.getBODY().getTOTAL_AMT());//存折余额
			bck0014ResBodyBean.setInteCollect(outResBean.getBODY().getINTE_COLLECT());//利息积数
			bck0014ResBodyBean.setTimes(outResBean.getBODY().getTIMES());//次数
			bck0014ResBodyBean.setMaxSub(outResBean.getBODY().getMAX_SUB());//最大序号
			bck0014ResBodyBean.setPrtedSub(outResBean.getBODY().getPRTED_SUB());//已打序号
			bck0014ResBodyBean.setBookAdd(outResBean.getBODY().getBOOK_ADD());//存折位置
			bck0014ResBodyBean.setStopAmt(outResBean.getBODY().getSTOP_AMT());//止付金额
			bck0014ResBodyBean.setUnUseAmt(outResBean.getBODY().getUN_USE_AMT());//不可用金额
			bck0014ResBodyBean.setFreezeAmt(outResBean.getBODY().getFREEZE_AMT());//冻结金额
			bck0014ResBodyBean.setHoldAmt(outResBean.getBODY().getHOLD_AMT());//圈存金额
			bck0014ResBodyBean.setSubAcctXjAmt(outResBean.getBODY().getSUB_ACCT_XJ_AMT());//子帐号现金累计取款
			bck0014ResBodyBean.setSubAcctZzAmt(outResBean.getBODY().getSUB_ACCT_ZZ_AMT());//子帐号转帐累计取款
			bck0014ResBodyBean.setSubAcctCashAmt(outResBean.getBODY().getSUB_ACCT_CASH_AMT());//子帐号现金存款
			bck0014ResBodyBean.setSubAcctExchgAmt(outResBean.getBODY().getSUB_ACCT_EXCHG_AMT());//子帐号转帐存款
			bck0014ResBodyBean.setAcctStat(outResBean.getBODY().getACCT_STAT());//账号状态
			bck0014ResBodyBean.setClearDate(outResBean.getBODY().getCLEAR_DATE());//结清日期
			bck0014ResBodyBean.setClearTeller(outResBean.getBODY().getCLEAR_TELLER());//清户柜员号
			bck0014ResBodyBean.setClearInst(outResBean.getBODY().getCLEAR_INST());//清户机构
			bck0014ResBodyBean.setClearReason(outResBean.getBODY().getCLEAR_REASON());//清户原因
			bck0014ResBodyBean.setAcctQlt(outResBean.getBODY().getACCT_QLT());//账号性质
			bck0014ResBodyBean.setOthOpenAcct(outResBean.getBODY().getOTH_OPEN_ACCT());//另开新户账号
			bck0014ResBodyBean.setPrlAcct(outResBean.getBODY().getPRL_ACCT());//协议户账号
			bck0014ResBodyBean.setLeaveAmt(outResBean.getBODY().getLEAVE_AMT());//留存额度
			bck0014ResBodyBean.setLimit(outResBean.getBODY().getLIMIT());//收付限制 
			bck0014ResBodyBean.setAcctType2(outResBean.getBODY().getACCT_TYPE2());//账户种类
			bck0014ResBodyBean.setClearMan(outResBean.getBODY().getCLEAR_MAN());//发放授权人
			bck0014ResBodyBean.setClearRate(outResBean.getBODY().getCLEAR_RATE());//清户利率
			bck0014ResBodyBean.setRound(outResBean.getBODY().getROUND());//周期
			bck0014ResBodyBean.setRoundAmt(outResBean.getBODY().getROUND_AMT());//周期金额
			bck0014ResBodyBean.setFgtInout(outResBean.getBODY().getFGT_INOUT());//是否漏存取
			bck0014ResBodyBean.setPreDate(outResBean.getBODY().getPRE_DATE());//预约日期
			bck0014ResBodyBean.setPreAmt(outResBean.getBODY().getPRE_AMT());//预约金额
			bck0014ResBodyBean.setBalDirect(outResBean.getBODY().getBAL_DIRECT());//余额方向
			bck0014ResBodyBean.setCustName1(outResBean.getBODY().getCUST_NAME1());//客户名称
			bck0014ResBodyBean.setInOutType(outResBean.getBODY().getIN_OUT_TYPE());//表内/表外标志
			bck0014ResBodyBean.setLongFlag(outResBean.getBODY().getLONG_FLAG());//是否久悬户
			bck0014ResBodyBean.setBillNo(outResBean.getBODY().getBILL_NO());//借据号
			bck0014ResBodyBean.setDealAcct(outResBean.getBODY().getDEAL_ACCT());//结算账号
			bck0014ResBodyBean.setAddLimit(outResBean.getBODY().getADD_LIMIT());//累计贷款限额
			bck0014ResBodyBean.setBackDate(outResBean.getBODY().getBACK_DATE());//还清日期
			bck0014ResBodyBean.setRateNo(outResBean.getBODY().getRATE_NO());//基准利率代号
			bck0014ResBodyBean.setInAcctNo(outResBean.getBODY().getIN_ACCT_NO());//表内挂息户
			bck0014ResBodyBean.setOutAcctNo(outResBean.getBODY().getOUT_ACCT_NO());//表外挂息户
			bck0014ResBodyBean.setContNo(outResBean.getBODY().getCONT_NO());//合同号
			bck0014ResBodyBean.setCollDate(outResBean.getBODY().getCOLL_DATE());//积数日期
			bck0014ResBodyBean.setRateFloat(outResBean.getBODY().getRATE_FLOAT());//利率浮动值
			bck0014ResBodyBean.setDueFloat(outResBean.getBODY().getDUE_FLOAT());//逾期浮动值
			bck0014ResBodyBean.setLastDate(outResBean.getBODY().getLAST_DATE());//上次结息日期
			bck0014ResBodyBean.setChangeDate(outResBean.getBODY().getCHANGE_DATE());//变动日期
			bck0014ResBodyBean.setAddOutAmt(outResBean.getBODY().getADD_OUT_AMT());//累计发放
			bck0014ResBodyBean.setAddBackAmt(outResBean.getBODY().getADD_BACK_AMT());//累计收回
			bck0014ResBodyBean.setProAcct(outResBean.getBODY().getPRO_ACCT());//保证账号
			bck0014ResBodyBean.setTrustAcct(outResBean.getBODY().getTRUST_ACCT());//委托账号
			bck0014ResBodyBean.setFlag(outResBean.getBODY().getFLAG());//标志
			bck0014ResBodyBean.setInnAmt(outResBean.getBODY().getINN_AMT());//复息余额
			bck0014ResBodyBean.setSbackDate(outResBean.getBODY().getSBACK_DATE());//应还日期
			bck0014ResBodyBean.setBalance(outResBean.getBODY().getBALANCE());//本金余额
			bck0014ResBodyBean.setInnBalance(outResBean.getBODY().getINN_BALANCE());//利息余额
			bck0014ResBodyBean.setThisAmt(outResBean.getBODY().getTHIS_AMT());//本次本金
			bck0014ResBodyBean.setThisInne(outResBean.getBODY().getTHIS_INNE());//本次利息
			bck0014ResBodyBean.setThisDinne(outResBean.getBODY().getTHIS_DINNE());//本次复息
			bck0014ResBodyBean.setCrDate(outResBean.getBODY().getCR_DATE());//贷款日
			bck0014ResBodyBean.setSettAcctFlag(outResBean.getBODY().getSETT_ACCT_FLAG());//是否结算户
			bck0014ResBodyBean.setPayTimes(outResBean.getBODY().getPAY_TIMES());//已部提次数
			bck0014ResBodyBean.setOpenNature(outResBean.getBODY().getOPEN_NATURE());//卡本通开户性质
			bck0014ResBodyBean.setOpenChnal(outResBean.getBODY().getOPEN_CHNAL());//开户渠道
			bck0014ResBodyBean.setProdName(outResBean.getBODY().getPROD_NAME());//产品名称
			bck0014ResBodyBean.setCustNo(outResBean.getBODY().getCUST_NO());//客户号
			bck0014ResBodyBean.setCustName(outResBean.getBODY().getCUST_NAME());//客户名称
			bck0014ResBodyBean.setIdType(outResBean.getBODY().getID_TYPE());//证件类别
			bck0014ResBodyBean.setIdNo(outResBean.getBODY().getID_NO());//证件号
			bck0014ResBodyBean.setIdInst(outResBean.getBODY().getID_INST());//发证机关
			bck0014ResBodyBean.setPhoneNo(outResBean.getBODY().getPHONE_NO());//电话号码
			bck0014ResBodyBean.setAddress(outResBean.getBODY().getADDRESS());//地址
			bck0014ResBodyBean.setGroupInst(outResBean.getBODY().getGROUP_INST());//组织机构代码
			bck0014ResBodyBean.setGsdjId(outResBean.getBODY().getGSDJ_ID());//国税登记证号
			bck0014ResBodyBean.setDsdjId(outResBean.getBODY().getDSDJ_ID());//地税登记证号
			
			inResBean.setBody(bck0014ResBodyBean);
			
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			reqXs.alias("Root", BCK0014ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}

	private Out02882ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Out02882ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out02882ResBodyBean.class);
			
			return (Out02882ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out02882ReqBean();
			Out02882ReqBodyBean out02882ReqBodyBean = new Out02882ReqBodyBean();
			out02882ReqBodyBean.setCERT_NO(inReqBean.getBody().getCertNo()); //凭证号
			out02882ReqBodyBean.setCERT_TYPE(inReqBean.getBody().getCertType()); //凭证种类
			outReqBean.setBODY(out02882ReqBodyBean);
			OutHeadBean outHeadBean = new OutHeadBean();
			outHeadBean.setTRAN_CODE("02791");
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
			
			xs.alias("ROOT", Out02882ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out02882ReqBodyBean.class);
			String xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xs.toXML(outReqBean);
			logger.info("发送前置报文：\n"+xml);
			byte[] bbb = xml.getBytes("GBK");
			return MACProtocolUtils.toRequest(mac.getMackeyflag(), bbb);
		} catch (Exception e) {
			logger.error("生成前置请求报文异常",e);
			throw new Exception("生成前置请求报文异常", e);
		}
		
	}

	private BCK0014ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK0014ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK0014ReqBodyBean.class);
		
		return (BCK0014ReqBean)reqXs.fromXML(msg);
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
