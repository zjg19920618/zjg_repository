package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:柜员认证方式查询
 * Description:访问前置
 * @author zhang.m
 * @date 2016年9月9日 下午4:12:57
 */
@XStreamAlias("ROOT")
public class Out07659ReqBean extends OutReqBean {
	private Out07659ReqBodyBean BODY;

	public Out07659ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out07659ReqBodyBean bODY) {
		BODY = bODY;
	}
	
	
}
