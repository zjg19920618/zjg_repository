package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 账户限额查询【02879】-前置02781
 * @author hk
 */
@XStreamAlias("ROOT")
public class Out02781ResBean extends OutResBean {
	private Out02781ResBodyBean BODY;

	public Out02781ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02781ResBodyBean bODY) {
		BODY = bODY;
	}
}
