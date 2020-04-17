package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *个人客户查询( 01020)-前置07519
 * @author hk
 *
 */
@XStreamAlias("Root")  
public class BCK07519ReqBean extends InReqBean{
	
	private BCK07519ReqBodyBean Body;

	public BCK07519ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07519ReqBodyBean body) {
		Body = body;
	}

}
