package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 小额普通贷记往帐录入（通用）02224
 * BCK_02224响应报文Bean
 * @author wang.sk
 *
 */
@XStreamAlias("Root")
public class BCK02224ResBean extends InResBean {
	private BCK02224ResBodyBean body;

	public BCK02224ResBodyBean getBody() {
		return body;
	}

	public void setBody(BCK02224ResBodyBean body) {
		this.body = body;
	}
	
}
