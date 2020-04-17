package com.boomhope.tms.message.account.in.tms;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:开户功能请求入库
 * Description:
 * @author mouchunyue
 * @date 2016年12月20日 上午10:06:02
 */
@XStreamAlias("Body")
public class TmsAccountSaveOrderReqBodyBean {

	private String TERMINAL_CODE;
	private String STATUS;
	private String CREATE_DATE;
	private String UNIT_CODE;
	private String UNIT_NAME;
	private String ORDER_NO;
	private String CERT_NO;
	private String CARD_NO;
	private String CUSTOMER_NAME;
	private String ACCOUNT_NO;
	private String PRODUCT_CODE;
	private String SUB_ACCOUNT_NO;
	private String DEPOSIT_PERIOD;
	private String DEPOSIT_AMT;
	private String RATE;
	private String INTEREST;
	private String DEPOSIT_PASSWORD_ENABLED;
	private String DEPOSIT_PASSWORD;
	private String REQ_02808;
	private String REP_02808;
	private String REQ_07506;
	private String REP_07506;
	private String REQ_07505;
	private String REP_07505;
	private String REMARK;
	private String DEPOSIT_RESAVE_ENABLED;
	private String POINT;
	private String PRODUCT_NAME;
	private String PAY_TYPE;
	public String getPAY_TYPE() {
		return PAY_TYPE;
	}
	public void setPAY_TYPE(String pAY_TYPE) {
		PAY_TYPE = pAY_TYPE;
	}
	public String getTERMINAL_CODE() {
		return TERMINAL_CODE;
	}
	public void setTERMINAL_CODE(String tERMINAL_CODE) {
		TERMINAL_CODE = tERMINAL_CODE;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(String cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}
	public String getUNIT_CODE() {
		return UNIT_CODE;
	}
	public void setUNIT_CODE(String uNIT_CODE) {
		UNIT_CODE = uNIT_CODE;
	}
	public String getUNIT_NAME() {
		return UNIT_NAME;
	}
	public void setUNIT_NAME(String uNIT_NAME) {
		UNIT_NAME = uNIT_NAME;
	}
	public String getORDER_NO() {
		return ORDER_NO;
	}
	public void setORDER_NO(String oRDER_NO) {
		ORDER_NO = oRDER_NO;
	}
	public String getCERT_NO() {
		return CERT_NO;
	}
	public void setCERT_NO(String cERT_NO) {
		CERT_NO = cERT_NO;
	}
	public String getCARD_NO() {
		return CARD_NO;
	}
	public void setCARD_NO(String cARD_NO) {
		CARD_NO = cARD_NO;
	}
	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}
	public void setCUSTOMER_NAME(String cUSTOMER_NAME) {
		CUSTOMER_NAME = cUSTOMER_NAME;
	}
	public String getACCOUNT_NO() {
		return ACCOUNT_NO;
	}
	public void setACCOUNT_NO(String aCCOUNT_NO) {
		ACCOUNT_NO = aCCOUNT_NO;
	}
	public String getPRODUCT_CODE() {
		return PRODUCT_CODE;
	}
	public void setPRODUCT_CODE(String pRODUCT_CODE) {
		PRODUCT_CODE = pRODUCT_CODE;
	}
	public String getSUB_ACCOUNT_NO() {
		return SUB_ACCOUNT_NO;
	}
	public void setSUB_ACCOUNT_NO(String sUB_ACCOUNT_NO) {
		SUB_ACCOUNT_NO = sUB_ACCOUNT_NO;
	}
	public String getDEPOSIT_PERIOD() {
		return DEPOSIT_PERIOD;
	}
	public void setDEPOSIT_PERIOD(String dEPOSIT_PERIOD) {
		DEPOSIT_PERIOD = dEPOSIT_PERIOD;
	}
	public String getDEPOSIT_AMT() {
		return DEPOSIT_AMT;
	}
	public void setDEPOSIT_AMT(String dEPOSIT_AMT) {
		DEPOSIT_AMT = dEPOSIT_AMT;
	}
	public String getRATE() {
		return RATE;
	}
	public void setRATE(String rATE) {
		RATE = rATE;
	}
	public String getINTEREST() {
		return INTEREST;
	}
	public void setINTEREST(String iNTEREST) {
		INTEREST = iNTEREST;
	}
	public String getDEPOSIT_PASSWORD_ENABLED() {
		return DEPOSIT_PASSWORD_ENABLED;
	}
	public void setDEPOSIT_PASSWORD_ENABLED(String dEPOSIT_PASSWORD_ENABLED) {
		DEPOSIT_PASSWORD_ENABLED = dEPOSIT_PASSWORD_ENABLED;
	}
	public String getDEPOSIT_PASSWORD() {
		return DEPOSIT_PASSWORD;
	}
	public void setDEPOSIT_PASSWORD(String dEPOSIT_PASSWORD) {
		DEPOSIT_PASSWORD = dEPOSIT_PASSWORD;
	}
	public String getREQ_02808() {
		return REQ_02808;
	}
	public void setREQ_02808(String rEQ_02808) {
		REQ_02808 = rEQ_02808;
	}
	public String getREP_02808() {
		return REP_02808;
	}
	public void setREP_02808(String rEP_02808) {
		REP_02808 = rEP_02808;
	}
	public String getREQ_07506() {
		return REQ_07506;
	}
	public void setREQ_07506(String rEQ_07506) {
		REQ_07506 = rEQ_07506;
	}
	public String getREP_07506() {
		return REP_07506;
	}
	public void setREP_07506(String rEP_07506) {
		REP_07506 = rEP_07506;
	}
	public String getREQ_07505() {
		return REQ_07505;
	}
	public void setREQ_07505(String rEQ_07505) {
		REQ_07505 = rEQ_07505;
	}
	public String getREP_07505() {
		return REP_07505;
	}
	public void setREP_07505(String rEP_07505) {
		REP_07505 = rEP_07505;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getDEPOSIT_RESAVE_ENABLED() {
		return DEPOSIT_RESAVE_ENABLED;
	}
	public void setDEPOSIT_RESAVE_ENABLED(String dEPOSIT_RESAVE_ENABLED) {
		DEPOSIT_RESAVE_ENABLED = dEPOSIT_RESAVE_ENABLED;
	}
	public String getPOINT() {
		return POINT;
	}
	public void setPOINT(String pOINT) {
		POINT = pOINT;
	}
	public String getPRODUCT_NAME() {
		return PRODUCT_NAME;
	}
	public void setPRODUCT_NAME(String pRODUCT_NAME) {
		PRODUCT_NAME = pRODUCT_NAME;
	}
	
}
