package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *Title:个人IC卡验证-前置07602
 * @author hk
 *
 */
@XStreamAlias("Root")  
public class BCK07602ResBean extends InResBean{
	
	private BCK07602ResBodyBean Body;

	public BCK07602ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07602ResBodyBean body) {
		Body = body;
	}

}
