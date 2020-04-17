package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * 账户限额查询【02879】-前置02781
 * @author hk
 */
@XStreamAlias("BODY")
public class Out02781ResBodyBean {
	
	public String SVR_DATE;//核心日期
	public String SVR_JRNL_NO;//流水号
	
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
