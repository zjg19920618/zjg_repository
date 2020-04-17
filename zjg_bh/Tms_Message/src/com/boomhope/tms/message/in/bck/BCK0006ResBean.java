package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:唐豆收回
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午5:54:36
 */
@XStreamAlias("Root")
public class BCK0006ResBean extends InResBean{

	private BCK0006ResBodyBean Body;

	public BCK0006ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0006ResBodyBean body) {
		Body = body;
	}
}