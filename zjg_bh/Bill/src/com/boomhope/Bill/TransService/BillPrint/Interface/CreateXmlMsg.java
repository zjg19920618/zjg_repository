package com.boomhope.Bill.TransService.BillPrint.Interface;

import java.util.Map;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK02864ReqBean;
import com.boomhope.tms.message.account.in.BCK02864ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK03845ReqBean;
import com.boomhope.tms.message.account.in.BCK03845ReqBodyBean;
import com.boomhope.tms.message.account.in.tms.TmsAccountSaveOrderReqBean;
import com.boomhope.tms.message.account.in.tms.TmsAccountSaveOrderReqBodyBean;
import com.boomhope.tms.message.cdjmac.CdjOpenAccReqBean;
import com.boomhope.tms.message.cdjmac.CdjOpenAccReqBodyBean;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.bck.BCK07518ReqBean;
import com.boomhope.tms.message.in.bck.BCK07518ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07519ReqBean;
import com.boomhope.tms.message.in.bck.BCK07519ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07520ReqBean;
import com.boomhope.tms.message.in.bck.BCK07520ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07522ReqBean;
import com.boomhope.tms.message.in.bck.BCK07522ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07523ReqBean;
import com.boomhope.tms.message.in.bck.BCK07523ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07601ReqBean;
import com.boomhope.tms.message.in.bck.BCK07601ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07819ReqBean;
import com.boomhope.tms.message.in.bck.BCK07819ReqBodyBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 创建XML报文信息
 * @author Wang.xm
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
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);// 机器号
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);// 机构号
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);// 机构号
		return inReqHeadBean;
		
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
		bck02864ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));//账号
		bck02864ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));//子账号
		bck02864ReqBean.setBody(bck02864ReqBodyBean);
		xstream.alias("Root", BCK02864ReqBean.class);
		return xstream.toXML(bck02864ReqBean);
	}
	/**
	 * 根据客户号查询所有卡号信息【90001】-前置07520
	 * @param map
	 * @return
	 */
	public static String BCK_07520(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07520ReqBean bck07520ReqBean = new BCK07520ReqBean();
		//请求报文体
		BCK07520ReqBodyBean bck07520ReqBodyBean = new BCK07520ReqBodyBean();
		bck07520ReqBodyBean.setCUST_NO(map.get("CUST_NO"));
		bck07520ReqBean.setBody(bck07520ReqBodyBean);
		//请求报文头
		bck07520ReqBean.setHeadBean(getInReqHeadBean("BCK_07520"));
		
		xstream.alias("Root", BCK07520ReqBean.class);
		xstream.alias("Body", BCK07520ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07520ReqBean);
	}
	
	/**
	 * 电子账户子账户列表查询【35109】
	 * @param map
	 * @return
	 */
	public static String BCK_07819(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07819ReqBean bck07819ReqBean = new BCK07819ReqBean();
		//请求报文体
		BCK07819ReqBodyBean bck07819ReqBodyBean = new BCK07819ReqBodyBean();
		bck07819ReqBodyBean.setCUST_NO(map.get("CUST_NO"));
		bck07819ReqBean.setBody(bck07819ReqBodyBean);
		//请求报文头
		bck07819ReqBean.setHeadBean(getInReqHeadBean("BCK_07819"));
		
		xstream.alias("Root", BCK07819ReqBean.class);
		xstream.alias("Body", BCK07819ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07819ReqBean);
	}
	
	/**
	 * 银行卡开户入库
	 */
	public static String saveOpenAccXml(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		TmsAccountSaveOrderReqBean accountSaveOrderReqBean = new TmsAccountSaveOrderReqBean();
		InReqHeadBean inReqHeadBean = getInReqHeadBean("ACCOUNT_0001");
		accountSaveOrderReqBean.setHeadBean(inReqHeadBean);
		
		TmsAccountSaveOrderReqBodyBean accountSaveOrderReqBodyBean = new TmsAccountSaveOrderReqBodyBean();
		accountSaveOrderReqBodyBean.setCARD_NO(map.get("CARD_NO"));//卡号
		accountSaveOrderReqBodyBean.setCERT_NO(map.get("CERT_NO"));//凭证号
		accountSaveOrderReqBodyBean.setCUSTOMER_NAME(map.get("CUSTOMER_NAME"));//户名
		accountSaveOrderReqBodyBean.setDEPOSIT_AMT(map.get("DEPOSIT_AMT"));//存款金额
		accountSaveOrderReqBodyBean.setDEPOSIT_PASSWORD_ENABLED(map.get("DEPOSIT_PASSWORD_ENABLED"));//是否加密
		accountSaveOrderReqBodyBean.setDEPOSIT_PERIOD(map.get("DEPOSIT_PERIOD"));//大写存期
		accountSaveOrderReqBodyBean.setDEPOSIT_RESAVE_ENABLED(map.get("DEPOSIT_RESAVE_ENABLED"));//是否转存  1：是。0：否
		accountSaveOrderReqBodyBean.setPRODUCT_CODE(map.get("PRODUCT_CODE"));//产品编号
		accountSaveOrderReqBodyBean.setPRODUCT_NAME(map.get("PRODUCT_NAME"));//产品名称
		accountSaveOrderReqBodyBean.setRATE(map.get("RATE"));//利率
		accountSaveOrderReqBodyBean.setREP_02808(map.get("Res03870"));//开户返回报文
		accountSaveOrderReqBodyBean.setREP_07505(map.get("Res07505"));//唐豆派发查询返回报文
		accountSaveOrderReqBodyBean.setREP_07506(map.get("Res07506"));//唐豆兑付返回报文
		
		accountSaveOrderReqBodyBean.setSUB_ACCOUNT_NO(map.get("SUB_ACCOUNT_NO"));//存单账号
		accountSaveOrderReqBodyBean.setTERMINAL_CODE(map.get("TERMINAL_CODE"));//机器出厂编号
		accountSaveOrderReqBodyBean.setUNIT_CODE(map.get("UNIT_CODE"));//机构编号
		accountSaveOrderReqBodyBean.setUNIT_NAME(map.get("UNIT_NAME"));//机构名称
		accountSaveOrderReqBodyBean.setPAY_TYPE(map.get("PAY_TYPE"));//开户支付类型
		accountSaveOrderReqBean.setBody(accountSaveOrderReqBodyBean);
		
		xstream.alias("Root", TmsAccountSaveOrderReqBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		xstream.alias("Body", TmsAccountSaveOrderReqBodyBean.class);
		return xstream.toXML(accountSaveOrderReqBean);
	}
	
	/**
	 * 查询图片上传路径
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static String checkUploadPathXml(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		CdjOpenAccReqBean cdjOpenAccReqBean = new CdjOpenAccReqBean();
		com.boomhope.tms.message.cdjmac.InReqHeadBean inReqHeadBean = new com.boomhope.tms.message.cdjmac.InReqHeadBean();
		inReqHeadBean.setTradeCode("openAccMsg");
		cdjOpenAccReqBean.setHeadBean(inReqHeadBean);
		
		CdjOpenAccReqBodyBean cdjOpenAccReqBodyBean = new CdjOpenAccReqBodyBean();
		cdjOpenAccReqBodyBean.setOpenAccNo(map.get("openAccNo"));//开户账户
		cdjOpenAccReqBodyBean.setOpenAccDate(map.get("openAccDate"));//开户日期
		
		cdjOpenAccReqBean.setBody(cdjOpenAccReqBodyBean);
		
		xstream.alias("Root", CdjOpenAccReqBean.class);
		xstream.alias("Head", com.boomhope.tms.message.cdjmac.InReqHeadBean.class);
		xstream.alias("Body", CdjOpenAccReqBodyBean.class);
		return xstream.toXML(cdjOpenAccReqBean);
	} 
	
	/**
	 * 子账号开户流水查询【20110】-07522
	 * @param map
	 * @return
	 */
	public static String BCK_07522(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07522ReqBean bck07522ReqBean = new BCK07522ReqBean();
		//请求报文体
		BCK07522ReqBodyBean bck07522ReqBodyBean = new BCK07522ReqBodyBean();
		bck07522ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));
		bck07522ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));
		bck07522ReqBean.setBody(bck07522ReqBodyBean);
		//请求报文头
		bck07522ReqBean.setHeadBean(getInReqHeadBean("BCK_07522"));
		
		xstream.alias("Root", BCK07522ReqBean.class);
		xstream.alias("Body", BCK07522ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07522ReqBean);
	}
	
	/**
	 * 存款关联账号查询【02947】-前置07518
	 * @param map
	 * @return
	 */
	public static String BCK_07518(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07518ReqBean bck07518ReqBean = new BCK07518ReqBean();
		//请求报文体
		BCK07518ReqBodyBean bck07518ReqBodyBean = new BCK07518ReqBodyBean();
		bck07518ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));
		bck07518ReqBodyBean.setCUST_NAME(map.get("CUST_NAME"));
		bck07518ReqBodyBean.setEND_DATE(map.get("END_DATE"));
		bck07518ReqBodyBean.setINPUT_INST_NO(map.get("INPUT_INST_NO"));
		bck07518ReqBodyBean.setQRY_TYPE(map.get("QRY_TYPE"));
		bck07518ReqBodyBean.setSTART_DATE(map.get("START_DATE"));
		bck07518ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));
		bck07518ReqBean.setBody(bck07518ReqBodyBean);
		//请求报文头
		bck07518ReqBean.setHeadBean(getInReqHeadBean("BCK_07518"));
		
		xstream.alias("Root", BCK07518ReqBean.class);
		xstream.alias("Body", BCK07518ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07518ReqBean);
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
	 * 银行卡换开查询原卡号【08029】前置07523
	 */
	public static String BCK_07523(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07523ReqBean bCK07523ReqBean = new BCK07523ReqBean();
		//请求报文体
		BCK07523ReqBodyBean bCK07523ReqBodyBean = new BCK07523ReqBodyBean();
		bCK07523ReqBodyBean.setCARD_NO(map.get("CARD_NO"));//卡号
		bCK07523ReqBean.setBody(bCK07523ReqBodyBean);
		//请求报文头
		bCK07523ReqBean.setHeadBean(getInReqHeadBean("BCK_07523"));
		
		xstream.alias("Root", BCK07523ReqBean.class);
		xstream.alias("Body", BCK07523ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bCK07523ReqBean);
	}
}
