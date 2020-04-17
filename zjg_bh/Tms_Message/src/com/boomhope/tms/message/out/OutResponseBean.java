package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * Response报文域
 * @author shaopeng
 *
 */
@XStreamAlias("RESPONSE")  
public class OutResponseBean {

	String RET_CODE;	// 响应代码
	String RET_MSG;		// 响应信息
	
	public void setRetCode(String retcode){
		this.RET_CODE = retcode;
	}
	
	public String getRetCode(){
		return this.RET_CODE;
	}
	
	public void setRetMsg(String retmsg){
		this.RET_MSG = retmsg;
	}
	
	public String getRetMsg(){
		return this.RET_MSG;
	}
	
}
