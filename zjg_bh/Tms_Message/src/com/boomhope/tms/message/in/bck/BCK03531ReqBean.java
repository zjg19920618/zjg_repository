package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *卡系统-交易辅助登记[74073]-前置03531
 * @author ly
 *
 */
@XStreamAlias("Root")  
public class BCK03531ReqBean extends InReqBean{
	
	private BCK03531ReqBodyBean Body;

	public BCK03531ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03531ReqBodyBean body) {
		Body = body;
	}

}
