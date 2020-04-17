package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:唐豆收回
 * Description:前置返回
 * @author mouchunyue
 * @date 2016年9月9日 下午5:54:36
 */
@XStreamAlias("ROOT")
public class Out01597ResBean extends OutResBean{

	private Out01597ResBodyBean BODY;

	public Out01597ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out01597ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
