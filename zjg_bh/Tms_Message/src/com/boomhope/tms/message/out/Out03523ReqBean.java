package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *查询账户利率浮动信息【55030】-前置03523
 * @author shaopeng
 *
 */
@XStreamAlias("ROOT")  
public class Out03523ReqBean extends OutReqBean{
	
	private Out03523ReqBodyBean BODY;

	public Out03523ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03523ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
