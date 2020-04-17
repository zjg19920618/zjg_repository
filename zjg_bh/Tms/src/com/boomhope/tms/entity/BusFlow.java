package com.boomhope.tms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "bus_flow")
@SequenceGenerator(name="tmsSeq",sequenceName="tms_sequence")
@SuppressWarnings("all")
public class BusFlow implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int flowId;//业务流水ID
	private DeployMachine deployMachine;
	private BaseUnit baseUnit;//机构代码
	private String projectCode;//产品编号
	private String projectName;//产品名称
	private String billNo;//存单账号
	private String certNo;//凭证号
	private String realPri;//实付本金
	private String realInte;//实付利息
	private String canelType;//销户类型
	private String bankCardNo;//转入卡号
	private String bankCardName;//转入卡姓名
	private String createDate;//创建时间
	private String checkStatus;//标记存单是否自动识别，1自动识别，2手动输入
	
	@Id
	@Column(name = "flow_id", length = 18)
	@GeneratedValue(generator="tmsSeq")
	public int getFlowId() {
		return flowId;
	}
	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unit_code")
	public BaseUnit getBaseUnit() {
		return baseUnit;
	}
	public void setBaseUnit(BaseUnit baseUnit) {
		this.baseUnit = baseUnit;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "machine_no")
	public DeployMachine getDeployMachine() {
		return deployMachine;
	}
	public void setDeployMachine(DeployMachine deployMachine) {
		this.deployMachine = deployMachine;
	}
	@Column(name = "project_code",length = 10)
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	@Column(name = "project_name",length = 60)
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	@Column(name = "bill_no",length = 60)
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	@Column(name = "cert_no",length = 60)
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	@Column(name = "real_pri",length = 12)
	public String getRealPri() {
		return realPri;
	}
	public void setRealPri(String realPri) {
		this.realPri = realPri;
	}
	@Column(name = "real_inte",length = 12)
	public String getRealInte() {
		return realInte;
	}
	public void setRealInte(String realInte) {
		this.realInte = realInte;
	}
	@Column(name = "canel_type",length = 1)
	public String getCanelType() {
		return canelType;
	}
	public void setCanelType(String canelType) {
		this.canelType = canelType;
	}
	@Column(name = "bank_card_no",length = 30)
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	@Column(name = "bank_card_name",length = 60)
	public String getBankCardName() {
		return bankCardName;
	}
	public void setBankCardName(String bankCardName) {
		this.bankCardName = bankCardName;
	}
	@Column(name = "create_date",length = 14)
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	@Column(name = "checkstatus",length = 10)
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

}
