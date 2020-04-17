package com.boomhope.tms.message.account.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:卡信息查询
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午6:08:02
 */
@XStreamAlias("BODY")
public class Account03845ReqBodyBean {

	private String CARD_NO;//卡号
	private String SUB_ACCT_NO;//子账号
	private String ISPIN;//是否验密
	private String PASSWD;//密码
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
	public String getISPIN() {
		return ISPIN;
	}
	public void setISPIN(String iSPIN) {
		ISPIN = iSPIN;
	}
	public String getPASSWD() {
		return PASSWD;
	}
	public void setPASSWD(String pASSWD) {
		PASSWD = pASSWD;
	}
	
}
