package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 账号信息综合查询【02880】前置03515
 * @author zjg
 * @date 2016年11月7日 上午10:49:13
 */
@XStreamAlias("ROOT")
public class Out03515ResBean extends OutResBean {
	public Out03515ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03515ResBodyBean bODY) {
		BODY = bODY;
	}

	Out03515ResBodyBean BODY;

}
