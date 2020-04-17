package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *  账户限额查询【02879】-前置02781
 * @author hk
 *
 */
@XStreamAlias("Root")
public class BCK07496ResBean extends InResBean{
	BCK07496ResBodyBean BODY;

	public BCK07496ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(BCK07496ResBodyBean bODY) {
		BODY = bODY;
	}

	
}
