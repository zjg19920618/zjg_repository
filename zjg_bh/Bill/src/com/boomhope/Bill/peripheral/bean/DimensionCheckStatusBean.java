package com.boomhope.Bill.peripheral.bean;
/**
 * 
 * Title:二维码扫描仪扫描状态检测
 * Description:
 * @author mouchunyue
 * @date 2016年9月20日 上午11:16:10
 */
public class DimensionCheckStatusBean {
	//0|序列号|4|设备状态值 0：正常  1：故障
	private String id;//0
	private String code;//序列号
	private String num;//4
	private String status;//设备状态值
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
