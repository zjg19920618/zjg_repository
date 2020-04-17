package com.boomhope.tms.message.in.tms;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 凭证信息管理
 * @author zjg   
 * @date 2016年11月16日 上午9:55:51
 */
@XStreamAlias("Body")
public class Tms0005ReqBodyBean {
	private String ISNo; //标识
	private String ID; //凭证号ID
	private String MACHINE_NO; //机器编号
	private String START_NO; //起始凭证 号
	private String END_NO;	//结束凭证号
	private String NOW_BO; //当前凭证号
	private String UPDATE_DATE; //更新时间
	private String CREATE_DATE; //创建时间
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getMACHINE_NO() {
		return MACHINE_NO;
	}
	public void setMACHINE_NO(String mACHINE_NO) {
		MACHINE_NO = mACHINE_NO;
	}
	public String getSTART_NO() {
		return START_NO;
	}
	public void setSTART_NO(String sTART_NO) {
		START_NO = sTART_NO;
	}
	public String getEND_NO() {
		return END_NO;
	}
	public void setEND_NO(String eND_NO) {
		END_NO = eND_NO;
	}
	public String getNOW_BO() {
		return NOW_BO;
	}
	public void setNOW_BO(String nOW_BO) {
		NOW_BO = nOW_BO;
	}
	public String getUPDATE_DATE() {
		return UPDATE_DATE;
	}
	public void setUPDATE_DATE(String uPDATE_DATE) {
		UPDATE_DATE = uPDATE_DATE;
	}
	public String getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(String cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}
	public String getISNo() {
		return ISNo;
	}
	public void setISNo(String iSNo) {
		ISNo = iSNo;
	}
	
}
