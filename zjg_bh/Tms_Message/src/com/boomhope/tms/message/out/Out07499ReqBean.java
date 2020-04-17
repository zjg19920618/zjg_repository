package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 唐宝x号转入【79100】-前置07499
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class Out07499ReqBean extends OutReqBean{
	
	private Out07499ReqBodyBean BODY;

	public Out07499ReqBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out07499ReqBodyBean body) {
		BODY = body;
	}

	

}
