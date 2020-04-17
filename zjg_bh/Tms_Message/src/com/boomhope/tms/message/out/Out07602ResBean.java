package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:个人IC卡验证-前置07602
 * @author hk
 */
@XStreamAlias("ROOT")  
public class Out07602ResBean extends OutResBean{
	
	private Out07602ResBodyBean BODY;
	
	public Out07602ResBean(){
		this.BODY = new Out07602ResBodyBean();
	}

	public void setBODY(Out07602ResBodyBean BODY){
		this.BODY = BODY;
	}
	
	public Out07602ResBodyBean getBODY(){
		return this.BODY;
	}
}
