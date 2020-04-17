package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *银行卡换开查询原卡号【79114】-07523
 * @author zjg
 *
 */
@XStreamAlias("ROOT")  
public class Out07523ReqBean extends OutReqBean{
	
	private Out07523ReqBodyBean BODY;

	public Out07523ReqBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out07523ReqBodyBean body) {
		BODY = body;
	}

}
