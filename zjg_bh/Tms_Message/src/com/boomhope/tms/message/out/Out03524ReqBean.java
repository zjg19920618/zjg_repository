package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *客户评级查询03524
 *
 */
@XStreamAlias("ROOT")  
public class Out03524ReqBean extends OutReqBean{
	
	private Out03524ReqBodyBean BODY;

	public Out03524ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03524ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
