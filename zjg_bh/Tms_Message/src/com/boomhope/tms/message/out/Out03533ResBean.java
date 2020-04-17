package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *推荐信息录入【17034】前置-03533
 * @author ly
 *
 */
@XStreamAlias("ROOT")  
public class Out03533ResBean extends OutResBean{
	
	private Out03533ResBodyBean BODY;

	public Out03533ResBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out03533ResBodyBean body) {
		BODY = body;
	}

}
