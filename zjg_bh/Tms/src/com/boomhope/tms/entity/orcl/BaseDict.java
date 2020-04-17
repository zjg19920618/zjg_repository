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
//@Table(name = "base_dict")
//@SequenceGenerator(name="tmsSeq",sequenceName="tms_sequence")
//@SuppressWarnings("all")
public class BaseDict implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String groupName;
	private String groupDesc;
	private String valueName;
	private String valueDesc;
	private String remark;

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 18)
	@GeneratedValue(generator="tmsSeq")
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id=id;
	}
	@Column(name = "group_name",  length = 255)
	public String getGroupName(){
		return groupName;
	}
	public void setGroupName(String groupName){
		this.groupName=groupName;
	}
	@Column(name = "group_desc",  length = 255)
	public String getGroupDesc(){
		return groupDesc;
	}
	public void setGroupDesc(String groupDesc){
		this.groupDesc=groupDesc;
	}
	@Column(name = "value_name",  length = 255)
	public String getValueName(){
		return valueName;
	}
	public void setValueName(String valueName){
		this.valueName=valueName;
	}
	@Column(name = "value_desc",  length = 255)
	public String getValueDesc(){
		return valueDesc;
	}
	public void setValueDesc(String valueDesc){
		this.valueDesc=valueDesc;
	}
	@Column(name = "remark",  length = 255)
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
}

