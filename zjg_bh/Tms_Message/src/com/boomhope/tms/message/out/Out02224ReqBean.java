package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 小额普通贷记往帐录入（通用）02224
 * 02224请求报文
 * @author wang.sk
 *
 */
@XStreamAlias("ROOT")
public class Out02224ReqBean extends OutReqBean {
	private Out02224ReqBodyBean BODY;

	public Out02224ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02224ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
