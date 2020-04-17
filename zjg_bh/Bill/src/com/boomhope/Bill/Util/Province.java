package com.boomhope.Bill.Util;
/**
 * 省份Bean
 * @author hao
 *
 */
public class Province {
	private String proId;
	private String ProName;
	private String JdCode;
	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	public String getProName() {
		return ProName;
	}
	public void setProName(String proName) {
		ProName = proName;
	}
	public String getJdCode() {
		return JdCode;
	}
	public void setJdCode(String jdCode) {
		JdCode = jdCode;
	}
	@Override
	public String toString() {
		return "Province [proId=" + proId + ", ProName=" + ProName
				+ ", JdCode=" + JdCode + "]";
	}
	
	
}
