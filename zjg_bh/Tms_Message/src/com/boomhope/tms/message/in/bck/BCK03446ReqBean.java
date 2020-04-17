package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 个人客户建立【前置交易码-03446】
 */
@XStreamAlias("Root")  
public class BCK03446ReqBean extends InReqBean{
	
	private BCK03446ReqBodyBean Body;

	public BCK03446ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03446ReqBodyBean body) {
		Body = body;
	}

}
