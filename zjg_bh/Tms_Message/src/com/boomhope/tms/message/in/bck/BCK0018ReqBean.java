package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 子账户列表查询-【75109】前置03519
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("Root")
public class BCK0018ReqBean extends InReqBean{
	
	private BCK0018ReqBodyBean Body;

	public BCK0018ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0018ReqBodyBean body) {
		Body = body;
	}


	
}
