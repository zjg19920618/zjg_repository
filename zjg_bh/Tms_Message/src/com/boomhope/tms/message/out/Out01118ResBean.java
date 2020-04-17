package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:根据机构号查询支付行信息-前置01118
 * @author hk
 */
@XStreamAlias("ROOT")  
public class Out01118ResBean extends OutResBean{
	
	private Out01118ResBodyBean BODY;
	
	public Out01118ResBean(){
		this.BODY = new Out01118ResBodyBean();
	}

	public void setBODY(Out01118ResBodyBean BODY){
		this.BODY = BODY;
	}
	
	public Out01118ResBodyBean getBODY(){
		return this.BODY;
	}
}
