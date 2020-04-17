package com.boomhope.tms.message.account.in;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03870ResBean 
* @Description "积享存"个人存款开户【75100】前置【03870】   
* @author zhang.m 
* @date 2016年12月5日 下午6:27:06  
*/
@XStreamAlias("Root")
public class BCK03870ResBean extends InResBean {
	private BCK03870ResBodyBean Body;

	public BCK03870ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03870ResBodyBean body) {
		Body = body;
	}

}
