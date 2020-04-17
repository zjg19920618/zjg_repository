package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 核心节假日查询-前置07495
 * @author wang.sk
 *
 */
@XStreamAlias("ROOT")
public class Out07495ResBean extends OutResBean {
	private Out07495ResBodyBean BODY;

	public Out07495ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out07495ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
