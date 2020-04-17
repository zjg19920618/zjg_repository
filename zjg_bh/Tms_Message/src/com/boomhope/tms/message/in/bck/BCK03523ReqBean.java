package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *查询账户利率浮动信息【55030】-前置03523
 * @author shaopeng
 *
 */
@XStreamAlias("Root")  
public class BCK03523ReqBean extends InReqBean{
	
	private BCK03523ReqBodyBean Body;

	public BCK03523ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03523ReqBodyBean body) {
		Body = body;
	}

}
