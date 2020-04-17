package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:普通定期存单预计利息
 * Description:
 * @author mouchunyue
 * @date 2016年9月12日 下午2:21:23
 */
@XStreamAlias("Root")
public class BCK0003ResBean extends InResBean {

	private BCK0003ResBodyBean Body;

	public BCK0003ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0003ResBodyBean body) {
		Body = body;
	}
	
}
