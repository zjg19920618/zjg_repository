package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 机构关系查询交易【20102】--前置01569
 * @author hk
 *
 */
@XStreamAlias("BODY")  
public class Out01569ResBodyBean {
	
	private String SVR_DATE;//核心日期
	private String SVR_JRNL_NO;//流水号
	private String TC_TD_FLAG;//通存通兑标志
	
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
	public String getTC_TD_FLAG() {
		return TC_TD_FLAG;
	}
	public void setTC_TD_FLAG(String tC_TD_FLAG) {
		TC_TD_FLAG = tC_TD_FLAG;
	}
	
}
