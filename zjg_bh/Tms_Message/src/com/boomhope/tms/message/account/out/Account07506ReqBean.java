package com.boomhope.tms.message.account.out;

import com.boomhope.tms.message.out.OutReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account07506ReqBean 
* @Description 派发规则查询(交易码07506)  
* @author zh.m 
* @date 2016年12月4日 上午9:50:09  
*/
@XStreamAlias("ROOT")
public class Account07506ReqBean extends OutReqBean {
	private Account07506ReqBodyBean BODY;

	public Account07506ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Account07506ReqBodyBean bODY) {
		BODY = bODY;
	}
	
	
}
