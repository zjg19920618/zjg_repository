package com.boomhope.Bill.TransService.LostReport.Bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boomhope.Bill.Framework.BaseTransBean;

/**
 * 挂失/解挂公共bean字段
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
public class LostPubBean extends BaseTransBean {
	
	//业务选择
	private String LOrS;//选择挂失或解挂(0-挂失，1-解挂)
	private String LostOrSolve;//选择具体业务(0-单独挂失，1-挂失补领,2-挂失销户，3-解挂补发,4-解挂销户,5-挂失撤销)
	
	//挂失相关标识
	private Map<String,ShowAccNoMsgBean> accMap = new HashMap<String , ShowAccNoMsgBean>();//存放选中要挂失的账户信息      selectMsg：showAccNoMsgBean
	private Map<String,List> importMap = new HashMap<String,List>();// 重要字段
	private String YseNoPass;//是否知道密码(0-知道，1不知道)
	private String RecOrCan;//选择挂失补领或挂失销户(0-补领，1-销户)
	private String IsCan;//选择是否立即销户(0-否，1-是)
	private String lostType;//挂失种类(0-银行卡挂失，1-存单挂失，2-存折挂失)
	private String SolveLostType;//解挂种类(0-银行卡解挂，1-个人存单解挂，1_1-卡下子账户存单解挂，1_2-电子子账户存单解挂，2-存折解挂)
	private String pwd;//录入的密码
	private String custNo;//客户号
	private String[] cards;//卡号列表
	private String cardNo;//卡号
	private String openDate;//开户日期
	private String endDate;//到期日
	private String openAmt;//开户金额
	private String openAmtUpper;//开户金额大写
	private String depTerm;//存期
	private String accType;//账户类型(银行卡，个人存单，存折，卡下子账户，电子子账户)
	private String certNo;//凭证号码
	private String custName;//客户名称
	private String endAmt;//存单/存折结存额，卡及卡子帐号总结存额
	private String cardEndAmt;//卡结存额
	private String totalAmt;//存折余额
	private String limitAmt;//查询出的限额
	private String proName;//产品名称
	private String proCode;//产品代码(0000-存折，其他存单)
	private String openRate;//开户利率
	private String accState;//账户状态
	private String cardState;//卡状态
	private String accKind;//账户种类(1-个人，2-一本通，其他)
	private String drawCond;//支付条件(0-无密码，1-凭密码，2-凭证件，3-凭印鉴)
	private String cardSubNo;//卡下子帐号/电子子帐号
	private String IsPinPass; //是否验密(0-不验，1-验)
	private String accIdType;//返回的证件类型
	private String accIdNo;//返回身份证号
	private String accIdName;//返回的客户名称
	private String windownTepterm;//窗口期
	private String openChannel;//开户渠道
	private String preDate;//预约日期
	private String stateName;//账户状态
	private String lostprintState;//申请书打印状态(f.打印失败)
	
	
	//挂失处理相关标识
	private String lostJrnlNo;//挂失/解挂统一流水号
	private String lostApplNo;//挂失申请书号
	private String newAccNo;//挂失补发时返回的新账号（只针对普通账户，卡下子账户和电子账户子账户为原来的账号+子账号）
	private String lostJpgPath;//挂失申请书图片路径
	private String lostselect;//上传前置挂失标识 (0.挂失  1. 解挂)
	
	//挂失销户相关
	private List<String> backCards = new ArrayList<String>();//筛选所有正常状态的银行卡
	private String selectCardNo;//销户转入的银行卡
	private String chkFlag;//理财产品可销户标志(N-不允许，Y-允许)
	private String amtFlag;//是否有关联贷款标志(0-没有，1-有)
	private String fundFlag;//基金是否可销户标志(S可以,F不可以)
	private String IsAgreedDep;//是否约定转存(0 未开通 1 开通)
	private String agreeDepJrnlNo;//取消约定转存流水号
	private String agreeInt;//约定转存利息
	private String agreeIntTax;//约定转存利息税
	private String agreeRealInt;//约定转存实付利息
	private String agreeAmt;//约定转存金额
	private String canceAmt;//销户实付本金
	private String cancelRealInte;//销户实付利息
	private String tdPayAmt;//唐豆支取金额
	private String Shtds;//唐豆收回数量
	private String shtdy;//唐豆收回金额
	private String tdshJrnlNo;//唐豆收回流水号
	private String tangCode;//唐豆收回错误码
	private String tangErrMsg;//唐豆收回错误信息
	private String tangChaErrMsg;//唐豆查询错误信息
	private String XYDChaErrMsg;//已付收益查询错误信息
	private String advnInit;//已付收益
	private String yjlx;//预计利息
	private String tiZhiQu;//提前支取标志(0-已到期，1-未到期)
	private String xydAmt;//幸运豆
	private String zydAmt;//增益豆
	private String xfdCount;//消费豆数量
	private String xfdAmt;//消费豆金额
	private String pinErrMsg;//调用凭条错误信息
	private String clearRate;//清户利率
	
	//解挂相关
	private LostCheckBean applInfos;//挂失申请书查询的信息
	private String solveType;//解挂种类（0-卡，1-非卡（账户、存折））
	private String reMakePwd;//重置的密码
	private String lostDate;//挂失日期
	private String openInst;//开户机构号
	private String solveAccState;//挂失状态(0_卡正式挂失、1_密码+卡挂失、2_存单正式挂失、3密码+存单挂失、4_存折正式挂失、5密码+存折挂失)
	private String reMakePwdJrnlNo;//重置的密码流水号()

	public String getLostprintState() {
		return lostprintState;
	}

	public void setLostprintState(String lostprintState) {
		this.lostprintState = lostprintState;
	}

	public String getLostselect() {
		return lostselect;
	}

	public void setLostselect(String lostselect) {
		this.lostselect = lostselect;
	}

	public String getReMakePwdJrnlNo() {
		return reMakePwdJrnlNo;
	}

	public void setReMakePwdJrnlNo(String reMakePwdJrnlNo) {
		this.reMakePwdJrnlNo = reMakePwdJrnlNo;
	}

	
	public String getXfdCount() {
		return xfdCount;
	}

	public void setXfdCount(String xfdCount) {
		this.xfdCount = xfdCount;
	}

	public String getXfdAmt() {
		return xfdAmt;
	}

	public void setXfdAmt(String xfdAmt) {
		this.xfdAmt = xfdAmt;
	}

	public String getLostJpgPath() {
		return lostJpgPath;
	}

	public void setLostJpgPath(String lostJpgPath) {
		this.lostJpgPath = lostJpgPath;
	}

	public String getXydAmt() {
		return xydAmt;
	}

	public void setXydAmt(String xydAmt) {
		this.xydAmt = xydAmt;
	}

	public String getZydAmt() {
		return zydAmt;
	}

	public void setZydAmt(String zydAmt) {
		this.zydAmt = zydAmt;
	}

	public String getOpenChannel() {
		return openChannel;
	}

	public void setOpenChannel(String openChannel) {
		this.openChannel = openChannel;
	}

	public String getLostDate() {
		return lostDate;
	}

	public void setLostDate(String lostDate) {
		this.lostDate = lostDate;
	}

	public String getReMakePwd() {
		return reMakePwd;
	}

	public void setReMakePwd(String reMakePwd) {
		this.reMakePwd = reMakePwd;
	}

	public String getSolveType() {
		return solveType;
	}

	public void setSolveType(String solveType) {
		this.solveType = solveType;
	}

	public LostCheckBean getApplInfos() {
		return applInfos;
	}

	public void setApplInfos(LostCheckBean applInfos) {
		this.applInfos = applInfos;
	}

	public String getLostOrSolve() {
		return LostOrSolve;
	}

	public String getWindownTepterm() {
		return windownTepterm;
	}

	public void setWindownTepterm(String windownTepterm) {
		this.windownTepterm = windownTepterm;
	}

	public String getOpenAmtUpper() {
		return openAmtUpper;
	}

	public void setOpenAmtUpper(String openAmtUpper) {
		this.openAmtUpper = openAmtUpper;
	}

	public String getNewAccNo() {
		return newAccNo;
	}

	public void setNewAccNo(String newAccNo) {
		this.newAccNo = newAccNo;
	}

	public String getLostJrnlNo() {
		return lostJrnlNo;
	}

	public void setLostJrnlNo(String lostJrnlNo) {
		this.lostJrnlNo = lostJrnlNo;
	}

	public String getLostApplNo() {
		return lostApplNo;
	}

	public void setLostApplNo(String lostApplNo) {
		this.lostApplNo = lostApplNo;
	}

	public void setLostOrSolve(String lostOrSolve) {
		LostOrSolve = lostOrSolve;
	}

	public String getYseNoPass() {
		return YseNoPass;
	}

	public void setYseNoPass(String yseNoPass) {
		YseNoPass = yseNoPass;
	}

	public String getRecOrCan() {
		return RecOrCan;
	}

	public void setRecOrCan(String recOrCan) {
		RecOrCan = recOrCan;
	}

	public String getLostType() {
		return lostType;
	}

	public void setLostType(String lostType) {
		this.lostType = lostType;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String[] getCards() {
		return cards;
	}

	public void setCards(String[] cards) {
		this.cards = cards;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Map getAccMap() {
		return accMap;
	}

	public void setAccMap(Map<String, ShowAccNoMsgBean> accMap) {
		this.accMap = accMap;
	}

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public String getDepTerm() {
		return depTerm;
	}

	public void setDepTerm(String depTerm) {
		this.depTerm = depTerm;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getEndAmt() {
		return endAmt;
	}

	public void setEndAmt(String endAmt) {
		this.endAmt = endAmt;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getAccState() {
		return accState;
	}

	public void setAccState(String accState) {
		this.accState = accState;
	}

	public String getAccKind() {
		return accKind;
	}

	public void setAccKind(String accKind) {
		this.accKind = accKind;
	}

	public String getCardState() {
		return cardState;
	}

	public void setCardState(String cardState) {
		this.cardState = cardState;
	}

	public String getCardSubNo() {
		return cardSubNo;
	}

	public void setCardSubNo(String cardSubNo) {
		this.cardSubNo = cardSubNo;
	}

	public String getLOrS() {
		return LOrS;
	}

	public void setLOrS(String lOrS) {
		LOrS = lOrS;
	}

	public String getIsPinPass() {
		return IsPinPass;
	}

	public void setIsPinPass(String isPinPass) {
		IsPinPass = isPinPass;
	}

	public String getOpenAmt() {
		return openAmt;
	}

	public void setOpenAmt(String openAmt) {
		this.openAmt = openAmt;
	}

	public String getDrawCond() {
		return drawCond;
	}

	public void setDrawCond(String drawCond) {
		this.drawCond = drawCond;
	}

	public String getAccIdType() {
		return accIdType;
	}

	public void setAccIdType(String accIdType) {
		this.accIdType = accIdType;
	}

	public String getAccIdNo() {
		return accIdNo;
	}

	public void setAccIdNo(String accIdNo) {
		this.accIdNo = accIdNo;
	}

	public String getAccIdName() {
		return accIdName;
	}

	public void setAccIdName(String accIdName) {
		this.accIdName = accIdName;
	}

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getCardEndAmt() {
		return cardEndAmt;
	}

	public void setCardEndAmt(String cardEndAmt) {
		this.cardEndAmt = cardEndAmt;
	}

	public String getChkFlag() {
		return chkFlag;
	}

	public void setChkFlag(String chkFlag) {
		this.chkFlag = chkFlag;
	}

	public String getAmtFlag() {
		return amtFlag;
	}

	public void setAmtFlag(String amtFlag) {
		this.amtFlag = amtFlag;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getIsAgreedDep() {
		return IsAgreedDep;
	}

	public void setIsAgreedDep(String isAgreedDep) {
		IsAgreedDep = isAgreedDep;
	}

	public String getSelectCardNo() {
		return selectCardNo;
	}

	public void setSelectCardNo(String selectCardNo) {
		this.selectCardNo = selectCardNo;
	}

	public List<String> getBackCards() {
		return backCards;
	}

	public void setBackCards(List<String> backCards) {
		this.backCards = backCards;
	}

	public String getLimitAmt() {
		return limitAmt;
	}

	public void setLimitAmt(String limitAmt) {
		this.limitAmt = limitAmt;
	}

	public String getAgreeDepJrnlNo() {
		return agreeDepJrnlNo;
	}

	public void setAgreeDepJrnlNo(String agreeDepJrnlNo) {
		this.agreeDepJrnlNo = agreeDepJrnlNo;
	}

	public String getAgreeInt() {
		return agreeInt;
	}

	public void setAgreeInt(String agreeInt) {
		this.agreeInt = agreeInt;
	}

	public String getAgreeIntTax() {
		return agreeIntTax;
	}

	public void setAgreeIntTax(String agreeIntTax) {
		this.agreeIntTax = agreeIntTax;
	}

	public String getAgreeRealInt() {
		return agreeRealInt;
	}

	public void setAgreeRealInt(String agreeRealInt) {
		this.agreeRealInt = agreeRealInt;
	}

	public String getAgreeAmt() {
		return agreeAmt;
	}

	public void setAgreeAmt(String agreeAmt) {
		this.agreeAmt = agreeAmt;
	}

	public String getFundFlag() {
		return fundFlag;
	}

	public void setFundFlag(String fundFlag) {
		this.fundFlag = fundFlag;
	}

	public String getOpenRate() {
		return openRate;
	}

	public void setOpenRate(String openRate) {
		this.openRate = openRate;
	}

	public String getCanceAmt() {
		return canceAmt;
	}

	public void setCanceAmt(String canceAmt) {
		this.canceAmt = canceAmt;
	}

	public String getCancelRealInte() {
		return cancelRealInte;
	}

	public void setCancelRealInte(String cancelRealInte) {
		this.cancelRealInte = cancelRealInte;
	}

	public String getTdshJrnlNo() {
		return tdshJrnlNo;
	}

	public void setTdshJrnlNo(String tdshJrnlNo) {
		this.tdshJrnlNo = tdshJrnlNo;
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
		return Shtds;
	}

	public void setShtds(String shtds) {
		Shtds = shtds;
	}

	public String getTangCode() {
		return tangCode;
	}

	public void setTangCode(String tangCode) {
		this.tangCode = tangCode;
	}

	public String getTangErrMsg() {
		return tangErrMsg;
	}

	public void setTangErrMsg(String tangErrMsg) {
		this.tangErrMsg = tangErrMsg;
	}

	public String getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getSolveLostType() {
		return SolveLostType;
	}

	public void setSolveLostType(String solveLostType) {
		SolveLostType = solveLostType;
	}

	public String getOpenInst() {
		return openInst;
	}

	public void setOpenInst(String openInst) {
		this.openInst = openInst;
	}

	public String getSolveAccState() {
		return solveAccState;
	}

	public void setSolveAccState(String solveAccState) {
		this.solveAccState = solveAccState;
	}

	public String getIsCan() {
		return IsCan;
	}

	public void setIsCan(String isCan) {
		IsCan = isCan;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getAdvnInit() {
		return advnInit;
	}

	public void setAdvnInit(String advnInit) {
		this.advnInit = advnInit;
	}

	public String getYjlx() {
		return yjlx;
	}

	public void setYjlx(String yjlx) {
		this.yjlx = yjlx;
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

	public String getPreDate() {
		return preDate;
	}

	public void setPreDate(String preDate) {
		this.preDate = preDate;
	}

	public String getTiZhiQu() {
		return tiZhiQu;
	}

	public void setTiZhiQu(String tiZhiQu) {
		this.tiZhiQu = tiZhiQu;
	}

	public String getPinErrMsg() {
		return pinErrMsg;
	}

	public void setPinErrMsg(String pinErrMsg) {
		this.pinErrMsg = pinErrMsg;
	}

	public Map<String, List> getImportMap() {
		return importMap;
	}

	public void setImportMap(Map<String, List> importMap) {
		this.importMap = importMap;
	}

	public String getClearRate() {
		return clearRate;
	}

	public void setClearRate(String clearRate) {
		this.clearRate = clearRate;
	}

}
