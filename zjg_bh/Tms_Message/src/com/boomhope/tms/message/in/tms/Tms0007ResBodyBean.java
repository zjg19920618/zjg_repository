package com.boomhope.tms.message.in.tms;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 设备登录(TMS_0001)响应报文BodyBean
 * @author shaopeng
 *
 */
@XStreamAlias("Body")  
public class Tms0007ResBodyBean {

	private String resultCode;
	
	private String resultMsg;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
}
