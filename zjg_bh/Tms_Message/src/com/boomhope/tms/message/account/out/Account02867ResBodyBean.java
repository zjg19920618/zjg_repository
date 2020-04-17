package com.boomhope.tms.message.account.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:产品利率信息查询--02867
 * Description:
 * @author mouchunyue
 * @date 2016年12月3日 下午2:41:52
 */
@XStreamAlias("BODY")
public class Account02867ResBodyBean {

	private String SVR_DATE;
	private String SVR_JRNL_NO;
	private String PRODUCT_FI_NM;
	private String AMT_FL_NM;
	private String FILE_NAME_RET;
	
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
	public String getPRODUCT_FI_NM() {
		return PRODUCT_FI_NM;
	}
	public void setPRODUCT_FI_NM(String pRODUCT_FI_NM) {
		PRODUCT_FI_NM = pRODUCT_FI_NM;
	}
	public String getAMT_FL_NM() {
		return AMT_FL_NM;
	}
	public void setAMT_FL_NM(String aMT_FL_NM) {
		AMT_FL_NM = aMT_FL_NM;
	}
	public String getFILE_NAME_RET() {
		return FILE_NAME_RET;
	}
	public void setFILE_NAME_RET(String fILE_NAME_RET) {
		FILE_NAME_RET = fILE_NAME_RET;
	}
	
}
