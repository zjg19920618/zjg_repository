package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:账号信息综合查询BODY实体类
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午3:28:39
 */
@XStreamAlias("BODY")
public class Out02880ReqBodyBean {

	private String ACCT_NO;//账号
	private String SUB_ACCT_NO;//子账号
	private String CHK_PIN;//是否验密
	private String PASSWD;//密码
	private String CERT_TYPE;//凭证种类
	private String CERT_NO_ADD;//凭证号
	
	public String getCERT_TYPE() {
		return CERT_TYPE;
	}
	public void setCERT_TYPE(String cERT_TYPE) {
		CERT_TYPE = cERT_TYPE;
	}
	public String getCERT_NO_ADD() {
		return CERT_NO_ADD;
	}
	public void setCERT_NO_ADD(String cERT_NO_ADD) {
		CERT_NO_ADD = cERT_NO_ADD;
	}
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
	
}
