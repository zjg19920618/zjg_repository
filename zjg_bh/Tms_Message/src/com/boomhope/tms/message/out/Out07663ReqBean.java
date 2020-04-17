package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 唐豆派发—02206前置【07663】
 */
@XStreamAlias("ROOT")  
public class Out07663ReqBean extends OutReqBean{
	
	private Out07663ReqBodyBean BODY;

	public Out07663ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out07663ReqBodyBean bODY) {
		BODY = bODY;
	}

}
