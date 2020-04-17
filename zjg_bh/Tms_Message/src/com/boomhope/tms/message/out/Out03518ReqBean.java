package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 待打印存单查询【02948】前置03518
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("ROOT")
public class Out03518ReqBean extends OutReqBean {
	private Out03518ReqBodyBean BODY;

	public Out03518ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03518ReqBodyBean bODY) {
		BODY = bODY;
	}

}
