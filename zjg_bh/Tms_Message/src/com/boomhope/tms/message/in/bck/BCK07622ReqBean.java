package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:唐豆收回查询
 * Description:
 * @author zjg
 * @date 2017年07月04日 下午3:23:47
 */
@XStreamAlias("Root")
public class BCK07622ReqBean extends InReqBean{

	private BCK07622ReqBodyBean Body;

	public BCK07622ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07622ReqBodyBean body) {
		Body = body;
	}

}
