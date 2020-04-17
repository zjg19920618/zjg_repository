package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:身份核查
 * Description:访问前置
 * @author mouchunyue
 * @date 2016年9月9日 下午6:31:18
 */
@XStreamAlias("ROOT")
public class Out03448ReqBean extends OutReqBean{

	private Out03448ReqBodyBean BODY;

	public Out03448ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03448ReqBodyBean bODY) {
		BODY = bODY;
	}
	
	
}
