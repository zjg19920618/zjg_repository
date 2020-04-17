package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:个人账户销户
 * Description:前置返回
 * @author mouchunyue
 * @date 2016年9月9日 下午4:34:05
 */
@XStreamAlias("ROOT")
public class Out02026ResBean extends OutResBean{

	private Out02026ResBodyBean BODY;

	public Out02026ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02026ResBodyBean bODY) {
		BODY = bODY;
	}
	
	
}
