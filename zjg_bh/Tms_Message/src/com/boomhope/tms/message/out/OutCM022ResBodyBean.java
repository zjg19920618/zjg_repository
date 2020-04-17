package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *定时发送交易信息查询-前置CM022
 * @author zjg
 *
 */
@XStreamAlias("BODY")  
public class OutCM022ResBodyBean {
	
	private String FILE_NAME;//文件名

	public String getFILE_NAME() {
		return FILE_NAME;
	}

	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}
	
}
