package com.boomhope.tms.message.cdjmac;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  CdjOpenAccResBean
* @Description 获取存单电子签名等上传路径  
* @author zjg 
*/
@XStreamAlias("Root")
public class CdjOpenAccResBean extends InResBean{

	private CdjOpenAccResBodyBean Body;//存单机、存单回收机响应报文
	private InResponseBean response;//存单机、存单回收机响应结果

	public CdjOpenAccResBodyBean getBody() {
		return Body;
	}

	public void setBody(CdjOpenAccResBodyBean body) {
		this.Body = body;
	}

	public InResponseBean getResponse() {
		return response;
	}

	public void setResponse(InResponseBean response) {
		this.response = response;
	}
	
}
