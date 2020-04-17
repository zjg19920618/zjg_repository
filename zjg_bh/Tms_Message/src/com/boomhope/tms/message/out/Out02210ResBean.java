package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:唐豆收回查询
 * Description:前置返回
 * @author mouchunyue
 * @date 2016年9月9日 下午4:57:07
 */
@XStreamAlias("ROOT")
public class Out02210ResBean extends OutResBean{

	private Out02210ResBodyBean BODY;

	public Out02210ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02210ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
