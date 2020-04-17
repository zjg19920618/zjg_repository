package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:代理人身份信息核对
 * Description:
 * @author zjg
 * @date 2017年07月04日 下午6:44:49
 */
@XStreamAlias("Body")
public class BCK08021ReqBodyBean {

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
