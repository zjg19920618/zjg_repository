package com.boomhope.tms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MacWarningViewId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String machineNo;
	private Integer id;
	@Column(name = "machine_no", length = 18)
	public String getMachineNo(){
		return machineNo;
	}
	public void setMachineNo(String machineNo){
		this.machineNo=machineNo;
	}
	@Column(name = "id", length = 18)
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id=id;
	}
}

