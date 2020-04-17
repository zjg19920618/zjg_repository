package com.boomhope.Bill.TransService.AccOpen.Bean;

import java.util.HashMap;
import java.util.Map;

import com.boomhope.Bill.Framework.BaseTransBean;


public class AccPublicBean extends BaseTransBean{
	//交易授权变化  0-不需要授权 1-第一次需要授权 2-已经有过授权交易
	private String transChangeNo;
	
	//本人身份证信息
	private String idCardNo;//身份证号
	private String idCardName;//身份证名称
	private String address;// 户口地址
	private String sex;// 性别
	private String qfjg;// 证件签发机关
	private String idCardface;//身份证正面照路径
	private String idCardtitle;//身份证上头像照片路径
	private String idCardback;//身份证背面照路径
	private String inspect;//核查结果
	private String birthday;//生日
	private String endDate;//有效期结束		
	private String custNo;// 客户号
	private String photoPath;//联网核查头像路径
	private String svrRetCode;//客户信息查询返回结果代码
	private String nineMsg;//客户基本信息九项信息
	private String infoQualityFlag;//客户信息质量标志
	private String custLevel;//客户级别
	
	//代理人信息
	private String haveAgentFlag;//HAV_AGENT_FLAG是否有代理人标志0-是 1-否
	private String agentIdCardNo;//代理人身份证号
	private String agentIdCardName;//代理人身份证名称
	private String agentaddress;// 代理人户口地址
	private String agentsex;// 代理人性别
	private String agentqfjg;// 证件签发机关
	private String agentIdCardface;//身份证正面照路径
	private String agentIdCardtitle;//代理人身份证上头像照片
	private String agentIdCardback;//身份证背面照路径
	private String agentinspect;//核查结果
	private String agentBirthday;//生日
	private String agentEndDate;//有效期结束
	private String agentPhotoPath;//联网核查头像路径
	private String nation;//国籍
	private String agentCheckResult;//代理人信息核查结果 
	
	
	//卡信息
	private String cardName;// 卡名
	private String cardNames;// 带*号的卡名
	private String cardNo;//卡号
	private String cardPwd;// 卡密码
	private String cardAmt;// 卡可用余额
	private String cardStatus;//卡状态
	private String cardIsPin;//是否验证密码 0-否 1-是
	//55域卡信息
	private String arqc;//ARQC
	private String arqcSrcData;//ARQC生成数据
	private String termChkValue;//终端验证结果
	private String appAcctSeq;//应用主账户序列号
	private String issAppData;//发卡行应用数据
	private String cardPov;//卡片有效期
	private String erCiInfo;//二磁信息
	
	//授权柜员信息
	private String supTellerNo;//授权柜员号
	private String checkMod;//CHECK_MOD	认证方式	1	1 口令2 指纹 3 口令加指纹
	private String supTellerPass;//授权密码
	private String finPriLen;//FIN_PRI_LEN	指纹长度
	private String finPriVal;//FIN_PRI_VAL	指纹值	512
	private String faceCompareVal;//人脸比对结果
	private String isCheckedPic;//是否已经核查照片  0-未核查 1-已经核查 2-核查未通过重新拍照
	private String isCheckInfos;//是否已经进行了联网核查和人脸识别 0-未核查 1-已经核查
	
	//存单录入信息
	private String productTotalName;//产品系列名称
	private String productName;//产品名称
	private String subProductName;//关联产品名称
	private String monthsUpper; //存期   (Y,M,D)
	private String monthsUpperXySh; //存期   (Y,M,D)
	private String monthsUpperStr; //存期(年，月，日) 
	private String depUnit;//DEP_UNIT存期单位Y-年，	M-月，D-日
	private String money;//存入小写金额
	private String moneyUpper;//大写金额
	private String IntMoney;//加息金额
	private String IntSvrNo;//加息券流水号
	private String calMode;//CAL_MODE结算方式0-现金，1-转账，默认转账
	private String autoRedpFlag;//AUTO_REDP_FLAG自动转存标志
	private String inte;//定期利息
	private String rate;//定期利率
	private String zzAmt;//卡当日存款金额
	private String beiZzAmt;//交易完成后备份当日存款金额
	private String moreThanAmt;//当日累计金额是否大于5万  0-大于等于5万; 1-小于5万
	private String printerL51Str;//约享窗口利率期
	private String billPrinterL51Str;//约享窗口期
	private String printerL52Str;//安享存窗口利率期
	private String billPrinterL52Str;//安享存打印存单窗口期
	private String qxGetHaveType;//千禧收益方式
	private String productNameNew;//用于千喜和幸福1+1
	private String ruleName;//规则名称
	//存单信息
	private String isAuthorize;//是否授权
	private String accountCardNo;//开户银行卡号
	private String AccinputNo;//账号
	private String subAcctNo;//子账号
	private String inputNo;//收益卡号
	private String inputName;//受益人姓名
	private String certPrint;//是否打印存单0不需要1需要

	//产品信息
	private String accChose;//业务选择
	private String qryType;//QRY_TYPE查询类型
	private String productCode;//产品代码   02867
	private String productType;//产品类型   02867
	private String minAmt;//最小起存金额   02867
	private String maxAmt;//最大起存金额   02867
	private String chlId;//渠道模块标识
	private String agreementPrint;//是否打印协议书 0-打印协议书 1-不打印协议书
	
	//核心信息
	private String createTime; //开始时间
	private String svrDate;//核心日期
	private String svrJrnlNo;//SVR_JRNL_NO核心流水号
	private String endTime;//结束日期
	private String autoSaving;//自动转存标识
	
	//唐豆信息
	private boolean hasTangDou;//是否有唐豆
	private String tangDouAmt;//唐豆金额
	private String termCode;//TERM_CODE	唐豆期次代码
	private String count;//COUNT	唐豆数量
	private String exchangeMode;//EXCHANGE_MODE	唐豆兑换方式	Char(1)	0-现金 1-转账 2-兑物
	private String exchangeProp;//EXCHANGE_PROP	唐豆兑现比例
	private String exchangeAmt;//EXCHANGE_AMT	兑现金额
	private String dealType;//DEAL_TYPE	历史唐豆处理标志	1	0-历史唐豆 1-当期唐豆 ，必输
	private String isHonour;//唐豆是否兑付成功	0-成功 1-失败
	private String tangDDFSvrNo;//唐豆兑付流水号
	private String zydCount;//增益豆数量
	private String xfdCount;//消费豆数量
	private String tdTotalCount;//唐豆总数（幸运豆+消费豆+增益豆）
	
	private String haveRelaAcc;//是否有关联如意存账号 0-有 1-无
	private String relaAcctNo;//积享存关联的如意存账号
	private String subRelaAcctNo;//积享存关联子账号
	private String IsprivateLine;//私行快线标识符
	private String proCodeBiaoShi;//产品代码标识
	
	//开户返回信息
	private String areaFloatRet;//区域浮动利率
	private String chlFloatRet;//渠道浮动利率
	private String birthFloatRet;//生日浮动利率
	private String timeFloatRet;//时间段浮动利率
	private String combFloatRet;//组合浮动利率
	private String floatSum;//浮动利率汇总
	private String startDate;//起息日期
	
	//凭条机错误信息
	private String billMsg ;//凭条打印错误信息
	//人脸识别错误信息
	private String checkFaceMsg;//错误原因
	private String cameraCount;//拍照次数
	private String isReCamera;//是否重新拍照 0-否 1-重新拍照
	//签字
	private String isSign;//是否已经签字  0-未签字 ，1-已经签字 ,2-已经签字并通过审核
	//继续交易标志
	private String goOnTrans;//第二笔交易以上 0-第一笔交易  1-第二笔交易
	//积享存、如意存+存期
	private String jxRyjDepTem;//积享存、如意存存期(天数)
	
	//重要信息字段存放
	private Map mapInfos=new HashMap();
	
	//分页数据
	private int n;//当前页
	private int x;//上一页
	
	private int qxn=1;//千禧当前页
	private int qxpage; //千禧一共页数
	
	private int ryn=1;//如意当前页
	private int rypage; //如意一共页数

	private String subAccNoCancel;
	private String billCancel;

	private int yxn=1;//约享当前页
	private int yxpage; //约享一共页数
	
	private int ldn=1;//立得存当前页
	private int ldpage; //立得存一共页数
	
	private int jxn=1;//积享存当前页
	private int jxpage; //积享存一共页数
	
	private int ryjn=1;//如意存+存当前页
	private int ryjpage; //如意存+存一共页数
	
	private int xfn=1;//幸福1+1当前页
	private int xfpage; //幸福1+1一共页数
	
	private String res03870;//开户接口返回报文
	private String res07506;//唐豆派发查询
	private String res07505;//唐豆兑付
	private String uploadPath;//上传路径
	
	
	
	public String getProductNameNew() {
		return productNameNew;
	}

	public void setProductNameNew(String productNameNew) {
		this.productNameNew = productNameNew;
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

	public Map getMapInfos() {
		return mapInfos;
	}

	public void setMapInfos(Map mapInfos) {
		this.mapInfos = mapInfos;
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

	public String getIsReCamera() {
		return isReCamera;
	}

	public void setIsReCamera(String isReCamera) {
		this.isReCamera = isReCamera;
	}

	public String getProductTotalName() {
		return productTotalName;
	}

	public void setProductTotalName(String productTotalName) {
		this.productTotalName = productTotalName;
	}

	public String getBillPrinterL51Str() {
		return billPrinterL51Str;
	}

	public void setBillPrinterL51Str(String billPrinterL51Str) {
		this.billPrinterL51Str = billPrinterL51Str;
	}

	public String getHaveRelaAcc() {
		return haveRelaAcc;
	}

	public void setHaveRelaAcc(String haveRelaAcc) {
		this.haveRelaAcc = haveRelaAcc;
	}

	public String getIntSvrNo() {
		return IntSvrNo;
	}

	public void setIntSvrNo(String intSvrNo) {
		IntSvrNo = intSvrNo;
	}

	public String getTransChangeNo() {
		return transChangeNo;
	}

	public void setTransChangeNo(String transChangeNo) {
		this.transChangeNo = transChangeNo;
	}

	public String getBeiZzAmt() {
		return beiZzAmt;
	}

	public void setBeiZzAmt(String beiZzAmt) {
		this.beiZzAmt = beiZzAmt;
	}

	public String getBillPrinterL52Str() {
		return billPrinterL52Str;
	}

	public void setBillPrinterL52Str(String billPrinterL52Str) {
		this.billPrinterL52Str = billPrinterL52Str;
	}

	public String getFloatSum() {
		return floatSum;
	}

	public void setFloatSum(String floatSum) {
		this.floatSum = floatSum;
	}

	public String getGoOnTrans() {
		return goOnTrans;
	}

	public void setGoOnTrans(String goOnTrans) {
		this.goOnTrans = goOnTrans;
	}

	public String getIsCheckInfos() {
		return isCheckInfos;
	}

	public void setIsCheckInfos(String isCheckInfos) {
		this.isCheckInfos = isCheckInfos;
	}

	public String getMoreThanAmt() {
		return moreThanAmt;
	}

	public void setMoreThanAmt(String moreThanAmt) {
		this.moreThanAmt = moreThanAmt;
	}

	public String getZzAmt() {
		return zzAmt;
	}

	public void setZzAmt(String zzAmt) {
		this.zzAmt = zzAmt;
	}

	public String getTangDDFSvrNo() {
		return tangDDFSvrNo;
	}

	public void setTangDDFSvrNo(String tangDDFSvrNo) {
		this.tangDDFSvrNo = tangDDFSvrNo;
	}

	public String getCustLevel() {
		return custLevel;
	}

	public void setCustLevel(String custLevel) {
		this.custLevel = custLevel;
	}

	public String getNineMsg() {
		return nineMsg;
	}

	public void setNineMsg(String nineMsg) {
		this.nineMsg = nineMsg;
	}

	public String getInfoQualityFlag() {
		return infoQualityFlag;
	}

	public void setInfoQualityFlag(String infoQualityFlag) {
		this.infoQualityFlag = infoQualityFlag;
	}

	public String getSvrRetCode() {
		return svrRetCode;
	}

	public void setSvrRetCode(String svrRetCode) {
		this.svrRetCode = svrRetCode;
	}

	public String getAgentCheckResult() {
		return agentCheckResult;
	}

	public void setAgentCheckResult(String agentCheckResult) {
		this.agentCheckResult = agentCheckResult;
	}

	public String getIsCheckedPic() {
		return isCheckedPic;
	}

	public void setIsCheckedPic(String isCheckedPic) {
		this.isCheckedPic = isCheckedPic;
	}

	public String getIsSign() {
		return isSign;
	}

	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}

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

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getAgreementPrint() {
		return agreementPrint;
	}

	public void setAgreementPrint(String agreementPrint) {
		this.agreementPrint = agreementPrint;
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

	public String getDepUnit() {
		return depUnit;
	}

	public void setDepUnit(String depUnit) {
		this.depUnit = depUnit;
	}

	public String getCardAmt() {
		return cardAmt;
	}

	public void setCardAmt(String cardAmt) {
		this.cardAmt = cardAmt;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

//	public String getCertNoAdd() {
//		return certNoAdd;
//	}
//
//	public void setCertNoAdd(String certNoAdd) {
//		this.certNoAdd = certNoAdd;
//	}

	public String getSubAcctNo() {
		return subAcctNo;
	}

	public void setSubAcctNo(String subAcctNo) {
		this.subAcctNo = subAcctNo;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getSvrDate() {
		return svrDate;
	}

	public void setSvrDate(String svrDate) {
		this.svrDate = svrDate;
	}

	public String getMinAmt() {
		return minAmt;
	}

	public void setMinAmt(String minAmt) {
		this.minAmt = minAmt;
	}

	public String getMaxAmt() {
		return maxAmt;
	}

	public void setMaxAmt(String maxAmt) {
		this.maxAmt = maxAmt;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getAccinputNo() {
		return AccinputNo;
	}

	public void setAccinputNo(String accinputNo) {
		AccinputNo = accinputNo;
	}


	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
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
	public String getCertPrint() {
		return certPrint;
	}
	
	public void setCertPrint(String certPrint) {
		this.certPrint = certPrint;
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

	public String getInspect() {
		return inspect;
	}

	public void setInspect(String inspect) {
		this.inspect = inspect;
	}

	public String getBirthday() {
		return birthday;
	}

	public String getCardIsPin() {
		return cardIsPin;
	}
	
	public void setCardIsPin(String cardIsPin) {
		this.cardIsPin = cardIsPin;
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

	public String getCardPwd() {
		return cardPwd;
	}

	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getSupTellerNo() {
		return supTellerNo;
	}

	public void setSupTellerNo(String supTellerNo) {
		this.supTellerNo = supTellerNo;
	}

	public String getSupTellerPass() {
		return supTellerPass;
	}

	public void setSupTellerPass(String supTellerPass) {
		this.supTellerPass = supTellerPass;
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

	public String getAccountCardNo() {
		return accountCardNo;
	}

	public void setAccountCardNo(String accountCardNo) {
		this.accountCardNo = accountCardNo;
	}

	public String getCalMode() {
		return calMode;
	}

	public void setCalMode(String calMode) {
		this.calMode = calMode;
	}

	public String getAutoRedpFlag() {
		return autoRedpFlag;
	}

	public void setAutoRedpFlag(String autoRedpFlag) {
		this.autoRedpFlag = autoRedpFlag;
	}

	public String getHaveAgentFlag() {
		return haveAgentFlag;
	}

	public void setHaveAgentFlag(String haveAgentFlag) {
		this.haveAgentFlag = haveAgentFlag;
	}

	public String getSvrJrnlNo() {
		return svrJrnlNo;
	}

	public void setSvrJrnlNo(String svrJrnlNo) {
		this.svrJrnlNo = svrJrnlNo;
	}

	public boolean isHasTangDou() {
		return hasTangDou;
	}

	public void setHasTangDou(boolean hasTangDou) {
		this.hasTangDou = hasTangDou;
	}

	public String getTangDouAmt() {
		return tangDouAmt;
	}

	public void setTangDouAmt(String tangDouAmt) {
		this.tangDouAmt = tangDouAmt;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getExchangeProp() {
		return exchangeProp;
	}

	public void setExchangeProp(String exchangeProp) {
		this.exchangeProp = exchangeProp;
	}

	public String getExchangeMode() {
		return exchangeMode;
	}

	public void setExchangeMode(String exchangeMode) {
		this.exchangeMode = exchangeMode;
	}

	public String getExchangeAmt() {
		return exchangeAmt;
	}

	public void setExchangeAmt(String exchangeAmt) {
		this.exchangeAmt = exchangeAmt;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
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

	public String getInte() {
		return inte;
	}

	public void setInte(String inte) {
		this.inte = inte;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getQryType() {
		return qryType;
	}

	public void setQryType(String qryType) {
		this.qryType = qryType;
	}

	public String getChlId() {
		return chlId;
	}

	public void setChlId(String chlId) {
		this.chlId = chlId;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getAutoSaving() {
		return autoSaving;
	}

	public void setAutoSaving(String autoSaving) {
		this.autoSaving = autoSaving;
	}

	public String getMoneyUpper() {
		return moneyUpper;
	}

	public void setMoneyUpper(String moneyUpper) {
		this.moneyUpper = moneyUpper;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getAgentPhotoPath() {
		return agentPhotoPath;
	}

	public void setAgentPhotoPath(String agentPhotoPath) {
		this.agentPhotoPath = agentPhotoPath;
	}
	 
	public String getIsAuthorize() {
		return isAuthorize;
	}

	public void setIsAuthorize(String isAuthorize) {
		this.isAuthorize = isAuthorize;
	}

	public String getInputNo() {
		return inputNo;
	}

	public void setInputNo(String inputNo) {
		this.inputNo = inputNo;
	}

	public String getRelaAcctNo() {
		return relaAcctNo;
	}

	public void setRelaAcctNo(String relaAcctNo) {
		this.relaAcctNo = relaAcctNo;
	}

	public String getSubRelaAcctNo() {
		return subRelaAcctNo;
	}

	public void setSubRelaAcctNo(String subRelaAcctNo) {
		this.subRelaAcctNo = subRelaAcctNo;
	}

	public String getIntMoney() {
		return IntMoney;
	}

	public void setIntMoney(String intMoney) {
		IntMoney = intMoney;
	}
	
	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public String getPrinterL51Str() {
		return printerL51Str;
	}

	public void setPrinterL51Str(String printerL51Str) {
		this.printerL51Str = printerL51Str;
	}

	public String getAreaFloatRet() {
		return areaFloatRet;
	}

	public void setAreaFloatRet(String areaFloatRet) {
		this.areaFloatRet = areaFloatRet;
	}

	public String getChlFloatRet() {
		return chlFloatRet;
	}

	public void setChlFloatRet(String chlFloatRet) {
		this.chlFloatRet = chlFloatRet;
	}

	public String getBirthFloatRet() {
		return birthFloatRet;
	}

	public void setBirthFloatRet(String birthFloatRet) {
		this.birthFloatRet = birthFloatRet;
	}

	public String getTimeFloatRet() {
		return timeFloatRet;
	}

	public void setTimeFloatRet(String timeFloatRet) {
		this.timeFloatRet = timeFloatRet;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getCombFloatRet() {
		return combFloatRet;
	}

	public void setCombFloatRet(String combFloatRet) {
		this.combFloatRet = combFloatRet;
	}

//	public String getCertNoId() {
//		return certNoId;
//	}
//
//	public void setCertNoId(String certNoId) {
//		this.certNoId = certNoId;
//	}

	public String getIsprivateLine() {
		return IsprivateLine;
	}

	public void setIsprivateLine(String isprivateLine) {
		IsprivateLine = isprivateLine;
	}

	public String getProCodeBiaoShi() {
		return proCodeBiaoShi;
	}

	public void setProCodeBiaoShi(String proCodeBiaoShi) {
		this.proCodeBiaoShi = proCodeBiaoShi;
	}

//	public String getCertStartNo() {
//		return certStartNo;
//	}
//
//	public void setCertStartNo(String certStartNo) {
//		this.certStartNo = certStartNo;
//	}
//
//	public String getCertEndNo() {
//		return certEndNo;
//	}
//
//	public void setCertEndNo(String certEndNo) {
//		this.certEndNo = certEndNo;
//	}
//
//	public String getCreatTime() {
//		return creatTime;
//	}
//
//	public void setCreatTime(String creatTime) {
//		this.creatTime = creatTime;
//	}

	public String getFaceCompareVal() {
		return faceCompareVal;
	}

	public void setFaceCompareVal(String faceCompareVal) {
		this.faceCompareVal = faceCompareVal;
	}

	public String getPrinterL52Str() {
		return printerL52Str;
	}

	public void setPrinterL52Str(String printerL52Str) {
		this.printerL52Str = printerL52Str;
	}

	public String getIsHonour() {
		return isHonour;
	}

	public void setIsHonour(String isHonour) {
		this.isHonour = isHonour;
	}

	public String getSubProductName() {
		return subProductName;
	}

	public void setSubProductName(String subProductName) {
		this.subProductName = subProductName;
	}

	public String getMonthsUpperXySh() {
		return monthsUpperXySh;
	}

	public void setMonthsUpperXySh(String monthsUpperXySh) {
		this.monthsUpperXySh = monthsUpperXySh;
	}

	public String getAccChose() {
		return accChose;
	}

	public void setAccChose(String accChose) {
		this.accChose = accChose;
	}

	public String getBillMsg() {
		return billMsg;
	}

	public void setBillMsg(String billMsg) {
		this.billMsg = billMsg;
	}

	public String getJxRyjDepTem() {
		return jxRyjDepTem;
	}

	public void setJxRyjDepTem(String jxRyjDepTem) {
		this.jxRyjDepTem = jxRyjDepTem;
	}

	public String getRes03870() {
		return res03870;
	}

	public void setRes03870(String res03870) {
		this.res03870 = res03870;
	}

	public String getRes07506() {
		return res07506;
	}

	public void setRes07506(String res07506) {
		this.res07506 = res07506;
	}

	public String getRes07505() {
		return res07505;
	}

	public void setRes07505(String res07505) {
		this.res07505 = res07505;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

}
