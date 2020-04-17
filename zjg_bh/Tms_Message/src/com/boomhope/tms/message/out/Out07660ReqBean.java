package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:柜员授权
 * Description:访问前置
 * @author zhang.m
 * @date 2016年9月9日 下午6:39:10
 */
@XStreamAlias("ROOT")
public class Out07660ReqBean extends OutReqBean{

	private Out07660ReqBodyBean BODY;

	public Out07660ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out07660ReqBodyBean bODY) {
		BODY = bODY;
	}
	
	
}
