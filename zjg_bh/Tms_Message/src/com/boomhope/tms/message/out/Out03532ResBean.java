package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *推荐人奖励领取【17030】前置-03532
 * @author ly
 *
 */
@XStreamAlias("ROOT")  
public class Out03532ResBean extends OutResBean{
	
	private Out03532ResBodyBean BODY;

	public Out03532ResBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out03532ResBodyBean body) {
		BODY = body;
	}

}
