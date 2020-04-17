package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 存单打印【02949】前置03514
 * @author zjg
 * @date 2016年11月7日 上午10:37:25
 */
@XStreamAlias("ROOT")
public class Out03514ReqBean extends OutReqBean {
	Out03514ReqBodyBean BODY;

	public Out03514ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03514ReqBodyBean bODY) {
		BODY = bODY;
	}

}
