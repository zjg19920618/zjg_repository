package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *个人客户查询( 01020)-前置07519
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class Out07519ResBean extends OutResBean{
	
	private Out07519ResBodyBean BODY;

	public Out07519ResBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out07519ResBodyBean body) {
		BODY = body;
	}

}
