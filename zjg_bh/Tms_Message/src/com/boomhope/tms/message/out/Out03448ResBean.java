package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:身份核查
 * Description:前置返回
 * @author mouchunyue
 * @date 2016年9月9日 下午6:34:02
 */
@XStreamAlias("ROOT")
public class Out03448ResBean extends OutResBean{

	private Out03448ResBodyBean BODY;

	public Out03448ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03448ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
