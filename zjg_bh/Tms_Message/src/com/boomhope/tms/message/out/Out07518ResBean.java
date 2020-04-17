package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *5.24存款关联账号查询【02947】-前置07518
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class Out07518ResBean extends OutResBean{
	
	private Out07518ResBodyBean BODY;

	public Out07518ResBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out07518ResBodyBean body) {
		BODY = body;
	}

}
