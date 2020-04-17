package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 转账客户列表信息删除-前置07496
 * @author hk
 */
@XStreamAlias("ROOT")
public class Out07496ResBean extends OutResBean {
	private Out07496ResBodyBean BODY;

	public Out07496ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out07496ResBodyBean bODY) {
		BODY = bODY;
	}
}
