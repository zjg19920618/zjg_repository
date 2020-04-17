package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 按交易授权前置77017
 * @author zjg
 *
 */
@XStreamAlias("Root")
public class BCK77017ReqBean extends InReqBean{
	private BCK77017ReqBodyBean Body;

	public BCK77017ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK77017ReqBodyBean body) {
		Body = body;
	}

	
}
