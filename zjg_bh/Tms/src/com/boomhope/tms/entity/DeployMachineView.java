package com.boomhope.tms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.transaction.Transactional;

import com.boomhope.tms.util.Dict;

@Entity
@Table(name = "deploy_machine_view")
@SuppressWarnings("all")
public class DeployMachineView implements Serializable {
	private static final long serialVersionUID = 1L;
	private DeployMachineViewId id;//机器编号
	private String machineType;//机器类型
	private String machineName;//机器名称
	private String manuCode;//厂商代码
	private String manuName;//厂商名称
	private String remark;
	private String unitCode;//机构编号
	private String unitName;//机构名称
	private String status;
	private String ip;//ip地址
	private String tellerNo;//
	private String machineCode;//机器型号
	private String machineTypeName;
	private String createDate;//创建日期
	
	private String repTime;//上传时间
	private String repStatus;//机器当前状态 1-正常 2-异常
	private String repDesc;//异常描述
	private String repStatusDesc;
	
	private String majorkeyflag;//keyboard_major_keyflag
	private String workkeyflag;//keyboard_work_keyflag
	private String keyflag;//key_flag
	private String mackeyflag;//mac_keyflag
	private String macFacNum;//机器出厂编号
	private String districtCounty;//所属区县
	@Transient
	public String getRepStatusDesc() {
		if(repStatus == null){
			repStatusDesc = "";
		}else if(repStatus.equals(Dict.REP_STATUS_NORMAL)){
			repStatusDesc = "正常";
		}else if(repStatus.equals(Dict.REP_STATUS_ERROR)){
			repStatusDesc = "异常";
		}
		return repStatusDesc;
	}
	@Id
	public DeployMachineViewId getId() {
		return id;
	}
	public void setId(DeployMachineViewId id) {
		this.id = id;
	}
	
	@Column(name = "create_date", length = 18)
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
	@Column(name = "machine_type", length = 255)
	public String getMachineType(){
		return machineType;
	}
	public void setMachineType(String machineType){
		this.machineType=machineType;
	}
	@Column(name = "machine_name", length = 255)
	public String getMachineName(){
		return machineName;
	}
	public void setMachineName(String machineName){
		this.machineName=machineName;
	}
	@Column(name = "manu_code", length = 255)
	public String getManuCode(){
		return manuCode;
	}
	public void setManuCode(String manuCode){
		this.manuCode=manuCode;
	}
	@Column(name = "manu_name", length = 255)
	public String getManuName(){
		return manuName;
	}
	public void setManuName(String manuName){
		this.manuName=manuName;
	}
	@Column(name = "remark", length = 255)
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	@Column(name = "unit_code", length = 255)
	public String getUnitCode(){
		return unitCode;
	}
	public void setUnitCode(String unitCode){
		this.unitCode=unitCode;
	}
	@Column(name = "unit_name", length = 255)
	public String getUnitName(){
		return unitName;
	}
	public void setUnitName(String unitName){
		this.unitName=unitName;
	}
	@Column(name = "status", length = 255)
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status=status;
	}
	@Column(name = "ip", length = 255)
	public String getIp(){
		return ip;
	}
	public void setIp(String ip){
		this.ip=ip;
	}
	@Column(name = "teller_no", length = 255)
	public String getTellerNo(){
		return tellerNo;
	}
	public void setTellerNo(String tellerNo){
		this.tellerNo=tellerNo;
	}
	@Column(name = "rep_Time", length = 255)
	public String getRepTime() {
		return repTime;
	}
	public void setRepTime(String repTime) {
		this.repTime = repTime;
	}
	@Column(name = "rep_Status", length = 255)
	public String getRepStatus() {
		return repStatus;
	}
	public void setRepStatus(String repStatus) {
		this.repStatus = repStatus;
	}
	@Column(name = "rep_Desc", length = 255)
	public String getRepDesc() {
		return repDesc;
	}
	public void setRepDesc(String repDesc) {
		this.repDesc = repDesc;
	}
	@Column(name = "machine_code", length = 255)
	public String getMachineCode() {
		return machineCode;
	}
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	@Column(name = "machine_type_desc", length = 255)
	public String getMachineTypeName() {
		return machineTypeName;
	}
	public void setMachineTypeName(String machineTypeName) {
		this.machineTypeName = machineTypeName;
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
	@Column(name = "mac_fac_num", length = 18)
	public String getMacFacNum() {
		return macFacNum;
	}
	public void setMacFacNum(String macFacNum) {
		this.macFacNum = macFacNum;
	}
	@Column(name = "district_county", length = 10)
	public String getDistrictCounty() {
		return districtCounty;
	}
	public void setDistrictCounty(String districtCounty) {
		this.districtCounty = districtCounty;
	}
}

