package com.boomhope.Bill.peripheral.bean;

/**
 *凭条打印状态检测 
 */

public class BillCheckStateBean {
	//0|序列号|4|设备状态
	private String  id;//0正常   其他错误
	private String code;//序列号
	private String num;//4
	private String status;//设备状态为空，检测不到状态，0正常  其他故障 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "BillCheckStateBean [id=" + id + ", code=" + code + ", num="
				+ num + ", status=" + status + "]";
	}
 	
}