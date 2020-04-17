package com.boomhope.tms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class UnitViewId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String unitCode;

	

	@Column(name = "unit_code", length = 18)
	public String getUnitCode(){
		return unitCode;
	}
	public void setUnitCode(String unitCode){
		this.unitCode=unitCode;
	}
	

}

