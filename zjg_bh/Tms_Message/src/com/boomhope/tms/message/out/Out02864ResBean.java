package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:产品利率信息查询--02864
 * Description:
 * @author mouchunyue
 * @date 2016年11月26日 下午3:57:32
 */
@XStreamAlias("ROOT")  
public class Out02864ResBean extends OutResBean{
	
	private Out02864ResBodyBean BODY;
	
	public Out02864ResBean(){
		this.BODY = new Out02864ResBodyBean();
	}

	public void setBODY(Out02864ResBodyBean BODY){
		this.BODY = BODY;
	}
	
	public Out02864ResBodyBean getBODY(){
		return this.BODY;
	}
}
