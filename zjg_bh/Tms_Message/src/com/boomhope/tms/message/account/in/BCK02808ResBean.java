package com.boomhope.tms.message.account.in;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;


/** 
* @ClassName  Account02808ResBean 
* @Description  个人存款开户--02808
* @author zh.m 
* @date 2016年12月4日 上午9:06:05  
*/
@XStreamAlias("Root")
public class BCK02808ResBean extends InResBean {
	
	private BCK02808ResBodyBean Body;

	public BCK02808ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK02808ResBodyBean body) {
		Body = body;
	}

}
