package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *推荐人奖励领取【17030】前置-03532
 * @author ly
 *
 */
@XStreamAlias("Root")  
public class BCK03532ReqBean extends InReqBean{
	
	private BCK03532ReqBodyBean Body;

	public BCK03532ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03532ReqBodyBean body) {
		Body = body;
	}

}
