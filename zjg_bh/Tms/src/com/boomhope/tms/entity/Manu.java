package com.boomhope.tms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mac_manufacturer")
@SuppressWarnings("all")
public class Manu implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String manuCode; 
	private String manuName; 
	private String manuDesc;
	private String manuStatus; 
	private String conPerson;
	private String Phone; 
	private String tel;
//	private String createTime;
	private String createDate;
	private String creater;
	
	@Id
	@Column(name = "manu_code", unique = true, nullable = false)
	public String getManuCode() {
		return manuCode;
	}
	public void setManuCode(String manuCode) {
		this.manuCode = manuCode;
	}
	@Column(name = "manu_name", length = 64)
	public String getManuName() {
		return manuName;
	}
	public void setManuName(String manuName) {
		this.manuName = manuName;
	}
	@Column(name = "manu_desc", length = 256)
	public String getManuDesc() {
		return manuDesc;
	}
	public void setManuDesc(String manuDesc) {
		this.manuDesc = manuDesc;
	}
	@Column(name = "manu_status", length = 1)
	public String getManuStatus() {
		return manuStatus;
	}
	public void setManuStatus(String manuStatus) {
		this.manuStatus = manuStatus;
	}
	@Column(name = "con_person", length = 1)
	public String getConPerson() {
		return conPerson;
	}
	public void setConPerson(String conPerson) {
		this.conPerson = conPerson;
	}
	@Column(name = "phone", length = 1)
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String Phone) {
		this.Phone = Phone;
	}
	@Column(name = "tel", length = 1)
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
//	@Column(name = "create_time", length = 1)
//	public String getCreateTime() {
//		return createTime;
//	}
//	public void setCreateTime(String createTime) {
//		this.createTime = createTime;
//	}
	@Column(name = "create_date", length = 1)
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	@Column(name = "creater", length = 1)
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	
	
	
}
