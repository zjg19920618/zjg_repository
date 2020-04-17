package com.boomhope.tms.message.moneybox;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Request")
public class MoneyBoxTransApplyReqBodyBean {

	private String IDNum;//身份证号
	private String OrderNum;//订单号
	private String Password;//临时密码
	private String Amount;//交易金额
	private String TermNum;//终端号
	private String TransType;//交易类型
	private String Account;//客户账户
	private String TellerNum;//柜员号
	private String CartonNum;//尾箱号
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
	public String getAmount() {
		return Amount;
	}
	public void setAmount(String amount) {
		Amount = amount;
	}
	public String getTermNum() {
		return TermNum;
	}
	public void setTermNum(String termNum) {
		TermNum = termNum;
	}
	public String getTransType() {
		return TransType;
	}
	public void setTransType(String transType) {
		TransType = transType;
	}
	public String getAccount() {
		return Account;
	}
	public void setAccount(String account) {
		Account = account;
	}
	public String getTellerNum() {
		return TellerNum;
	}
	public void setTellerNum(String tellerNum) {
		TellerNum = tellerNum;
	}
	public String getCartonNum() {
		return CartonNum;
	}
	public void setCartonNum(String cartonNum) {
		CartonNum = cartonNum;
	}
	
}
