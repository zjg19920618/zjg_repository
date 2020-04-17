package com.boomhope.tms.message.account.out;

import com.boomhope.tms.message.out.OutReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03511ReqBean 
* @Description   产品可开立额度信息查询-03511
* @author zhang.m 
* @date 2016年12月5日 上午11:36:19  
*/
@XStreamAlias("ROOT")
public class Account03511ReqBean extends OutReqBean {
	private Account03511ReqBodyBean BODY;

	public Account03511ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Account03511ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
