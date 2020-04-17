package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 机构关系查询交易【20102】--前置01569
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class Out01596ResBean extends OutResBean{
	
	private Out01596ResBodyBean BODY;
	
	public Out01596ResBean(){
		this.BODY = new Out01596ResBodyBean();
	}

	public void setBODY(Out01596ResBodyBean BODY){
		this.BODY = BODY;
	}
	
	public Out01596ResBodyBean getBODY(){
		return this.BODY;
	}
}
