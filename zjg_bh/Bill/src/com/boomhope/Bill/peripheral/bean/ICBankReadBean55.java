package com.boomhope.Bill.peripheral.bean;

/**
 * Title:读取IC卡55域信息
 * @author Administrator
 *
 */

public class ICBankReadBean55 {
	
	private String status;//状态码
	private String code;//序列号
	private String num;//6
	private String icBankCode;//银行卡号
	private String erCiData;//二磁数据
	private String cardValidity;//有效期(YYMMDD)
	private String cardNo;//卡序列号
	private String transDate;//本地交易日期(YYMMDD)
	private String transTime;//本地交易时间(HHMMSS)
	private String data55;//55域数据 
	private String statusDesc;//状态描述（失败时才有值）
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getIcBankCode() {
		return icBankCode;
	}
	public void setIcBankCode(String icBankCode) {
		this.icBankCode = icBankCode;
	}
	public String getErCiData() {
		return erCiData;
	}
	public void setErCiData(String erCiData) {
		this.erCiData = erCiData;
	}
	public String getCardValidity() {
		return cardValidity;
	}
	public void setCardValidity(String cardValidity) {
		this.cardValidity = cardValidity;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getTransTime() {
		return transTime;
	}
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}
	public String getData55() {
		return data55;
	}
	public void setData55(String data55) {
		this.data55 = data55;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
	
	
	
}
