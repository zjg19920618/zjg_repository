package com.boomhope.tms.message.account.out;

import com.boomhope.tms.message.out.OutReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account07505ReqBean 
* @Description  唐豆兑付—07505   
* @author zh.m 
* @date 2016年12月4日 上午9:17:49  
*/
@XStreamAlias("ROOT")
public class Account07505ReqBean extends OutReqBean {
	private Account07505ReqBodyBean BODY;

	public Account07505ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Account07505ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
