package com.boomhope.tms.message.account.in;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account07505ReqBodyBean 
* @Description   唐豆兑付—07505
* @author zh.m 
* @date 2016年12月4日 上午9:16:54  
*/
@XStreamAlias("Body")
public class BCK07505ReqBodyBean {
	private BCK07505AcctInfo ACCT_INFO;//账户信息
	private String PRODUCT_CODE;//产品代码
	private String DEP_TERM;//存期
	private String ACCEPT_DATE;//兑付日
	private String TOTAL_BAL;//账户总余额
	private String TERM_CODE;//唐豆期次代码
	private String COUNT;//唐豆数量
	private String EXCHANGE_MODE;//唐豆兑换方式
	private String EXCHANGE_PROP;//唐豆兑现比例
	private String EXCHANGE_AMT;//兑现金额
	private String CUSTOM_MANAGER_NO;//客户经理号
	private String CUSTOM_MANAGER_NAME;//客户经理名称
	private String OPPO_ACCT_NO;//对方账号
	private String ACT_CHNL;//活动渠道
	private String DEAL_TYPE;//历史唐豆处理标志
	public BCK07505AcctInfo getACCT_INFO() {
		return ACCT_INFO;
	}
	public void setACCT_INFO(BCK07505AcctInfo aCCT_INFO) {
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
	
}
