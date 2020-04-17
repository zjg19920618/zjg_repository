package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * @Description: 卡系统 子账户销户【75104】前置03517----代理人信息
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("AGENT_INF")
public class Out03517ReqAgentInfBean {
	private String CUST_NAME;//客户姓名
	private String SEX;//性别
	private String ID_TYPE;//证件类别
	private String ID_NO;//证件号码
	private String ISSUE_DATE;//签发日期
	private String DUE_DATE;//到期日期
	private String ISSUE_INST;//签发机关
	private String NATION;//国籍
	private String OCCUPATION;//职业
	private String REGIS_PER_RES;//户口所在地
	private String J_C_ADD;//经常居住地
	private String TELEPHONE;//固定电话
	private String MOB_PHONE;//移动电话
	public String getCUST_NAME() {
		return CUST_NAME;
	}
	public void setCUST_NAME(String cUST_NAME) {
		CUST_NAME = cUST_NAME;
	}
	public String getSEX() {
		return SEX;
	}
	public void setSEX(String sEX) {
		SEX = sEX;
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
	public String getNATION() {
		return NATION;
	}
	public void setNATION(String nATION) {
		NATION = nATION;
	}
	public String getOCCUPATION() {
		return OCCUPATION;
	}
	public void setOCCUPATION(String oCCUPATION) {
		OCCUPATION = oCCUPATION;
	}
	public String getREGIS_PER_RES() {
		return REGIS_PER_RES;
	}
	public void setREGIS_PER_RES(String rEGIS_PER_RES) {
		REGIS_PER_RES = rEGIS_PER_RES;
	}
	public String getJ_C_ADD() {
		return J_C_ADD;
	}
	public void setJ_C_ADD(String j_C_ADD) {
		J_C_ADD = j_C_ADD;
	}
	public String getTELEPHONE() {
		return TELEPHONE;
	}
	public void setTELEPHONE(String tELEPHONE) {
		TELEPHONE = tELEPHONE;
	}
	public String getMOB_PHONE() {
		return MOB_PHONE;
	}
	public void setMOB_PHONE(String mOB_PHONE) {
		MOB_PHONE = mOB_PHONE;
	}
}
