package com.boomhope.tms.message.account.out;

import com.boomhope.tms.message.out.OutResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03511ResBean 
* @Description   产品可开立额度信息查询-03511
* @author zhang.m 
* @date 2016年12月5日 上午11:46:06  
*/
@XStreamAlias("ROOT")
public class Account03511ResBean extends OutResBean {
	private Account03511ResBodyBean BODY;

	public Account03511ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Account03511ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
