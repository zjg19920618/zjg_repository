package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:客户手机绑定信息查询【前置-07491】
 * Description:访问前置
 * @author zhang.m
 * @date 2016年10月20日 下午14:39:38
 *
 */
@XStreamAlias("ROOT")
public class Out07491ReqBean extends OutReqBean {
	
	private Out07491ReqBodyBean BODY;

	public Out07491ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out07491ReqBodyBean bODY) {
		BODY = bODY;
	}
}
