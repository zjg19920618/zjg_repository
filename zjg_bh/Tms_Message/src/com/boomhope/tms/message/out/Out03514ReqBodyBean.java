package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 存单打印【02949】前置03514
 * @author zjg
 * @date 2016年11月7日 上午10:37:31
 */
@XStreamAlias("BODY")
public class Out03514ReqBodyBean {
	private String ACCT_NO; // 卡号
	private String SUB_ACCT_NO; // 子账号
	private String CERT_NO_ADD; // 凭证号

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

	public String getCERT_NO_ADD() {
		return CERT_NO_ADD;
	}

	public void setCERT_NO_ADD(String cERT_NO_ADD) {
		CERT_NO_ADD = cERT_NO_ADD;
	}

}
