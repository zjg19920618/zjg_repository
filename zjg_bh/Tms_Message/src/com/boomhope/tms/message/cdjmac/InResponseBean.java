package com.boomhope.tms.message.cdjmac;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 存单机响应报文头Bean
 * @author shaopeng
 *
 */
@XStreamAlias("Response")  
public class InResponseBean {
	
	private String ResCode;	// 返回代码
	private String ResMsg;	// 返回信息
	
	public String getResCode() {
		return ResCode;
	}
	public void setResCode(String resCode) {
		ResCode = resCode;
	}
	public String getResMsg() {
		return ResMsg;
	}
	public void setResMsg(String resMsg) {
		ResMsg = resMsg;
	}
	
}
