package com.boomhope.tms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mac_warning_view")
public class MacWarningView implements Serializable {
	private static final long serialVersionUID = 1L;
	private String machineCode;//机器型号
	private String machineType;//机器类型
	private String machineName;//机器名称
	private String manuCode;//厂商代码
	private String manuName;//厂商名称
	private String unitCode;//机构编号
	private String unitName;//机构名称
	private String proStatus;//处理状态 1-未处理    2-已处理
	private String proDesc;//处理描述
	private String createDate;//创建日期
	private String proDate;//预警描述

	private MacWarningViewId id;//机器部署编号
	@Id
	public MacWarningViewId getId() {
		return id;
	}
	public void setId(MacWarningViewId id) {
		this.id = id;
	}
	@Column(name = "machine_code", length = 255)
	public String getMachineCode(){
		return machineCode;
	}
	public void setMachineCode(String machineCode){
		this.machineCode=machineCode;
	}
	@Column(name = "machine_type", length = 255)
	public String getMachineType(){
		return machineType;
	}
	public void setMachineType(String machineType){
		this.machineType=machineType;
	}
	@Column(name = "machine_name", length = 255)
	public String getMachineName(){
		return machineName;
	}
	public void setMachineName(String machineName){
		this.machineName=machineName;
	}
	@Column(name = "manu_code", length = 255)
	public String getManuCode(){
		return manuCode;
	}
	public void setManuCode(String manuCode){
		this.manuCode=manuCode;
	}
	@Column(name = "manu_name", length = 255)
	public String getManuName(){
		return manuName;
	}
	public void setManuName(String manuName){
		this.manuName=manuName;
	}
	@Column(name = "unit_code", length = 255)
	public String getUnitCode(){
		return unitCode;
	}
	public void setUnitCode(String unitCode){
		this.unitCode=unitCode;
	}
	@Column(name = "unit_name", length = 255)
	public String getUnitName(){
		return unitName;
	}
	public void setUnitName(String unitName){
		this.unitName=unitName;
	}
	@Column(name = "pro_status", length = 255)
	public String getProStatus(){
		return proStatus;
	}
	public void setProStatus(String proStatus){
		this.proStatus=proStatus;
	}
	@Column(name = "pro_desc", length = 255)
	public String getProDesc(){
		return proDesc;
	}
	public void setProDesc(String proDesc){
		this.proDesc=proDesc;
	}
	@Column(name = "create_date", length = 255)
	public String getCreateDate(){
		return createDate;
	}
	public void setCreateDate(String createDate){
		this.createDate=createDate;
	}
	@Column(name = "pro_date", length = 255)
	public String getProDate() {
		return proDate;
	}
	public void setProDate(String proDate) {
		this.proDate = proDate;
	}
}

