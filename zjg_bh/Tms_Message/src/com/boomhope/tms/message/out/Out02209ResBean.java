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
public class Out02209ResBean extends OutResBean{

	private Out02209ResBodyBean BODY;

	public Out02209ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02209ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
