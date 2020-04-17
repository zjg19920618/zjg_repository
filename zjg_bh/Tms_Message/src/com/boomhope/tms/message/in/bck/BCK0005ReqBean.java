package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:唐豆收回查询
 * Description:
 * @author mouchunyue
 * @date 2016年9月12日 下午3:23:47
 */
@XStreamAlias("Root")
public class BCK0005ReqBean extends InReqBean{

	private BCK0005ReqBodyBean Body;

	public BCK0005ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0005ReqBodyBean body) {
		Body = body;
	}

}
