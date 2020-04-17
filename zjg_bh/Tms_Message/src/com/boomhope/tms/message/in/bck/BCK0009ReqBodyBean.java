package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:柜员认证方式查询
 * Description:
 * @author zhang.m
 * @date 2016年9月9日 下午4:30:48
 */
@XStreamAlias("Body")
public class BCK0009ReqBodyBean {
	private String QRY_TELLER_NO;//查询柜员号
	
	public String getQRY_TELLER_NO() {
		return QRY_TELLER_NO;
	}

	public void setQRY_TELLER_NO(String qRY_TELLER_NO) {
		QRY_TELLER_NO = qRY_TELLER_NO;
	}
	
	
}
