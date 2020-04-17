package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 权限明细查询【77015】(前置交易码02956)
 * 02956请求报文体Bean
 * @author wang.sk
 */
@XStreamAlias("Body")
public class BCK02956ReqBodyBean {
	private String QRY_OPTION;	//查询选项
	private String CARD_NO;     //卡号
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
	
}
