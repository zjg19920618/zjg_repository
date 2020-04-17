package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *个人客户查询( 01020)-前置07519
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class Out07519ReqBean extends OutReqBean{
	
	private Out07519ReqBodyBean BODY;

	public Out07519ReqBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out07519ReqBodyBean body) {
		BODY = body;
	}

}
