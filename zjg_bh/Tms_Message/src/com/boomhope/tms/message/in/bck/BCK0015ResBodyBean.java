package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 4.13待打印存单查询【02948】前置03518
 * @author zjg   
 * @date 2016年11月8日 下午5:49:07
 */
@XStreamAlias("Body")
public class BCK0015ResBodyBean {
	private String FILE_NAME;// 查询结果(文件)

	public String getFILE_NAME() {
		return FILE_NAME;
	}

	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}

}
