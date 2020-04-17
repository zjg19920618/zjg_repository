package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 电子账户 子账户销户【35104】-前置03522
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("ROOT")
public class Out03522ResBean extends OutResBean{
	private Out03522ResBodyBean BODY;

	public Out03522ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03522ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
