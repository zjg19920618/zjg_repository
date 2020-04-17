package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:行内汇划-前置02600
 * @author hk
 */
@XStreamAlias("DETAIL")
public class Out02600ReqBodyDetailBean {
	private String ENDOR_NAME;//背书人清单

	public String getENDOR_NAME() {
		return ENDOR_NAME;
	}

	public void setENDOR_NAME(String eNDOR_NAME) {
		ENDOR_NAME = eNDOR_NAME;
	}
	
	
	
}
