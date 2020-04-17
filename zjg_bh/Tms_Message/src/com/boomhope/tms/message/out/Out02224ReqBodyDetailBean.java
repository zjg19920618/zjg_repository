package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 小额普通贷记往帐录入（通用）02224
 * BCK_02224请求报文体Bean
 * @author zjg
 */
@XStreamAlias("Detail")
public class Out02224ReqBodyDetailBean {
	private String PAYEE_BANK_NO;		//收款人行号
	private String PAYEE_BANK_NAME;		//收款人行名
	private String PAYEE_HBR_NO;		//收款开户行号
	private String PAYEE_HBR_NAME;		//收款开户行名
	private String PAYEE_ACCT_NO;		//收款人帐号
	private String PAYEE_NAME;		//收款人户名
	private String PAYEE_ADDR;		//收款人地址
	private String PAY_AMT;		//支付金额
	private String APPD_TEXT;		//附言
	public String getPAYEE_BANK_NO() {
		return PAYEE_BANK_NO;
	}
	public void setPAYEE_BANK_NO(String pAYEE_BANK_NO) {
		PAYEE_BANK_NO = pAYEE_BANK_NO;
	}
	public String getPAYEE_BANK_NAME() {
		return PAYEE_BANK_NAME;
	}
	public void setPAYEE_BANK_NAME(String pAYEE_BANK_NAME) {
		PAYEE_BANK_NAME = pAYEE_BANK_NAME;
	}
	public String getPAYEE_HBR_NO() {
		return PAYEE_HBR_NO;
	}
	public void setPAYEE_HBR_NO(String pAYEE_HBR_NO) {
		PAYEE_HBR_NO = pAYEE_HBR_NO;
	}
	public String getPAYEE_HBR_NAME() {
		return PAYEE_HBR_NAME;
	}
	public void setPAYEE_HBR_NAME(String pAYEE_HBR_NAME) {
		PAYEE_HBR_NAME = pAYEE_HBR_NAME;
	}
	public String getPAYEE_ACCT_NO() {
		return PAYEE_ACCT_NO;
	}
	public void setPAYEE_ACCT_NO(String pAYEE_ACCT_NO) {
		PAYEE_ACCT_NO = pAYEE_ACCT_NO;
	}
	public String getPAYEE_NAME() {
		return PAYEE_NAME;
	}
	public void setPAYEE_NAME(String pAYEE_NAME) {
		PAYEE_NAME = pAYEE_NAME;
	}
	public String getPAYEE_ADDR() {
		return PAYEE_ADDR;
	}
	public void setPAYEE_ADDR(String pAYEE_ADDR) {
		PAYEE_ADDR = pAYEE_ADDR;
	}
	public String getPAY_AMT() {
		return PAY_AMT;
	}
	public void setPAY_AMT(String pAY_AMT) {
		PAY_AMT = pAY_AMT;
	}
	public String getAPPD_TEXT() {
		return APPD_TEXT;
	}
	public void setAPPD_TEXT(String aPPD_TEXT) {
		APPD_TEXT = aPPD_TEXT;
	}
	
}
