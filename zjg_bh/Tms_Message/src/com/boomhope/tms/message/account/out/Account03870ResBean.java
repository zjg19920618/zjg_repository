package com.boomhope.tms.message.account.out;

import com.boomhope.tms.message.out.OutResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03870ResBean 
* @Description "积享存"个人存款开户【75100】前置【03870】   
* @author zhang.m 
* @date 2016年12月5日 下午6:27:06  
*/
@XStreamAlias("ROOT")
public class Account03870ResBean extends OutResBean {
	private Account03870ResBodyBean BODY;

	public Account03870ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Account03870ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
