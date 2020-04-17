package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *客户评级查询03524
 * @author 
 */
@XStreamAlias("Body")  
public class BCK03524ReqBodyBean {
	
	private String ACCT_NO;//账号
	private String CUST_NO;//客户号
	
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
	

}
