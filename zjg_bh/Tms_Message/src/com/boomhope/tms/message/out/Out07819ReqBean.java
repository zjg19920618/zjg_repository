package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *电子账户子账户列表查询【35109】（直连电子账户）-前置07819
 * @author zjg
 *
 */
@XStreamAlias("ROOT")  
public class Out07819ReqBean extends OutReqBean{
	
	private Out07819ReqBodyBean BODY;

	public Out07819ReqBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out07819ReqBodyBean body) {
		BODY = body;
	}

}
