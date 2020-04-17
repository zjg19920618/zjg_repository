package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 小额普通贷记往帐录入（通用）02224
 * BCK_02224响应报文体Bean
 * @author wang.sk
 *
 */
@XStreamAlias("BODY")
public class Out02224ResBodyBean {
	private String SVR_JRNL_NO;	//流水号
	private String TRAN_NO;		//支付交易序号
	private String SVR_DATE;	//核心日期
	private String TRAN_TIME;//交易时间
	
	public String getSVR_JRNL_NO() {
		return SVR_JRNL_NO;
	}
	public void setSVR_JRNL_NO(String sVR_JRNL_NO) {
		SVR_JRNL_NO = sVR_JRNL_NO;
	}
	public String getTRAN_NO() {
		return TRAN_NO;
	}
	public void setTRAN_NO(String tRAN_NO) {
		TRAN_NO = tRAN_NO;
	}
	public String getSVR_DATE() {
		return SVR_DATE;
	}
	public void setSVR_DATE(String sVR_DATE) {
		SVR_DATE = sVR_DATE;
	}
	public String getTRAN_TIME() {
		return TRAN_TIME;
	}
	public void setTRAN_TIME(String tRAN_TIME) {
		TRAN_TIME = tRAN_TIME;
	}
	
	
}
