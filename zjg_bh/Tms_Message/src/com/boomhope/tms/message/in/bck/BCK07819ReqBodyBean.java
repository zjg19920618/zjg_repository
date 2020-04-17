package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *电子账户子账户列表查询【35109】（直连电子账户）-前置07819
 * @author zjg
 *
 */
@XStreamAlias("Body")  
public class BCK07819ReqBodyBean {
	
	private String CARD_NO;//卡号
	private String CUST_NO;//客户号

	public String getCARD_NO() {
		return CARD_NO;
	}

	public void setCARD_NO(String cARD_NO) {
		CARD_NO = cARD_NO;
	}

	public String getCUST_NO() {
		return CUST_NO;
	}

	public void setCUST_NO(String cUST_NO) {
		CUST_NO = cUST_NO;
	}

}
