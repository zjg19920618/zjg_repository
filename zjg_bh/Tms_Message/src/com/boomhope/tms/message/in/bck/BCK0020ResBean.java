package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:产品利率信息查询--02864
 * Description:
 * @author mouchunyue
 * @date 2016年11月26日 下午3:49:15
 */
@XStreamAlias("Root")
public class BCK0020ResBean extends InResBean{
	
	private BCK0020ResBodyBean Body;

	public BCK0020ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0020ResBodyBean body) {
		Body = body;
	}
	
}
