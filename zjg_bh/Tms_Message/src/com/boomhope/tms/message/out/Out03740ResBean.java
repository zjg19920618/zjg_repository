package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 权限明细查询【78010】(前置交易码03740)
 * 03740响应报文
 * @author zjg
 *
 */
@XStreamAlias("ROOT")
public class Out03740ResBean extends OutResBean {
	private Out03740ResBodyBean BODY;

	public Out03740ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03740ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
