package com.boomhope.Bill.TransService.BillChOpen.Bean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.boomhope.Bill.Framework.BaseTransBean;

/**
 * 存单换开公共字段bean
 * @author 
 *
 */

public class PublicBillChangeOpenBean extends BaseTransBean{
	
	private String subAccNoCancel;//判断卡下子账户、普通账户(0:卡下子账号;1：普通账号;3:电子账户)
	private String setFlag = new String();//选中标识
	//账户信息
	private String accNo;	   // 账号
	private String subCardNo;	//子账号所属卡号
	private String subAccNo;	//子账号	
	private String custNo;	//客户号
	private String billType;//凭证种类
	private String billNo;	// 识别、输入的凭证号
	private String billPass;//存单密码
	private String noPass;//无密码
	private String accName;	// 户名
	private String amount;	// 本金
	private String MoneyUpper;//大写金额
	private String totalAmt;//存折余额
	private String endAmt;//结存额
	private String drawCoun;//是否有密码(0 无密码，1 有密码)
	private String OpenInstNo;//存单开户机构号
	private String openDate;// 开户日
	private String dueDate;	// 到期日
	private String proName; // 产品名称
	private String proCode; // 产品编号
	private String openchanl; //开户渠道
	private String svrDate;//核心日期
	private String startDate;// 起息日
	private String accState;//存单状态
	private String openQlt;//存单户性质
	private String certNo;//前置、返回的凭证号
	private String billIdNo;//返回的存单身份证号
	private String billIdName;//返回的存单户名
	private String backAccNo;//查询出返回的账号
	private String floatSum;//浮动利率汇总
	
	
	private String tellNo1;//授权柜员1
	private String tellNo2;//授权柜员2
	private String tellNo3;//授权柜员3
	private String tellNoState;//是否已对第一个柜员授权(1-鉴伪失败金额超限第一次授权标识，2-鉴伪失败金额超限第二次授权标识/第一次鉴伪通过但第二次鉴伪失败第二次授权标识)
	private String jwResult;//鉴伪结果
	
	private String havAgentFlag; //选择是否代理人办理(0：无代理人  1：有代理人)
	private String checkMod;//CHECK_MOD	认证方式	1	1 口令2 指纹 3 口令加指纹
	private String finPriLen;//FIN_PRI_LEN	指纹长度
	private String finPriVal;//FIN_PRI_VAL	指纹值	512
	private String supTellerNo;//授权柜员号
	private String supPass;//授权密码
	
	private String billIdType;// 存单对应的证件类型
	private String billIdCard;// 存单对应的身份证号
	private String billPath_fc;//存单正面
	private String billPath_rc;//存单背面
	
	private String checkStatus;//标记存单是否自动识别，1自动识别，2手动输入
	private String fid;//插入存单生产流程序号
	private String isCheckPass;//是否验密(0-否，1-是)
	private String HKSvrJrnlNo;//换开流水号
	
	private Map<String,List> CardMap = new HashMap<String,List>();// 卡号
	private Map<String,List> AccMap = new HashMap<String,List>();// 普通账号
	private Map<String,List> DZAccMap = new HashMap<String,List>();//电子账号
	private Map<String,List> subMap = new HashMap<String,List>();// 获取子账号
	private Map<String, Object> map = new HashMap<String, Object>();//读卡器读取的信息
	private String jwState;//鉴伪次数标识(1-鉴伪通过金额超限后授权标识)
	private String accFirstNo;//第一次识别的账号
	private String billFirstNo;//第一次识别的凭证号
	private String secondCardNocard;//两次卡号不一致标识(1-不一致)
	private String firstCardNo;//第一次录入银行卡号
	
	private String Yz;
	//卡信息
	private String cardName;// 卡名
	private String cardNames;// 带*号的卡名
	private String cardNo;//卡号
	private String bindIdNo;//绑定的电子账户
	private String eleCardPwd;//电子账户密码
	private String inCardOrHandCard;//选择插入或手输卡号标识(1-插卡 2手输)
	
	private String  open_card_num;//开卡张数
	private String  open_card;//开卡卡号
	
	//代理核对
	private String checkResult;//代理人核对结果（0：我行 。1：非我行）
	//联网核查
	private String inspect;//本人联网核查结果信息
	private String agentInspect;//代理人联网核查结果信息
	
	//人脸识别
	private String checkFaceMsg;//照片较验结果
	private String faceCompareVal;//比较值
	private String cameraCount;//拍照次数
	
	private String judgeState; //判断提前支取 1.提前支取 0 到期支取
	private String amtState;//判断是否大于等于5万(1大于等于0小于)
	
	private String XHBankMsgIsSign;//是否有电子签名(Y-已签名)
	private String authNoCode;//授权主交易码
	private String tellerIsChecked;//授权之前是否确认信息(1-已确认信息 2-允许授权)
	
	private String reCamera;//重新拍照标识（0-重新拍照）
	
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


	//55域卡信息
	private String arqc;//ARQC
	private String arqcSrcData;//ARQC生成数据
	private String termChkValue;//终端验证结果
	private String appAcctSeq;//应用主账户序列号
	private String issAppData;//发卡行应用数据
	private String cardPov;//卡片有效期
	private String erCiInfo;//二磁信息
	
	private String printerL51Str;//约享窗口利率期
	private String billPrinterL51Str;//约享窗口期
	private String printerL52Str;//安享存窗口利率期
	
	private String XYpass;//校验密码标识
	
	
	
	public String getYz() {
		return Yz;
	}
	public void setYz(String yz) {
		Yz = yz;
	}
	public String getXYpass() {
		return XYpass;
	}
	public void setXYpass(String xYpass) {
		XYpass = xYpass;
	}
	public String getPrinterL51Str() {
		return printerL51Str;
	}
	public void setPrinterL51Str(String printerL51Str) {
		this.printerL51Str = printerL51Str;
	}
	public String getBillPrinterL51Str() {
		return billPrinterL51Str;
	}
	public void setBillPrinterL51Str(String billPrinterL51Str) {
		this.billPrinterL51Str = billPrinterL51Str;
	}
	public String getPrinterL52Str() {
		return printerL52Str;
	}
	public void setPrinterL52Str(String printerL52Str) {
		this.printerL52Str = printerL52Str;
	}
	public String getFloatSum() {
		return floatSum;
	}
	public void setFloatSum(String floatSum) {
		this.floatSum = floatSum;
	}
	public String getOpenchanl() {
		return openchanl;
	}
	public void setOpenchanl(String openchanl) {
		this.openchanl = openchanl;
	}
	public String getEndAmt() {
		return endAmt;
	}
	public void setEndAmt(String endAmt) {
		this.endAmt = endAmt;
	}
	public String getSetFlag() {
		return setFlag;
	}
	public void setSetFlag(String setFlag) {
		this.setFlag = setFlag;
	}
	public Map<String, List> getCardMap() {
		return CardMap;
	}
	public void setCardMap(Map<String, List> cardMap) {
		CardMap = cardMap;
	}
	public Map<String, List> getAccMap() {
		return AccMap;
	}
	public void setAccMap(Map<String, List> accMap) {
		AccMap = accMap;
	}
	public Map<String, List> getDZAccMap() {
		return DZAccMap;
	}
	public void setDZAccMap(Map<String, List> dZAccMap) {
		DZAccMap = dZAccMap;
	}
	public Map<String, List> getSubMap() {
		return subMap;
	}
	public void setSubMap(Map<String, List> subMap) {
		this.subMap = subMap;
	}
	public String getOpen_card() {
		return open_card;
	}
	public void setOpen_card(String open_card) {
		this.open_card = open_card;
	}
	public String getOpen_card_num() {
		return open_card_num;
	}
	public void setOpen_card_num(String open_card_num) {
		this.open_card_num = open_card_num;
	}
	

	//凭条机错误信息
	private String billMsg ;//凭条打印错误信息
	private String nowNo;//当前回收机的凭证号
	private String createTime; //开始时间
	private String endTime;//结束日期
	private int ePages;//存单文件页数
	//private Map<String,List> importMap = new HashMap<String,List>();// 存单返回重要字段
	private int nowPage=1;//当前页数
	//积享存、如意存+存期
	private String jxRyjDepTem;//积享存、如意存存期(天数)
	private String monthsUpper; //存期   (Y,M,D)
	private String monthsUpperStr; //存期(年，月，日)
	private String productCode;// 产品代码
	private String Rate; //定期利率
	private String Routing;// 到期利息
	private String ExchFlag;//自动转存标志
	private String HKMethod;//判断换开方式(1:银行卡换开;2:输入存单信息)
	
	

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
	public String getCheckMod() {
		return checkMod;
	}
	public void setCheckMod(String checkMod) {
		this.checkMod = checkMod;
	}
	public String getSupTellerNo() {
		return supTellerNo;
	}
	public void setSupTellerNo(String supTellerNo) {
		this.supTellerNo = supTellerNo;
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
	public String getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}
	public String getBillPass() {
		return billPass;
	}
	public void setBillPass(String billPass) {
		this.billPass = billPass;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	public String getIdName() {
		return idName;
	}
	public void setIdName(String idName) {
		this.idName = idName;
	}
	public String getProCode() {
		return proCode;
	}
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
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
	public String getBackAccNo() {
		return backAccNo;
	}
	public void setBackAccNo(String backAccNo) {
		this.backAccNo = backAccNo;
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
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getOpenQlt() {
		return openQlt;
	}
	public void setOpenQlt(String openQlt) {
		this.openQlt = openQlt;
	}
	public String getAccState() {
		return accState;
	}
	public void setAccState(String accState) {
		this.accState = accState;
	}
	public String getOpenInstNo() {
		return OpenInstNo;
	}
	public void setOpenInstNo(String openInstNo) {
		OpenInstNo = openInstNo;
	}
	public String getDrawCoun() {
		return drawCoun;
	}
	public void setDrawCoun(String drawCoun) {
		this.drawCoun = drawCoun;
	}
	public String getSubAccNoCancel() {
		return subAccNoCancel;
	}
	public void setSubAccNoCancel(String subAccNoCancel) {
		this.subAccNoCancel = subAccNoCancel;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
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
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getIsCheckPass() {
		return isCheckPass;
	}
	public void setIsCheckPass(String isCheckPass) {
		this.isCheckPass = isCheckPass;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
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
	public String getJwResult() {
		return jwResult;
	}
	public void setJwResult(String jwResult) {
		this.jwResult = jwResult;
	}
	public String getNoPass() {
		return noPass;
	}
	public void setNoPass(String noPass) {
		this.noPass = noPass;
	}
	public String getJwState() {
		return jwState;
	}
	public void setJwState(String jwState) {
		this.jwState = jwState;
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
	public String getSecondCardNocard() {
		return secondCardNocard;
	}
	public void setSecondCardNocard(String secondCardNocard) {
		this.secondCardNocard = secondCardNocard;
	}
	public String getFirstCardNo() {
		return firstCardNo;
	}
	public void setFirstCardNo(String firstCardNo) {
		this.firstCardNo = firstCardNo;
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
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getBindIdNo() {
		return bindIdNo;
	}
	public void setBindIdNo(String bindIdNo) {
		this.bindIdNo = bindIdNo;
	}
	public String getEleCardPwd() {
		return eleCardPwd;
	}
	public void setEleCardPwd(String eleCardPwd) {
		this.eleCardPwd = eleCardPwd;
	}
	public String getInCardOrHandCard() {
		return inCardOrHandCard;
	}
	public void setInCardOrHandCard(String inCardOrHandCard) {
		this.inCardOrHandCard = inCardOrHandCard;
	}
	public String getJudgeState() {
		return judgeState;
	}
	public void setJudgeState(String judgeState) {
		this.judgeState = judgeState;
	}
	public String getAmtState() {
		return amtState;
	}
	public void setAmtState(String amtState) {
		this.amtState = amtState;
	}
	public String getXHBankMsgIsSign() {
		return XHBankMsgIsSign;
	}
	public void setXHBankMsgIsSign(String xHBankMsgIsSign) {
		XHBankMsgIsSign = xHBankMsgIsSign;
	}
	public String getSupPass() {
		return supPass;
	}
	public void setSupPass(String supPass) {
		this.supPass = supPass;
	}
	public String getAuthNoCode() {
		return authNoCode;
	}
	public void setAuthNoCode(String authNoCode) {
		this.authNoCode = authNoCode;
	}
	public String getHavAgentFlag() {
		return havAgentFlag;
	}
	public void setHavAgentFlag(String havAgentFlag) {
		this.havAgentFlag = havAgentFlag;
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
	public String getTellerIsChecked() {
		return tellerIsChecked;
	}
	public void setTellerIsChecked(String tellerIsChecked) {
		this.tellerIsChecked = tellerIsChecked;
	}
	public String getTellNo3() {
		return tellNo3;
	}
	public void setTellNo3(String tellNo3) {
		this.tellNo3 = tellNo3;
	}
	public String getInspect() {
		return inspect;
	}
	public void setInspect(String inspect) {
		this.inspect = inspect;
	}
	public String getAgentInspect() {
		return agentInspect;
	}
	public void setAgentInspect(String agentInspect) {
		this.agentInspect = agentInspect;
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
	public String getCameraCount() {
		return cameraCount;
	}
	public void setCameraCount(String cameraCount) {
		this.cameraCount = cameraCount;
	}
	public String getReCamera() {
		return reCamera;
	}
	public void setReCamera(String reCamera) {
		this.reCamera = reCamera;
	}
	public String getCardPwd() {
		return cardPwd;
	}
	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
	}
	public String getCardIsPin() {
		return cardIsPin;
	}
	public void setCardIsPin(String cardIsPin) {
		this.cardIsPin = cardIsPin;
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
	public String getAgentIdType() {
		return agentIdType;
	}
	public void setAgentIdType(String agentIdType) {
		this.agentIdType = agentIdType;
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
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public String getHKSvrJrnlNo() {
		return HKSvrJrnlNo;
	}
	public void setHKSvrJrnlNo(String hKSvrJrnlNo) {
		HKSvrJrnlNo = hKSvrJrnlNo;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getNowNo() {
		return nowNo;
	}
	public void setNowNo(String nowNo) {
		this.nowNo = nowNo;
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
	public String getBillMsg() {
		return billMsg;
	}
	public void setBillMsg(String billMsg) {
		this.billMsg = billMsg;
	}
	public String getMoneyUpper() {
		return MoneyUpper;
	}
	public void setMoneyUpper(String moneyUpper) {
		MoneyUpper = moneyUpper;
	}
	public int getePages() {
		return ePages;
	}
	public void setePages(int ePages) {
		this.ePages = ePages;
	}
//	public Map<String, List> getImportMap() {
//		return importMap;
//	}
//	public void setImportMap(Map<String, List> importMap) {
//		this.importMap = importMap;
//	}
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	public String getJxRyjDepTem() {
		return jxRyjDepTem;
	}
	public void setJxRyjDepTem(String jxRyjDepTem) {
		this.jxRyjDepTem = jxRyjDepTem;
	}
	public String getMonthsUpper() {
		return monthsUpper;
	}
	public void setMonthsUpper(String monthsUpper) {
		this.monthsUpper = monthsUpper;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getRate() {
		return Rate;
	}
	public void setRate(String rate) {
		Rate = rate;
	}
	public String getRouting() {
		return Routing;
	}
	public void setRouting(String routing) {
		Routing = routing;
	}
	public String getExchFlag() {
		return ExchFlag;
	}
	public void setExchFlag(String exchFlag) {
		ExchFlag = exchFlag;
	}
	public String getMonthsUpperStr() {
		return monthsUpperStr;
	}
	public void setMonthsUpperStr(String monthsUpperStr) {
		this.monthsUpperStr = monthsUpperStr;
	}
	public String getHKMethod() {
		return HKMethod;
	}
	public void setHKMethod(String hKMethod) {
		HKMethod = hKMethod;
	}
	
	
	
}
