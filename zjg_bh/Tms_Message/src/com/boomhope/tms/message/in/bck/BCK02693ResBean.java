package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *定时发送交易撤销-前置02693
 * @author zjg
 *
 */
@XStreamAlias("Root")  
public class BCK02693ResBean extends InResBean{
	
	private BCK02693ResBodyBean Body;

	public BCK02693ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK02693ResBodyBean body) {
		Body = body;
	}

}
