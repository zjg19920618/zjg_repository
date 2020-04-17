package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 权限明细查询【78010】(前置交易码03740)
 * 03740请求报文
 * @author zjg
 *
 */
@XStreamAlias("BODY")
public class Out03740ReqBodyBean {
	private String QRY_OPTION;	//查询选项
	private String CARD_NO;     //卡号
	private String MP_NO;     //手机号
	public String getQRY_OPTION() {
		return QRY_OPTION;
	}
	public void setQRY_OPTION(String qRY_OPTION) {
		QRY_OPTION = qRY_OPTION;
	}
	public String getCARD_NO() {
		return CARD_NO;
	}
	public void setCARD_NO(String cARD_NO) {
		CARD_NO = cARD_NO;
	}
	public String getMP_NO() {
		return MP_NO;
	}
	public void setMP_NO(String mP_NO) {
		MP_NO = mP_NO;
	}
	
}
