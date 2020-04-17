package com.boomhope.tms.message.account.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03512ResBodyBean 
* @Description   如意存明细查询【55020】--前置03512
* @author zhang.m 
* @date 2016年12月5日 下午12:03:09  
*/
@XStreamAlias("BODY")
public class Account03512ResBodyBean {
	private String SVR_DATE;//核心日期
	private String SVR_JRNL_NO;//流水号
	private String FILE_NAME;//文件
	
	public String getFILE_NAME() {
		return FILE_NAME;
	}
	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}
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
