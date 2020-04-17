package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 唐宝x号转入【79100】-前置07499
 * @author hk
 *
 */
@XStreamAlias("Root")  
public class BCK07499ReqBean extends InReqBean{
	
	private BCK07499ReqBodyBean Body;

	public BCK07499ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07499ReqBodyBean body) {
		Body = body;
	}

	

}
