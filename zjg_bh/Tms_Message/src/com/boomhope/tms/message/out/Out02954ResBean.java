package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 单位校验对手信息
 * 02954响应报文体Bean
 * @author zjg
 *
 */
@XStreamAlias("ROOT")
public class Out02954ResBean extends OutResBean {
	private Out02954ResBodyBean BODY;

	public Out02954ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02954ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
