package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 子账户列表查询-【75109】前置03519
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("Body")
public class BCK0018ReqBodyBean {
	
	private String CARD_NO; //银行卡号

	public String getCARD_NO() {
		return CARD_NO;
	}

	public void setCARD_NO(String cARD_NO) {
		CARD_NO = cARD_NO;
	}
	
}
