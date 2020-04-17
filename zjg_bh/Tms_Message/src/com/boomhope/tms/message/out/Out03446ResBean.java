package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 个人客户建立【前置交易码-03446】
 */
@XStreamAlias("ROOT")
public class Out03446ResBean extends OutResBean {

	private Out03446ResBodyBean BODY;

	public Out03446ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03446ResBodyBean bODY) {
		BODY = bODY;
	}

}
