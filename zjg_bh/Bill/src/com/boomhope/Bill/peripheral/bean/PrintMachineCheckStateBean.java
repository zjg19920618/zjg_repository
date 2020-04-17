package com.boomhope.Bill.peripheral.bean;

/**
 *存单打印机状态检测 
 */

public class PrintMachineCheckStateBean {
	//0|序列号|999|设备状态
	private String  id;//0正常   4找不到打印机  6找不到处理者
	private String code;//序列号
	private String num;//4
	private String status;//6切刀错误  7卡纸  8纸将尽 
	private String status1;//8纸将尽
	private String status2;//7卡纸  
	private String status3;//6切刀错误 
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
	
	public String getStatus1() {
		return status1;
	}
	public void setStatus1(String status1) {
		this.status1 = status1;
	}
	public String getStatus2() {
		return status2;
	}
	public void setStatus2(String status2) {
		this.status2 = status2;
	}
	public String getStatus3() {
		return status3;
	}
	public void setStatus3(String status3) {
		this.status3 = status3;
	}
	@Override
	public String toString() {
		return "PrintMachineCheckStateBean [id=" + id + ", code=" + code
				+ ", num=" + num + ", status=" + status + ", status1="
				+ status1 + ", status2=" + status2 + ", status3=" + status3
				+ "]";
	}
 	
}