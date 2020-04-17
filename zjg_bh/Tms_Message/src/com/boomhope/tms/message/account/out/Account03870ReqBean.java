package com.boomhope.tms.message.account.out;

import com.boomhope.tms.message.out.OutReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03870ReqBean 
* @Description  "积享存"个人存款开户【75100】前置【03870】 
* @author zhang.m 
* @date 2016年12月5日 下午12:21:30  
*/
@XStreamAlias("ROOT")
public class Account03870ReqBean extends OutReqBean {
	private Account03870ReqBodyBean BODY;

	public Account03870ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Account03870ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
