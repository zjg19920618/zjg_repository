package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:柜员认证方式查询
 * Description:前置返回
 * @author zhang.m
 * @date 2016年9月9日 下午4:34:05
 */

@XStreamAlias("Root")
public class BCK0009ResBean extends InResBean {
	
	private BCK0009ResBodyBean Body;

	public BCK0009ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0009ResBodyBean body) {
		Body = body;
	}
	
}
