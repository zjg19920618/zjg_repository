package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 存单打印【02949】前置03514
 * @author zjg
 * @date 2016年11月7日 上午10:38:14
 */
@XStreamAlias("Body")
public class BCK0016ResBodyBean {
	private String DATE; // 日期
	private String SVR_JRNL_NO; // 流水号
	private String OPEN_RATE; // 开户利率
	private String OPEN_INST; // 开户机构
	private String START_DATE; // 起息日期
	private String END_DATE; // 到期日期
	private String BALANCE; // 余额
	private String CERT_NO; // 凭证号
	private String DEP_TERM; // 存期	
	private String INTE; // 预计利息
	private String EXCH_FLAG; //	自动转存标志
	private String PRO_CODE; //	产品代码
	private String PROD_NAME; //	产品名称 
	private String ACCT_NAME; //	户名
	private String OPEN_DATE; //	开户日期
	private String ITEM_NO; //	科目号
	private String TYPE; //	类型
	private String PAY_COND; //	支付条件
	private String AREA_FLOAT_RET; //	区域浮动利率
	private String CHL_FLOAT_RET; //	渠道浮动利率
	private String BIRTH_FLOAT_RET; //	生日浮动利率
	private String TIME_FLOAT_RET; //	时间段浮动利率
	private String COMB_FLOT_RET; //	组合浮动利率
	private String FILE_NAME_RET;//浮动利率组合文件
	
	public String getDATE() {
		return DATE;
	}
	public void setDATE(String dATE) {
		DATE = dATE;
	}
	public String getSVR_JRNL_NO() {
		return SVR_JRNL_NO;
	}
	public void setSVR_JRNL_NO(String sVR_JRNL_NO) {
		SVR_JRNL_NO = sVR_JRNL_NO;
	}
	public String getOPEN_RATE() {
		return OPEN_RATE;
	}
	public void setOPEN_RATE(String oPEN_RATE) {
		OPEN_RATE = oPEN_RATE;
	}
	public String getOPEN_INST() {
		return OPEN_INST;
	}
	public void setOPEN_INST(String oPEN_INST) {
		OPEN_INST = oPEN_INST;
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
	public String getBALANCE() {
		return BALANCE;
	}
	public void setBALANCE(String bALANCE) {
		BALANCE = bALANCE;
	}
	public String getCERT_NO() {
		return CERT_NO;
	}
	public void setCERT_NO(String cERT_NO) {
		CERT_NO = cERT_NO;
	}
	public String getDEP_TERM() {
		return DEP_TERM;
	}
	public void setDEP_TERM(String dEP_TERM) {
		DEP_TERM = dEP_TERM;
	}
	public String getINTE() {
		return INTE;
	}
	public void setINTE(String iNTE) {
		INTE = iNTE;
	}
	public String getEXCH_FLAG() {
		return EXCH_FLAG;
	}
	public void setEXCH_FLAG(String eXCH_FLAG) {
		EXCH_FLAG = eXCH_FLAG;
	}
	public String getPRO_CODE() {
		return PRO_CODE;
	}
	public void setPRO_CODE(String pRO_CODE) {
		PRO_CODE = pRO_CODE;
	}
	public String getPROD_NAME() {
		return PROD_NAME;
	}
	public void setPROD_NAME(String pROD_NAME) {
		PROD_NAME = pROD_NAME;
	}
	public String getACCT_NAME() {
		return ACCT_NAME;
	}
	public void setACCT_NAME(String aCCT_NAME) {
		ACCT_NAME = aCCT_NAME;
	}
	public String getOPEN_DATE() {
		return OPEN_DATE;
	}
	public void setOPEN_DATE(String oPEN_DATE) {
		OPEN_DATE = oPEN_DATE;
	}
	public String getITEM_NO() {
		return ITEM_NO;
	}
	public void setITEM_NO(String iTEM_NO) {
		ITEM_NO = iTEM_NO;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public String getPAY_COND() {
		return PAY_COND;
	}
	public void setPAY_COND(String pAY_COND) {
		PAY_COND = pAY_COND;
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
	public String getFILE_NAME_RET() {
		return FILE_NAME_RET;
	}
	public void setFILE_NAME_RET(String fILE_NAME_RET) {
		FILE_NAME_RET = fILE_NAME_RET;
	}
	
}
