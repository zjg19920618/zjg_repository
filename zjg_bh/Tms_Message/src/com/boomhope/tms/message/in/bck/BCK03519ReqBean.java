package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 子账户列表查询-【75109】前置03519
 * @author zjg
 * @date 2017年07月07日 上午10:30:47
 */
@XStreamAlias("Root")
public class BCK03519ReqBean extends InReqBean{
	
	private BCK03519ReqBodyBean Body;

	public BCK03519ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03519ReqBodyBean body) {
		Body = body;
	}


	
}
