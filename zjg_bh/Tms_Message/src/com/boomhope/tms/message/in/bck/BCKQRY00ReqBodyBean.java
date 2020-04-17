package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *前置系统参数查询-前置QRY00
 * @author zjg
 *
 */
@XStreamAlias("Body")  
public class BCKQRY00ReqBodyBean {
	
	private String PARAM_CODE;//查询参数代码

	public String getPARAM_CODE() {
		return PARAM_CODE;
	}

	public void setPARAM_CODE(String pARAM_CODE) {
		PARAM_CODE = pARAM_CODE;
	}
	
}
