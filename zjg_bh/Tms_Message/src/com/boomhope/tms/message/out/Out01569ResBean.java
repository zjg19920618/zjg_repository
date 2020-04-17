package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 机构关系查询交易【20102】--前置01569
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class Out01569ResBean extends OutResBean{
	
	private Out01569ResBodyBean BODY;
	
	public Out01569ResBean(){
		this.BODY = new Out01569ResBodyBean();
	}

	public void setBODY(Out01569ResBodyBean BODY){
		this.BODY = BODY;
	}
	
	public Out01569ResBodyBean getBODY(){
		return this.BODY;
	}
}
