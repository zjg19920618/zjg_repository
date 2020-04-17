package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:凭证信息综合查询BODY实体类
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午3:28:39
 */
@XStreamAlias("BODY")
public class Out02882ReqBodyBean {

	private String CERT_NO;//账号
	private String CERT_TYPE;//子账号
	public String getCERT_NO() {
		return CERT_NO;
	}
	public void setCERT_NO(String cERT_NO) {
		CERT_NO = cERT_NO;
	}
	public String getCERT_TYPE() {
		return CERT_TYPE;
	}
	public void setCERT_TYPE(String cERT_TYPE) {
		CERT_TYPE = cERT_TYPE;
	}
	
	
}
