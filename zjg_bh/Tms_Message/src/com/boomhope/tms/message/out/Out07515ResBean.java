package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *前置系统参数查询-前置QRY00
 * @author zjg
 *
 */
@XStreamAlias("ROOT")  
public class Out07515ResBean extends OutResBean{
	
	private Out07515ResBodyBean BODY;

	public Out07515ResBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out07515ResBodyBean body) {
		BODY = body;
	}

}
