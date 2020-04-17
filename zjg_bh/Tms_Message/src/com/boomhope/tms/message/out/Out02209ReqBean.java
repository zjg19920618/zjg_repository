package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:唐豆收回
 * Description:访问前置
 * @author mouchunyue
 * @date 2016年9月9日 下午5:31:01
 */
@XStreamAlias("ROOT")
public class Out02209ReqBean extends OutReqBean{

	private Out02209ReqBodyBean BODY;

	public Out02209ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02209ReqBodyBean bODY) {
		BODY = bODY;
	}
	
	
}
