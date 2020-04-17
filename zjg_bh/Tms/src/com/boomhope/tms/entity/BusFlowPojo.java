package com.boomhope.tms.entity;

import java.io.Serializable;


public class BusFlowPojo extends BusFlow implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String total; //总额
	
	private int utnum;//次数
	
	private String kstime;//开始时间
	
	private String jstime;//结束时间
	public String getKstime() {
		return kstime;
	}

	public void setKstime(String kstime) {
		this.kstime = kstime;
	}

	public String getJstime() {
		return jstime;
	}

	public void setJstime(String jstime) {
		this.jstime = jstime;
	}

	public BusFlowPojo(){}
	
	public BusFlowPojo(BusFlow busFlow) {
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}

	public int getUtnum() {
		return utnum;
	}

	public void setUtnum(int utnum) {
		this.utnum = utnum;
	}
		
	

}
