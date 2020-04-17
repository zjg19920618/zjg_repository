package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:唐豆收回
 * Description:
 * @author zjg
 * @date 2017年07月04日 下午5:59:19
 */
@XStreamAlias("Root")
public class BCK07665ResBean extends InResBean{

	private BCK07665ResBodyBean Body;

	public BCK07665ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07665ResBodyBean body) {
		Body = body;
	}
}