package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 权限明细查询【77015】(前置交易码02956)
 * 02956请求报文
 * @author wang.sk
 *
 */
@XStreamAlias("ROOT")
public class Out02956ReqBean extends OutReqBean {
	private Out02956ReqBodyBean BODY;

	public Out02956ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02956ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
