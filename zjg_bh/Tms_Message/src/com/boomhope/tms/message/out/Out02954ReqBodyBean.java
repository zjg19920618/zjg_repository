package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 单位校验对手信息
 * 02954请求报文体Bean
 * @author zjg
 *
 */
@XStreamAlias("BODY")
public class Out02954ReqBodyBean {
	private String CARD_NO;	//卡号
	private String OTHER_ACCT_NO;     //对方账号
	private String OPER_CHOOSE;     //操作选择
	public String getCARD_NO() {
		return CARD_NO;
	}
	public void setCARD_NO(String cARD_NO) {
		CARD_NO = cARD_NO;
	}
	public String getOTHER_ACCT_NO() {
		return OTHER_ACCT_NO;
	}
	public void setOTHER_ACCT_NO(String oTHER_ACCT_NO) {
		OTHER_ACCT_NO = oTHER_ACCT_NO;
	}
	public String getOPER_CHOOSE() {
		return OPER_CHOOSE;
	}
	public void setOPER_CHOOSE(String oPER_CHOOSE) {
		OPER_CHOOSE = oPER_CHOOSE;
	}
	
}
