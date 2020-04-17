package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *子账号开户流水查询【20110】-07522
 * @author zjg
 *
 */
@XStreamAlias("Root")  
public class BCK07522ResBean extends InResBean{
	
	private BCK07522ResBodyBean Body;

	public BCK07522ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07522ResBodyBean body) {
		Body = body;
	}

}
