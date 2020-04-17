package com.boomhope.tms.message.out;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *定时发送交易撤销-前置02693
 * @author zjg
 *
 */
@XStreamAlias("ROOT")  
public class Out02693ReqBean extends OutReqBean{
	
	private Out02693ReqBodyBean BODY;

	public Out02693ReqBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out02693ReqBodyBean body) {
		BODY = body;
	}

}
