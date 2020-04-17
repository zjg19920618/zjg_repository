package com.boomhope.tms.message.out;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *定时发送交易撤销-前置02693
 * @author zjg
 *
 */
@XStreamAlias("ROOT")  
public class Out02693ResBean extends OutResBean{
	
	private Out02693ResBodyBean BODY;

	public Out02693ResBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out02693ResBodyBean body) {
		BODY = body;
	}

}
