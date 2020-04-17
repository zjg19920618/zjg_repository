package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:账号信息综合查询
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午3:46:27
 */
@XStreamAlias("BODY")
public class Out02880ResBodyBean {

	private String SVR_DATE;//核心日期
	private String SVR_JRNL_NO;//核心流水号
	private String ACCT_TYPE;//账号种类
	private String CUST_TYPE;//客户种类
	private String TERM_FLAG;//定活性质
	private String ITEM_NO;//科目号
	private String ACCT_NO;//账号 
	private String PROD_CODE;//产品代码 
	private String CURRENCY;//币种
	private String CERT_NO;//凭证号
	private String CERT_USE_DATE;//凭证使用日期
	private String OPEN_INST_NO;//开户机构
	private String OPEN_AMT;//开户金额
	private String OPEN_DATE;//开户日
	private String START_INT_DATE;//起息日
	private String DEP_TERM;//存期
	private String END_DATE;//到期日
	private String RATE;//开户利率
	private String OPEN_QLT;//开户性质
	private String ORIG_ACCT_NO;//原账号
	private String EXCH_FLAG;//自动转存标志
	private String DRAW_COND;//支付条件
	private String OPEN_TELLER;//开户柜员
	private String END_AMT;//结存额
	private String USE_AMT;//可用余额
	private String TOTAL_AMT;//存折余额
	private String INTE_COLLECT;//利息积数
	private String TIMES;//次数
	private String MAX_SUB;//最大序号
	private String PRTED_SUB;//已打序号
	private String BOOK_ADD;//存折位置
	private String STOP_AMT;//止付金额
	private String ACCT_STAT;//账号状态
	private String UN_USE_AMT;//不可用金额
	private String FREEZE_AMT;//冻结金额
	private String HOLD_AMT;//圈存金额
	private String SUB_ACCT_XJ_AMT;//子帐号现金累计取款
	private String SUB_ACCT_ZZ_AMT;//子帐号转帐累计取款
	private String SUB_ACCT_CASH_AMT;//子帐号现金存款
	private String SUB_ACCT_EXCHG_AMT;//子帐号转帐存款
	private String CLEAR_DATE;//结清日期
	private String CLEAR_TELLER;//清户柜员号
	private String CLEAR_INST;//清户机构
	private String CLEAR_REASON;//清户原因
	private String ACCT_QLT;//账号性质
	private String OTH_OPEN_ACCT;//另开新户账号
	private String PRL_ACCT;//协议户账号
	private String LEAVE_AMT;//留存额度
	private String LIMIT;//收付限制 
	private String ACCT_TYPE2;//账户种类
	private String CLEAR_MAN;//发放授权人
	private String CLEAR_RATE;//清户利率
	private String ROUND;//周期
	private String ROUND_AMT;//周期金额
	private String FGT_INOUT;//是否漏存取
	private String PRE_DATE;//预约日期
	private String PRE_AMT;//预约金额
	private String BAL_DIRECT;//余额方向
	private String CUST_NAME1;//客户名称
	private String SETT_ACCT_FLAG;//是否结算户
	private String IN_OUT_TYPE;//表内/表外标志
	private String LONG_FLAG;//是否久悬户
	private String BILL_NO;//借据号
	private String DEAL_ACCT;//结算账号
	private String ADD_LIMIT;//累计贷款限额
	private String BACK_DATE;//还清日期
	private String RATE_NO;//基准利率代号
	private String IN_ACCT_NO;//表内挂息户
	private String OUT_ACCT_NO;//表外挂息户
	private String CONT_NO;//合同号
	private String COLL_DATE;//积数日期
	private String RATE_FLOAT;//利率浮动值
	private String DUE_FLOAT;//逾期浮动值
	private String LAST_DATE;//上次结息日期
	private String CHANGE_DATE;//变动日期
	private String ADD_OUT_AMT;//累计发放
	private String ADD_BACK_AMT;//累计收回
	private String PRO_ACCT;//保证账号
	private String TRUST_ACCT;//委托账号
	private String FLAG;//标志
	private String INN_AMT;//复息余额
	private String SBACK_DATE;//应还日期
	private String BALANCE;//本金余额
	private String INN_BALANCE;//利息余额
	private String THIS_AMT;//本次本金
	private String THIS_INNE;//本次利息
	private String THIS_DINNE;//本次复息
	private String CR_DATE;//贷款日
	private String PAY_TIMES;//已部提次数
	private String OPEN_NATURE;//卡本通开户性质
	private String OPEN_CHNAL;//开户渠道
	private String PROD_NAME;//产品名称
	private String CUST_NO;//客户号
	private String CUST_NAME;//客户名称
	private String ID_TYPE;//证件类别
	private String ID_NO;//证件号
	private String ID_INST;//发证机关
	private String PHONE_NO;//电话号码
	private String ADDRESS;//地址
	private String GROUP_INST;//组织机构代码
	private String GSDJ_ID;//国税登记证号
	private String DSDJ_ID;//地税登记证号
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
	public String getACCT_TYPE() {
		return ACCT_TYPE;
	}
	public void setACCT_TYPE(String aCCT_TYPE) {
		ACCT_TYPE = aCCT_TYPE;
	}
	public String getCUST_TYPE() {
		return CUST_TYPE;
	}
	public void setCUST_TYPE(String cUST_TYPE) {
		CUST_TYPE = cUST_TYPE;
	}
	public String getTERM_FLAG() {
		return TERM_FLAG;
	}
	public void setTERM_FLAG(String tERM_FLAG) {
		TERM_FLAG = tERM_FLAG;
	}
	public String getITEM_NO() {
		return ITEM_NO;
	}
	public void setITEM_NO(String iTEM_NO) {
		ITEM_NO = iTEM_NO;
	}
	public String getACCT_NO() {
		return ACCT_NO;
	}
	public void setACCT_NO(String aCCT_NO) {
		ACCT_NO = aCCT_NO;
	}
	public String getPROD_CODE() {
		return PROD_CODE;
	}
	public void setPROD_CODE(String pROD_CODE) {
		PROD_CODE = pROD_CODE;
	}
	public String getCURRENCY() {
		return CURRENCY;
	}
	public void setCURRENCY(String cURRENCY) {
		CURRENCY = cURRENCY;
	}
	public String getCERT_NO() {
		return CERT_NO;
	}
	public void setCERT_NO(String cERT_NO) {
		CERT_NO = cERT_NO;
	}
	public String getCERT_USE_DATE() {
		return CERT_USE_DATE;
	}
	public void setCERT_USE_DATE(String cERT_USE_DATE) {
		CERT_USE_DATE = cERT_USE_DATE;
	}
	public String getOPEN_INST_NO() {
		return OPEN_INST_NO;
	}
	public void setOPEN_INST_NO(String oPEN_INST_NO) {
		OPEN_INST_NO = oPEN_INST_NO;
	}
	public String getOPEN_AMT() {
		return OPEN_AMT;
	}
	public void setOPEN_AMT(String oPEN_AMT) {
		OPEN_AMT = oPEN_AMT;
	}
	public String getOPEN_DATE() {
		return OPEN_DATE;
	}
	public void setOPEN_DATE(String oPEN_DATE) {
		OPEN_DATE = oPEN_DATE;
	}
	public String getSTART_INT_DATE() {
		return START_INT_DATE;
	}
	public void setSTART_INT_DATE(String sTART_INT_DATE) {
		START_INT_DATE = sTART_INT_DATE;
	}
	public String getDEP_TERM() {
		return DEP_TERM;
	}
	public void setDEP_TERM(String dEP_TERM) {
		DEP_TERM = dEP_TERM;
	}
	public String getEND_DATE() {
		return END_DATE;
	}
	public void setEND_DATE(String eND_DATE) {
		END_DATE = eND_DATE;
	}
	public String getRATE() {
		return RATE;
	}
	public void setRATE(String rATE) {
		RATE = rATE;
	}
	public String getOPEN_QLT() {
		return OPEN_QLT;
	}
	public void setOPEN_QLT(String oPEN_QLT) {
		OPEN_QLT = oPEN_QLT;
	}
	public String getORIG_ACCT_NO() {
		return ORIG_ACCT_NO;
	}
	public void setORIG_ACCT_NO(String oRIG_ACCT_NO) {
		ORIG_ACCT_NO = oRIG_ACCT_NO;
	}
	public String getEXCH_FLAG() {
		return EXCH_FLAG;
	}
	public void setEXCH_FLAG(String eXCH_FLAG) {
		EXCH_FLAG = eXCH_FLAG;
	}
	public String getDRAW_COND() {
		return DRAW_COND;
	}
	public void setDRAW_COND(String dRAW_COND) {
		DRAW_COND = dRAW_COND;
	}
	public String getOPEN_TELLER() {
		return OPEN_TELLER;
	}
	public void setOPEN_TELLER(String oPEN_TELLER) {
		OPEN_TELLER = oPEN_TELLER;
	}
	public String getEND_AMT() {
		return END_AMT;
	}
	public void setEND_AMT(String eND_AMT) {
		END_AMT = eND_AMT;
	}
	public String getUSE_AMT() {
		return USE_AMT;
	}
	public void setUSE_AMT(String uSE_AMT) {
		USE_AMT = uSE_AMT;
	}
	public String getTOTAL_AMT() {
		return TOTAL_AMT;
	}
	public void setTOTAL_AMT(String tOTAL_AMT) {
		TOTAL_AMT = tOTAL_AMT;
	}
	public String getINTE_COLLECT() {
		return INTE_COLLECT;
	}
	public void setINTE_COLLECT(String iNTE_COLLECT) {
		INTE_COLLECT = iNTE_COLLECT;
	}
	public String getTIMES() {
		return TIMES;
	}
	public void setTIMES(String tIMES) {
		TIMES = tIMES;
	}
	public String getMAX_SUB() {
		return MAX_SUB;
	}
	public void setMAX_SUB(String mAX_SUB) {
		MAX_SUB = mAX_SUB;
	}
	public String getPRTED_SUB() {
		return PRTED_SUB;
	}
	public void setPRTED_SUB(String pRTED_SUB) {
		PRTED_SUB = pRTED_SUB;
	}
	public String getBOOK_ADD() {
		return BOOK_ADD;
	}
	public void setBOOK_ADD(String bOOK_ADD) {
		BOOK_ADD = bOOK_ADD;
	}
	public String getSTOP_AMT() {
		return STOP_AMT;
	}
	public void setSTOP_AMT(String sTOP_AMT) {
		STOP_AMT = sTOP_AMT;
	}
	public String getACCT_STAT() {
		return ACCT_STAT;
	}
	public void setACCT_STAT(String aCCT_STAT) {
		ACCT_STAT = aCCT_STAT;
	}
	public String getUN_USE_AMT() {
		return UN_USE_AMT;
	}
	public void setUN_USE_AMT(String uN_USE_AMT) {
		UN_USE_AMT = uN_USE_AMT;
	}
	public String getFREEZE_AMT() {
		return FREEZE_AMT;
	}
	public void setFREEZE_AMT(String fREEZE_AMT) {
		FREEZE_AMT = fREEZE_AMT;
	}
	public String getHOLD_AMT() {
		return HOLD_AMT;
	}
	public void setHOLD_AMT(String hOLD_AMT) {
		HOLD_AMT = hOLD_AMT;
	}
	public String getSUB_ACCT_XJ_AMT() {
		return SUB_ACCT_XJ_AMT;
	}
	public void setSUB_ACCT_XJ_AMT(String sUB_ACCT_XJ_AMT) {
		SUB_ACCT_XJ_AMT = sUB_ACCT_XJ_AMT;
	}
	public String getSUB_ACCT_ZZ_AMT() {
		return SUB_ACCT_ZZ_AMT;
	}
	public void setSUB_ACCT_ZZ_AMT(String sUB_ACCT_ZZ_AMT) {
		SUB_ACCT_ZZ_AMT = sUB_ACCT_ZZ_AMT;
	}
	public String getSUB_ACCT_CASH_AMT() {
		return SUB_ACCT_CASH_AMT;
	}
	public void setSUB_ACCT_CASH_AMT(String sUB_ACCT_CASH_AMT) {
		SUB_ACCT_CASH_AMT = sUB_ACCT_CASH_AMT;
	}
	public String getSUB_ACCT_EXCHG_AMT() {
		return SUB_ACCT_EXCHG_AMT;
	}
	public void setSUB_ACCT_EXCHG_AMT(String sUB_ACCT_EXCHG_AMT) {
		SUB_ACCT_EXCHG_AMT = sUB_ACCT_EXCHG_AMT;
	}
	public String getCLEAR_DATE() {
		return CLEAR_DATE;
	}
	public void setCLEAR_DATE(String cLEAR_DATE) {
		CLEAR_DATE = cLEAR_DATE;
	}
	public String getCLEAR_TELLER() {
		return CLEAR_TELLER;
	}
	public void setCLEAR_TELLER(String cLEAR_TELLER) {
		CLEAR_TELLER = cLEAR_TELLER;
	}
	public String getCLEAR_INST() {
		return CLEAR_INST;
	}
	public void setCLEAR_INST(String cLEAR_INST) {
		CLEAR_INST = cLEAR_INST;
	}
	public String getCLEAR_REASON() {
		return CLEAR_REASON;
	}
	public void setCLEAR_REASON(String cLEAR_REASON) {
		CLEAR_REASON = cLEAR_REASON;
	}
	public String getACCT_QLT() {
		return ACCT_QLT;
	}
	public void setACCT_QLT(String aCCT_QLT) {
		ACCT_QLT = aCCT_QLT;
	}
	public String getOTH_OPEN_ACCT() {
		return OTH_OPEN_ACCT;
	}
	public void setOTH_OPEN_ACCT(String oTH_OPEN_ACCT) {
		OTH_OPEN_ACCT = oTH_OPEN_ACCT;
	}
	public String getPRL_ACCT() {
		return PRL_ACCT;
	}
	public void setPRL_ACCT(String pRL_ACCT) {
		PRL_ACCT = pRL_ACCT;
	}
	public String getLEAVE_AMT() {
		return LEAVE_AMT;
	}
	public void setLEAVE_AMT(String lEAVE_AMT) {
		LEAVE_AMT = lEAVE_AMT;
	}
	public String getLIMIT() {
		return LIMIT;
	}
	public void setLIMIT(String lIMIT) {
		LIMIT = lIMIT;
	}
	public String getACCT_TYPE2() {
		return ACCT_TYPE2;
	}
	public void setACCT_TYPE2(String aCCT_TYPE2) {
		ACCT_TYPE2 = aCCT_TYPE2;
	}
	public String getCLEAR_MAN() {
		return CLEAR_MAN;
	}
	public void setCLEAR_MAN(String cLEAR_MAN) {
		CLEAR_MAN = cLEAR_MAN;
	}
	public String getCLEAR_RATE() {
		return CLEAR_RATE;
	}
	public void setCLEAR_RATE(String cLEAR_RATE) {
		CLEAR_RATE = cLEAR_RATE;
	}
	public String getROUND() {
		return ROUND;
	}
	public void setROUND(String rOUND) {
		ROUND = rOUND;
	}
	public String getROUND_AMT() {
		return ROUND_AMT;
	}
	public void setROUND_AMT(String rOUND_AMT) {
		ROUND_AMT = rOUND_AMT;
	}
	public String getFGT_INOUT() {
		return FGT_INOUT;
	}
	public void setFGT_INOUT(String fGT_INOUT) {
		FGT_INOUT = fGT_INOUT;
	}
	public String getPRE_DATE() {
		return PRE_DATE;
	}
	public void setPRE_DATE(String pRE_DATE) {
		PRE_DATE = pRE_DATE;
	}
	public String getPRE_AMT() {
		return PRE_AMT;
	}
	public void setPRE_AMT(String pRE_AMT) {
		PRE_AMT = pRE_AMT;
	}
	public String getBAL_DIRECT() {
		return BAL_DIRECT;
	}
	public void setBAL_DIRECT(String bAL_DIRECT) {
		BAL_DIRECT = bAL_DIRECT;
	}
	public String getCUST_NAME1() {
		return CUST_NAME1;
	}
	public void setCUST_NAME1(String cUST_NAME1) {
		CUST_NAME1 = cUST_NAME1;
	}
	public String getSETT_ACCT_FLAG() {
		return SETT_ACCT_FLAG;
	}
	public void setSETT_ACCT_FLAG(String sETT_ACCT_FLAG) {
		SETT_ACCT_FLAG = sETT_ACCT_FLAG;
	}
	public String getIN_OUT_TYPE() {
		return IN_OUT_TYPE;
	}
	public void setIN_OUT_TYPE(String iN_OUT_TYPE) {
		IN_OUT_TYPE = iN_OUT_TYPE;
	}
	public String getLONG_FLAG() {
		return LONG_FLAG;
	}
	public void setLONG_FLAG(String lONG_FLAG) {
		LONG_FLAG = lONG_FLAG;
	}
	public String getBILL_NO() {
		return BILL_NO;
	}
	public void setBILL_NO(String bILL_NO) {
		BILL_NO = bILL_NO;
	}
	public String getDEAL_ACCT() {
		return DEAL_ACCT;
	}
	public void setDEAL_ACCT(String dEAL_ACCT) {
		DEAL_ACCT = dEAL_ACCT;
	}
	public String getADD_LIMIT() {
		return ADD_LIMIT;
	}
	public void setADD_LIMIT(String aDD_LIMIT) {
		ADD_LIMIT = aDD_LIMIT;
	}
	public String getBACK_DATE() {
		return BACK_DATE;
	}
	public void setBACK_DATE(String bACK_DATE) {
		BACK_DATE = bACK_DATE;
	}
	public String getRATE_NO() {
		return RATE_NO;
	}
	public void setRATE_NO(String rATE_NO) {
		RATE_NO = rATE_NO;
	}
	public String getIN_ACCT_NO() {
		return IN_ACCT_NO;
	}
	public void setIN_ACCT_NO(String iN_ACCT_NO) {
		IN_ACCT_NO = iN_ACCT_NO;
	}
	public String getOUT_ACCT_NO() {
		return OUT_ACCT_NO;
	}
	public void setOUT_ACCT_NO(String oUT_ACCT_NO) {
		OUT_ACCT_NO = oUT_ACCT_NO;
	}
	public String getCONT_NO() {
		return CONT_NO;
	}
	public void setCONT_NO(String cONT_NO) {
		CONT_NO = cONT_NO;
	}
	public String getCOLL_DATE() {
		return COLL_DATE;
	}
	public void setCOLL_DATE(String cOLL_DATE) {
		COLL_DATE = cOLL_DATE;
	}
	public String getRATE_FLOAT() {
		return RATE_FLOAT;
	}
	public void setRATE_FLOAT(String rATE_FLOAT) {
		RATE_FLOAT = rATE_FLOAT;
	}
	public String getDUE_FLOAT() {
		return DUE_FLOAT;
	}
	public void setDUE_FLOAT(String dUE_FLOAT) {
		DUE_FLOAT = dUE_FLOAT;
	}
	public String getLAST_DATE() {
		return LAST_DATE;
	}
	public void setLAST_DATE(String lAST_DATE) {
		LAST_DATE = lAST_DATE;
	}
	public String getCHANGE_DATE() {
		return CHANGE_DATE;
	}
	public void setCHANGE_DATE(String cHANGE_DATE) {
		CHANGE_DATE = cHANGE_DATE;
	}
	public String getADD_OUT_AMT() {
		return ADD_OUT_AMT;
	}
	public void setADD_OUT_AMT(String aDD_OUT_AMT) {
		ADD_OUT_AMT = aDD_OUT_AMT;
	}
	public String getADD_BACK_AMT() {
		return ADD_BACK_AMT;
	}
	public void setADD_BACK_AMT(String aDD_BACK_AMT) {
		ADD_BACK_AMT = aDD_BACK_AMT;
	}
	public String getPRO_ACCT() {
		return PRO_ACCT;
	}
	public void setPRO_ACCT(String pRO_ACCT) {
		PRO_ACCT = pRO_ACCT;
	}
	public String getTRUST_ACCT() {
		return TRUST_ACCT;
	}
	public void setTRUST_ACCT(String tRUST_ACCT) {
		TRUST_ACCT = tRUST_ACCT;
	}
	public String getFLAG() {
		return FLAG;
	}
	public void setFLAG(String fLAG) {
		FLAG = fLAG;
	}
	public String getINN_AMT() {
		return INN_AMT;
	}
	public void setINN_AMT(String iNN_AMT) {
		INN_AMT = iNN_AMT;
	}
	public String getSBACK_DATE() {
		return SBACK_DATE;
	}
	public void setSBACK_DATE(String sBACK_DATE) {
		SBACK_DATE = sBACK_DATE;
	}
	public String getBALANCE() {
		return BALANCE;
	}
	public void setBALANCE(String bALANCE) {
		BALANCE = bALANCE;
	}
	public String getINN_BALANCE() {
		return INN_BALANCE;
	}
	public void setINN_BALANCE(String iNN_BALANCE) {
		INN_BALANCE = iNN_BALANCE;
	}
	public String getTHIS_AMT() {
		return THIS_AMT;
	}
	public void setTHIS_AMT(String tHIS_AMT) {
		THIS_AMT = tHIS_AMT;
	}
	public String getTHIS_INNE() {
		return THIS_INNE;
	}
	public void setTHIS_INNE(String tHIS_INNE) {
		THIS_INNE = tHIS_INNE;
	}
	public String getTHIS_DINNE() {
		return THIS_DINNE;
	}
	public void setTHIS_DINNE(String tHIS_DINNE) {
		THIS_DINNE = tHIS_DINNE;
	}
	public String getCR_DATE() {
		return CR_DATE;
	}
	public void setCR_DATE(String cR_DATE) {
		CR_DATE = cR_DATE;
	}
	public String getPAY_TIMES() {
		return PAY_TIMES;
	}
	public void setPAY_TIMES(String pAY_TIMES) {
		PAY_TIMES = pAY_TIMES;
	}
	public String getOPEN_NATURE() {
		return OPEN_NATURE;
	}
	public void setOPEN_NATURE(String oPEN_NATURE) {
		OPEN_NATURE = oPEN_NATURE;
	}
	public String getOPEN_CHNAL() {
		return OPEN_CHNAL;
	}
	public void setOPEN_CHNAL(String oPEN_CHNAL) {
		OPEN_CHNAL = oPEN_CHNAL;
	}
	public String getPROD_NAME() {
		return PROD_NAME;
	}
	public void setPROD_NAME(String pROD_NAME) {
		PROD_NAME = pROD_NAME;
	}
	public String getCUST_NO() {
		return CUST_NO;
	}
	public void setCUST_NO(String cUST_NO) {
		CUST_NO = cUST_NO;
	}
	public String getCUST_NAME() {
		return CUST_NAME;
	}
	public void setCUST_NAME(String cUST_NAME) {
		CUST_NAME = cUST_NAME;
	}
	public String getID_TYPE() {
		return ID_TYPE;
	}
	public void setID_TYPE(String iD_TYPE) {
		ID_TYPE = iD_TYPE;
	}
	public String getID_NO() {
		return ID_NO;
	}
	public void setID_NO(String iD_NO) {
		ID_NO = iD_NO;
	}
	public String getID_INST() {
		return ID_INST;
	}
	public void setID_INST(String iD_INST) {
		ID_INST = iD_INST;
	}
	public String getPHONE_NO() {
		return PHONE_NO;
	}
	public void setPHONE_NO(String pHONE_NO) {
		PHONE_NO = pHONE_NO;
	}
	public String getADDRESS() {
		return ADDRESS;
	}
	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}
	public String getGROUP_INST() {
		return GROUP_INST;
	}
	public void setGROUP_INST(String gROUP_INST) {
		GROUP_INST = gROUP_INST;
	}
	public String getGSDJ_ID() {
		return GSDJ_ID;
	}
	public void setGSDJ_ID(String gSDJ_ID) {
		GSDJ_ID = gSDJ_ID;
	}
	public String getDSDJ_ID() {
		return DSDJ_ID;
	}
	public void setDSDJ_ID(String dSDJ_ID) {
		DSDJ_ID = dSDJ_ID;
	}
	
}
