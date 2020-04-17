package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *推荐信息录入【17034】前置-03533
 * @author ly
 *
 */
@XStreamAlias("Root")  
public class BCK03533ResBean extends InResBean{
	
	private BCK03533ResBodyBean Body;

	public BCK03533ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03533ResBodyBean body) {
		Body = body;
	}

}
