package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:客户手机绑定信息查询【前置-07491】
 * Description:前置返回
 * @author zhang.m
 * @date 2016年10月20日 下午14:47:13
 */
@XStreamAlias("ROOT")
public class Out07491ResBean extends OutResBean{

	private Out07491ResBodyBean BODY;

	public Out07491ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out07491ResBodyBean bODY) {
		BODY = bODY;
	}
}
