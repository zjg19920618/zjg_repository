package com.boomhope.tms.message.moneybox;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Root")
public class MoneyBoxQueryOrderReqBean extends MoneyBoxOutReqBean{
	
	private MoneyBoxQueryOrderReqBodyBean Request;
	private MoneyBoxQueryOrderReqResponseBean Response;
	
	public MoneyBoxQueryOrderReqBodyBean getRequest() {
		return Request;
	}

	public void setRequest(MoneyBoxQueryOrderReqBodyBean request) {
		Request = request;
	}

	public MoneyBoxQueryOrderReqResponseBean getResponse() {
		return Response;
	}

	public void setResponse(MoneyBoxQueryOrderReqResponseBean response) {
		Response = response;
	}
}
