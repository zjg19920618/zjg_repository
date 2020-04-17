package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 小额普通贷记往帐录入（通用）02224
 * BCK_02224响应报文Bean
 * @author wang.sk
 *
 */
@XStreamAlias("ROOT")
public class Out02600ResBean extends OutResBean {
	private Out02600ResBodyBean BODY;

	public Out02600ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02600ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
