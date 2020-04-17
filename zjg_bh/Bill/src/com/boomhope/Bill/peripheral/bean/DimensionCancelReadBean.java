package com.boomhope.Bill.peripheral.bean;
/**
 * 
 * Title:取消二维码扫描仪扫描
 * Description:
 * @author mouchunyue
 * @date 2016年9月20日 上午11:11:00
 */
public class DimensionCancelReadBean {
	//0|序列号|2
	private String status;//状态码
	private String code;//序列号
	private String num;//2
	private String statusDesc;//描述
	
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
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
}
