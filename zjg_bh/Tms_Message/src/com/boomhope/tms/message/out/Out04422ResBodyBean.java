package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 客户基本信息查询 04422
 * @author hao
 *
 */
@XStreamAlias("BODY")
public class Out04422ResBodyBean {
	private String ECIF_CUST_NO;//客户编号
	private String PARTY_NAME;//客户名称
	private String CHN_NAME;//拼音姓名
	private String CERT_TYPE;//证件类型
	private String CERT_NO;//证件号码
	private String GENDER;//性别
	private String COUNTRY_CODE;//国别代码
	private String JOB_PRFSSN;//职业
	private String CERT_EXPD_DATE;//证件到期日
	private String CERT_VALID_DATE;//证件生效日期
	private String ISSUED_INS;//发证机关所在地
	private String CUST_LEVEL;//客户等级
	private String REC_PRODUCT;//推荐产品
	private String IS_LOCBANKEMP;//是否为本行员工
	private String LOC_BANKS_HOLDER_FLAG;//是否我行股东
	private String PHONE_NO;//固话
	private String MOBILE_PHONE;//手机
	private String ADDR_LINE;//地址
	private String NINE_ITEM_COMPLETED_FLAG;//九项是否完整 1完整2不完整 3无关
	private String SIMILAR_INFO_FLAG;//是否相似客户 1是2不是
	private String CERTNO_LEGAL_FALG;//身份证号码是否合法 1合法2非法 3无关
	private String MONTH_INCOME;//个人月收入
	private String BIRTH_DATE;//出生日期
	private String NATION_CODE;//民族
	private String VISA_TYPE;//签证类型
	private String VISA_EXPD_DATE;//签证到期日期
	private String JOB_PRFSSN_DES;//职业描述
	private String MAIN_ECON_RESUR_DES;//经济来源（经营项目）描述
	private String IS_EXT_MSG;//是否录入附加信息
	private String SOCIAL_CONTEXT;//社会背景说明
	private String OPEN_PURPOSE;//开户目的
	private String FUNDS_PROVIDED;//资金来源
	private String TRANSACTION_SCALE;//预期交易规模
	private String PRODUCTS_SERVICES;//拟使用产品和服务
	private String RELATION_COUNTRY;//交易涉及国家
	private String NEGATIVE_NEWS;//负面媒体报道情况
	private String IS_NARCOTICS;//是否涉恐、涉毒
	private String INFO_QUALITY_FLAG;//客户信息质量标志
	public String getECIF_CUST_NO() {
		return ECIF_CUST_NO;
	}
	public void setECIF_CUST_NO(String eCIF_CUST_NO) {
		ECIF_CUST_NO = eCIF_CUST_NO;
	}
	public String getPARTY_NAME() {
		return PARTY_NAME;
	}
	public void setPARTY_NAME(String pARTY_NAME) {
		PARTY_NAME = pARTY_NAME;
	}
	public String getCHN_NAME() {
		return CHN_NAME;
	}
	public void setCHN_NAME(String cHN_NAME) {
		CHN_NAME = cHN_NAME;
	}
	public String getCERT_TYPE() {
		return CERT_TYPE;
	}
	public void setCERT_TYPE(String cERT_TYPE) {
		CERT_TYPE = cERT_TYPE;
	}
	public String getCERT_NO() {
		return CERT_NO;
	}
	public void setCERT_NO(String cERT_NO) {
		CERT_NO = cERT_NO;
	}
	public String getGENDER() {
		return GENDER;
	}
	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}
	public String getCOUNTRY_CODE() {
		return COUNTRY_CODE;
	}
	public void setCOUNTRY_CODE(String cOUNTRY_CODE) {
		COUNTRY_CODE = cOUNTRY_CODE;
	}
	public String getJOB_PRFSSN() {
		return JOB_PRFSSN;
	}
	public void setJOB_PRFSSN(String jOB_PRFSSN) {
		JOB_PRFSSN = jOB_PRFSSN;
	}
	public String getCERT_EXPD_DATE() {
		return CERT_EXPD_DATE;
	}
	public void setCERT_EXPD_DATE(String cERT_EXPD_DATE) {
		CERT_EXPD_DATE = cERT_EXPD_DATE;
	}
	public String getCERT_VALID_DATE() {
		return CERT_VALID_DATE;
	}
	public void setCERT_VALID_DATE(String cERT_VALID_DATE) {
		CERT_VALID_DATE = cERT_VALID_DATE;
	}
	public String getISSUED_INS() {
		return ISSUED_INS;
	}
	public void setISSUED_INS(String iSSUED_INS) {
		ISSUED_INS = iSSUED_INS;
	}
	public String getCUST_LEVEL() {
		return CUST_LEVEL;
	}
	public void setCUST_LEVEL(String cUST_LEVEL) {
		CUST_LEVEL = cUST_LEVEL;
	}
	public String getREC_PRODUCT() {
		return REC_PRODUCT;
	}
	public void setREC_PRODUCT(String rEC_PRODUCT) {
		REC_PRODUCT = rEC_PRODUCT;
	}
	public String getIS_LOCBANKEMP() {
		return IS_LOCBANKEMP;
	}
	public void setIS_LOCBANKEMP(String iS_LOCBANKEMP) {
		IS_LOCBANKEMP = iS_LOCBANKEMP;
	}
	public String getLOC_BANKS_HOLDER_FLAG() {
		return LOC_BANKS_HOLDER_FLAG;
	}
	public void setLOC_BANKS_HOLDER_FLAG(String lOC_BANKS_HOLDER_FLAG) {
		LOC_BANKS_HOLDER_FLAG = lOC_BANKS_HOLDER_FLAG;
	}
	public String getPHONE_NO() {
		return PHONE_NO;
	}
	public void setPHONE_NO(String pHONE_NO) {
		PHONE_NO = pHONE_NO;
	}
	public String getMOBILE_PHONE() {
		return MOBILE_PHONE;
	}
	public void setMOBILE_PHONE(String mOBILE_PHONE) {
		MOBILE_PHONE = mOBILE_PHONE;
	}
	public String getADDR_LINE() {
		return ADDR_LINE;
	}
	public void setADDR_LINE(String aDDR_LINE) {
		ADDR_LINE = aDDR_LINE;
	}
	public String getNINE_ITEM_COMPLETED_FLAG() {
		return NINE_ITEM_COMPLETED_FLAG;
	}
	public void setNINE_ITEM_COMPLETED_FLAG(String nINE_ITEM_COMPLETED_FLAG) {
		NINE_ITEM_COMPLETED_FLAG = nINE_ITEM_COMPLETED_FLAG;
	}
	public String getSIMILAR_INFO_FLAG() {
		return SIMILAR_INFO_FLAG;
	}
	public void setSIMILAR_INFO_FLAG(String sIMILAR_INFO_FLAG) {
		SIMILAR_INFO_FLAG = sIMILAR_INFO_FLAG;
	}
	public String getCERTNO_LEGAL_FALG() {
		return CERTNO_LEGAL_FALG;
	}
	public void setCERTNO_LEGAL_FALG(String cERTNO_LEGAL_FALG) {
		CERTNO_LEGAL_FALG = cERTNO_LEGAL_FALG;
	}
	public String getMONTH_INCOME() {
		return MONTH_INCOME;
	}
	public void setMONTH_INCOME(String mONTH_INCOME) {
		MONTH_INCOME = mONTH_INCOME;
	}
	public String getBIRTH_DATE() {
		return BIRTH_DATE;
	}
	public void setBIRTH_DATE(String bIRTH_DATE) {
		BIRTH_DATE = bIRTH_DATE;
	}
	public String getNATION_CODE() {
		return NATION_CODE;
	}
	public void setNATION_CODE(String nATION_CODE) {
		NATION_CODE = nATION_CODE;
	}
	public String getVISA_TYPE() {
		return VISA_TYPE;
	}
	public void setVISA_TYPE(String vISA_TYPE) {
		VISA_TYPE = vISA_TYPE;
	}
	public String getVISA_EXPD_DATE() {
		return VISA_EXPD_DATE;
	}
	public void setVISA_EXPD_DATE(String vISA_EXPD_DATE) {
		VISA_EXPD_DATE = vISA_EXPD_DATE;
	}
	public String getJOB_PRFSSN_DES() {
		return JOB_PRFSSN_DES;
	}
	public void setJOB_PRFSSN_DES(String jOB_PRFSSN_DES) {
		JOB_PRFSSN_DES = jOB_PRFSSN_DES;
	}
	public String getMAIN_ECON_RESUR_DES() {
		return MAIN_ECON_RESUR_DES;
	}
	public void setMAIN_ECON_RESUR_DES(String mAIN_ECON_RESUR_DES) {
		MAIN_ECON_RESUR_DES = mAIN_ECON_RESUR_DES;
	}
	public String getIS_EXT_MSG() {
		return IS_EXT_MSG;
	}
	public void setIS_EXT_MSG(String iS_EXT_MSG) {
		IS_EXT_MSG = iS_EXT_MSG;
	}
	public String getSOCIAL_CONTEXT() {
		return SOCIAL_CONTEXT;
	}
	public void setSOCIAL_CONTEXT(String sOCIAL_CONTEXT) {
		SOCIAL_CONTEXT = sOCIAL_CONTEXT;
	}
	public String getOPEN_PURPOSE() {
		return OPEN_PURPOSE;
	}
	public void setOPEN_PURPOSE(String oPEN_PURPOSE) {
		OPEN_PURPOSE = oPEN_PURPOSE;
	}
	public String getFUNDS_PROVIDED() {
		return FUNDS_PROVIDED;
	}
	public void setFUNDS_PROVIDED(String fUNDS_PROVIDED) {
		FUNDS_PROVIDED = fUNDS_PROVIDED;
	}
	public String getTRANSACTION_SCALE() {
		return TRANSACTION_SCALE;
	}
	public void setTRANSACTION_SCALE(String tRANSACTION_SCALE) {
		TRANSACTION_SCALE = tRANSACTION_SCALE;
	}
	public String getPRODUCTS_SERVICES() {
		return PRODUCTS_SERVICES;
	}
	public void setPRODUCTS_SERVICES(String pRODUCTS_SERVICES) {
		PRODUCTS_SERVICES = pRODUCTS_SERVICES;
	}
	public String getRELATION_COUNTRY() {
		return RELATION_COUNTRY;
	}
	public void setRELATION_COUNTRY(String rELATION_COUNTRY) {
		RELATION_COUNTRY = rELATION_COUNTRY;
	}
	public String getNEGATIVE_NEWS() {
		return NEGATIVE_NEWS;
	}
	public void setNEGATIVE_NEWS(String nEGATIVE_NEWS) {
		NEGATIVE_NEWS = nEGATIVE_NEWS;
	}
	public String getIS_NARCOTICS() {
		return IS_NARCOTICS;
	}
	public void setIS_NARCOTICS(String iS_NARCOTICS) {
		IS_NARCOTICS = iS_NARCOTICS;
	}
	public String getINFO_QUALITY_FLAG() {
		return INFO_QUALITY_FLAG;
	}
	public void setINFO_QUALITY_FLAG(String iNFO_QUALITY_FLAG) {
		INFO_QUALITY_FLAG = iNFO_QUALITY_FLAG;
	}
	
}
