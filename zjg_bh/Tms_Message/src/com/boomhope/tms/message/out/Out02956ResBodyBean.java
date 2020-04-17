package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 权限明细查询【77015】(前置交易码02956)
 * 02956响应报文体Bean
 * @author wang.sk
 *
 */
@XStreamAlias("BODY")
public class Out02956ResBodyBean {
	private String FILE_NAME;	//输出文件
	private String SVR_DATE;	//核心日期

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
