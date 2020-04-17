package com.boomhope.tms.message.account.out;

import com.boomhope.tms.message.out.OutReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03510ReqBean 
* @Description   产品预计利息(24小时)-03510
* @author zhang.m 
* @date 2016年12月5日 上午11:08:44  
*/
@XStreamAlias("ROOT")
public class Account03510ReqBean extends OutReqBean {
	private Account03510ReqBodyBean BODY;

	public Account03510ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Account03510ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
