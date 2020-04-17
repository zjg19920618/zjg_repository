package com.boomhope.Bill.Util;
/**
 * 银行信息Bean
 * @author hao
 *
 */
public class BankInfo {
	private String bankId;//行号
	private String hbCode;//行别代码
	private String zjBankCode;//所属直接参与者行号
	private String treeCode;//所在节点代码
	private String preCanyu;//本行上级参与者
	private String peopleBankCode;//所属人行代码
	private String cityCode;//所在城市代码
	private String name;//参与者全称
	private String namej;//参与者简称
	private String address;//地址
	private String	email;//邮编
	private String	phone;//电话
	private String EMAIL;//EMAIL
	private String	chlid;//有效标识
	private String jgType;//参与机构类别
	private String belongPeople;//所属法人
	private String cjBankCode;//承接行行号
	private String sysBusinessChlid;//加入业务系统标识
	private String times;//变更期数
	private String StartDate;//生效日期
	private String endDate;//失效日期
	private String bgTyoe;//变更类型
	@Override
	public String toString() {
		return "BankInfo [bankId=" + bankId + ", hbCode=" + hbCode
				+ ", zjBankCode=" + zjBankCode + ", treeCode=" + treeCode
				+ ", preCanyu=" + preCanyu + ", peopleBankCode="
				+ peopleBankCode + ", cityCode=" + cityCode + ", name=" + name
				+ ", namej=" + namej + ", address=" + address + ", email="
				+ email + ", phone=" + phone + ", chlid=" + chlid + ", jgType="
				+ jgType + ", belongPeople=" + belongPeople + ", cjBankCode="
				+ cjBankCode + ", sysBusinessChlid=" + sysBusinessChlid
				+ ", times=" + times + ", StartDate=" + StartDate
				+ ", endDate=" + endDate + ", bgTyoe=" + bgTyoe + "]";
	}
	public String getEMAIL() {
		return EMAIL;
	}
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getHbCode() {
		return hbCode;
	}
	public void setHbCode(String hbCode) {
		this.hbCode = hbCode;
	}
	public String getZjBankCode() {
		return zjBankCode;
	}
	public void setZjBankCode(String zjBankCode) {
		this.zjBankCode = zjBankCode;
	}
	public String getTreeCode() {
		return treeCode;
	}
	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}
	public String getPreCanyu() {
		return preCanyu;
	}
	public void setPreCanyu(String preCanyu) {
		this.preCanyu = preCanyu;
	}
	public String getPeopleBankCode() {
		return peopleBankCode;
	}
	public void setPeopleBankCode(String peopleBankCode) {
		this.peopleBankCode = peopleBankCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNamej() {
		return namej;
	}
	public void setNamej(String namej) {
		this.namej = namej;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getChlid() {
		return chlid;
	}
	public void setChlid(String chlid) {
		this.chlid = chlid;
	}
	public String getJgType() {
		return jgType;
	}
	public void setJgType(String jgType) {
		this.jgType = jgType;
	}
	public String getBelongPeople() {
		return belongPeople;
	}
	public void setBelongPeople(String belongPeople) {
		this.belongPeople = belongPeople;
	}
	public String getCjBankCode() {
		return cjBankCode;
	}
	public void setCjBankCode(String cjBankCode) {
		this.cjBankCode = cjBankCode;
	}
	public String getSysBusinessChlid() {
		return sysBusinessChlid;
	}
	public void setSysBusinessChlid(String sysBusinessChlid) {
		this.sysBusinessChlid = sysBusinessChlid;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getStartDate() {
		return StartDate;
	}
	public void setStartDate(String startDate) {
		StartDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getBgTyoe() {
		return bgTyoe;
	}
	public void setBgTyoe(String bgTyoe) {
		this.bgTyoe = bgTyoe;
	}
}
