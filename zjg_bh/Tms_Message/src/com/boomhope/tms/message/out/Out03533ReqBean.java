package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *推荐信息录入【17034】前置-03533
 * @author ly
 *
 */
@XStreamAlias("ROOT")  
public class Out03533ReqBean extends OutReqBean{
	
	private Out03533ReqBodyBean BODY;

	public Out03533ReqBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out03533ReqBodyBean body) {
		BODY = body;
	}

}
