package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *唐豆信息查询【02217】-前置07515
 * @author zjg
 *
 */
@XStreamAlias("Root")  
public class BCK07515ResBean extends InResBean{
	
	private BCK07515ResBodyBean Body;

	public BCK07515ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07515ResBodyBean body) {
		Body = body;
	}

}
