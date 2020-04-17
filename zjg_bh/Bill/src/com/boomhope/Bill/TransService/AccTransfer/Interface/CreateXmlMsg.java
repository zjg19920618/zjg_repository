package com.boomhope.Bill.TransService.AccTransfer.Interface;

import java.util.Map;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK03845ReqBean;
import com.boomhope.tms.message.account.in.BCK03845ReqBodyBean;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.bck.BCK01118ReqBean;
import com.boomhope.tms.message.in.bck.BCK01118ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK01569ReqBean;
import com.boomhope.tms.message.in.bck.BCK01569ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK01597ReqBean;
import com.boomhope.tms.message.in.bck.BCK01597ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK02013ReqBean;
import com.boomhope.tms.message.in.bck.BCK02013ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK02224ReqBean;
import com.boomhope.tms.message.in.bck.BCK02224ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK02224ReqBodyDetailBean;
import com.boomhope.tms.message.in.bck.BCK02600ReqBean;
import com.boomhope.tms.message.in.bck.BCK02600ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK02600ReqBodyDetailBean;
import com.boomhope.tms.message.in.bck.BCK02693ReqBean;
import com.boomhope.tms.message.in.bck.BCK02693ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK02781ReqBean;
import com.boomhope.tms.message.in.bck.BCK02781ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK02954ReqBean;
import com.boomhope.tms.message.in.bck.BCK02954ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK02956ReqBean;
import com.boomhope.tms.message.in.bck.BCK02956ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03445ReqBean;
import com.boomhope.tms.message.in.bck.BCK03445ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03453ReqBean;
import com.boomhope.tms.message.in.bck.BCK03453ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03521ReqBean;
import com.boomhope.tms.message.in.bck.BCK03521ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03740ReqBean;
import com.boomhope.tms.message.in.bck.BCK03740ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07492ReqBean;
import com.boomhope.tms.message.in.bck.BCK07492ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07494ReqBean;
import com.boomhope.tms.message.in.bck.BCK07494ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07495ReqBean;
import com.boomhope.tms.message.in.bck.BCK07495ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07496ReqBean;
import com.boomhope.tms.message.in.bck.BCK07496ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07602ReqBean;
import com.boomhope.tms.message.in.bck.BCK07602ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07659ReqBean;
import com.boomhope.tms.message.in.bck.BCK07659ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07660ReqBean;
import com.boomhope.tms.message.in.bck.BCK07660ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07670ReqBean;
import com.boomhope.tms.message.in.bck.BCK07670ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCKCM021ReqBean;
import com.boomhope.tms.message.in.bck.BCKCM021ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCKCM022ReqBean;
import com.boomhope.tms.message.in.bck.BCKCM022ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCKQRY00ReqBean;
import com.boomhope.tms.message.in.bck.BCKQRY00ReqBodyBean;
import com.boomhope.tms.message.in.tms.Tms0008ReqBean;
import com.boomhope.tms.message.in.tms.Tms0008ReqBodyBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class CreateXmlMsg {
	/**
	 * 请求头信息 
	 * @param tranCoede
	 * @return
	 */
	public static InReqHeadBean getInReqHeadBean(String tranCoede){
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode(tranCoede);//前置交易码
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);// 设备编号
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);// 机构号
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);// 机构号
		inReqHeadBean.setSupTellerNo(GlobalParameter.supTellerNo);// 授权柜员号
		return inReqHeadBean;
		
	}
	
	/**
	 * 查询灰名单
	 * @param map
	 * @return
	 */
	public static String BCK_01597(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK01597ReqBean bck01597ReqBean = new BCK01597ReqBean();
		//请求报文体
		BCK01597ReqBodyBean bck01597ReqBodyBean = new BCK01597ReqBodyBean();
		bck01597ReqBodyBean.setACC_NAME1(map.get("ACC_NAME1"));//出账户名
		bck01597ReqBodyBean.setACC_NAME2(map.get("ACC_NAME2"));//入账户名
		bck01597ReqBodyBean.setACC_NO1(map.get("ACC_NO1"));//出账账号
		bck01597ReqBodyBean.setACC_NO2(map.get("ACC_NO2"));//入账账号
		bck01597ReqBodyBean.setBANK_ID1(map.get("BANK_ID1"));//出账支付行号
		bck01597ReqBodyBean.setBANK_ID2(map.get("BANK_ID2"));//入账支付行号
		bck01597ReqBodyBean.setCARD_NO1(map.get("CARD_NO1"));//出账卡号
		bck01597ReqBodyBean.setCARD_NO2(map.get("CARD_NO2"));//入账卡号
		bck01597ReqBodyBean.setCCY(map.get("CCY"));//币种
		bck01597ReqBodyBean.setID_NAME1(map.get("ID_NAME1"));//出账客户证件姓名
		bck01597ReqBodyBean.setID_NAME2(map.get("ID_NAME2"));//入账客户证件姓名
		bck01597ReqBodyBean.setID_NUMBER1(map.get("ID_NUMBER1"));//出账客户证件号码
		bck01597ReqBodyBean.setID_NUMBER2(map.get("ID_NUMBER2"));//入账客户证件号码
		bck01597ReqBodyBean.setID_TYPE1(map.get("ID_TYPE1"));//出账客户证件类型
		bck01597ReqBodyBean.setID_TYPE2(map.get("ID_TYPE2"));//入账客户证件类型
		bck01597ReqBodyBean.setMEMO1(map.get("MEMO1"));//预留
		bck01597ReqBodyBean.setMEMO2(map.get("MEMO2"));//预留
		bck01597ReqBodyBean.setMEMO3(map.get("MEMO3"));//预留
		bck01597ReqBodyBean.setMEMO4(map.get("MEMO4"));//预留
		bck01597ReqBodyBean.setTRAN_AMT(map.get("TRAN_AMT"));//交易金额
		bck01597ReqBodyBean.setPROGRAM_FLAG(map.get("PROGRAM_FLAG"));//程序标识 单元素100501/双元素100503
		bck01597ReqBean.setBody(bck01597ReqBodyBean);
		//请求报文头
		bck01597ReqBean.setHeadBean(getInReqHeadBean("BCK_01597"));
		
		xstream.alias("Root", BCK01597ReqBean.class);
		xstream.alias("Body", BCK01597ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck01597ReqBean);
	}
	
	/**
	 * 卡信息查询
	 * @param map
	 * @return
	 */
	public static String BCK_03845(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK03845ReqBean bck03845ReqBean = new BCK03845ReqBean();
		//请求报文体
		BCK03845ReqBodyBean bck03845ReqBodyBean = new BCK03845ReqBodyBean();
		bck03845ReqBodyBean.setCARD_NO(map.get("CARD_NO"));//卡号
		bck03845ReqBodyBean.setISPIN(map.get("ISPIN"));//是否验密
		bck03845ReqBodyBean.setPASSWD(map.get("PASSWD"));//密码
		bck03845ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));//子帐号
		bck03845ReqBean.setBody(bck03845ReqBodyBean);
		//请求报文头
		bck03845ReqBean.setHeadBean(getInReqHeadBean("BCK_03845"));
		
		xstream.alias("Root", BCK03845ReqBean.class);
		xstream.alias("Body", BCK03845ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck03845ReqBean);
	}
	
	/**
	 * 证件信息查询
	 * @param map
	 * @return
	 */
	public static String BCK_03445(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK03445ReqBean bck03445ReqBean = new BCK03445ReqBean();
		//请求报文体
		BCK03445ReqBodyBean bck03445ReqBodyBean = new BCK03445ReqBodyBean();
		bck03445ReqBodyBean.setCUST_NAME(map.get("CUST_NAME"));
		bck03445ReqBodyBean.setID_NO(map.get("ID_NO"));
		bck03445ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));
		bck03445ReqBean.setBODY(bck03445ReqBodyBean);
		//请求报文头
		bck03445ReqBean.setHeadBean(getInReqHeadBean("BCK_03445"));
		
		xstream.alias("Root", BCK03445ReqBean.class);
		xstream.alias("Body", BCK03445ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck03445ReqBean);
	}
	
	/**
	 * 身份联网核查
	 * @return
	 */
	public static String BCK_07670(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07670ReqBean bCK07670ReqBean = new BCK07670ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_07670");	
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		bCK07670ReqBean.setHeadBean(inReqHeadBean);		
		BCK07670ReqBodyBean bCK07670ReqBodyBean = new BCK07670ReqBodyBean();
		bCK07670ReqBodyBean.setID(map.get("ID"));
		bCK07670ReqBodyBean.setNAME(map.get("NAME"));
		bCK07670ReqBean.setBody(bCK07670ReqBodyBean);
		xstream.alias("ROOT", BCK07670ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK07670ReqBodyBean.class);
		return xstream.toXML(bCK07670ReqBean);
	}
	
	/**
	 * 柜员授权
	 * @param map
	 * @return
	 */
	public static String BCK_07660(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07660ReqBean bck07660ReqBean = new BCK07660ReqBean();
		//请求报文体
		BCK07660ReqBodyBean bck07660ReqBodyBean = new BCK07660ReqBodyBean();
		bck07660ReqBodyBean.setFIN_PRI_LEN(map.get("FIN_PRI_LEN"));
		bck07660ReqBodyBean.setFIN_PRI_VAL(map.get("FIN_PRI_VAL"));
		bck07660ReqBodyBean.setSUP_TELLER_NO(map.get("SUP_TELLER_NO"));
		bck07660ReqBodyBean.setSUP_TELLER_PWD(map.get("SUP_TELLER_PWD"));
		bck07660ReqBean.setBody(bck07660ReqBodyBean);
		//请求报文头
		bck07660ReqBean.setHeadBean(getInReqHeadBean("BCK_07660"));
		
		xstream.alias("Root", BCK07660ReqBean.class);
		xstream.alias("Body", BCK07660ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07660ReqBean);
	}
	
	/**
	 * 柜员认证方式查询
	 * @param map
	 * @return
	 */
	public static String BCK_07659(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07659ReqBean bck07659ReqBean = new BCK07659ReqBean();
		bck07659ReqBean.setHeadBean(getInReqHeadBean("BCK_07659"));
		BCK07659ReqBodyBean bck07659ReqBodyBean = new BCK07659ReqBodyBean();
		bck07659ReqBodyBean.setQRY_TELLER_NO(map.get("QRY_TELLER_NO")); //设置柜员号
		bck07659ReqBean.setBody(bck07659ReqBodyBean);
		xstream.alias("Root", BCK07659ReqBean.class);
		xstream.alias("Body", BCK07659ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07659ReqBean);
	}
	
	/**
	 * 转账客户列表信息查询-前置07492
	 */
	public static String BCK_07492(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07492ReqBean bck07492ReqBean = new BCK07492ReqBean();
		bck07492ReqBean.setHeadBean(getInReqHeadBean("BCK_07492"));
		BCK07492ReqBodyBean bck07492ReqBodyBean = new BCK07492ReqBodyBean();
		bck07492ReqBodyBean.setACCT_NAME(map.get("ACCT_NAME"));//付款人名称
		bck07492ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));//付款卡号
		bck07492ReqBodyBean.setQRY_CHNL(map.get("QRY_CHNL"));//查询渠道(选输：不为空，查对应渠道转账信息，为空，查所有渠道转账信息)
		bck07492ReqBean.setBody(bck07492ReqBodyBean);
		xstream.alias("Root", BCK07492ReqBean.class);
		xstream.alias("Body", BCK07492ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07492ReqBean);
	}
	/**
	 * 大小额系统参数查询CM021
	 */
	public static String BCK_CM021(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCKCM021ReqBean bckcm021ReqBean = new BCKCM021ReqBean();
		bckcm021ReqBean.setHeadBean(getInReqHeadBean("BCK_CM021"));
		BCKCM021ReqBodyBean bckcm021ReqBodyBean = new BCKCM021ReqBodyBean();
		bckcm021ReqBodyBean.setSYSCODE(map.get("SYSCODE"));//系统代码
		bckcm021ReqBodyBean.setPARCODE(map.get("PARCODE"));//参数代码
		bckcm021ReqBean.setBody(bckcm021ReqBodyBean);
		xstream.alias("Root", BCKCM021ReqBean.class);
		xstream.alias("Body", BCKCM021ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bckcm021ReqBean);
	}
	
	/**
	 * 机构关系查询交易【20102】--前置01569
	 */
	public static String BCK_01569(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK01569ReqBean bck01569ReqBean = new BCK01569ReqBean();
		bck01569ReqBean.setHeadBean(getInReqHeadBean("BCK_01569"));
		BCK01569ReqBodyBean bck01569ReqBodyBean = new BCK01569ReqBodyBean();
		bck01569ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));//账号(非必输:账号与机构号其一必输)
		bck01569ReqBodyBean.setINST_CODE(map.get("INST_CODE"));//机构号(非必输:账号与机构号其一必输)
		bck01569ReqBodyBean.setSVR_INST_NO(map.get("SVR_INST_NO"));//交易机构号(非必输:不输时， 核心默认为本交易机构)
		bck01569ReqBodyBean.setCHL_NO(map.get("CHL_NO"));//渠道号(必输)
		bck01569ReqBean.setBody(bck01569ReqBodyBean);
		xstream.alias("Root", BCK01569ReqBean.class);
		xstream.alias("Body", BCK01569ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck01569ReqBean);
	}
	
	/**
	 * 大额普通汇兑往帐发送交易接口（通用）02013
	 */
	public static String BCK_02013(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK02013ReqBean bck02013ReqBean = new BCK02013ReqBean();
		bck02013ReqBean.setHeadBean(getInReqHeadBean("BCK_02013"));
		
		//必输（M）/选输（O）
		BCK02013ReqBodyBean bck02013ReqBodyBean = new BCK02013ReqBodyBean();
		bck02013ReqBodyBean.setMSG_TYPE(map.get("MSG_TYPE"));//M 报文类型(100_汇兑)
		bck02013ReqBodyBean.setCURRENCY(map.get("CURRENCY"));//M 币种(00_人民币)
		bck02013ReqBodyBean.setBOOK_CARD(map.get("BOOK_CARD"));//M 折卡选择(0_账号，1_卡号)
		bck02013ReqBodyBean.setPAY_ACCT_NO(map.get("PAY_ACCT_NO"));//O 付款人帐号(BOOK_CARD为“0”时，必输)
		bck02013ReqBodyBean.setPAY_CARD_NO(map.get("PAY_CARD_NO"));//O 付款人卡号(BOOK_CARD为“1”时，必输)
		bck02013ReqBodyBean.setPAY_NAME(map.get("PAY_NAME"));//M 付款人名称(允许中文)
		bck02013ReqBodyBean.setPAY_ADDR(map.get("PAY_ADDR"));//O 付款人地址
		bck02013ReqBodyBean.setBALANCE(map.get("BALANCE"));//O 余额
		bck02013ReqBodyBean.setSETT_TYPE(map.get("SETT_TYPE"));//M 结算方式(0_现金，1_转帐)
		bck02013ReqBodyBean.setDRAW_COND(map.get("DRAW_COND"));//M 支取条件(0_无1_凭密码2_凭证件3_凭印鉴4_凭印鉴和密码)
		bck02013ReqBodyBean.setPASSWORD(map.get("PASSWORD"));//O 密码(DRAW_COND为“1”时，必输)
		bck02013ReqBodyBean.setCERT_TYPE(map.get("CERT_TYPE"));//O 凭证种类(使用凭证时必输参照凭证种类对照表)
		bck02013ReqBodyBean.setCERT_NO(map.get("CERT_NO"));//O 凭证号码(使用凭证时必输)
		bck02013ReqBodyBean.setSUMM_CODE(map.get("SUMM_CODE"));//M 摘要代码(6_汇出)
		bck02013ReqBodyBean.setSUMM_TEXT(map.get("SUMM_TEXT"));//O 摘要内容(送资金用途：资金用途代码-内容)
		bck02013ReqBodyBean.setREMIT_AMT(map.get("REMIT_AMT"));//M  汇款金额(例如：1999.22)
		bck02013ReqBodyBean.setFEE_TYPE(map.get("FEE_TYPE"));//M 手续费类型(0_个人手续费，1_对公手续费)
		bck02013ReqBodyBean.setFEE(map.get("FEE"));//M 手续费(例如：19.20，若无手续费，填写0.00)
		bck02013ReqBodyBean.setPOST_FEE(map.get("POST_FEE"));//M 邮电费(例如：19.20，若无邮电费，填写0.00)
		bck02013ReqBodyBean.setBUSI_TYPE(map.get("BUSI_TYPE"));//M 业务种类(02102_普通汇兑：通过转账方式发起的汇款业务,02103_网银支付)
		bck02013ReqBodyBean.setRECV_BANK_NO(map.get("RECV_BANK_NO"));//M 接收行行号(支付系统行号)
		bck02013ReqBodyBean.setRECV_BANK_NAME(map.get("RECV_BANK_NAME"));//M 接收行行名(支付系统行名)
		bck02013ReqBodyBean.setRECV_CLR_BANK_NO(map.get("RECV_CLR_BANK_NO"));//M 接收行清算行号(支付系统清算行号)
		bck02013ReqBodyBean.setRECV_CLR_BANK_NAME(map.get("RECV_CLR_BANK_NAME"));//M 接收行清算行名(支付系统清算行名)
		bck02013ReqBodyBean.setPAYEE_HBR_NO(map.get("PAYEE_HBR_NO"));//M 收款人开户行号
		bck02013ReqBodyBean.setPAYEE_HBR_NAME(map.get("PAYEE_HBR_NAME"));//M 收款人开户行名
		bck02013ReqBodyBean.setPAYEE_ACCT_NO(map.get("PAYEE_ACCT_NO"));//M 收款人帐号
		bck02013ReqBodyBean.setPAYEE_NAME(map.get("PAYEE_NAME"));//M 收款人户名
		bck02013ReqBodyBean.setPAYEE_ADDR(map.get("PAYEE_ADDR"));//O 收款人地址
		bck02013ReqBodyBean.setORIG_SEND_DATE(map.get("ORIG_SEND_DATE"));//O 原发报日期
		bck02013ReqBodyBean.setORIG_TRAN_NO(map.get("ORIG_TRAN_NO"));//O 原交易序号
		bck02013ReqBodyBean.setORIG_TRUST_DATE(map.get("ORIG_TRUST_DATE"));//O 原委托日期
		bck02013ReqBodyBean.setORIG_CMT_NO(map.get("ORIG_CMT_NO"));//O 原CMT号码
		bck02013ReqBodyBean.setORIG_AMT(map.get("ORIG_AMT"));//O 原金额
		bck02013ReqBodyBean.setORIG_PAY_ACCT_NO(map.get("ORIG_PAY_ACCT_NO"));//O 原付款人帐号
		bck02013ReqBodyBean.setORIG_PAY_NAME(map.get("ORIG_PAY_NAME"));//O 原付款人名称
		bck02013ReqBodyBean.setORIG_PAYEE_ACCT_NO(map.get("ORIG_PAYEE_ACCT_NO"));//O 原收款人帐号
		bck02013ReqBodyBean.setORIG_PAYEE_NAME(map.get("ORIG_PAYEE_NAME"));//O 原收款人名称
		bck02013ReqBodyBean.setORIG_APPD_TEXT(map.get("ORIG_APPD_TEXT"));//O 原附言
		bck02013ReqBodyBean.setAPPD_TEXT(map.get("APPD_TEXT"));//O 附言
		bck02013ReqBodyBean.setBUSI_CLASS(map.get("BUSI_CLASS"));//M 业务类型(A100-普通汇兑)
		bck02013ReqBodyBean.setBUSI_PRIORITY(map.get("BUSI_PRIORITY"));//M 业务优先级(NORM：普通,HIGH：紧急,URGT：特急)
		bck02013ReqBodyBean.setPAY_HBR_NO(map.get("PAY_HBR_NO"));//O 付款人开户行行号
		bck02013ReqBodyBean.setPAY_HBR_NAME(map.get("PAY_HBR_NAME"));//O 付款人开户行名称
		bck02013ReqBodyBean.setPAY_HBR_INST_NO(map.get("PAY_HBR_INST_NO"));//M 付款人开户机构号(上送付款人开户行内机构代码)
		bck02013ReqBodyBean.setREMARK(map.get("REMARK"));//O 备注
		bck02013ReqBodyBean.setREMARK2(map.get("REMARK2"));//O 备注2
		bck02013ReqBodyBean.setAPPD_TEXT2(map.get("APPD_TEXT2"));//O 附言2
		bck02013ReqBodyBean.setRETURN_TEXT(map.get("RETURN_TEXT"));//M 退汇原因(当MSG_TYPE为“108”退汇时必输)
		bck02013ReqBodyBean.setTASK_ID(map.get("TASK_ID"));//M 任务号(标识唯一一笔大额往帐业务使用ZHFW+YYMMDD+付款人开户机构号+5位序号)
		bck02013ReqBodyBean.setOPER_DATE(map.get("OPER_DATE"));//M 操作日期(交易发起日期)
		bck02013ReqBodyBean.setCORE_PRJ_NO(map.get("CORE_PRJ_NO"));//M 核心项目编号(核心记账时使用，可存放上送渠道编号 ZHFWHVPS)
		bck02013ReqBodyBean.setCORE_PRO_CODE(map.get("CORE_PRO_CODE"));//M 核心产品代码(核心记账时使用，可存放上送渠道代码ZHFWHVPS)
		bck02013ReqBodyBean.setCNAPS_MSG_TYPE(map.get("CNAPS_MSG_TYPE"));//M 二代报文种类(hvps.111.001.01)
		bck02013ReqBodyBean.setPP_No(map.get("PP_No"));//O 平盘单号
		bck02013ReqBodyBean.setTRANSFER_FLAG(map.get("TRANSFER_FLAG"));//M 转账标志 (第一位： 0-本人 1-他人 第二位：0-本行 1-跨行,第一位按照收付款人是否相同：0-相同，1-不同；第二位送1.)
		bck02013ReqBodyBean.setNEXT_DAY_SEND_FLAG(map.get("NEXT_DAY_SEND_FLAG"));//O 次日发送标志
		bck02013ReqBodyBean.setTIMED_SEND_TIME(map.get("TIMED_SEND_TIME"));//O 定时发送时间
		bck02013ReqBodyBean.setTEL_NO(map.get("TEL_NO"));//手机号
		bck02013ReqBean.setBody(bck02013ReqBodyBean);
		
		xstream.alias("Root", BCK02013ReqBean.class);
		xstream.alias("Body", BCK02013ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck02013ReqBean);
	}
	
	/**
	 * 小额普通贷记往帐录入（通用）02224
	 */
	public static String BCK_02224(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK02224ReqBean bck02224ReqBean = new BCK02224ReqBean();
		bck02224ReqBean.setHeadBean(getInReqHeadBean("BCK_02224"));
		
		//必输（M）/选输（O）
		BCK02224ReqBodyBean bck02224ReqBodyBean = new BCK02224ReqBodyBean();
		bck02224ReqBodyBean.setBUSI_OPTION(map.get("BUSI_OPTION"));//M 业务选择(0_普通贷记往帐)
		bck02224ReqBodyBean.setBUSI_CLASS(map.get("BUSI_CLASS"));//M 业务类型(A100-普通汇兑)
		bck02224ReqBodyBean.setCURRENCY(map.get("CURRENCY"));//M 币种(00-人民币)
		bck02224ReqBodyBean.setBOOK_CARD(map.get("BOOK_CARD"));//M 折卡选择(0_帐号,1_卡号)
		bck02224ReqBodyBean.setPAY_ACCT_NO(map.get("PAY_ACCT_NO"));//O 付款人帐号(BOOK_CARD为0时必输)
		bck02224ReqBodyBean.setPAY_CARD_NO(map.get("PAY_CARD_NO"));//O 付款人卡号(BOOK_CARD为1时必输)
		bck02224ReqBodyBean.setPAY_NAME(map.get("PAY_NAME"));//M 付款人名称(支持中文)
		bck02224ReqBodyBean.setPAY_ADDR(map.get("PAY_ADDR"));//O 付款人地址()
		bck02224ReqBodyBean.setPAY_HBR_NO(map.get("PAY_HBR_NO"));//O 付款人开户行号()
		bck02224ReqBodyBean.setPAY_HBR_NAME(map.get("PAY_HBR_NAME"));//O 付款人开户行名()
		bck02224ReqBodyBean.setPAY_HBR_INST_NO(map.get("PAY_HBR_INST_NO"));//M 付款人开户机构号(上送付款人开户行内机构代码)
		bck02224ReqBodyBean.setDRAW_COND(map.get("DRAW_COND"));//M 支付条件(0_无1_凭密2_凭证件3_凭印鉴4_凭印鉴和密码)
		bck02224ReqBodyBean.setBOOK_NO(map.get("BOOK_NO"));//O 存折号码()
		bck02224ReqBodyBean.setPASSWORD(map.get("PASSWORD"));//O 密码(当DRAW_COND为1或4时必输)
		bck02224ReqBodyBean.setBALANCE(map.get("BALANCE"));//O 余额(格式：1234.56)
		bck02224ReqBodyBean.setCERT_TYPE(map.get("CERT_TYPE"));//O 凭证种类(使用凭证时必输)
		bck02224ReqBodyBean.setCERT_NO(map.get("CERT_NO"));//O 凭证号码(使用凭证时必输)
		bck02224ReqBodyBean.setSUMM_CODE(map.get("SUMM_CODE"));//O 摘要()
		bck02224ReqBodyBean.setSUMM_TEXT(map.get("SUMM_TEXT"));//O 摘要内容(送资金用途：资金用途代码-内容)
		bck02224ReqBodyBean.setREMIT_AMT(map.get("REMIT_AMT"));//M 汇款金额(格式：1234.56)
		bck02224ReqBodyBean.setFEE_TYPE(map.get("FEE_TYPE"));//M 手续费类型(0-个人手续费,1-对公手续费)
		bck02224ReqBodyBean.setFEE(map.get("FEE"));//M 手续费(格式：1234.56)
		bck02224ReqBodyBean.setPOST_FEE(map.get("POST_FEE"));//M 邮电费(格式：1234.56)
		bck02224ReqBodyBean.setBUSI_TYPE(map.get("BUSI_TYPE"));//M 业务种类(02102-普通汇兑（客户通过转账方式发起额汇款）02103-网银支付)
		bck02224ReqBodyBean.setITEM_NUM(map.get("ITEM_NUM"));//M 笔数(笔数上送“1”) 
		bck02224ReqBodyBean.setTRAN_AMT(map.get("TRAN_AMT"));//M 交易金额()
		bck02224ReqBodyBean.setPAY_ACCT_TYPE(map.get("PAY_ACCT_TYPE"));//M 付款类型(付款帐户类型:0银行账号,1贷记卡,2借记卡,9其他)
		bck02224ReqBodyBean.setPAYEE_ACCT_TYPE(map.get("PAYEE_ACCT_TYPE"));//M 收款类型(收款帐户类型:0银行账号,1贷记卡,2借记卡,9其他)
		bck02224ReqBodyBean.setSETT_TYPE(map.get("SETT_TYPE"));//M 业务模式(0_现金,1_转账)
		bck02224ReqBodyBean.setTASK_ID(map.get("TASK_ID"));//M 任务号(每笔交易任务号均不相同，若某笔交易需要重新发送时，任务号需要使用相同值，且交易各相关要素必须相同，标识唯一一笔小额往帐业务使用ZHFW+YYMMDD+付款人开户机构号+5位序号)
		bck02224ReqBodyBean.setCORE_PRJ_NO(map.get("CORE_PRJ_NO"));//M 核心项目编号(核心记账时使用，可存放上送渠道编号ZHFWBEPS)
		bck02224ReqBodyBean.setCORE_PRO_CODE(map.get("CORE_PRO_CODE"));//M 核心产品代码(核心记账时使用，可存放上送渠道代码ZHFWBEPS)
		bck02224ReqBodyBean.setREMARK1(map.get("REMARK1"));//M 备注1()
		bck02224ReqBodyBean.setREMARK2(map.get("REMARK2"));//M 备注2()
		bck02224ReqBodyBean.setTRANSFER_FLAG(map.get("TRANSFER_FLAG"));//M 转账标志()
		bck02224ReqBodyBean.setNEXT_DAY_SEND_FLAG(map.get("NEXT_DAY_SEND_FLAG"));//O 次日发送标志(0-普通到账；1-下一自然日到账只有当BUSI_OPTION为“0-普通”且BUSI_CLASS为“A100”时，此字段可输入“1”；若为空，默认0-实时发送)
		bck02224ReqBodyBean.setTIMED_SEND_TIME(map.get("TIMED_SEND_TIME"));//O 定时发送时间(格式：HHMMSS若NEXT_DAY_SEND_FLAG为空或0，此域无效若NEXT_DAY_SEND_FLAG为1时，此域生效，若此域为空，默认100000之后发送)
		bck02224ReqBodyBean.setTEL_NO(map.get("TEL_NO"));//手机号
		
		//收款方明细
		BCK02224ReqBodyDetailBean bck02224ReqBodyDetailBean = new BCK02224ReqBodyDetailBean();
		bck02224ReqBodyDetailBean.setPAYEE_BANK_NO(map.get("PAYEE_BANK_NO"));//M 收款人行号()
		bck02224ReqBodyDetailBean.setPAYEE_BANK_NAME(map.get("PAYEE_BANK_NAME"));//M 收款人行名()
		bck02224ReqBodyDetailBean.setPAYEE_HBR_NO(map.get("PAYEE_HBR_NO"));//M 收款开户行号()
		bck02224ReqBodyDetailBean.setPAYEE_HBR_NAME(map.get("PAYEE_HBR_NAME"));//M 收款开户行名()
		bck02224ReqBodyDetailBean.setPAYEE_ACCT_NO(map.get("PAYEE_ACCT_NO"));//M 收款人帐号()
		bck02224ReqBodyDetailBean.setPAYEE_NAME(map.get("PAYEE_NAME"));//M 收款人户名()
		bck02224ReqBodyDetailBean.setPAYEE_ADDR(map.get("PAYEE_ADDR"));//O 收款人地址()
		bck02224ReqBodyDetailBean.setPAY_AMT(map.get("PAY_AMT"));//M 支付金额()
		bck02224ReqBodyDetailBean.setAPPD_TEXT(map.get("APPD_TEXT"));//O 附言()
		bck02224ReqBodyBean.setDETAIL(bck02224ReqBodyDetailBean);
		bck02224ReqBean.setBody(bck02224ReqBodyBean);
		
		xstream.alias("Root",BCK02224ReqBean.class);
		xstream.alias("Body", BCK02224ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck02224ReqBean);
	}
	
	/**
	 * 单日累计划转金额查询-前置07494
	 */
	public static String BCK_07494(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07494ReqBean bck07494ReqBean = new BCK07494ReqBean();
		bck07494ReqBean.setHeadBean(getInReqHeadBean("BCK_07494"));
		
		BCK07494ReqBodyBean bck07494ReqBodyBean = new BCK07494ReqBodyBean();
		bck07494ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));//账号
		bck07494ReqBean.setBody(bck07494ReqBodyBean);
		
		xstream.alias("Root",BCK07494ReqBean.class);
		xstream.alias("Body", BCK07494ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07494ReqBean);
	}
	
	/**
	 * 根据机构号查询支付行信息-前置01118
	 */
	public static String BCK_01118(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK01118ReqBean bck01118ReqBean = new BCK01118ReqBean();
		bck01118ReqBean.setHeadBean(getInReqHeadBean("BCK_01118"));
		
		BCK01118ReqBodyBean bck01118ReqBodyBean = new BCK01118ReqBodyBean();
		bck01118ReqBodyBean.setSCH_INST_NO(map.get("SCH_INST_NO"));//机构号
		bck01118ReqBean.setBody(bck01118ReqBodyBean);
		
		xstream.alias("Root",BCK01118ReqBean.class);
		xstream.alias("Body", BCK01118ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck01118ReqBean);
	}
	
	/**
	 * 行内汇划-前置02600
	 */
	public static String BCK_02600(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK02600ReqBean bck02600ReqBean = new BCK02600ReqBean();
		bck02600ReqBean.setHeadBean(getInReqHeadBean("BCK_02600"));
		
		BCK02600ReqBodyBean bck02600ReqBodyBean = new BCK02600ReqBodyBean();
		bck02600ReqBodyBean.setBUSI_TYPE(map.get("BUSI_TYPE"));//业务类型(1、代收  2、代付，默认为代收，代付我行开通未使用)
		bck02600ReqBodyBean.setDB_CR_FLAG(map.get("DB_CR_FLAG"));//借贷标志(1_借方2_贷方，默认为借方)
		bck02600ReqBodyBean.setAGENT_INST_NO(map.get("AGENT_INST_NO"));//被代理机构号(被代理机构号（付款人账号所在机构，清算中心上送；网点不上送）)
		bck02600ReqBodyBean.setSEND_BANK_NO(map.get("SEND_BANK_NO"));//提出行行号(默认为本机构)
		bck02600ReqBodyBean.setSEND_BANK_NAME(map.get("SEND_BANK_NAME"));//提出行名称(由提出行行号自动带出)
		bck02600ReqBodyBean.setPAY_ACCT_NO(map.get("PAY_ACCT_NO"));//付款人账号(网点：前端调用账号信息查询接口，前置返回开户行号，前端校验开户行号与提出行号是否一致，不一致报错)
		bck02600ReqBodyBean.setPAY_NAME(map.get("PAY_NAME"));//付款人名称(由付款账号自动带出)
		bck02600ReqBodyBean.setDRAW_COND(map.get("DRAW_COND"));//支取条件(必输项，选项：0_无 、1_凭印签，内部账户009026205（默认为1 凭印鉴 在讨论）、 其余默认为0，内部账选0 无 时，不验印需授权，对公客户账号默认为1 凭印签，默认为1、凭印签)
		bck02600ReqBodyBean.setRECV_BANK_NO(map.get("RECV_BANK_NO"));//提入行行号(必输项，核心校验是否为辖内机构号)
		bck02600ReqBodyBean.setRECV_BANK_NAME(map.get("RECV_BANK_NAME"));//提入行名称(由行号自动带出)
		bck02600ReqBodyBean.setPAYEE_ACCT_NO(map.get("PAYEE_ACCT_NO"));//收款人账号(必输项，核心校验是否为所输提入行的账号)
		bck02600ReqBodyBean.setPAYEE_NAME(map.get("PAYEE_NAME"));//收款人名称(由收款人账号自动带出)
		bck02600ReqBodyBean.setBILL_TYPE(map.get("BILL_TYPE"));//票据种类(必输项（转账支票是代付业务，进账单是代收业务，通用可代收代付。（现凭证种类代码108转账支票， 308通用凭证，107进账单）如果为108时判断是否为该账号下的支票)
		bck02600ReqBodyBean.setBILL_NO(map.get("BILL_NO"));//票据号码(凭证种类为108必输，其他不输自动为灰色)
		bck02600ReqBodyBean.setPIN(map.get("PIN"));//支付密码()
		bck02600ReqBodyBean.setAVAL_BAL(map.get("AVAL_BAL"));//可用余额(由付款账号自动带出)
		bck02600ReqBodyBean.setCUEERNCY(map.get("CUEERNCY"));//币种(默认 RMB)
		bck02600ReqBodyBean.setAMT(map.get("AMT"));//金    额(必输项，金额圈存，必须小于等于可用余额，内部账户不圈存)
		bck02600ReqBodyBean.setBILL_DATE(map.get("BILL_DATE"));//出票日期(当票据种类为108时必输项，其余为空根据出票日期判断印签的有效日期（核心印签库不支持变更前的印鉴核验，要求更改）)
		bck02600ReqBodyBean.setNOTE_DATE(map.get("NOTE_DATE"));//提示付款日期(当票据种类为108时必输项，其余为空)
		bck02600ReqBodyBean.setENDOR_NUM(map.get("ENDOR_NUM"));//背书次数(非必输, 业务确定去掉字段，前置接口保留备用)
		bck02600ReqBodyBean.setREMARK(map.get("REMARK"));//备注(当票据种类为108时必输项，其余选输)
		bck02600ReqBodyBean.setPURPOS(map.get("PURPOS"));//用途()
		bck02600ReqBodyBean.setTRN_REASON(map.get("TRN_REASON"));//转账原因()
		bck02600ReqBodyBean.setORIG_CERT_TYPE(map.get("ORIG_CERT_TYPE"));//原始凭证种类(非必输，业务确定去掉字段，前置接口保留备用)
		bck02600ReqBodyBean.setATTACH_NUM(map.get("ATTACH_NUM"));//张数(非必输，业务确定去掉字段，前置接口保留备用)
		bck02600ReqBodyBean.setAPPD_TEXT(map.get("APPD_TEXT"));//附加信息(非必输，业务确定去掉字段，前置接口保留备用)
		bck02600ReqBodyBean.setSUMM_CODE(map.get("SUMM_CODE"));//摘要代码()
		bck02600ReqBodyBean.setSUMM_TEXT(map.get("SUMM_TEXT"));//摘要内容()
		bck02600ReqBodyBean.setTASK_IDTRNS(map.get("TASK_IDTRNS"));//任务号
		bck02600ReqBodyBean.setNEXT_DAY_SEND_FLAG(map.get("NEXT_DAY_SEND_FLAG"));//次日发送标志(0-普通转账；1-下一工作日转账若为空，默认0-实时发送)
		bck02600ReqBodyBean.setTIMED_SEND_TIME(map.get("TIMED_SEND_TIME"));//定时发送时间(格式：HHMMSS若NEXT_DAY_SEND_FLAG为空或0，此域无效	若NEXT_DAY_SEND_FLAG为1时，此域生效，若此域为空，默认100000之后发送)
		bck02600ReqBodyBean.setTEL_NO(map.get("TEL_NO"));//手机号
		
		//背书人清单
		BCK02600ReqBodyDetailBean bck02600detailbean = new BCK02600ReqBodyDetailBean();
		bck02600detailbean.setENDOR_NAME(map.get("ENDOR_NAME"));//背书人清单(最多支持6000字。非必输,业务确定去掉字段，前置接口保留备用)
		bck02600ReqBodyBean.setDETAIL(bck02600detailbean);
		
		bck02600ReqBean.setBody(bck02600ReqBodyBean);
		
		xstream.alias("Root", BCK02600ReqBean.class);
		xstream.alias("Body", BCK02600ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck02600ReqBean);
	}
	
	/**
	 * 前置02956(权限明细查询)
	 */
	public static String BCK_02956(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK02956ReqBean bck02956ReqBean = new BCK02956ReqBean();
		bck02956ReqBean.setHeadBean(getInReqHeadBean("BCK_02956"));
		
		BCK02956ReqBodyBean bck02956ReqBodyBean = new BCK02956ReqBodyBean();
		bck02956ReqBodyBean.setQRY_OPTION(map.get("QRY_OPTION"));//查询选项(1_交易渠道类型限额信息 2_交易对手信息  3_动账及业务通知联系人信息)
		bck02956ReqBodyBean.setCARD_NO(map.get("CARD_NO"));//卡号
		
		bck02956ReqBean.setBody(bck02956ReqBodyBean);
		
		xstream.alias("Root", BCK02956ReqBean.class);
		xstream.alias("Body", BCK02956ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck02956ReqBean);
	}
	
	/**
	 * 账户信息查询及密码验证-前置03521
	 */
	public static String BCK_03521(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK03521ReqBean bck03521ReqBean = new BCK03521ReqBean();
		bck03521ReqBean.setHeadBean(getInReqHeadBean("BCK_03521"));
		
		BCK03521ReqBodyBean bck03521ReqBodyBean = new BCK03521ReqBodyBean();
		bck03521ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));//账号
		bck03521ReqBodyBean.setPASSWORD(map.get("PASSWORD"));//密码
		bck03521ReqBodyBean.setPIN_VAL_FLAG(map.get("PIN_VAL_FLAG"));//验密标志
		
		bck03521ReqBean.setBody(bck03521ReqBodyBean);
		
		xstream.alias("Root", BCK03521ReqBean.class);
		xstream.alias("Body", BCK03521ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck03521ReqBean);
	}
	/**
	 * 核心节假日查询-前置07495
	 */
	public static String BCK_07495(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07495ReqBean bck07495ReqBean = new BCK07495ReqBean();
		bck07495ReqBean.setHeadBean(getInReqHeadBean("BCK_07495"));
		
		BCK07495ReqBodyBean bck07495ReqBodyBean = new BCK07495ReqBodyBean();
		bck07495ReqBodyBean.setQRY_DATE(map.get("QRY_DATE"));//查询日期
		
		bck07495ReqBean.setBody(bck07495ReqBodyBean);
		
		xstream.alias("Root", BCK07495ReqBean.class);
		xstream.alias("Body", BCK07495ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07495ReqBean);
	}
	
	/**
	 *前置系统参数查询-前置QRY00
	 * @param map
	 * @return
	 */
	public static String BCK_QRY00(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCKQRY00ReqBean bckQRY00ReqBean = new BCKQRY00ReqBean();
		//请求报文体
		BCKQRY00ReqBodyBean bckQRY00ReqBodyBean = new BCKQRY00ReqBodyBean();
		bckQRY00ReqBodyBean.setPARAM_CODE(map.get("PARAM_CODE"));//出账户名
		bckQRY00ReqBean.setBody(bckQRY00ReqBodyBean);
		//请求报文头
		bckQRY00ReqBean.setHeadBean(getInReqHeadBean("BCK_QRY00"));
		
		xstream.alias("Root", BCKQRY00ReqBean.class);
		xstream.alias("Body", BCKQRY00ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bckQRY00ReqBean);
	}
	
	/**
	 *单位卡IC卡验证（核心77102）-前置03453
	 * @param map
	 * @return
	 */
	public static String BCK_03453(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK03453ReqBean bck03453ReqBean = new BCK03453ReqBean();
		//请求报文体
		BCK03453ReqBodyBean bck03453ReqBodyBean = new BCK03453ReqBodyBean();
		bck03453ReqBodyBean.setCARD_NO(map.get("CARD_NO"));//卡号
		bck03453ReqBodyBean.setPASSWD(map.get("PASSWD"));//密码
		bck03453ReqBodyBean.setTRACK2_INFO(map.get("TRACK2_INFO"));//二磁信息
		bck03453ReqBodyBean.setFALL_BACK_FLAG(map.get("FALL_BACK_FLAG"));//降级标志
		bck03453ReqBodyBean.setARQC(map.get("ARQC"));//ARQC
		bck03453ReqBodyBean.setARQC_SRC_DATA(map.get("ARQC_SRC_DATA"));//ARQC生成数据
		bck03453ReqBodyBean.setTERM_RESULT(map.get("TERM_RESULT"));//终端验证结果
		bck03453ReqBodyBean.setISSUER_APP_DATA(map.get("ISSUER_APP_DATA"));//发卡行应用数据
		bck03453ReqBodyBean.setICCARD_DATA(map.get("ICCARD_DATA"));//应用主账户序列号
		bck03453ReqBodyBean.setDATE_EXPR(map.get("DATE_EXPR"));//卡片有效期
		
		bck03453ReqBean.setBody(bck03453ReqBodyBean);
		//请求报文头
		bck03453ReqBean.setHeadBean(getInReqHeadBean("BCK_03453"));
		
		xstream.alias("Root", BCK03453ReqBean.class);
		xstream.alias("Body", BCK03453ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck03453ReqBean);
	}
	
	
	/**
	 *个人IC卡验证(卡75048)	-前置【07602】
	 * @param map
	 * @return
	 */
	public static String BCK_07602(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07602ReqBean bck07602ReqBean = new BCK07602ReqBean();
		//请求报文体
		BCK07602ReqBodyBean bck07602ReqBodyBean = new BCK07602ReqBodyBean();
		bck07602ReqBodyBean.setIC_CHL(map.get("IC_CHL"));//渠道号
		bck07602ReqBodyBean.setCARD_NO(map.get("CARD_NO"));//卡号
		bck07602ReqBodyBean.setAID(map.get("AID"));//AID
		bck07602ReqBodyBean.setARQC(map.get("ARQC"));//ARQC
		bck07602ReqBodyBean.setARQC_SRC_DATA(map.get("ARQC_SRC_DATA"));//ARQC生成数据
		bck07602ReqBodyBean.setTERM_CHK_VALUE(map.get("TERM_CHK_VALUE"));//终端验证结果
		bck07602ReqBodyBean.setAPP_ACCT_SEQ(map.get("APP_ACCT_SEQ"));//应用主账户序列号
		bck07602ReqBodyBean.setISS_APP_DATA(map.get("ISS_APP_DATA"));//发卡行应用数据
		bck07602ReqBodyBean.setCARD_POV(map.get("CARD_POV"));//卡片有效期
		
		bck07602ReqBean.setBody(bck07602ReqBodyBean);
		//请求报文头
		bck07602ReqBean.setHeadBean(getInReqHeadBean("BCK_07602"));
		
		xstream.alias("Root", BCK07602ReqBean.class);
		xstream.alias("Body", BCK07602ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07602ReqBean);
	}
	
	/**
	 *权限明细查询【78010】(前置交易码03740)
	 * @param map
	 * @return
	 */
	public static String BCK_03740(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK03740ReqBean bck03740ReqBean = new BCK03740ReqBean();
		//请求报文体
		BCK03740ReqBodyBean bck03740ReqBodyBean = new BCK03740ReqBodyBean();
		bck03740ReqBodyBean.setQRY_OPTION(map.get("QRY_OPTION"));//查询选项
		bck03740ReqBodyBean.setCARD_NO(map.get("CARD_NO"));//卡号
		bck03740ReqBodyBean.setMP_NO(map.get("MP_NO"));//手机号
		
		bck03740ReqBean.setBody(bck03740ReqBodyBean);
		//请求报文头
		bck03740ReqBean.setHeadBean(getInReqHeadBean("BCK_03740"));
		
		xstream.alias("Root", BCK03740ReqBean.class);
		xstream.alias("Body", BCK03740ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck03740ReqBean);
	}
	
	/**
	 *校验对手信息【77016】(前置交易码02954)
	 * @param map
	 * @return
	 */
	public static String BCK_02954(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK02954ReqBean bck02954ReqBean = new BCK02954ReqBean();
		//请求报文体
		BCK02954ReqBodyBean bck02954ReqBodyBean = new BCK02954ReqBodyBean();
		bck02954ReqBodyBean.setCARD_NO(map.get("CARD_NO"));//卡号
		bck02954ReqBodyBean.setOPER_CHOOSE(map.get("OPER_CHOOSE"));//操作选择
		bck02954ReqBodyBean.setOTHER_ACCT_NO(map.get("OTHER_ACCT_NO"));//对方账号
		
		bck02954ReqBean.setBody(bck02954ReqBodyBean);
		//请求报文头
		bck02954ReqBean.setHeadBean(getInReqHeadBean("BCK_02954"));
		
		xstream.alias("Root", BCKQRY00ReqBean.class);
		xstream.alias("Body", BCKQRY00ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck02954ReqBean);
	}
	
	/**
	 * 查询行名
	 * @param map
	 * @return
	 */
	public static String Tms_0008(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		Tms0008ReqBean tms0008ReqBean = new Tms0008ReqBean();
		//请求报文体
		Tms0008ReqBodyBean tms0008ReqBodyBean = new Tms0008ReqBodyBean();
		tms0008ReqBodyBean.setBankName(map.get("bankName"));
		tms0008ReqBodyBean.setCityCode(map.get("cityCode"));
		tms0008ReqBodyBean.setCityName(map.get("cityName"));
		tms0008ReqBodyBean.setProvinceCode(map.get("provinceCode"));
		
		tms0008ReqBean.setBodyBean(tms0008ReqBodyBean);
		//请求报文头
		tms0008ReqBean.setHeadBean(getInReqHeadBean("TMS_0008"));
		
		xstream.alias("Root", Tms0008ReqBean.class);
		xstream.alias("Body", Tms0008ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(tms0008ReqBean);
	}
	
	/**
	 * 定时发送交易信息查询
	 * @param map
	 * @return
	 */
	public static String BCK_CM022(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCKCM022ReqBean bckCM022ReqBean = new BCKCM022ReqBean();
		//请求报文体
		BCKCM022ReqBodyBean bckCM022ReqBodyBean = new BCKCM022ReqBodyBean();
		bckCM022ReqBodyBean.setTRAN_CHANNEL(map.get("TRAN_CHANNEL"));//交易渠道
		bckCM022ReqBodyBean.setSTART_DATE(map.get("START_DATE"));//起始日期
		bckCM022ReqBodyBean.setEND_DATE(map.get("END_DATE"));//终止日期
		bckCM022ReqBodyBean.setQRY_INST_NO(map.get("QRY_INST_NO"));//交易机构
		bckCM022ReqBodyBean.setQRY_TRAN_CODE(map.get("QRY_TRAN_CODE"));//交易码
		bckCM022ReqBodyBean.setSEND_STAT(map.get("SEND_STAT"));//交易状态
		bckCM022ReqBodyBean.setQRY_TASK_ID(map.get("QRY_TASK_ID"));//任务号
		bckCM022ReqBodyBean.setMS_ACCT_NO(map.get("MS_ACCT_NO"));//付款账号
		bckCM022ReqBodyBean.setTRAN_AMT(map.get("TRAN_AMT"));//交易金额
		bckCM022ReqBean.setBody(bckCM022ReqBodyBean);
		//请求报文头
		bckCM022ReqBean.setHeadBean(getInReqHeadBean("BCK_CM022"));
		
		xstream.alias("Root", BCKCM022ReqBean.class);
		xstream.alias("Body", BCKCM022ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bckCM022ReqBean);
	}
	
	/**
	 * 定时发送交易撤销
	 * @param map
	 * @return
	 */
	public static String BCK_02693(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK02693ReqBean bck02693ReqBean = new BCK02693ReqBean();
		//请求报文体
		BCK02693ReqBodyBean bck02693ReqBodyBean = new BCK02693ReqBodyBean();
		bck02693ReqBodyBean.setORIG_CHANNEL(map.get("ORIG_CHANNEL"));//原交易渠道
		bck02693ReqBodyBean.setORIG_SYS_DATE(map.get("ORIG_SYS_DATE"));//原交易前置日期
		bck02693ReqBodyBean.setORIG_TASK_ID(map.get("ORIG_TASK_ID"));//原交易任务号
		bck02693ReqBean.setBody(bck02693ReqBodyBean);
		//请求报文头
		bck02693ReqBean.setHeadBean(getInReqHeadBean("BCK_02693"));
		
		xstream.alias("Root", BCK02693ReqBean.class);
		xstream.alias("Body", BCK02693ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck02693ReqBean);
	}
	
	/**
	 * 账户限额查询【02879】-前置02781
	 * @param map
	 * @return
	 */
	public static String BCK_02781(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK02781ReqBean bck02781ReqBean = new BCK02781ReqBean();
		//请求报文体
		BCK02781ReqBodyBean bck02781ReqBodyBean = new BCK02781ReqBodyBean();
		bck02781ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));//ACCT_NO	账号
		bck02781ReqBodyBean.setDB_CR_FLAG(map.get("DB_CR_FLAG"));//借贷标志
		bck02781ReqBodyBean.setOPPO_ACCT_NO(map.get("OPPO_ACCT_NO"));//对方账户
		bck02781ReqBodyBean.setTRAN_AMT(map.get("TRAN_AMT"));//发生额
		
		bck02781ReqBean.setBody(bck02781ReqBodyBean);
		//请求报文头
		bck02781ReqBean.setHeadBean(getInReqHeadBean("BCK_02781"));
		
		xstream.alias("Root", BCKQRY00ReqBean.class);
		xstream.alias("Body", BCKQRY00ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck02781ReqBean);
	}
	
	/**
	 * 转账客户列表信息删除-前置07496
	 * @param map
	 * @return
	 */
	public static String BCK_07496(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07496ReqBean bck07496ReqBean = new BCK07496ReqBean();
		//请求报文体
		BCK07496ReqBodyBean bck07496ReqBodyBean = new BCK07496ReqBodyBean();
		bck07496ReqBodyBean.setPAY_ACCT_NO(map.get("PAY_ACCT_NO"));//转出账号
		bck07496ReqBodyBean.setPAY_ACCT_NAME(map.get("PAY_ACCT_NAME"));//转出户名
		bck07496ReqBodyBean.setPAYEE_ACCT_NO(map.get("PAYEE_ACCT_NO"));//转入账号
		bck07496ReqBodyBean.setPAYEE_ACCT_NAME(map.get("PAYEE_ACCT_NAME"));//转入户名
		bck07496ReqBodyBean.setQRY_CHNL(map.get("QRY_CHNL"));//查询渠道
		
		bck07496ReqBean.setBody(bck07496ReqBodyBean);
		//请求报文头
		bck07496ReqBean.setHeadBean(getInReqHeadBean("BCK_07496"));
		
		xstream.alias("Root", BCKQRY00ReqBean.class);
		xstream.alias("Body", BCKQRY00ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07496ReqBean);
	}
	
}
