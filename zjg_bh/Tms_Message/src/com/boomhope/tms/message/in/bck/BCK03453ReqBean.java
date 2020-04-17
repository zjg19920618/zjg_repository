package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *单位卡IC卡验证（核心77102）-前置03453
 * @author hk
 *
 */
@XStreamAlias("Root")  
public class BCK03453ReqBean extends InReqBean{
	
	private BCK03453ReqBodyBean Body;

	public BCK03453ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03453ReqBodyBean body) {
		Body = body;
	}

}
