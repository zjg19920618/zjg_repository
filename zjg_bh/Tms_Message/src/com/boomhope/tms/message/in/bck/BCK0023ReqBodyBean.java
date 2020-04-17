package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:产品利率信息查询【02077】-前置07666
 * Description:访问前置
 * @author mouchunyue
 * @date 2016年9月9日 下午6:44:49
 */
@XStreamAlias("Body")
public class BCK0023ReqBodyBean {
	private String PRO_CODE;//产品代码
	private String RATE_DATE;//利率日期
	private String CHL_NO;//渠道号
	private String CHL_ID;//渠道模块标识
	public String getPRO_CODE() {
		return PRO_CODE;
	}
	public void setPRO_CODE(String pRO_CODE) {
		PRO_CODE = pRO_CODE;
	}
	public String getRATE_DATE() {
		return RATE_DATE;
	}
	public void setRATE_DATE(String rATE_DATE) {
		RATE_DATE = rATE_DATE;
	}
	public String getCHL_NO() {
		return CHL_NO;
	}
	public void setCHL_NO(String cHL_NO) {
		CHL_NO = cHL_NO;
	}
	public String getCHL_ID() {
		return CHL_ID;
	}
	public void setCHL_ID(String cHL_ID) {
		CHL_ID = cHL_ID;
	}
	
}
