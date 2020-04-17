package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 转账客户列表信息查询-前置07492
 * BCK_07492响应报文Bean
 * @author wang.sk
 *
 */
@XStreamAlias("Root")
public class BCK07492ResBean extends InResBean {
	private BCK07492ResBodyBean body;

	public BCK07492ResBodyBean getBody() {
		return body;
	}

	public void setBody(BCK07492ResBodyBean body) {
		this.body = body;
	}
	
}
