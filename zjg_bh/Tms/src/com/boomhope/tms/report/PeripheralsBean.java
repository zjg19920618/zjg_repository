package com.boomhope.tms.report;

public class PeripheralsBean {

	private int periId; //外设代码
	private String periCode; //外设型号
	private String periType; //外设类型
	private String periName;//外设名称
	private String createDate; //创建时间
	private String creater;//创建者
	public int getPeriId() {
		return periId;
	}
	public void setPeriId(int periId) {
		this.periId = periId;
	}
	public String getPeriCode() {
		return periCode;
	}
	public void setPeriCode(String periCode) {
		this.periCode = periCode;
	}
	public String getPeriType() {
		return periType;
	}
	public void setPeriType(String periType) {
		this.periType = periType;
	}
	public String getPeriName() {
		return periName;
	}
	public void setPeriName(String periName) {
		this.periName = periName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	
	
}
