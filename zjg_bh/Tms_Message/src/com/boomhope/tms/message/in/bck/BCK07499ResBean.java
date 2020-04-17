package com.boomhope.tms.message.in.bck;


import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 唐宝x号转入【79100】-前置07499
 * @author hk
 *
 */
@XStreamAlias("Root")  
public class BCK07499ResBean extends InResBean{
	
	private BCK07499ResBodyBean Body;

	public BCK07499ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07499ResBodyBean body) {
		Body = body;
	}

	

}
