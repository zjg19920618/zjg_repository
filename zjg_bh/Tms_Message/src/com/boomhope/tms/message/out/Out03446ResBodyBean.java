package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/***
 * 个人客户建立【前置交易码-03446】
 */
@XStreamAlias("BODY")
public class Out03446ResBodyBean {

	private String SVR_DATE;//核心日期
	private String SVR_JRNL_NO;//核心流水号
	private String CUST_NO;//客户号
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
	public String getCUST_NO() {
		return CUST_NO;
	}
	public void setCUST_NO(String cUST_NO) {
		CUST_NO = cUST_NO;
	}
	
}
