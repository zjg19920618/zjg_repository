package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 大额普通汇兑往帐发送交易接口（通用）02013
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class Out02013ReqBean extends OutReqBean{
	
	private Out02013ReqBodyBean BODY;
	
	public Out02013ReqBean(){
		this.BODY = new Out02013ReqBodyBean();
	}

	public Out02013ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02013ReqBodyBean bODY) {
		BODY = bODY;
	}
	
	

}
