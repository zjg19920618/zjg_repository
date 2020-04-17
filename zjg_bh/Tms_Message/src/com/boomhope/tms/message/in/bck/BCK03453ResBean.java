package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *单位卡IC卡验证（核心77102）-前置03453
 * @author hk
 *
 */
@XStreamAlias("Root")  
public class BCK03453ResBean extends InResBean{
	
	private BCK03453ResBodyBean Body;

	public BCK03453ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03453ResBodyBean body) {
		Body = body;
	}

}
