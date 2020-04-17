package com.boomhope.Bill.TransService.LostReport.Interface;

import java.util.Map;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK02864ReqBean;
import com.boomhope.tms.message.account.in.BCK02864ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK03845ReqBean;
import com.boomhope.tms.message.account.in.BCK03845ReqBodyBean;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.bck.*;
import com.boomhope.tms.message.in.tms.Tms0005ReqBean;
import com.boomhope.tms.message.in.tms.Tms0005ReqBodyBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 组装接口报文(挂失/解挂)
 * @author Administrator
 *
 */
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
	 * 身份核查
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
		bck01597ReqBodyBean.setCARD_NO1(map.get("CARD_NO1"));
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
	 * 个人客户查询( 01020)-前置07519
	 * @param map
	 * @return
	 */
	public static String BCK_07519(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07519ReqBean bck07519ReqBean = new BCK07519ReqBean();
		//请求报文体
		BCK07519ReqBodyBean bck07519ReqBodyBean = new BCK07519ReqBodyBean();
		bck07519ReqBodyBean.setSCH_TYPE(map.get("SCH_TYPE"));
		bck07519ReqBodyBean.setCUST_NO(map.get("CUST_NO"));
		bck07519ReqBodyBean.setCUST_NAME(map.get("CUST_NAME"));
		bck07519ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));
		bck07519ReqBodyBean.setID_NO(map.get("ID_NO"));
		bck07519ReqBodyBean.setCUST_PASS_WORD(map.get("CUST_PASS_WORD"));
		bck07519ReqBodyBean.setINFO_TYPE(map.get("INFO_TYPE"));
		bck07519ReqBean.setBody(bck07519ReqBodyBean);
		//请求报文头
		bck07519ReqBean.setHeadBean(getInReqHeadBean("BCK_07519"));
		
		xstream.alias("Root", BCK07519ReqBean.class);
		xstream.alias("Body", BCK07519ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07519ReqBean);
	}
	
	/**
	  * 按客户查询账户信息【 20103】-前置07527
	  */
	public static String BCK_07527(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07527ReqBean bck07527ReqBean = new BCK07527ReqBean();
		//请求报文体
		BCK07527ReqBodyBean bck07527ReqBodyBean = new BCK07527ReqBodyBean();
		bck07527ReqBodyBean.setCUST_NO(map.get("CUST_NO"));//客户号
		bck07527ReqBodyBean.setCUST_TYPE(map.get("CUST_TYPE"));//客户类型
		bck07527ReqBodyBean.setCUST_NAME(map.get("CUST_NAME"));//客户名称
		bck07527ReqBodyBean.setID_NO(map.get("ID_NO"));//证件号
		bck07527ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));//证件类型
		bck07527ReqBodyBean.setQRY_TYPE(map.get("QRY_TYPE"));//查询类型
		   
		bck07527ReqBean.setBody(bck07527ReqBodyBean);
		//请求报文头
		bck07527ReqBean.setHeadBean(getInReqHeadBean("BCK_07527"));
		   
		xstream.alias("Root", BCK07527ReqBean.class);
		xstream.alias("Body", BCK07527ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07527ReqBean);   
	}
	
	/**
	 * 开卡明细联动查询【70028】-07524
	 */
	public static String BCK_07524(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07524ReqBean bck07524ReqBean = new BCK07524ReqBean();
		//请求报文体
		BCK07524ReqBodyBean bck07524ReqBodyBean = new BCK07524ReqBodyBean();
		bck07524ReqBodyBean.setID_NO(map.get("ID_NO"));//证件号
		bck07524ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));//证件类型
		
		bck07524ReqBean.setBody(bck07524ReqBodyBean);
		//请求报文头
		bck07524ReqBean.setHeadBean(getInReqHeadBean("BCK_07524"));
		
		xstream.alias("Root", BCK07524ReqBean.class);
		xstream.alias("Body", BCK07524ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07524ReqBean);
	}
	
	/**
	 * 卡信息查询03845
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
	 * 卡下子账户列表查询03519
	 */
	public static String BCK_03519(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK03519ReqBean bck03519ReqBean = new BCK03519ReqBean();
		//请求报文体
		BCK03519ReqBodyBean bck03519ReqBodyBean = new BCK03519ReqBodyBean();
		bck03519ReqBodyBean.setCARD_NO(map.get("CARD_NO"));//卡号
		bck03519ReqBean.setBody(bck03519ReqBodyBean);
		//请求报文头
		bck03519ReqBean.setHeadBean(getInReqHeadBean("BCK_03519"));
		
		xstream.alias("Root", BCK03519ReqBean.class);
		xstream.alias("Body", BCK03519ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck03519ReqBean);
	}

	/**
	 * 电子子账户列表查询07819
	 */
	public static String BCK_07819(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07819ReqBean bck07819ReqBean = new BCK07819ReqBean();
		//请求报文体
		BCK07819ReqBodyBean bck07819ReqBodyBean = new BCK07819ReqBodyBean();
		bck07819ReqBodyBean.setCARD_NO(map.get("CARD_NO"));//电子卡号
		bck07819ReqBodyBean.setCUST_NO(map.get("CUST_NO"));//客户号
		bck07819ReqBean.setBody(bck07819ReqBodyBean);
		//请求报文头
		bck07819ReqBean.setHeadBean(getInReqHeadBean("BCK_07819"));
		
		xstream.alias("Root", BCK07819ReqBean.class);
		xstream.alias("Body", BCK07819ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07819ReqBean);
	}
	  
	/**
	 * 柜员认证方式查询
	 * @param map
	 * @return
	 */
	public static String BCK_07659(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK07659ReqBean bck07659ReqBean = new BCK07659ReqBean();
		bck07659ReqBean.setHeadBean(getInReqHeadBean("BCK_07659"));
		BCK07659ReqBodyBean bck07659ReqBodyBean = new BCK07659ReqBodyBean();
		bck07659ReqBodyBean.setQRY_TELLER_NO(map.get("QRY_TELLER_NO")); //设置柜员号
		bck07659ReqBean.setBody(bck07659ReqBodyBean);
		xstream.alias("Root", BCK07659ReqBean.class);
		return xstream.toXML(bck07659ReqBean);
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
		xstream.alias("Head", InReqHeadBean.class);
		xstream.alias("Body", BCK07660ReqBodyBean.class);
		return xstream.toXML(bck07660ReqBean);
	}
	
	/**
	 * 日切接口（核心接口待完善）【00002】-前置【08169】
	 */
	public static String BCK_08169(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08169ReqBean bck08169ReqBean = new BCK08169ReqBean();
		//请求报文体
		BCK08169ReqBodyBean bck08169ReqBodyBean = new BCK08169ReqBodyBean();
		bck08169ReqBodyBean.setREVERSE(map.get("REVERSE"));//保留字段
		bck08169ReqBean.setBody(bck08169ReqBodyBean);
		//请求报文头
		bck08169ReqBean.setHeadBean(getInReqHeadBean("BCK_08169"));
		
		xstream.alias("Root", BCK08169ReqBean.class);
		xstream.alias("Body", BCK08169ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck08169ReqBean);
	}
	
	/**
	 * 存单账户信息综合查询07601
	 */
	public static String BCK_07601(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07601ReqBean bCK07601ReqBean = new BCK07601ReqBean();
		//请求报文体
		BCK07601ReqBodyBean bCK07601ReqBodyBean = new BCK07601ReqBodyBean();
		bCK07601ReqBodyBean.setAcctNo(map.get("ACCT_NO"));//账号
		bCK07601ReqBodyBean.setSubAcctNo(map.get("SUB_ACCT_NO"));//SUB_ACCT_NO
		bCK07601ReqBodyBean.setChkPin(map.get("CHK_PIN"));//是否验密
		bCK07601ReqBodyBean.setPasswd(map.get("PASSWD"));//密码
		bCK07601ReqBodyBean.setCERT_TYPE(map.get("CERT_TYPE"));//凭证种类
		bCK07601ReqBodyBean.setCERT_NO_ADD(map.get("CERT_NO_ADD"));//凭证号
		bCK07601ReqBean.setBody(bCK07601ReqBodyBean);
		//请求报文头
		bCK07601ReqBean.setHeadBean(getInReqHeadBean("BCK_07601"));
		
		xstream.alias("Root", BCK07601ReqBean.class);
		xstream.alias("Body", BCK07601ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bCK07601ReqBean);
	}
	
	/**
	 * 挂失【75001】-前置【08190】
	 */
	public static String BCK_08190(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08190ReqBean bCK08190ReqBean = new BCK08190ReqBean();
		//请求报文体
		BCK08190ReqBodyBean bCK08190ReqBodyBean = new BCK08190ReqBodyBean();
		bCK08190ReqBodyBean.setLOST_TYPE(map.get("LOST_TYPE"));//挂失选择
		bCK08190ReqBodyBean.setCARD_NO(map.get("CARD_NO"));//卡号
		bCK08190ReqBodyBean.setCARD_PWD(map.get("CARD_PWD"));//卡密码
		bCK08190ReqBodyBean.setLOST_ID_TYPE(map.get("LOST_ID_TYPE"));//挂失证件类型
		bCK08190ReqBodyBean.setLOST_ID_NO(map.get("LOST_ID_NO"));//挂失证件号码
		bCK08190ReqBodyBean.setFEE(map.get("FEE"));//手续费
		bCK08190ReqBodyBean.setAGENT_ID_TYPE(map.get("AGENT_ID_TYPE"));//代理人证件类型
		bCK08190ReqBodyBean.setAGENT_ID_NO(map.get("AGENT_ID_NO"));//代理人证件号
		bCK08190ReqBodyBean.setAGENT_NAME(map.get("AGENT_NAME"));//代理人名称
		bCK08190ReqBodyBean.setAGENT_ADDR(map.get("AGENT_ADDR"));//代理人地址
		bCK08190ReqBodyBean.setAGENT_PHONE(map.get("AGENT_PHONE"));//代理人电话
		bCK08190ReqBodyBean.setCHL_NO(map.get("CHL_NO"));//渠道号
		bCK08190ReqBodyBean.setAPPL_NO(map.get("APPL_NO"));//挂失申请书号
		bCK08190ReqBodyBean.setFEE_FLAG(map.get("FEE_FLAG"));//是否收手续费
		bCK08190ReqBodyBean.setFEE_AMT(map.get("FEE_AMT"));//手续费金额
		bCK08190ReqBodyBean.setDE_REASON(map.get("DE_REASON"));//减免原因
		bCK08190ReqBodyBean.setCASH_FLAG(map.get("CASH_FLAG"));//现转标志
		
		bCK08190ReqBean.setBody(bCK08190ReqBodyBean);
		//请求报文头
		bCK08190ReqBean.setHeadBean(getInReqHeadBean("BCK_08190"));
		
		xstream.alias("Root", BCK08190ReqBean.class);
		xstream.alias("Body", BCK08190ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bCK08190ReqBean);
	}
	
	
	/**
	 * 账户正式挂失(85024)-前置【08193】
	 */
	public static String BCK_08193(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08193ReqBean bCK08193ReqBean = new BCK08193ReqBean();
		//请求报文体
		BCK08193ReqBodyBean bCK08193ReqBodyBean = new BCK08193ReqBodyBean();
		bCK08193ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));//账号
		bCK08193ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));//子账号
		bCK08193ReqBodyBean.setPROD_CODE(map.get("PROD_CODE"));//产品代码
		bCK08193ReqBodyBean.setCUST_NAME(map.get("CUST_NAME"));//客户名称
		bCK08193ReqBodyBean.setLOST_TYPE(map.get("LOST_TYPE"));//挂失类型
		bCK08193ReqBodyBean.setBALANCE(map.get("BALANCE"));//单折余额
		bCK08193ReqBodyBean.setOPEN_DATE(map.get("OPEN_DATE"));//开户日期
		bCK08193ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));//证件类型
		bCK08193ReqBodyBean.setID_NO(map.get("ID_NO"));//证件号码
		bCK08193ReqBodyBean.setISSUE_BRANCH(map.get("ISSUE_BRANCH"));//发证机关
		bCK08193ReqBodyBean.setPASSWORD(map.get("PASSWORD"));//密码
		bCK08193ReqBodyBean.setLOST_ID_TYPE(map.get("LOST_ID_TYPE"));//证件类型（挂）
		bCK08193ReqBodyBean.setLOST_ID_NO(map.get("LOST_ID_NO"));//证件号码（挂）
		bCK08193ReqBodyBean.setLOST_ISSUE_BRANCH(map.get("LOST_ISSUE_BRANCH"));//发证机关（挂）
		bCK08193ReqBodyBean.setDEPU_FLAG(map.get("DEPU_FLAG"));//是否代理挂失
		bCK08193ReqBodyBean.setDEPU_ID_TYPE(map.get("DEPU_ID_TYPE"));//代理挂失人证件
		bCK08193ReqBodyBean.setDEPU_ID_NO(map.get("DEPU_ID_NO"));//代理挂失人证件号码
		bCK08193ReqBodyBean.setDEPU_ISSUE_BRANCH(map.get("DEPU_ISSUE_BRANCH"));//代理挂失人发证机关
		bCK08193ReqBodyBean.setAGENT_NAME(map.get("AGENT_NAME"));//代理人名称
		bCK08193ReqBodyBean.setAGENT_PHONE(map.get("AGENT_PHONE"));//代理人电话
		bCK08193ReqBodyBean.setCHL_NO(map.get("CHL_NO"));//渠道号
		bCK08193ReqBodyBean.setAGENT_ADDR(map.get("AGENT_ADDR"));//代理人地址
		bCK08193ReqBodyBean.setFEE(map.get("FEE"));//手续费
		bCK08193ReqBean.setBody(bCK08193ReqBodyBean);
		//请求报文头
		bCK08193ReqBean.setHeadBean(getInReqHeadBean("BCK_08193"));
		
		xstream.alias("Root", BCK08193ReqBean.class);
		xstream.alias("Body", BCK08193ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bCK08193ReqBean);
	}
	
	
	/**
	 * 客户基本信息查询 04422
	 * @return
	 */
	public static String BCK_04422(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK04422ReqBean bCK04422ReqBean = new BCK04422ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_04422");	
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		bCK04422ReqBean.setHeadBean(inReqHeadBean);		
		BCK04422ReqBodyBean bCK04422ReqBodyBean = new BCK04422ReqBodyBean();
		bCK04422ReqBodyBean.setRESOLVE_TYPE(map.get("RESOLVE_TYPE"));
		bCK04422ReqBodyBean.setECIF_CUST_NO(map.get("ECIF_CUST_NO"));
		bCK04422ReqBodyBean.setPARTY_NAME(map.get("PARTY_NAME"));
		bCK04422ReqBodyBean.setCERT_TYPE(map.get("CERT_TYPE"));
		bCK04422ReqBodyBean.setCERT_NO(map.get("CERT_NO"));
		bCK04422ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));
		bCK04422ReqBean.setBody(bCK04422ReqBodyBean);
		xstream.alias("ROOT", BCK04422ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK04422ReqBodyBean.class);
		return xstream.toXML(bCK04422ReqBean);
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
	 * 理财客户销户可销户状态查询【520104】-前置【08199】
	 * @return
	 */
	public static String BCK_08199(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08199ReqBean bCK08199ReqBean = new BCK08199ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_08199");	
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		bCK08199ReqBean.setHeadBean(inReqHeadBean);		
		BCK08199ReqBodyBean bCK08199ReqBodyBean = new BCK08199ReqBodyBean();
		bCK08199ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));
		bCK08199ReqBean.setBody(bCK08199ReqBodyBean);
		xstream.alias("ROOT", BCK08199ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK08199ReqBodyBean.class);
		return xstream.toXML(bCK08199ReqBean);
	}
	
	/**
	 * 帐户/卡户是否关联贷款信息查询【02945】-前置【08240】
	 * @return
	 */
	public static String BCK_08240(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08240ReqBean bCK08240ReqBean = new BCK08240ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_08240");	
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		bCK08240ReqBean.setHeadBean(inReqHeadBean);		
		BCK08240ReqBodyBean bCK08240ReqBodyBean = new BCK08240ReqBodyBean();
		bCK08240ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));
		bCK08240ReqBean.setBody(bCK08240ReqBodyBean);
		xstream.alias("ROOT", BCK08240ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK08240ReqBodyBean.class);
		return xstream.toXML(bCK08240ReqBean);
	}
	
	/**
	 * 挂失补发02960（新增）-前置【08181】
	 */
	public static String BCK_08181(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08181ReqBean bck08181ReqBean = new BCK08181ReqBean();
		bck08181ReqBean.setHeadBean(getInReqHeadBean("BCK_08181"));
		
		BCK08181ReqBodyBean bck08181ReqBodyBean = new BCK08181ReqBodyBean();
		bck08181ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));//账号
		bck08181ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));//子账号
		bck08181ReqBodyBean.setPROD_COD(map.get("PROD_CODE"));//产品代码
		bck08181ReqBodyBean.setCUST_NO(map.get("CUST_NO"));//客户号
		bck08181ReqBodyBean.setCUST_NAME(map.get("CUST_NAME"));//客户名称
		bck08181ReqBodyBean.setBALANCE(map.get("BALANCE"));//余额
		bck08181ReqBodyBean.setDEP_TERM(map.get("DEP_TERM"));//存期
		bck08181ReqBodyBean.setPASSWORD(map.get("PASSWORD"));//密码
		bck08181ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));//证件类型
		bck08181ReqBodyBean.setID_NO(map.get("ID_NO"));//证件号码
		bck08181ReqBodyBean.setISSUE_BRANCH(map.get("ISSUE_BRANCH"));//发证机关
		bck08181ReqBodyBean.setNEW_CERT_NO(map.get("NEW_CERT_NO"));//新凭证号码
		
		bck08181ReqBean.setBody(bck08181ReqBodyBean);
		
		xstream.alias("Root", BCK08181ReqBean.class);
		xstream.alias("Body", BCK08181ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck08181ReqBean);
	}
	
	/**
	 * 4.9	产品利率信息查询
	 * @param map
	 * @return
	 */
	public static String BCK_02864(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK02864ReqBean bck02864ReqBean = new BCK02864ReqBean();
		bck02864ReqBean.setHeadBean(getInReqHeadBean("BCK_02864"));
		BCK02864ReqBodyBean bck02864ReqBodyBean = new BCK02864ReqBodyBean();
		bck02864ReqBodyBean.setPROD_CODE(map.get("PROD_CODE")); //产品代码
		bck02864ReqBodyBean.setRATE_DATE(map.get("RATE_DATE"));//利率日期
		bck02864ReqBodyBean.setFLOAT_FLAG(map.get("FLOAT_FLAG"));//浮动标志
		bck02864ReqBodyBean.setCUST_NO(map.get("CUST_NO"));//客户号
		bck02864ReqBodyBean.setCHL_ID(map.get("CHL_ID"));//渠道模块标识
		bck02864ReqBodyBean.setCUST_LEVEL(map.get("CUST_LEVEL"));//客户评级
		bck02864ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));//子账号
		bck02864ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));//账号
		bck02864ReqBean.setBody(bck02864ReqBodyBean);
		xstream.alias("Root", BCK02864ReqBean.class);
		return xstream.toXML(bck02864ReqBean);
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
	 * 更改凭证信息
	 */
	public static String Tms0006(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));

		xstream.autodetectAnnotations(true);
		Tms0005ReqBean tms0005ReqBean = new Tms0005ReqBean();
		Tms0005ReqBodyBean tms0005ReqBodyBean = new Tms0005ReqBodyBean();
		tms0005ReqBodyBean.setISNo(map.get("isNo")); //标识
		tms0005ReqBodyBean.setID(map.get("id")); //主键
		tms0005ReqBodyBean.setMACHINE_NO(GlobalParameter.machineNo); //机器编号
		tms0005ReqBodyBean.setSTART_NO(map.get("startNo")); //起始号
		tms0005ReqBodyBean.setEND_NO(map.get("endNo")); //结尾号
		tms0005ReqBodyBean.setNOW_BO(map.get("nowNo")); //当前号
		tms0005ReqBodyBean.setCREATE_DATE(map.get("createDate")); //创建时间
		tms0005ReqBodyBean.setUPDATE_DATE(map.get("updateDate")); //修改时间
		tms0005ReqBean.setBodyBean(tms0005ReqBodyBean);
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("TMS_0006");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		tms0005ReqBean.setHeadBean(inReqHeadBean);
		xstream.alias("Root", Tms0005ReqBean.class);
		return xstream.toXML(tms0005ReqBean);
	}
	/**
	 * 一本通账号查询【20098】-前置【08176】
	 * @param map
	 * @return
	 */
	public static String BCK_08176(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08176ReqBean bck08176ReqBean = new BCK08176ReqBean();
		//请求报文体
		BCK08176ReqBodyBean bck08176ReqBodyBean = new BCK08176ReqBodyBean();
		bck08176ReqBodyBean.setREAL_ACCT_NO(map.get("REAL_ACCT_NO"));//REAL_ACCT_NO	实际账号		
		bck08176ReqBean.setBody(bck08176ReqBodyBean);
		//请求报文头
		bck08176ReqBean.setHeadBean(getInReqHeadBean("BCK_08176"));		
		xstream.alias("Root", BCKQRY00ReqBean.class);
		xstream.alias("Body", BCKQRY00ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck08176ReqBean);
	}
	
	/**
	 * 挂失销户限额查询（20097）-前置【08177】(核心)
	 * @param map
	 * @return
	 */
	public static String BCK_08177(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08177ReqBean bck08177ReqBean = new BCK08177ReqBean();
		//请求报文体
		BCK08177ReqBodyBean bck08177ReqBodyBean = new BCK08177ReqBodyBean();
		bck08177ReqBodyBean.setCHL_NO(map.get("CHL_NO"));//渠道号
		bck08177ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));//账号
		bck08177ReqBean.setBody(bck08177ReqBodyBean);
		//请求报文头
		bck08177ReqBean.setHeadBean(getInReqHeadBean("BCK_08177"));		
		xstream.alias("Root", BCK08177ReqBean.class);
		xstream.alias("Body", BCK08177ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck08177ReqBean);
	}
	
	/**
	 * 挂失销户限额查询（75209）-前置【08178】(卡系统)
	 * @param map
	 * @return
	 */
	public static String BCK_08178(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08178ReqBean bck08178ReqBean = new BCK08178ReqBean();
		//请求报文体
		BCK08178ReqBodyBean bck08178ReqBodyBean = new BCK08178ReqBodyBean();
		bck08178ReqBodyBean.setCHL_NO(map.get("CHL_NO"));//渠道号
		bck08178ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));//账号
		bck08178ReqBean.setBody(bck08178ReqBodyBean);
		//请求报文头
		bck08178ReqBean.setHeadBean(getInReqHeadBean("BCK_08178"));		
		xstream.alias("Root", BCK08178ReqBean.class);
		xstream.alias("Body", BCK08178ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck08178ReqBean);
	}
	
	/**
	 * 代理人身份信息查询
	 * @return
	 */
	public static String BCK_08021(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08021ReqBean bCK08021ReqBean = new BCK08021ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_08021");	
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		bCK08021ReqBean.setHeadBean(inReqHeadBean);		
		BCK08021ReqBodyBean bCK08021ReqBodyBean = new BCK08021ReqBodyBean();
		bCK08021ReqBodyBean.setID_NO(map.get("ID_NO"));
		bCK08021ReqBodyBean.setNAME(map.get("NAME"));
		bCK08021ReqBean.setBody(bCK08021ReqBodyBean);
		xstream.alias("ROOT", BCK08021ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK08021ReqBodyBean.class);
		return xstream.toXML(bCK08021ReqBean);
	}
	
	/**
	 * 账户挂失销户挂失销户02961（新增）-前置【08182】
	 */
	public static String BCK_08182(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08182ReqBean bCK08182ReqBean = new BCK08182ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_08182");	
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		bCK08182ReqBean.setHeadBean(inReqHeadBean);		
		BCK08182ReqBodyBean bCK08182ReqBodyBean = new BCK08182ReqBodyBean();
		bCK08182ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));
		bCK08182ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));
		bCK08182ReqBodyBean.setPROD_CODE(map.get("PROD_CODE"));
		bCK08182ReqBodyBean.setCUST_NO(map.get("CUST_NO"));
		bCK08182ReqBodyBean.setCUST_NAME(map.get("CUST_NAME"));
		bCK08182ReqBodyBean.setBALANCE(map.get("BALANCE"));
		bCK08182ReqBodyBean.setSTART_DATE(map.get("START_DATE"));
		bCK08182ReqBodyBean.setDEP_TERM(map.get("DEP_TERM"));
		bCK08182ReqBodyBean.setOPEN_RATE(map.get("OPEN_RATE"));
		bCK08182ReqBodyBean.setLOST_DATE(map.get("LOST_DATE"));
		bCK08182ReqBodyBean.setPAY_COND(map.get("PAY_COND"));
		bCK08182ReqBodyBean.setPASSWORD(map.get("PASSWORD"));
		bCK08182ReqBodyBean.setID_NO(map.get("ID_NO"));
		bCK08182ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));
		bCK08182ReqBodyBean.setISSUE_BRANCH(map.get("ISSUE_BRANCH"));
		bCK08182ReqBodyBean.setDRAW_CURRENCY(map.get("DRAW_CURRENCY"));
		bCK08182ReqBodyBean.setDRAW_AMT(map.get("DRAW_AMT"));
		bCK08182ReqBodyBean.setPRI_WAY(map.get("PRI_WAY"));
		bCK08182ReqBodyBean.setINT_WAY(map.get("INT_WAY"));
		bCK08182ReqBodyBean.setPRE_DATE(map.get("PRE_DATE"));
		bCK08182ReqBodyBean.setOPP_ACCT_NO(map.get("OPP_ACCT_NO"));
		bCK08182ReqBodyBean.setBOOK_NO(map.get("BOOK_NO"));
		bCK08182ReqBean.setBody(bCK08182ReqBodyBean);
		xstream.alias("ROOT", BCK08182ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK08182ReqBodyBean.class);
		return xstream.toXML(bCK08182ReqBean);
	}
	
	/**
	 * 卡挂失销户70030（新增）-前置【08186】
	 */
	public static String BCK_08186(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08186ReqBean bCK08186ReqBean = new BCK08186ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_08186");	
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		bCK08186ReqBean.setHeadBean(inReqHeadBean);		
		BCK08186ReqBodyBean bCK08186ReqBodyBean = new BCK08186ReqBodyBean();
		bCK08186ReqBodyBean.setCARD_NO(map.get("CARD_NO"));
		bCK08186ReqBodyBean.setPASSWORD(map.get("PASSWORD"));
		bCK08186ReqBodyBean.setLOST_DATE(map.get("LOST_DATE"));
		bCK08186ReqBodyBean.setCERT_TYPE(map.get("CERT_TYPE"));
		bCK08186ReqBodyBean.setCERT_NO(map.get("CERT_NO"));
		bCK08186ReqBodyBean.setCASH_FLAG(map.get("CASH_FLAG"));
		bCK08186ReqBodyBean.setOPP_ACCT_NO(map.get("OPP_ACCT_NO"));
		bCK08186ReqBodyBean.setID_NO(map.get("ID_NO"));
		bCK08186ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));
		bCK08186ReqBodyBean.setCHL_NO(map.get("CHL_NO"));
		bCK08186ReqBodyBean.setOPEN_FLAG(map.get("OPEN_FLAG"));
		bCK08186ReqBean.setBody(bCK08186ReqBodyBean);
		xstream.alias("ROOT", BCK08186ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK08186ReqBodyBean.class);
		return xstream.toXML(bCK08186ReqBean);
	}
	
//	/**
//	 * 取消约定转存【75032】前置【08189】   ----已废弃，未使用
//	 */
//	public static String BCK_08189(Map<String,String> map){
//		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
//		xstream.autodetectAnnotations(true);
//		BCK08189ReqBean bCK08189ReqBean = new BCK08189ReqBean();
//		InReqHeadBean inReqHeadBean = new InReqHeadBean();
//		inReqHeadBean.setTradeCode("BCK_08189");	
//		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
//		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
//		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
//		bCK08189ReqBean.setHeadBean(inReqHeadBean);		
//		BCK08189ReqBodyBean bCK08189ReqBodyBean = new BCK08189ReqBodyBean();
//		bCK08189ReqBodyBean.setCARD_NO(map.get("CARD_NO"));
//		bCK08189ReqBodyBean.setCARD_PWD(map.get("CARD_PWD"));
//		bCK08189ReqBodyBean.setCERT_TYPE(map.get("CERT_TYPE"));
//		bCK08189ReqBodyBean.setCERT_NO(map.get("CERT_NO"));
//		bCK08189ReqBodyBean.setHAV_AGENT_FLAG(map.get("HAV_AGENT_FLAG"));
//		bCK08189ReqBodyBean.setAGENT_CUST_NAME(map.get("AGENT_CUST_NAME"));
//		bCK08189ReqBodyBean.setAGENT_SEX(map.get("AGENT_SEX"));
//		bCK08189ReqBodyBean.setAGENT_ID_TYPE(map.get("AGENT_ID_TYPE"));
//		bCK08189ReqBodyBean.setAGENT_ID_NO(map.get("AGENT_ID_NO"));
//		bCK08189ReqBodyBean.setAGENT_ISSUE_DATE(map.get("AGENT_ISSUE_DATE"));
//		bCK08189ReqBodyBean.setAGENT_ISSUE_INST(map.get("AGENT_ISSUE_INST"));
//		bCK08189ReqBodyBean.setAGENT_DUE_DATE(map.get("AGENT_DUE_DATE"));
//		bCK08189ReqBodyBean.setAGENT_NATION(map.get("AGENT_NATION"));
//		bCK08189ReqBodyBean.setAGENT_OCCUPATION(map.get("AGENT_OCCUPATION"));
//		bCK08189ReqBodyBean.setAGENT_REGIS_PER_RES(map.get("AGENT_REGIS_PER_RES"));
//		bCK08189ReqBodyBean.setAGENT_J_C_ADD(map.get("AGENT_J_C_ADD"));
//		bCK08189ReqBodyBean.setAGENT_TELEPHONE(map.get("AGENT_TELEPHONE"));
//		bCK08189ReqBodyBean.setAGENT_MOB_PHONE(map.get("AGENT_MOB_PHONE"));
//		bCK08189ReqBean.setBody(bCK08189ReqBodyBean);
//		xstream.alias("ROOT", BCK08189ReqBean.class);
//		xstream.alias("HEAD", InReqHeadBean.class);
//		xstream.alias("BODY", BCK08189ReqBodyBean.class);
//		return xstream.toXML(bCK08189ReqBean);
//	}
	
	/**
	 * 挂失申请书打印查询02962-前置【08179】（到核心）
	 * @param map
	 * @return
	 */
	public static String BCK_08179(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08179ReqBean bck08179ReqBean = new BCK08179ReqBean();
		//请求报文体
		BCK08179ReqBodyBean bck08179ReqBodyBean = new BCK08179ReqBodyBean();
		bck08179ReqBodyBean.setDATE(map.get("DATE"));
		bck08179ReqBodyBean.setINST_NO_I(map.get("INST_NO_I"));
		bck08179ReqBodyBean.setJRNL_NO_I(map.get("JRNL_NO_I"));
		bck08179ReqBodyBean.setLOST_APPL_NO(map.get("LOST_APPL_NO"));
		bck08179ReqBodyBean.setTELLER_NO_I(map.get("TELLER_NO_I"));
		bck08179ReqBean.setBody(bck08179ReqBodyBean);
		//请求报文头
		bck08179ReqBean.setHeadBean(getInReqHeadBean("BCK_08179"));		
		xstream.alias("Root", BCK08179ReqBean.class);
		xstream.alias("Body", BCK08179ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck08179ReqBean);
	}
	
	/**
	 * 挂失申请书打印查询70031-前置【08180】（到卡系统）
	 * @param map
	 * @return
	 */
	public static String BCK_08180(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08180ReqBean bck08180ReqBean = new BCK08180ReqBean();
		//请求报文体
		BCK08180ReqBodyBean bck08180ReqBodyBean = new BCK08180ReqBodyBean();
		bck08180ReqBodyBean.setDATE(map.get("DATE"));
		bck08180ReqBodyBean.setINST_NO_I(map.get("INST_NO_I"));
		bck08180ReqBodyBean.setJRNL_NO_I(map.get("JRNL_NO_I"));
		bck08180ReqBodyBean.setLOST_APPL_NO(map.get("LOST_APPL_NO"));
		bck08180ReqBodyBean.setTELLER_NO_I(map.get("TELLER_NO_I"));
		bck08180ReqBean.setBody(bck08180ReqBodyBean);
		//请求报文头
		bck08180ReqBean.setHeadBean(getInReqHeadBean("BCK_08180"));		
		xstream.alias("Root", BCK08180ReqBean.class);
		xstream.alias("Body", BCK08180ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck08180ReqBean);
	}
	
	/**
	 * 销户查询（100304） –前置 【08160】
	 * @param map
	 * @return
	 */
	public static String BCK_08160(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08160ReqBean bck08160ReqBean = new BCK08160ReqBean();
		//请求报文体
		BCK08160ReqBodyBean bck08160ReqBodyBean = new BCK08160ReqBodyBean();
		bck08160ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));
		bck08160ReqBodyBean.setID_NO(map.get("ID_NO"));
		bck08160ReqBodyBean.setOPEN_INST(map.get("OPEN_INST"));
		bck08160ReqBean.setBody(bck08160ReqBodyBean);
		//请求报文头
		bck08160ReqBean.setHeadBean(getInReqHeadBean("BCK_08160"));		
		xstream.alias("Root", BCK08160ReqBean.class);
		xstream.alias("Body", BCK08160ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck08160ReqBean);
	}
	
	/**
	 * 唐豆回收查询07622
	 */
	public static String BCK_07622(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07622ReqBean bck07622ReqBean = new BCK07622ReqBean();
		//请求报文体
		BCK07622ReqBodyBean bck07622ReqBodyBean = new BCK07622ReqBodyBean();
		bck07622ReqBodyBean.setAcctNo(map.get("ACCT_NO"));//账号
		bck07622ReqBodyBean.setSubAcctNo(map.get("SUB_ACCT_NO"));//子账户
		bck07622ReqBodyBean.setPayDate(map.get("PAY_DATE"));//日期
		bck07622ReqBodyBean.setPayJrnl(map.get("PAY_JRNL"));//流水号
		bck07622ReqBean.setBody(bck07622ReqBodyBean);
		//请求报文头
		bck07622ReqBean.setHeadBean(getInReqHeadBean("BCK_07622"));
		
		xstream.alias("Root", BCK07622ReqBean.class);
		xstream.alias("Body", BCK07622ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07622ReqBean);
	}
	/**
	 * 唐豆回收07665
	 */
	public static String BCK_07665(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07665ReqBean bck07665ReqBean = new BCK07665ReqBean();
		//请求报文体
		BCK07665ReqBodyBean bck07665ReqBodyBean = new BCK07665ReqBodyBean();
		bck07665ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));//账号
		bck07665ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));//子账号
		bck07665ReqBodyBean.setPAY_DATE(map.get("PAY_DATE"));//支取日期
		bck07665ReqBodyBean.setPAY_JRNL(map.get("PAY_JRNL"));//支取流水
		bck07665ReqBodyBean.setPAY_AMT(map.get("PAY_AMT"));//支取金额
		bck07665ReqBodyBean.setORG_EXCHANGE_MODE(map.get("ORG_EXCHANGE_MODE"));//原唐豆兑换方式
		bck07665ReqBodyBean.setORG_EXCHANGE_PROP(map.get("ORG_EXCHANGE_PROP"));//原唐豆兑现比例
		bck07665ReqBodyBean.setBACK_COUNT(map.get("BACK_COUNT"));//收回唐豆数量
		bck07665ReqBodyBean.setBACK_PROP(map.get("BACK_PROP"));//收回比例
		bck07665ReqBodyBean.setBACK_EXCHANGE_AMT(map.get("BACK_EXCHANGE_AMT"));//收回兑现金额
		bck07665ReqBodyBean.setRECOV_TYPE(map.get("RECOV_TYPE"));//唐豆收回方式
		bck07665ReqBodyBean.setOPPO_ACCT_NO(map.get("OPPO_ACCT_NO"));//对方账号
		bck07665ReqBodyBean.setPASSWORD(map.get("PASSWORD"));//对方账号密码
		bck07665ReqBean.setBody(bck07665ReqBodyBean);
		//请求报文头
		bck07665ReqBean.setHeadBean(getInReqHeadBean("BCK_07665"));
		
		xstream.alias("Root", BCK07665ReqBean.class);
		xstream.alias("Body", BCK07665ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07665ReqBean);
	}
	
	/**
	 * 弱密码较验
	 * @param map
	 * @return
	 */
	public static String BCK_01325(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK01325ReqBean bck01325ReqBean = new BCK01325ReqBean();
		
		//请求报文体
		BCK01325ReqBodyBean bck01325ReqBodyBean = new BCK01325ReqBodyBean();
		bck01325ReqBodyBean.setNEW_PASS_WORD(map.get("NEW_PASS_WORD"));
		bck01325ReqBodyBean.setACCT_NO1(map.get("ACCT_NO1"));
		bck01325ReqBean.setBody(bck01325ReqBodyBean);
		//请求报文头
		bck01325ReqBean.setHeadBean(getInReqHeadBean("BCK_01325"));
		
		xstream.alias("Root", BCK01325ReqBean.class);
		xstream.alias("Body", BCK01325ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck01325ReqBean);
	}
	
	
	/**
	 * 解挂【75002】-前置【08198】
	 * @param map
	 * @return
	 */
	public static String BCK_08198(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08198ReqBean bck08198ReqBean = new BCK08198ReqBean();
		
		//请求报文体
		BCK08198ReqBodyBean bck08198ReqBodyBean = new BCK08198ReqBodyBean();
		bck08198ReqBodyBean.setLOST_TYPE(map.get("LOST_TYPE"));//挂式类型
		bck08198ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));//证件类别
		bck08198ReqBodyBean.setID_NO(map.get("ID_NO"));//证件号
		bck08198ReqBodyBean.setCARD_NO(map.get("CARD_NO"));//卡号
		bck08198ReqBodyBean.setLOST_DATE(map.get("LOST_DATE"));//挂失日期
		bck08198ReqBodyBean.setAPPL_NO(map.get("APPL_NO"));//申请书号
		bck08198ReqBodyBean.setCARD_PIN(map.get("CARD_PIN"));//卡密码
		bck08198ReqBodyBean.setCHL_NO(map.get("CHL_NO"));//渠道号
		bck08198ReqBodyBean.setFEE_FLAG(map.get("FEE_FLAG"));//是否收手续费
		bck08198ReqBodyBean.setFEE_AMT(map.get("FEE_AMT"));//手续费金额
		bck08198ReqBodyBean.setDE_REASON(map.get("DE_REASON"));//减免原因
		bck08198ReqBodyBean.setCASH_FLAG(map.get("CASH_FLAG"));//现转标志
		bck08198ReqBean.setBody(bck08198ReqBodyBean);
		//请求报文头
		bck08198ReqBean.setHeadBean(getInReqHeadBean("BCK_08198"));
		
		xstream.alias("Root", BCK08198ReqBean.class);
		xstream.alias("Body", BCK08198ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck08198ReqBean);
	}
	
	/**
	 * 账户正式挂失解挂【02817】-前置【08194】
	 * @param map
	 * @return
	 */
	public static String BCK_08194(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08194ReqBean bck08194ReqBean = new BCK08194ReqBean();
		
		//请求报文体
		BCK08194ReqBodyBean bck08194ReqBodyBean = new BCK08194ReqBodyBean();
		bck08194ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));//账户
		bck08194ReqBodyBean.setPRO_CODE(map.get("PRO_CODE"));//产品代码
		bck08194ReqBodyBean.setCUST_NAME(map.get("CUST_NAME"));//客户名称
		bck08194ReqBodyBean.setDISL_TYPE(map.get("DISL_TYPE"));//解挂选择
		bck08194ReqBodyBean.setLOST_DATE(map.get("LOST_DATE"));//挂失日期
		bck08194ReqBodyBean.setCRET_INST(map.get("CRET_INST"));//发证机关
		bck08194ReqBodyBean.setLOST_APP_NO(map.get("LOST_APP_NO"));//挂失申请书号
		bck08194ReqBodyBean.setCHL_NO(map.get("CHL_NO"));//渠道号
		bck08194ReqBean.setBody(bck08194ReqBodyBean);
		//请求报文头
		bck08194ReqBean.setHeadBean(getInReqHeadBean("BCK_08194"));
		
		xstream.alias("Root", BCK08194ReqBean.class);
		xstream.alias("Body", BCK08194ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck08194ReqBean);
	}
	
	
//	/**
//	    * 修改支付条件【02805】-前置07517
//	    * @param map
//	    * @return
//	    */
//	 public static String BCK_07517(Map<String, String> map) {
//	  XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
//	  xstream.autodetectAnnotations(true);
//	  BCK07517ReqBean bck07517ReqBean = new BCK07517ReqBean();
//	  //请求报文体
//	  BCK07517ReqBodyBean bck07517ReqBodyBean = new BCK07517ReqBodyBean();
//	  bck07517ReqBodyBean.setACCT_NO(map.get("ACC_NO"));
//	  bck07517ReqBodyBean.setCUST_NO(map.get("CUST_NO"));
//	  bck07517ReqBodyBean.setCUST_NAME(map.get("CUST_NAME"));
//	  bck07517ReqBodyBean.setCERT_NO(map.get("CERT_NO"));
//	  bck07517ReqBodyBean.setOLD_PAY_COND(map.get("OLD_PAY_COND"));
//	  bck07517ReqBodyBean.setNEW_PAY_COND(map.get("NEW_PAY_COND"));
//	  bck07517ReqBodyBean.setCHANGE_REASON(map.get("CHANGE_REASON"));
//	  bck07517ReqBodyBean.setOLD_PASSWORD(map.get("OLD_PASSWORD"));
//	  bck07517ReqBodyBean.setNEW_PASSWORD(map.get("NEW_PASSWORD"));
//	  bck07517ReqBodyBean.setLOST_APPL_NO(map.get("LOST_APPL_NO"));
//	  bck07517ReqBodyBean.setID_NO(map.get("ID_NO"));
//	  bck07517ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));
//	  bck07517ReqBean.setBody(bck07517ReqBodyBean);
//	  //请求报文头
//	  bck07517ReqBean.setHeadBean(getInReqHeadBean("BCK_07517"));
//	    
//	  xstream.alias("Root", BCK07517ReqBean.class);
//	  xstream.alias("Body", BCK07517ReqBodyBean.class);
//	  xstream.alias("Head", InReqHeadBean.class);
//	  return xstream.toXML(bck07517ReqBean);
//	  
//	 }
//	 
//	 /**
//	    * 改密【75005】-前置【08159】
//	    * @param map
//	    * @return
//	    */
//	 public static String BCK_08159(Map<String, String> map) {
//	  XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
//	  xstream.autodetectAnnotations(true);
//	  BCK08159ReqBean bck08159ReqBean = new BCK08159ReqBean();
//	  //请求报文体
//	  BCK08159ReqBodyBean bck08159ReqBodyBean = new BCK08159ReqBodyBean();
//	  bck08159ReqBodyBean.setNEW_CARD_PWD(map.get("NEW_CARD_PWD"));//新密码
//	  bck08159ReqBodyBean.setORIG_CARD_PWD(map.get("ORIG_CARD_PWD"));//原密码
//	  bck08159ReqBodyBean.setCARD_NO2(map.get("CARD_NO2"));//卡号
//	  bck08159ReqBodyBean.setCHG_REASON(map.get("CHG_REASON"));//更换原因
//	  bck08159ReqBodyBean.setLOST_APPL_NO(map.get("LOST_APPL_NO"));//挂失申请书号
//	  bck08159ReqBodyBean.setLOST_DATE(map.get("LOST_DATE"));//挂失日期
//	  bck08159ReqBodyBean.setINPUT_NAME(map.get("INPUT_NAME"));//姓名
//	  bck08159ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));//证件类型
//	  bck08159ReqBodyBean.setID_NO(map.get("ID_NO"));//证件号码
//	  bck08159ReqBodyBean.setCHL_NO(map.get("CHL_NO"));//渠道号
//	  
//	  bck08159ReqBean.setBody(bck08159ReqBodyBean);
//	  //请求报文头
//	  bck08159ReqBean.setHeadBean(getInReqHeadBean("BCK_08159"));
//	    
//	  xstream.alias("Root", BCK08159ReqBean.class);
//	  xstream.alias("Body", BCK08159ReqBodyBean.class);
//	  xstream.alias("Head", InReqHeadBean.class);
//	  return xstream.toXML(bck08159ReqBean);
//	  
//	 }
//	 
//	 /**
//	 * 挂失解挂凭证号信息综合查询02791
//	 */
//	public static String BCK_02791(Map<String,String> map){
//		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
//		xstream.autodetectAnnotations(true);
//		BCK02791ReqBean bCK02791ReqBean = new BCK02791ReqBean();
//		//请求报文体
//		BCK02791ReqBodyBean bCK02791ReqBodyBean = new BCK02791ReqBodyBean();
//		
//		bCK02791ReqBodyBean.setCERT_TYPE(map.get("CERT_TYPE"));//凭证种类
//		bCK02791ReqBodyBean.setCERT_NO(map.get("CERT_NO"));//凭证号
//		bCK02791ReqBean.setBody(bCK02791ReqBodyBean);
//		//请求报文头
//		bCK02791ReqBean.setHeadBean(getInReqHeadBean("BCK_02791"));
//		
//		xstream.alias("Root", BCK02791ReqBean.class);
//		xstream.alias("Body", BCK02791ReqBodyBean.class);
//		xstream.alias("Head", InReqHeadBean.class);
//		return xstream.toXML(bCK02791ReqBean);
//	}
	 /**
	  * 挂失解挂账户信息全面查询
	  */
	public static String BCK_08223(Map<String, String> map) {
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08223ReqBean bCK08223ReqBean = new BCK08223ReqBean();
		// 请求报文体
		BCK08223ReqBodyBean bCK08223ReqBodyBean = new BCK08223ReqBodyBean();

		bCK08223ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));
		bCK08223ReqBodyBean.setCUST_NAME(map.get("CUST_NO"));
		bCK08223ReqBodyBean.setCUST_NO(map.get("CUST_NAME"));
		bCK08223ReqBodyBean.setQRY_TYPE(map.get("QRY_TYPE"));
		bCK08223ReqBean.setBody(bCK08223ReqBodyBean);
		// 请求报文头 
		bCK08223ReqBean.setHeadBean(getInReqHeadBean("BCK_08223"));

		xstream.alias("Root", BCK08223ReqBean.class);
		xstream.alias("Body", BCK08223ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bCK08223ReqBean);
	}
	 /**
	  * (核心)挂失补发（7天）【02834】-前置【08230】
	  */
	public static String BCK_08230(Map<String, String> map) {
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08230ReqBean bCK08230ReqBean = new BCK08230ReqBean();
		// 请求报文体
		BCK08230ReqBodyBean bCK08230ReqBodyBean = new BCK08230ReqBodyBean();

		bCK08230ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));
		bCK08230ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));
		bCK08230ReqBodyBean.setPRO_CODE(map.get("PRO_CODE"));
		bCK08230ReqBodyBean.setAPP_NO(map.get("APP_NO"));
		bCK08230ReqBodyBean.setCUST_NO(map.get("CUST_NO"));
		bCK08230ReqBodyBean.setCUST_NAME(map.get("CUST_NAME"));
		bCK08230ReqBodyBean.setBAL_AMT(map.get("BAL_AMT"));
		bCK08230ReqBodyBean.setSAVE_TERM(map.get("SAVE_TERM"));
		bCK08230ReqBodyBean.setLOST_DATE(map.get("LOST_DATE"));
		bCK08230ReqBodyBean.setPASS_WORD(map.get("PASS_WORD"));
		bCK08230ReqBodyBean.setCERT_TYPE1(map.get("CERT_TYPE1"));
		bCK08230ReqBodyBean.setCERT_NO1(map.get("CERT_NO1"));
		bCK08230ReqBodyBean.setCRET_INST1(map.get("CRET_INST1"));
		bCK08230ReqBodyBean.setCERT_TYPE2(map.get("CERT_TYPE2"));
		bCK08230ReqBodyBean.setCERT_NO2(map.get("CERT_NO2"));
		bCK08230ReqBodyBean.setCRET_INST2(map.get("CRET_INST2"));
		bCK08230ReqBodyBean.setNEW_CRET_NO(map.get("NEW_CRET_NO"));
		bCK08230ReqBean.setBody(bCK08230ReqBodyBean);
		// 请求报文头 
		bCK08230ReqBean.setHeadBean(getInReqHeadBean("BCK_08230"));

		xstream.alias("Root", BCK08230ReqBean.class);
		xstream.alias("Body", BCK08230ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bCK08230ReqBean);
	}
	
	 /**
	  * 一步解双挂并补发【02968】-前置【08257】
	  */
	public static String BCK_08257(Map<String, String> map) {
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08257ReqBean bCK08257ReqBean = new BCK08257ReqBean();
		// 请求报文体
		BCK08257ReqBodyBean bCK08257ReqBodyBean = new BCK08257ReqBodyBean();

		bCK08257ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));
		bCK08257ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));
		bCK08257ReqBodyBean.setCUST_NO(map.get("CUST_NO"));
		bCK08257ReqBodyBean.setCUST_NAME(map.get("CUST_NAME"));
		bCK08257ReqBodyBean.setPASSWORD(map.get("PASSWORD"));
		bCK08257ReqBodyBean.setREI_PASSWORD(map.get("REI_PASSWORD"));
		bCK08257ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));
		bCK08257ReqBodyBean.setID_NO(map.get("ID_NO"));
		bCK08257ReqBodyBean.setCERT_NO(map.get("CERT_NO"));
		bCK08257ReqBodyBean.setNEW_CERT_NO(map.get("NEW_CERT_NO"));
		bCK08257ReqBodyBean.setAPPL_NO(map.get("APPL_NO"));
		
		bCK08257ReqBean.setBody(bCK08257ReqBodyBean);
		// 请求报文头 
		bCK08257ReqBean.setHeadBean(getInReqHeadBean("BCK_08257"));

		xstream.alias("Root", BCK08257ReqBean.class);
		xstream.alias("Body", BCK08257ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bCK08257ReqBean);
	}
	/**
	 * (卡)挂失期满销户（7天）【70008】-前置【08222】
	 */
	public static String BCK_08222(Map<String, String> map) {
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08222ReqBean bCK08222ReqBean = new BCK08222ReqBean();
		// 请求报文体
		BCK08222ReqBodyBean bCK08222ReqBodyBean = new BCK08222ReqBodyBean();
		
		bCK08222ReqBodyBean.setCARD_NO(map.get("CARD_NO"));
		bCK08222ReqBodyBean.setCARD_PWD(map.get("CARD_PWD"));
		bCK08222ReqBodyBean.setAPPL_NO(map.get("APPL_NO"));
		bCK08222ReqBodyBean.setLOST_DATE(map.get("LOST_DATE"));
		bCK08222ReqBodyBean.setCASH_OR_CARRY(map.get("CASH_OR_CARRY"));
		bCK08222ReqBodyBean.setOPPO_ACCT_NO(map.get("OPPO_ACCT_NO"));
		bCK08222ReqBodyBean.setCERT_TYPE(map.get("CERT_TYPE"));
		bCK08222ReqBodyBean.setCERT_NO(map.get("CERT_NO"));
		bCK08222ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));
		bCK08222ReqBodyBean.setID_NO(map.get("ID_NO"));
		bCK08222ReqBodyBean.setCHL_NO(map.get("CHL_NO"));
		bCK08222ReqBean.setBody(bCK08222ReqBodyBean);
		// 请求报文头 
		bCK08222ReqBean.setHeadBean(getInReqHeadBean("BCK_08222"));
		
		xstream.alias("Root", BCK08222ReqBean.class);
		xstream.alias("Body", BCK08222ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bCK08222ReqBean);
	}
	
	/**
	 * 改密销户一体化【70036】-前置【08260】
	 */
	public static String BCK_08260(Map<String, String> map) {
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08260ReqBean bCK08260ReqBean = new BCK08260ReqBean();
		// 请求报文体
		BCK08260ReqBodyBean bCK08260ReqBodyBean = new BCK08260ReqBodyBean();
		
		bCK08260ReqBodyBean.setNEW_CARD_PWD(map.get("NEW_CARD_PWD"));
		bCK08260ReqBodyBean.setOLD_CARD_PWD(map.get("OLD_CARD_PWD"));
		bCK08260ReqBodyBean.setCARD_TYPE(map.get("CARD_TYPE"));
		bCK08260ReqBodyBean.setCHG_REASON(map.get("CHG_REASON"));
		bCK08260ReqBodyBean.setAPPL_NO(map.get("APPL_NO"));
		bCK08260ReqBodyBean.setLOST_DATE(map.get("LOST_DATE"));
		bCK08260ReqBodyBean.setCUST_NAME(map.get("CUST_NAME"));
		bCK08260ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));
		bCK08260ReqBodyBean.setID_NO(map.get("ID_NO"));
		bCK08260ReqBodyBean.setCHL_NO(map.get("CHL_NO"));
		bCK08260ReqBodyBean.setCARD_NO(map.get("CARD_NO"));
		bCK08260ReqBodyBean.setOPP_CARD_NO(map.get("OPP_CARD_NO"));
		bCK08260ReqBodyBean.setCASH_FLAG(map.get("CASH_FLAG"));
		
		bCK08260ReqBean.setBody(bCK08260ReqBodyBean);
		// 请求报文头 
		bCK08260ReqBean.setHeadBean(getInReqHeadBean("BCK_08260"));
		
		xstream.alias("Root", BCK08260ReqBean.class);
		xstream.alias("Body", BCK08260ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bCK08260ReqBean);
	}
	
	/**
	 * 激活销户一体化【70037】-前置【08262】
	 */
	public static String BCK_08262(Map<String, String> map) {
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08262ReqBean bCK08262ReqBean = new BCK08262ReqBean();
		// 请求报文体
		BCK08262ReqBodyBean bCK08262ReqBodyBean = new BCK08262ReqBodyBean();
		
		bCK08262ReqBodyBean.setNEW_CARD_PWD(map.get("NEW_CARD_PWD"));
		bCK08262ReqBodyBean.setOLD_CARD_PWD(map.get("OLD_CARD_PWD"));
		bCK08262ReqBodyBean.setCARD_TYPE(map.get("CARD_TYPE"));
		bCK08262ReqBodyBean.setCHG_REASON(map.get("CHG_REASON"));
		bCK08262ReqBodyBean.setAPPL_NO(map.get("APPL_NO"));
		bCK08262ReqBodyBean.setLOST_DATE(map.get("LOST_DATE"));
		bCK08262ReqBodyBean.setCUST_NAME(map.get("CUST_NAME"));
		bCK08262ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));
		bCK08262ReqBodyBean.setID_NO(map.get("ID_NO"));
		bCK08262ReqBodyBean.setCHL_NO(map.get("CHL_NO"));
		bCK08262ReqBodyBean.setCARD_NO(map.get("CARD_NO"));
		bCK08262ReqBodyBean.setOPP_CARD_NO(map.get("OPP_CARD_NO"));
		bCK08262ReqBodyBean.setCASH_FLAG(map.get("CASH_FLAG"));
		
		bCK08262ReqBean.setBody(bCK08262ReqBodyBean);
		// 请求报文头 
		bCK08262ReqBean.setHeadBean(getInReqHeadBean("BCK_08262"));
		
		xstream.alias("Root", BCK08262ReqBean.class);
		xstream.alias("Body", BCK08262ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bCK08262ReqBean);
	}
	/**
	 * (核心)挂失期满销户（7天）【02833】-前置【08231】
	 */
	public static String BCK_08231(Map<String, String> map) {
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08231ReqBean bCK08231ReqBean = new BCK08231ReqBean();
		// 请求报文体
		BCK08231ReqBodyBean bCK08231ReqBodyBean = new BCK08231ReqBodyBean();
		
		bCK08231ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));
		bCK08231ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));
		bCK08231ReqBodyBean.setPRO_CODE(map.get("PRO_CODE"));
		bCK08231ReqBodyBean.setAPP_NO(map.get("APP_NO"));
		bCK08231ReqBodyBean.setCUST_NO(map.get("CUST_NO"));
		bCK08231ReqBodyBean.setCUST_NAME(map.get("CUST_NAME"));
		bCK08231ReqBodyBean.setBAL_AMT(map.get("BAL_AMT"));
		bCK08231ReqBodyBean.setINT_START_DATE(map.get("INT_START_DATE"));
		bCK08231ReqBodyBean.setSAVE_TERM(map.get("SAVE_TERM"));
		bCK08231ReqBodyBean.setOPEN_RATE(map.get("OPEN_RATE"));
		bCK08231ReqBodyBean.setLOST_DATE(map.get("LOST_DATE"));
		bCK08231ReqBodyBean.setPAY_COND(map.get("PAY_COND"));
		bCK08231ReqBodyBean.setPASS_WORD(map.get("PASS_WORD"));
		bCK08231ReqBodyBean.setCERT_TYPE1(map.get("CERT_TYPE1"));
		bCK08231ReqBodyBean.setCERT_NO1(map.get("CERT_NO1"));
		bCK08231ReqBodyBean.setCRET_INST1(map.get("CRET_INST1"));
		bCK08231ReqBodyBean.setCERT_TYPE2(map.get("CERT_TYPE2"));
		bCK08231ReqBodyBean.setCERT_NO2(map.get("CERT_NO2"));
		bCK08231ReqBodyBean.setCRET_INST2(map.get("CRET_INST2"));
		bCK08231ReqBodyBean.setPAY_CURR(map.get("PAY_CURR"));
		bCK08231ReqBodyBean.setPAY_AMT(map.get("PAY_AMT"));
		bCK08231ReqBodyBean.setAMT_DIRE(map.get("AMT_DIRE"));
		bCK08231ReqBodyBean.setINTER_DIRE(map.get("INTER_DIRE"));
		bCK08231ReqBodyBean.setBOOK_DATE(map.get("BOOK_DATE"));
		bCK08231ReqBodyBean.setOPP_ACCT_NO(map.get("OPP_ACCT_NO"));
		bCK08231ReqBean.setBody(bCK08231ReqBodyBean);
		// 请求报文头 
		bCK08231ReqBean.setHeadBean(getInReqHeadBean("BCK_08231"));
		
		xstream.alias("Root", BCK08231ReqBean.class);
		xstream.alias("Body", BCK08231ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bCK08231ReqBean);
	}
	
	/**
	 * 一步解双挂并销户【02969】-前置【08258】
	 */
	public static String BCK_08258(Map<String, String> map) {
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08258ReqBean bCK08258ReqBean = new BCK08258ReqBean();
		// 请求报文体
		BCK08258ReqBodyBean bCK08258ReqBodyBean = new BCK08258ReqBodyBean();
		
		bCK08258ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));
		bCK08258ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));
		bCK08258ReqBodyBean.setAPPL_NO(map.get("APPL_NO"));
		bCK08258ReqBodyBean.setPASSWORD(map.get("PASSWORD"));
		bCK08258ReqBodyBean.setCLOSE_PASSWORD(map.get("CLOSE_PASSWORD"));
		bCK08258ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));
		bCK08258ReqBodyBean.setID_NO(map.get("ID_NO"));
		bCK08258ReqBodyBean.setCURRENCY(map.get("CURRENCY"));
		bCK08258ReqBodyBean.setDRAW_AMT(map.get("DRAW_AMT"));
		bCK08258ReqBodyBean.setPRI_WAY(map.get("PRI_WAY"));
		bCK08258ReqBodyBean.setINT_WAY(map.get("INT_WAY"));
		bCK08258ReqBodyBean.setPRE_DATE(map.get("PRE_DATE"));
		bCK08258ReqBodyBean.setOPPO_ACCT_NO(map.get("OPPO_ACCT_NO"));
		bCK08258ReqBodyBean.setBOOK_NO(map.get("BOOK_NO"));
		bCK08258ReqBodyBean.setCERT_NO(map.get("CERT_NO"));
		
		bCK08258ReqBean.setBody(bCK08258ReqBodyBean);
		// 请求报文头 
		bCK08258ReqBean.setHeadBean(getInReqHeadBean("BCK_08258"));
		
		xstream.alias("Root", BCK08258ReqBean.class);
		xstream.alias("Body", BCK08258ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bCK08258ReqBean);
	}
	
	/**
	 *	唐豆信息查询【02217】-前置07515
	 */
	public static String BCK_07515(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK07515ReqBean bckReqBean = new BCK07515ReqBean();
		bckReqBean.setHeadBean(getInReqHeadBean("BCK_07515"));
		BCK07515ReqBodyBean bck07515ReqBodyBean = new BCK07515ReqBodyBean();
		bck07515ReqBodyBean.setACCT_NO(map.get("ACCT_NO")); //账号
		bck07515ReqBodyBean.setCUST_NO(map.get("CUST_NO")); //客户号
		bck07515ReqBodyBean.setID_TYPE(map.get("ID_TYPE")); //证件类型
		bck07515ReqBodyBean.setID_NO(map.get("ID_NO")); //证件号
		bckReqBean.setBody(bck07515ReqBodyBean);
		xstream.alias("Root", BCK07515ReqBean.class);
		return xstream.toXML(bckReqBean);
	}
	
	/**
	 * 普通预计利息查询07696
	 */
	public static String BCK_07696(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07696ReqBean bck07696ReqBean = new BCK07696ReqBean();
		//请求报文体
		BCK07696ReqBodyBean bck07696ReqBodyBean = new BCK07696ReqBodyBean();
		bck07696ReqBodyBean.setAcctNO(map.get("ACCT_NO"));
		bck07696ReqBean.setBody(bck07696ReqBodyBean);
		//请求报文头
		bck07696ReqBean.setHeadBean(getInReqHeadBean("BCK_07696"));
		
		xstream.alias("Root", BCK07696ReqBean.class);
		xstream.alias("Body", BCK07696ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07696ReqBean);
	}
	/**
	 * 黑名单查询【20115】-前置【08236】
	 */
	public static String BCK_08236(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08236ReqBean bck08236ReqBean = new BCK08236ReqBean();
		//请求报文体
		BCK08236ReqBodyBean bck08236ReqBodyBean = new BCK08236ReqBodyBean();
		bck08236ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));
		bck08236ReqBean.setBody(bck08236ReqBodyBean);
		//请求报文头
		bck08236ReqBean.setHeadBean(getInReqHeadBean("BCK_08236"));
		
		xstream.alias("Root", BCK08236ReqBean.class);
		xstream.alias("Body", BCK08236ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck08236ReqBean);
	}
	
	/**
	 * 5.65实际账号查询外部账号【02966】-前置【08237】
	 */
	public static String BCK_08237(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08237ReqBean bck08237ReqBean = new BCK08237ReqBean();
		//请求报文体
		BCK08237ReqBodyBean bck08237ReqBodyBean = new BCK08237ReqBodyBean();
		bck08237ReqBodyBean.setIN_ACCT_NO(map.get("IN_ACCT_NO"));
		bck08237ReqBean.setBody(bck08237ReqBodyBean);
		//请求报文头
		bck08237ReqBean.setHeadBean(getInReqHeadBean("BCK_08237"));
		
		xstream.alias("Root", BCK08237ReqBean.class);
		xstream.alias("Body", BCK08237ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck08237ReqBean);
	}

	/**
	 *	账户信息查询【96010】-前置【08239】(电子账户)
	 */
	public static String BCK_08239(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK08239ReqBean bckReqBean = new BCK08239ReqBean();
		bckReqBean.setHeadBean(getInReqHeadBean("BCK_08239"));
		BCK08239ReqBodyBean bck08239ReqBodyBean = new BCK08239ReqBodyBean();
		bck08239ReqBodyBean.setACCT_NO(map.get("ACCT_NO")); //账号
		bck08239ReqBodyBean.setPIN_VAL_FLAG(map.get("PIN_VAL_FLAG")); //查询条件
		bck08239ReqBodyBean.setPASSWORD(map.get("PASSWORD")); //密码
		bckReqBean.setBody(bck08239ReqBodyBean);
		xstream.alias("Root", BCK08239ReqBean.class);
		return xstream.toXML(bckReqBean);
	}
	
	/**
	 * 上传挂失解挂申请书图片-前置【08233】
	 */
	public static String BCK_08233(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08233ReqBean bck08233ReqBean = new BCK08233ReqBean();
		//请求报文体
		BCK08233ReqBodyBean bck08233ReqBodyBean = new BCK08233ReqBodyBean();
		bck08233ReqBodyBean.setSVR_DATE(map.get("SVR_DATE"));
		bck08233ReqBodyBean.setLOST_FLAG(map.get("LOST_FLAG"));
		bck08233ReqBodyBean.setLOST_APPL_NO(map.get("LOST_APPL_NO"));
		bck08233ReqBodyBean.setFILE_NAME(map.get("FILE_NAME"));
		bck08233ReqBean.setBody(bck08233ReqBodyBean);
		//请求报文头
		bck08233ReqBean.setHeadBean(getInReqHeadBean("BCK_08233"));
		
		xstream.alias("Root", BCK08233ReqBean.class);
		xstream.alias("Body", BCK08233ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck08233ReqBean);
	}
	
	/**
	 * 卡权限管理【75008】前置03527
	 */
	public static String BCK_03527(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK03527ReqBean bck03527ReqBean = new BCK03527ReqBean();
		//请求报文体
		BCK03527ReqBodyBean bck03527ReqBodyBean = new BCK03527ReqBodyBean();
		bck03527ReqBodyBean.setCARD_NO(map.get("CARD_NO"));
		bck03527ReqBodyBean.setCARD_PIN(map.get("CARD_PIN"));
		bck03527ReqBodyBean.setID_CODE(map.get("ID_CODE"));
		bck03527ReqBodyBean.setID_NO(map.get("ID_NO"));
		bck03527ReqBodyBean.setYDZC_FLAG(map.get("YDZC_FLAG"));
		bck03527ReqBodyBean.setZZSBZZ_FLAG(map.get("ZZSBZZ_FLAG"));
		bck03527ReqBodyBean.setSHORT_MSG_REMIND(map.get("SHORT_MSG_REMIND"));
		bck03527ReqBodyBean.setBIND_MSG_PHONE_NO(map.get("BIND_MSG_PHONE_NO"));
		bck03527ReqBodyBean.setNO_CARD_PAY_FLAG(map.get("NO_CARD_PAY_FLAG"));
		bck03527ReqBodyBean.setCUST_NO(map.get("CUST_NO"));
		bck03527ReqBodyBean.setUSER_NAME(map.get("USER_NAME"));
		bck03527ReqBodyBean.setNO_CARD_PHONE_NO(map.get("NO_CARD_PHONE_NO"));
		bck03527ReqBodyBean.setVLI_DATE(map.get("VLI_DATE"));
		bck03527ReqBodyBean.setHAV_AGENT_FLAG(map.get("HAV_AGENT_FLAG"));
		
		BCK03527ReqAgentInfBean bck03527ReqAgentInfBean = new BCK03527ReqAgentInfBean();
		bck03527ReqAgentInfBean.setCUST_NAME(map.get("CUST_NAME"));
		bck03527ReqAgentInfBean.setSEX(map.get("SEX"));
		bck03527ReqAgentInfBean.setID_TYPE(map.get("ID_TYPE"));
		bck03527ReqAgentInfBean.setID_NO(map.get("ID_NO"));
		bck03527ReqAgentInfBean.setISSUE_DATE(map.get("ISSUE_DATE"));
		bck03527ReqAgentInfBean.setDUE_DATE(map.get("DUE_DATE"));
		bck03527ReqAgentInfBean.setISSUE_INST(map.get("ISSUE_INST"));
		bck03527ReqAgentInfBean.setNATION(map.get("NATION"));
		bck03527ReqAgentInfBean.setOCCUPATION(map.get("OCCUPATION"));
		bck03527ReqAgentInfBean.setREGIS_PER_RES(map.get("REGIS_PER_RES"));
		bck03527ReqAgentInfBean.setJ_C_ADD(map.get("J_C_ADD"));
		bck03527ReqAgentInfBean.setTELEPHONE(map.get("TELEPHONE"));
		bck03527ReqAgentInfBean.setMOB_PHONE(map.get("MOB_PHONE"));
		bck03527ReqBodyBean.setAGENT_INF(bck03527ReqAgentInfBean);
		
		bck03527ReqBodyBean.setDAY_LIMIT_AMT(map.get("DAY_LIMIT_AMT"));
		bck03527ReqBodyBean.setDAY_LIMIT_NUM(map.get("DAY_LIMIT_NUM"));
		bck03527ReqBodyBean.setYEAR_LIMIT_AMT(map.get("YEAR_LIMIT_AMT"));
		bck03527ReqBodyBean.setXFDXF_OPER_CHOOSE(map.get("XFDXF_OPER_CHOOSE"));
		bck03527ReqBodyBean.setJFXF_OPER_CHOOSE(map.get("JFXF_OPER_CHOOSE"));
		bck03527ReqBodyBean.setTDZX_OPER_CHOOSE(map.get("TDZX_OPER_CHOOSE"));
		bck03527ReqBean.setBody(bck03527ReqBodyBean);
		//请求报文头
		bck03527ReqBean.setHeadBean(getInReqHeadBean("BCK_03527"));
		
		xstream.alias("Root", BCK03527ReqBean.class);
		xstream.alias("Body", BCK03527ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck03527ReqBean);
	}
}
