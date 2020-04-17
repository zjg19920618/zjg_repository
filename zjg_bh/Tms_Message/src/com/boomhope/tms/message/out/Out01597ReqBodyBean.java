package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Out01597ReqBodyBean 
* @Description   黑灰名单请求Body
* @author zh.m 
* @date 2016年12月2日 下午1:59:05  
*/
@XStreamAlias("BODY")
public class Out01597ReqBodyBean {

	private String PROGRAM_FLAG;//程序标识 单元素100501/双元素100503
	private String ACC_NO1;//出账账号
	private String CARD_NO1;//出账卡号
	private String ACC_NAME1;//出账户名
	private String BANK_ID1;//出账支付行号
	private String ID_TYPE1;//出账客户证件类型
	private String ID_NUMBER1;//出账客户证件号码
	private String ID_NAME1;//出账客户证件姓名
	private String ACC_NO2;//入账账号
	private String CARD_NO2;//入账卡号
	private String ACC_NAME2;//入账户名
	private String BANK_ID2;//入账支付行号
	private String ID_TYPE2;//入账客户证件类型
	private String ID_NUMBER2;//入账客户证件号码
	private String ID_NAME2;//入账客户证件姓名
	private String CCY;//币种
	private String TRAN_AMT;//交易金额
	private String MEMO1;//预留
	private String MEMO2;//预留
	private String MEMO3;//预留
	private String MEMO4;//预留
	public String getPROGRAM_FLAG() {
		return PROGRAM_FLAG;
	}
	public void setPROGRAM_FLAG(String pROGRAM_FLAG) {
		PROGRAM_FLAG = pROGRAM_FLAG;
	}
	public String getACC_NO1() {
		return ACC_NO1;
	}
	public void setACC_NO1(String aCC_NO1) {
		ACC_NO1 = aCC_NO1;
	}
	public String getCARD_NO1() {
		return CARD_NO1;
	}
	public void setCARD_NO1(String cARD_NO1) {
		CARD_NO1 = cARD_NO1;
	}
	public String getACC_NAME1() {
		return ACC_NAME1;
	}
	public void setACC_NAME1(String aCC_NAME1) {
		ACC_NAME1 = aCC_NAME1;
	}
	public String getBANK_ID1() {
		return BANK_ID1;
	}
	public void setBANK_ID1(String bANK_ID1) {
		BANK_ID1 = bANK_ID1;
	}
	public String getID_TYPE1() {
		return ID_TYPE1;
	}
	public void setID_TYPE1(String iD_TYPE1) {
		ID_TYPE1 = iD_TYPE1;
	}
	public String getID_NUMBER1() {
		return ID_NUMBER1;
	}
	public void setID_NUMBER1(String iD_NUMBER1) {
		ID_NUMBER1 = iD_NUMBER1;
	}
	public String getID_NAME1() {
		return ID_NAME1;
	}
	public void setID_NAME1(String iD_NAME1) {
		ID_NAME1 = iD_NAME1;
	}
	public String getACC_NO2() {
		return ACC_NO2;
	}
	public void setACC_NO2(String aCC_NO2) {
		ACC_NO2 = aCC_NO2;
	}
	public String getCARD_NO2() {
		return CARD_NO2;
	}
	public void setCARD_NO2(String cARD_NO2) {
		CARD_NO2 = cARD_NO2;
	}
	public String getACC_NAME2() {
		return ACC_NAME2;
	}
	public void setACC_NAME2(String aCC_NAME2) {
		ACC_NAME2 = aCC_NAME2;
	}
	public String getBANK_ID2() {
		return BANK_ID2;
	}
	public void setBANK_ID2(String bANK_ID2) {
		BANK_ID2 = bANK_ID2;
	}
	public String getID_TYPE2() {
		return ID_TYPE2;
	}
	public void setID_TYPE2(String iD_TYPE2) {
		ID_TYPE2 = iD_TYPE2;
	}
	public String getID_NUMBER2() {
		return ID_NUMBER2;
	}
	public void setID_NUMBER2(String iD_NUMBER2) {
		ID_NUMBER2 = iD_NUMBER2;
	}
	public String getID_NAME2() {
		return ID_NAME2;
	}
	public void setID_NAME2(String iD_NAME2) {
		ID_NAME2 = iD_NAME2;
	}
	public String getCCY() {
		return CCY;
	}
	public void setCCY(String cCY) {
		CCY = cCY;
	}
	public String getTRAN_AMT() {
		return TRAN_AMT;
	}
	public void setTRAN_AMT(String tRAN_AMT) {
		TRAN_AMT = tRAN_AMT;
	}
	public String getMEMO1() {
		return MEMO1;
	}
	public void setMEMO1(String mEMO1) {
		MEMO1 = mEMO1;
	}
	public String getMEMO2() {
		return MEMO2;
	}
	public void setMEMO2(String mEMO2) {
		MEMO2 = mEMO2;
	}
	public String getMEMO3() {
		return MEMO3;
	}
	public void setMEMO3(String mEMO3) {
		MEMO3 = mEMO3;
	}
	public String getMEMO4() {
		return MEMO4;
	}
	public void setMEMO4(String mEMO4) {
		MEMO4 = mEMO4;
	}
	
}
