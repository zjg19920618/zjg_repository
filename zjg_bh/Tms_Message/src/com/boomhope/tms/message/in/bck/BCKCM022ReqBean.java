package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *定时发送交易信息查询-前置CM022
 * @author zjg
 *
 */
@XStreamAlias("Root")  
public class BCKCM022ReqBean extends InReqBean{
	
	private BCKCM022ReqBodyBean Body;

	public BCKCM022ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCKCM022ReqBodyBean body) {
		Body = body;
	}

}
