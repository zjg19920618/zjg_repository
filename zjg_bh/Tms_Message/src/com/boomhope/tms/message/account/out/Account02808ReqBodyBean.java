package com.boomhope.tms.message.account.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account02808ReqBodyBean 
* @Description  个人存款开户—02808
* @author zh.m 
* @date 2016年12月3日 下午9:52:59  
*/
@XStreamAlias("BODY")
public class Account02808ReqBodyBean {
	private String ID_TYPE;//证件代号
	private String ID_NO;//证件号码
	private String CUST_NO;//客户号
	private String ISSUE_BRANCH;//发证机关
	private String CUST_NAME;//客户名称
	private String CUST_ADDRESS;//客户地址
	private String CUST_TELE;//客户电话
	private String CURRENCY;//币  种
	private String PROD_CODE;//产品代码
	private String ACCT_TYPE;//账号种类
	private String LIMIT;//支付条件
	private String PASSWORD;//密码
	private String START_INT_DATE;//起息日期
	private String NOTE_TERM;//通知期限
	private String UNIT_FLAG;//存期单位
	private String DEP_TERM;//存期
	private String DRAW_AMT_TERM;//取款期
	private String DRAW_INTE_TERM;//取息期
	private String SETT_TYPE;//结算方式
	private String OPPO_ACCT_NO;//对方账号
	private String SUB_ACCT_NO;//子账号
	private String OPPO_DRAW_COND;//对方户支取条件
	private String DRAW_PASSWORD;//密码
	private String CERT_TYPE;//凭证种类
	private String CERT_NO;//凭证号码
	private String CERT_DATE;//票据日期
	private String AMT;//存款金额
	private String PAY_PASSWD;//支付密码
	private String NEW_CERT_NO;//凭证号
	private String EXCH_FLAG;//自动转存次shu
	private String ANALY_NO;//分析号
	private String CORRECT_FLAG;//打印户名
	private String HAV_AGENT_FLAG;//是否有代理人标志
	private String DETAIL;//代理人信息
	private String PUT_INT_ACCT;//收益帐号
	private String SUB_ACCT_NO1;//子账号
	private String CHL_ID;//渠道模块标识
	public String getID_TYPE() {
		return ID_TYPE;
	}
	public void setID_TYPE(String iD_TYPE) {
		ID_TYPE = iD_TYPE;
	}
	public String getID_NO() {
		return ID_NO;
	}
	public void setID_NO(String iD_NO) {
		ID_NO = iD_NO;
	}
	public String getCUST_NO() {
		return CUST_NO;
	}
	public void setCUST_NO(String cUST_NO) {
		CUST_NO = cUST_NO;
	}
	public String getISSUE_BRANCH() {
		return ISSUE_BRANCH;
	}
	public void setISSUE_BRANCH(String iSSUE_BRANCH) {
		ISSUE_BRANCH = iSSUE_BRANCH;
	}
	public String getCUST_NAME() {
		return CUST_NAME;
	}
	public void setCUST_NAME(String cUST_NAME) {
		CUST_NAME = cUST_NAME;
	}
	public String getCUST_ADDRESS() {
		return CUST_ADDRESS;
	}
	public void setCUST_ADDRESS(String cUST_ADDRESS) {
		CUST_ADDRESS = cUST_ADDRESS;
	}
	public String getCUST_TELE() {
		return CUST_TELE;
	}
	public void setCUST_TELE(String cUST_TELE) {
		CUST_TELE = cUST_TELE;
	}
	public String getCURRENCY() {
		return CURRENCY;
	}
	public void setCURRENCY(String cURRENCY) {
		CURRENCY = cURRENCY;
	}
	public String getPROD_CODE() {
		return PROD_CODE;
	}
	public void setPROD_CODE(String pROD_CODE) {
		PROD_CODE = pROD_CODE;
	}
	public String getACCT_TYPE() {
		return ACCT_TYPE;
	}
	public void setACCT_TYPE(String aCCT_TYPE) {
		ACCT_TYPE = aCCT_TYPE;
	}
	public String getLIMIT() {
		return LIMIT;
	}
	public void setLIMIT(String lIMIT) {
		LIMIT = lIMIT;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getSTART_INT_DATE() {
		return START_INT_DATE;
	}
	public void setSTART_INT_DATE(String sTART_INT_DATE) {
		START_INT_DATE = sTART_INT_DATE;
	}
	public String getNOTE_TERM() {
		return NOTE_TERM;
	}
	public void setNOTE_TERM(String nOTE_TERM) {
		NOTE_TERM = nOTE_TERM;
	}
	public String getUNIT_FLAG() {
		return UNIT_FLAG;
	}
	public void setUNIT_FLAG(String uNIT_FLAG) {
		UNIT_FLAG = uNIT_FLAG;
	}
	public String getDEP_TERM() {
		return DEP_TERM;
	}
	public void setDEP_TERM(String dEP_TERM) {
		DEP_TERM = dEP_TERM;
	}
	public String getDRAW_AMT_TERM() {
		return DRAW_AMT_TERM;
	}
	public void setDRAW_AMT_TERM(String dRAW_AMT_TERM) {
		DRAW_AMT_TERM = dRAW_AMT_TERM;
	}
	public String getDRAW_INTE_TERM() {
		return DRAW_INTE_TERM;
	}
	public void setDRAW_INTE_TERM(String dRAW_INTE_TERM) {
		DRAW_INTE_TERM = dRAW_INTE_TERM;
	}
	public String getSETT_TYPE() {
		return SETT_TYPE;
	}
	public void setSETT_TYPE(String sETT_TYPE) {
		SETT_TYPE = sETT_TYPE;
	}
	public String getOPPO_ACCT_NO() {
		return OPPO_ACCT_NO;
	}
	public void setOPPO_ACCT_NO(String oPPO_ACCT_NO) {
		OPPO_ACCT_NO = oPPO_ACCT_NO;
	}
	public String getSUB_ACCT_NO() {
		return SUB_ACCT_NO;
	}
	public void setSUB_ACCT_NO(String sUB_ACCT_NO) {
		SUB_ACCT_NO = sUB_ACCT_NO;
	}
	public String getOPPO_DRAW_COND() {
		return OPPO_DRAW_COND;
	}
	public void setOPPO_DRAW_COND(String oPPO_DRAW_COND) {
		OPPO_DRAW_COND = oPPO_DRAW_COND;
	}
	public String getDRAW_PASSWORD() {
		return DRAW_PASSWORD;
	}
	public void setDRAW_PASSWORD(String dRAW_PASSWORD) {
		DRAW_PASSWORD = dRAW_PASSWORD;
	}
	public String getCERT_TYPE() {
		return CERT_TYPE;
	}
	public void setCERT_TYPE(String cERT_TYPE) {
		CERT_TYPE = cERT_TYPE;
	}
	public String getCERT_NO() {
		return CERT_NO;
	}
	public void setCERT_NO(String cERT_NO) {
		CERT_NO = cERT_NO;
	}
	public String getCERT_DATE() {
		return CERT_DATE;
	}
	public void setCERT_DATE(String cERT_DATE) {
		CERT_DATE = cERT_DATE;
	}
	public String getAMT() {
		return AMT;
	}
	public void setAMT(String aMT) {
		AMT = aMT;
	}
	public String getPAY_PASSWD() {
		return PAY_PASSWD;
	}
	public void setPAY_PASSWD(String pAY_PASSWD) {
		PAY_PASSWD = pAY_PASSWD;
	}
	public String getNEW_CERT_NO() {
		return NEW_CERT_NO;
	}
	public void setNEW_CERT_NO(String nEW_CERT_NO) {
		NEW_CERT_NO = nEW_CERT_NO;
	}
	public String getEXCH_FLAG() {
		return EXCH_FLAG;
	}
	public void setEXCH_FLAG(String eXCH_FLAG) {
		EXCH_FLAG = eXCH_FLAG;
	}
	public String getANALY_NO() {
		return ANALY_NO;
	}
	public void setANALY_NO(String aNALY_NO) {
		ANALY_NO = aNALY_NO;
	}
	public String getCORRECT_FLAG() {
		return CORRECT_FLAG;
	}
	public void setCORRECT_FLAG(String cORRECT_FLAG) {
		CORRECT_FLAG = cORRECT_FLAG;
	}
	public String getHAV_AGENT_FLAG() {
		return HAV_AGENT_FLAG;
	}
	public void setHAV_AGENT_FLAG(String hAV_AGENT_FLAG) {
		HAV_AGENT_FLAG = hAV_AGENT_FLAG;
	}
	public String getDETAIL() {
		return DETAIL;
	}
	public void setDETAIL(String dETAIL) {
		DETAIL = dETAIL;
	}
	public String getPUT_INT_ACCT() {
		return PUT_INT_ACCT;
	}
	public void setPUT_INT_ACCT(String pUT_INT_ACCT) {
		PUT_INT_ACCT = pUT_INT_ACCT;
	}
	public String getSUB_ACCT_NO1() {
		return SUB_ACCT_NO1;
	}
	public void setSUB_ACCT_NO1(String sUB_ACCT_NO1) {
		SUB_ACCT_NO1 = sUB_ACCT_NO1;
	}
	public String getCHL_ID() {
		return CHL_ID;
	}
	public void setCHL_ID(String cHL_ID) {
		CHL_ID = cHL_ID;
	}
	
}
