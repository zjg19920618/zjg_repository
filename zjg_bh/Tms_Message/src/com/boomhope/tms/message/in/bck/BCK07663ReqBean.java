package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 唐豆派发—02206前置【07663】
 */
@XStreamAlias("Root")  
public class BCK07663ReqBean extends InReqBean{
	
	private BCK07663ReqBodyBean Body;

	public BCK07663ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07663ReqBodyBean body) {
		Body = body;
	}

}
