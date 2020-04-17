package com.boomhope.tms.entity;

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

@Entity
@Table(name = "mac_warning")
@SequenceGenerator(name="tmsSeq",sequenceName="tms_sequence")
@SuppressWarnings("all")
public class MacWarning implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private DeployMachine deployMachine;//机器编号
	private String proStatus;//1-未处理\n            2-已处理'
	private String proDate;//'处理时间'
	private String proDesc;//'处理描述'
	private String createDate;//'创建时间'
	private String warDesc;//预警描述
	private String warType;//预警类型   -W-预警  E-异常'
	
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 18)
	@GeneratedValue(generator="tmsSeq")
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id=id;
	}
	@Column(name = "war_desc",  length = 255)
	public String getWarDesc() {
		return warDesc;
	}
	public void setWarDesc(String warDesc) {
		this.warDesc = warDesc;
	}
	@Column(name = "war_type",  length = 1)
	public String getWarType() {
		return warType;
	}
	public void setWarType(String warType) {
		this.warType = warType;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "machine_no")
	public DeployMachine getDeployMachine(){
		return deployMachine;
	}
	public void setDeployMachine(DeployMachine deployMachine){
		this.deployMachine=deployMachine;
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

