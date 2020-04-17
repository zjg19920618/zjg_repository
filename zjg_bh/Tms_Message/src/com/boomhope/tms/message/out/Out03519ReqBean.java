package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 子账户列表查询-【75109】前置03519
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("ROOT")
public class Out03519ReqBean extends OutReqBean{
	
	private Out03519ReqBodyBean BODY;

	public Out03519ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03519ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
