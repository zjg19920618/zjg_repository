package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:唐豆收回查询
 * Description:
 * @author zjg
 * @date 2017年07月04日 下午3:23:47
 */
@XStreamAlias("Root")
public class BCK07622ResBean extends InResBean{

	private BCK07622ResBodyBean Body;

	public BCK07622ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07622ResBodyBean body) {
		Body = body;
	}

}
