package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:行内汇划-前置02600
 * @author hk
 */
@XStreamAlias("Root")
public class BCK02600ResBean extends InResBean{

	private BCK02600ResBodyBean Body;

	public BCK02600ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK02600ResBodyBean body) {
		Body = body;
	}

}
