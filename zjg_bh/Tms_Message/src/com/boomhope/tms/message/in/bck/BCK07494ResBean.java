package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 单日累计划转金额查询-前置07494
 * @author zjg
 *
 */
@XStreamAlias("Root")
public class BCK07494ResBean extends InResBean{
	private BCK07494ResBodyBean body;

	public BCK07494ResBodyBean getBody() {
		return body;
	}

	public void setBody(BCK07494ResBodyBean body) {
		this.body = body;
	}
}
