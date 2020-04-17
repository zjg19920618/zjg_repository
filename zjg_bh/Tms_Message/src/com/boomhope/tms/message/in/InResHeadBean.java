package com.boomhope.tms.message.in;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 内联网关响应报文头Bean
 * @author shaopeng
 *
 */
@XStreamAlias("Head")  
public class InResHeadBean {
	
	private String ResCode;	// 返回代码
	private String ResMsg;	// 返回信息
	
	public void setResCode(String resCode){
		this.ResCode = resCode;
	}
	
	public String getResCode(){
		return this.ResCode;
	}
	
	public void setResMsg(String resMsg){
		this.ResMsg = resMsg;
	}
	
	public String getResMsg(){
		return this.ResMsg;
	}

}
