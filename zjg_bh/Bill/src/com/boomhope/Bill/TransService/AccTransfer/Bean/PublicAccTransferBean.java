package com.boomhope.Bill.TransService.AccTransfer.Bean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.boomhope.Bill.Framework.BaseTransBean;

/**
 * 汇划转账公共字段bean
 * @author 
 *
 */
public class PublicAccTransferBean extends BaseTransBean{
	
	//逻辑判断
		private Map<String,Object> params = new HashMap<String,Object>();//存放判断的map
		private Set<String> setFlag = new HashSet<String>();
		private String isCardBin;//出账卡 判断单位卡或个人卡  0选择个人，1选择单位
		private String cardBin;//出账卡卡Bin
		private String isRuZhangCardBin;//入账为本行，判断单位卡或个人卡 0选择个人，1选择单位
		private String ruZhangCardBin;//本行入账卡卡Bin
		private String isCardBank;//判断入账为本行还是跨行 0本行，1他行
	    
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
		private String photoPath;//联网核查头像路径
		private String custNo;// 客户号
		private String resultCheckIdCard;//联网核查结果
		
		//账户信息查询
		private String chuZhangAcctName;//出账卡单位账户户名
		private String chuZhangAcctNo;//出账卡单位账号
		private String chuZhangcardName;// 出账卡户名
		private String chuZhangcardNames;// 带*号的出账卡名
		private String chuZhangCardNo;//出账卡号
		private String chuZhangIdCardNo;//出账身份证号
		private String chuZhangIdCardName;//出账身份证姓名
		private String chuZhangCardCleanAcctNo;//出账卡结算账号
		private String ruZhangcardName;// 入账卡户名
		private String ruZhangcardNames;// 带*号的入账卡名
		private String ruZhangCardNo;//入账卡号
		private String cardPwd;// 卡密码
		private String cardAmt;// 卡可用余额
		private String cardStatus;//卡状态
		private String acctStatus;//账户状态
		private String cardIsPin;//是否验证密码 0-否 1-是	
		private String isMoneyRoot;//是否有金额查询权限 YES有，NO无
		private String certDate;//核心日期
		private String isHaveAcctNo;//查询有无此账户
		private String isUtilAcctNO;//查询是否单位账户
		private String balance;//结存额
		private String erCiInfo;//二磁信息
		
		//黑灰名单
		private String programFlag;//程序标识：单元素100501，双元素100503
		private String ruZhangIdCardNo;//入账身份证号
		private String ruZhangIdCardName;//入账身份证姓名
		private String isChuRu;//校验出账信息还是入账信息：0出账，1入账
		
		//授权柜员信息
		private String supTellerNo;//第一授权柜员号
		private String supTellerNo2;//第二授权柜员号
		private String checkMod;//CHECK_MOD	认证方式	1	1 口令2 指纹 3 口令加指纹
		private String supTellerPass;//第一授权柜员授权密码
		private String supTellerPass2;//第二授权柜员授权密码
		private String finPriLen;//FIN_PRI_LEN	第一授权员指纹长度
		private String finPriVal;//FIN_PRI_VAL	第一授权员指纹值	512
		private String finPriLen2;//FIN_PRI_LEN	第二授权员指纹长度
		private String finPriVal2;//FIN_PRI_VAL	第二授权员指纹值	512
		private String faceCompareVal;//人脸比对结果	
		private String tellerIsChecked;//柜员是否已经检查 null-未检查照片，1-第一授权员已经检查照片，2-需要第二授权员检查照片还未检查，3-第二授权员已经检查照片
		
		//大小额系统参数查询
		private String checkFlag;//查询大额系统参数标志 0-查询大额系统状态 1-查询下一个大额系统日期
		private String sysCode;//系统代码(BEPS：小额,HVPS：大额)
		private String parCode;//参数代码(CUR_SYS_STAT 当前系统状态-与大额绑定,HOLIDAY_FLAG 节假日标志-与小额绑定)
		private String bigCode;//大额返回参数
		private String dateCode;//大额系统下一系统日期
		private String smallCode;//小额返回参数
		
		//机构关系查询交易
		private String TcTdFlag;//通存通兑标志
		
		//单日累计划转金额查询
		private String sumAmt;//单日转账总金额
		
		//大额、小额通用
		private String summCode;//资金用途(编码)
		private String summText;//资金用途(汉字说明)
		private String isWorkDay;//是否为工作日（0、正常工作日；1、工作日最后一天；2、休假日最后一天；3、休假日）
		private String transMarkNo;//选择到账方式交易码
		private String zhangWay;//到账方式
		private String svrJrnlNo;//转账流水号
		private String svrDate;//转账核心日期
		private String appdText;//附言
		
		//大额转账
		private String paymentPwd;//转账密码
		private String remitAmt;//汇款金额
		private String payHbrInstNo;//付款人开户机构号(上送付款人开户行内机构代码)
		private String payHbrInstName;//付款人开户机构名称
		private String recvBankNo;//接收行行号、 收款人开户行号
		private String recvBankName;//接收行行名、  收款人开户行名
		private String recvClrBankNo;//接收行清算行号
		private String recvClrBankName;//接收行清算行名
		private String taskId;//C端生成的前半截任务号，任务号(标识唯一一笔大额往帐业务使用ZHFW+YYMMDD+付款人开户机构号+5位序号)   
		private String taskIdNo;//P端返回的完整任务号
		private String trabsferFlag;//转账标志
		private String nextDaySendFlag;//次日发送标志
		
		//行内汇划
		private String busiType;//业务类型(1、代收  2、代付，默认为代收，代付我行开通未使用)
		private String dbCrFlag;//借贷标志(1_借方2_贷方，默认为借方)
		private String agentInstNo;//被代理机构号(付款人账号所在机构，清算中心上送；网点不上送)
		private String sendBankNo;//提出行行号(默认为本机构)
		private String sendBankName;//提出行名称(由提出行行号自动带出)
		private String payAcctNo;//付款人账号(前端调用账号信息查询接口，前置返回开户行号，前端校验开户行号与提出行号是否一致，不一致报错)
		private String payName;//付款人名称(由付款账号自动带出)
		private String drawCond;//支取条件(选项：0_无 、1_凭印签，内部账户009026205（默认为1 凭印鉴 在讨论）、 其余默认为0，内部账选0 无 时，不验印需授权，对公客户账号默认为1 凭印签，默认为1、凭印签)
		private String billType;//票据种类(必输项（转账支票是代付业务，进账单是代收业务，通用可代收代付。（现凭证种类代码108转账支票， 308通用凭证，107进账单）如果为108时判断是否为该账号下的支票)
		private String billNo;//票据号码(凭证种类为108必输，其他不输自动为灰色)
		private String pin;//支付密码
		private String billDate;//出票日期(当票据种类为108时必输项，其余为空根据出票日期判断印签的有效日期（核心印签库不支持变更前的印鉴核验，要求更改)
		private String noteDate;//提示付款日期(当票据种类为108时必输项，其余为空)
		private String remark;//备注(当票据种类为108时必输项，其余选输)
		private String timedSendTime;//定时发送时间(格式：HHMMSS若NEXT_DAY_SEND_FLAG为空或0，此域无效若NEXT_DAY_SEND_FLAG为1时，此域生效，若此域为空，默认100000之后发送)
		private String purpos;//用途
		
		//55域卡信息
		private String arqc;//ARQC
		private String arqcSrcData;//ARQC生成数据
		private String termChkValue;//终端验证结果
		private String appAcctSeq;//应用主账户序列号
		private String issAppData;//发卡行应用数据
		private String cardPov;//卡片有效期
		
		//权限明细查询字段
		private String qryOption;	//查询选项
		private String cardNO;		//卡号
		
		//收款信息
		private String payeeAcctNo;//收款人帐号
		private String payeeName;//收款人户名
		
		//是否有签名
		private String isSign;//判断是否有签名
		
		//凭条信息
		private String billMsg;//凭条打印调用错误信息
		
		private String svrTranDate; //查询出的核心工作日期
		private String cameraCount;//拍照次数
		private String transNo;//交易笔数
		
		
		//短信查询手机号
		private String telNo;//手机号
		
		private int page=1;//标记是第几页
		private int total;//同时选中最多允许三项
		
		private String qryCode;//撤销交易码
		
		private String origChannel;//原交易渠道
		private String origSysDate;//原交易前置日期
		private String origTaskId;//原交易任务号
		
		private String msTranDate;//撤销交易前置日期
		private String msJrnlNo;//撤销交易前置流水号
		private String transDoTime;//操作时间
		
		
		
		
		
		public String getSupTellerNo2() {
			return supTellerNo2;
		}
		public void setSupTellerNo2(String supTellerNo2) {
			this.supTellerNo2 = supTellerNo2;
		}
		public String getSupTellerPass2() {
			return supTellerPass2;
		}
		public void setSupTellerPass2(String supTellerPass2) {
			this.supTellerPass2 = supTellerPass2;
		}
		public String getFinPriLen2() {
			return finPriLen2;
		}
		public void setFinPriLen2(String finPriLen2) {
			this.finPriLen2 = finPriLen2;
		}
		public String getFinPriVal2() {
			return finPriVal2;
		}
		public void setFinPriVal2(String finPriVal2) {
			this.finPriVal2 = finPriVal2;
		}
		public String getCheckFlag() {
			return checkFlag;
		}
		public void setCheckFlag(String checkFlag) {
			this.checkFlag = checkFlag;
		}
		public String getDateCode() {
			return dateCode;
		}
		public void setDateCode(String dateCode) {
			this.dateCode = dateCode;
		}
		public String getTransDoTime() {
			return transDoTime;
		}
		public void setTransDoTime(String transDoTime) {
			this.transDoTime = transDoTime;
		}
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
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
		
		public String getTransNo() {
			return transNo;
		}

		public void setTransNo(String transNo) {
			this.transNo = transNo;
		}

		public String getCameraCount() {
			return cameraCount;
		}

		public void setCameraCount(String cameraCount) {
			this.cameraCount = cameraCount;
		}

		public String getAppdText() {
			return appdText;
		}

		public void setAppdText(String appdText) {
			this.appdText = appdText;
		}

		public String getPurpos() {
			return purpos;
		}

		public void setPurpos(String purpos) {
			this.purpos = purpos;
		}

		public String getTellerIsChecked() {
			return tellerIsChecked;
		}

		public void setTellerIsChecked(String tellerIsChecked) {
			this.tellerIsChecked = tellerIsChecked;
		}

		public String getBalance() {
			return balance;
		}

		public void setBalance(String balance) {
			this.balance = balance;
		}

		public String getTransMarkNo() {
			return transMarkNo;
		}

		public void setTransMarkNo(String transMarkNo) {
			this.transMarkNo = transMarkNo;
		}


		public String getIsWorkDay() {
			return isWorkDay;
		}

		public void setIsWorkDay(String isWorkDay) {
			this.isWorkDay = isWorkDay;
		}

		public String getCertDate() {
			return certDate;
		}

		public void setCertDate(String certDate) {
			this.certDate = certDate;
		}
		
		public String getQryOption() {
			return qryOption;
		}

		public void setQryOption(String qryOption) {
			this.qryOption = qryOption;
		}

		public String getCardNO() {
			return cardNO;
		}

		public void setCardNO(String cardNO) {
			this.cardNO = cardNO;
		}
		
		public String getBusiType() {
			return busiType;
		}

		public void setBusiType(String busiType) {
			this.busiType = busiType;
		}

		public String getDbCrFlag() {
			return dbCrFlag;
		}

		public void setDbCrFlag(String dbCrFlag) {
			this.dbCrFlag = dbCrFlag;
		}

		public String getAgentInstNo() {
			return agentInstNo;
		}

		public void setAgentInstNo(String agentInstNo) {
			this.agentInstNo = agentInstNo;
		}

		public String getSendBankNo() {
			return sendBankNo;
		}

		public void setSendBankNo(String sendBankNo) {
			this.sendBankNo = sendBankNo;
		}

		public String getSendBankName() {
			return sendBankName;
		}

		public void setSendBankName(String sendBankName) {
			this.sendBankName = sendBankName;
		}

		public String getPayAcctNo() {
			return payAcctNo;
		}

		public void setPayAcctNo(String payAcctNo) {
			this.payAcctNo = payAcctNo;
		}

		public String getPayName() {
			return payName;
		}

		public void setPayName(String payName) {
			this.payName = payName;
		}

		public String getDrawCond() {
			return drawCond;
		}

		public void setDrawCond(String drawCond) {
			this.drawCond = drawCond;
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

		public String getPin() {
			return pin;
		}

		public void setPin(String pin) {
			this.pin = pin;
		}

		public String getBillDate() {
			return billDate;
		}

		public void setBillDate(String billDate) {
			this.billDate = billDate;
		}

		public String getNoteDate() {
			return noteDate;
		}

		public void setNoteDate(String noteDate) {
			this.noteDate = noteDate;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getSummCode() {
			
			return summCode;
		}

		public void setSummCode(String summCode) {
			this.summCode = summCode;
		}

		public String getTimedSendTime() {
			return timedSendTime;
		}

		public void setTimedSendTime(String timedSendTime) {
			this.timedSendTime = timedSendTime;
		}

		public Map<String, Object> getParams() {
			return params;
		}

		public void setParams(Map<String, Object> params) {
			this.params = params;
		}

		public String getCardBin() {
			return cardBin;
		}

		public void setCardBin(String cardBin) {
			this.cardBin = cardBin;
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

		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public String getCustNo() {
			return custNo;
		}

		public void setCustNo(String custNo) {
			this.custNo = custNo;
		}

		public String getPhotoPath() {
			return photoPath;
		}

		public void setPhotoPath(String photoPath) {
			this.photoPath = photoPath;
		}

		public String getChuZhangcardName() {
			return chuZhangcardName;
		}

		public void setChuZhangcardName(String chuZhangcardName) {
			this.chuZhangcardName = chuZhangcardName;
		}

		public String getChuZhangcardNames() {
			return chuZhangcardNames;
		}

		public void setChuZhangcardNames(String chuZhangcardNames) {
			this.chuZhangcardNames = chuZhangcardNames;
		}

		public String getChuZhangCardNo() {
			return chuZhangCardNo;
		}

		public void setChuZhangCardNo(String chuZhangCardNo) {
			this.chuZhangCardNo = chuZhangCardNo;
		}
		
		public String getRuZhangcardName() {
			return ruZhangcardName;
		}

		public void setRuZhangcardName(String ruZhangcardName) {
			this.ruZhangcardName = ruZhangcardName;
		}

		public String getRuZhangcardNames() {
			return ruZhangcardNames;
		}

		public void setRuZhangcardNames(String ruZhangcardNames) {
			this.ruZhangcardNames = ruZhangcardNames;
		}

		public String getRuZhangCardNo() {
			return ruZhangCardNo;
		}

		public void setRuZhangCardNo(String ruZhangCardNo) {
			this.ruZhangCardNo = ruZhangCardNo;
		}

		public String getCardPwd() {
			return cardPwd;
		}

		public void setCardPwd(String cardPwd) {
			this.cardPwd = cardPwd;
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

		public String getAcctStatus() {
			return acctStatus;
		}

		public void setAcctStatus(String acctStatus) {
			this.acctStatus = acctStatus;
		}

		public String getCardIsPin() {
			return cardIsPin;
		}

		public void setCardIsPin(String cardIsPin) {
			this.cardIsPin = cardIsPin;
		}

		public String getProgramFlag() {
			return programFlag;
		}

		public void setProgramFlag(String programFlag) {
			this.programFlag = programFlag;
		}

		public String getRuZhangIdCardNo() {
			return ruZhangIdCardNo;
		}

		public void setRuZhangIdCardNo(String ruZhangIdCardNo) {
			this.ruZhangIdCardNo = ruZhangIdCardNo;
		}

		public String getRuZhangIdCardName() {
			return ruZhangIdCardName;
		}

		public void setRuZhangIdCardName(String ruZhangIdCardName) {
			this.ruZhangIdCardName = ruZhangIdCardName;
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

		public String getCheckMod() {
			return checkMod;
		}

		public void setCheckMod(String checkMod) {
			this.checkMod = checkMod;
		}

		public String getSupTellerPass() {
			return supTellerPass;
		}

		public void setSupTellerPass(String supTellerPass) {
			this.supTellerPass = supTellerPass;
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

		public String getFaceCompareVal() {
			return faceCompareVal;
		}

		public void setFaceCompareVal(String faceCompareVal) {
			this.faceCompareVal = faceCompareVal;
		}

		public String getIsCardBin() {
			return isCardBin;
		}

		public void setIsCardBin(String isCardBin) {
			this.isCardBin = isCardBin;
		}

		public String getIsMoneyRoot() {
			return isMoneyRoot;
		}

		public void setIsMoneyRoot(String isMoneyRoot) {
			this.isMoneyRoot = isMoneyRoot;
		}

		public String getIsChuRu() {
			return isChuRu;
		}

		public void setIsChuRu(String isChuRu) {
			this.isChuRu = isChuRu;
		}

		public String getSysCode() {
			return sysCode;
		}

		public void setSysCode(String sysCode) {
			this.sysCode = sysCode;
		}

		public String getParCode() {
			return parCode;
		}

		public void setParCode(String parCode) {
			this.parCode = parCode;
		}

		public String getBigCode() {
			return bigCode;
		}

		public void setBigCode(String bigCode) {
			this.bigCode = bigCode;
		}

		public String getSmallCode() {
			return smallCode;
		}

		public void setSmallCode(String smallCode) {
			this.smallCode = smallCode;
		}

		public String getTcTdFlag() {
			return TcTdFlag;
		}

		public void setTcTdFlag(String tcTdFlag) {
			TcTdFlag = tcTdFlag;
		}

		public String getSumAmt() {
			return sumAmt;
		}

		public void setSumAmt(String sumAmt) {
			this.sumAmt = sumAmt;
		}

		public String getChuZhangIdCardNo() {
			return chuZhangIdCardNo;
		}

		public void setChuZhangIdCardNo(String chuZhangIdCardNo) {
			this.chuZhangIdCardNo = chuZhangIdCardNo;
		}

		public String getChuZhangIdCardName() {
			return chuZhangIdCardName;
		}

		public void setChuZhangIdCardName(String chuZhangIdCardName) {
			this.chuZhangIdCardName = chuZhangIdCardName;
		}

		public String getPaymentPwd() {
			return paymentPwd;
		}

		public void setPaymentPwd(String paymentPwd) {
			this.paymentPwd = paymentPwd;
		}

		public String getSummText() {
			return summText;
		}

		public void setSummText(String summText) {
			this.summText = summText;
		}

		public String getRemitAmt() {
			return remitAmt;
		}

		public void setRemitAmt(String remitAmt) {
			this.remitAmt = remitAmt;
		}

		public String getRecvBankNo() {
			return recvBankNo;
		}

		public void setRecvBankNo(String recvBankNo) {
			this.recvBankNo = recvBankNo;
		}

		public String getRecvBankName() {
			return recvBankName;
		}

		public void setRecvBankName(String recvBankName) {
			this.recvBankName = recvBankName;
		}

		public String getTaskId() {
			return taskId;
		}

		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}

		public String getTrabsferFlag() {
			return trabsferFlag;
		}

		public void setTrabsferFlag(String trabsferFlag) {
			this.trabsferFlag = trabsferFlag;
		}

		public String getNextDaySendFlag() {
			return nextDaySendFlag;
		}

		public void setNextDaySendFlag(String nextDaySendFlag) {
			this.nextDaySendFlag = nextDaySendFlag;
		}

		public String getRecvClrBankNo() {
			return recvClrBankNo;
		}

		public void setRecvClrBankNo(String recvClrBankNo) {
			this.recvClrBankNo = recvClrBankNo;
		}

		public String getRecvClrBankName() {
			return recvClrBankName;
		}

		public void setRecvClrBankName(String recvClrBankName) {
			this.recvClrBankName = recvClrBankName;
		}

		public String getPayHbrInstNo() {
			return payHbrInstNo;
		}

		public void setPayHbrInstNo(String payHbrInstNo) {
			this.payHbrInstNo = payHbrInstNo;
		}

		public String getPayeeAcctNo() {
			return payeeAcctNo;
		}

		public void setPayeeAcctNo(String payeeAcctNo) {
			this.payeeAcctNo = payeeAcctNo;
		}

		public String getPayeeName() {
			return payeeName;
		}

		public void setPayeeName(String payeeName) {
			this.payeeName = payeeName;
		}

		public String getIsSign() {
			return isSign;
		}

		public void setIsSign(String isSign) {
			this.isSign = isSign;
		}

		public String getChuZhangCardCleanAcctNo() {
			return chuZhangCardCleanAcctNo;
		}

		public void setChuZhangCardCleanAcctNo(String chuZhangCardCleanAcctNo) {
			this.chuZhangCardCleanAcctNo = chuZhangCardCleanAcctNo;
		}

		public String getPayHbrInstName() {
			return payHbrInstName;
		}

		public void setPayHbrInstName(String payHbrInstName) {
			this.payHbrInstName = payHbrInstName;
		}

		public String getChuZhangAcctName() {
			return chuZhangAcctName;
		}

		public void setChuZhangAcctName(String chuZhangAcctName) {
			this.chuZhangAcctName = chuZhangAcctName;
		}

		public String getIsRuZhangCardBin() {
			return isRuZhangCardBin;
		}

		public void setIsRuZhangCardBin(String isRuZhangCardBin) {
			this.isRuZhangCardBin = isRuZhangCardBin;
		}

		public String getIsCardBank() {
			return isCardBank;
		}

		public void setIsCardBank(String isCardBank) {
			this.isCardBank = isCardBank;
		}

		public String getRuZhangCardBin() {
			return ruZhangCardBin;
		}

		public void setRuZhangCardBin(String ruZhangCardBin) {
			this.ruZhangCardBin = ruZhangCardBin;
		}

		public String getChuZhangAcctNo() {
			return chuZhangAcctNo;
		}

		public void setChuZhangAcctNo(String chuZhangAcctNo) {
			this.chuZhangAcctNo = chuZhangAcctNo;
		}

		public String getZhangWay() {
			return zhangWay;
		}

		public void setZhangWay(String zhangWay) {
			this.zhangWay = zhangWay;
		}

		public String getSvrJrnlNo() {
			return svrJrnlNo;
		}

		public void setSvrJrnlNo(String svrJrnlNo) {
			this.svrJrnlNo = svrJrnlNo;
		}

		public String getResultCheckIdCard() {
			return resultCheckIdCard;
		}

		public void setResultCheckIdCard(String resultCheckIdCard) {
			this.resultCheckIdCard = resultCheckIdCard;
		}

		public String getBillMsg() {
			return billMsg;
		}

		public void setBillMsg(String billMsg) {
			this.billMsg = billMsg;
		}

		public String getIsHaveAcctNo() {
			return isHaveAcctNo;
		}

		public void setIsHaveAcctNo(String isHaveAcctNo) {
			this.isHaveAcctNo = isHaveAcctNo;
		}

		public String getIsUtilAcctNO() {
			return isUtilAcctNO;
		}

		public void setIsUtilAcctNO(String isUtilAcctNO) {
			this.isUtilAcctNO = isUtilAcctNO;
		}

		public String getSvrTranDate() {
			return svrTranDate;
		}

		public void setSvrTranDate(String svrTranDate) {
			this.svrTranDate = svrTranDate;
		}
		
		public String getTelNo() {
			return telNo;
		}

		public void setTelNo(String telNo) {
			this.telNo = telNo;
		}

		public String getQryCode() {
			return qryCode;
		}

		public void setQryCode(String qryCode) {
			this.qryCode = qryCode;
		}

		public String getOrigChannel() {
			return origChannel;
		}

		public void setOrigChannel(String origChannel) {
			this.origChannel = origChannel;
		}

		public String getOrigSysDate() {
			return origSysDate;
		}

		public void setOrigSysDate(String origSysDate) {
			this.origSysDate = origSysDate;
		}

		public String getOrigTaskId() {
			return origTaskId;
		}

		public void setOrigTaskId(String origTaskId) {
			this.origTaskId = origTaskId;
		}

		public String getMsTranDate() {
			return msTranDate;
		}

		public void setMsTranDate(String msTranDate) {
			this.msTranDate = msTranDate;
		}

		public String getMsJrnlNo() {
			return msJrnlNo;
		}

		public void setMsJrnlNo(String msJrnlNo) {
			this.msJrnlNo = msJrnlNo;
		}
		public Set<String> getSetFlag() {
			return setFlag;
		}
		public void setSetFlag(Set<String> setFlag) {
			this.setFlag = setFlag;
		}
		public String getTaskIdNo() {
			return taskIdNo;
		}
		public void setTaskIdNo(String taskIdNo) {
			this.taskIdNo = taskIdNo;
		}

}
