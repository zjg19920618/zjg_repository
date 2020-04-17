package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 客户评级查询03524
 *
 */
@XStreamAlias("ROOT")  
public class Out03524ResBean extends OutResBean{
	
	private Out03524ResBodyBean BODY;

	public Out03524ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03524ResBodyBean bODY) {
		BODY = bODY;
	}

	
}
