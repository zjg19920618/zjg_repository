package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:唐豆收回查询
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午4:55:11
 */
@XStreamAlias("BODY")
public class Out02210ReqBodyBean {

	private String ACCT_NO;//账号
	private String SUB_ACCT_NO;//子账号
	private String PAY_DATE;//日期
	private String PAY_JRNL;//流水号
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
	public String getPAY_DATE() {
		return PAY_DATE;
	}
	public void setPAY_DATE(String pAY_DATE) {
		PAY_DATE = pAY_DATE;
	}
	public String getPAY_JRNL() {
		return PAY_JRNL;
	}
	public void setPAY_JRNL(String pAY_JRNL) {
		PAY_JRNL = pAY_JRNL;
	}
	
}
