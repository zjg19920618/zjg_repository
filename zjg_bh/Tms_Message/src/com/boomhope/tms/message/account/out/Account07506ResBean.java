package com.boomhope.tms.message.account.out;

import com.boomhope.tms.message.out.OutResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;


/** 
* @ClassName  Account07506ResBean 
* @Description 派发规则查询(交易码07506)  
* @author zh.m 
* @date 2016年12月4日 上午9:55:50  
*/
@XStreamAlias("ROOT")
public class Account07506ResBean extends OutResBean {
	private Account07506ResBodyBean BODY;

	public Account07506ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Account07506ResBodyBean bODY) {
		BODY = bODY;
	}
	
	
}
