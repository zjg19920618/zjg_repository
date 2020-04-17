package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:普通定期存单预计利息
 * Description:
 * @author zjg
 * @date 2017年07月04日 下午2:21:44
 */
@XStreamAlias("Root")
public class BCK07696ResBean extends InResBean {

	private BCK07696ResBodyBean Body;

	public BCK07696ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07696ResBodyBean body) {
		Body = body;
	}
	
}
