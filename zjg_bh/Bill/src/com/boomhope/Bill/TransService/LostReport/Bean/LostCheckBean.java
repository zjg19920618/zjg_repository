package com.boomhope.Bill.TransService.LostReport.Bean;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;
/**
 *挂失申请书打印查询
 * @author hp
 *
 */
@Number(id = 1)
public class LostCheckBean {
	//柜员号||挂失申请书编号||账号||客户名称||挂失种类||证件类型||证件号码
	//||申请人地址||申请人电话||代理人姓名||代理人证件类型||代理人证件号码||代理人地址||代理人电话||凭证号码||摘要||开户日期||打印状态||挂失渠道||挂失流水号||挂失机构
	//||解挂标志||日期1||日期2
	//挂失种类：08179（核心）返回文件类型为111，一个三位数字。  [1] 0-账户挂失 1-凭证挂失 2-挂失申请书挂失
    //														 [2] 0-临时挂失 1-正式挂失
    //														 [3] (账户挂失时此标志位有效)0-密码挂失 1-印鉴挂失 2-单折挂失 3-证件挂失 6-双挂失*/
	//挂失种类：08180（卡系统）返回文件类型为1；一个一位数字。 0-卡临时挂失 1-密码临时挂失 2-卡正式挂失 3-密码正式挂失 4-双挂失
	//解挂标志：000000（08179）返回文件类型 000000 一个六位数字。查看当前挂失状态只查看第三位
	//		空/0-单挂、1-双挂未解、2-双挂单解密、3-双挂双解
	//解挂标志：000000（08180）返回文件类型 000000 一个六位数字。查看当前挂失状态只查看第四位
	//		空/0-单挂、1-双挂未解、2-双挂单解密、3-双挂双解
	//		如果为单挂是，日期1为1900/01/01则表示单挂未解，否则为单挂已结
	@FieldSort(NO = 0)
	private String supTellerNo;//柜员号
	@FieldSort(NO =1)
	private String lostApplNo;//挂失申请书编号
	@FieldSort(NO = 2)
	private String acc_no;//账号
	@FieldSort(NO = 3)
	private String cust_name;//客户名称
	@FieldSort(NO = 4)
	private String lostType;//挂失种类
	@FieldSort(NO = 5)
	private String id_type;//证件类型
	@FieldSort(NO =6)
	private String id_no;//证件号码
	@FieldSort(NO = 7)
	private String id_Address;//申请人地址
	@FieldSort(NO = 8)
	private String id_Phone;//申请人电话
	@FieldSort(NO = 9)
	private String id_AgentName;//代理人姓名
	@FieldSort(NO = 10)
	private String id_AgentType;//代理人证件类型
	@FieldSort(NO = 11)
	private String id_AgentNo;//代理人证件号码
	@FieldSort(NO = 12)
	private String id_AgentAddress;//代理人地址
	@FieldSort(NO = 13)
	private String id_AgentPhone;//代理人电话
	@FieldSort(NO = 14)
	private String cert_no;//凭证号码
	@FieldSort(NO = 15)
	private String zhaiyao;//摘要
	@FieldSort(NO = 16)
	private String openDate;//开户日期
	@FieldSort(NO = 17)
	private String printState;//打印状态
	@FieldSort(NO = 18)
	private String channel;//挂失渠道
	@FieldSort(NO = 19)
	private String jrnlNo;//流水号
	@FieldSort(NO = 20)
	private String lostInstNo;//挂失机构
	@FieldSort(NO = 21)
	private String lostOrSolveState;//当前状态
	@FieldSort(NO = 22)
	private String solveDate1;//解挂日期1
	@FieldSort(NO = 23)
	private String solveDate2;//解挂日期2
	
	private String endAmt;//结存额
	
	
	public String getSolveDate1() {
		return solveDate1;
	}
	public void setSolveDate1(String solveDate1) {
		this.solveDate1 = solveDate1;
	}
	public String getSolveDate2() {
		return solveDate2;
	}
	public void setSolveDate2(String solveDate2) {
		this.solveDate2 = solveDate2;
	}
	public String getLostOrSolveState() {
		return lostOrSolveState;
	}
	public void setLostOrSolveState(String lostOrSolveState) {
		this.lostOrSolveState = lostOrSolveState;
	}
	public String getEndAmt() {
		return endAmt;
	}
	public void setEndAmt(String endAmt) {
		this.endAmt = endAmt;
	}
	public String getSupTellerNo() {
		return supTellerNo;
	}
	public void setSupTellerNo(String supTellerNo) {
		this.supTellerNo = supTellerNo;
	}
	public String getLostApplNo() {
		return lostApplNo;
	}
	public void setLostApplNo(String lostApplNo) {
		this.lostApplNo = lostApplNo;
	}
	public String getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getLostType() {
		return lostType;
	}
	public void setLostType(String lostType) {
		this.lostType = lostType;
	}
	public String getId_type() {
		return id_type;
	}
	public void setId_type(String id_type) {
		this.id_type = id_type;
	}
	public String getId_no() {
		return id_no;
	}
	public void setId_no(String id_no) {
		this.id_no = id_no;
	}
	public String getId_Address() {
		return id_Address;
	}
	public void setId_Address(String id_Address) {
		this.id_Address = id_Address;
	}
	public String getId_Phone() {
		return id_Phone;
	}
	public void setId_Phone(String id_Phone) {
		this.id_Phone = id_Phone;
	}
	public String getId_AgentName() {
		return id_AgentName;
	}
	public void setId_AgentName(String id_AgentName) {
		this.id_AgentName = id_AgentName;
	}
	public String getId_AgentType() {
		return id_AgentType;
	}
	public void setId_AgentType(String id_AgentType) {
		this.id_AgentType = id_AgentType;
	}
	public String getId_AgentNo() {
		return id_AgentNo;
	}
	public void setId_AgentNo(String id_AgentNo) {
		this.id_AgentNo = id_AgentNo;
	}
	public String getId_AgentAddress() {
		return id_AgentAddress;
	}
	public void setId_AgentAddress(String id_AgentAddress) {
		this.id_AgentAddress = id_AgentAddress;
	}
	public String getId_AgentPhone() {
		return id_AgentPhone;
	}
	public void setId_AgentPhone(String id_AgentPhone) {
		this.id_AgentPhone = id_AgentPhone;
	}
	public String getCert_no() {
		return cert_no;
	}
	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}
	public String getZhaiyao() {
		return zhaiyao;
	}
	public void setZhaiyao(String zhaiyao) {
		this.zhaiyao = zhaiyao;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getPrintState() {
		return printState;
	}
	public void setPrintState(String printState) {
		this.printState = printState;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getJrnlNo() {
		return jrnlNo;
	}
	public void setJrnlNo(String jrnlNo) {
		this.jrnlNo = jrnlNo;
	}
	public String getLostInstNo() {
		return lostInstNo;
	}
	public void setLostInstNo(String lostInstNo) {
		this.lostInstNo = lostInstNo;
	}
	
}
