package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 个人客户建立【前置交易码-03446】
 */
@XStreamAlias("Root")  
public class BCK03446ResBean extends InResBean{
	
	private BCK03446ResBodyBean Body;

	public BCK03446ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03446ResBodyBean body) {
		Body = body;
	}

}
