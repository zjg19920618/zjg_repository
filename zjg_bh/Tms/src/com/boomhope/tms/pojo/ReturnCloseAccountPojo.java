package com.boomhope.tms.pojo;
/**
 *  销户查询导出
 * @author Administrator
 *
 */
public class ReturnCloseAccountPojo {

	private String unitCode; // 机构编号
	private String unitName;//	机构名称
	private String machineNo;// 设备编号
	private String projectName;// 产品名称
	private Long number;// 销户次数
	
	public ReturnCloseAccountPojo() {
		super();
	}

	public ReturnCloseAccountPojo(Object[] objects) {
		super();
		this.unitCode = (String) objects[0];
		this.unitName = (String) objects[1];
		this.machineNo = (String) objects[2];
		this.projectName = (String) objects[3];
		this.number = (Long) objects[4];
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getMachineNo() {
		return machineNo;
	}
	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}
}
