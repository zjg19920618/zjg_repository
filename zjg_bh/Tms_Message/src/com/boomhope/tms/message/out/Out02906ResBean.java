package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *客户账户基本信息维护【17024】前置-02906
 * @author ly
 *
 */
@XStreamAlias("ROOT")  
public class Out02906ResBean extends OutResBean{
	
	private Out02906ResBodyBean BODY;

	public Out02906ResBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out02906ResBodyBean body) {
		BODY = body;
	}

}
