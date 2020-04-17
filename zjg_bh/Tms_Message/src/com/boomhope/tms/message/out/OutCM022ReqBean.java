package com.boomhope.tms.message.out;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *定时发送交易信息查询-前置CM022
 * @author zjg
 *
 */
@XStreamAlias("ROOT")  
public class OutCM022ReqBean extends OutReqBean{
	
	private OutCM022ReqBodyBean BODY;

	public OutCM022ReqBodyBean getBody() {
		return BODY;
	}

	public void setBody(OutCM022ReqBodyBean body) {
		BODY = body;
	}

}
