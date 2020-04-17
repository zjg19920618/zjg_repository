package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 客户基本信息查询 04422
 * @author hao
 *
 */
@XStreamAlias("ROOT")
public class Out04422ResBean extends OutResBean{
	private Out04422ResBodyBean BODY;

	public Out04422ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out04422ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
