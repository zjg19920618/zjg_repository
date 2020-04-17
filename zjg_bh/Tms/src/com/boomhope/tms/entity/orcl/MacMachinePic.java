package com.boomhope.tms.entity.orcl;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//@Entity
//@Table(name = "mac_machine_pic")
//@SequenceGenerator(name="tmsSeq",sequenceName="tms_sequence")
//@SuppressWarnings("all")
public class MacMachinePic implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String machineCode;
	private String pic;
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 18)
	@GeneratedValue(generator="tmsSeq")
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id=id;
	}
	@Column(name = "machine_code", length = 20)
	public String getMachineCode(){
		return machineCode;
	}
	public void setMachineCode(String machineCode){
		this.machineCode=machineCode;
	}
	@Column(name = "pic", length = 20)
	public String getPic(){
		return pic;
	}
	public void setPic(String pic){
		this.pic=pic;
	}
}

