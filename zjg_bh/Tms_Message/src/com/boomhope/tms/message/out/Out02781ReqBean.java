package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * 账户限额查询【02879】-前置02781
 * @author hk
 */
@XStreamAlias("ROOT")
public class Out02781ReqBean extends OutReqBean{

	private Out02781ReqBodyBean BODY;

	public Out02781ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02781ReqBodyBean bODY) {
		BODY = bODY;
	}
	
	
}
