package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:柜员授权
 * Description:前置返回
 * @author zhang.m
 * @date 2016年9月9日 下午6:34:02
 */
@XStreamAlias("ROOT")
public class Out07660ResBean extends OutResBean {
	private Out07660ResBodyBean BODY;

	public Out07660ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out07660ResBodyBean bODY) {
		BODY = bODY;
	}
}
