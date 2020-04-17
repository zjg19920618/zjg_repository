package com.boomhope.tms.pojo;

public class UnitMachineStatusPojo {

	private String districtCounty;//所属区县
	private String unitName;//机构名称
	private String machineNo;//机器编号
	private String machineType;//机器类型
	private String machineName;//机器名称
	private String machineCode;//机器型号
	private String repStatus;//机器当前状态 1-正常 2-异常
	private String repTime;//上报时间
	private String pic;//图片地址(通过机器编号2次查询)
	private String repDesc;//异常描述
	private String dqTime;//当前时间
	public String getDqTime() {
		return dqTime;
	}
	public void setDqTime(String dqTime) {
		this.dqTime = dqTime;
	}
	public UnitMachineStatusPojo(){
		
	}
	public UnitMachineStatusPojo(Object[] objects){
		super();
		this.districtCounty = (String) objects[0];
		this.unitName = (String) objects[1];
		this.machineNo = (String) objects[2];
		this.machineType = (String) objects[3];
		this.machineName = (String) objects[4];
		this.machineCode = (String) objects[5];
		this.repTime = (String) objects[6];
		this.repStatus = (String) objects[7];
		this.repDesc = (String) objects[8];
	}
	public String getRepDesc() {
		return repDesc;
	}
	public void setRepDesc(String repDesc) {
		this.repDesc = repDesc;
	}
	public String getRepTime() {
		return repTime;
	}
	public void setRepTime(String repTime) {
		this.repTime = repTime;
	}
	
	public String getMachineCode() {
		return machineCode;
	}
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
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
	public String getMachineNo() {
		return machineNo;
	}
	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
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
	public String getRepStatus() {
		return repStatus;
	}
	public void setRepStatus(String repStatus) {
		this.repStatus = repStatus;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
}
