package com.boomhope.tms.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "base_user")
@SuppressWarnings("all")
public class BaseUser implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private String pwd;
	private String name;
	private String gender;
	private String phone;
	private String tel;
	private String address;
	private String status;
	private String idType;
	private String cerCode;
	private String title;
	private String email;
	private String qq;
	private String createDate;
	private String creater;
	private String updateDate;
	private String updater;
	private String lastTime;

	@Id
	@Column(name = "username", unique = true, nullable = false, length = 18)
	public String getUsername(){
		return username;
	}
	public void setUsername(String username){
		this.username=username;
	}
	@Column(name = "pwd", length = 60)
	public String getPwd(){
		return pwd;
	}
	public void setPwd(String pwd){
		this.pwd=pwd;
	}
	@Column(name = "name", length = 255)
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
	@Column(name = "gender", length = 2)
	public String getGender(){
		return gender;
	}
	public void setGender(String gender){
		this.gender=gender;
	}
	@Column(name = "phone", length = 11)
	public String getPhone(){
		return phone;
	}
	public void setPhone(String phone){
		this.phone=phone;
	}
	@Column(name = "tel", length = 20)
	public String getTel(){
		return tel;
	}
	public void setTel(String tel){
		this.tel=tel;
	}
	@Column(name = "address", length = 255)
	public String getAddress(){
		return address;
	}
	public void setAddress(String address){
		this.address=address;
	}
	@Column(name = "status", length = 2)
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status=status;
	}
	@Column(name = "id_type", length = 2)
	public String getIdType(){
		return idType;
	}
	public void setIdType(String idType){
		this.idType=idType;
	}
	@Column(name = "cer_code", length = 255)
	public String getCerCode(){
		return cerCode;
	}
	public void setCerCode(String cerCode){
		this.cerCode=cerCode;
	}
	@Column(name = "title", length = 255)
	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title=title;
	}
	@Column(name = "email", length = 255)
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email=email;
	}
	@Column(name = "qq", length = 255)
	public String getQq(){
		return qq;
	}
	public void setQq(String qq){
		this.qq=qq;
	}
	@Column(name = "create_date", length = 255)
	public String getCreateDate(){
		return createDate;
	}
	public void setCreateDate(String createDate){
		this.createDate=createDate;
	}
	@Column(name = "creater", length = 255)
	public String getCreater(){
		return creater;
	}
	public void setCreater(String creater){
		this.creater=creater;
	}
	@Column(name = "update_date", length = 255)
	public String getUpdateDate(){
		return updateDate;
	}
	public void setUpdateDate(String updateDate){
		this.updateDate=updateDate;
	}
	@Column(name = "updater", length = 255)
	public String getUpdater(){
		return updater;
	}
	public void setUpdater(String updater){
		this.updater=updater;
	}
	
	@Column(name = "last_time", length = 14)
	public String getLastTime(){
		return lastTime;
	}
	public void setLastTime(String lastTime){
		this.lastTime=lastTime;
	}
}

