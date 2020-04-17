package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *卡系统-交易辅助登记[74073]-前置03531
 * @author ly
 *
 */
@XStreamAlias("Root")  
public class BCK03531ResBean extends InResBean{
	
	private BCK03531ResBodyBean Body;

	public BCK03531ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03531ResBodyBean body) {
		Body = body;
	}

}
