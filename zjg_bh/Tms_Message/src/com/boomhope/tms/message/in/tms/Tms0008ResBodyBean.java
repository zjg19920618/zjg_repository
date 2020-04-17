package com.boomhope.tms.message.in.tms;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 查询银行行号(TMS_0001)响应报文BodyBean
 * @author shaopeng
 *
 */
@XStreamAlias("Body")  
public class Tms0008ResBodyBean {
	
	private String bankNum;
	
	private String bankInfoList;

	public String getBankNum() {
		return bankNum;
	}

	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}

	public String getBankInfoList() {
		return bankInfoList;
	}

	public void setBankInfoList(String bankInfoList) {
		this.bankInfoList = bankInfoList;
	}
}
