package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *子账号开户流水查询【20110】-07522
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class Out07522ResBean extends OutResBean{
	
	private Out07522ResBodyBean BODY;

	public Out07522ResBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out07522ResBodyBean body) {
		BODY = body;
	}

}
