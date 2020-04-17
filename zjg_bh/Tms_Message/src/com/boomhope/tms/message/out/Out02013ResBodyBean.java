package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 大额普通汇兑往帐发送交易接口（通用）02013
 * @author hk
 *
 */
@XStreamAlias("BODY")  
public class Out02013ResBodyBean {
	
	private String SVR_JRNL_NO;//核心流水号
	private String TRAN_NO;//支付交易序号 
	private String PAY_DATE;//支付日期
	private String SVR_DATE;//核心日期
	private String SEND_DESC;//发送描述
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
	public String getPAY_DATE() {
		return PAY_DATE;
	}
	public void setPAY_DATE(String pAY_DATE) {
		PAY_DATE = pAY_DATE;
	}
	public String getSVR_DATE() {
		return SVR_DATE;
	}
	public void setSVR_DATE(String sVR_DATE) {
		SVR_DATE = sVR_DATE;
	}
	public String getSEND_DESC() {
		return SEND_DESC;
	}
	public void setSEND_DESC(String sEND_DESC) {
		SEND_DESC = sEND_DESC;
	}
	public String getTRAN_TIME() {
		return TRAN_TIME;
	}
	public void setTRAN_TIME(String tRAN_TIME) {
		TRAN_TIME = tRAN_TIME;
	}
	
	
}
