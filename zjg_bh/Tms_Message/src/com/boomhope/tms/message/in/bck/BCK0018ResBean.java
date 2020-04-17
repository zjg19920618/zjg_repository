package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 子账户列表查询-【75109】前置03519
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("Root")
public class BCK0018ResBean extends InResBean{
	
	private BCK0018ResBodyBean Body;

	public BCK0018ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0018ResBodyBean body) {
		Body = body;
	};

	
}
