package com.boomhope.tms.message.out;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *卡系统-交易辅助登记[74073]-前置03531
 * @author ly
 *
 */
@XStreamAlias("ROOT")  
public class Out03531ResBean extends OutResBean{
	
	private Out03531ResBodyBean BODY;

	public Out03531ResBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out03531ResBodyBean body) {
		BODY = body;
	}

}
