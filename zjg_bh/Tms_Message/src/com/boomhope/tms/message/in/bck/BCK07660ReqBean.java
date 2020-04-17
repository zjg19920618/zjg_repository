package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 柜员授权 -前置07660
 * @author hao
 *
 */
@XStreamAlias("Root")
public class BCK07660ReqBean extends InReqBean{
	private BCK07660ReqBodyBean Body;

	public BCK07660ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07660ReqBodyBean body) {
		Body = body;
	}

	
}
