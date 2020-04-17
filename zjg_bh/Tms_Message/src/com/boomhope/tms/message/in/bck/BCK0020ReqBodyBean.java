package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:产品利率信息查询--02864
 * Description:
 * @author mouchunyue
 * @date 2016年11月26日 下午3:49:06
 */
@XStreamAlias("Body")
public class BCK0020ReqBodyBean {
	private String PROD_CODE; //产品编号
	private String RATE_DATE;//核心当前日期
	private String FLOAT_FLAG;//非必输1-浮动，非1-不浮动
	private String CUST_NO;//客户号
	private String CHL_ID;//渠道模块标识
	private String CUST_LEVEL;//客户评级
	private String ACCT_NO;//账号
	private String SUB_ACCT_NO;//子账号
	public String getPROD_CODE() {
		return PROD_CODE;
	}
	public void setPROD_CODE(String pROD_CODE) {
		PROD_CODE = pROD_CODE;
	}
	public String getRATE_DATE() {
		return RATE_DATE;
	}
	public void setRATE_DATE(String rATE_DATE) {
		RATE_DATE = rATE_DATE;
	}
	public String getFLOAT_FLAG() {
		return FLOAT_FLAG;
	}
	public void setFLOAT_FLAG(String fLOAT_FLAG) {
		FLOAT_FLAG = fLOAT_FLAG;
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
	
}
