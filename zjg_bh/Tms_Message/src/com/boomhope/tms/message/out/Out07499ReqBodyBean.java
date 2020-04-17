package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 唐宝x号转入【79100】-前置07499
 * @author hk
 *
 */
@XStreamAlias("BODY")  
public class Out07499ReqBodyBean {
	private String CUST_NO;//客户号
	private String ID_TYPE;//证件类别
	private String ID_NO;//证件号码
	private String PROD_CODE;//产品代码
	private String CARD_NO;//卡号
	private String PASSWORD;//密码
	private String SUB_ACCT_NO;//子账号
	private String TRAN_AMT;//转入金额
	
	

	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
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
	public String getCARD_NO() {
		return CARD_NO;
	}
	public void setCARD_NO(String cARD_NO) {
		CARD_NO = cARD_NO;
	}
	public String getSUB_ACCT_NO() {
		return SUB_ACCT_NO;
	}
	public void setSUB_ACCT_NO(String sUB_ACCT_NO) {
		SUB_ACCT_NO = sUB_ACCT_NO;
	}
	public String getCUST_NO() {
		return CUST_NO;
	}
	public void setCUST_NO(String cUST_NO) {
		CUST_NO = cUST_NO;
	}
	public String getPROD_CODE() {
		return PROD_CODE;
	}
	public void setPROD_CODE(String pROD_CODE) {
		PROD_CODE = pROD_CODE;
	}
	public String getTRAN_AMT() {
		return TRAN_AMT;
	}
	public void setTRAN_AMT(String tRAN_AMT) {
		TRAN_AMT = tRAN_AMT;
	}
	
	

}
