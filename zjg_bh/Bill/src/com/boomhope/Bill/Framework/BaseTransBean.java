package com.boomhope.Bill.Framework;

import java.awt.Component;

import com.boomhope.Bill.peripheral.bean.IdCardReadBean;
import com.boomhope.Bill.monitor.bean.ReqMCM001;

/***
 * 基础交易Bean
 * @author hk
 *
 */
@SuppressWarnings("rawtypes")
public class BaseTransBean {
	
	private String transNo;	// 交易代码
	
	//打印存单凭证信息
	private String certNoAdd;//凭证号CERT_NO_ADD
	private String certNoId;//凭证ID
	private String certStartNo;//起始凭证号
	private String certEndNo;//结束凭证号
	private String creatTime;//创建时间
	private String isEnought;//凭条是否充足 0：足够 1:不足
	
	//下一步操作标志
	private static String nextStepName;//记录下一步去往页面的名字
	private static String upStepName;//上一步页面标记
	private static Component thisComponent;//当前页面
	
	
	/**
	 * 公共信息字段
	 */
	private static String allPubAccNo;//账号（卡/电子-子账号/存单号/存折号）
	private static String allPubPassAccNo;//校验密码所用的的(卡号/存单号/存折号)
	private static String allPubAccPwd;//密码(银行卡密码/存单密码/存折密码)
	private static String allPubAccSttn;//账户黑灰名单状态  0-正常，1-涉案名单，2-可疑名单
	private static String allPubSvrDate;//核心日期 
	
	/**
	 * 身份证信息字段
	 */
	private static String allPubIsDeputy;//是否为代理人 0-本人 1-代理人
	private static String allPubCheckIdCardNo;//被查询的身份正号（主要用于本人和代理人身份查询时的公共接口）
	private static String allPubCheckIdCardName;//被查询的身份名字（主要用于本人和代理人身份查询时的公共接口）
	//本人身份信息
	private static IdCardReadBean allPubIDCardReadInfo;
	private static String allPubIDNo;//身份证号
	private static String allPubIdCardName;//身份证名称
	private static String allPubAddress;// 户口地址
	private static String allPubSex;// 性别
	private static String allPubQfjg;// 证件签发机关
	private static String allPubIdCardface;//身份证正面照路径
	private static String allPubIdCardtitle;//身份证上头像照片路径
	private static String allPubIdCardback;//身份证背面照路径
	private static String allPubBirthday;//生日
	private static String allPubPhone;//本人电话
	private static String allPubEndDate;//有效期结束	
	private static String allPubPhotoPath;//联网核查本人头像路径
	private static String allPubInspect;//联网核查本人结果
	private static String allPubSttn;//本人黑灰名单状态 0-正常，1-涉案名单，2-可疑名单
	//代理人身份信息
	private static IdCardReadBean allPubAgentIDCardReadInfo;
	private static String allPubAgentIDNo;//身份证号
	private static String allPubAgentIdCardName;//身份证名称
	private static String allPubAgentAddress;// 户口地址
	private static String allPubAgentSex;// 性别
	private static String allPubAgentQfjg;// 证件签发机关
	private static String allPubAgentIdCardface;//身份证正面照路径
	private static String allPubAgentIdCardtitle;//身份证上头像照片路径
	private static String allPubAgentIdCardback;//身份证背面照路径
	private static String allPubAgentBirthday;//生日
	private static String allPubAgentPhone;//代理人电话
	private static String allPubAgentEndDate;//有效期结束
	private static String allPubAgentPhotoPath;//联网核查代理人头像路径
	private static String allPubAgentInspect;//联网核查代理人结果
	private static String allPubAgentSttn;//代理人黑灰名单 0-正常，1-涉案名单，2-可疑名单
	
	/**
	 * 人脸识别
	 */
	private static String allPubReCamera;//是否重新拍照  0-否  1-是（重新拍照）
	private static String allPubSims;//人脸识别结果相似度
	private static String allPubCameraResultMsg;//人脸识别结果信息
	
	/**
	 * 授权信息字段
	 * @return
	 */
	private static String allPubFristSupTellerNo;//第一授权柜员号
	private static String allPubFristCheckMod;//第一授权柜员授权方式  1-口令 2-指纹 3-口令+指纹
	private static String allPubFristPass;//第一授权员授权密码
	private static String allPubFristFingerLenght;//第一授权员指纹长度
	private static String allPubFristFingerVal;//第一授权员的指纹值
	private static String allPubtransAuthorNo;//交易授权变化  0-不需要授权 1-第一次需要授权 2-已经有过授权交易
	private static String allPubIsCheckAuthor;//是否核查客户信息 0-未审核 1-审核照片通过 2-审核签名通过 3-审核照片不通过 4-审核签名不通过
	private static String allPubIsSign;//是否已经签字 0-未签字 1-已经签字 2-已经签字单审核未通过	
	/**
	 * 判断电子印章
	 */
	private static String allPubDzyz; //判断电子印章是否正常返回     Y.正常返回  
	
	/**
	 * 密码
	 * @Description: 
	 * @Author: hk
	 * @date 2018年5月9日 上午9:15:33
	 */
	private static String reMakePwdNo;//重置密码标志（0-重置密码，但还未输入密码；1-第一次输入密码完成；2-第二次输入密码完成；3-重置密码成功）
	
	private ReqMCM001 reqMCM001 = new ReqMCM001();//流水上送请求报文信息
	
	
	public ReqMCM001 getReqMCM001() {
		return reqMCM001;
	}

	public static String getReMakePwdNo() {
		return reMakePwdNo;
	}

	public static void setReMakePwdNo(String reMakePwdNo) {
		BaseTransBean.reMakePwdNo = reMakePwdNo;
	}

	public static IdCardReadBean getAllPubIDCardReadInfo() {
		return allPubIDCardReadInfo;
	}

	public static void setAllPubIDCardReadInfo(IdCardReadBean allPubIDCardReadInfo) {
		BaseTransBean.allPubIDCardReadInfo = allPubIDCardReadInfo;
	}

	public static IdCardReadBean getAllPubAgentIDCardReadInfo() {
		return allPubAgentIDCardReadInfo;
	}

	public static void setAllPubAgentIDCardReadInfo(
			IdCardReadBean allPubAgentIDCardReadInfo) {
		BaseTransBean.allPubAgentIDCardReadInfo = allPubAgentIDCardReadInfo;
	}

	public static String getAllPubDzyz() {
		return allPubDzyz;
	}

	public static void setAllPubDzyz(String allPubDzyz) {
		BaseTransBean.allPubDzyz = allPubDzyz;
	}

	public static String getAllPubPhone() {
		return allPubPhone;
	}

	public static void setAllPubPhone(String allPubPhone) {
		BaseTransBean.allPubPhone = allPubPhone;
	}

	public static String getAllPubAgentPhone() {
		return allPubAgentPhone;
	}

	public static void setAllPubAgentPhone(String allPubAgentPhone) {
		BaseTransBean.allPubAgentPhone = allPubAgentPhone;
	}

	public static String getAllPubAccSttn() {
		return allPubAccSttn;
	}

	public static void setAllPubAccSttn(String allPubAccSttn) {
		BaseTransBean.allPubAccSttn = allPubAccSttn;
	}

	public static String getAllPubAccNo() {
		return allPubAccNo;
	}

	public static void setAllPubAccNo(String allPubAccNo) {
		BaseTransBean.allPubAccNo = allPubAccNo;
	}

	public static String getAllPubAccPwd() {
		return allPubAccPwd;
	}

	public static void setAllPubAccPwd(String allPubAccPwd) {
		BaseTransBean.allPubAccPwd = allPubAccPwd;
	}

	public static String getUpStepName() {
		return upStepName;
	}

	public static void setUpStepName(String upStepName) {
		BaseTransBean.upStepName = upStepName;
	}

	public static String getAllPubFristPass() {
		return allPubFristPass;
	}

	public static void setAllPubFristPass(String allPubFristPass) {
		BaseTransBean.allPubFristPass = allPubFristPass;
	}

	public static String getAllPubFristFingerLenght() {
		return allPubFristFingerLenght;
	}

	public static void setAllPubFristFingerLenght(String allPubFristFingerLenght) {
		BaseTransBean.allPubFristFingerLenght = allPubFristFingerLenght;
	}

	public static String getAllPubFristFingerVal() {
		return allPubFristFingerVal;
	}

	public static void setAllPubFristFingerVal(String allPubFristFingerVal) {
		BaseTransBean.allPubFristFingerVal = allPubFristFingerVal;
	}

	public static String getAllPubFristCheckMod() {
		return allPubFristCheckMod;
	}

	public static void setAllPubFristCheckMod(String allPubFristCheckMod) {
		BaseTransBean.allPubFristCheckMod = allPubFristCheckMod;
	}

	public static String getAllPubFristSupTellerNo() {
		return allPubFristSupTellerNo;
	}

	public static void setAllPubFristSupTellerNo(String allPubFristSupTellerNo) {
		BaseTransBean.allPubFristSupTellerNo = allPubFristSupTellerNo;
	}

	public static String getAllPubIsSign() {
		return allPubIsSign;
	}

	public static void setAllPubIsSign(String allPubIsSign) {
		BaseTransBean.allPubIsSign = allPubIsSign;
	}

	public static String getAllPubAgentPhotoPath() {
		return allPubAgentPhotoPath;
	}

	public static void setAllPubAgentPhotoPath(String allPubAgentPhotoPath) {
		BaseTransBean.allPubAgentPhotoPath = allPubAgentPhotoPath;
	}

	public static String getAllPubAgentInspect() {
		return allPubAgentInspect;
	}

	public static void setAllPubAgentInspect(String allPubAgentInspect) {
		BaseTransBean.allPubAgentInspect = allPubAgentInspect;
	}

	public static String getAllPubtransAuthorNo() {
		return allPubtransAuthorNo;
	}

	public static void setAllPubtransAuthorNo(String allPubtransAuthorNo) {
		BaseTransBean.allPubtransAuthorNo = allPubtransAuthorNo;
	}

	public static String getAllPubSims() {
		return allPubSims;
	}

	public static void setAllPubSims(String allPubSims) {
		BaseTransBean.allPubSims = allPubSims;
	}

	public static String getAllPubCameraResultMsg() {
		return allPubCameraResultMsg;
	}

	public static void setAllPubCameraResultMsg(String allPubCameraResultMsg) {
		BaseTransBean.allPubCameraResultMsg = allPubCameraResultMsg;
	}

	public static String getAllPubReCamera() {
		return allPubReCamera;
	}

	public static void setAllPubReCamera(String allPubReCamera) {
		BaseTransBean.allPubReCamera = allPubReCamera;
	}

	public static String getAllPubSttn() {
		return allPubSttn;
	}

	public static void setAllPubSttn(String allPubSttn) {
		BaseTransBean.allPubSttn = allPubSttn;
	}

	public static String getAllPubAgentSttn() {
		return allPubAgentSttn;
	}

	public static void setAllPubAgentSttn(String allPubAgentSttn) {
		BaseTransBean.allPubAgentSttn = allPubAgentSttn;
	}

	public static String getAllPubCheckIdCardNo() {
		return allPubCheckIdCardNo;
	}

	public static void setAllPubCheckIdCardNo(String allPubCheckIdCardNo) {
		BaseTransBean.allPubCheckIdCardNo = allPubCheckIdCardNo;
	}

	public static String getAllPubCheckIdCardName() {
		return allPubCheckIdCardName;
	}

	public static void setAllPubCheckIdCardName(String allPubCheckIdCardName) {
		BaseTransBean.allPubCheckIdCardName = allPubCheckIdCardName;
	}

	public static String getAllPubAgentIDNo() {
		return allPubAgentIDNo;
	}

	public static void setAllPubAgentIDNo(String allPubAgentIDNo) {
		BaseTransBean.allPubAgentIDNo = allPubAgentIDNo;
	}

	public static String getAllPubAgentIdCardName() {
		return allPubAgentIdCardName;
	}

	public static void setAllPubAgentIdCardName(String allPubAgentIdCardName) {
		BaseTransBean.allPubAgentIdCardName = allPubAgentIdCardName;
	}

	public static String getAllPubAgentAddress() {
		return allPubAgentAddress;
	}

	public static void setAllPubAgentAddress(String allPubAgentAddress) {
		BaseTransBean.allPubAgentAddress = allPubAgentAddress;
	}

	public static String getAllPubAgentSex() {
		return allPubAgentSex;
	}

	public static void setAllPubAgentSex(String allPubAgentSex) {
		BaseTransBean.allPubAgentSex = allPubAgentSex;
	}

	public static String getAllPubAgentQfjg() {
		return allPubAgentQfjg;
	}

	public static void setAllPubAgentQfjg(String allPubAgentQfjg) {
		BaseTransBean.allPubAgentQfjg = allPubAgentQfjg;
	}

	public static String getAllPubAgentIdCardface() {
		return allPubAgentIdCardface;
	}

	public static void setAllPubAgentIdCardface(String allPubAgentIdCardface) {
		BaseTransBean.allPubAgentIdCardface = allPubAgentIdCardface;
	}

	public static String getAllPubAgentIdCardtitle() {
		return allPubAgentIdCardtitle;
	}

	public static void setAllPubAgentIdCardtitle(String allPubAgentIdCardtitle) {
		BaseTransBean.allPubAgentIdCardtitle = allPubAgentIdCardtitle;
	}

	public static String getAllPubAgentIdCardback() {
		return allPubAgentIdCardback;
	}

	public static void setAllPubAgentIdCardback(String allPubAgentIdCardback) {
		BaseTransBean.allPubAgentIdCardback = allPubAgentIdCardback;
	}

	public static String getAllPubAgentBirthday() {
		return allPubAgentBirthday;
	}

	public static void setAllPubAgentBirthday(String allPubAgentBirthday) {
		BaseTransBean.allPubAgentBirthday = allPubAgentBirthday;
	}

	public static String getAllPubAgentEndDate() {
		return allPubAgentEndDate;
	}

	public static void setAllPubAgentEndDate(String allPubAgentEndDate) {
		BaseTransBean.allPubAgentEndDate = allPubAgentEndDate;
	}

	public static String getAllPubSvrDate() {
		return allPubSvrDate;
	}

	public static void setAllPubSvrDate(String allPubSvrDate) {
		BaseTransBean.allPubSvrDate = allPubSvrDate;
	}

	public static String getAllPubPhotoPath() {
		return allPubPhotoPath;
	}

	public static void setAllPubPhotoPath(String allPubPhotoPath) {
		BaseTransBean.allPubPhotoPath = allPubPhotoPath;
	}

	public static String getAllPubIsCheckAuthor() {
		return allPubIsCheckAuthor;
	}

	public static void setAllPubIsCheckAuthor(String allPubIsCheckAuthor) {
		BaseTransBean.allPubIsCheckAuthor = allPubIsCheckAuthor;
	}

	public static String getAllPubIdCardName() {
		return allPubIdCardName;
	}

	public static void setAllPubIdCardName(String allPubIdCardName) {
		BaseTransBean.allPubIdCardName = allPubIdCardName;
	}

	public static String getAllPubAddress() {
		return allPubAddress;
	}

	public static void setAllPubAddress(String allPubAddress) {
		BaseTransBean.allPubAddress = allPubAddress;
	}

	public static String getAllPubSex() {
		return allPubSex;
	}

	public static void setAllPubSex(String allPubSex) {
		BaseTransBean.allPubSex = allPubSex;
	}

	public static String getAllPubQfjg() {
		return allPubQfjg;
	}

	public static void setAllPubQfjg(String allPubQfjg) {
		BaseTransBean.allPubQfjg = allPubQfjg;
	}

	public static String getAllPubIdCardface() {
		return allPubIdCardface;
	}

	public static void setAllPubIdCardface(String allPubIdCardface) {
		BaseTransBean.allPubIdCardface = allPubIdCardface;
	}

	public static String getAllPubIdCardtitle() {
		return allPubIdCardtitle;
	}

	public static void setAllPubIdCardtitle(String allPubIdCardtitle) {
		BaseTransBean.allPubIdCardtitle = allPubIdCardtitle;
	}

	public static String getAllPubIdCardback() {
		return allPubIdCardback;
	}

	public static void setAllPubIdCardback(String allPubIdCardback) {
		BaseTransBean.allPubIdCardback = allPubIdCardback;
	}

	public static String getAllPubInspect() {
		return allPubInspect;
	}

	public static void setAllPubInspect(String allPubInspect) {
		BaseTransBean.allPubInspect = allPubInspect;
	}

	public static String getAllPubBirthday() {
		return allPubBirthday;
	}

	public static void setAllPubBirthday(String allPubBirthday) {
		BaseTransBean.allPubBirthday = allPubBirthday;
	}

	public static String getAllPubEndDate() {
		return allPubEndDate;
	}

	public static void setAllPubEndDate(String allPubEndDate) {
		BaseTransBean.allPubEndDate = allPubEndDate;
	}

	public static String getAllPubIsDeputy() {
		return allPubIsDeputy;
	}

	public static void setAllPubIsDeputy(String allPubIsDeputy) {
		BaseTransBean.allPubIsDeputy = allPubIsDeputy;
	}

	public static String getAllPubIDNo() {
		return allPubIDNo;
	}

	public static void setAllPubIDNo(String allPubIDNo) {
		BaseTransBean.allPubIDNo = allPubIDNo;
	}
	
	public String getIsEnought() {
		return isEnought;
	}

	public void setIsEnought(String isEnought) {
		this.isEnought = isEnought;
	}

	public String getCertNoAdd() {
		return certNoAdd;
	}

	public static String getNextStepName() {
		return nextStepName;
	}

	public static void setNextStepName(String nextStepName) {
		BaseTransBean.nextStepName = nextStepName;
	}

	public static Component getThisComponent() {
		return thisComponent;
	}

	public static void setThisComponent(Component thisComponent) {
		BaseTransBean.thisComponent = thisComponent;
	}
	
	public void setCertNoAdd(String certNoAdd) {
		this.certNoAdd = certNoAdd;
	}

	public String getCertNoId() {
		return certNoId;
	}

	public void setCertNoId(String certNoId) {
		this.certNoId = certNoId;
	}

	public String getCertStartNo() {
		return certStartNo;
	}

	public void setCertStartNo(String certStartNo) {
		this.certStartNo = certStartNo;
	}

	public String getCertEndNo() {
		return certEndNo;
	}

	public void setCertEndNo(String certEndNo) {
		this.certEndNo = certEndNo;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public static String getAllPubPassAccNo() {
		return allPubPassAccNo;
	}

	public static void setAllPubPassAccNo(String allPubPassAccNo) {
		BaseTransBean.allPubPassAccNo = allPubPassAccNo;
	}

	/***
	 * 设置交易代码
	 * @param transNo
	 */
	public void setTransNo(String transNo){
		this.transNo = transNo;
	}
	
	/***
	 * 获取交易代码
	 * @return
	 */
	public String getTransNo(){
		return this.transNo;
	}

}
