package com.boomhope.tms.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DeployMachineViewId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String machineNo;

	@Column(name = "machine_no", length = 18)
	public String getMachineNo(){
		return machineNo;
	}
	public void setMachineNo(String machineNo){
		this.machineNo=machineNo;
	}
	
}

