package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 唐豆派发—02206前置【07663】
 */
@XStreamAlias("BODY")  
public class Out07663ReqBodyBean {
	
	
	private Out07663ReqAgentInfBean ACCT_INFO;//账号
	private String PRODUCT_CODE;
	private String DEP_TERM;
	private String ACCEPT_DATE;
	private String TOTAL_BAL;
	private String TERM_CODE;
	private String COUNT;
	private String EXCHANGE_MODE;
	private String EXCHANGE_PROP;
	private String EXCHANGE_AMT;
	private String CUSTOM_MANAGER_NO;
	private String CUSTOM_MANAGER_NAME;
	private String OPPO_ACCT_NO;
	private String ACT_CHNL;
	private String DEAL_TYPE;
	private String AMT_CHL_FLAG;
	public Out07663ReqAgentInfBean getACCT_INFO() {
		return ACCT_INFO;
	}
	public void setACCT_INFO(Out07663ReqAgentInfBean aCCT_INFO) {
		ACCT_INFO = aCCT_INFO;
	}
	public String getPRODUCT_CODE() {
		return PRODUCT_CODE;
	}
	public void setPRODUCT_CODE(String pRODUCT_CODE) {
		PRODUCT_CODE = pRODUCT_CODE;
	}
	public String getDEP_TERM() {
		return DEP_TERM;
	}
	public void setDEP_TERM(String dEP_TERM) {
		DEP_TERM = dEP_TERM;
	}
	public String getACCEPT_DATE() {
		return ACCEPT_DATE;
	}
	public void setACCEPT_DATE(String aCCEPT_DATE) {
		ACCEPT_DATE = aCCEPT_DATE;
	}
	public String getTOTAL_BAL() {
		return TOTAL_BAL;
	}
	public void setTOTAL_BAL(String tOTAL_BAL) {
		TOTAL_BAL = tOTAL_BAL;
	}
	public String getTERM_CODE() {
		return TERM_CODE;
	}
	public void setTERM_CODE(String tERM_CODE) {
		TERM_CODE = tERM_CODE;
	}
	public String getCOUNT() {
		return COUNT;
	}
	public void setCOUNT(String cOUNT) {
		COUNT = cOUNT;
	}
	public String getEXCHANGE_MODE() {
		return EXCHANGE_MODE;
	}
	public void setEXCHANGE_MODE(String eXCHANGE_MODE) {
		EXCHANGE_MODE = eXCHANGE_MODE;
	}
	public String getEXCHANGE_PROP() {
		return EXCHANGE_PROP;
	}
	public void setEXCHANGE_PROP(String eXCHANGE_PROP) {
		EXCHANGE_PROP = eXCHANGE_PROP;
	}
	public String getEXCHANGE_AMT() {
		return EXCHANGE_AMT;
	}
	public void setEXCHANGE_AMT(String eXCHANGE_AMT) {
		EXCHANGE_AMT = eXCHANGE_AMT;
	}
	public String getCUSTOM_MANAGER_NO() {
		return CUSTOM_MANAGER_NO;
	}
	public void setCUSTOM_MANAGER_NO(String cUSTOM_MANAGER_NO) {
		CUSTOM_MANAGER_NO = cUSTOM_MANAGER_NO;
	}
	public String getCUSTOM_MANAGER_NAME() {
		return CUSTOM_MANAGER_NAME;
	}
	public void setCUSTOM_MANAGER_NAME(String cUSTOM_MANAGER_NAME) {
		CUSTOM_MANAGER_NAME = cUSTOM_MANAGER_NAME;
	}
	public String getOPPO_ACCT_NO() {
		return OPPO_ACCT_NO;
	}
	public void setOPPO_ACCT_NO(String oPPO_ACCT_NO) {
		OPPO_ACCT_NO = oPPO_ACCT_NO;
	}
	public String getACT_CHNL() {
		return ACT_CHNL;
	}
	public void setACT_CHNL(String aCT_CHNL) {
		ACT_CHNL = aCT_CHNL;
	}
	public String getDEAL_TYPE() {
		return DEAL_TYPE;
	}
	public void setDEAL_TYPE(String dEAL_TYPE) {
		DEAL_TYPE = dEAL_TYPE;
	}
	public String getAMT_CHL_FLAG() {
		return AMT_CHL_FLAG;
	}
	public void setAMT_CHL_FLAG(String aMT_CHL_FLAG) {
		AMT_CHL_FLAG = aMT_CHL_FLAG;
	}

}
