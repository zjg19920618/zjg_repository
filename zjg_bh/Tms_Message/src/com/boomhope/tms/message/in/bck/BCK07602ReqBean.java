package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 个人IC卡验证-前置07602
 * @author hk
 *
 */
@XStreamAlias("Root")  
public class BCK07602ReqBean extends InReqBean{
	
	private BCK07602ReqBodyBean Body;

	public BCK07602ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07602ReqBodyBean body) {
		Body = body;
	}

}
