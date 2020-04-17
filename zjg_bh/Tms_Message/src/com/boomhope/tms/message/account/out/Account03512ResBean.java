package com.boomhope.tms.message.account.out;

import com.boomhope.tms.message.out.OutResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03512ResBean 
* @Description 如意存明细查询【55020】--前置03512  
* @author zhang.m 
* @date 2016年12月5日 下午12:04:18  
*/
@XStreamAlias("ROOT")
public class Account03512ResBean extends OutResBean {
	private Account03512ResBodyBean BODY;

	public Account03512ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Account03512ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
