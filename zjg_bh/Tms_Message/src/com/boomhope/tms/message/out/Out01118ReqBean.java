package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 根据机构号查询支付行信息-前置01118
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class Out01118ReqBean extends OutReqBean{
	
	private Out01118ReqBodyBean BODY;
	
	public Out01118ReqBean(){
		this.BODY = new Out01118ReqBodyBean();
	}

	public Out01118ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out01118ReqBodyBean bODY) {
		BODY = bODY;
	}
	
	

}
