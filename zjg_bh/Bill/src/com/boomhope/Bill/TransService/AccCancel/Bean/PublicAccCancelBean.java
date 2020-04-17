package com.boomhope.Bill.TransService.AccCancel.Bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boomhope.Bill.Framework.BaseTransBean;

/**
 * 存单销户公共字段bean
 * @author 
 *
 */

public class PublicAccCancelBean extends BaseTransBean{
	private String AccCancelType;//撤销方式   001:银行卡;002电子账户;003:唐行宝
	private String subAccNoCancel;//判断卡下子账户、普通账户(0:卡下子账号;1：普通账号)
	private String haveAcc;//是否有存单  0：有;1：无
	
	//55域卡信息
	private String arqc;//ARQC
	private String arqcSrcData;//ARQC生成数据
	private String termChkValue;//终端验证结果
	private String appAcctSeq;//应用主账户序列号
	private String issAppData;//发卡行应用数据
	private String cardPov;//卡片有效期
	private String erCiInfo;//二磁信息
	
	private String cardPwd;//银行卡密码
	private String cardIsPin;//是否验密值为1时则必须进行验密
	
	//代理人信息
	private String readAgentIdNo;//读取的代理人身份证号
	private String readAgentIdName;//读取的代理人身份证名称
	private String agentaddress;// 代理人户口地址
	private String agentsex;// 代理人性别
	private String agentqfjg;// 证件签发机关
	private String agentIdface;
	private String agentIdback;
	private String agentIdtitle;//代理人身份证上头像照片
	private String agentBirthday;//生日
	private String agentEndDate;//有效期结束
	private String agentIdType;//身份证类型
	private String agentIdNo;//代理人身份证号
	private String agentIdName;//代理人身份证名称
	//本人身份证信息
	private String readIdNo;//读取的身份证号
	private String readIdName;//读取的身份证名称
	private String address;// 户口地址
	private String sex;// 性别
	private String qfjg;// 证件签发机关
	private String idface;
	private String idback;
	private String idtitle;//身份证上头像照片
	private String birthday;//生日
	private String endDate;//有效期结束
	private String idType;//身份证类型
	private String idNo;//身份证号
	private String idName;//身份证名称
	
	//卡信息
	private String cardName;// 卡名
	private String cardNames;// 带*号的卡名
	private String cardNo;//卡号
	private String bindIdNo;//绑定的电子账户
	private String eleCardPwd;//电子账户密码
	
	//代理核对
	private String checkResult;//代理人核对结果（0：我行 。1：非我行）
	//证件信息查询
	private String custNo;// 客户号
	//联网核查
	private String inspect;//本人联网核查结果信息
	private String agentInspect;//代理人联网核查结果信息
	
	//人脸识别
	private String checkFaceMsg;//照片较验结果
	private String faceCompareVal;//比较值
	private String cameraCount;//拍照次数
	
	//账户信息查询
	private String accNo;	// 输入的账号
	private String accNo2;//账号-子账号
	private String subCardNo;	//子账号所属卡号
	private String accName;	// 户名
	private String billType;//凭证种类
	private String billNo;	// 输入的凭证号
	private String drawCoun;//是否有密码
	private String openQlt;//存单户性质
	private String proCode; // 产品编号
	private String exchflag;//自动转存标志
	private String svrDate;//核心日期
	private String startDate;// 起息日
	private String subAccNo;	//子账号
	private String OpenInstNo;//存单开户机构号
	private String openDate;// 开户日
	private String dueDate;	// 到期日
	private String proName; // 产品名称
	private String cancelDepTerm;//销户存期
	private String depTerm;//存期
	private String openRate;//利率
	private String balance;//结存额
	private String channel;//办理渠道
	private String printState;//打印状态
	private String accState;//存单状态
	private String certNo;//返回的凭证号
	private String billIdNo;//返回的存单身份证号
	private String billIdName;//返回的存单户名
	private String accFirstNo;//第一次识别的账号
	private String billFirstNo;//第一次识别的凭证号
	private String backAccNo;//查询出返回的账号
	
	//销户信息
	private String noPass;//无密码
	private String billPass;//存单密码
	private String EaccPass;//电子账户密码
	private String amount;	// 本金
	private String yjlx;  // 预计利息
	private String inteAmt;  // 应付利息
	private String alreAmt;  // 已付利息
	private String realAmt; // 实付利息
	private String advnInit;//预付利息
	private String realPri; //实付本金
	private String XFD_COUNT;//消费豆
	private String XHSvrJrnlNo;//销户流水号
	
	private String shtds; // 唐豆个数
	private String shtdy; // 收回唐豆金额（元）
	private String tdPayAmt;//支取金额
	private String demTerm;//存单存期
	private String subalreAmt;//子账户已支付利息
	
	private String billIdType;// 存单对应的证件类型
	private String billIdCard;// 存单对应的身份证号
	private String billPath_fc;//存单正面
	private String billPath_rc;//存单背面
	
	// 其它
	private Map<String,List> importMap = new HashMap<String,List>();// 重要字段
	private Map<String, Object> map = new HashMap<String, Object>();//读卡器读取的信息
	private String accCncelType;//0：普通账户    1：卡下子账户     2：电子子账户
	private String havAgentFlag; //选择是否代理人办理(0：无代理人  1：有代理人)
	private String checkMod;//CHECK_MOD	认证方式	1	1 口令2 指纹 3 口令加指纹
	private String finPriLen;//FIN_PRI_LEN	指纹长度
	private String finPriVal;//FIN_PRI_VAL	指纹值	512
	private String supTellerNo;//授权柜员号
	private String supPass;//授权密码
	private String checkStatus;//标记存单是否自动识别，1自动识别，2手动输入，3无存单
	private String judgeState; //判断提前支取 1.提前支取 0 到期支取
	private String amtState;//判断是否大于等于5万(1大于等于0小于)
	private String fileName;//文件名(身份证照片, 文件名是全路径, 到前置服务器下取文件)
	private String fid;//插入存单生产流程序号
	private int subPages;//子账户页数
	private int ePages;//电子账户页数
	private int nowPage=1;//当前页数
	private String jwResult;//鉴伪结果
	private String errmsg;//凭条打印机错误信息
	private String tangErrMsg;//唐豆兑付错误信息
	private String tangChaErrMsg;//唐豆查询错误信息
	private String XYDChaErrMsg;//幸运豆查询错误信息
	private String DFTang;//唐豆是否兑付成功 0-兑付失败 1 兑付成功
	private String pringPT;//是否打印凭条 0-打印 1 不打印
	private String tangXB;//是否成功转入唐行宝 0-失败 1-成功
	private String isTangXB;//银行卡是否绑定唐行宝 0-没有绑定 1-未绑定
	private String tangCode;//唐豆调用错误码
	
	//唐行宝
	private String txbInputAmt;//唐行宝转入金额
	private String txbSubAccNo;//唐行宝子账户
	private String txbProCode;//唐行宝产品代码
	
	//签字区
	private String XHBankMsgIsSign;//是否有销户转卡电子签名(Y-已签名)
	private String tellerIsChecked;//授权之前是否确认销户信息(1-已确认销户信息 2-允许授权)
	
	private String jwState;//鉴伪次数标识(1-鉴伪通过金额超限后授权标识)
	private String tellNo1;//授权柜员1
	private String tellNo2;//授权柜员2
	private String tellNo3;//授权柜员3
	private String tellNoState;//是否已对第一个柜员授权(1-鉴伪失败金额超限第一次授权标识，2-鉴伪失败金额超限第二次授权标识/第一次鉴伪通过但第二次鉴伪失败第二次授权标识)
	private String isCheckPass;//是否验密(0-否，1-是)
	private String firstCardNo;//第一次录入银行卡号
	private String secondCardNocard;//两次卡号不一致标识(1-不一致)、
	private String inCardOrHandCard;//选择插入或手输卡号标识(1-插卡 2手输)
	private String xfdAmt;//消费豆金额
	private String tdSvrJrnlNo;//唐豆收回流水
	private String cNo;//无存单银行卡号
	private String rate;//销户利率
	private String yuanAccNo;//存单原账号
	private String totalAmt;//存折余额
	private String authNoCode;//授权主交易码
	private String reCamera;//重新拍照标识（0-重新拍照）
	private String txbSvrJrnlNo;//转入唐行宝流水
	private String preDate;//预约日期
	
	public String getCameraCount() {
		return cameraCount;
	}
	public void setCameraCount(String cameraCount) {
		this.cameraCount = cameraCount;
	}
	public String getCheckFaceMsg() {
		return checkFaceMsg;
	}
	public void setCheckFaceMsg(String checkFaceMsg) {
		this.checkFaceMsg = checkFaceMsg;
	}
	public String getFaceCompareVal() {
		return faceCompareVal;
	}
	public void setFaceCompareVal(String faceCompareVal) {
		this.faceCompareVal = faceCompareVal;
	}
	public String getEleCardPwd() {
		return eleCardPwd;
	}
	public void setEleCardPwd(String eleCardPwd) {
		this.eleCardPwd = eleCardPwd;
	}
	public String getIsTangXB() {
		return isTangXB;
	}
	public void setIsTangXB(String isTangXB) {
		this.isTangXB = isTangXB;
	}
	public String getTangXB() {
		return tangXB;
	}
	public void setTangXB(String tangXB) {
		this.tangXB = tangXB;
	}
	public String getPringPT() {
		return pringPT;
	}
	public void setPringPT(String pringPT) {
		this.pringPT = pringPT;
	}
	public String getDFTang() {
		return DFTang;
	}
	public void setDFTang(String dFTang) {
		DFTang = dFTang;
	}
	public String getTangErrMsg() {
		return tangErrMsg;
	}
	public void setTangErrMsg(String tangErrMsg) {
		this.tangErrMsg = tangErrMsg;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getCheckMod() {
		return checkMod;
	}
	public void setCheckMod(String checkMod) {
		this.checkMod = checkMod;
	}
	public String getFinPriLen() {
		return finPriLen;
	}
	public void setFinPriLen(String finPriLen) {
		this.finPriLen = finPriLen;
	}
	public String getFinPriVal() {
		return finPriVal;
	}
	public void setFinPriVal(String finPriVal) {
		this.finPriVal = finPriVal;
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
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getPrintState() {
		return printState;
	}
	public void setPrintState(String printState) {
		this.printState = printState;
	}
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	public int getSubPages() {
		return subPages;
	}
	public void setSubPages(int subPages) {
		this.subPages = subPages;
	}
	public int getePages() {
		return ePages;
	}
	public void setePages(int ePages) {
		this.ePages = ePages;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getIdName() {
		return idName;
	}
	public void setIdName(String idName) {
		this.idName = idName;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getCardPwd() {
		return cardPwd;
	}

	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
	}

	public String getArqc() {
		return arqc;
	}

	public void setArqc(String arqc) {
		this.arqc = arqc;
	}

	public String getArqcSrcData() {
		return arqcSrcData;
	}

	public void setArqcSrcData(String arqcSrcData) {
		this.arqcSrcData = arqcSrcData;
	}

	public String getTermChkValue() {
		return termChkValue;
	}

	public void setTermChkValue(String termChkValue) {
		this.termChkValue = termChkValue;
	}

	public String getAppAcctSeq() {
		return appAcctSeq;
	}

	public void setAppAcctSeq(String appAcctSeq) {
		this.appAcctSeq = appAcctSeq;
	}

	public String getIssAppData() {
		return issAppData;
	}

	public void setIssAppData(String issAppData) {
		this.issAppData = issAppData;
	}

	public String getCardPov() {
		return cardPov;
	}

	public void setCardPov(String cardPov) {
		this.cardPov = cardPov;
	}

	public String getErCiInfo() {
		return erCiInfo;
	}

	public void setErCiInfo(String erCiInfo) {
		this.erCiInfo = erCiInfo;
	}

	public String getHaveAcc() {
		return haveAcc;
	}

	public void setHaveAcc(String haveAcc) {
		this.haveAcc = haveAcc;
	}

	public String getAccCancelType() {
		return AccCancelType;
	}

	public void setAccCancelType(String accCancelType) {
		AccCancelType = accCancelType;
	}
	public String getAgentIdNo() {
		return agentIdNo;
	}
	public void setAgentIdNo(String agentIdNo) {
		this.agentIdNo = agentIdNo;
	}
	public String getAgentIdName() {
		return agentIdName;
	}
	public void setAgentIdName(String agentIdName) {
		this.agentIdName = agentIdName;
	}
	public String getAgentIdType() {
		return agentIdType;
	}
	public void setAgentIdType(String agentIdType) {
		this.agentIdType = agentIdType;
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
	public String getAgentIdface() {
		return agentIdface;
	}
	public void setAgentIdface(String agentIdface) {
		this.agentIdface = agentIdface;
	}
	public String getAgentIdback() {
		return agentIdback;
	}
	public void setAgentIdback(String agentIdback) {
		this.agentIdback = agentIdback;
	}
	public String getAgentIdtitle() {
		return agentIdtitle;
	}
	public void setAgentIdtitle(String agentIdtitle) {
		this.agentIdtitle = agentIdtitle;
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
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
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
	public String getIdface() {
		return idface;
	}
	public void setIdface(String idface) {
		this.idface = idface;
	}
	public String getIdback() {
		return idback;
	}
	public void setIdback(String idback) {
		this.idback = idback;
	}
	public String getIdtitle() {
		return idtitle;
	}
	public void setIdtitle(String idtitle) {
		this.idtitle = idtitle;
	}
	public String getInspect() {
		return inspect;
	}
	public void setInspect(String inspect) {
		this.inspect = inspect;
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
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getCardNames() {
		return cardNames;
	}
	public void setCardNames(String cardNames) {
		this.cardNames = cardNames;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getSvrDate() {
		return svrDate;
	}
	public void setSvrDate(String svrDate) {
		this.svrDate = svrDate;
	}
	public String getSupTellerNo() {
		return supTellerNo;
	}
	public void setSupTellerNo(String supTellerNo) {
		this.supTellerNo = supTellerNo;
	}
	public String getReadAgentIdNo() {
		return readAgentIdNo;
	}
	public void setReadAgentIdNo(String readAgentIdNo) {
		this.readAgentIdNo = readAgentIdNo;
	}
	public String getReadAgentIdName() {
		return readAgentIdName;
	}
	public void setReadAgentIdName(String readAgentIdName) {
		this.readAgentIdName = readAgentIdName;
	}
	public String getReadIdNo() {
		return readIdNo;
	}
	public void setReadIdNo(String readIdNo) {
		this.readIdNo = readIdNo;
	}
	public String getReadIdName() {
		return readIdName;
	}
	public void setReadIdName(String readIdName) {
		this.readIdName = readIdName;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
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
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getBillPass() {
		return billPass;
	}
	public void setBillPass(String billPass) {
		this.billPass = billPass;
	}
	public String getProCode() {
		return proCode;
	}
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
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
	public String getAdvnInit() {
		return advnInit;
	}
	public void setAdvnInit(String advnInit) {
		this.advnInit = advnInit;
	}
	public String getRealPri() {
		return realPri;
	}
	public void setRealPri(String realPri) {
		this.realPri = realPri;
	}
	public String getXHSvrJrnlNo() {
		return XHSvrJrnlNo;
	}
	public void setXHSvrJrnlNo(String xHSvrJrnlNo) {
		XHSvrJrnlNo = xHSvrJrnlNo;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getShtds() {
		return shtds;
	}
	public void setShtds(String shtds) {
		this.shtds = shtds;
	}
	public String getShtdy() {
		return shtdy;
	}
	public void setShtdy(String shtdy) {
		this.shtdy = shtdy;
	}
	public String getSubalreAmt() {
		return subalreAmt;
	}
	public void setSubalreAmt(String subalreAmt) {
		this.subalreAmt = subalreAmt;
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
	public String getExchflag() {
		return exchflag;
	}
	public void setExchflag(String exchflag) {
		this.exchflag = exchflag;
	}
	public Map<String, List> getImportMap() {
		return importMap;
	}
	public void setImportMap(Map<String, List> importMap) {
		this.importMap = importMap;
	}
	public String getSupPass() {
		return supPass;
	}
	public void setSupPass(String supPass) {
		this.supPass = supPass;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getJudgeState() {
		return judgeState;
	}
	public void setJudgeState(String judgeState) {
		this.judgeState = judgeState;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getBindIdNo() {
		return bindIdNo;
	}
	public void setBindIdNo(String bindIdNo) {
		this.bindIdNo = bindIdNo;
	}
	public String getDrawCoun() {
		return drawCoun;
	}
	public void setDrawCoun(String drawCoun) {
		this.drawCoun = drawCoun;
	}
	public String getOpenInstNo() {
		return OpenInstNo;
	}
	public void setOpenInstNo(String openInstNo) {
		OpenInstNo = openInstNo;
	}
	public String getAccState() {
		return accState;
	}
	public void setAccState(String accState) {
		this.accState = accState;
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
	public String getAccCncelType() {
		return accCncelType;
	}
	public void setAccCncelType(String accCncelType) {
		this.accCncelType = accCncelType;
	}
	public String getHavAgentFlag() {
		return havAgentFlag;
	}
	public void setHavAgentFlag(String havAgentFlag) {
		this.havAgentFlag = havAgentFlag;
	}
	
	public String getAgentInspect() {
		return agentInspect;
	}
	public void setAgentInspect(String agentInspect) {
		this.agentInspect = agentInspect;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getDemTerm() {
		return demTerm;
	}
	public void setDemTerm(String demTerm) {
		this.demTerm = demTerm;
	}
	public String getTdPayAmt() {
		return tdPayAmt;
	}
	public void setTdPayAmt(String tdPayAmt) {
		this.tdPayAmt = tdPayAmt;
	}
	public String getXFD_COUNT() {
		return XFD_COUNT;
	}
	public void setXFD_COUNT(String xFD_COUNT) {
		XFD_COUNT = xFD_COUNT;
	}
	public String getJwResult() {
		return jwResult;
	}
	public void setJwResult(String jwResult) {
		this.jwResult = jwResult;
	}
	public String getSubAccNoCancel() {
		return subAccNoCancel;
	}
	public void setSubAccNoCancel(String subAccNoCancel) {
		this.subAccNoCancel = subAccNoCancel;
	}
	public String getCardIsPin() {
		return cardIsPin;
	}
	public void setCardIsPin(String cardIsPin) {
		this.cardIsPin = cardIsPin;
	}
	public String getXHBankMsgIsSign() {
		return XHBankMsgIsSign;
	}
	public void setXHBankMsgIsSign(String xHBankMsgIsSign) {
		XHBankMsgIsSign = xHBankMsgIsSign;
	}
	public String getBillIdNo() {
		return billIdNo;
	}
	public void setBillIdNo(String billIdNo) {
		this.billIdNo = billIdNo;
	}
	public String getBillIdName() {
		return billIdName;
	}
	public void setBillIdName(String billIdName) {
		this.billIdName = billIdName;
	}
	public String getTangCode() {
		return tangCode;
	}
	public void setTangCode(String tangCode) {
		this.tangCode = tangCode;
	}
	public String getTxbInputAmt() {
		return txbInputAmt;
	}
	public void setTxbInputAmt(String txbInputAmt) {
		this.txbInputAmt = txbInputAmt;
	}
	public String getTxbSubAccNo() {
		return txbSubAccNo;
	}
	public void setTxbSubAccNo(String txbSubAccNo) {
		this.txbSubAccNo = txbSubAccNo;
	}
	public String getTangChaErrMsg() {
		return tangChaErrMsg;
	}
	public void setTangChaErrMsg(String tangChaErrMsg) {
		this.tangChaErrMsg = tangChaErrMsg;
	}
	public String getXYDChaErrMsg() {
		return XYDChaErrMsg;
	}
	public void setXYDChaErrMsg(String xYDChaErrMsg) {
		XYDChaErrMsg = xYDChaErrMsg;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getEaccPass() {
		return EaccPass;
	}
	public void setEaccPass(String eaccPass) {
		EaccPass = eaccPass;
	}
	public String getTxbProCode() {
		return txbProCode;
	}
	public void setTxbProCode(String txbProCode) {
		this.txbProCode = txbProCode;
	}
	public String getTellerIsChecked() {
		return tellerIsChecked;
	}
	public void setTellerIsChecked(String tellerIsChecked) {
		this.tellerIsChecked = tellerIsChecked;
	}
	public String getAmtState() {
		return amtState;
	}
	public void setAmtState(String amtState) {
		this.amtState = amtState;
	}
	public String getNoPass() {
		return noPass;
	}
	public void setNoPass(String noPass) {
		this.noPass = noPass;
	}
	public String getAccFirstNo() {
		return accFirstNo;
	}
	public void setAccFirstNo(String accFirstNo) {
		this.accFirstNo = accFirstNo;
	}
	public String getBillFirstNo() {
		return billFirstNo;
	}
	public void setBillFirstNo(String billFirstNo) {
		this.billFirstNo = billFirstNo;
	}
	public String getJwState() {
		return jwState;
	}
	public void setJwState(String jwState) {
		this.jwState = jwState;
	}
	public String getTellNo1() {
		return tellNo1;
	}
	public void setTellNo1(String tellNo1) {
		this.tellNo1 = tellNo1;
	}
	public String getTellNo2() {
		return tellNo2;
	}
	public void setTellNo2(String tellNo2) {
		this.tellNo2 = tellNo2;
	}
	public String getTellNoState() {
		return tellNoState;
	}
	public void setTellNoState(String tellNoState) {
		this.tellNoState = tellNoState;
	}
	public String getIsCheckPass() {
		return isCheckPass;
	}
	public void setIsCheckPass(String isCheckPass) {
		this.isCheckPass = isCheckPass;
	}
	public String getFirstCardNo() {
		return firstCardNo;
	}
	public void setFirstCardNo(String firstCardNo) {
		this.firstCardNo = firstCardNo;
	}
	public String getSecondCardNocard() {
		return secondCardNocard;
	}
	public void setSecondCardNocard(String secondCardNocard) {
		this.secondCardNocard = secondCardNocard;
	}
	public String getInCardOrHandCard() {
		return inCardOrHandCard;
	}
	public void setInCardOrHandCard(String inCardOrHandCard) {
		this.inCardOrHandCard = inCardOrHandCard;
	}
	public String getBackAccNo() {
		return backAccNo;
	}
	public void setBackAccNo(String backAccNo) {
		this.backAccNo = backAccNo;
	}
	public String getXfdAmt() {
		return xfdAmt;
	}
	public void setXfdAmt(String xfdAmt) {
		this.xfdAmt = xfdAmt;
	}
	public String getTellNo3() {
		return tellNo3;
	}
	public void setTellNo3(String tellNo3) {
		this.tellNo3 = tellNo3;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public String getTdSvrJrnlNo() {
		return tdSvrJrnlNo;
	}
	public void setTdSvrJrnlNo(String tdSvrJrnlNo) {
		this.tdSvrJrnlNo = tdSvrJrnlNo;
	}
	public String getcNo() {
		return cNo;
	}
	public void setcNo(String cNo) {
		this.cNo = cNo;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getYuanAccNo() {
		return yuanAccNo;
	}
	public void setYuanAccNo(String yuanAccNo) {
		this.yuanAccNo = yuanAccNo;
	}
	public String getCancelDepTerm() {
		return cancelDepTerm;
	}
	public void setCancelDepTerm(String cancelDepTerm) {
		this.cancelDepTerm = cancelDepTerm;
	}
	public String getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}
	public String getAuthNoCode() {
		return authNoCode;
	}
	public void setAuthNoCode(String authNoCode) {
		this.authNoCode = authNoCode;
	}
	public String getReCamera() {
		return reCamera;
	}
	public void setReCamera(String reCamera) {
		this.reCamera = reCamera;
	}
	public String getTxbSvrJrnlNo() {
		return txbSvrJrnlNo;
	}
	public void setTxbSvrJrnlNo(String txbSvrJrnlNo) {
		this.txbSvrJrnlNo = txbSvrJrnlNo;
	}
	public String getPreDate() {
		return preDate;
	}
	public void setPreDate(String preDate) {
		this.preDate = preDate;
	}
	
}
