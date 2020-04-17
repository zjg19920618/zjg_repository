package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *前置系统参数查询-前置QRY00
 * @author zjg
 *
 */
@XStreamAlias("ROOT")  
public class Out07515ReqBean extends OutReqBean{
	
	private Out07515ReqBodyBean BODY;

	public Out07515ReqBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out07515ReqBodyBean body) {
		BODY = body;
	}

}
