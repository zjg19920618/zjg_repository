package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 转账客户列表信息查询-前置07492
 * BCK_07492响应报文Bean
 * @author wang.sk
 *
 */
@XStreamAlias("ROOT")
public class Out07492ResBean extends OutResBean {
	private Out07492ResBodyBean BODY;

	public Out07492ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out07492ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
