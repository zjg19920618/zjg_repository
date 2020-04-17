package com.boomhope.tms.message.account.in;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:产品利率信息查询--02867
 * Description:
 * @author mouchunyue
 * @date 2016年12月3日 下午2:41:38
 */
@XStreamAlias("Body")
public class BCK02867ReqBodyBean {

	private String QRY_TYPE;//查询类型
	private String PRODUCT_CODE;//产品代码
	private String CUST_NO;//客户号
	private String CHL_ID;//渠道模块标识
	private String DEP_AMT;//最低起存
	private String MAX_AMT;//最高起存
	private String CUST_LEVEL;//客户评级
	
	
	public String getCUST_LEVEL() {
		return CUST_LEVEL;
	}
	public void setCUST_LEVEL(String cUST_LEVEL) {
		CUST_LEVEL = cUST_LEVEL;
	}
	public String getDEP_AMT() {
		return DEP_AMT;
	}
	public void setDEP_AMT(String dEP_AMT) {
		DEP_AMT = dEP_AMT;
	}
	public String getMAX_AMT() {
		return MAX_AMT;
	}
	public void setMAX_AMT(String mAX_AMT) {
		MAX_AMT = mAX_AMT;
	}
	public String getQRY_TYPE() {
		return QRY_TYPE;
	}
	public void setQRY_TYPE(String qRY_TYPE) {
		QRY_TYPE = qRY_TYPE;
	}
	public String getPRODUCT_CODE() {
		return PRODUCT_CODE;
	}
	public void setPRODUCT_CODE(String pRODUCT_CODE) {
		PRODUCT_CODE = pRODUCT_CODE;
	}
	public String getCUST_NO() {
		return CUST_NO;
	}
	public void setCUST_NO(String cUST_NO) {
		CUST_NO = cUST_NO;
	}
	public String getCHL_ID() {
		return CHL_ID;
	}
	public void setCHL_ID(String cHL_ID) {
		CHL_ID = cHL_ID;
	}
	
}
