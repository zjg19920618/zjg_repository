package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 行内汇划-前置02600
 * BCK_02600请求报文体Bean
 * @author hk
 */
@XStreamAlias("Detail")
public class BCK02600ReqBodyDetailBean {
	
	
	private String ENDOR_NAME;//背书人清单

	public String getENDOR_NAME() {
		return ENDOR_NAME;
	}

	public void setENDOR_NAME(String eNDOR_NAME) {
		ENDOR_NAME = eNDOR_NAME;
	}
	
	
	
	
}
