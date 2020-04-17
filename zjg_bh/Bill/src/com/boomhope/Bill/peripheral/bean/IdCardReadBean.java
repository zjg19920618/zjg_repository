package com.boomhope.Bill.peripheral.bean;
/**
 * 
 * Title:身份证读取
 * Description:
 * @author mouchunyue
 * @date 2016年9月20日 上午11:12:19
 */
public class IdCardReadBean {
	//0|序列号|1|姓名&#性别&#民族&#生日&#地址&#身份证号&#发证机关&#有效期开始&#有效期结束|照片绝对路径|正面扫描图绝对路径|反面扫描图绝对路径|
	private String status;// 状态码
	private String code;//序列号
	private String num; //1
	private String name;//姓名
	private String sex;//性别
	private String nationality;//民族
	private String birthday;//生日
	private String address;//地址
	private String idCode;//身份证号
	private String issuingUnit;//发证机关
	private String startDate;//有效期开始
	private String endDate;//有效期结束
	private String photoPath;//照片绝对路径
	private String frontFace;//正面扫描图绝对路径
	private String backFace;//反面扫描图绝对路径
	private String statusDesc;//状态描述（失败时才有值）
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIdCode() {
		return idCode;
	}
	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
	public String getIssuingUnit() {
		return issuingUnit;
	}
	public void setIssuingUnit(String issuingUnit) {
		this.issuingUnit = issuingUnit;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	public String getFrontFace() {
		return frontFace;
	}
	public void setFrontFace(String frontFace) {
		this.frontFace = frontFace;
	}
	public String getBackFace() {
		return backFace;
	}
	public void setBackFace(String backFace) {
		this.backFace = backFace;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
}
