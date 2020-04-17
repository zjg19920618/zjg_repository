package com.boomhope.tms.message.cdjmac;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  CdjOpenAccReqBean
* @Description 获取存单电子签名等上传路径  
* @author zjg 
*/
@XStreamAlias("Root")
public class CdjOpenAccReqBean extends InReqBean{

	private CdjOpenAccReqBodyBean Body;//存单机请求报文

	public CdjOpenAccReqBodyBean getBody() {
		return Body;
	}

	public void setBody(CdjOpenAccReqBodyBean body) {
		this.Body = body;
	}
	
}
