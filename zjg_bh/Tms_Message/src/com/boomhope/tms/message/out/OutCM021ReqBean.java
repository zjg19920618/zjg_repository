package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 前置证件信息查询(86002)请求报文Bean
 * @author shaopeng
 *
 */
@XStreamAlias("ROOT")  
public class OutCM021ReqBean extends OutReqBean{
	
	private OutCM021ReqBodyBean BODY;
	
	public OutCM021ReqBean(){
		this.BODY = new OutCM021ReqBodyBean();
	}

	public OutCM021ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(OutCM021ReqBodyBean bODY) {
		BODY = bODY;
	}
	
	

}
