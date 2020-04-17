package com.boomhope.tms.pojo;

public class DeployControlPojo {
	
	private String machineNo; // 机器编号
	private String machineCode;//机器型号
	private String machineType;//机器类型
	private String machineName;//机器名称
	private String manuName;//所属厂商
	private String unitName;//所属机构
	private String repTime;//上报时间
	private String repStatus; //机器状态
	private String repDesc;//异常描述
	private Long errorCount;//异常错误次数
	
	
	public DeployControlPojo(){
		
	}
	public DeployControlPojo(Object[] objects) {
		super();
		this.machineNo = (String) objects[0];
		this.machineCode = (String) objects[1];
		this.machineType = (String) objects[2];
		this.machineName = (String) objects[3];
		this.manuName = (String) objects[4];
		this.unitName = (String) objects[5];
		this.repTime = (String) objects[6];
		this.repStatus = (String) objects[7];
		this.repDesc = (String) objects[8];
	}
	
	public String getMachineNo() {
		return machineNo;
	}
	public String getRepTime() {
		return repTime;
	}
	public void setRepTime(String repTime) {
		this.repTime = repTime;
	}
	public String getRepStatus() {
		return repStatus;
	}
	public void setRepStatus(String repStatus) {
		this.repStatus = repStatus;
	}
	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}
	public String getMachineCode() {
		return machineCode;
	}
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	public String getMachineType() {
		return machineType;
	}
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public String getManuName() {
		return manuName;
	}
	public void setManuName(String manuName) {
		this.manuName = manuName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	public Long getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(Long errorCount) {
		this.errorCount = errorCount;
	}
	public String getRepDesc() {
		return repDesc;
	}
	public void setRepDesc(String repDesc) {
		this.repDesc = repDesc;
	}
}
