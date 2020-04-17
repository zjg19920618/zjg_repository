package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 转入唐宝账户查询【55060】-前置07498
 * @author hk
 *
 */
@XStreamAlias("Root")  
public class BCK07498ReqBean extends InReqBean{
	
	private BCK07498ReqBodyBean Body;

	public BCK07498ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07498ReqBodyBean body) {
		Body = body;
	}

	

}
