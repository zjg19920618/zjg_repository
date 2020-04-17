package com.boomhope.tms.message.moneybox;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Response")
public class MoneyBoxTransApplyReqResponseBean {

	private String RetCode="";
	private String ErrorMessage="";
	private String ApplyCode="";//交易申请码
	private String BankAccount="";//银行内部帐号
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
	public String getApplyCode() {
		return ApplyCode;
	}
	public void setApplyCode(String applyCode) {
		ApplyCode = applyCode;
	}
	public String getBankAccount() {
		return BankAccount;
	}
	public void setBankAccount(String bankAccount) {
		BankAccount = bankAccount;
	}
	
}
