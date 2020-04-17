package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 小额普通贷记往帐录入（通用）02224
 * 02224请求报文
 * @author wang.sk
 *
 */
@XStreamAlias("Root")
public class BCK02224ReqBean extends InReqBean {
	private BCK02224ReqBodyBean body;

	public BCK02224ReqBodyBean getBody() {
		return body;
	}

	public void setBody(BCK02224ReqBodyBean body) {
		this.body = body;
	}
	
}
