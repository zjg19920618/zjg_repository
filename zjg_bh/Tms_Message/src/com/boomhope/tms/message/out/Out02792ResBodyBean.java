package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 待打印存单查询【02948】前置02792
 * @author zhang.jg
 *
 */
@XStreamAlias("BODY")
public class Out02792ResBodyBean {
	private String FILE_NAME;// 查询结果(文件)

	public String getFILE_NAME() {
		return FILE_NAME;
	}

	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}

}
