package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *子账号开户流水查询【20110】-07522
 * @author hk
 *
 */
@XStreamAlias("Body")  
public class BCK07522ResBodyBean {
	
	private String ORIG_SVR_JRNL_NO;//原流水号
	private String SVR_JRNL_NO;//流水号
	
	
	public String getORIG_SVR_JRNL_NO() {
		return ORIG_SVR_JRNL_NO;
	}
	public void setORIG_SVR_JRNL_NO(String oRIG_SVR_JRNL_NO) {
		ORIG_SVR_JRNL_NO = oRIG_SVR_JRNL_NO;
	}
	public String getSVR_JRNL_NO() {
		return SVR_JRNL_NO;
	}
	public void setSVR_JRNL_NO(String sVR_JRNL_NO) {
		SVR_JRNL_NO = sVR_JRNL_NO;
	}
	
	
	
}
