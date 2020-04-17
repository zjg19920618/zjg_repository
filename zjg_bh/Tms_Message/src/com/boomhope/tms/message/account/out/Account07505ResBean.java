package com.boomhope.tms.message.account.out;

import com.boomhope.tms.message.out.OutResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account07505ResBean 
* @Description   唐豆兑付—07505
* @author zh.m 
* @date 2016年12月4日 上午9:25:08  
*/
@XStreamAlias("ROOT")
public class Account07505ResBean extends OutResBean {
	private Account07505ResBodyBean BODY;

	public Account07505ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Account07505ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
