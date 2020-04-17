package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:根据机构号查询支付行信息-前置01118
 * @author hk
 */
@XStreamAlias("Body")
public class BCK01118ReqBodyBean {

	private String SCH_INST_NO;//机构号
	

	public String getSCH_INST_NO() {
		return SCH_INST_NO;
	}

	public void setSCH_INST_NO(String sCH_INST_NO) {
		SCH_INST_NO = sCH_INST_NO;
	}
	
	
	
}
