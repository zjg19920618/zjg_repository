package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 权限明细查询【77015】(前置交易码02956)
 * 02956响应报文Bean
 * @author wang.sk
 *
 */
@XStreamAlias("ROOT")
public class Out02956ResBean extends OutResBean {
	private Out02956ResBodyBean BODY;

	public Out02956ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02956ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
