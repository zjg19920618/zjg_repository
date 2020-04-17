package com.boomhope.tms.entity.orcl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

//@Entity
//@Table(name = "mac_peripherals")
//@SequenceGenerator(name="tmsSeq",sequenceName="tms_sequence")
//@SuppressWarnings("all")
public class Peripherals implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private int periId; 
	private String periCode; 
	private String periType; 
	private String periName;
	private String periDesc;
//	private String machineCode;
	private String createDate; 
	private String creater;
	private String selected;
	

	@Id
	@Column(name = "peri_id", unique = true, nullable = false)
	@GeneratedValue(generator="tmsSeq")
	public int getPeriId() {
		return periId;
	}
	public void setPeriId(int periId) {
		this.periId = periId;
	}
	@Column(name = "peri_code", length = 32)
	public String getPeriCode() {
		return periCode;
	}
	public void setPeriCode(String periCode) {
		this.periCode = periCode;
	}
	@Column(name = "peri_type", length = 8)
	public String getPeriType() {
		return periType;
	}
	public void setPeriType(String periType) {
		this.periType = periType;
	}
	@Column(name = "peri_name", length = 32)
	public String getPeriName() {
		return periName;
	}
	public void setPeriName(String periName) {
		this.periName = periName;
	}
	@Column(name = "peri_desc", length = 255)
	public String getPeriDesc() {
		return periDesc;
	}
	public void setPeriDesc(String periDesc) {
		this.periDesc = periDesc;
	}
//	@Column(name = "machine_code", length = 32)
//	public String getMachineCode() {
//		return machineCode;
//	}
//	public void setMachineCode(String machineCode) {
//		this.machineCode = machineCode;
//	}
	@Column(name = "create_date", length = 14)
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	@Column(name = "creater", length = 255)
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	
	@Transient
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	
}
