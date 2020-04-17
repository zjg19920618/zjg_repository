package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *查询账户利率浮动信息【55030】-前置03523
 * @author shaopeng
 *
 */
@XStreamAlias("ROOT")  
public class Out03523ResBean extends OutResBean{
	
	private Out03523ResBodyBean BODY;

	public Out03523ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03523ResBodyBean bODY) {
		BODY = bODY;
	}

	
}
