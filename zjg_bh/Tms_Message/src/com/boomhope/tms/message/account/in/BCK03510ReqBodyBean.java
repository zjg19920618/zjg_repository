package com.boomhope.tms.message.account.in;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03510ReqBodyBean 
* @Description  产品预计利息(24小时)-03510   
* @author zhang.m 
* @date 2016年12月5日 上午10:57:59  
*/
@XStreamAlias("Body")
public class BCK03510ReqBodyBean {
	private String ACCT_NO;//账号
	private String SUB_ACCT_NO;//子账号
	private String PROD_CODE;//产品代码
	private String AMT;//金额
	private String OPEN_DATE;//开户日
	private String DRAW_AMT_DATE;//取款期
	private String OPEN_CHL;//开户渠道
	private String CUST_NO;//客户号
	private String CHL_ID;//渠道模块标识
	private String CUST_LEVEL;//客户评级
	
	
	public String getCUST_LEVEL() {
		return CUST_LEVEL;
	}
	public void setCUST_LEVEL(String cUST_LEVEL) {
		CUST_LEVEL = cUST_LEVEL;
	}
	public String getACCT_NO() {
		return ACCT_NO;
	}
	public void setACCT_NO(String aCCT_NO) {
		ACCT_NO = aCCT_NO;
	}
	public String getSUB_ACCT_NO() {
		return SUB_ACCT_NO;
	}
	public void setSUB_ACCT_NO(String sUB_ACCT_NO) {
		SUB_ACCT_NO = sUB_ACCT_NO;
	}
	public String getPROD_CODE() {
		return PROD_CODE;
	}
	public void setPROD_CODE(String pROD_CODE) {
		PROD_CODE = pROD_CODE;
	}
	public String getAMT() {
		return AMT;
	}
	public void setAMT(String aMT) {
		AMT = aMT;
	}
	public String getOPEN_DATE() {
		return OPEN_DATE;
	}
	public void setOPEN_DATE(String oPEN_DATE) {
		OPEN_DATE = oPEN_DATE;
	}
	public String getDRAW_AMT_DATE() {
		return DRAW_AMT_DATE;
	}
	public void setDRAW_AMT_DATE(String dRAW_AMT_DATE) {
		DRAW_AMT_DATE = dRAW_AMT_DATE;
	}
	public String getOPEN_CHL() {
		return OPEN_CHL;
	}
	public void setOPEN_CHL(String oPEN_CHL) {
		OPEN_CHL = oPEN_CHL;
	}
	public String getCUST_NO() {
		return CUST_NO;
	}
	public void setCUST_NO(String cUST_NO) {
		CUST_NO = cUST_NO;
	}
	public String getCHL_ID() {
		return CHL_ID;
	}
	public void setCHL_ID(String cHL_ID) {
		CHL_ID = cHL_ID;
	}
}
