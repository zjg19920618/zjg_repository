package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 待打印存单查询【02948】前置03518
 * @author zjg
 * @date 2016年11月7日 上午10:32:40
 */
@XStreamAlias("ROOT")
public class Out03518ResBean extends OutResBean {
	private Out03518ResBodyBean BODY;

	public Out03518ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03518ResBodyBean bODY) {
		BODY = bODY;
	}

}
