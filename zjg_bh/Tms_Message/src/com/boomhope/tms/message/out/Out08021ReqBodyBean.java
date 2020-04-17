package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:代理人身份信息核对
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午6:46:03
 */
@XStreamAlias("BODY")
public class Out08021ReqBodyBean {

	private String NAME;//姓名
	private String ID_NO;//证件号码
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getID_NO() {
		return ID_NO;
	}
	public void setID_NO(String iD_NO) {
		ID_NO = iD_NO;
	}
	
}
