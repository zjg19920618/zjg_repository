package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *唐豆信息查询【02217】-前置07515
 * @author zjg
 *
 */
@XStreamAlias("Root")  
public class BCK07515ReqBean extends InReqBean{
	
	private BCK07515ReqBodyBean Body;

	public BCK07515ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07515ReqBodyBean body) {
		Body = body;
	}

}
