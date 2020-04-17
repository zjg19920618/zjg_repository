package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *5.24存款关联账号查询【02947】-前置07518
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class Out07518ReqBean extends OutReqBean{
	
	private Out07518ReqBodyBean BODY;

	public Out07518ReqBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out07518ReqBodyBean body) {
		BODY = body;
	}

}
