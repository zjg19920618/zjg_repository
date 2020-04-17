package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 按交易授权前置77017
 * @author zjg
 *
 */
@XStreamAlias("Root")
public class BCK77017ResBean extends InResBean{
	private BCK77017ResBodyBean Body;

	public BCK77017ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK77017ResBodyBean body) {
		Body = body;
	}

	
}
