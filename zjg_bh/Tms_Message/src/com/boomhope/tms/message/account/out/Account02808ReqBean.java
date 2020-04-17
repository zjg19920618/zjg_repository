package com.boomhope.tms.message.account.out;

import com.boomhope.tms.message.out.OutReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;


/** 
* @ClassName  Account02808ReqBean 
* @Description  个人存款开户—02808
* @author zh.m 
* @date 2016年12月4日 上午8:49:56  
*/
@XStreamAlias("ROOT")
public class Account02808ReqBean extends OutReqBean{
	private Account02808ReqBodyBean BODY;

	public Account02808ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Account02808ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
