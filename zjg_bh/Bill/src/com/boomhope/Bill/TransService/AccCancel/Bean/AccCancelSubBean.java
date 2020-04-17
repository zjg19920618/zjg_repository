package com.boomhope.Bill.TransService.AccCancel.Bean;

/**
 * 子账户的bean
 * @author Administrator
 *
 */
public class AccCancelSubBean {

	//账户信息
	private String cardNo;//卡号
	private String subAccNo;//子账号
	private String acc_No;//账号—子账号
	private String cardName;//户名
	private String openInstNo;//开户机构
	private String openDate;//开户日期
	private String endIntDate;//到期日期
	private String openATM;//开户金额
	private String productName;//产品名称
	private String depTerm;//存期
	private String openRate;//利率
	private String balance;//结存额
	private String accNoState;//存单状态
	private String channel;//渠道
	private String bill;//凭证号
	private String printState;//打印状态
	private String drawCound;//是否验密
	private String openQlt;//开户性质
	private String certNo;//返回的存凭证号
	private String idNo;//存单证件号
	private String idName;//存单证件的姓名
	private String amount;//存单余额
	private String produectCode;//产品代码
	private String exchFlag;//自动转存标志
	private String svrDate;//核心日期
	private String startDate;//起息日
	private String custNo;//客户号
	private String billPwd;//存单密码
	private String inteAmt;//应付利息
	private String realAmt;//实付利息
	private String alreadyAmt;//已付利息
	private String advninit;//加息券
	private String realPri;//实付本金
	private String xfdCount;//扣回消费豆
	private String svrJrnlNo;//销户流水号
	private String shtdy;//收回唐豆金额
	private String tdPayAmt;//唐豆支取金额
	private String shtds;//收回唐豆数量
	
	
	//代理人信息
	private String haveAgentFlag;//是否有代理人标志 
	private String agentName;//代理人姓名
	private String agentSex;//代理人性别
	private String agentIdNo;//代理人证件号码
	private String agentQfjg;//使人证件签发机关
	private String agentJob;//代理人职业 
	private String agentAdress;//代理人户口所在地
	
	
	
	public String getSvrJrnlNo() {
		return svrJrnlNo;
	}
	public void setSvrJrnlNo(String svrJrnlNo) {
		this.svrJrnlNo = svrJrnlNo;
	}
	public String getShtdy() {
		return shtdy;
	}
	public void setShtdy(String shtdy) {
		this.shtdy = shtdy;
	}
	public String getTdPayAmt() {
		return tdPayAmt;
	}
	public void setTdPayAmt(String tdPayAmt) {
		this.tdPayAmt = tdPayAmt;
	}
	public String getShtds() {
		return shtds;
	}
	public void setShtds(String shtds) {
		this.shtds = shtds;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getBillPwd() {
		return billPwd;
	}
	public void setBillPwd(String billPwd) {
		this.billPwd = billPwd;
	}
	public String getInteAmt() {
		return inteAmt;
	}
	public void setInteAmt(String inteAmt) {
		this.inteAmt = inteAmt;
	}
	public String getRealAmt() {
		return realAmt;
	}
	public void setRealAmt(String realAmt) {
		this.realAmt = realAmt;
	}
	public String getAlreadyAmt() {
		return alreadyAmt;
	}
	public void setAlreadyAmt(String alreadyAmt) {
		this.alreadyAmt = alreadyAmt;
	}
	public String getAdvninit() {
		return advninit;
	}
	public void setAdvninit(String advninit) {
		this.advninit = advninit;
	}
	public String getRealPri() {
		return realPri;
	}
	public void setRealPri(String realPri) {
		this.realPri = realPri;
	}
	public String getXfdCount() {
		return xfdCount;
	}
	public void setXfdCount(String xfdCount) {
		this.xfdCount = xfdCount;
	}
	public String getHaveAgentFlag() {
		return haveAgentFlag;
	}
	public void setHaveAgentFlag(String haveAgentFlag) {
		this.haveAgentFlag = haveAgentFlag;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getAgentSex() {
		return agentSex;
	}
	public void setAgentSex(String agentSex) {
		this.agentSex = agentSex;
	}
	public String getAgentIdNo() {
		return agentIdNo;
	}
	public void setAgentIdNo(String agentIdNo) {
		this.agentIdNo = agentIdNo;
	}
	public String getAgentQfjg() {
		return agentQfjg;
	}
	public void setAgentQfjg(String agentQfjg) {
		this.agentQfjg = agentQfjg;
	}
	public String getAgentJob() {
		return agentJob;
	}
	public void setAgentJob(String agentJob) {
		this.agentJob = agentJob;
	}
	public String getAgentAdress() {
		return agentAdress;
	}
	public void setAgentAdress(String agentAdress) {
		this.agentAdress = agentAdress;
	}
	public String getAcc_No() {
		return acc_No;
	}
	public void setAcc_No(String acc_No) {
		this.acc_No = acc_No;
	}
	public String getDrawCound() {
		return drawCound;
	}
	public void setDrawCound(String drawCound) {
		this.drawCound = drawCound;
	}
	public String getOpenQlt() {
		return openQlt;
	}
	public void setOpenQlt(String openQlt) {
		this.openQlt = openQlt;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getIdName() {
		return idName;
	}
	public void setIdName(String idName) {
		this.idName = idName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getProduectCode() {
		return produectCode;
	}
	public void setProduectCode(String produectCode) {
		this.produectCode = produectCode;
	}
	public String getExchFlag() {
		return exchFlag;
	}
	public void setExchFlag(String exchFlag) {
		this.exchFlag = exchFlag;
	}
	public String getSvrDate() {
		return svrDate;
	}
	public void setSvrDate(String svrDate) {
		this.svrDate = svrDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getSubAccNo() {
		return subAccNo;
	}
	public void setSubAccNo(String subAccNo) {
		this.subAccNo = subAccNo;
	}
	public String getOpenInstNo() {
		return openInstNo;
	}
	public void setOpenInstNo(String openInstNo) {
		this.openInstNo = openInstNo;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getEndIntDate() {
		return endIntDate;
	}
	public void setEndIntDate(String endIntDate) {
		this.endIntDate = endIntDate;
	}
	public String getOpenATM() {
		return openATM;
	}
	public void setOpenATM(String openATM) {
		this.openATM = openATM;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDepTerm() {
		return depTerm;
	}
	public void setDepTerm(String depTerm) {
		this.depTerm = depTerm;
	}
	public String getOpenRate() {
		return openRate;
	}
	public void setOpenRate(String openRate) {
		this.openRate = openRate;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getAccNoState() {
		return accNoState;
	}
	public void setAccNoState(String accNoState) {
		this.accNoState = accNoState;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getBill() {
		return bill;
	}
	public void setBill(String bill) {
		this.bill = bill;
	}
	public String getPrintState() {
		return printState;
	}
	public void setPrintState(String printState) {
		this.printState = printState;
	}
	
	
	
}
