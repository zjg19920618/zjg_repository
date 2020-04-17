package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *子账号开户流水查询【20110】-07522
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class Out07522ReqBean extends OutReqBean{
	
	private Out07522ReqBodyBean BODY;

	public Out07522ReqBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out07522ReqBodyBean body) {
		BODY = body;
	}

}
