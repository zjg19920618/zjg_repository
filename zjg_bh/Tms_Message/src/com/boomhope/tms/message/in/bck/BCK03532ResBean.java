package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *推荐人奖励领取【17030】前置-03532
 * @author ly
 *
 */
@XStreamAlias("Root")  
public class BCK03532ResBean extends InResBean{
	
	private BCK03532ResBodyBean Body;

	public BCK03532ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03532ResBodyBean body) {
		Body = body;
	}

}
