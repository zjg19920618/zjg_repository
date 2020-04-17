package com.boomhope.tms.message.in.tms;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * @Description: 管理员登录校验
 * @author zjg   
 * @date 2016年11月16日 上午9:49:39
 */
@XStreamAlias("Root")  
public class Tms0004ReqBean extends InReqBean{
	Tms0004ReqBodyBean bodyBean;

	public Tms0004ReqBodyBean getBodyBean() {
		return bodyBean;
	}

	public void setBodyBean(Tms0004ReqBodyBean bodyBean) {
		this.bodyBean = bodyBean;
	}
	
}
