package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *推荐人奖励领取【17030】前置-03532
 * @author ly
 *
 */
@XStreamAlias("ROOT")  
public class Out03532ReqBean extends OutReqBean{
	
	private Out03532ReqBodyBean BODY;

	public Out03532ReqBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out03532ReqBodyBean body) {
		BODY = body;
	}

}
