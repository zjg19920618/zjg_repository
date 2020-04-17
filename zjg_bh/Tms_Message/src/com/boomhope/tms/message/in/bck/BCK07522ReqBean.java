package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *子账号开户流水查询【20110】-07522
 * @author hk
 *
 */
@XStreamAlias("Root")  
public class BCK07522ReqBean extends InReqBean{
	
	private BCK07522ReqBodyBean Body;

	public BCK07522ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07522ReqBodyBean body) {
		Body = body;
	}

}
