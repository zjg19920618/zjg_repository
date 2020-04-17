package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 账户信息查询及密码验证-前置03521
 * @author wang.sk
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("Body")
public class BCK03521ReqBodyBean {
	private String ACCT_NO; //账户
	private String PIN_VAL_FLAG;//验密标志
	private String PASSWORD;//密码
	public String getACCT_NO() {
		return ACCT_NO;
	}
	public void setACCT_NO(String aCCT_NO) {
		ACCT_NO = aCCT_NO;
	}
	public String getPIN_VAL_FLAG() {
		return PIN_VAL_FLAG;
	}
	public void setPIN_VAL_FLAG(String pIN_VAL_FLAG) {
		PIN_VAL_FLAG = pIN_VAL_FLAG;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	
	
}
