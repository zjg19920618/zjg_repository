package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *根据客户号查询所有卡号信息【90001】-前置07520
 * @author hk
 *
 */
@XStreamAlias("Body")  
public class BCK07520ResBodyBean {
	
	private String FILE_NAME;//文件
	
	public String getFILE_NAME() {
		return FILE_NAME;
	}
	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}

	
}
