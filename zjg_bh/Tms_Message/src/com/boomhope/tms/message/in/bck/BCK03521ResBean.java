package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 账户信息查询及密码验证-前置03521
 * @author wang.sk
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("Root")
public class BCK03521ResBean extends InResBean{
	
	private BCK03521ResBodyBean Body;

	public BCK03521ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03521ResBodyBean body) {
		Body = body;
	}
	
}
