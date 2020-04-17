package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 按交易授权前置77017
 * @author zjg
 *
 */
@XStreamAlias("ROOT")
public class Out77017ReqBean extends OutReqBean{
	private Out77017ReqBodyBean BODY;

	public Out77017ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out77017ReqBodyBean body) {
		BODY = body;
	}

	
}
