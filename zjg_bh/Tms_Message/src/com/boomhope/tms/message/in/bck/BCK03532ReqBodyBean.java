package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *推荐人奖励领取【17030】前置-03532
 * @author ly
 *
 */
@XStreamAlias("Body")  
public class BCK03532ReqBodyBean {
	
	private String ACCESS_CODE;//领取码
	private String CUST_NO;//客户号
	public String getACCESS_CODE() {
		return ACCESS_CODE;
	}
	public void setACCESS_CODE(String aCCESS_CODE) {
		ACCESS_CODE = aCCESS_CODE;
	}
	public String getCUST_NO() {
		return CUST_NO;
	}
	public void setCUST_NO(String cUST_NO) {
		CUST_NO = cUST_NO;
	}
	
	

	
}
