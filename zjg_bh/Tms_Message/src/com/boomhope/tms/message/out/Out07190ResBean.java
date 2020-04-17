package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:单条短信发送 【前置-07190】
 * Description:前置返回
 * @author zhang.m
 * @date 2016年10月20日 下午15:04:21
 *
 */
@XStreamAlias("ROOT")
public class Out07190ResBean extends OutResBean {
	private Out07190ResBodyBean BODY;

	public Out07190ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out07190ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
