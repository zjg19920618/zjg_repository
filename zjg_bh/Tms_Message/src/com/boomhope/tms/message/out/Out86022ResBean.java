package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 前置证件信息查询(86002)报文Bean
 * @author shaopeng
 *
 */
@XStreamAlias("ROOT")  
public class Out86022ResBean extends OutResBean{
	
	private Out86022ResBodyBean BODY;
	
	public Out86022ResBean(){
		this.BODY = new Out86022ResBodyBean();
	}

	public void setBODY(Out86022ResBodyBean BODY){
		this.BODY = BODY;
	}
	
	public Out86022ResBodyBean getBODY(){
		return this.BODY;
	}
}
