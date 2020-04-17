package com.boomhope.tms.message.account.out;

import com.boomhope.tms.message.out.OutReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account02864ReqBean 
* @Description  产品利率信息查询—02864 
* @author zhang.m 
* @date 2016年12月5日 上午11:50:56  
*/
@XStreamAlias("ROOT")
public class Account02864ReqBean extends OutReqBean{
	private Account02864ReqBodyBean BODY;

	public Account02864ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Account02864ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
