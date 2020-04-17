package com.boomhope.tms.report;

import com.boomhope.tms.entity.UnitViewId;

public class UnitBean {

	private String unitCode;//机构编号
	private String unitName;//机构名称
	private String address;//机构地址
	private String unitType;//机构类型（0-总行，1-分行，2-支行，3-网点）
	private String tel;//机构电话
	private String contactor;//机构负责人
	private String email;//邮箱
	private String status;//机构状态 1-正常 2-废弃
	private String parentName;//上级机构
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getContactor() {
		return contactor;
	}
	public void setContactor(String contactor) {
		this.contactor = contactor;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	
}
