package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:柜员授权
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午6:40:08
 */
@XStreamAlias("Body")
public class BCK0010ReqBodyBean {
	private String SUP_TELLER_NO;//授权柜员号
	private String SUP_TELLER_PWD;//授权密码
	private String FIN_PRI_LEN;//指纹长度
	private String FIN_PRI_VAL;//指纹值
	
	public String getSUP_TELLER_NO() {
		return SUP_TELLER_NO;
	}

	public void setSUP_TELLER_NO(String sUP_TELLER_NO) {
		SUP_TELLER_NO = sUP_TELLER_NO;
	}

	public String getSUP_TELLER_PWD() {
		return SUP_TELLER_PWD;
	}

	public void setSUP_TELLER_PWD(String sUP_TELLER_PWD) {
		SUP_TELLER_PWD = sUP_TELLER_PWD;
	}

	public String getFIN_PRI_LEN() {
		return FIN_PRI_LEN;
	}

	public void setFIN_PRI_LEN(String fIN_PRI_LEN) {
		FIN_PRI_LEN = fIN_PRI_LEN;
	}

	public String getFIN_PRI_VAL() {
		return FIN_PRI_VAL;
	}

	public void setFIN_PRI_VAL(String fIN_PRI_VAL) {
		FIN_PRI_VAL = fIN_PRI_VAL;
	}
	
}
