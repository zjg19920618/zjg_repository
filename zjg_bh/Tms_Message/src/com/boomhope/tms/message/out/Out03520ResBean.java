package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 存单打印状态变更【02950】前置03520
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("ROOT")
public class Out03520ResBean extends OutResBean{
	private Out03520ResBodyBean BODY;

	public Out03520ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03520ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
