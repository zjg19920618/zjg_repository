package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *个人客户查询( 01020)-前置07519
 * @author hk
 *
 */
@XStreamAlias("BODY")  
public class Out07519ReqBodyBean {
	
	private String SCH_TYPE;//查询选择
	private String CUST_NO;//客户号
	private String CUST_NAME;//客户名称
	private String ID_TYPE;//证件类别
	private String ID_NO;//证件号码
	private String CUST_PASS_WORD;//客户密码
	private String INFO_TYPE;//信息类型
	public String getSCH_TYPE() {
		return SCH_TYPE;
	}
	public void setSCH_TYPE(String sCH_TYPE) {
		SCH_TYPE = sCH_TYPE;
	}
	public String getCUST_NO() {
		return CUST_NO;
	}
	public void setCUST_NO(String cUST_NO) {
		CUST_NO = cUST_NO;
	}
	public String getCUST_NAME() {
		return CUST_NAME;
	}
	public void setCUST_NAME(String cUST_NAME) {
		CUST_NAME = cUST_NAME;
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
	public String getCUST_PASS_WORD() {
		return CUST_PASS_WORD;
	}
	public void setCUST_PASS_WORD(String cUST_PASS_WORD) {
		CUST_PASS_WORD = cUST_PASS_WORD;
	}
	public String getINFO_TYPE() {
		return INFO_TYPE;
	}
	public void setINFO_TYPE(String iNFO_TYPE) {
		INFO_TYPE = iNFO_TYPE;
	}

	
}
