package com.boomhope.tms.message.account.out;

import com.boomhope.tms.message.out.OutResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;


/** 
* @ClassName  Account02808ResBean 
* @Description  个人存款开户--02808
* @author zh.m 
* @date 2016年12月4日 上午9:06:05  
*/
@XStreamAlias("ROOT")
public class Account02808ResBean extends OutResBean {
	
	private Account02808ResBodyBean BODY;

	public Account02808ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Account02808ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
