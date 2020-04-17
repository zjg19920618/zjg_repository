package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:唐豆收回查询
 * Description:
 * @author mouchunyue
 * @date 2016年9月12日 下午3:24:03
 */
@XStreamAlias("Root")
public class BCK0005ResBean extends InResBean{

	private BCK0005ResBodyBean Body;

	public BCK0005ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0005ResBodyBean body) {
		Body = body;
	}

}
