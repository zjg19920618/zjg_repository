package com.boomhope.Bill.peripheral.bean;
/**
 * 
 * Title:银行卡设备状态检测
 * Description:
 * @author mouchunyue
 * @date 2016年9月20日 上午11:10:07
 */
public class ICBankCheckStatusBean {
	//0|序列号|999|接触式卡座状态&非接触式卡座状态&SAM卡座状态&刷卡器状态&
	private String status;//状态码
	private String code;//序列号
	private String num;//999操作码
	private String touchStatus;//接触式卡座状态
	private String notTouchStatus;//非接触式卡座状态
	private String samStatus;//非接触式卡座状态
	private String posStatus;//刷卡器状态
	private String statusDesc;//状态描述
	
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
	public String getTouchStatus() {
		return touchStatus;
	}
	public void setTouchStatus(String touchStatus) {
		this.touchStatus = touchStatus;
	}
	public String getNotTouchStatus() {
		return notTouchStatus;
	}
	public void setNotTouchStatus(String notTouchStatus) {
		this.notTouchStatus = notTouchStatus;
	}
	public String getSamStatus() {
		return samStatus;
	}
	public void setSamStatus(String samStatus) {
		this.samStatus = samStatus;
	}
	public String getPosStatus() {
		return posStatus;
	}
	public void setPosStatus(String posStatus) {
		this.posStatus = posStatus;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
}
