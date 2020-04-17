package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/***
 * 个人客户建立【前置交易码-03446】
 */
@XStreamAlias("ROOT")
public class Out03446ReqBean extends OutReqBean {

	private Out03446ReqBodyBean BODY;

	public Out03446ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03446ReqBodyBean bODY) {
		BODY = bODY;
	}

}
