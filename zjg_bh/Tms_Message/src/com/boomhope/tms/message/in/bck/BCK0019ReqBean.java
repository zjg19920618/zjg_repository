package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 账户信息查询及密码验证-前置03521
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("Root")
public class BCK0019ReqBean extends InReqBean {
	
	private BCK0019ReqBodyBean Body;

	public BCK0019ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0019ReqBodyBean body) {
		Body = body;
	}
	
}
