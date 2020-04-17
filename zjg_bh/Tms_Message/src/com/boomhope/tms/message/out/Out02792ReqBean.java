package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 待打印存单查询【02948】前置02792
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("ROOT")
public class Out02792ReqBean extends OutReqBean {
	private Out02792ReqBodyBean BODY;

	public Out02792ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02792ReqBodyBean bODY) {
		BODY = bODY;
	}

}
