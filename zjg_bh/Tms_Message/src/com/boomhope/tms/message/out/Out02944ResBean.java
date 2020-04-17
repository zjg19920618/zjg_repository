package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:普通定期存单预计利息
 * Description:前置返回
 * @author mouchunyue
 * @date 2016年9月9日 下午4:06:35
 */
@XStreamAlias("ROOT")
public class Out02944ResBean extends OutResBean{

	private Out02944ResBodyBean BODY;

	public Out02944ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02944ResBodyBean bODY) {
		BODY = bODY;
	}
	
	
}
