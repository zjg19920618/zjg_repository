package com.boomhope.tms.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "base_unit")
@SuppressWarnings("all")
public class BaseUnit implements Serializable {
	private static final long serialVersionUID = 1L;
	private String unitCode;
	private String unitName;
	private String parentCode;
	private String unitType;
	private String tel;
	private String address;
	private String contactor;
	private String email;
	private String contactorTel;
	private String contactorPhone;
	private String status;
	private String createDate;
	private String creater;
	private String updateDate;
	private String updater;
	private String districtCounty;//所属区县

	@Id
	@Column(name = "unit_code", unique = true, nullable = false, length = 18)
	public String getUnitCode(){
		return unitCode;
	}
	public void setUnitCode(String unitCode){
		this.unitCode=unitCode;
	}
	@Column(name = "unit_name",  length = 255)
	public String getUnitName(){
		return unitName;
	}
	public void setUnitName(String unitName){
		this.unitName=unitName;
	}
	@Column(name = "parent_code",  length = 18)
	public String getParentCode(){
		return parentCode;
	}
	public void setParentCode(String parentCode){
		this.parentCode=parentCode;
	}
	@Column(name = "unit_type",  length = 2)
	public String getUnitType(){
		return unitType;
	}
	public void setUnitType(String unitType){
		this.unitType=unitType;
	}
	@Column(name = "tel",  length = 255)
	public String getTel(){
		return tel;
	}
	public void setTel(String tel){
		this.tel=tel;
	}
	@Column(name = "address",  length = 255)
	public String getAddress(){
		return address;
	}
	public void setAddress(String address){
		this.address=address;
	}
	@Column(name = "contactor",  length = 255)
	public String getContactor(){
		return contactor;
	}
	public void setContactor(String contactor){
		this.contactor=contactor;
	}
	@Column(name = "contactor_tel",  length = 255)
	public String getContactorTel(){
		return contactorTel;
	}
	public void setContactorTel(String contactorTel){
		this.contactorTel=contactorTel;
	}
	@Column(name = "contactor_phone",  length = 255)
	public String getContactorPhone(){
		return contactorPhone;
	}
	public void setContactorPhone(String contactorPhone){
		this.contactorPhone=contactorPhone;
	}
	@Column(name = "status",  length = 255)
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status=status;
	}
	@Column(name = "create_date",  length = 255)
	public String getCreateDate(){
		return createDate;
	}
	public void setCreateDate(String createDate){
		this.createDate=createDate;
	}
	@Column(name = "creater",  length = 255)
	public String getCreater(){
		return creater;
	}
	public void setCreater(String creater){
		this.creater=creater;
	}
	@Column(name = "update_date",  length = 255)
	public String getUpdateDate(){
		return updateDate;
	}
	public void setUpdateDate(String updateDate){
		this.updateDate=updateDate;
	}
	@Column(name = "updater",  length = 255)
	public String getUpdater(){
		return updater;
	}
	public void setUpdater(String updater){
		this.updater=updater;
	}
	@Column(name = "email",  length = 255)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "district_county", length = 10)
	public String getDistrictCounty() {
		return districtCounty;
	}
	public void setDistrictCounty(String districtCounty) {
		this.districtCounty = districtCounty;
	}
}

