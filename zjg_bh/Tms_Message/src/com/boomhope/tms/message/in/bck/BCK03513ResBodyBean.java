package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 4.13待打印存单查询【02946】前置03513
 * 
 * @author zhang.jg
 *
 */
@XStreamAlias("Body")
public class BCK03513ResBodyBean {
	private String FILE_NAME;// 查询结果(文件)

	public String getFILE_NAME() {
		return FILE_NAME;
	}

	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}

}
