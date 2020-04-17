package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 待打印存单查询【02948】前置03518
 * @author zjg
 * @date 2016年11月7日 上午10:32:40
 */
@XStreamAlias("BODY")
public class Out03518ResBodyBean {
	private String FILE_NAME;// 查询结果(文件)

	public String getFILE_NAME() {
		return FILE_NAME;
	}

	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}

}
