package com.boomhope.tms.message.account.in;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account02808ResBodyBean 
* @Description  个人存款开户--02808
* @author zh.m 
* @date 2016年12月4日 上午8:52:24  
*/
@XStreamAlias("Body")
public class BCK02808ResBodyBean {
	private String SVR_DATE;//核心日期
	private String SVR_JRNL_NO;//流水号
	private String ACCT_NO;//账 号
	private String CERT_NO;//凭证号
	private String RATE;//开户利率
	private String INTE_DATE;//起息日期
	private String END_DATE;//到期日
	private String DEP_TERM;//存 期
	private String FILE_NAME;//存折文件
	private String ITEM_NO;//科目
	private String OPEN_INST;//开户机构
	private String OPP_ACCT_NO;//对方账号
	private String OPP_ACCT_NAME;//对方户名
	private String OPP_ITEM_NO;//对方科目
	private String OPP_OPEN_INST;//对方开户机构
	private String CR_DB_FLAG;//借贷标志
	private String INTE;//预计利息
	private String AREA_FLOAT_RET;//区域浮动利率
	private String CHL_FLOAT_RET;//渠道浮动利率
	private String BIRTH_FLOAT_RET;//生日浮动利率
	private String TIME_FLOAT_RET;//时间段浮动利率
	private String COMB_FLOT_RET;//组合浮动利率
	private String FILE_NAME_RET;//文件
	
	public String getFILE_NAME_RET() {
		return FILE_NAME_RET;
	}
	public void setFILE_NAME_RET(String fILE_NAME_RET) {
		FILE_NAME_RET = fILE_NAME_RET;
	}
	public String getCOMB_FLOT_RET() {
		return COMB_FLOT_RET;
	}
	public void setCOMB_FLOT_RET(String cOMB_FLOT_RET) {
		COMB_FLOT_RET = cOMB_FLOT_RET;
	}
	public String getSVR_DATE() {
		return SVR_DATE;
	}
	public void setSVR_DATE(String sVR_DATE) {
		SVR_DATE = sVR_DATE;
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
	public String getCERT_NO() {
		return CERT_NO;
	}
	public void setCERT_NO(String cERT_NO) {
		CERT_NO = cERT_NO;
	}
	public String getRATE() {
		return RATE;
	}
	public void setRATE(String rATE) {
		RATE = rATE;
	}
	public String getINTE_DATE() {
		return INTE_DATE;
	}
	public void setINTE_DATE(String iNTE_DATE) {
		INTE_DATE = iNTE_DATE;
	}
	public String getEND_DATE() {
		return END_DATE;
	}
	public void setEND_DATE(String eND_DATE) {
		END_DATE = eND_DATE;
	}
	public String getDEP_TERM() {
		return DEP_TERM;
	}
	public void setDEP_TERM(String dEP_TERM) {
		DEP_TERM = dEP_TERM;
	}
	public String getFILE_NAME() {
		return FILE_NAME;
	}
	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}
	public String getITEM_NO() {
		return ITEM_NO;
	}
	public void setITEM_NO(String iTEM_NO) {
		ITEM_NO = iTEM_NO;
	}
	public String getOPEN_INST() {
		return OPEN_INST;
	}
	public void setOPEN_INST(String oPEN_INST) {
		OPEN_INST = oPEN_INST;
	}
	public String getOPP_ACCT_NO() {
		return OPP_ACCT_NO;
	}
	public void setOPP_ACCT_NO(String oPP_ACCT_NO) {
		OPP_ACCT_NO = oPP_ACCT_NO;
	}
	public String getOPP_ACCT_NAME() {
		return OPP_ACCT_NAME;
	}
	public void setOPP_ACCT_NAME(String oPP_ACCT_NAME) {
		OPP_ACCT_NAME = oPP_ACCT_NAME;
	}
	public String getOPP_ITEM_NO() {
		return OPP_ITEM_NO;
	}
	public void setOPP_ITEM_NO(String oPP_ITEM_NO) {
		OPP_ITEM_NO = oPP_ITEM_NO;
	}
	public String getOPP_OPEN_INST() {
		return OPP_OPEN_INST;
	}
	public void setOPP_OPEN_INST(String oPP_OPEN_INST) {
		OPP_OPEN_INST = oPP_OPEN_INST;
	}
	public String getCR_DB_FLAG() {
		return CR_DB_FLAG;
	}
	public void setCR_DB_FLAG(String cR_DB_FLAG) {
		CR_DB_FLAG = cR_DB_FLAG;
	}
	public String getINTE() {
		return INTE;
	}
	public void setINTE(String iNTE) {
		INTE = iNTE;
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
	
}
