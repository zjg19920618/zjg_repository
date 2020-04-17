package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 权限明细查询【78010】(前置交易码03740)
 * 03740响应报文
 * @author zjg
 *
 */
@XStreamAlias("Body")
public class BCK03740ResBodyBean {
	private String FILE_NAME;	//输出文件
	private String SVR_DATE;	//核心日期
	private String YDZC_FLAG;	//约定转存标志
	private String ZZSBZZ_FLAG;	//自助设备转账标志
	private String DAY_LIMIT_AMT;	//日累计限额
	private String DAY_LIMIT_NUM;	//日累计笔数
	private String YEAR_LIMIT_AMT;	//年累计限额
	private String XFDXF_FLAG;//消费豆消费标志
	private String JFXF_FLAG;//积分消费标志

	public String getFILE_NAME() {
		return FILE_NAME;
	}

	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}

	public String getSVR_DATE() {
		return SVR_DATE;
	}

	public void setSVR_DATE(String sVR_DATE) {
		SVR_DATE = sVR_DATE;
	}

	public String getYDZC_FLAG() {
		return YDZC_FLAG;
	}

	public void setYDZC_FLAG(String yDZC_FLAG) {
		YDZC_FLAG = yDZC_FLAG;
	}

	public String getZZSBZZ_FLAG() {
		return ZZSBZZ_FLAG;
	}

	public void setZZSBZZ_FLAG(String zZSBZZ_FLAG) {
		ZZSBZZ_FLAG = zZSBZZ_FLAG;
	}

	public String getDAY_LIMIT_AMT() {
		return DAY_LIMIT_AMT;
	}

	public void setDAY_LIMIT_AMT(String dAY_LIMIT_AMT) {
		DAY_LIMIT_AMT = dAY_LIMIT_AMT;
	}

	public String getDAY_LIMIT_NUM() {
		return DAY_LIMIT_NUM;
	}

	public void setDAY_LIMIT_NUM(String dAY_LIMIT_NUM) {
		DAY_LIMIT_NUM = dAY_LIMIT_NUM;
	}

	public String getYEAR_LIMIT_AMT() {
		return YEAR_LIMIT_AMT;
	}

	public void setYEAR_LIMIT_AMT(String yEAR_LIMIT_AMT) {
		YEAR_LIMIT_AMT = yEAR_LIMIT_AMT;
	}

	public String getXFDXF_FLAG() {
		return XFDXF_FLAG;
	}

	public void setXFDXF_FLAG(String xFDXF_FLAG) {
		XFDXF_FLAG = xFDXF_FLAG;
	}

	public String getJFXF_FLAG() {
		return JFXF_FLAG;
	}

	public void setJFXF_FLAG(String jFXF_FLAG) {
		JFXF_FLAG = jFXF_FLAG;
	}
	
}
