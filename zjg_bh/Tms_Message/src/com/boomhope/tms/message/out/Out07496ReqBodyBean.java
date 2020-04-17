package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * 转账客户列表信息删除-前置07496
 * @author hk
 */
@XStreamAlias("BODY")
public class Out07496ReqBodyBean {
	public String PAY_ACCT_NO;//转出账号
	public String PAY_ACCT_NAME;//转出户名
	public String PAYEE_ACCT_NO;//转入账号
	public String PAYEE_ACCT_NAME;//转入户名
	public String QRY_CHNL;//查询渠道
	
	public String getPAY_ACCT_NO() {
		return PAY_ACCT_NO;
	}
	public void setPAY_ACCT_NO(String pAY_ACCT_NO) {
		PAY_ACCT_NO = pAY_ACCT_NO;
	}
	public String getPAY_ACCT_NAME() {
		return PAY_ACCT_NAME;
	}
	public void setPAY_ACCT_NAME(String pAY_ACCT_NAME) {
		PAY_ACCT_NAME = pAY_ACCT_NAME;
	}
	public String getPAYEE_ACCT_NO() {
		return PAYEE_ACCT_NO;
	}
	public void setPAYEE_ACCT_NO(String pAYEE_ACCT_NO) {
		PAYEE_ACCT_NO = pAYEE_ACCT_NO;
	}
	public String getPAYEE_ACCT_NAME() {
		return PAYEE_ACCT_NAME;
	}
	public void setPAYEE_ACCT_NAME(String pAYEE_ACCT_NAME) {
		PAYEE_ACCT_NAME = pAYEE_ACCT_NAME;
	}
	public String getQRY_CHNL() {
		return QRY_CHNL;
	}
	public void setQRY_CHNL(String qRY_CHNL) {
		QRY_CHNL = qRY_CHNL;
	}
	
	
	
	
	
}
