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
public class Out07670ReqBean extends OutReqBean{

	private Out07670ReqBodyBean BODY;

	public Out07670ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out07670ReqBodyBean bODY) {
		BODY = bODY;
	}
	
	
}
