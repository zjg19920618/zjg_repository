package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 单日累计划转金额查询-前置07494
 * @author zjg
 *
 */
@XStreamAlias("ROOT")
public class Out07494ReqBean extends OutReqBean {
	private Out07494ReqBodyBean BODY;

	public Out07494ReqBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out07494ReqBodyBean body) {
		this.BODY = body;
	}
	
}