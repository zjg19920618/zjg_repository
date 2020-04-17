package com.boomhope.tms.message.in.tms;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 开关状态查询
 * @author zjg
 *
 */
@XStreamAlias("Body")  
public class Tms0009ResBodyBean {

	private String onOffState;	// 开关启用状态

	public String getOnOffState() {
		return onOffState;
	}

	public void setOnOffState(String onOffState) {
		this.onOffState = onOffState;
	}

}
