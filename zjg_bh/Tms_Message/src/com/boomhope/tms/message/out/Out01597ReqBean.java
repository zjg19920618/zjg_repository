package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Out01597ReqBean 
* @Description   黑灰名单请求bean
* @author zh.m 
* @date 2016年12月2日 下午1:58:25  
*/
@XStreamAlias("ROOT")
public class Out01597ReqBean extends OutReqBean{

	private Out01597ReqBodyBean BODY;

	public Out01597ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out01597ReqBodyBean bODY) {
		BODY = bODY;
	}
	
	
}
