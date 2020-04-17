package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:单条短信发送 【前置-07190】
 * Description:访问前置
 * @author zhang.m
 * @date 2016年10月20日 下午14:51:21
 *
 */
@XStreamAlias("ROOT")
public class Out07190ReqBean extends OutReqBean {
	private Out07190ReqBodyBean BODY;

	public Out07190ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out07190ReqBodyBean bODY) {
		BODY = bODY;
	}
}
