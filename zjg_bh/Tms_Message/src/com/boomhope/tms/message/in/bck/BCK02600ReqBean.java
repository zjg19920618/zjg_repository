package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:行内汇划-前置02600
 * @author hk
 */
@XStreamAlias("Root")
public class BCK02600ReqBean extends InReqBean{

	private BCK02600ReqBodyBean Body;

	public BCK02600ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK02600ReqBodyBean body) {
		Body = body;
	}
	
}
