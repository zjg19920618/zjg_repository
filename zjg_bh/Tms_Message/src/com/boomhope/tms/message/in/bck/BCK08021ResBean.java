package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:代理人身份信息核对
 * Description:
 * @author zjg
 * @date 2017年07月04日 下午6:44:49
 */
@XStreamAlias("Root")
public class BCK08021ResBean extends InResBean{

	private BCK08021ResBodyBean Body;

	public BCK08021ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK08021ResBodyBean body) {
		Body = body;
	}
	
}
