package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *根据客户号查询所有卡号信息【90001】-前置07520
 * @author hk
 *
 */
@XStreamAlias("Body")  
public class BCK07520ReqBodyBean {
	
	private String CUST_NO;//客户号

	public String getCUST_NO() {
		return CUST_NO;
	}

	public void setCUST_NO(String cUST_NO) {
		CUST_NO = cUST_NO;
	}
	
	
	
	
}
