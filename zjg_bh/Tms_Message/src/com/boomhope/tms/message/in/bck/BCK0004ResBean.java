package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:个人账户销户
 * Description:
 * @author mouchunyue
 * @date 2016年9月12日 下午6:16:10
 */
@XStreamAlias("Root")
public class BCK0004ResBean extends InResBean{

	private BCK0004ResBodyBean Body;

	public BCK0004ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0004ResBodyBean body) {
		Body = body;
	}
	
}
