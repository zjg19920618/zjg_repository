package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 大小额系统参数查询CM021
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class OutCM021ResBean extends OutResBean{
	
	private OutCM021ResBodyBean BODY;
	
	public OutCM021ResBean(){
		this.BODY = new OutCM021ResBodyBean();
	}

	public void setBODY(OutCM021ResBodyBean BODY){
		this.BODY = BODY;
	}
	
	public OutCM021ResBodyBean getBODY(){
		return this.BODY;
	}
}
