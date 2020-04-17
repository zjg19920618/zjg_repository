package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *客户评级查询03524
 *
 */
@XStreamAlias("BODY")  
public class Out03524ResBodyBean {
	
	private String CUST_LEVEL;//客户评级

	public String getCUST_LEVEL() {
		return CUST_LEVEL;
	}

	public void setCUST_LEVEL(String cUST_LEVEL) {
		CUST_LEVEL = cUST_LEVEL;
	}
	
	
	
	

}
