package com.boomhope.tms.message.out;


import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 唐宝x号转入【79100】-前置07499
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class Out07499ResBean extends OutResBean{
	
	private Out07499ResBodyBean BODY;

	public Out07499ResBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out07499ResBodyBean body) {
		BODY = body;
	}

	

}
