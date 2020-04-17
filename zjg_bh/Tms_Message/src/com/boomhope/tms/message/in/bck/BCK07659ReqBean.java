package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 柜员认证方式查询-前置07659请求报文体Bean
 * @author Yangtao
 *
 */
@XStreamAlias("Root")  
public class BCK07659ReqBean extends InReqBean{
	
	private BCK07659ReqBodyBean Body;

	public BCK07659ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07659ReqBodyBean body) {
		Body = body;
	}

	

}
