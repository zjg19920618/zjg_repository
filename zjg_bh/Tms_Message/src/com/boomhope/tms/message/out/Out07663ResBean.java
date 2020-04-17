package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 唐豆派发—02206前置【07663】
 */
@XStreamAlias("ROOT")  
public class Out07663ResBean extends OutResBean{
	
	private Out07663ResBodyBean BODY;

	public Out07663ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out07663ResBodyBean bODY) {
		BODY = bODY;
	}

}
