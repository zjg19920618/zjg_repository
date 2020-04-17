package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 个人客户建立【前置交易码-03446】
 */
@XStreamAlias("Body")  
public class BCK03446ReqBodyBean {
	
	private String CUSTOM_MANAGER_NO;//客户经理编号
	private String CUST_NAME;//客户姓名
	private String CUST_SHORT;//简称
	private String EN_NAME_1;//英文姓氏
	private String EN_NAME_2;//英文名称
	private String CUST_SEX;//性别
	private String ID_TYPE;//证件类别
	private String ID_NO;//证件号码
	private String ISSUE_DATE;//签发日期
	private String DUE_DATE;//失效日期
	private String ISSUE_INST;//签发机关
	private String BIRTH_RES;//出生地点
	private String BIRTH_DATE;//出生日期
	private String COUNTRY;//国籍
	private String NATION;//民族
	private String EDU_GRADE;//教育程度
	private String MAR_STATUS;//婚姻状况
	private String HEA_STATUS;//健康状况
	private String CUST_TYPE;//客户类型
	private String BANK_SER_LEV;//银行服务评级
	private String BANK_STAFF;//是否银行员工
	private String BANK_PART;//是否银行股东
	private String BUS_STATUS;//职业状况
	private String J_C_ADD;//经常居住地
	private String DOMICILE_PLACE;//户口所在地
	private String TEL_NO;//手机号码
	public String getCUSTOM_MANAGER_NO() {
		return CUSTOM_MANAGER_NO;
	}
	public void setCUSTOM_MANAGER_NO(String cUSTOM_MANAGER_NO) {
		CUSTOM_MANAGER_NO = cUSTOM_MANAGER_NO;
	}
	public String getCUST_NAME() {
		return CUST_NAME;
	}
	public void setCUST_NAME(String cUST_NAME) {
		CUST_NAME = cUST_NAME;
	}
	public String getCUST_SHORT() {
		return CUST_SHORT;
	}
	public void setCUST_SHORT(String cUST_SHORT) {
		CUST_SHORT = cUST_SHORT;
	}
	public String getEN_NAME_1() {
		return EN_NAME_1;
	}
	public void setEN_NAME_1(String eN_NAME_1) {
		EN_NAME_1 = eN_NAME_1;
	}
	public String getEN_NAME_2() {
		return EN_NAME_2;
	}
	public void setEN_NAME_2(String eN_NAME_2) {
		EN_NAME_2 = eN_NAME_2;
	}
	public String getCUST_SEX() {
		return CUST_SEX;
	}
	public void setCUST_SEX(String cUST_SEX) {
		CUST_SEX = cUST_SEX;
	}
	public String getID_TYPE() {
		return ID_TYPE;
	}
	public void setID_TYPE(String iD_TYPE) {
		ID_TYPE = iD_TYPE;
	}
	public String getID_NO() {
		return ID_NO;
	}
	public void setID_NO(String iD_NO) {
		ID_NO = iD_NO;
	}
	public String getISSUE_DATE() {
		return ISSUE_DATE;
	}
	public void setISSUE_DATE(String iSSUE_DATE) {
		ISSUE_DATE = iSSUE_DATE;
	}
	public String getDUE_DATE() {
		return DUE_DATE;
	}
	public void setDUE_DATE(String dUE_DATE) {
		DUE_DATE = dUE_DATE;
	}
	public String getISSUE_INST() {
		return ISSUE_INST;
	}
	public void setISSUE_INST(String iSSUE_INST) {
		ISSUE_INST = iSSUE_INST;
	}
	public String getBIRTH_RES() {
		return BIRTH_RES;
	}
	public void setBIRTH_RES(String bIRTH_RES) {
		BIRTH_RES = bIRTH_RES;
	}
	public String getBIRTH_DATE() {
		return BIRTH_DATE;
	}
	public void setBIRTH_DATE(String bIRTH_DATE) {
		BIRTH_DATE = bIRTH_DATE;
	}
	public String getCOUNTRY() {
		return COUNTRY;
	}
	public void setCOUNTRY(String cOUNTRY) {
		COUNTRY = cOUNTRY;
	}
	public String getNATION() {
		return NATION;
	}
	public void setNATION(String nATION) {
		NATION = nATION;
	}
	public String getEDU_GRADE() {
		return EDU_GRADE;
	}
	public void setEDU_GRADE(String eDU_GRADE) {
		EDU_GRADE = eDU_GRADE;
	}
	public String getMAR_STATUS() {
		return MAR_STATUS;
	}
	public void setMAR_STATUS(String mAR_STATUS) {
		MAR_STATUS = mAR_STATUS;
	}
	public String getHEA_STATUS() {
		return HEA_STATUS;
	}
	public void setHEA_STATUS(String hEA_STATUS) {
		HEA_STATUS = hEA_STATUS;
	}
	public String getCUST_TYPE() {
		return CUST_TYPE;
	}
	public void setCUST_TYPE(String cUST_TYPE) {
		CUST_TYPE = cUST_TYPE;
	}
	public String getBANK_SER_LEV() {
		return BANK_SER_LEV;
	}
	public void setBANK_SER_LEV(String bANK_SER_LEV) {
		BANK_SER_LEV = bANK_SER_LEV;
	}
	public String getBANK_STAFF() {
		return BANK_STAFF;
	}
	public void setBANK_STAFF(String bANK_STAFF) {
		BANK_STAFF = bANK_STAFF;
	}
	public String getBANK_PART() {
		return BANK_PART;
	}
	public void setBANK_PART(String bANK_PART) {
		BANK_PART = bANK_PART;
	}
	public String getBUS_STATUS() {
		return BUS_STATUS;
	}
	public void setBUS_STATUS(String bUS_STATUS) {
		BUS_STATUS = bUS_STATUS;
	}
	public String getJ_C_ADD() {
		return J_C_ADD;
	}
	public void setJ_C_ADD(String j_C_ADD) {
		J_C_ADD = j_C_ADD;
	}
	public String getDOMICILE_PLACE() {
		return DOMICILE_PLACE;
	}
	public void setDOMICILE_PLACE(String dOMICILE_PLACE) {
		DOMICILE_PLACE = dOMICILE_PLACE;
	}
	public String getTEL_NO() {
		return TEL_NO;
	}
	public void setTEL_NO(String tEL_NO) {
		TEL_NO = tEL_NO;
	}

}
