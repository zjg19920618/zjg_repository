package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *银行卡换开查询原卡号【79114】-07523
 * @author zjg
 *
 */
@XStreamAlias("ROOT")  
public class Out07523ResBean extends OutResBean{
	
	private Out07523ResBodyBean BODY;

	public Out07523ResBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out07523ResBodyBean body) {
		BODY = body;
	}

}
