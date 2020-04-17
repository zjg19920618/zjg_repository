package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *客户账户基本信息维护【17024】前置-02906
 * @author ly
 *
 */
@XStreamAlias("BODY")  
public class Out02906ResBodyBean {
	
	private String CUST_NO;//客户号
	private String ID_NO;//证件号码
	private String CUST_NAME;//客户名称
	private String TEL_NO;//手机号
	private String ADDRESS;//地址
	private String CARD_NO;//卡号
	private String ACCT_NO;//账号
	private String REC_CUST_NO;//推荐人客户号
	private String REC_NAME;//推荐人姓名
	private String REC_DATE;//推荐日期
	private String REC_ID_NO;//推荐人证件号
	private String REC_TEL_NO;//推荐人手机号
	
	public String getCUST_NO() {
		return CUST_NO;
	}
	public void setCUST_NO(String cUST_NO) {
		CUST_NO = cUST_NO;
	}
	public String getID_NO() {
		return ID_NO;
	}
	public void setID_NO(String iD_NO) {
		ID_NO = iD_NO;
	}
	public String getCUST_NAME() {
		return CUST_NAME;
	}
	public void setCUST_NAME(String cUST_NAME) {
		CUST_NAME = cUST_NAME;
	}
	public String getTEL_NO() {
		return TEL_NO;
	}
	public void setTEL_NO(String tEL_NO) {
		TEL_NO = tEL_NO;
	}
	public String getADDRESS() {
		return ADDRESS;
	}
	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}
	public String getCARD_NO() {
		return CARD_NO;
	}
	public void setCARD_NO(String cARD_NO) {
		CARD_NO = cARD_NO;
	}
	public String getACCT_NO() {
		return ACCT_NO;
	}
	public void setACCT_NO(String aCCT_NO) {
		ACCT_NO = aCCT_NO;
	}
	public String getREC_CUST_NO() {
		return REC_CUST_NO;
	}
	public void setREC_CUST_NO(String rEC_CUST_NO) {
		REC_CUST_NO = rEC_CUST_NO;
	}
	public String getREC_NAME() {
		return REC_NAME;
	}
	public void setREC_NAME(String rEC_NAME) {
		REC_NAME = rEC_NAME;
	}
	public String getREC_DATE() {
		return REC_DATE;
	}
	public void setREC_DATE(String rEC_DATE) {
		REC_DATE = rEC_DATE;
	}
	public String getREC_ID_NO() {
		return REC_ID_NO;
	}
	public void setREC_ID_NO(String rEC_ID_NO) {
		REC_ID_NO = rEC_ID_NO;
	}
	public String getREC_TEL_NO() {
		return REC_TEL_NO;
	}
	public void setREC_TEL_NO(String rEC_TEL_NO) {
		REC_TEL_NO = rEC_TEL_NO;
	}

}
