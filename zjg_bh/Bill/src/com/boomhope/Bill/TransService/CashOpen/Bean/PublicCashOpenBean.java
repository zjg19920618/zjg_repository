package com.boomhope.Bill.TransService.CashOpen.Bean;

import java.util.HashMap;
import java.util.Map;

import com.boomhope.Bill.Framework.BaseTransBean;

/**
 * 现金开户公共字段bean
 * @author Administrator
 *
 */
public class PublicCashOpenBean extends BaseTransBean{
	//现金开户
	/* 钱柜相关信息 */
	private String orderNo;      // 订单号
	private String orderStatus;//订单状态 
	private String orderPwd;     // 订单密码
	private String inputOrderPwd;//未加密的密码
	private String QrCode;//二维码跳转页面标识
	private String OrderAmount;//订单余额
	private String tel;//手机号
	private String ApplyCode;//交易申请码 
	private String BankAccount;//银行内部账号 
	private String commiTtype;//业务办理结果
	private int khqxn;//千禧当前页
	private int khqxpage; //千禧一共页数	
	private int skhqxn;//千禧当前页
	private int skhqxpage; //千禧一共页数
	private int khryn;//如意当前页
	private int khrypage; //如意一共页数
	private int skhryn;//如意当前页
	private int skhrypage; //如意一共页数
	private int khyxn;//约享当前页
	private int khyxpage; //约享一共页数
	private int skhyxn;//约享当前页
	private int skhyxpage; //约享一共页数
	private int khxfn;//幸福1+1当前页
	private int khxfpage; //幸福1+1一共页数
	private int skhxfn;//幸福1+1当前页
	private int skhxfpage; //幸福1+1一共页数
	private int khldn;//立得存当前页
	private int khldpage; //立得存一共页数
	private int skhldn;//立得存当前页
	private int skhldpage; //立得存一共页数
	private int khjxn;//积享存当前页
	private int khjxpage; //积享存一共页数
	private int skhjxn;//积享存当前页
	private int skhjxpage; //积享存一共页数
	private int khryjn;//如意存+存当前页
	private int khryjpage; //如意存+存一共页数
	private int skhryjn;//如意存+存当前页
	private int skhryjpage; //如意存+存一共页数
	private int khaxcn;//安享存当前页
	private int khaxcpage; //安享存存一共页数
	private int skhaxcn;//安享存当前页
	private int skhaxcpage; //安享存存一共页数
	private String supTellerNo;//授权柜员号
	//卡信息
	private String cardName;// 卡名
	private String cardNo;//卡号
	private Map<String,String> importMap = new HashMap<String,String>();// 重要字段
	private String productCode;//产品编号
	private String productName;//产品名称
	private String InterestCount;//加息额度
	private String tangDouCount;//糖豆数量
	private boolean tangDouReturn;//糖豆标识位  为true则将糖豆计算成金额，为false则为空串
	private String tangDouExchangeAmt;//糖豆---金额
	private String zydCount;//增益豆数量
	private String tdTotalCount;//唐豆总数（幸运豆+增益豆+消费豆）
	private String xfdCount;//消费豆数量
	private String printerL51Str;//约享存，窗口期
	private String AgreementIdentification;//协议标识
	private String dayUpper;//存期天数
	private String inputNo;//输入的卡号(立得存使用)
	private String ldcType;//判断立得存类型(自享0，他享1，安享存2)
	private String ldcTXacctName;//存他享客户名
	private String SubAcctNo2;//子账户数量
	private String relaAcctNo;//积享存关联的如意存账号存放
	private String relaAcctName;//积享存关联的如意存名字存放
	private String privateLine;//私行快线标识符
	private String AccinputNo;//账号
	private int money;//存款金额
	private String MoneyUpper;//大写金额
	private String autoSaving;//自动转存
	private String createTime;//开户日期
	private String svrJrnlNo;//流水号
	private String valueDate;//开户日期
	private String endTime;//结束日期
	private String monthsUpper; //存期   
	private String monthsUpperStr; //存期   
	private String accountCardNo;//开户银行卡号
	private String PromptPages;//温馨提示页面标识
	private String supPass;// 授权密码
	private Map<String,Object> accountList = new HashMap<String,Object>();// 存放存单开户中的文件中字段的集合
	private String subPwd="";//
	private String svrDate;//核心日期
	private String Rate;//利率
	private String inte;//利息
	private String subCardNo;//子账户
	private String limit;//开户支付条件
	private String custNo;// 客户号
	private String accNo;	// 账号
	private String subAccNo;	//子账号
	private String payType;//支付类型:C卡支付，H现金支付
	private String jobs;
	private String readIdcard;
	private String errmsg;  // 错误原因
	private String serStopMsg; // 服务终止信息
	private String custSvrNo;//新建客户信息核心流水号
	private String billPath_fc;//存单正面
	private String billPath_rc;//存单背面
	private String qxGetHaveType;//千禧收益方式
	private String productedNameNew;//新产品名称
	private String ruleName;//规则名称
	//本人身份证信息
	private String idCardNo;//身份证号
	private String idCardName;//身份证名称
	private String address;// 户口地址
	private String sex;// 代理人性别
	private String qfjg;// 证件签发机关
	private String idCardface;
	private String idCardtitle;//身份证上头像照片
	private String idCardback;
	private String inspect;//核查结果
	private String birthday;//生日
	private String endDate;//有效期结束
	//代理人信息
	private String agentIdCardNo;//代理人身份证号
	private String agentIdCardName;//代理人身份证名称
	private String agentaddress;// 代理人户口地址
	private String agentsex;// 代理人性别
	private String agentqfjg;// 证件签发机关
	private String agentIdCardface;
	private String agentIdCardtitle;//代理人身份证上头像照片
	private String agentIdCardback;
	private String agentinspect;//核查结果
	private String agentBirthday;//生日
	private String agentEndDate;//有效期结束
	private String handSupTellerNo;//手动授权柜员号
	private int n;//当前页
	private int x;//上一页
	private String fid = "";	
	private String jijvOrPuhui;
	private String tangDSvrJrnlNo;//唐豆流水
	
	
	public String getProductedNameNew() {
		return productedNameNew;
	}
	public void setProductedNameNew(String productedNameNew) {
		this.productedNameNew = productedNameNew;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getQxGetHaveType() {
		return qxGetHaveType;
	}
	public void setQxGetHaveType(String qxGetHaveType) {
		this.qxGetHaveType = qxGetHaveType;
	}
	
	public String getTdTotalCount() {
		return tdTotalCount;
	}
	public void setTdTotalCount(String tdTotalCount) {
		this.tdTotalCount = tdTotalCount;
	}
	public String getXfdCount() {
		return xfdCount;
	}
	public void setXfdCount(String xfdCount) {
		this.xfdCount = xfdCount;
	}
	public String getZydCount() {
		return zydCount;
	}
	public void setZydCount(String zydCount) {
		this.zydCount = zydCount;
	}
	public String getTangDSvrJrnlNo() {
		return tangDSvrJrnlNo;
	}
	public void setTangDSvrJrnlNo(String tangDSvrJrnlNo) {
		this.tangDSvrJrnlNo = tangDSvrJrnlNo;
	}
	public String getJijvOrPuhui() {
		return jijvOrPuhui;
	}
	public void setJijvOrPuhui(String jijvOrPuhui) {
		this.jijvOrPuhui = jijvOrPuhui;
	}
	public String getInspect() {
		return inspect;
	}
	public void setInspect(String inspect) {
		this.inspect = inspect;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getIdCardface() {
		return idCardface;
	}
	public void setIdCardface(String idCardface) {
		this.idCardface = idCardface;
	}
	public String getIdCardtitle() {
		return idCardtitle;
	}
	public void setIdCardtitle(String idCardtitle) {
		this.idCardtitle = idCardtitle;
	}
	public String getIdCardback() {
		return idCardback;
	}
	public void setIdCardback(String idCardback) {
		this.idCardback = idCardback;
	}
	public String getTangDouCount() {
		return tangDouCount;
	}
	public void setTangDouCount(String tangDouCount) {
		this.tangDouCount = tangDouCount;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getSubAccNo() {
		return subAccNo;
	}
	public void setSubAccNo(String subAccNo) {
		this.subAccNo = subAccNo;
	}
	public String getDayUpper() {
		return dayUpper;
	}
	public void setDayUpper(String dayUpper) {
		this.dayUpper = dayUpper;
	}
	public String getRelaAcctNo() {
		return relaAcctNo;
	}
	public void setRelaAcctNo(String relaAcctNo) {
		this.relaAcctNo = relaAcctNo;
	}
	public String getAccinputNo() {
		return AccinputNo;
	}
	public void setAccinputNo(String accinputNo) {
		AccinputNo = accinputNo;
	}
	public String getRelaAcctName() {
		return relaAcctName;
	}
	public void setRelaAcctName(String relaAcctName) {
		this.relaAcctName = relaAcctName;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public String getAgreementIdentification() {
		return AgreementIdentification;
	}
	public void setAgreementIdentification(String agreementIdentification) {
		AgreementIdentification = agreementIdentification;
	}
	public String getPrivateLine() {
		return privateLine;
	}
	public void setPrivateLine(String privateLine) {
		this.privateLine = privateLine;
	}
	public String getPromptPages() {
		return PromptPages;
	}
	public void setPromptPages(String promptPages) {
		PromptPages = promptPages;
	}
	public Map<String, Object> getAccountList() {
		return accountList;
	}
	public void setAccountList(Map<String, Object> accountList) {
		this.accountList = accountList;
	}
	public String getMonthsUpper() {
		return monthsUpper;
	}
	public void setMonthsUpper(String monthsUpper) {
		this.monthsUpper = monthsUpper;
	}
	public String getBillPath_fc() {
		return billPath_fc;
	}
	public void setBillPath_fc(String billPath_fc) {
		this.billPath_fc = billPath_fc;
	}
	public String getBillPath_rc() {
		return billPath_rc;
	}
	public void setBillPath_rc(String billPath_rc) {
		this.billPath_rc = billPath_rc;
	}
	public String getHandSupTellerNo() {
		return handSupTellerNo;
	}
	public void setHandSupTellerNo(String handSupTellerNo) {
		this.handSupTellerNo = handSupTellerNo;
	}
	public String getAgentIdCardNo() {
		return agentIdCardNo;
	}
	public void setAgentIdCardNo(String agentIdCardNo) {
		this.agentIdCardNo = agentIdCardNo;
	}
	public String getAgentIdCardName() {
		return agentIdCardName;
	}
	public void setAgentIdCardName(String agentIdCardName) {
		this.agentIdCardName = agentIdCardName;
	}
	public String getAgentaddress() {
		return agentaddress;
	}
	public void setAgentaddress(String agentaddress) {
		this.agentaddress = agentaddress;
	}
	public String getAgentsex() {
		return agentsex;
	}
	public void setAgentsex(String agentsex) {
		this.agentsex = agentsex;
	}
	public String getAgentqfjg() {
		return agentqfjg;
	}
	public void setAgentqfjg(String agentqfjg) {
		this.agentqfjg = agentqfjg;
	}
	public String getAgentIdCardface() {
		return agentIdCardface;
	}
	public void setAgentIdCardface(String agentIdCardface) {
		this.agentIdCardface = agentIdCardface;
	}
	public String getAgentIdCardtitle() {
		return agentIdCardtitle;
	}
	public void setAgentIdCardtitle(String agentIdCardtitle) {
		this.agentIdCardtitle = agentIdCardtitle;
	}
	public String getAgentIdCardback() {
		return agentIdCardback;
	}
	public void setAgentIdCardback(String agentIdCardback) {
		this.agentIdCardback = agentIdCardback;
	}
	public String getAgentinspect() {
		return agentinspect;
	}
	public void setAgentinspect(String agentinspect) {
		this.agentinspect = agentinspect;
	}
	public String getAgentBirthday() {
		return agentBirthday;
	}
	public void setAgentBirthday(String agentBirthday) {
		this.agentBirthday = agentBirthday;
	}
	public String getAgentEndDate() {
		return agentEndDate;
	}
	public void setAgentEndDate(String agentEndDate) {
		this.agentEndDate = agentEndDate;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getJobs() {
		return jobs;
	}
	public void setJobs(String jobs) {
		this.jobs = jobs;
	}
	public String getReadIdcard() {
		return readIdcard;
	}
	public void setReadIdcard(String readIdcard) {
		this.readIdcard = readIdcard;
	}
	
	public String getIdCardName() {
		return idCardName;
	}
	public void setIdCardName(String idCardName) {
		this.idCardName = idCardName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getQfjg() {
		return qfjg;
	}
	public void setQfjg(String qfjg) {
		this.qfjg = qfjg;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getSupPass() {
		return supPass;
	}
	public void setSupPass(String supPass) {
		this.supPass = supPass;
	}
	public String getCustSvrNo() {
		return custSvrNo;
	}
	public void setCustSvrNo(String custSvrNo) {
		this.custSvrNo = custSvrNo;
	}
	public String getSvrDate() {
		return svrDate;
	}
	public void setSvrDate(String svrDate) {
		this.svrDate = svrDate;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getSerStopMsg() {
		return serStopMsg;
	}
	public void setSerStopMsg(String serStopMsg) {
		this.serStopMsg = serStopMsg;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getLdcTXacctName() {
		return ldcTXacctName;
	}
	public void setLdcTXacctName(String ldcTXacctName) {
		this.ldcTXacctName = ldcTXacctName;
	}
	public String getInputNo() {
		return inputNo;
	}
	public void setInputNo(String inputNo) {
		this.inputNo = inputNo;
	}
	public String getLdcType() {
		return ldcType;
	}
	public void setLdcType(String ldcType) {
		this.ldcType = ldcType;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getSubAcctNo2() {
		return SubAcctNo2;
	}
	public void setSubAcctNo2(String subAcctNo2) {
		SubAcctNo2 = subAcctNo2;
	}
	public String getAccountCardNo() {
		return accountCardNo;
	}
	public void setAccountCardNo(String accountCardNo) {
		this.accountCardNo = accountCardNo;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getPrinterL51Str() {
		return printerL51Str;
	}
	public void setPrinterL51Str(String printerL51Str) {
		this.printerL51Str = printerL51Str;
	}
	public String getAutoSaving() {
		return autoSaving;
	}
	public void setAutoSaving(String autoSaving) {
		this.autoSaving = autoSaving;
	}
	public String getSvrJrnlNo() {
		return svrJrnlNo;
	}
	public void setSvrJrnlNo(String svrJrnlNo) {
		this.svrJrnlNo = svrJrnlNo;
	}
	public String getSubPwd() {
		return subPwd;
	}
	public void setSubPwd(String subPwd) {
		this.subPwd = subPwd;
	}
	public String getInte() {
		return inte;
	}
	public void setInte(String inte) {
		this.inte = inte;
	}
	public String getRate() {
		return Rate;
	}
	public void setRate(String rate) {
		Rate = rate;
	}
	public String getValueDate() {
		return valueDate;
	}
	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getMonthsUpperStr() {
		return monthsUpperStr;
	}
	public void setMonthsUpperStr(String monthsUpperStr) {
		this.monthsUpperStr = monthsUpperStr;
	}
	public String getMoneyUpper() {
		return MoneyUpper;
	}
	public void setMoneyUpper(String moneyUpper) {
		MoneyUpper = moneyUpper;
	}
	public String getSubCardNo() {
		return subCardNo;
	}
	public void setSubCardNo(String subCardNo) {
		this.subCardNo = subCardNo;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	
	public String getTangDouExchangeAmt() {
		return tangDouExchangeAmt;
	}
	public void setTangDouExchangeAmt(String tangDouExchangeAmt) {
		this.tangDouExchangeAmt = tangDouExchangeAmt;
	}
	public boolean isTangDouReturn() {
		return tangDouReturn;
	}
	public void setTangDouReturn(boolean tangDouReturn) {
		this.tangDouReturn = tangDouReturn;
	}
	public String getInterestCount() {
		return InterestCount;
	}
	public void setInterestCount(String interestCount) {
		InterestCount = interestCount;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderPwd() {
		return orderPwd;
	}
	public void setOrderPwd(String orderPwd) {
		this.orderPwd = orderPwd;
	}
	public String getInputOrderPwd() {
		return inputOrderPwd;
	}
	public void setInputOrderPwd(String inputOrderPwd) {
		this.inputOrderPwd = inputOrderPwd;
	}
	public String getQrCode() {
		return QrCode;
	}
	public void setQrCode(String qrCode) {
		QrCode = qrCode;
	}
	public String getOrderAmount() {
		return OrderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		OrderAmount = orderAmount;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getApplyCode() {
		return ApplyCode;
	}
	public void setApplyCode(String applyCode) {
		ApplyCode = applyCode;
	}
	public String getBankAccount() {
		return BankAccount;
	}
	public void setBankAccount(String bankAccount) {
		BankAccount = bankAccount;
	}
	public String getCommiTtype() {
		return commiTtype;
	}
	public void setCommiTtype(String commiTtype) {
		this.commiTtype = commiTtype;
	}
	public int getKhqxn() {
		return khqxn;
	}
	public void setKhqxn(int khqxn) {
		this.khqxn = khqxn;
	}
	public int getKhqxpage() {
		return khqxpage;
	}
	public void setKhqxpage(int khqxpage) {
		this.khqxpage = khqxpage;
	}
	public int getSkhqxn() {
		return skhqxn;
	}
	public void setSkhqxn(int skhqxn) {
		this.skhqxn = skhqxn;
	}
	public int getSkhqxpage() {
		return skhqxpage;
	}
	public void setSkhqxpage(int skhqxpage) {
		this.skhqxpage = skhqxpage;
	}
	public int getKhryn() {
		return khryn;
	}
	public void setKhryn(int khryn) {
		this.khryn = khryn;
	}
	public int getKhrypage() {
		return khrypage;
	}
	public void setKhrypage(int khrypage) {
		this.khrypage = khrypage;
	}
	public int getSkhryn() {
		return skhryn;
	}
	public void setSkhryn(int skhryn) {
		this.skhryn = skhryn;
	}
	public int getSkhrypage() {
		return skhrypage;
	}
	public void setSkhrypage(int skhrypage) {
		this.skhrypage = skhrypage;
	}
	public int getKhyxn() {
		return khyxn;
	}
	public void setKhyxn(int khyxn) {
		this.khyxn = khyxn;
	}
	public int getKhyxpage() {
		return khyxpage;
	}
	public void setKhyxpage(int khyxpage) {
		this.khyxpage = khyxpage;
	}
	public int getSkhyxn() {
		return skhyxn;
	}
	public void setSkhyxn(int skhyxn) {
		this.skhyxn = skhyxn;
	}
	public int getSkhyxpage() {
		return skhyxpage;
	}
	public void setSkhyxpage(int skhyxpage) {
		this.skhyxpage = skhyxpage;
	}
	public int getKhxfn() {
		return khxfn;
	}
	public void setKhxfn(int khxfn) {
		this.khxfn = khxfn;
	}
	public int getKhxfpage() {
		return khxfpage;
	}
	public void setKhxfpage(int khxfpage) {
		this.khxfpage = khxfpage;
	}
	public int getSkhxfn() {
		return skhxfn;
	}
	public void setSkhxfn(int skhxfn) {
		this.skhxfn = skhxfn;
	}
	public int getSkhxfpage() {
		return skhxfpage;
	}
	public void setSkhxfpage(int skhxfpage) {
		this.skhxfpage = skhxfpage;
	}
	public int getKhldn() {
		return khldn;
	}
	public void setKhldn(int khldn) {
		this.khldn = khldn;
	}
	public int getKhldpage() {
		return khldpage;
	}
	public void setKhldpage(int khldpage) {
		this.khldpage = khldpage;
	}
	public int getSkhldn() {
		return skhldn;
	}
	public void setSkhldn(int skhldn) {
		this.skhldn = skhldn;
	}
	public int getSkhldpage() {
		return skhldpage;
	}
	public void setSkhldpage(int skhldpage) {
		this.skhldpage = skhldpage;
	}
	public int getKhjxn() {
		return khjxn;
	}
	public void setKhjxn(int khjxn) {
		this.khjxn = khjxn;
	}
	public int getKhjxpage() {
		return khjxpage;
	}
	public void setKhjxpage(int khjxpage) {
		this.khjxpage = khjxpage;
	}
	public int getSkhjxn() {
		return skhjxn;
	}
	public void setSkhjxn(int skhjxn) {
		this.skhjxn = skhjxn;
	}
	public int getSkhjxpage() {
		return skhjxpage;
	}
	public void setSkhjxpage(int skhjxpage) {
		this.skhjxpage = skhjxpage;
	}
	public int getKhryjn() {
		return khryjn;
	}
	public void setKhryjn(int khryjn) {
		this.khryjn = khryjn;
	}
	public int getKhryjpage() {
		return khryjpage;
	}
	public void setKhryjpage(int khryjpage) {
		this.khryjpage = khryjpage;
	}
	public int getSkhryjn() {
		return skhryjn;
	}
	public void setSkhryjn(int skhryjn) {
		this.skhryjn = skhryjn;
	}
	public int getSkhryjpage() {
		return skhryjpage;
	}
	public void setSkhryjpage(int skhryjpage) {
		this.skhryjpage = skhryjpage;
	}
	public int getKhaxcn() {
		return khaxcn;
	}
	public void setKhaxcn(int khaxcn) {
		this.khaxcn = khaxcn;
	}
	public int getKhaxcpage() {
		return khaxcpage;
	}
	public void setKhaxcpage(int khaxcpage) {
		this.khaxcpage = khaxcpage;
	}
	public int getSkhaxcn() {
		return skhaxcn;
	}
	public void setSkhaxcn(int skhaxcn) {
		this.skhaxcn = skhaxcn;
	}
	public int getSkhaxcpage() {
		return skhaxcpage;
	}
	public void setSkhaxcpage(int skhaxcpage) {
		this.skhaxcpage = skhaxcpage;
	}
	public String getSupTellerNo() {
		return supTellerNo;
	}
	public void setSupTellerNo(String supTellerNo) {
		this.supTellerNo = supTellerNo;
	}
	public Map<String, String> getImportMap() {
		return importMap;
	}
	public void setImportMap(Map<String, String> importMap) {
		this.importMap = importMap;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
}
