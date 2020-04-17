package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 账户信息查询及密码验证-前置03521
 * @author wang.sk
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("Root")
public class BCK03521ReqBean extends InReqBean {
	
	private BCK03521ReqBodyBean Body;

	public BCK03521ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03521ReqBodyBean body) {
		Body = body;
	}
	
}
