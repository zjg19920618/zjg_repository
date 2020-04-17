package com.boomhope.tms.message.in.tms;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 外送设备上送(TMS_0003)请求报文体Bean
 * @author acer
 *
 */
@XStreamAlias("Body")
public class Tms0003ReqBodyBean {

	@XStreamAlias("Peri")
	private Tms0003PeriBean periBean;

	public Tms0003PeriBean getPeriBean() {
		return periBean;
	}

	public void setPeriBean(Tms0003PeriBean periBean) {
		this.periBean = periBean;
	}
	
	
}
