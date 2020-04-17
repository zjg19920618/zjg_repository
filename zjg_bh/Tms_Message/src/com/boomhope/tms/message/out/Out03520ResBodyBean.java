package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 存单打印状态变更【02950】前置03520
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("BODY")
public class Out03520ResBodyBean {
	private String SVR_DATE; // 日期
	private String SVR_JRNL_NO; //流水号
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
