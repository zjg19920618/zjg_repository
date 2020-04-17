package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *推荐信息录入【17034】前置-03533
 * @author ly
 *
 */
@XStreamAlias("Root")  
public class BCK03533ReqBean extends InReqBean{
	
	private BCK03533ReqBodyBean Body;

	public BCK03533ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03533ReqBodyBean body) {
		Body = body;
	}

}
