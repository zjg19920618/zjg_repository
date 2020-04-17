package com.boomhope.tms.pojo;

public class ReturnAccountPojo {

	private String unitName; // 机构名称
	private String productName;// 业务名称
	private Long number;// 办理次数
	private String total;// 总金额
	private String productCode;// 业务标识
	private String kstime;
	private String jstime;
	
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

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

	public ReturnAccountPojo() {
		super();
	}

	public ReturnAccountPojo(Object[] objects) {
		super();
		this.unitName = (String) objects[0];
		this.productName = (String) objects[1];
		this.number = (Long) objects[2];
		this.total = (String) objects[3];
		this.productCode = (String) objects[4];
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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
