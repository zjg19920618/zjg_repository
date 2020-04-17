package com.boomhope.tms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/***
 * 交易流水
 * @author shaopeng
 *
 */
@Entity
@Table(name = "trans_flow")
@SequenceGenerator(name="tmsSeq",sequenceName="tms_sequence")
@SuppressWarnings("all")
public class TransFlow  implements Serializable{
	
	private long transFlowNo;	// 交易流水号
	private String transCode;	// 交易代码
	private String transName;	// 交易名称
	private String productCode;	// 产品编号
	private String branchNo;	// 机构号
	private String machineType;	// 机器类型
	private String machineId;	// 机器ID
	private String transStatus;	// 交易状态
	private String businessStatus;	// 业务状态
	private String transStatusDesc;	// 交易状态描述
	private String transDate;	// 交易日期
	private String transTime;	// 交易时间
	
	@Id
	@Column(name = "trans_flow_no", nullable = false, length = 16)
	@GeneratedValue(generator="tmsSeq")
	public long getTransFlowNo() {
		return transFlowNo;
	}

	public void setTransFlowNo(long transFlowNo) {
		this.transFlowNo = transFlowNo;
	}

	@Column(name = "trans_code", nullable = false, length = 8)
	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	@Column(name = "trans_name", nullable = false, length = 32)
	public String getTransName() {
		return transName;
	}

	public void setTransName(String transName) {
		this.transName = transName;
	}

	@Column(name = "product_code", nullable = true, length = 8)
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	@Column(name = "branch_no", nullable = false, length = 16)
	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	@Column(name = "machine_type", nullable = false, length = 16)
	public String getMachineType() {
		return machineType;
	}

	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}

	@Column(name = "machine_id", nullable = false, length = 32)
	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	@Column(name = "trans_status", nullable = false, length = 1)
	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	@Column(name = "business_status", nullable = true, length = 8)
	public String getBusinessStatus() {
		return businessStatus;
	}

	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
	}

	@Column(name = "trans_status_desc", nullable = true, length = 512)
	public String getTransStatusDesc() {
		return transStatusDesc;
	}

	public void setTransStatusDesc(String transStatusDesc) {
		this.transStatusDesc = transStatusDesc;
	}

	@Column(name = "trans_date", nullable = false, length = 8)
	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	@Column(name = "trans_time", nullable = false, length = 6)
	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}


}
