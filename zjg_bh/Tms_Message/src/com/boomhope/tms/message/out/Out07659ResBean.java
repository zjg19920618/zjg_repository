package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:柜员认证方式查询
 * Description:前置返回
 * @author zhang.m
 * @date 2016年9月9日 下午4:34:05
 */

@XStreamAlias("ROOT")
public class Out07659ResBean extends OutResBean {
	
	private Out07659ResBodyBean BODY;

	public Out07659ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out07659ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
