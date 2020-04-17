package com.boomhope.tms.message.in.tms;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 开关状态查询
 * @author zjg
 *
 */
@XStreamAlias("Body")  
public class Tms0009ReqBodyBean {
	
	private String onOffName;	// 开关名称

	public String getOnOffName() {
		return onOffName;
	}

	public void setOnOffName(String onOffName) {
		this.onOffName = onOffName;
	}

}
