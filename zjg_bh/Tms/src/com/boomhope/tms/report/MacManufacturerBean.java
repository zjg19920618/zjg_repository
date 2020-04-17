package com.boomhope.tms.report;

/**
 * 厂商维护数据Bean
 * @author Administrator
 *
 */
public class MacManufacturerBean {

	private String manuCode; //厂商代码
	private String manuName; //厂商名称
	private String manuDesc;//厂商描述
	private String manuStatus;// 厂商状态
	private String conPerson;//联系人
	private String Phone; //手机号
	private String tel;//电话号码
	
	public String getManuCode() {
		return manuCode;
	}
	public void setManuCode(String manuCode) {
		this.manuCode = manuCode;
	}
	public String getManuName() {
		return manuName;
	}
	public void setManuName(String manuName) {
		this.manuName = manuName;
	}
	public String getManuDesc() {
		return manuDesc;
	}
	public void setManuDesc(String manuDesc) {
		this.manuDesc = manuDesc;
	}
	public String getManuStatus() {
		return manuStatus;
	}
	public void setManuStatus(String manuStatus) {
		this.manuStatus = manuStatus;
	}
	public String getConPerson() {
		return conPerson;
	}
	public void setConPerson(String conPerson) {
		this.conPerson = conPerson;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

}
