package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 待打印存单查询【02948】前置02792
 * @author zjg
 * @date 2016年11月7日 上午10:32:40
 */
@XStreamAlias("ROOT")
public class Out02792ResBean extends OutResBean {
	private Out02792ResBodyBean BODY;

	public Out02792ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02792ResBodyBean bODY) {
		BODY = bODY;
	}

}
