package com.boomhope.tms.message.account.in;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;


/** 
* @ClassName  Account02808ReqBean 
* @Description  个人存款开户—02808
* @author zh.m 
* @date 2016年12月4日 上午8:49:56  
*/
@XStreamAlias("Root")
public class BCK02808ReqBean extends InReqBean{
	private BCK02808ReqBodyBean Body;

	public BCK02808ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK02808ReqBodyBean body) {
		Body = body;
	}

}
