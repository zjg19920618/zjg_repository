package com.boomhope.tms.entity.orcl;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.boomhope.tms.entity.Machine;

//@Entity
//@Table(name = "mac_deploy_machine")
//@SequenceGenerator(name="tmsSeq",sequenceName="tms_sequence")
//@SuppressWarnings("all")
public class DeployMachine implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String machineNo; 
	private String unitCode; 
	private Machine machine;
	private String tellerNo;
	private String managePassword;
	private String ip;
	private String remark;
	private String status;
	private String closeDesc;
	private String createDate;
	private String creater;
	private String updateDate;
	private String updater;
	private String repTime;
	private String repStatus;
	private String repDesc;
	private String majorkeyflag;//keyboard_major_keyflag
	private String workkeyflag;//keyboard_work_keyflag
	private String keyflag;//key_flag
	private String mackeyflag;//mac_keyflag
	private String authorizekeyflag;//authorize_keyflag
	private String channel;
	private String merNo;
	
	@Id
	@Column(name = "machine_no", unique = true, nullable = false)
	@GeneratedValue(generator="tmsSeq")
	public String getMachineNo() {
		return machineNo;
	}
	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}
	@Column(name = "unit_code", length = 18)
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "machine_code")
	public Machine getMachine() {
		return machine;
	}
	public void setMachine(Machine machine) {
		this.machine = machine;
	}
	@Column(name = "teller_no", length = 18)
	public String getTellerNo() {
		return tellerNo;
	}
	public void setTellerNo(String tellerNo) {
		this.tellerNo = tellerNo;
	}
	@Column(name = "manage_password", length = 18)
	public String getManagePassword() {
		return managePassword;
	}
	public void setManagePassword(String managePassword) {
		this.managePassword = managePassword;
	}
	@Column(name = "ip", length = 18)
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Column(name = "remark", length = 18)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "status", length = 18)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "close_desc", length = 18)
	public String getCloseDesc() {
		return closeDesc;
	}
	public void setCloseDesc(String closeDesc) {
		this.closeDesc = closeDesc;
	}
	@Column(name = "create_date", length = 18)
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	@Column(name = "creater", length = 18)
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	@Column(name = "update_date", length = 18)
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	@Column(name = "updater", length = 18)
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	@Column(name = "rep_time", length = 18)
	public String getRepTime() {
		return repTime;
	}
	public void setRepTime(String repTime) {
		this.repTime = repTime;
	}
	@Column(name = "rep_status", length = 18)
	public String getRepStatus() {
		return repStatus;
	}
	public void setRepStatus(String repStatus) {
		this.repStatus = repStatus;
	}
	@Column(name = "rep_desc", length = 18)
	public String getRepDesc() {
		return repDesc;
	}
	public void setRepDesc(String repDesc) {
		this.repDesc = repDesc;
	}
	@Column(name = "keyboard_major_keyflag", length = 18)
	public String getMajorkeyflag() {
		return majorkeyflag;
	}
	public void setMajorkeyflag(String majorkeyflag) {
		this.majorkeyflag = majorkeyflag;
	}
	@Column(name = "keyboard_work_keyflag", length = 18)
	public String getWorkkeyflag() {
		return workkeyflag;
	}
	public void setWorkkeyflag(String workkeyflag) {
		this.workkeyflag = workkeyflag;
	}
	@Column(name = "key_flag", length = 18)
	public String getKeyflag() {
		return keyflag;
	}
	public void setKeyflag(String keyflag) {
		this.keyflag = keyflag;
	}
	@Column(name = "mac_keyflag", length = 18)
	public String getMackeyflag() {
		return mackeyflag;
	}
	public void setMackeyflag(String mackeyflag) {
		this.mackeyflag = mackeyflag;
	}
	@Column(name = "authorize_keyflag", length = 18)
	public String getAuthorizekeyflag() {
		return authorizekeyflag;
	}
	public void setAuthorizekeyflag(String authorizekeyflag) {
		this.authorizekeyflag = authorizekeyflag;
	}
	
	@Column(name = "channel", length = 10)
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	@Column(name = "mer_no", length = 10)
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	
	
}
