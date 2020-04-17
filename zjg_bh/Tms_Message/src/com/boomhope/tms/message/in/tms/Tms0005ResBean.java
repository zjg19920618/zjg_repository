package com.boomhope.tms.message.in.tms;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 凭证信息管理
 * @author zjg   
 * @date 2016年11月16日 上午9:55:51
 */
@XStreamAlias("Root")
public class Tms0005ResBean extends InResBean{
	Tms0005ResBodyBean BodyBean;

	public Tms0005ResBodyBean getBody() {
		return BodyBean;
	}

	public void setBody(Tms0005ResBodyBean bodyBean) {
		this.BodyBean = bodyBean;
	}
	
}
