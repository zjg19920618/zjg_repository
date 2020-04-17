package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 个人IC卡验证-前置07602
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class Out07602ReqBean extends OutReqBean{
	
	private Out07602ReqBodyBean BODY;
	
	public Out07602ReqBean(){
		this.BODY = new Out07602ReqBodyBean();
	}

	public Out07602ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out07602ReqBodyBean bODY) {
		BODY = bODY;
	}
	
	

}
