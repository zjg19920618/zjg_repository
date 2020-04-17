package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 核心节假日查询-前置07495
 * @author wang.sk
 *
 */
@XStreamAlias("Root")
public class BCK07495ReqBean extends InReqBean {
	private BCK07495ReqBodyBean body;

	public BCK07495ReqBodyBean getBody() {
		return body;
	}

	public void setBody(BCK07495ReqBodyBean body) {
		this.body = body;
	}
	
}
