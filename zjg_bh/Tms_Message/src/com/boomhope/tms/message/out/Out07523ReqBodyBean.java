package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *银行卡换开查询原卡号【79114】-07523
 * @author zjg
 *
 */
@XStreamAlias("BODY")  
public class Out07523ReqBodyBean {
	
	private String CARD_NO;//卡号

	public String getCARD_NO() {
		return CARD_NO;
	}

	public void setCARD_NO(String cARD_NO) {
		CARD_NO = cARD_NO;
	}
	
	
}
