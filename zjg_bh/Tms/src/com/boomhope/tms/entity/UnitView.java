package com.boomhope.tms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "unit_view")
@SuppressWarnings("all")
public class UnitView implements Serializable {
	private static final long serialVersionUID = 1L;
	private UnitViewId unitViewId;
	private String parentName;
	private String unitName;
	private String parentCode;
	private String unitType;
	private String tel;
	private String address;
	private String contactor;
	private String contactorTel;
	private String contactorPhone;
	private String status;
	private String createDate;
	private String email;
	private String districtCounty;//所属区县


	@Id
	public UnitViewId getUnitViewId() {
		return unitViewId;
	}
	public void setUnitViewId(UnitViewId unitViewId) {
		this.unitViewId = unitViewId;
	}
	@Column(name = "parent_name", length = 255)
	public String getParentName(){
		return parentName;
	}
	public void setParentName(String parentName){
		this.parentName=parentName;
	}
	@Column(name = "unit_name", length = 255)
	public String getUnitName(){
		return unitName;
	}
	public void setUnitName(String unitName){
		this.unitName=unitName;
	}
	@Column(name = "parent_code", length = 18)
	public String getParentCode(){
		return parentCode;
	}
	public void setParentCode(String parentCode){
		this.parentCode=parentCode;
	}
	@Column(name = "unit_type", length = 18)
	public String getUnitType(){
		return unitType;
	}
	public void setUnitType(String unitType){
		this.unitType=unitType;
	}
	@Column(name = "tel", length = 18)
	public String getTel(){
		return tel;
	}
	public void setTel(String tel){
		this.tel=tel;
	}
	@Column(name = "address", length = 18)
	public String getAddress(){
		return address;
	}
	public void setAddress(String address){
		this.address=address;
	}
	@Column(name = "contactor", length = 18)
	public String getContactor(){
		return contactor;
	}
	public void setContactor(String contactor){
		this.contactor=contactor;
	}
	@Column(name = "contactor_tel", length = 18)
	public String getContactorTel(){
		return contactorTel;
	}
	public void setContactorTel(String contactorTel){
		this.contactorTel=contactorTel;
	}
	@Column(name = "contactor_phone", length = 18)
	public String getContactorPhone(){
		return contactorPhone;
	}
	public void setContactorPhone(String contactorPhone){
		this.contactorPhone=contactorPhone;
	}
	@Column(name = "status", length = 18)
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status=status;
	}
	@Column(name = "create_date", length = 255)
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	@Column(name = "email", length = 255)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "district_county", length = 30)
	public String getDistrictCounty() {
		return districtCounty;
	}
	public void setDistrictCounty(String districtCounty) {
		this.districtCounty = districtCounty;
	}
}

