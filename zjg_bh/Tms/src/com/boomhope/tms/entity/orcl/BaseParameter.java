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
//@Table(name = "base_parameter")
//@SequenceGenerator(name="tmsSeq",sequenceName="tms_sequence")
//@SuppressWarnings("all")
public class BaseParameter implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String parameterType;
	private String parameterName;
	private String parameterValue;

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 18)
	@GeneratedValue(generator="tmsSeq")
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id=id;
	}
	@Column(name = "parameter_type", length = 255)
	public String getParameterType(){
		return parameterType;
	}
	public void setParameterType(String parameterType){
		this.parameterType=parameterType;
	}
	@Column(name = "parameter_name", length = 255)
	public String getParameterName(){
		return parameterName;
	}
	public void setParameterName(String parameterName){
		this.parameterName=parameterName;
	}
	@Column(name = "parameter_value", length = 255)
	public String getParameterValue(){
		return parameterValue;
	}
	public void setParameterValue(String parameterValue){
		this.parameterValue=parameterValue;
	}
}

