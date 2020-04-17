package com.boomhope.tms.message.account.in;
 
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03511ResBodyBean 
* @Description 产品可开立额度信息查询-03511   
* @author zhang.m 
* @date 2016年12月5日 上午11:43:03  
*/
@XStreamAlias("Body")
public class BCK03511ResBodyBean {
	private String SVR_DATE;//核心日期
	private String SVR_JRNL_NO;//流水号
	private String BALA;//可用额度
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
	public String getBALA() {
		return BALA;
	}
	public void setBALA(String bALA) {
		BALA = bALA;
	}

	
}
