package com.boomhope.tms.message.moneybox;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Root")
public class MoneyBoxTransApplyReqBean extends MoneyBoxOutReqBean{
	
	private MoneyBoxTransApplyReqBodyBean Request;
	private MoneyBoxTransApplyReqResponseBean Response;
	
	public MoneyBoxTransApplyReqBodyBean getRequest() {
		return Request;
	}

	public void setRequest(MoneyBoxTransApplyReqBodyBean request) {
		Request = request;
	}

	public MoneyBoxTransApplyReqResponseBean getResponse() {
		return Response;
	}

	public void setResponse(MoneyBoxTransApplyReqResponseBean response) {
		Response = response;
	}
}
