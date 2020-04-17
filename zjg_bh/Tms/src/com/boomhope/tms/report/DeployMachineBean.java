package com.boomhope.tms.report;

import com.boomhope.tms.entity.BaseUnit;
import com.boomhope.tms.entity.Machine;

public class DeployMachineBean {
	private String machineNo; //机器编号
	private String macFacNum;//机器出厂编号
	private String machineType; //机器类型
	private String machineName;//机器名称
	private String manuName;//所属厂商
	private String districtCounty;//所属区县
	private String unitName;//机构名称
	private String status;//设备状态
	public String getMachineNo() {
		return machineNo;
	}
	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}
	public String getMacFacNum() {
		return macFacNum;
	}
	public void setMacFacNum(String macFacNum) {
		this.macFacNum = macFacNum;
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
	public String getDistrictCounty() {
		return districtCounty;
	}
	public void setDistrictCounty(String districtCounty) {
		this.districtCounty = districtCounty;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
