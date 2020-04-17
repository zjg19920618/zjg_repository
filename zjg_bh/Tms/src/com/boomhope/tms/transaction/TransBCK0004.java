package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.BaseUnit;
import com.boomhope.tms.entity.BusFlow;
import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK0004ReqBean;
import com.boomhope.tms.message.in.bck.BCK0004ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0004ResBean;
import com.boomhope.tms.message.in.bck.BCK0004ResBodyBean;
import com.boomhope.tms.message.out.Out02026ReqAgentInfBean;
import com.boomhope.tms.message.out.Out02026ReqBean;
import com.boomhope.tms.message.out.Out02026ReqBodyBean;
import com.boomhope.tms.message.out.Out02026ResBean;
import com.boomhope.tms.message.out.Out02026ResBodyBean;
import com.boomhope.tms.message.out.OutHeadBean;
import com.boomhope.tms.message.out.OutResponseBean;
import com.boomhope.tms.service.IDeployMachineService;
import com.boomhope.tms.service.IFlowService;
import com.boomhope.tms.service.ITransFlowService;
import com.boomhope.tms.socket.SocketClient;
import com.boomhope.tms.util.DateUtil;
import com.boomhope.tms.util.EncryptUtils;
import com.boomhope.tms.util.MACProtocolUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class TransBCK0004 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK0004.class);
	ITransFlowService transFlowService;	// 交易流水服务

	IFlowService flowService;   //业务流水服务

	IDeployMachineService deployMachineService = null;

	BCK0004ReqBean inReqBean;	// 渠道请求Bean
	BCK0004ResBean inResBean;	// 渠道响应Bean
	Out02026ReqBean	outReqBean;	// 前置请求Bean
	Out02026ResBean	outResBean;	// 前置响应Bean

	BusFlow busFlow;        //业务流水Bean

	

	DeployMachine mac = null;
	TransFlow transFlow;	// 交易流水Bean
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK0004.getBean("TransFlowService");
			deployMachineService = (IDeployMachineService) TransTms0001.getBean("deployMachineService");
			flowService = (IFlowService) TransBCK0004.getBean("flowService");
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
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			logger.info("向前置发送07624请求"+outReqMsg);
			//解析前置响应报文 
			outResBean = makeOutResBean(outResMsg);
			logger.info("接收置响应07624请求"+outReqMsg);
			
			/* 更新流水信息 */
			updateTransFlow();

			String resMsg = makeResMsg();
			String resCode = inResBean.getHeadBean().getResCode();
			if("000000".equals(resCode)){
				/*记录业务流水信息 */
				addBusFlow();
			}
			//返回信息
			return resMsg;
		} catch (Exception e) {
			return makeResErrorMsg(e.getMessage(),null);
		}
		
	}
	//增加业务流水信息
	private void addBusFlow() throws Exception {
		try {
			busFlow = new BusFlow();
			DeployMachine deployMachine = new DeployMachine();
			deployMachine.setMachineNo(mac.getMachineNo().trim());//终端编号
			busFlow.setDeployMachine(deployMachine);
			BaseUnit baseUnit = new BaseUnit();
			baseUnit.setUnitCode(mac.getBaseUnit().getUnitCode().trim());//机构代码
			busFlow.setBaseUnit(baseUnit);
			busFlow.setProjectName(inReqBean.getBody().getPROD_NAME().trim());//产品名称
			busFlow.setProjectCode(inReqBean.getBody().getPROD_CODE().trim());//产品编号
			busFlow.setBillNo(inReqBean.getBody().getACCT_NO().trim());//存单账号
			busFlow.setCertNo(inReqBean.getBody().getCERT_NO().trim());//凭证号
			busFlow.setRealPri(outResBean.getBODY().getREAL_PRI().trim());//实付本金
			busFlow.setRealInte(outResBean.getBODY().getREAL_INTE().trim());//实付利息
			busFlow.setCanelType(inReqBean.getBody().getCANEL_TYPE().trim());//销户类型
			busFlow.setBankCardNo(inReqBean.getBody().getOPPO_ACCT_NO().trim());//转入卡号
			busFlow.setBankCardName(inReqBean.getBody().getOPPO_NAME().trim());//转入卡姓名
			busFlow.setCreateDate(DateUtil.getDateTime("yyyyMMddHHmmss"));//创建时间
			busFlow.setCheckStatus(inReqBean.getBody().getCHECKSTATUS().trim());//创建时间
			
			flowService.addBusFlow(busFlow);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception("接收业务请求入库失败",e);
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
			transFlow.setTransCode("BCK_0004");
			transFlow.setTransName("回单机-个人账户销户");
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
				
		inResBean = new BCK0004ResBean();
		BCK0004ResBodyBean bck0004ResBodyBean = new BCK0004ResBodyBean();
		
		inResBean.setBody(bck0004ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK0004ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK0004ResBean();
			BCK0004ResBodyBean bck0004ResBodyBean = new BCK0004ResBodyBean();
			bck0004ResBodyBean.setBALANCE(outResBean.getBODY().getBALANCE());
			bck0004ResBodyBean.setCR_DB_FLAG(outResBean.getBODY().getCR_DB_FLAG());
			bck0004ResBodyBean.setDEP_DAYS(outResBean.getBODY().getDEP_DAYS());
			bck0004ResBodyBean.setDUE_DATE(outResBean.getBODY().getDUE_DATE());
			bck0004ResBodyBean.setFILE_NAME(outResBean.getBODY().getFILE_NAME());
			bck0004ResBodyBean.setINTE_AMT(outResBean.getBODY().getINTE_AMT());
			bck0004ResBodyBean.setITEM_NO(outResBean.getBODY().getITEM_NO());
			bck0004ResBodyBean.setOPEN_INST(outResBean.getBODY().getOPEN_INST());
			bck0004ResBodyBean.setOPP_ACCT_NAME(outResBean.getBODY().getOPP_ACCT_NAME());
			bck0004ResBodyBean.setOPP_ACCT_NO(outResBean.getBODY().getOPP_ACCT_NO());
			bck0004ResBodyBean.setOPP_ITEM_NO(outResBean.getBODY().getOPP_ITEM_NO());
			bck0004ResBodyBean.setOPP_OPEN_INST(outResBean.getBODY().getOPP_OPEN_INST());
			bck0004ResBodyBean.setRATE(outResBean.getBODY().getRATE());
			bck0004ResBodyBean.setRATE_TAX(outResBean.getBODY().getRATE_TAX());
			bck0004ResBodyBean.setREAL_INTE(outResBean.getBODY().getREAL_INTE());
			bck0004ResBodyBean.setRATE_ITEM(outResBean.getBODY().getRATE_ITEM());
			bck0004ResBodyBean.setREAL_PRI(outResBean.getBODY().getREAL_PRI());
			bck0004ResBodyBean.setSTART_INT_DATE(outResBean.getBODY().getSTART_INT_DATE());
			bck0004ResBodyBean.setSUBSIDY_AMT(outResBean.getBODY().getSUBSIDY_AMT());
			bck0004ResBodyBean.setSVR_DATE(outResBean.getBODY().getSVR_DATE());
			bck0004ResBodyBean.setSVR_JRNL_NO(outResBean.getBODY().getSVR_JRNL_NO());
			bck0004ResBodyBean.setTAX_RATE(outResBean.getBODY().getTAX_RATE());
			bck0004ResBodyBean.setTRAN_AMT(outResBean.getBODY().getTRAN_AMT());
			bck0004ResBodyBean.setADVN_INIT(outResBean.getBODY().getADVN_INIT());
			bck0004ResBodyBean.setXFD_COUNT(outResBean.getBODY().getXFD_COUNT());
			bck0004ResBodyBean.setXFD_AMT(outResBean.getBODY().getXFD_AMT());
			inResBean.setBody(bck0004ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK0004ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}

	private Out02026ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Out02026ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out02026ResBodyBean.class);
			
			return (Out02026ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out02026ReqBean();
			Out02026ReqBodyBean outBody = new Out02026ReqBodyBean();
			outBody.setACCT_NO(inReqBean.getBody().getACCT_NO());//账号
			outBody.setCERT_NO(inReqBean.getBody().getCERT_NO());//凭证号
			outBody.setCUST_NO(inReqBean.getBody().getCUST_NO());//客户号
			outBody.setCUST_NAME(inReqBean.getBody().getCUST_NAME());//客户名称
			outBody.setCURRENCY_TYPE(inReqBean.getBody().getCURRENCY_TYPE());//客户名称
			outBody.setDRAW_COND(inReqBean.getBody().getDRAW_COND());//支付条件(0-无;1-凭密码;2-凭证件;3-凭印鉴;4_凭印鉴和密码;3、4需要电子验印)
			if(inReqBean.getBody().getDRAW_COND().equals("1")){
				/*凭证密码加密处理*/
				String pwd = inReqBean.getBody().getPASSWORD();//获取终端密文密码
//				logger.debug("*******终端传送密文："+pwd);
				String acctNo = inReqBean.getBody().getACCT_NO();//获取存单账号
//				logger.debug("*******存单账号："+acctNo);
				logger.info("加密前：   "+pwd);
				String tranPwd=EncryptUtils.tranPin310(mac.getWorkkeyflag(), mac.getKeyflag(), acctNo, pwd);
				logger.info("加密后：   "+tranPwd);
				logger.debug("*******转加密密文："+tranPwd);
				outBody.setPASSWORD(tranPwd);//有凭证密码
			}else{
				outBody.setPASSWORD(inReqBean.getBody().getPASSWORD());//无凭证密码
			}
			outBody.setCURRENCY(inReqBean.getBody().getCURRENCY());//货币号
			outBody.setPROD_CODE(inReqBean.getBody().getPROD_CODE());//产品代码
			outBody.setPROD_NAME(inReqBean.getBody().getPROD_NAME());//产品名称
			outBody.setBALANCE(inReqBean.getBody().getBALANCE());//余额
			outBody.setDEP_TERM(inReqBean.getBody().getDEP_TERM());//存期
			outBody.setSTART_INT_DATE(inReqBean.getBody().getSTART_INT_DATE());//起息日
			outBody.setOPEN_RATE(inReqBean.getBody().getOPEN_RATE());//开户利率
			outBody.setCYC_AMT(inReqBean.getBody().getCYC_AMT());//周期金额
			outBody.setCYC(inReqBean.getBody().getCYC());//周期
			outBody.setTIMES(inReqBean.getBody().getTIMES());//次数
			outBody.setBES_AMT(inReqBean.getBody().getBES_AMT());//预约金额
			outBody.setBES_DATE(inReqBean.getBody().getBES_DATE());//预约日期
			outBody.setDRAW_CURRENCY(inReqBean.getBody().getDRAW_CURRENCY());//支取币种
			outBody.setPRI_INTE_FLAG(inReqBean.getBody().getPRI_INTE_FLAG());//本息分开（必输，0否、1是）
			outBody.setCANCEL_AMT(inReqBean.getBody().getCANCEL_AMT());//销户金额
			outBody.setPRI_INTE_WAY(inReqBean.getBody().getPRI_INTE_WAY());//本息走向
			outBody.setOPPO_ACCT_NO(inReqBean.getBody().getOPPO_ACCT_NO());//对方账号（本息转账，有）
			outBody.setSUB_ACCT_NO(inReqBean.getBody().getSUB_ACCT_NO());//子账号（必输项，对方账号为一本通使显示，默认为0基本账户可修改）
			outBody.setPRI_WAY(inReqBean.getBody().getPRI_INTE_WAY());//本金走向
			outBody.setOPPO_ACCT_NO1(inReqBean.getBody().getOPPO_ACCT_NO1());//对方账号（本金转账，本金、利息分开时，对方账户不允许为同一账户）
			outBody.setSUB_ACCT_NO1(inReqBean.getBody().getSUB_ACCT_NO1());//子账号（必输项，对方账号为一本通使显示，默认为0基本账户可修改）
			outBody.setINTE_WAY(inReqBean.getBody().getINTE_WAY());//利息走向
			outBody.setOPPO_ACCT_NO2(inReqBean.getBody().getOPPO_ACCT_NO2());//对方账号（利息转账，有）
			outBody.setSUB_ACCT_NO2(inReqBean.getBody().getSUB_ACCT_NO2());//子账号（必输项，对方账号为一本通使显示，默认为0基本账户可修改）
			outBody.setID_TYPE(inReqBean.getBody().getID_TYPE());//证件类型
			outBody.setID_NO(inReqBean.getBody().getID_NO());//证件号码
			outBody.setPROVE_FLAG(inReqBean.getBody().getPROVE_FLAG());//证明标志
			outBody.setCASH_ANALY_NO(inReqBean.getBody().getCASH_ANALY_NO());//现金分析号
			outBody.setHAV_AGENT_FLAG(inReqBean.getBody().getHAV_AGENT_FLAG());//是否有代理人标志
			if(inReqBean.getBody().getHAV_AGENT_FLAG().equals("1")){
				//拼接输出的AGENT_INF
				Out02026ReqAgentInfBean outAgentInf = new Out02026ReqAgentInfBean();
				outAgentInf.setCUST_NAME(inReqBean.getBody().getAGENT_INF().getCUST_NAME());//客户姓名
				outAgentInf.setDUE_DATE(inReqBean.getBody().getAGENT_INF().getDUE_DATE());//到期日期
				outAgentInf.setID_NO(inReqBean.getBody().getAGENT_INF().getID_NO());//证件号码
				outAgentInf.setID_TYPE(inReqBean.getBody().getAGENT_INF().getID_TYPE());//证件类别
				outAgentInf.setISSUE_DATE(inReqBean.getBody().getAGENT_INF().getISSUE_DATE());//签发日期
				outAgentInf.setISSUE_INST(inReqBean.getBody().getAGENT_INF().getISSUE_INST());//签发机关
				outAgentInf.setJ_C_ADD(inReqBean.getBody().getAGENT_INF().getJ_C_ADD());//经常居住地
				outAgentInf.setMOB_PHONE(inReqBean.getBody().getAGENT_INF().getMOB_PHONE());//移动电话
				outAgentInf.setNATION(inReqBean.getBody().getAGENT_INF().getNATION());//国籍
				outAgentInf.setOCCUPATION(inReqBean.getBody().getAGENT_INF().getOCCUPATION());//职业
				outAgentInf.setREGIS_PER_RES(inReqBean.getBody().getAGENT_INF().getREGIS_PER_RES());//户口所在地
				outAgentInf.setSEX(inReqBean.getBody().getAGENT_INF().getSEX());//性别
				outAgentInf.setTELEPHONE(inReqBean.getBody().getAGENT_INF().getTELEPHONE());//固定电话
				
				outBody.setAGENT_INF(outAgentInf);
			}
			
			
			//拼接输出的head
			OutHeadBean outHeadBean = new OutHeadBean();
			outHeadBean.setTRAN_CODE("07624");//前置交易码
			outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
			outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));//交易时间
			outHeadBean.setCARD_ACCP_TERM_ID(inReqBean.getHeadBean().getMachineNo());//自助设备编号
			outHeadBean.setMER_NO(mac.getMerNo());//商户号
			outHeadBean.setINST_NO(inReqBean.getHeadBean().getBranchNo());//机构号
			outHeadBean.setTELLER_NO(mac.getTellerNo());// 柜员号
			outHeadBean.setCHANNEL(mac.getChannel());// 渠道号
			outReqBean.setBODY(outBody);
			outReqBean.setHeadBean(outHeadBean);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("ROOT", Out02026ReqBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("BODY", Out02026ReqBodyBean.class);
			
			String xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+reqXs.toXML(outReqBean);
			logger.info("发送前置报文：\n"+xml);
			byte[] bbb = xml.getBytes("GBK");
			return MACProtocolUtils.toRequest(mac.getMackeyflag(), bbb);
		} catch (Exception e) {
			logger.error("生成前置请求报文异常",e);
			throw new Exception("生成前置请求报文异常", e);
		}
		
	}

	private BCK0004ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK0004ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK0004ReqBodyBean.class);
		
		return (BCK0004ReqBean)reqXs.fromXML(msg);
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
