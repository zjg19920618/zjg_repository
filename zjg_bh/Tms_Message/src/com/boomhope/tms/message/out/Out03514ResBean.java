package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 存单打印【02949】前置03514
 * @author zjg
 * @date 2016年11月7日 上午10:37:51
 */
@XStreamAlias("ROOT")
public class Out03514ResBean extends OutResBean {
	Out03514ResBodyBean BODY;

	public Out03514ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03514ResBodyBean bODY) {
		BODY = bODY;
	}

}
