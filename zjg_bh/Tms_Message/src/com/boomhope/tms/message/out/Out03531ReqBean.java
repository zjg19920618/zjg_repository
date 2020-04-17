package com.boomhope.tms.message.out;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *卡系统-交易辅助登记[74073]-前置03531
 * @author ly
 *
 */
@XStreamAlias("ROOT")  
public class Out03531ReqBean extends OutReqBean{
	
	private Out03531ReqBodyBean BODY;

	public Out03531ReqBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out03531ReqBodyBean body) {
		BODY = body;
	}

}
