package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 存单打印状态变更【02950】前置03520
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("ROOT")
public class Out03520ReqBean extends OutReqBean {
	private Out03520ReqBodyBean  BODY;

	public Out03520ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03520ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
