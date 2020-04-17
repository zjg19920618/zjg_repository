package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 子账户列表查询-【75109】前置03519
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("Body")
public class BCK0018ResBodyBean {
	
	private String FILE_NAME; //账户明细文件
	private String SVR_DATE;//日期
	public String getFILE_NAME() {
		return FILE_NAME;
	}
	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}
	public String getSVR_DATE() {
		return SVR_DATE;
	}
	public void setSVR_DATE(String sVR_DATE) {
		SVR_DATE = sVR_DATE;
	}
	
	
}
