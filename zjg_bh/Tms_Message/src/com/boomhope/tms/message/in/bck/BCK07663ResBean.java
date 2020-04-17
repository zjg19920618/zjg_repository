package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 唐豆派发—02206前置【07663】
 */
@XStreamAlias("Root")  
public class BCK07663ResBean extends InResBean{
	
	private BCK07663ResBodyBean Body;

	public BCK07663ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07663ResBodyBean body) {
		Body = body;
	}

}
