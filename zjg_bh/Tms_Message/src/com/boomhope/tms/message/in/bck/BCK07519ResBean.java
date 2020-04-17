package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *个人客户查询( 01020)-前置07519
 * @author hk
 *
 */
@XStreamAlias("Root")  
public class BCK07519ResBean extends InResBean{
	
	private BCK07519ResBodyBean Body;

	public BCK07519ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07519ResBodyBean body) {
		Body = body;
	}

}
