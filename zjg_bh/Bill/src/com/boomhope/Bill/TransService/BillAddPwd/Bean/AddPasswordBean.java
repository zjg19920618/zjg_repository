package com.boomhope.Bill.TransService.BillAddPwd.Bean;

import java.util.HashMap;
import java.util.Map;

import com.boomhope.Bill.Framework.BaseTransBean;

public class AddPasswordBean extends BaseTransBean{

	private String subAccNoCancel;//判断卡下子账户、普通账户(0:卡下子账号;1：普通账号  3:电子账户)
	
	//身份证信息
	private String readIdNo;//读取的身份证号
	private String readIdName;//读取的身份证名称
	private String address;// 户口地址
	private String sex;// 性别
	private String qfjg;// 证件签发机关
	private String birthday;//生日
	private String endDate;//有效期结束
	
	private String fristPassword;//第一次输入密码
	private String password;//密码
	//联网核查
	private String inspect;//本人联网核查结果信息
	
	//人脸识别
	private String checkFaceMsg;//照片较验结果
	private String faceCompareVal;//比较值
	private String cameraCount;//拍照次数
	private String racameraCout;//检查客户信息页重新拍照 
	
	private String idType;//身份证类型
	private String idNo;//身份证号
	private String idName;//身份证名称
	
	//账户信息
	private String accFirstNo;//第一次识别的账号
	private String billFirstNo;//第一次识别的凭证号
	private String accNo;	   // 账号
	private String subCardNo;	//子账号所属卡号
	private String subAccNo;	//子账号	
	private String custNo;	//客户号	
	private String billType;//凭证种类
	private String billNo;	// 识别、输入的凭证号
	private String billPass;//存单密码
	private String accName;	// 户名
	private String amount;	// 本金
	private String totalAmt;//存折余额
	private String drawCoun;//是否有密码(0 无密码，1 有密码)
	private String OpenInstNo;//存单开户机构号
	private String openDate;// 开户日
	private String dueDate;	// 到期日
	private String proName; // 产品名称
	private String proCode; // 产品编号
	private String svrDate;//核心日期
	private String startDate;// 起息日
	private String accState;//存单状态
	private String openQlt;//存单户性质
	private String certNo;//前置、返回的凭证号
	private String billIdNo;//返回的存单身份证号
	private String billIdName;//返回的存单户名
	private String backAccNo;//查询出返回的账号
	
	private Map<String, Object> map = new HashMap<String, Object>();//读卡器读取的信息
	private String jwState;//鉴伪次数标识(1-鉴伪通过金额超限后授权标识)
	private String authNoCode;//授权主交易码
	private String finPriLen;//FIN_PRI_LEN	指纹长度
	private String finPriVal;//FIN_PRI_VAL	指纹值	512
	private String checkMod;//CHECK_MOD	认证方式	1	1 口令2 指纹 3 口令加指纹
	private String supTellerNo;//授权柜员号
	private String tellNo1;//授权柜员1
	private String tellNo2;//授权柜员2
	private String tellNo3;//授权柜员3
	private String supPass;//授权密码
	private String tellNoState;//是否已对第一个柜员授权(1-鉴伪失败金额超限第一次授权标识，2-鉴伪失败金额超限第二次授权标识/第一次鉴伪通过但第二次鉴伪失败第二次授权标识)
	private String jwResult;//鉴伪结果
	private String ZMSvrJrnlNo;//增密流水号
	private String billIdType;// 存单对应的证件类型
	private String billIdCard;// 存单对应的身份证号
	private String billPath_fc;//存单正面
	private String billPath_rc;//存单背面
	
	private String checkStatus;//标记存单是否自动识别，1自动识别，2手动输入
	private String fid;//插入存单生产流程序号
	private String isCheckPass;//是否验密(0-否，1-是)

	private String SPisSign;  //是否有签名

	private String tellerIsChecked;//授权之前是否确认用户信息(1-已确认用户信息 2-允许授权)


	
	public String getRacameraCout() {
		return racameraCout;
	}
	public void setRacameraCout(String racameraCout) {
		this.racameraCout = racameraCout;
	}
	public String getFristPassword() {
		return fristPassword;
	}
	public void setFristPassword(String fristPassword) {
		this.fristPassword = fristPassword;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getSPisSign() {
		return SPisSign;
	}
	public void setSPisSign(String sPisSign) {
		SPisSign = sPisSign;
	}


	
	
	public String getZMSvrJrnlNo() {
		return ZMSvrJrnlNo;
	}
	public void setZMSvrJrnlNo(String zMSvrJrnlNo) {
		ZMSvrJrnlNo = zMSvrJrnlNo;
	}
	public String getTellNo3() {
		return tellNo3;
	}
	public void setTellNo3(String tellNo3) {
		this.tellNo3 = tellNo3;
	}
	public String getTellerIsChecked() {
		return tellerIsChecked;
	}
	public void setTellerIsChecked(String tellerIsChecked) {
		this.tellerIsChecked = tellerIsChecked;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getInspect() {
		return inspect;
	}
	public void setInspect(String inspect) {
		this.inspect = inspect;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
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
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
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
	public String getAuthNoCode() {
		return authNoCode;
	}
	public void setAuthNoCode(String authNoCode) {
		this.authNoCode = authNoCode;
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
	public String getSupPass() {
		return supPass;
	}
	public void setSupPass(String supPass) {
		this.supPass = supPass;
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
	
}
