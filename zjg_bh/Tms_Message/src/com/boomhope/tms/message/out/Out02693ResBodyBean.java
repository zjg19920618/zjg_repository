package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *定时发送交易撤销-前置02693
 * @author zjg
 *
 */
@XStreamAlias("BODY")  
public class Out02693ResBodyBean {
	
	private String MS_TRAN_DATE;//撤销交易前置日期
	private String MS_JRNL_NO;//撤销交易前置流水号
	public String getMS_TRAN_DATE() {
		return MS_TRAN_DATE;
	}
	public void setMS_TRAN_DATE(String mS_TRAN_DATE) {
		MS_TRAN_DATE = mS_TRAN_DATE;
	}
	public String getMS_JRNL_NO() {
		return MS_JRNL_NO;
	}
	public void setMS_JRNL_NO(String mS_JRNL_NO) {
		MS_JRNL_NO = mS_JRNL_NO;
	}

}
