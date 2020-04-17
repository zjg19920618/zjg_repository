package com.boomhope.tms.message.account.out;

import com.boomhope.tms.message.out.OutReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03512ReqBean 
* @Description 如意存明细查询【55020】--前置03512  
* @author zhang.m 
* @date 2016年12月5日 下午12:00:49  
*/
@XStreamAlias("ROOT")
public class Account03512ReqBean extends OutReqBean {
	private Account03512ReqBodyBean BODY;

	public Account03512ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Account03512ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
