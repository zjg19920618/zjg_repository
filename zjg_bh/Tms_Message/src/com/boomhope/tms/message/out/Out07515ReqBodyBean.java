package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *前置系统参数查询-前置QRY00
 * @author zjg
 *
 */
@XStreamAlias("BODY")  
public class Out07515ReqBodyBean {
	
	private String ACCT_NO;//账号
	private String CUST_NO;//客户号
	private String ID_TYPE;//证件类别
	private String ID_NO;//证件号码
	
	public String getACCT_NO() {
		return ACCT_NO;
	}
	public void setACCT_NO(String aCCT_NO) {
		ACCT_NO = aCCT_NO;
	}
	public String getCUST_NO() {
		return CUST_NO;
	}
	public void setCUST_NO(String cUST_NO) {
		CUST_NO = cUST_NO;
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

	
}
