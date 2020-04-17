package com.boomhope.tms.message.account.out;

import com.boomhope.tms.message.out.OutResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03510ResBean 
* @Description 产品预计利息(24小时)-03510   
* @author zhang.m 
* @date 2016年12月5日 上午11:14:24  
*/
@XStreamAlias("ROOT")
public class Account03510ResBean extends OutResBean {
	private Account03510ResBodyBean BODY;

	public Account03510ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Account03510ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
