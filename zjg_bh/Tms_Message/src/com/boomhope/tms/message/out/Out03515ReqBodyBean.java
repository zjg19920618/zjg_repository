package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 账号信息综合查询【02880】前置03515
 * @author zjg
 * @date 2016年11月7日 上午10:49:00
 */
@XStreamAlias("BODY")
public class Out03515ReqBodyBean {
	public String getCERT_TYPE() {
		return CERT_TYPE;
	}

	public void setCERT_TYPE(String cERT_TYPE) {
		CERT_TYPE = cERT_TYPE;
	}

	private String ACCT_NO; // 账号
	private String SUB_ACCT_NO; // 子账号
	private String CHK_PIN; // 验密
	private String PASSWD; // 密码
	private String CERT_NO; // 凭证号
	private String CERT_TYPE; // 凭证种类
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

	public String getCHK_PIN() {
		return CHK_PIN;
	}

	public void setCHK_PIN(String cHK_PIN) {
		CHK_PIN = cHK_PIN;
	}

	public String getPASSWD() {
		return PASSWD;
	}

	public void setPASSWD(String pASSWD) {
		PASSWD = pASSWD;
	}

	public String getCERT_NO() {
		return CERT_NO;
	}

	public void setCERT_NO(String cERT_NO) {
		CERT_NO = cERT_NO;
	}
	
}
