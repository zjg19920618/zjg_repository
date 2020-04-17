package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *银行卡换开查询原卡号【79114】-07523
 * @author zjg
 *
 */
@XStreamAlias("Body")  
public class BCK07523ResBodyBean {
	
	private String FILE_NAME;//文件名
	private String SVR_JRNL_NO;//流水号
	private String SVR_DATE;//日期

	public String getFILE_NAME() {
		return FILE_NAME;
	}

	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}

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
