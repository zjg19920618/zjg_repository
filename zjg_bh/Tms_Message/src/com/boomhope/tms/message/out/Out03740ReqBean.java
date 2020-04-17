package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 权限明细查询【78010】(前置交易码03740)
 * 03740请求报文
 * @author zjg
 *
 */
@XStreamAlias("ROOT")
public class Out03740ReqBean extends OutReqBean {
	private Out03740ReqBodyBean BODY;

	public Out03740ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03740ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
