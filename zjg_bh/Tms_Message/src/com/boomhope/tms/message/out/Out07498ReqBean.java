package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 转入唐宝账户查询【55060】-前置07498
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class Out07498ReqBean extends OutReqBean{
	
	private Out07498ReqBodyBean BODY;

	public Out07498ReqBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out07498ReqBodyBean body) {
		BODY = body;
	}

	

}
