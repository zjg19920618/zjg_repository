package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 单位校验对手信息
 * 02954响应报文体Bean
 * @author zjg
 *
 */
@XStreamAlias("BODY")
public class Out02954ResBodyBean {
	private String SVR_JRNL_NO;	//核心流水号
	private String SVR_DATE;	//核心日期

	
	public String getSVR_JRNL_NO() {
		return SVR_JRNL_NO;
	}

	public void setSVR_JRNL_NO(String sVR_JRNL_NO) {
		SVR_JRNL_NO = sVR_JRNL_NO;
	}

	public String getSVR_DATE() {
		return SVR_DATE;
	}

	public void setSVR_DATE(String sVR_DATE) {
		SVR_DATE = sVR_DATE;
	}
	
}
