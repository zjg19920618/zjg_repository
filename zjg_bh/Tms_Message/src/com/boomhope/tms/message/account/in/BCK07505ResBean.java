package com.boomhope.tms.message.account.in;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account07505ResBean 
* @Description   唐豆兑付—07505
* @author zh.m 
* @date 2016年12月4日 上午9:25:08  
*/
@XStreamAlias("Root")
public class BCK07505ResBean extends InResBean {
	private BCK07505ResBodyBean Body;

	public BCK07505ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07505ResBodyBean body) {
		Body = body;
	}

}
