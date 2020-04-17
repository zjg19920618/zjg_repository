package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 唐豆派发—02206前置【07663】
 */
@XStreamAlias("ACCT_INFO")
public class BCK07663ReqAgentInfBean {
	
	private String ACCT_NO; //账号
	private String SUB_ACCT_NO;//子账号
	private String ACCT_BAL;//余额
	
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
	public String getACCT_BAL() {
		return ACCT_BAL;
	}
	public void setACCT_BAL(String aCCT_BAL) {
		ACCT_BAL = aCCT_BAL;
	}
	
}
