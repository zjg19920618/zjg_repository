package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 卡系统 子账户销户【75104】前置03517
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("ROOT")	
public class Out03517ResBean extends OutResBean{
	private Out03517ResBodyBean BODY;

	public Out03517ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03517ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
