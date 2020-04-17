package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 核心节假日查询-前置07495
 * @author wang.sk
 *
 */
@XStreamAlias("Root")
public class BCK07495ResBean extends InResBean {
	private BCK07495ResBodyBean body;

	public BCK07495ResBodyBean getBody() {
		return body;
	}

	public void setBody(BCK07495ResBodyBean body) {
		this.body = body;
	}
	
}
