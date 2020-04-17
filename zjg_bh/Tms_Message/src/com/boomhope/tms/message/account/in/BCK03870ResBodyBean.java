package com.boomhope.tms.message.account.in;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03870ResBodyBean 
* @Description   "积享存"个人存款开户【75100】前置【03870】
* @author zhang.m 
* @date 2016年12月5日 下午6:20:58  
*/
@XStreamAlias("Body")
public class BCK03870ResBodyBean {
	private String SVR_DATE;//核心日期
	private String CARD_DATE;//卡日期
	private String CARD_JRNL_NO;//卡流水号
	private String SVR_JRNL_NO;//核心流水号
	private String ACCT_NO;//账号
	private String TRN_RATE;//开户利率
	private String START_DATE;//起息日期
	private String END_DATE;//到期日
	private String SUB_ACCT_NO2;//子账户
	private String ITEM_NO;//科目
	private String DEP_TERM;//存期
	private String OPEN_INST;//开户机构
	private String CR_DB_FLAG;//借贷标志
	private String AREA_FLOAT_RET;//区域浮动利率
	private String CHL_FLOAT_RET;//渠道浮动利率
	private String BIRTH_FLOAT_RET;//生日浮动利率
	private String TIME_FLOAT_RET;//时间段浮动利率
	private String COMB_FLOT_RET;//组合浮动利率
	private String  FILE_NAME_RET;//浮动利率组合文件
	
	public String getFILE_NAME_RET() {
		return FILE_NAME_RET;
	}
	public void setFILE_NAME_RET(String fILE_NAME_RET) {
		FILE_NAME_RET = fILE_NAME_RET;
	}
	public String getSVR_DATE() {
		return SVR_DATE;
	}
	public void setSVR_DATE(String sVR_DATE) {
		SVR_DATE = sVR_DATE;
	}
	public String getCARD_DATE() {
		return CARD_DATE;
	}
	public void setCARD_DATE(String cARD_DATE) {
		CARD_DATE = cARD_DATE;
	}
	public String getCARD_JRNL_NO() {
		return CARD_JRNL_NO;
	}
	public void setCARD_JRNL_NO(String cARD_JRNL_NO) {
		CARD_JRNL_NO = cARD_JRNL_NO;
	}
	public String getSVR_JRNL_NO() {
		return SVR_JRNL_NO;
	}
	public void setSVR_JRNL_NO(String sVR_JRNL_NO) {
		SVR_JRNL_NO = sVR_JRNL_NO;
	}
	public String getACCT_NO() {
		return ACCT_NO;
	}
	public void setACCT_NO(String aCCT_NO) {
		ACCT_NO = aCCT_NO;
	}
	public String getTRN_RATE() {
		return TRN_RATE;
	}
	public void setTRN_RATE(String tRN_RATE) {
		TRN_RATE = tRN_RATE;
	}
	public String getSTART_DATE() {
		return START_DATE;
	}
	public void setSTART_DATE(String sTART_DATE) {
		START_DATE = sTART_DATE;
	}
	public String getEND_DATE() {
		return END_DATE;
	}
	public void setEND_DATE(String eND_DATE) {
		END_DATE = eND_DATE;
	}
	public String getSUB_ACCT_NO2() {
		return SUB_ACCT_NO2;
	}
	public void setSUB_ACCT_NO2(String sUB_ACCT_NO2) {
		SUB_ACCT_NO2 = sUB_ACCT_NO2;
	}
	public String getITEM_NO() {
		return ITEM_NO;
	}
	public void setITEM_NO(String iTEM_NO) {
		ITEM_NO = iTEM_NO;
	}
	public String getDEP_TERM() {
		return DEP_TERM;
	}
	public void setDEP_TERM(String dEP_TERM) {
		DEP_TERM = dEP_TERM;
	}
	public String getOPEN_INST() {
		return OPEN_INST;
	}
	public void setOPEN_INST(String oPEN_INST) {
		OPEN_INST = oPEN_INST;
	}
	public String getCR_DB_FLAG() {
		return CR_DB_FLAG;
	}
	public void setCR_DB_FLAG(String cR_DB_FLAG) {
		CR_DB_FLAG = cR_DB_FLAG;
	}
	public String getAREA_FLOAT_RET() {
		return AREA_FLOAT_RET;
	}
	public void setAREA_FLOAT_RET(String aREA_FLOAT_RET) {
		AREA_FLOAT_RET = aREA_FLOAT_RET;
	}
	public String getCHL_FLOAT_RET() {
		return CHL_FLOAT_RET;
	}
	public void setCHL_FLOAT_RET(String cHL_FLOAT_RET) {
		CHL_FLOAT_RET = cHL_FLOAT_RET;
	}
	public String getBIRTH_FLOAT_RET() {
		return BIRTH_FLOAT_RET;
	}
	public void setBIRTH_FLOAT_RET(String bIRTH_FLOAT_RET) {
		BIRTH_FLOAT_RET = bIRTH_FLOAT_RET;
	}
	public String getTIME_FLOAT_RET() {
		return TIME_FLOAT_RET;
	}
	public void setTIME_FLOAT_RET(String tIME_FLOAT_RET) {
		TIME_FLOAT_RET = tIME_FLOAT_RET;
	}
	public String getCOMB_FLOT_RET() {
		return COMB_FLOT_RET;
	}
	public void setCOMB_FLOT_RET(String cOMB_FLOT_RET) {
		COMB_FLOT_RET = cOMB_FLOT_RET;
	}

}
