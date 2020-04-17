package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 产品预计利息(24小时)-03510响应报文体Bean
 * @author Yangtao
 *
 */
@XStreamAlias("ROOT")
public class Out03510ResBean extends OutResBean {
	Out03510ResBodyBean BODY;

	public Out03510ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03510ResBodyBean bODY) {
		BODY = bODY;
	}

}
