package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *根据客户号查询所有卡号信息【90001】-前置07520
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class Out07520ReqBean extends OutReqBean{
	
	private Out07520ReqBodyBean BODY;

	public Out07520ReqBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out07520ReqBodyBean body) {
		BODY = body;
	}

}
