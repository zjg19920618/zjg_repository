package com.boomhope.tms.message.account.out;

import com.boomhope.tms.message.out.OutResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account02864ResBean 
* @Description 产品利率信息查询—02864   
* @author zhang.m 
* @date 2016年12月5日 上午11:54:40  
*/
@XStreamAlias("ROOT")
public class Account02864ResBean extends OutResBean {
	private Account02864ResBodyBean BODY;

	public Account02864ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Account02864ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
