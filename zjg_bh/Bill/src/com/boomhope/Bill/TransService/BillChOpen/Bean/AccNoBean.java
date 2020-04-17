package com.boomhope.Bill.TransService.BillChOpen.Bean;

public class AccNoBean {
  private String AccNo;
  private String Amt;
  private String CertNo;
  private String Enddata;
  private String procode;
  private String opendata;
  private String depmter;
  
	private String selectFlag;//选中标记
	private boolean flag = false; 
	
	
public String getProcode() {
		return procode;
	}
	public void setProcode(String procode) {
		this.procode = procode;
	}
	public String getOpendata() {
		return opendata;
	}
	public void setOpendata(String opendata) {
		this.opendata = opendata;
	}
	public String getDepmter() {
		return depmter;
	}
	public void setDepmter(String depmter) {
		this.depmter = depmter;
	}
public String getSelectFlag() {
		return selectFlag;
	}
	public void setSelectFlag(String selectFlag) {
		this.selectFlag = selectFlag;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
public String getAccNo() {
	return AccNo;
}
public void setAccNo(String accNo) {
	AccNo = accNo;
}
public String getAmt() {
	return Amt;
}
public void setAmt(String amt) {
	Amt = amt;
}
public String getCertNo() {
	return CertNo;
}
public void setCertNo(String certNo) {
	CertNo = certNo;
}
public String getEnddata() {
	return Enddata;
}
public void setEnddata(String enddata) {
	Enddata = enddata;
}
  
}
