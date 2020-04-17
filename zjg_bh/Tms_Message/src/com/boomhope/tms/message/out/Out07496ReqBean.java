package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * 转账客户列表信息删除-前置07496
 * @author hk
 */
@XStreamAlias("ROOT")
public class Out07496ReqBean extends OutReqBean{

	private Out07496ReqBodyBean BODY;

	public Out07496ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out07496ReqBodyBean bODY) {
		BODY = bODY;
	}
	
	
}
