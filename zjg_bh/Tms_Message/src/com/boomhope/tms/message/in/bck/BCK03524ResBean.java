package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *客户评级查询03524
 *
 */
@XStreamAlias("Root")  
public class BCK03524ResBean extends InResBean{
	
	private BCK03524ResBodyBean Body;

	public BCK03524ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03524ResBodyBean body) {
		Body = body;
	}

}
