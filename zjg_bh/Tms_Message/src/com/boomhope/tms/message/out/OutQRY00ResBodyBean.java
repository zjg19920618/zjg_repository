package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *前置系统参数查询-前置QRY00
 * @author zjg
 *
 */
@XStreamAlias("BODY")  
public class OutQRY00ResBodyBean {
	
	private String PARAM_VALUE;//参数值

	public String getPARAM_VALUE() {
		return PARAM_VALUE;
	}

	public void setPARAM_VALUE(String pARAM_VALUE) {
		PARAM_VALUE = pARAM_VALUE;
	}

	
}
