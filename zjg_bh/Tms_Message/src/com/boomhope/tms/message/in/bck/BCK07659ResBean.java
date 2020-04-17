package com.boomhope.tms.message.in.bck;


import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *柜员认证方式查询-前置07659响应报文体Bean
 * @author Yangtao
 *
 */
@XStreamAlias("Root")  
public class BCK07659ResBean extends InResBean{
	
	private BCK07659ResBodyBean Body;

	public BCK07659ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07659ResBodyBean body) {
		Body = body;
	}

	

}
