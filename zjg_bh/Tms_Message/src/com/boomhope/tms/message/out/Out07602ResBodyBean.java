package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:个人IC卡验证-前置07602
 * @author hk
 */
@XStreamAlias("BODY")  
public class Out07602ResBodyBean {
	
	private String SVR_DATE;//核心日期
	private String CARD_JRNL_NO;//卡流水号
	private String ARPC;//ARPC
	private String PROD_NO;//产品号
	private String ISSUSER;//发卡行
	public String getSVR_DATE() {
		return SVR_DATE;
	}
	public void setSVR_DATE(String sVR_DATE) {
		SVR_DATE = sVR_DATE;
	}
	public String getCARD_JRNL_NO() {
		return CARD_JRNL_NO;
	}
	public void setCARD_JRNL_NO(String cARD_JRNL_NO) {
		CARD_JRNL_NO = cARD_JRNL_NO;
	}
	public String getARPC() {
		return ARPC;
	}
	public void setARPC(String aRPC) {
		ARPC = aRPC;
	}
	public String getPROD_NO() {
		return PROD_NO;
	}
	public void setPROD_NO(String pROD_NO) {
		PROD_NO = pROD_NO;
	}
	public String getISSUSER() {
		return ISSUSER;
	}
	public void setISSUSER(String iSSUSER) {
		ISSUSER = iSSUSER;
	}
		
}
