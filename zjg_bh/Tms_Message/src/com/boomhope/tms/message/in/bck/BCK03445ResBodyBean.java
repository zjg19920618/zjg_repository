package com.boomhope.tms.message.in.bck;



import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 证件信息查询响应报文体Bean
 * @author wang.sk
 *
 */
@XStreamAlias("Body")
public class BCK03445ResBodyBean {
	private String SVR_DATE;//核心日期
	private String SVR_JRNL_NO;//流水号
	private String FILE_NAME;//证件信息
	private String SVR_RET_CODE;	// 
	private String SVR_RET_DESC;	// 
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
	public String getFILE_NAME() {
		return FILE_NAME;
	}
	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}
	public String getSVR_RET_CODE() {
		return SVR_RET_CODE;
	}
	public void setSVR_RET_CODE(String sVR_RET_CODE) {
		SVR_RET_CODE = sVR_RET_CODE;
	}
	public String getSVR_RET_DESC() {
		return SVR_RET_DESC;
	}
	public void setSVR_RET_DESC(String sVR_RET_DESC) {
		SVR_RET_DESC = sVR_RET_DESC;
	}
	
}
