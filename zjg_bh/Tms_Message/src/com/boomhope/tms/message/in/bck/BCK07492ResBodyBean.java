package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 转账客户列表信息查询-前置07492
 * BCK_07492响应报文体Bean
 * @author wang.sk
 *
 */
@XStreamAlias("Body")
public class BCK07492ResBodyBean {
	private String FILE_NAME;	//列表文件

	public String getFILE_NAME() {
		return FILE_NAME;
	}

	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}
	
}
