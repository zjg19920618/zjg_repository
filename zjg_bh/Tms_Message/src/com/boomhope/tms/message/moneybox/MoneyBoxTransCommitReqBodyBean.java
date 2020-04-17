package com.boomhope.tms.message.moneybox;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Request")
public class MoneyBoxTransCommitReqBodyBean {

	private String IDNum;//身份证号
	private String OrderNum;//订单号
	private String Password;//临时密码
	private String ApplyCode;//
	private String CommitStatus;//提交结果
	private String DepositReceiptNum;//订单号
	private String CoreJournal;//核心流水号
	public String getCoreJournal() {
		return CoreJournal;
	}
	public void setCoreJournal(String coreJournal) {
		CoreJournal = coreJournal;
	}
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
	public String getApplyCode() {
		return ApplyCode;
	}
	public void setApplyCode(String applyCode) {
		ApplyCode = applyCode;
	}
	public String getCommitStatus() {
		return CommitStatus;
	}
	public void setCommitStatus(String commitStatus) {
		CommitStatus = commitStatus;
	}
	public String getDepositReceiptNum() {
		return DepositReceiptNum;
	}
	public void setDepositReceiptNum(String depositReceiptNum) {
		DepositReceiptNum = depositReceiptNum;
	}
	
	
}
