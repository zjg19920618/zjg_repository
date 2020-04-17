package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 单日累计划转金额查询-前置07494
 * @author zjg
 *
 */
@XStreamAlias("Root")
public class BCK07494ReqBean extends InReqBean {
	private BCK07494ReqBodyBean body;

	public BCK07494ReqBodyBean getBody() {
		return body;
	}

	public void setBody(BCK07494ReqBodyBean body) {
		this.body = body;
	}
	
}