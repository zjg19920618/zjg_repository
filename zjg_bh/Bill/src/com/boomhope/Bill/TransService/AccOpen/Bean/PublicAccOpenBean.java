package com.boomhope.Bill.TransService.AccOpen.Bean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.boomhope.Bill.Framework.BaseTransBean;

/**
 * 存单开户公共字段bean
 * @author 
 *
 */
public class PublicAccOpenBean extends BaseTransBean{
	public String getMarks() {
		return marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	private String accNo;	// 账号
	private String accNo2;//账号-子账号
	private String subCardNo1;	//子账号所属卡号
	private String subAccNo;	//子账号
	private String accName;	// 户名
	private String billNo;	// 凭证号
	private String proCode; // 产品编号
	private String proName; // 产品名称
	private String amount;	// 本金
	private String yjlx;  // 预计利息
	private String inteAmt;  // 应付利息
	private String alreAmt;  // 已付利息
	private String realAmt; // 实付利息
	private String advnInit;//预付利息
	private String realPri; //实付本金
	private String XHSvrJrnlNo;//销户流水号
	private String subalreAmt;//子账户已支付利息
	private String khtd; // 唐豆个数
	private String shtdy; // 收回唐豆金额（元）
	private String openDate;// 开户日
	private String startDate;// 起息日
	private String dueDate;	// 到期日
	private String billPass;// 存单密码
	private String supPass;// 授权密码
	private String billIdType;// 存单对应的证件类型
	private String billIdCard;// 存单对应的身份证号
	private String billPath_fc;//存单正面
	private String billPath_rc;//存单背面
	private String exchflag;//自动转存标志
	private String svrDate;//核心日期
	private String telNo;//手机号
	private String checkStatus;//标记存单是否自动识别，1自动识别，2手动输入
	private String payType;//支付类型:C卡支付，H现金支付
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
	private String identification;//区分是否代理人标识（1有代理人 2无代理人）（不使用）
	
	//卡信息
	private String cardName;// 卡名
	private String cardNames;// 带*号的卡名
	private String cardNo;//卡号
	// 其它
	private String custNo;// 客户号
	private String custSvrNo;//新建客户信息核心流水号
	private String supTellerNo;//授权柜员号
	private String handSupTellerNo;//手动授权柜员号
	private String errmsg;  // 错误原因
	private String serStopMsg; // 服务终止信息
	private Map<String,String> importMap = new HashMap<String,String>();// 重要字段
	private String fileName;//文件名(身份证照片, 文件名是全路径, 到前置服务器下取文件)
	// 销户流水号
	private String canelBillId;
	private String canelBillDTransDate; 
	private String readIdcard;
	private String choose; //查询待打印/状态变更
	private int n;//当前页
	private int x;//上一页
	private int page; //一共页数
	private String bankCard ;//选中银行卡 
	private StringBuffer strbf = new StringBuffer();//打印
	private Set<String>select = new HashSet<String>();//打印
	private StringBuffer strbf2 = new StringBuffer();//状态变更
	private Set <String>select2 = new HashSet<String>();//状态变更
	private String mark ;//选中未打印存单标识
	private String marks;//选中多张未打印存单标识
	private String mark2 ;//选中状态变更标识
	private String marks2;//选中多个状态变更标识
	private String adminName; //管理员用户
	private String paw; //管理员密码
	private String paw2; //管理员密码修改
	private String busBillId; // 凭证id
	private String startBo; // 起始凭证号
	private String endBo; // 结尾凭证号
	private String nowBo; // 当前凭证号
	private String nowNumber; // 剩余存单数
	private String createDate; // 创建时间
	private String updateDate; // 修改时间
	private String policyIdentify;//存单标识（是否手动输入）
	private String judgeState; //判断提前支或者大于20万
	private String voice = "0";
	private float bala;
	private String fid = "";
	//开户
	private String monthsUpper; //存期   
	private String monthsUpperStr; //存期   
	
	private String accountCardNo;//开户银行卡号
	private String PromptPages;//温馨提示页面标识
	
	private String idType;//证件类型
	private String idNo;//证件号
	private String idInst;//发证机构
	private Double balance;//余额
	private String balanceStr;
	private String payCond;//支付条件
	private Map<String,Object> accountList = new HashMap<String,Object>();// 存放存单开户中的文件中字段的集合
	private int money;//存款金额
	private String MoneyUpper;//大写金额
	private String autoSaving;//自动转存
	private String cardPwd;//
	private String subPwd="";//
	private String Rate;//利率
	private String inte;//利息
	private String createTime;//开户日期
	private String svrJrnlNo;//流水号
	private String valueDate;//开户日期
	private String endTime;//结束日期
	private String subCardNo;//子账户
	private String limit;//开户支付条件
	private String productCode;//产品编号
	private String productName;//产品名称
	private String tangDouCount;//糖豆数量
	private boolean tangDouReturn;//糖豆标识位  为true则将糖豆计算成金额，为false则为空串
	private String tangDouExchangeAmt;//糖豆---金额
	private String printerL51Str;//约享存，窗口期
	private String AgreementIdentification;//协议标识
	private String dayUpper;//存期天数
	private String inputNo;//输入的卡号(立得存使用)
	private String ldcType;//判断立得存类型(自享0，他享1，安享存2)
	private String ldcTXacctName;//存他享客户名
	private String SubAcctNo2;//子账户数量
	private String InterestCount;//加息额度
	private String relaAcctNo;//积享存关联的如意存账号存放
	private String relaAcctName;//积享存关联的如意存名字存放
	private String privateLine;//私行快线标识符
	private String AccinputNo;//账号
	
	
	private int qxn;//千禧当前页
	private int qxpage; //千禧一共页数
	
	private int ryn;//如意当前页
	private int rypage; //如意一共页数

	private String subAccNoCancel;
	private String billCancel;

	private int yxn;//约享当前页
	private int yxpage; //约享一共页数
	
	private int ldn;//立得存当前页
	private int ldpage; //立得存一共页数
	
	private int jxn;//积享存当前页
	private int jxpage; //积享存一共页数
	
	private int ryjn;//如意存+存当前页
	private int ryjpage; //如意存+存一共页数
	
	private int xfn;//幸福1+1当前页
	private int xfpage; //幸福1+1一共页数
	
	private String bankCardNumber; //银行卡第二次校验标识
	
	private String jobs;

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

	public String getJobs() {
		return jobs;
	}

	public void setJobs(String jobs) {
		this.jobs = jobs;
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

	public String getQrCode() {
		return QrCode;
	}

	public void setQrCode(String qrCode) {
		QrCode = qrCode;
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

	public String getAccinputNo() {
		return AccinputNo;
	}

	public void setAccinputNo(String accinputNo) {
		AccinputNo = accinputNo;
	}

	public String getXHSvrJrnlNo() {
		return XHSvrJrnlNo;
	}

	public void setXHSvrJrnlNo(String xHSvrJrnlNo) {
		XHSvrJrnlNo = xHSvrJrnlNo;
	}

	
	public String getBankCardNumber() {
		return bankCardNumber;
	}

	public void setBankCardNumber(String bankCardNumber) {
		this.bankCardNumber = bankCardNumber;
	}

	public String getPrivateLine() {
		return privateLine;
	}

	public void setPrivateLine(String privateLine) {
		this.privateLine = privateLine;
	}

	public String getInterestCount() {
		return InterestCount;
	}

	public void setInterestCount(String interestCount) {
		InterestCount = interestCount;
	}

	public String getSubAcctNo2() {
		return SubAcctNo2;
	}

	public void setSubAcctNo2(String subAcctNo2) {
		SubAcctNo2 = subAcctNo2;
	}

	public String getLdcTXacctName() {
		return ldcTXacctName;
	}

	public void setLdcTXacctName(String ldcTXacctName) {
		this.ldcTXacctName = ldcTXacctName;
	}

	public String getLdcType() {
		return ldcType;
	}

	public void setLdcType(String ldcType) {
		this.ldcType = ldcType;
	}

	public String getInputNo() {
		return inputNo;
	}

	public void setInputNo(String inputNo) {
		this.inputNo = inputNo;
	}

	public String getRelaAcctName() {
		return relaAcctName;
	}

	public void setRelaAcctName(String relaAcctName) {
		this.relaAcctName = relaAcctName;
	}

	public String getRelaAcctNo() {
		return relaAcctNo;
	}

	public void setRelaAcctNo(String relaAcctNo) {
		this.relaAcctNo = relaAcctNo;
	}

	public String getPrinterL51Str() {
		return printerL51Str;
	}

	public void setPrinterL51Str(String printerL51Str) {
		this.printerL51Str = printerL51Str;
	}

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	
	public String getDayUpper() {
		return dayUpper;
	}

	public void setDayUpper(String dayUpper) {
		this.dayUpper = dayUpper;
	}

	public float getBala() {
		return bala;
	}

	public void setBala(float bala) {
		this.bala = bala;
	}

	public String getCardNames() {
		return cardNames;
	}

	public void setCardNames(String cardNames) {
		this.cardNames = cardNames;
	}

	public String getSvrJrnlNo() {
		return svrJrnlNo;
	}

	public void setSvrJrnlNo(String svrJrnlNo) {
		this.svrJrnlNo = svrJrnlNo;
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

	public String getTangDouCount() {
		return tangDouCount;
	}

	public void setTangDouCount(String tangDouCount) {
		this.tangDouCount = tangDouCount;
	}

	public String getValueDate() {
		return valueDate;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}


	public String getSubCardNo1() {
		return subCardNo1;
	}

	public void setSubCardNo1(String subCardNo1) {
		this.subCardNo1 = subCardNo1;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCardPwd() {
		return cardPwd;
	}

	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
	}

	public String getSubPwd() {
		return subPwd;
	}

	public void setSubPwd(String subPwd) {
		this.subPwd = subPwd;
	}

	public String getAutoSaving() {
		return autoSaving;
	}

	public void setAutoSaving(String autoSaving) {
		this.autoSaving = autoSaving;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getMoneyUpper() {
		return MoneyUpper;
	}

	public void setMoneyUpper(String moneyUpper) {
		MoneyUpper = moneyUpper;
	}

	public String getMonthsUpper() {
		return monthsUpper;
	}

	public void setMonthsUpper(String monthsUpper) {
		this.monthsUpper = monthsUpper;
	}

	public String getMonthsUpperStr() {
		return monthsUpperStr;
	}

	public void setMonthsUpperStr(String monthsUpperStr) {
		this.monthsUpperStr = monthsUpperStr;
	}

	public Map<String, Object> getAccountList() {
		return accountList;
	}

	public void setAccountList(Map<String, Object> accountList) {
		this.accountList = accountList;
	}

	public String getPayCond() {
		return payCond;
	}

	public void setPayCond(String payCond) {
		this.payCond = payCond;
	}

	public String getBalanceStr() {
		return balanceStr;
	}

	public void setBalanceStr(String balanceStr) {
		this.balanceStr = balanceStr;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getIdInst() {
		return idInst;
	}

	public void setIdInst(String idInst) {
		this.idInst = idInst;
	}

	public String getSubalreAmt() {
		return subalreAmt;
	}

	public void setSubalreAmt(String subalreAmt) {
		this.subalreAmt = subalreAmt;
	}

	public String getAdvnInit() {
		return advnInit;
	}

	public void setAdvnInit(String advnInit) {
		this.advnInit = advnInit;
	}

	public String getAccNo2() {
		return accNo2;
	}

	public void setAccNo2(String accNo2) {
		this.accNo2 = accNo2;
	}

	public String getSubCardNo() {
		return subCardNo;
	}

	public void setSubCardNo(String subCardNo) {
		this.subCardNo = subCardNo;
	}

	public String getSubAccNo() {
		return subAccNo;
	}

	public void setSubAccNo(String subAccNo) {
		this.subAccNo = subAccNo;
	}

	public int getQxn() {
		return qxn;
	}

	public void setQxn(int qxn) {
		this.qxn = qxn;
	}

	public int getQxpage() {
		return qxpage;
	}

	public void setQxpage(int qxpage) {
		this.qxpage = qxpage;
	}

	public int getRyn() {
		return ryn;
	}

	public void setRyn(int ryn) {
		this.ryn = ryn;
	}

	public int getRypage() {
		return rypage;
	}

	public void setRypage(int rypage) {
		this.rypage = rypage;
	}

	public int getYxn() {
		return yxn;
	}

	public void setYxn(int yxn) {
		this.yxn = yxn;
	}

	public int getYxpage() {
		return yxpage;
	}

	public void setYxpage(int yxpage) {
		this.yxpage = yxpage;
	}

	public int getLdn() {
		return ldn;
	}

	public void setLdn(int ldn) {
		this.ldn = ldn;
	}

	public int getLdpage() {
		return ldpage;
	}

	public void setLdpage(int ldpage) {
		this.ldpage = ldpage;
	}

	public int getJxn() {
		return jxn;
	}

	public void setJxn(int jxn) {
		this.jxn = jxn;
	}

	public int getJxpage() {
		return jxpage;
	}

	public void setJxpage(int jxpage) {
		this.jxpage = jxpage;
	}

	public int getRyjn() {
		return ryjn;
	}

	public void setRyjn(int ryjn) {
		this.ryjn = ryjn;
	}

	public int getRyjpage() {
		return ryjpage;
	}

	public void setRyjpage(int ryjpage) {
		this.ryjpage = ryjpage;
	}

	public int getXfn() {
		return xfn;
	}

	public void setXfn(int xfn) {
		this.xfn = xfn;
	}

	public int getXfpage() {
		return xfpage;
	}

	public void setXfpage(int xfpage) {
		this.xfpage = xfpage;
	}


	
	public String getSubAccNoCancel() {
		return subAccNoCancel;
	}

	public void setSubAccNoCancel(String subAccNoCancel) {
		this.subAccNoCancel = subAccNoCancel;
	}

	public String getBillCancel() {
		return billCancel;
	}

	public void setBillCancel(String billCancel) {
		this.billCancel = billCancel;
	}


	public String getAgreementIdentification() {
		return AgreementIdentification;
	}

	public void setAgreementIdentification(String agreementIdentification) {
		AgreementIdentification = agreementIdentification;
	}

	public String getPromptPages() {
		return PromptPages;
	}

	public void setPromptPages(String promptPages) {
		PromptPages = promptPages;
	}

	public String getAccountCardNo() {
		return accountCardNo;
	}

	public void setAccountCardNo(String accountCardNo) {
		this.accountCardNo = accountCardNo;
	}

	public String getHandSupTellerNo() {
		return handSupTellerNo;
	}

	public void setHandSupTellerNo(String handSupTellerNo) {
		this.handSupTellerNo = handSupTellerNo;
	}

	public String getChoose() {
		return choose;
	}

	public void setChoose(String choose) {
		this.choose = choose;
	}



	public String getJudgeState() {
		return judgeState;
	}

	public void setJudgeState(String judgeState) {
		this.judgeState = judgeState;
	}

	public String getPolicyIdentify() {
		return policyIdentify;
	}

	public void setPolicyIdentify(String policyIdentify) {
		this.policyIdentify = policyIdentify;
	}


	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
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



	public String getFid() {
		return fid;
	}



	public void setFid(String fid) {
		this.fid = fid;
	}



	public String getTelNo() {
		return telNo;
	}



	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}



	public String getSvrDate() {
		return svrDate;
	}



	public void setSvrDate(String svrDate) {
		this.svrDate = svrDate;
	}



	public String getExchflag() {
		return exchflag;
	}



	public void setExchflag(String exchflag) {
		this.exchflag = exchflag;
	}



	public String getIdentification() {
		return identification;
	}



	public void setIdentification(String identification) {
		this.identification = identification;
	}

	
	public String getAgentinspect() {
		return agentinspect;
	}



	public void setAgentinspect(String agentinspect) {
		this.agentinspect = agentinspect;
	}



	public String getInspect() {
		return inspect;
	}



	public void setInspect(String inspect) {
		this.inspect = inspect;
	}
	/***
	 * 设置账号
	 * @param accNo
	 */
	public void setAccNo(String accNo){
		this.accNo = accNo;
	}
	


	/***
	 * 获取账号
	 * @return
	 */
	public String getAccNo(){
		return this.accNo;
	}
	
	/***
	 * 设置户名
	 * @param accName
	 */
	public void setAccName(String accName){
		this.accName = accName;
	}
	
	/***
	 * 获取户名
	 * @return
	 */
	public String getAccName(){
		return this.accName;
	}
	
	/***
	 * 设置凭证号
	 * @param billNo
	 */
	public void setBillNo(String billNo){
		this.billNo = billNo;
	}
	
	/***
	 * 获取凭证号
	 * @return
	 */
	public String getBillNo(){
		return this.billNo;
	}
	
	/***
	 * 设置金额
	 * @param amount
	 */
	public void setAmount(String amount){
		this.amount = amount;
	}
	
	/***
	 * 获取金额
	 * @return
	 */
	public String getAmount(){
		return this.amount;
	}
	
	/***
	 * 设置开户日期
	 * @param openDate
	 */
	public void setOpenDate(String openDate){
		this.openDate = openDate;
	}
	
	/***
	 * 获取开户日期
	 * @return
	 */
	public String getOpenDate(){
		return this.openDate;
	}
	
	/***
	 * 设置到日期
	 * @param dueDate
	 */
	public void setDueDate(String dueDate){
		this.dueDate = dueDate;
	}
	
	/***
	 * 获取到期日
	 * @return
	 */
	public String getDueDate(){
		return this.dueDate;
	}
	
	/**
	 * 错误原因
	 * @return
	 */
	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	

	public String getSerStopMsg() {
		return serStopMsg;
	}

	public void setSerStopMsg(String serStopMsg) {
		this.serStopMsg = serStopMsg;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getYjlx() {
		return yjlx;
	}

	public void setYjlx(String yjlx) {
		this.yjlx = yjlx;
	}

	public String getInteAmt() {
		return inteAmt;
	}

	public void setInteAmt(String inteAmt) {
		this.inteAmt = inteAmt;
	}

	public String getAlreAmt() {
		return alreAmt;
	}

	public void setAlreAmt(String alreAmt) {
		this.alreAmt = alreAmt;
	}

	public String getRealAmt() {
		return realAmt;
	}

	public void setRealAmt(String realAmt) {
		this.realAmt = realAmt;
	}

	public String getKhtd() {
		return khtd;
	}

	public void setKhtd(String khtd) {
		this.khtd = khtd;
	}

	public String getShtdy() {
		return shtdy;
	}

	public void setShtdy(String shtdy) {
		this.shtdy = shtdy;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Map<String, String> getImportMap() {
		return importMap;
	}

	public void setImportMap(Map<String, String> importMap) {
		this.importMap = importMap;
	}

	public String getSupTellerNo() {
		return supTellerNo;
	}

	public void setSupTellerNo(String supTellerNo) {
		this.supTellerNo = supTellerNo;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	public String getIdCardName() {
		return idCardName;
	}

	public void setIdCardName(String idCardName) {
		this.idCardName = idCardName;
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
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}



	public String getAgentaddress() {
		return agentaddress;
	}



	public void setAgentaddress(String agentaddress) {
		this.agentaddress = agentaddress;
	}



	public String getAgentqfjg() {
		return agentqfjg;
	}



	public void setAgentqfjg(String agentqfjg) {
		this.agentqfjg = agentqfjg;
	}



	public String getAgentsex() {
		return agentsex;
	}



	public void setAgentsex(String agentsex) {
		this.agentsex = agentsex;
	}



	public String getRealPri() {
		return realPri;
	}



	public void setRealPri(String realPri) {
		this.realPri = realPri;
	}



	public String getBillPass() {
		return billPass;
	}



	public void setBillPass(String billPass) {
		this.billPass = billPass;
	}



	public String getSupPass() {
		return supPass;
	}



	public void setSupPass(String supPass) {
		this.supPass = supPass;
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



	public String getIdCardface() {
		return idCardface;
	}



	public void setIdCardface(String idCardface) {
		this.idCardface = idCardface;
	}



	public String getIdCardback() {
		return idCardback;
	}



	public void setIdCardback(String idCardback) {
		this.idCardback = idCardback;
	}



	public String getAgentIdCardface() {
		return agentIdCardface;
	}



	public void setAgentIdCardface(String agentIdCardface) {
		this.agentIdCardface = agentIdCardface;
	}



	public String getAgentIdCardback() {
		return agentIdCardback;
	}



	public void setAgentIdCardback(String agentIdCardback) {
		this.agentIdCardback = agentIdCardback;
	}



	public String getBillIdType() {
		return billIdType;
	}



	public void setBillIdType(String billIdType) {
		this.billIdType = billIdType;
	}



	public String getBillIdCard() {
		return billIdCard;
	}



	public void setBillIdCard(String billIdCard) {
		this.billIdCard = billIdCard;
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



	public String getReadIdcard() {
		return readIdcard;
	}



	public void setReadIdcard(String readIdcard) {
		this.readIdcard = readIdcard;
	}



	public String getAgentIdCardtitle() {
		return agentIdCardtitle;
	}



	public void setAgentIdCardtitle(String agentIdCardtitle) {
		this.agentIdCardtitle = agentIdCardtitle;
	}



	public String getIdCardtitle() {
		return idCardtitle;
	}



	public void setIdCardtitle(String idCardtitle) {
		this.idCardtitle = idCardtitle;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getPaw() {
		return paw;
	}

	public void setPaw(String paw) {
		this.paw = paw;
	}

	public String getPaw2() {
		return paw2;
	}

	public void setPaw2(String paw2) {
		this.paw2 = paw2;
	}

	public String getStartBo() {
		return startBo;
	}

	public void setStartBo(String startBo) {
		this.startBo = startBo;
	}

	public String getEndBo() {
		return endBo;
	}

	public void setEndBo(String endBo) {
		this.endBo = endBo;
	}

	public String getNowBo() {
		return nowBo;
	}

	public void setNowBo(String nowBo) {
		this.nowBo = nowBo;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getNowNumber() {
		return nowNumber;
	}

	public void setNowNumber(String nowNumber) {
		this.nowNumber = nowNumber;
	}

	public String getBusBillId() {
		return busBillId;
	}

	public void setBusBillId(String busBillId) {
		this.busBillId = busBillId;
	}

	public StringBuffer getStrbf() {
		return strbf;
	}

	public void setStrbf(StringBuffer strbf) {
		this.strbf = strbf;
	}

	public String getMark2() {
		return mark2;
	}

	public void setMark2(String mark2) {
		this.mark2 = mark2;
	}

	public String getMarks2() {
		return marks2;
	}

	public void setMarks2(String marks2) {
		this.marks2 = marks2;
	}

	public StringBuffer getStrbf2() {
		return strbf2;
	}

	public void setStrbf2(StringBuffer strbf2) {
		this.strbf2 = strbf2;
	}

	public Set getSelect() {
		return select;
	}

	public void setSelect(Set select) {
		this.select = select;
	}

	public Set getSelect2() {
		return select2;
	}

	public void setSelect2(Set select2) {
		this.select2 = select2;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderPwd() {
		return orderPwd;
	}

	public void setOrderPwd(String orderPwd) {
		this.orderPwd = orderPwd;
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

	public String getCommiTtype() {
		return commiTtype;
	}

	public void setCommiTtype(String commiTtype) {
		this.commiTtype = commiTtype;
	}
	
	public String getInputOrderPwd() {
		return inputOrderPwd;
	}

	public void setInputOrderPwd(String inputOrderPwd) {
		this.inputOrderPwd = inputOrderPwd;
	}
	
	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAgentEndDate() {
		return agentEndDate;
	}

	public void setAgentEndDate(String agentEndDate) {
		this.agentEndDate = agentEndDate;
	}

	public String getAgentBirthday() {
		return agentBirthday;
	}

	public void setAgentBirthday(String agentBirthday) {
		this.agentBirthday = agentBirthday;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCustSvrNo() {
		return custSvrNo;
	}

	public void setCustSvrNo(String custSvrNo) {
		this.custSvrNo = custSvrNo;
	}
}
