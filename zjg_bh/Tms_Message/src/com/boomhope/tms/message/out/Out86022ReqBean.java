package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 前置证件信息查询(86002)请求报文Bean
 * @author shaopeng
 *
 */
@XStreamAlias("ROOT")  
public class Out86022ReqBean extends OutReqBean{
	
	private Out86022ReqBodyBean BODY;
	
	public Out86022ReqBean(){
		this.BODY = new Out86022ReqBodyBean();
	}

	public Out86022ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out86022ReqBodyBean bODY) {
		BODY = bODY;
	}
	
	

}
