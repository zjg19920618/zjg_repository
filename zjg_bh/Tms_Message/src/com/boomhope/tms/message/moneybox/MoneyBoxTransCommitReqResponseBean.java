package com.boomhope.tms.message.moneybox;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Response")
public class MoneyBoxTransCommitReqResponseBean {

	private String RetCode="";
	private String ErrorMessage="";
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

}
