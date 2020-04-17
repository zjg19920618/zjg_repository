package com.boomhope.Bill.peripheral.bean;
/**
 * 
 * Title:指纹状态检测
 * Description:
 * @author mouchunyue
 * @date 2016年9月20日 上午11:55:26
 */
public class FingerPrintCheckStatusBean {
	//0|序列号|999|设备状态值|
	private String id;//0
	private String code;//序列号
	private String num;//999
	private String status;//设备状态//为空，检测不到状态，0正常 1故障
	
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
}
