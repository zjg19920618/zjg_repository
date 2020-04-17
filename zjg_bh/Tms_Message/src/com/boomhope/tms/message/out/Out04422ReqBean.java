package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 客户基本信息查询 04422
 * @author hao
 *
 */
@XStreamAlias("ROOT")
public class Out04422ReqBean extends OutReqBean{
	private Out04422ReqBodyBean BODY;

	public Out04422ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out04422ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
