package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 单日累计划转金额查询-前置07494
 * @author zjg
 *
 */
@XStreamAlias("ROOT")
public class Out07494ResBean extends OutResBean{
	private Out07494ResBodyBean BODY;

	public Out07494ResBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out07494ResBodyBean body) {
		this.BODY = body;
	}
}
