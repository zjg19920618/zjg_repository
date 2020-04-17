package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *子账号开户流水查询【20110】-07522
 * @author hk
 *
 */
@XStreamAlias("BODY")  
public class Out07522ReqBodyBean {
	
	private String ACCT_NO;//账号
	private String SUB_ACCT_NO;//子账号
	public String getACCT_NO() {
		return ACCT_NO;
	}
	public void setACCT_NO(String aCCT_NO) {
		ACCT_NO = aCCT_NO;
	}
	public String getSUB_ACCT_NO() {
		return SUB_ACCT_NO;
	}
	public void setSUB_ACCT_NO(String sUB_ACCT_NO) {
		SUB_ACCT_NO = sUB_ACCT_NO;
	}

	
	
}
