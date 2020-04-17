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
 * 行名行号对照表
 * @author wxm
 *
 */
@Entity
@Table(name = "t_cnaps_bkinfo")
@SuppressWarnings("all")
public class TCnapsBkinfo  implements Serializable{
	
	private String bankNo;	// 银行行号
	private String bankType;	// 行别代码
	private String clrBankNo;	// 清算行行号
	private String nodeCode;	// 所在节点代码
	private String suprList;	// 本行上级参与者
	private String pbcCode;	// 所属人行代码
	private String centerCode;	// 所在城市代码
	private String lname;	// 参与者全称
	private String sname;	// 参与者简称
	private String addr;	// 地址
	private String postCode;	// 邮编
	private String tel;	// 电话
	private String email;	// EMAIL
	private String flag;	// 有效标志：0-有效，1-无效
	private String instType;	// 参与机构类别
	private String legalPer;	// 所属法人
	private String bearBankNo;	// 承接行行号
	private String signFlag;	// 加入业务系统标识(0未加入，1加入。从左到右位为大额、小额、网银，其它位数保留)
	private String issueno;	// 变更期数
	private String effdate;	// 生效日期
	private String invdate;	// 失效日期
	private String alttype;// 变更类型(CC00：新增 CC01：变更 CC02：撤销)
	
	@Id
	@Column(name = "bank_no", nullable = false, length = 14)
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	@Column(name = "bank_type",  length = 3)
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	@Column(name = "clr_bank_no",  length = 14)
	public String getClrBankNo() {
		return clrBankNo;
	}
	public void setClrBankNo(String clrBankNo) {
		this.clrBankNo = clrBankNo;
	}
	@Column(name = "node_code",  length = 5)
	public String getNodeCode() {
		return nodeCode;
	}
	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}
	@Column(name = "supr_list",  length = 130)
	public String getSuprList() {
		return suprList;
	}
	public void setSuprList(String suprList) {
		this.suprList = suprList;
	}
	@Column(name = "pbc_code",  length = 14)
	public String getPbcCode() {
		return pbcCode;
	}
	public void setPbcCode(String pbcCode) {
		this.pbcCode = pbcCode;
	}
	@Column(name = "center_code",  length = 4)
	public String getCenterCode() {
		return centerCode;
	}
	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}
	@Column(name = "lname",  length = 255)
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	@Column(name = "sname",  length = 20)
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	@Column(name = "addr",  length = 255)
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	@Column(name = "post_code",  length = 6)
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	@Column(name = "tel",  length = 50)
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	@Column(name = "email",  length = 30)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "flag",  length = 1)
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Column(name = "inst_type",  length = 2)
	public String getInstType() {
		return instType;
	}
	public void setInstType(String instType) {
		this.instType = instType;
	}
	@Column(name = "legal_per",  length = 14)
	public String getLegalPer() {
		return legalPer;
	}
	public void setLegalPer(String legalPer) {
		this.legalPer = legalPer;
	}
	@Column(name = "bear_bank_no",  length = 14)
	public String getBearBankNo() {
		return bearBankNo;
	}
	public void setBearBankNo(String bearBankNo) {
		this.bearBankNo = bearBankNo;
	}
	@Column(name = "sign_flag",  length = 10)
	public String getSignFlag() {
		return signFlag;
	}
	public void setSignFlag(String signFlag) {
		this.signFlag = signFlag;
	}
	@Column(name = "issueno",  length = 8)
	public String getIssueno() {
		return issueno;
	}
	public void setIssueno(String issueno) {
		this.issueno = issueno;
	}
	@Column(name = "effdate",  length = 8)
	public String getEffdate() {
		return effdate;
	}
	public void setEffdate(String effdate) {
		this.effdate = effdate;
	}
	@Column(name = "invdate",  length = 8)
	public String getInvdate() {
		return invdate;
	}
	public void setInvdate(String invdate) {
		this.invdate = invdate;
	}
	@Column(name = "alttype",  length = 4)
	public String getAlttype() {
		return alttype;
	}
	public void setAlttype(String alttype) {
		this.alttype = alttype;
	}	
	

}
