package com.boomhope.tms.message.moneybox;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Root")
public class MoneyBoxTransCommitReqBean extends MoneyBoxOutReqBean{
	
	private MoneyBoxTransCommitReqBodyBean Request;
	private MoneyBoxTransCommitReqResponseBean Response;
	
	public MoneyBoxTransCommitReqBodyBean getRequest() {
		return Request;
	}

	public void setRequest(MoneyBoxTransCommitReqBodyBean request) {
		Request = request;
	}

	public MoneyBoxTransCommitReqResponseBean getResponse() {
		return Response;
	}

	public void setResponse(MoneyBoxTransCommitReqResponseBean response) {
		Response = response;
	}
}
