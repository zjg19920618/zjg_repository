package com.boomhope.tms.report;

import com.boomhope.tms.entity.Manu;

public class MachineBean {

	private String machineCode; //机器型号
	private String machineType; //机器类型
	private String machineName; //机器名称
	private String manuCode; //所属厂商
	private String machineDesc; //机器描述
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
	public String getManuCode() {
		return manuCode;
	}
	public void setManuCode(String manuCode) {
		this.manuCode = manuCode;
	}
	public String getMachineDesc() {
		return machineDesc;
	}
	public void setMachineDesc(String machineDesc) {
		this.machineDesc = machineDesc;
	}
	
	
}
