package com.boomhope.tms.message.in.tms;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 凭证信息管理
 * @author zjg   
 * @date 2016年11月16日 上午9:55:51
 */
@XStreamAlias("Root")
public class Tms0005ReqBean extends InReqBean{
	Tms0005ReqBodyBean bodyBean;

	public Tms0005ReqBodyBean getBodyBean() {
		return bodyBean;
	}

	public void setBodyBean(Tms0005ReqBodyBean bodyBean) {
		this.bodyBean = bodyBean;
	}
	
}
