package com.boomhope.tms.pojo;

public class ReturnForBusFlowPojo {
	private String unitCode; // 机构编号
	private String unitName; // 机构名称
	private String projectName;// 业务名称
	private Long number;// 办理次数
	private String total;// 总金额
	private String kstime;
	private String jstime;
	
	public String getKsTime() {
		return kstime;
	}

	public void setKsTime(String ksTime) {
		this.kstime = ksTime;
	}

	public String getJsTime() {
		return jstime;
	}

	public void setJsTime(String jsTime) {
		this.jstime = jsTime;
	}

	public ReturnForBusFlowPojo() {
		super();
	}

	public ReturnForBusFlowPojo(Object[] objects) {
		super();
		this.unitCode = (String) objects[0];
		this.unitName = (String) objects[1];
		this.projectName = (String) objects[2];
		this.number = (Long) objects[3];
		this.total = (String) objects[4];
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
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

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

}
