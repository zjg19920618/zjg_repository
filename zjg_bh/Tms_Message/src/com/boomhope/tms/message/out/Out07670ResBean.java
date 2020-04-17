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
public class Out07670ResBean extends OutResBean{

	private Out07670ResBodyBean BODY;

	public Out07670ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out07670ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
