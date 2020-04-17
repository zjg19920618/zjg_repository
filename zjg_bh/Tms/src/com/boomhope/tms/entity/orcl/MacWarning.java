package com.boomhope.tms.entity.orcl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//@Entity
//@Table(name = "mac_warning")
//@SequenceGenerator(name="tmsSeq",sequenceName="tms_sequence")
//@SuppressWarnings("all")
public class MacWarning implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String machineNo;
	private String proStatus;
	private String proDate;
	private String proDesc;
	private String createDate;
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 18)
	@GeneratedValue(generator="tmsSeq")
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id=id;
	}
	@Column(name = "machine_no",  length = 18)
	public String getMachineNo(){
		return machineNo;
	}
	public void setMachineNo(String machineNo){
		this.machineNo=machineNo;
	}
	@Column(name = "pro_status",  length = 255)
	public String getProStatus(){
		return proStatus;
	}
	public void setProStatus(String proStatus){
		this.proStatus=proStatus;
	}
	@Column(name = "pro_date",  length = 255)
	public String getProDate(){
		return proDate;
	}
	public void setProDate(String proDate){
		this.proDate=proDate;
	}
	@Column(name = "pro_desc",  length = 255)
	public String getProDesc(){
		return proDesc;
	}
	public void setProDesc(String proDesc){
		this.proDesc=proDesc;
	}
	@Column(name = "create_date",  length = 255)
	public String getCreateDate(){
		return createDate;
	}
	public void setCreateDate(String createDate){
		this.createDate=createDate;
	}
}

