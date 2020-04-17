package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:行内汇划-前置02600
 * @author hk
 */
@XStreamAlias("ROOT")
public class Out02600ReqBean extends OutReqBean {
	private Out02600ReqBodyBean BODY;

	public Out02600ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02600ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
