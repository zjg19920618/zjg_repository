package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *卡系统-交易辅助登记[74073]-前置03531
 * @author ly
 *
 */
@XStreamAlias("BODY")  
public class Out03531ReqBodyBean {
	
	private String WORK_NO;//工号
	private String JRNL_NO;//流水号
	
	public String getWORK_NO() {
		return WORK_NO;
	}
	public void setWORK_NO(String wORK_NO) {
		WORK_NO = wORK_NO;
	}
	public String getJRNL_NO() {
		return JRNL_NO;
	}
	public void setJRNL_NO(String jRNL_NO) {
		JRNL_NO = jRNL_NO;
	}
	

	
}
