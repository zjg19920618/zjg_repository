package com.boomhope.tms.message.out;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *定时发送交易信息查询-前置CM022
 * @author zjg
 *
 */
@XStreamAlias("ROOT")  
public class OutCM022ResBean extends OutResBean{
	
	private OutCM022ResBodyBean BODY;

	public OutCM022ResBodyBean getBody() {
		return BODY;
	}

	public void setBody(OutCM022ResBodyBean body) {
		BODY = body;
	}

}
