package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *  账户限额查询【02879】-前置02781
 * @author hk
 *
 */
@XStreamAlias("Root")
public class BCK02781ResBean extends InResBean{
	BCK02781ResBodyBean BODY;

	public BCK02781ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(BCK02781ResBodyBean bODY) {
		BODY = bODY;
	}

	
}
