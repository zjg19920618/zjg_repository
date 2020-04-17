package com.boomhope.tms.entity.orcl;

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

import com.boomhope.tms.entity.BaseUnit;
import com.boomhope.tms.entity.DeployMachine;

@Entity
@Table(name = "CDJ_ORDER")
@SequenceGenerator(name = "tmsSeq", sequenceName = "tms_sequence")
@SuppressWarnings("all")
public class CdjOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private DeployMachine deployMachine;//出厂编号
	private String terminalCode;//出厂编号
	private String status; //机构状态
	private String createDate; //创建时间
	private BaseUnit baseUnit; //机构编号
	private String unitName; //机构名称
	private String orderNo; 
	private String certNo;//凭证号
	private String cardNo;//转出卡号
	private String customerName;//姓名
	private String accountNo;
	private String productCode;//业务标识
	private String subAccountNo;//存单账户
	private String depositPeriod;//存期
	private String depositAmt;//存款金额
	private String rate;
	private String interest;
	private String depositPasswordEnabled;
	private String depositPassword;
	private String req_02808;
	private String rep_02808;
	private String req_07506;
	private String rep_07506;
	private String req_07505;
	private String rep_07505;
	private String remark;
	private String depositResaveEnabled;
	private String point;
	private String productName;//业务名称
	private String payType;
	
	@Id
	@Column(name = "id",unique = true, nullable = false, length = 255)
	@GeneratedValue(generator="tmsSeq")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "terminal_code",referencedColumnName="mac_fac_num")
	public DeployMachine getDeployMachine() {
		return deployMachine;
	}
	public void setDeployMachine(DeployMachine deployMachine) {
		this.deployMachine = deployMachine;
	}
	@Column(name = "status",length = 255)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "create_date",length = 255)
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unit_code")
	public BaseUnit getBaseUnit() {
		return baseUnit;
	}
	public void setBaseUnit(BaseUnit baseUnit) {
		this.baseUnit = baseUnit;
	}
	@Column(name = "unit_name",length = 255)
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	@Column(name = "order_no",length = 255)
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	@Column(name = "cert_no",length = 255)
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	@Column(name = "card_no",length = 255)
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	@Column(name = "customer_name",length = 255)
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	@Column(name = "account_no",length = 255)
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	@Column(name = "product_code",length = 255)
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	@Column(name = "sub_account_no",length = 255)
	public String getSubAccountNo() {
		return subAccountNo;
	}
	public void setSubAccountNo(String subAccountNo) {
		this.subAccountNo = subAccountNo;
	}
	@Column(name = "deposit_period",length = 255)
	public String getDepositPeriod() {
		return depositPeriod;
	}
	public void setDepositPeriod(String depositPeriod) {
		this.depositPeriod = depositPeriod;
	}
	@Column(name = "deposit_amt",length = 255)
	public String getDepositAmt() {
		return depositAmt;
	}
	public void setDepositAmt(String depositAmt) {
		this.depositAmt = depositAmt;
	}
	@Column(name = "rate",length = 255)
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	@Column(name = "interest",length = 255)
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	@Column(name = "deposit_password_enabled",length = 255)
	public String getDepositPasswordEnabled() {
		return depositPasswordEnabled;
	}
	public void setDepositPasswordEnabled(String depositPasswordEnabled) {
		this.depositPasswordEnabled = depositPasswordEnabled;
	}
	@Column(name = "deposit_password",length = 255)
	public String getDepositPassword() {
		return depositPassword;
	}
	public void setDepositPassword(String depositPassword) {
		this.depositPassword = depositPassword;
	}
	@Column(name = "req_02808",length = 255)
	public String getReq_02808() {
		return req_02808;
	}
	public void setReq_02808(String req_02808) {
		this.req_02808 = req_02808;
	}
	@Column(name = "rep_02808",length = 255)
	public String getRep_02808() {
		return rep_02808;
	}
	public void setRep_02808(String rep_02808) {
		this.rep_02808 = rep_02808;
	}
	@Column(name = "req_07506",length = 255)
	public String getReq_07506() {
		return req_07506;
	}
	public void setReq_07506(String req_07506) {
		this.req_07506 = req_07506;
	}
	@Column(name = "rep_07506",length = 255)
	public String getRep_07506() {
		return rep_07506;
	}
	public void setRep_07506(String rep_07506) {
		this.rep_07506 = rep_07506;
	}
	@Column(name = "req_07505",length = 255)
	public String getReq_07505() {
		return req_07505;
	}
	public void setReq_07505(String req_07505) {
		this.req_07505 = req_07505;
	}
	@Column(name = "rep_07505",length = 255)
	public String getRep_07505() {
		return rep_07505;
	}
	public void setRep_07505(String rep_07505) {
		this.rep_07505 = rep_07505;
	}
	@Column(name = "remark",length = 255)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "deposit_resave_enabled",length = 255)
	public String getDepositResaveEnabled() {
		return depositResaveEnabled;
	}
	public void setDepositResaveEnabled(String depositResaveEnabled) {
		this.depositResaveEnabled = depositResaveEnabled;
	}
	@Column(name = "point",length = 255)
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	@Column(name = "product_name",length = 255)
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Column(name = "pay_type",length = 20)
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
}
