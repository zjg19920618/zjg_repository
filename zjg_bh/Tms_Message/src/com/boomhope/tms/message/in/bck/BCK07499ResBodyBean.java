package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 唐宝x号转入【79100】-前置07499
 * @author hk
 *
 */
@XStreamAlias("Body")  
public class BCK07499ResBodyBean {
	
	private String SVR_DATE;//核心日期
	private String SVR_JRNL_NO;//流水号
	
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
