package com.boomhope.tms.message.moneybox;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Request")
public class MoneyBoxQueryOrderReqBodyBean {

	private String IDNum;//身份证号
	private String OrderNum;//订单号
	private String Password;//临时密码
	public String getIDNum() {
		return IDNum;
	}
	public void setIDNum(String iDNum) {
		IDNum = iDNum;
	}
	public String getOrderNum() {
		return OrderNum;
	}
	public void setOrderNum(String orderNum) {
		OrderNum = orderNum;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	
}
