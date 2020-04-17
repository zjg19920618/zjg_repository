package com.boomhope.tms.message.cdjmac;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  CdjOpenAccResBodyBean
* @Description 获取存单电子签名等上传路径  
* @author zjg 
*/
@XStreamAlias("Body")
public class CdjOpenAccResBodyBean {
	
	private String uploadPath;//电子签名等上传路径

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	
}
