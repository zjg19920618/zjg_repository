package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 柜员授权 -前置07660
 * @author hao
 *
 */
@XStreamAlias("Root")
public class BCK07660ResBean extends InResBean{
	BCK07660ResBodyBean BODY;

	public BCK07660ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(BCK07660ResBodyBean bODY) {
		BODY = bODY;
	}

	
}
