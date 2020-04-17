package com.boomhope.tms.message.moneybox;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Response")
public class MoneyBoxQueryOrderReqResponseBean {

	private String RetCode="";//接口调用返回码
	private String ErrorMessage="";//错误信息
	private String OrderAmount="";//订单余额
	private String OrderStatus="";//订单状态
	private String TermNum="";
	public String getRetCode() {
		return RetCode;
	}
	public void setRetCode(String retCode) {
		RetCode = retCode;
	}
	public String getErrorMessage() {
		return ErrorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		ErrorMessage = errorMessage;
	}
	public String getOrderAmount() {
		return OrderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		OrderAmount = orderAmount;
	}
	public String getOrderStatus() {
		return OrderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		OrderStatus = orderStatus;
	}
	public String getTermNum() {
		return TermNum;
	}
	public void setTermNum(String termNum) {
		TermNum = termNum;
	}
	
}
