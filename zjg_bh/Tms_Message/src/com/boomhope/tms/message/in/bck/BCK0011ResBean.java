package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:代理人身份信息核对
 * Description:前置返回
 * @author mouchunyue
 * @date 2016年9月9日 下午6:47:35
 */
@XStreamAlias("Root")
public class BCK0011ResBean extends InResBean{

	private BCK0011ResBodyBean Body;

	public BCK0011ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0011ResBodyBean body) {
		Body = body;
	}
	
}
