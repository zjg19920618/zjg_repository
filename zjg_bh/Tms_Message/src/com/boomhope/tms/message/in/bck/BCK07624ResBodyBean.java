package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:个人账户销户----代理人信息
 * Description:
 * @author zjg
 * @date 2017年07月04日 下午4:32:27
 */
@XStreamAlias("Body")
public class BCK07624ResBodyBean {

	private String SVR_DATE;//核心日期
	private String SVR_JRNL_NO;//流水号
	private String INTE_AMT;//应付利息
	private String TAX_RATE;//计税税率
	private String RATE_TAX;//利息税
	private String SUBSIDY_AMT;//保值贴补
	private String REAL_PRI;//实付本金
	private String REAL_INTE;//实付利息
	private String BALANCE;//余额
	private String START_INT_DATE;//起息日期
	private String DUE_DATE;//到期日期
	private String RATE;//利率
	private String DEP_DAYS;//实存天数
	private String FILE_NAME;//存折文件
	private String ITEM_NO;//科目
	private String OPEN_INST;//开户机构
	private String OPP_ACCT_NO;//对方账号
	private String OPP_ACCT_NAME;//对方户名
	private String OPP_ITEM_NO;//对方科目
	private String OPP_OPEN_INST;//对方开户机构
	private String CR_DB_FLAG;//借贷标志
	private String RATE_ITEM;//利息科目
	private String TRAN_AMT;//交易金额
	private String ADVN_INIT;//预付利息
	private String XFD_COUNT;//消费豆数量
	private String XFD_AMT;//消费豆金额
	
	public String getADVN_INIT() {
		return ADVN_INIT;
	}
	public void setADVN_INIT(String aDVN_INIT) {
		ADVN_INIT = aDVN_INIT;
	}
	public String getSVR_DATE() {
		return SVR_DATE;
	}
	public void setSVR_DATE(String sVR_DATE) {
		SVR_DATE = sVR_DATE;
	}
	public String getSVR_JRNL_NO() {
		return SVR_JRNL_NO;
	}
	public void setSVR_JRNL_NO(String sVR_JRNL_NO) {
		SVR_JRNL_NO = sVR_JRNL_NO;
	}
	public String getINTE_AMT() {
		return INTE_AMT;
	}
	public void setINTE_AMT(String iNTE_AMT) {
		INTE_AMT = iNTE_AMT;
	}
	public String getTAX_RATE() {
		return TAX_RATE;
	}
	public void setTAX_RATE(String tAX_RATE) {
		TAX_RATE = tAX_RATE;
	}
	public String getRATE_TAX() {
		return RATE_TAX;
	}
	public void setRATE_TAX(String rATE_TAX) {
		RATE_TAX = rATE_TAX;
	}
	public String getSUBSIDY_AMT() {
		return SUBSIDY_AMT;
	}
	public void setSUBSIDY_AMT(String sUBSIDY_AMT) {
		SUBSIDY_AMT = sUBSIDY_AMT;
	}
	public String getREAL_PRI() {
		return REAL_PRI;
	}
	public void setREAL_PRI(String rEAL_PRI) {
		REAL_PRI = rEAL_PRI;
	}
	public String getREAL_INTE() {
		return REAL_INTE;
	}
	public void setREAL_INTE(String rEAL_INTE) {
		REAL_INTE = rEAL_INTE;
	}
	public String getBALANCE() {
		return BALANCE;
	}
	public void setBALANCE(String bALANCE) {
		BALANCE = bALANCE;
	}
	public String getSTART_INT_DATE() {
		return START_INT_DATE;
	}
	public void setSTART_INT_DATE(String sTART_INT_DATE) {
		START_INT_DATE = sTART_INT_DATE;
	}
	public String getDUE_DATE() {
		return DUE_DATE;
	}
	public void setDUE_DATE(String dUE_DATE) {
		DUE_DATE = dUE_DATE;
	}
	public String getRATE() {
		return RATE;
	}
	public void setRATE(String rATE) {
		RATE = rATE;
	}
	public String getDEP_DAYS() {
		return DEP_DAYS;
	}
	public void setDEP_DAYS(String dEP_DAYS) {
		DEP_DAYS = dEP_DAYS;
	}
	public String getFILE_NAME() {
		return FILE_NAME;
	}
	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}
	public String getITEM_NO() {
		return ITEM_NO;
	}
	public void setITEM_NO(String iTEM_NO) {
		ITEM_NO = iTEM_NO;
	}
	public String getOPEN_INST() {
		return OPEN_INST;
	}
	public void setOPEN_INST(String oPEN_INST) {
		OPEN_INST = oPEN_INST;
	}
	public String getOPP_ACCT_NO() {
		return OPP_ACCT_NO;
	}
	public void setOPP_ACCT_NO(String oPP_ACCT_NO) {
		OPP_ACCT_NO = oPP_ACCT_NO;
	}
	public String getOPP_ACCT_NAME() {
		return OPP_ACCT_NAME;
	}
	public void setOPP_ACCT_NAME(String oPP_ACCT_NAME) {
		OPP_ACCT_NAME = oPP_ACCT_NAME;
	}
	public String getOPP_ITEM_NO() {
		return OPP_ITEM_NO;
	}
	public void setOPP_ITEM_NO(String oPP_ITEM_NO) {
		OPP_ITEM_NO = oPP_ITEM_NO;
	}
	public String getOPP_OPEN_INST() {
		return OPP_OPEN_INST;
	}
	public void setOPP_OPEN_INST(String oPP_OPEN_INST) {
		OPP_OPEN_INST = oPP_OPEN_INST;
	}
	public String getCR_DB_FLAG() {
		return CR_DB_FLAG;
	}
	public void setCR_DB_FLAG(String cR_DB_FLAG) {
		CR_DB_FLAG = cR_DB_FLAG;
	}
	public String getRATE_ITEM() {
		return RATE_ITEM;
	}
	public void setRATE_ITEM(String rATE_ITEM) {
		RATE_ITEM = rATE_ITEM;
	}
	public String getTRAN_AMT() {
		return TRAN_AMT;
	}
	public void setTRAN_AMT(String tRAN_AMT) {
		TRAN_AMT = tRAN_AMT;
	}
	public String getXFD_COUNT() {
		return XFD_COUNT;
	}
	public void setXFD_COUNT(String xFD_COUNT) {
		XFD_COUNT = xFD_COUNT;
	}
	public String getXFD_AMT() {
		return XFD_AMT;
	}
	public void setXFD_AMT(String xFD_AMT) {
		XFD_AMT = xFD_AMT;
	}
	
	
}
