package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *推荐人奖励领取【17030】前置-03532
 * @author ly
 *
 */
@XStreamAlias("BODY")  
public class Out03532ResBodyBean {
	
	private String SVR_DATE;//核心日期
	private String SVR_JRNL_NO;//核心流水号
	
	public String getSVR_DATE() {
		return SVR_DATE;
	}
	public void setSVR_DATE(String sVR_DATE) {
		SVR_DATE = sVR_DATE;
	}
	public String getSVR_JRNL_NO() {
		return SVR_JRNL_NO;
	}
	public void setSVR_JRNL_NO(String sVR_JRNL_NO) {
		SVR_JRNL_NO = sVR_JRNL_NO;
	}

}
