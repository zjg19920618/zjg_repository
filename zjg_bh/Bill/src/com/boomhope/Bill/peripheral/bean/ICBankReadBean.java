package com.boomhope.Bill.peripheral.bean;
/**
 * 
 * Title:读取银行卡
 * Description:
 * @author mouchunyue
 * @date 2016年9月20日 上午11:10:49
 */
public class ICBankReadBean {

	private String status;//状态码
	private String code;//序列号
	private String num;//6
	private String icBankCode;//银行卡账号
	private String IM;//等效
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
	public String getIM() {
		return IM;
	}
	public void setIM(String iM) {
		IM = iM;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
}
