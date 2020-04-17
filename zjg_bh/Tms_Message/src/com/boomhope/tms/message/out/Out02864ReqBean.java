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
public class Out02864ReqBean extends OutReqBean{
	
	private Out02864ReqBodyBean BODY;
	
	public Out02864ReqBean(){
		this.BODY = new Out02864ReqBodyBean();
	}

	public Out02864ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02864ReqBodyBean bODY) {
		BODY = bODY;
	}

}
