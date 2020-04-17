package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 核心节假日查询-前置07495
 * @author wang.sk
 *
 */
@XStreamAlias("Body")
public class BCK07495ResBodyBean {
	private String DATE_FLAG;//日期标志
	private String NEXT_SVR_DATE;//核心下一工作日

	public String getDATE_FLAG() {
		return DATE_FLAG;
	}

	public void setDATE_FLAG(String dATE_FLAG) {
		DATE_FLAG = dATE_FLAG;
	}

	public String getNEXT_SVR_DATE() {
		return NEXT_SVR_DATE;
	}

	public void setNEXT_SVR_DATE(String nEXT_SVR_DATE) {
		NEXT_SVR_DATE = nEXT_SVR_DATE;
	}
	
}
