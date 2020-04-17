package com.boomhope.tms.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "base_role")
@SequenceGenerator(name="tmsSeq",sequenceName="tms_sequence")
@SuppressWarnings("all")
public class BaseRole implements Serializable {
	private static final long serialVersionUID = 1L;
	private int roleCode;
	private String roleName;
	private String status;
	private String createDate;
	private String creater;
	private String updateDate;
	private String updater;
	private String roleDesc;

	@Id
	@Column(name = "role_code", unique = true, nullable = false, length = 18)
	@GeneratedValue(generator="tmsSeq")
	public int getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(int roleCode) {
		this.roleCode = roleCode;
	}
	@Column(name = "role_name", length = 255)
	public String getRoleName(){
		return roleName;
	}
	public void setRoleName(String roleName){
		this.roleName=roleName;
	}
	@Column(name = "status", length = 255)
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status=status;
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
	@Column(name = "role_desc", length = 255)
	public String getRoleDesc(){
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc){
		this.roleDesc=roleDesc;
	}
}

