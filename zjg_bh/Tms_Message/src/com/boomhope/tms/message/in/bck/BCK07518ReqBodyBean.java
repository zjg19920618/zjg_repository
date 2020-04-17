package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *5.24存款关联账号查询【02947】-前置07518
 * @author hk
 *
 */
@XStreamAlias("Body")  
public class BCK07518ReqBodyBean {
	
	private String QRY_TYPE;//查询选择
	private String ACCT_NO;//账号
	private String SUB_ACCT_NO;//子账号
	private String CUST_NAME;//客户名称
	private String START_DATE;//起始日期
	private String END_DATE;//终止日期
	private String INPUT_INST_NO;//交易机构
	
	public String getQRY_TYPE() {
		return QRY_TYPE;
	}
	public void setQRY_TYPE(String qRY_TYPE) {
		QRY_TYPE = qRY_TYPE;
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
	public String getCUST_NAME() {
		return CUST_NAME;
	}
	public void setCUST_NAME(String cUST_NAME) {
		CUST_NAME = cUST_NAME;
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
	public String getINPUT_INST_NO() {
		return INPUT_INST_NO;
	}
	public void setINPUT_INST_NO(String iNPUT_INST_NO) {
		INPUT_INST_NO = iNPUT_INST_NO;
	}
	
	
	
}
