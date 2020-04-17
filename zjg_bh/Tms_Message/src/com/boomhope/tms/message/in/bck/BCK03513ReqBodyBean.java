package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 待打印存单查询【02946】前置03513
 * @author zjg
 * @date 2016年11月7日 上午10:19:32
 */
@XStreamAlias("Body")
public class BCK03513ReqBodyBean {
	private String ID_TYPE;// 证件类型
	private String ID_NO;// 证件号
	private String OPER_CHOOSE;// 操作选择

	public String getID_TYPE() {
		return ID_TYPE;
	}

	public void setID_TYPE(String iD_TYPE) {
		ID_TYPE = iD_TYPE;
	}

	public String getID_NO() {
		return ID_NO;
	}

	public void setID_NO(String iD_NO) {
		ID_NO = iD_NO;
	}

	public String getOPER_CHOOSE() {
		return OPER_CHOOSE;
	}

	public void setOPER_CHOOSE(String oPER_CHOOSE) {
		OPER_CHOOSE = oPER_CHOOSE;
	}

}
