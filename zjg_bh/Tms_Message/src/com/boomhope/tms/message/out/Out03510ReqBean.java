package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 产品预计利息(24小时)-03510请求报文体Bean
 * @author Yangtao
 * 
 */
@XStreamAlias("ROOT")
public class Out03510ReqBean extends OutReqBean {
	Out03510ReqBodyBean BODY;

	public Out03510ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03510ReqBodyBean bODY) {
		BODY = bODY;
	}

}
