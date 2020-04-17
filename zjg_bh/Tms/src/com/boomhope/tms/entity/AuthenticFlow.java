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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "authentic_flow")
@SequenceGenerator(name="tmsSeq",sequenceName="tms_sequence")
@SuppressWarnings("all")
public class AuthenticFlow implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer flowId;
	private String status;
	private String flowTime;
	private String flowDate;
	private String remark;
	private String branchNo;
	private String machineNo;

	
	@Id
	@Column(name = "flow_id", unique = true, nullable = false, length = 18)
	@GeneratedValue(generator="tmsSeq")
	public Integer getFlowId() {
		return flowId;
	}
	public void setFlowId(Integer flowId) {
		this.flowId = flowId;
	}
	
	@Column(name = "status",  length = 2)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "flow_time",  length = 30)
	public String getFlowTime() {
		return flowTime;
	}
	public void setFlowTime(String flowTime) {
		this.flowTime = flowTime;
	}
	@Column(name = "flow_date",  length = 30)
	public String getFlowDate() {
		return flowDate;
	}
	public void setFlowDate(String flowDate) {
		this.flowDate = flowDate;
	}
	@Column(name = "remark",  length = 255)
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	@Column(name = "branch_no",  length = 30)
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	@Column(name = "machine_no",  length = 30)
	public String getMachineNo() {
		return machineNo;
	}
	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}
}

