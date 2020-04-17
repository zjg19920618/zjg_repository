package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 小额普通贷记往帐录入（通用）02224
 * BCK_02224响应报文Bean
 * @author wang.sk
 *
 */
@XStreamAlias("ROOT")
public class Out02224ResBean extends OutResBean {
	private Out02224ResBodyBean BODY;

	public Out02224ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02224ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
