package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *定时发送交易撤销-前置02693
 * @author zjg
 *
 */
@XStreamAlias("Root")  
public class BCK02693ReqBean extends InReqBean{
	
	private BCK02693ReqBodyBean Body;

	public BCK02693ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK02693ReqBodyBean body) {
		Body = body;
	}

}
