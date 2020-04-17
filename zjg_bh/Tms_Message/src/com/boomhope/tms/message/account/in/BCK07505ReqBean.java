package com.boomhope.tms.message.account.in;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account07505ReqBean 
* @Description  唐豆兑付—07505   
* @author zh.m 
* @date 2016年12月4日 上午9:17:49  
*/
@XStreamAlias("Root")
public class BCK07505ReqBean extends InReqBean {
	private BCK07505ReqBodyBean Body;

	public BCK07505ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07505ReqBodyBean body) {
		Body = body;
	}

}
