package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *大额普通汇兑往帐发送交易接口（通用）02013
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class Out02013ResBean extends OutResBean{
	
	private Out02013ResBodyBean BODY;
	
	public Out02013ResBean(){
		this.BODY = new Out02013ResBodyBean();
	}

	public void setBODY(Out02013ResBodyBean BODY){
		this.BODY = BODY;
	}
	
	public Out02013ResBodyBean getBODY(){
		return this.BODY;
	}
}
