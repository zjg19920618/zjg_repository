package com.boomhope.tms.message.account.in;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03510ResBodyBean 
* @Description  产品预计利息(24小时)-03510 
* @author zhang.m 
* @date 2016年12月5日 上午11:10:09  
*/
@XStreamAlias("Body")
public class BCK03510ResBodyBean {
	private String SVR_DATE;//核心日期
	private String SVR_JRNL_NO;//流水号
	private String FILE_NAME;//文件
	private String ADVN_INIT;//预付利息
	
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
	public String getADVN_INIT() {
		return ADVN_INIT;
	}
	public void setADVN_INIT(String aDVN_INIT) {
		ADVN_INIT = aDVN_INIT;
	}
	
}
