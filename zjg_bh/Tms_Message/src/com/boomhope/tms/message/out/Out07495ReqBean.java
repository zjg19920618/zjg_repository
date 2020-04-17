package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 核心节假日查询-前置07495
 * @author wang.sk
 *
 */
@XStreamAlias("ROOT")
public class Out07495ReqBean extends OutReqBean {
	private Out07495ReqBodyBean BODY;

	public Out07495ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out07495ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
