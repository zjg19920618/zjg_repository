package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:凭证信息综合查询BODY
 * Description:
 * @author mouchunyue
 * @date 2016年9月12日 下午4:31:24
 */
@XStreamAlias("Body")
public class BCK0014ReqBodyBean {

	private String CertNo;//凭证号
	private String CertType;//凭证种类
	public String getCertNo() {
		return CertNo;
	}
	public void setCertNo(String certNo) {
		CertNo = certNo;
	}
	public String getCertType() {
		return CertType;
	}
	public void setCertType(String certType) {
		CertType = certType;
	}
	
}
