package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 按交易授权前置77017
 * @author zjg
 *
 */
@XStreamAlias("ROOT")
public class Out77017ResBean extends OutResBean{
	private Out77017ResBodyBean BODY;

	public Out77017ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out77017ResBodyBean bODY) {
		BODY = bODY;
	}

	
}
