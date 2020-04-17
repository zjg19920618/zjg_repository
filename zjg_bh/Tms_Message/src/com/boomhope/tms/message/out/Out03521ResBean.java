package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 账户信息查询及密码验证-前置03521
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("ROOT")
public class Out03521ResBean extends OutResBean{
	
	private Out03521ResBodyBean BODY;

	public Out03521ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03521ResBodyBean bODY) {
		BODY = bODY;
	}

	
	
}
