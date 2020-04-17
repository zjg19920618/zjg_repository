package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:柜员认证方式查询
 * Description:访问前置
 * @author zhang.m
 * @date 2016年9月9日 下午4:12:57
 */
@XStreamAlias("Root")
public class BCK0009ReqBean extends InReqBean {
	private BCK0009ReqBodyBean Body;

	public BCK0009ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0009ReqBodyBean body) {
		Body = body;
	}
	
}
