package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:账号信息综合查询BODY
 * Description:
 * @author mouchunyue
 * @date 2016年9月12日 下午4:31:41
 */
@XStreamAlias("Body")
public class BCK0002ResBodyBean {

	private String SvrDate;//核心日期
	private String SvrJrnlNo;//核心流水号
	private String AcctType;//账号种类
	private String CustType;//客户种类
	private String TermFlag;//定活性质
	private String ItemNo;//科目号
	private String AcctNo;//账号 
	private String ProdCode;//产品代码 
	private String Currency;//币种
	private String CertNo;//凭证号
	private String CertUseDate;//凭证使用日期
	private String OpenInst_No;//开户机构
	private String OpenAmt;//开户金额
	private String OpenDate;//开户日
	private String StartIntDate;//起息日
	private String DepTerm;//存期
	private String EndDate;//到期日
	private String Rate;//开户利率
	private String OpenQlt;//开户性质
	private String OrigAcctNo;//原账号
	private String ExchFlag;//自动转存标志
	private String DrawCond;//支付条件
	private String OpenTeller;//开户柜员
	private String EndAmt;//结存额
	private String UseAmt;//可用余额
	private String TotalAmt;//存折余额
	private String InteCollect;//利息积数
	private String Times;//次数
	private String MaxSub;//最大序号
	private String PrtedSub;//已打序号
	private String BookAdd;//存折位置
	private String StopAmt;//止付金额
	private String UnUseAmt;//不可用金额
	private String FreezeAmt;//冻结金额
	private String HoldAmt;//圈存金额
	private String SubAcctXjAmt;//子帐号现金累计取款
	private String SubAcctZzAmt;//子帐号转帐累计取款
	private String SubAcctCashAmt;//子帐号现金存款
	private String SubAcctExchgAmt;//子帐号转帐存款
	private String AcctStat;//账号状态
	private String ClearDate;//结清日期
	private String ClearTeller;//清户柜员号
	private String ClearInst;//清户机构
	private String ClearReason;//清户原因
	private String AcctQlt;//账号性质
	private String OthOpenAcct;//另开新户账号
	private String PrlAcct;//协议户账号
	private String LeaveAmt;//留存额度
	private String Limit;//收付限制 
	private String AcctType2;//账户种类
	private String ClearMan;//发放授权人
	private String ClearRate;//清户利率
	private String Round;//周期
	private String RoundAmt;//周期金额
	private String FgtInout;//是否漏存取
	private String PreDate;//预约日期
	private String PreAmt;//预约金额
	private String BalDirect;//余额方向
	private String CustName1;//客户名称
	private String InOutType;//表内/表外标志
	private String LongFlag;//是否久悬户
	private String BillNo;//借据号
	private String DealAcct;//结算账号
	private String AddLimit;//累计贷款限额
	private String BackDate;//还清日期
	private String RateNo;//基准利率代号
	private String InAcctNo;//表内挂息户
	private String OutAcctNo;//表外挂息户
	private String ContNo;//合同号
	private String CollDate;//积数日期
	private String RateFloat;//利率浮动值
	private String DueFloat;//逾期浮动值
	private String LastDate;//上次结息日期
	private String ChangeDate;//变动日期
	private String AddOutAmt;//累计发放
	private String AddBackAmt;//累计收回
	private String ProAcct;//保证账号
	private String TrustAcct;//委托账号
	private String Flag;//标志
	private String InnAmt;//复息余额
	private String SbackDate;//应还日期
	private String Balance;//本金余额
	private String InnBalance;//利息余额
	private String ThisAmt;//本次本金
	private String ThisInne;//本次利息
	private String ThisDinne;//本次复息
	private String CrDate;//贷款日
	private String SettAcctFlag;//是否结算户
	private String PayTimes;//已部提次数
	private String OpenNature;//卡本通开户性质
	private String OpenChnal;//开户渠道
	private String ProdName;//产品名称
	private String CustNo;//客户号
	private String CustName;//客户名称
	private String IdType;//证件类别
	private String IdNo;//证件号
	private String IdInst;//发证机关
	private String PhoneNo;//电话号码
	private String Address;//地址
	private String GroupInst;//组织机构代码
	private String GsdjId;//国税登记证号
	private String DsdjId;//地税登记证号
	public String getSvrDate() {
		return SvrDate;
	}
	public void setSvrDate(String svrDate) {
		SvrDate = svrDate;
	}
	public String getSvrJrnlNo() {
		return SvrJrnlNo;
	}
	public void setSvrJrnlNo(String svrJrnlNo) {
		SvrJrnlNo = svrJrnlNo;
	}
	public String getAcctType() {
		return AcctType;
	}
	public void setAcctType(String acctType) {
		AcctType = acctType;
	}
	public String getCustType() {
		return CustType;
	}
	public void setCustType(String custType) {
		CustType = custType;
	}
	public String getTermFlag() {
		return TermFlag;
	}
	public void setTermFlag(String termFlag) {
		TermFlag = termFlag;
	}
	public String getItemNo() {
		return ItemNo;
	}
	public void setItemNo(String itemNo) {
		ItemNo = itemNo;
	}
	public String getAcctNo() {
		return AcctNo;
	}
	public void setAcctNo(String acctNo) {
		AcctNo = acctNo;
	}
	public String getProdCode() {
		return ProdCode;
	}
	public void setProdCode(String prodCode) {
		ProdCode = prodCode;
	}
	public String getCurrency() {
		return Currency;
	}
	public void setCurrency(String currency) {
		Currency = currency;
	}
	public String getCertNo() {
		return CertNo;
	}
	public void setCertNo(String certNo) {
		CertNo = certNo;
	}
	public String getCertUseDate() {
		return CertUseDate;
	}
	public void setCertUseDate(String certUseDate) {
		CertUseDate = certUseDate;
	}
	public String getOpenInst_No() {
		return OpenInst_No;
	}
	public void setOpenInst_No(String openInst_No) {
		OpenInst_No = openInst_No;
	}
	public String getOpenAmt() {
		return OpenAmt;
	}
	public void setOpenAmt(String openAmt) {
		OpenAmt = openAmt;
	}
	public String getOpenDate() {
		return OpenDate;
	}
	public void setOpenDate(String openDate) {
		OpenDate = openDate;
	}
	public String getStartIntDate() {
		return StartIntDate;
	}
	public void setStartIntDate(String startIntDate) {
		StartIntDate = startIntDate;
	}
	public String getDepTerm() {
		return DepTerm;
	}
	public void setDepTerm(String depTerm) {
		DepTerm = depTerm;
	}
	public String getEndDate() {
		return EndDate;
	}
	public void setEndDate(String endDate) {
		EndDate = endDate;
	}
	public String getRate() {
		return Rate;
	}
	public void setRate(String rate) {
		Rate = rate;
	}
	public String getOpenQlt() {
		return OpenQlt;
	}
	public void setOpenQlt(String openQlt) {
		OpenQlt = openQlt;
	}
	public String getOrigAcctNo() {
		return OrigAcctNo;
	}
	public void setOrigAcctNo(String origAcctNo) {
		OrigAcctNo = origAcctNo;
	}
	public String getExchFlag() {
		return ExchFlag;
	}
	public void setExchFlag(String exchFlag) {
		ExchFlag = exchFlag;
	}
	public String getDrawCond() {
		return DrawCond;
	}
	public void setDrawCond(String drawCond) {
		DrawCond = drawCond;
	}
	public String getOpenTeller() {
		return OpenTeller;
	}
	public void setOpenTeller(String openTeller) {
		OpenTeller = openTeller;
	}
	public String getEndAmt() {
		return EndAmt;
	}
	public void setEndAmt(String endAmt) {
		EndAmt = endAmt;
	}
	public String getUseAmt() {
		return UseAmt;
	}
	public void setUseAmt(String useAmt) {
		UseAmt = useAmt;
	}
	public String getTotalAmt() {
		return TotalAmt;
	}
	public void setTotalAmt(String totalAmt) {
		TotalAmt = totalAmt;
	}
	public String getInteCollect() {
		return InteCollect;
	}
	public void setInteCollect(String inteCollect) {
		InteCollect = inteCollect;
	}
	public String getTimes() {
		return Times;
	}
	public void setTimes(String times) {
		Times = times;
	}
	public String getMaxSub() {
		return MaxSub;
	}
	public void setMaxSub(String maxSub) {
		MaxSub = maxSub;
	}
	public String getPrtedSub() {
		return PrtedSub;
	}
	public void setPrtedSub(String prtedSub) {
		PrtedSub = prtedSub;
	}
	public String getBookAdd() {
		return BookAdd;
	}
	public void setBookAdd(String bookAdd) {
		BookAdd = bookAdd;
	}
	public String getStopAmt() {
		return StopAmt;
	}
	public void setStopAmt(String stopAmt) {
		StopAmt = stopAmt;
	}
	public String getUnUseAmt() {
		return UnUseAmt;
	}
	public void setUnUseAmt(String unUseAmt) {
		UnUseAmt = unUseAmt;
	}
	public String getFreezeAmt() {
		return FreezeAmt;
	}
	public void setFreezeAmt(String freezeAmt) {
		FreezeAmt = freezeAmt;
	}
	public String getHoldAmt() {
		return HoldAmt;
	}
	public void setHoldAmt(String holdAmt) {
		HoldAmt = holdAmt;
	}
	public String getSubAcctXjAmt() {
		return SubAcctXjAmt;
	}
	public void setSubAcctXjAmt(String subAcctXjAmt) {
		SubAcctXjAmt = subAcctXjAmt;
	}
	public String getSubAcctZzAmt() {
		return SubAcctZzAmt;
	}
	public void setSubAcctZzAmt(String subAcctZzAmt) {
		SubAcctZzAmt = subAcctZzAmt;
	}
	public String getSubAcctCashAmt() {
		return SubAcctCashAmt;
	}
	public void setSubAcctCashAmt(String subAcctCashAmt) {
		SubAcctCashAmt = subAcctCashAmt;
	}
	public String getSubAcctExchgAmt() {
		return SubAcctExchgAmt;
	}
	public void setSubAcctExchgAmt(String subAcctExchgAmt) {
		SubAcctExchgAmt = subAcctExchgAmt;
	}
	public String getAcctStat() {
		return AcctStat;
	}
	public void setAcctStat(String acctStat) {
		AcctStat = acctStat;
	}
	public String getClearDate() {
		return ClearDate;
	}
	public void setClearDate(String clearDate) {
		ClearDate = clearDate;
	}
	public String getClearTeller() {
		return ClearTeller;
	}
	public void setClearTeller(String clearTeller) {
		ClearTeller = clearTeller;
	}
	public String getClearInst() {
		return ClearInst;
	}
	public void setClearInst(String clearInst) {
		ClearInst = clearInst;
	}
	public String getClearReason() {
		return ClearReason;
	}
	public void setClearReason(String clearReason) {
		ClearReason = clearReason;
	}
	public String getAcctQlt() {
		return AcctQlt;
	}
	public void setAcctQlt(String acctQlt) {
		AcctQlt = acctQlt;
	}
	public String getOthOpenAcct() {
		return OthOpenAcct;
	}
	public void setOthOpenAcct(String othOpenAcct) {
		OthOpenAcct = othOpenAcct;
	}
	public String getRound() {
		return Round;
	}
	public void setRound(String round) {
		Round = round;
	}
	public String getRoundAmt() {
		return RoundAmt;
	}
	public void setRoundAmt(String roundAmt) {
		RoundAmt = roundAmt;
	}
	public String getFgtInout() {
		return FgtInout;
	}
	public void setFgtInout(String fgtInout) {
		FgtInout = fgtInout;
	}
	public String getPreDate() {
		return PreDate;
	}
	public void setPreDate(String preDate) {
		PreDate = preDate;
	}
	public String getPreAmt() {
		return PreAmt;
	}
	public void setPreAmt(String preAmt) {
		PreAmt = preAmt;
	}
	public String getSettAcctFlag() {
		return SettAcctFlag;
	}
	public void setSettAcctFlag(String settAcctFlag) {
		SettAcctFlag = settAcctFlag;
	}
	public String getPayTimes() {
		return PayTimes;
	}
	public void setPayTimes(String payTimes) {
		PayTimes = payTimes;
	}
	public String getOpenNature() {
		return OpenNature;
	}
	public void setOpenNature(String openNature) {
		OpenNature = openNature;
	}
	public String getOpenChnal() {
		return OpenChnal;
	}
	public void setOpenChnal(String openChnal) {
		OpenChnal = openChnal;
	}
	public String getProdName() {
		return ProdName;
	}
	public void setProdName(String prodName) {
		ProdName = prodName;
	}
	public String getCustNo() {
		return CustNo;
	}
	public void setCustNo(String custNo) {
		CustNo = custNo;
	}
	public String getCustName() {
		return CustName;
	}
	public void setCustName(String custName) {
		CustName = custName;
	}
	public String getIdType() {
		return IdType;
	}
	public void setIdType(String idType) {
		IdType = idType;
	}
	public String getIdNo() {
		return IdNo;
	}
	public void setIdNo(String idNo) {
		IdNo = idNo;
	}
	public String getIdInst() {
		return IdInst;
	}
	public void setIdInst(String idInst) {
		IdInst = idInst;
	}
	public String getPhoneNo() {
		return PhoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		PhoneNo = phoneNo;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getPrlAcct() {
		return PrlAcct;
	}
	public void setPrlAcct(String prlAcct) {
		PrlAcct = prlAcct;
	}
	public String getLeaveAmt() {
		return LeaveAmt;
	}
	public void setLeaveAmt(String leaveAmt) {
		LeaveAmt = leaveAmt;
	}
	public String getLimit() {
		return Limit;
	}
	public void setLimit(String limit) {
		Limit = limit;
	}
	public String getAcctType2() {
		return AcctType2;
	}
	public void setAcctType2(String acctType2) {
		AcctType2 = acctType2;
	}
	public String getClearMan() {
		return ClearMan;
	}
	public void setClearMan(String clearMan) {
		ClearMan = clearMan;
	}
	public String getClearRate() {
		return ClearRate;
	}
	public void setClearRate(String clearRate) {
		ClearRate = clearRate;
	}
	public String getBalDirect() {
		return BalDirect;
	}
	public void setBalDirect(String balDirect) {
		BalDirect = balDirect;
	}
	public String getCustName1() {
		return CustName1;
	}
	public void setCustName1(String custName1) {
		CustName1 = custName1;
	}
	public String getInOutType() {
		return InOutType;
	}
	public void setInOutType(String inOutType) {
		InOutType = inOutType;
	}
	public String getLongFlag() {
		return LongFlag;
	}
	public void setLongFlag(String longFlag) {
		LongFlag = longFlag;
	}
	public String getBillNo() {
		return BillNo;
	}
	public void setBillNo(String billNo) {
		BillNo = billNo;
	}
	public String getDealAcct() {
		return DealAcct;
	}
	public void setDealAcct(String dealAcct) {
		DealAcct = dealAcct;
	}
	public String getAddLimit() {
		return AddLimit;
	}
	public void setAddLimit(String addLimit) {
		AddLimit = addLimit;
	}
	public String getBackDate() {
		return BackDate;
	}
	public void setBackDate(String backDate) {
		BackDate = backDate;
	}
	public String getRateNo() {
		return RateNo;
	}
	public void setRateNo(String rateNo) {
		RateNo = rateNo;
	}
	public String getInAcctNo() {
		return InAcctNo;
	}
	public void setInAcctNo(String inAcctNo) {
		InAcctNo = inAcctNo;
	}
	public String getOutAcctNo() {
		return OutAcctNo;
	}
	public void setOutAcctNo(String outAcctNo) {
		OutAcctNo = outAcctNo;
	}
	public String getContNo() {
		return ContNo;
	}
	public void setContNo(String contNo) {
		ContNo = contNo;
	}
	public String getCollDate() {
		return CollDate;
	}
	public void setCollDate(String collDate) {
		CollDate = collDate;
	}
	public String getRateFloat() {
		return RateFloat;
	}
	public void setRateFloat(String rateFloat) {
		RateFloat = rateFloat;
	}
	public String getDueFloat() {
		return DueFloat;
	}
	public void setDueFloat(String dueFloat) {
		DueFloat = dueFloat;
	}
	public String getLastDate() {
		return LastDate;
	}
	public void setLastDate(String lastDate) {
		LastDate = lastDate;
	}
	public String getChangeDate() {
		return ChangeDate;
	}
	public void setChangeDate(String changeDate) {
		ChangeDate = changeDate;
	}
	public String getAddOutAmt() {
		return AddOutAmt;
	}
	public void setAddOutAmt(String addOutAmt) {
		AddOutAmt = addOutAmt;
	}
	public String getAddBackAmt() {
		return AddBackAmt;
	}
	public void setAddBackAmt(String addBackAmt) {
		AddBackAmt = addBackAmt;
	}
	public String getProAcct() {
		return ProAcct;
	}
	public void setProAcct(String proAcct) {
		ProAcct = proAcct;
	}
	public String getTrustAcct() {
		return TrustAcct;
	}
	public void setTrustAcct(String trustAcct) {
		TrustAcct = trustAcct;
	}
	public String getFlag() {
		return Flag;
	}
	public void setFlag(String flag) {
		Flag = flag;
	}
	public String getInnAmt() {
		return InnAmt;
	}
	public void setInnAmt(String innAmt) {
		InnAmt = innAmt;
	}
	public String getSbackDate() {
		return SbackDate;
	}
	public void setSbackDate(String sbackDate) {
		SbackDate = sbackDate;
	}
	public String getBalance() {
		return Balance;
	}
	public void setBalance(String balance) {
		Balance = balance;
	}
	public String getInnBalance() {
		return InnBalance;
	}
	public void setInnBalance(String innBalance) {
		InnBalance = innBalance;
	}
	public String getThisAmt() {
		return ThisAmt;
	}
	public void setThisAmt(String thisAmt) {
		ThisAmt = thisAmt;
	}
	public String getThisInne() {
		return ThisInne;
	}
	public void setThisInne(String thisInne) {
		ThisInne = thisInne;
	}
	public String getThisDinne() {
		return ThisDinne;
	}
	public void setThisDinne(String thisDinne) {
		ThisDinne = thisDinne;
	}
	public String getCrDate() {
		return CrDate;
	}
	public void setCrDate(String crDate) {
		CrDate = crDate;
	}
	public String getGroupInst() {
		return GroupInst;
	}
	public void setGroupInst(String groupInst) {
		GroupInst = groupInst;
	}
	public String getGsdjId() {
		return GsdjId;
	}
	public void setGsdjId(String gsdjId) {
		GsdjId = gsdjId;
	}
	public String getDsdjId() {
		return DsdjId;
	}
	public void setDsdjId(String dsdjId) {
		DsdjId = dsdjId;
	}
	
}
