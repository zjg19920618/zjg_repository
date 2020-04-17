package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *根据客户号查询所有卡号信息【90001】-前置07520
 * @author hk
 *
 */
@XStreamAlias("BODY")  
public class Out07520ResBodyBean {
	
	private String FILE_NAME;//文件
	public String getFILE_NAME() {
		return FILE_NAME;
	}
	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}
}
