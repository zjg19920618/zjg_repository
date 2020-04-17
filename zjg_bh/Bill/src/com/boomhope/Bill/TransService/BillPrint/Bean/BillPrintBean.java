package com.boomhope.Bill.TransService.BillPrint.Bean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.boomhope.Bill.Framework.BaseTransBean;

public class BillPrintBean extends BaseTransBean{
	
	private String printChoice;//选择打印产品 0-存单 1-协议书
	
	//代理人信息
	private String agentIdCardNo;//代理人身份证号
	private String readIdcard;//本人身份证号
	private String agentIdCardName;//代理人身份证名称
	private String agentsex;// 代理人性别
	private String agentqfjg;// 证件签发机关
	private String agentIdCardface;
	private String agentIdCardtitle;//代理人身份证上头像照片
	private String agentIdCardback;
	private String agentinspect;//核查结果
	private String agentBirthday;//生日
	private String agentEndDate;//有效期结束
	//本人身份证信息
	private String idCardName;//身份证名称
	private String custNo;//客户号
	private String sex;// 代理人性别
	private String qfjg;// 证件签发机关
	private String idCardface;
	private String idCardtitle;//身份证上头像照片
	private String idCardback;
	private String inspect;//核查结果
	private String birthday;//生日
	private String endDate;//有效期结束
	private String fid = "";
	private String svrDate;//核心日期
	//卡信息
	private String cardName;// 卡名
	private String errmsg;  // 错误原因
	private String cameraCount;//拍照次数
	private String inputOrderPwd;//输入银行卡密码
	private String supTellerNo;//授权柜员号
	private String accNo;//卡号
	private String subAccNo;//子账号
	private String choose;//选择状态变更或者是打印存单 0-打印存单 1-状态变更
	private int page;
	private int n;//当前页
	private String billPath_fc;//存单正面
	private Set <String>select2 = new HashSet<String>();//状态变更
	private int x;//上一页
	private boolean print;//是否已经选择打印存单 true-已选择 false-未选中
	private String voice;
	private String billPath_rc;
	private String faceCompareVal;
	private boolean sec;//标记是否状态变更
	private String cardType;//证件类型
	
	//打印协议书
	private Map<String,Object> subInfo=new HashMap<String,Object>();//存放子账户列表的信息
	private Map<String,String> channelMap=new HashMap<>();//存放渠道的编号和信息
	private BillPrintSubAccInfoBean subBean=new BillPrintSubAccInfoBean();//子账户信息
	private String qry_type;//关联账号查询类型
	private String cognateNo;//如意存+和积享存关联账号
	private String getNo;//安享存和立得存收益账号
	private String channel;//渠道信息
	private String printL52Str;//窗口期利率
	private String agreementEdition;//协议书版本号
	private String failPrint;//打印协议书失败的子账号信息
	private String createdSvrNo;//开户时流水号
	private String BillNo;//存单的凭证号
	private String BillType;//凭证号种类
	private String isCheckPass;//是否验密
	private String drawCond;//支付条件，是否需要有密码(0-无密；1-有密码)
	

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getDrawCond() {
		return drawCond;
	}

	public void setDrawCond(String drawCond) {
		this.drawCond = drawCond;
	}

	public String getIsCheckPass() {
		return isCheckPass;
	}

	public void setIsCheckPass(String isCheckPass) {
		this.isCheckPass = isCheckPass;
	}

	public String getSubAccNo() {
		return subAccNo;
	}

	public void setSubAccNo(String subAccNo) {
		this.subAccNo = subAccNo;
	}

	public String getBillType() {
		return BillType;
	}

	public void setBillType(String billType) {
		BillType = billType;
	}

	public String getBillNo() {
		return BillNo;
	}

	public void setBillNo(String billNo) {
		BillNo = billNo;
	}

	public String getCreatedSvrNo() {
		return createdSvrNo;
	}

	public void setCreatedSvrNo(String createdSvrNo) {
		this.createdSvrNo = createdSvrNo;
	}

	public String getFailPrint() {
		return failPrint;
	}

	public void setFailPrint(String failPrint) {
		this.failPrint = failPrint;
	}

	public String getAgreementEdition() {
		return agreementEdition;
	}

	public void setAgreementEdition(String agreementEdition) {
		this.agreementEdition = agreementEdition;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getPrintL52Str() {
		return printL52Str;
	}

	public void setPrintL52Str(String printL52Str) {
		this.printL52Str = printL52Str;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCognateNo() {
		return cognateNo;
	}

	public void setCognateNo(String cognateNo) {
		this.cognateNo = cognateNo;
	}

	public String getGetNo() {
		return getNo;
	}

	public void setGetNo(String getNo) {
		this.getNo = getNo;
	}

	public String getQry_type() {
		return qry_type;
	}

	public void setQry_type(String qry_type) {
		this.qry_type = qry_type;
	}

	public BillPrintSubAccInfoBean getSubBean() {
		return subBean;
	}

	public void setSubBean(BillPrintSubAccInfoBean subBean) {
		this.subBean = subBean;
	}

	public Map<String, String> getChannelMap() {
		channelMap.put("00", "柜台");
		channelMap.put("01", "本行ATM");
		channelMap.put("08", "机具");
		channelMap.put("50", "随身银行");
		channelMap.put("90", "网银");
		channelMap.put("0001", "AB系统");
		channelMap.put("0002", "帐务集中");
		channelMap.put("0005", "本行ATM");
		channelMap.put("0014", "网银");
		channelMap.put("0028", "手机银行系统");
		channelMap.put("0035", "机具");
		channelMap.put("0041", "随身银行");
		channelMap.put("0003", "信贷系统");
		channelMap.put("0007", "POS系统");
		channelMap.put("0008", "柜面通本代他");
		channelMap.put("0009", "银联");
		channelMap.put("0010", "TIPS系统");
		channelMap.put("0011", "非税系统");
		channelMap.put("0012", "支付密码系统");
		channelMap.put("0015", "综合理财");
		channelMap.put("0016", "NC");
		channelMap.put("0017", "银校通系统");
		channelMap.put("0018", "钱柜");
		channelMap.put("0019", "事后监督系统");
		channelMap.put("0020", "款箱系统");
		channelMap.put("0021", "核心系统");
		channelMap.put("0022", "卡系统");
		channelMap.put("0024", "黑名单系统");
		channelMap.put("0025", "互联网支付平台");
		channelMap.put("0026", "积分系统");
		channelMap.put("0027", "动态密码锁系统");
		channelMap.put("0029", "人力资源系统");
		channelMap.put("0031", "对账系统");
		channelMap.put("0032", "财政电子化支付系统");
		channelMap.put("0033", "回单机系统");
		channelMap.put("0034", "发卡机系统");
		channelMap.put("0036", "数据仓库");
		channelMap.put("0038", "股金系统");
		channelMap.put("0039", "基金系统");
		channelMap.put("0040", "电子账户系统");
		channelMap.put("0042", "移动发卡机系统");
		channelMap.put("0043", "金税系统");
		channelMap.put("0044", "电信诈骗系统");
		channelMap.put("0046", "快柜系统");
		channelMap.put("9999", "前置内部渠道");
		channelMap.put("VS01", "帐务集中定时查询");;
		return channelMap;
	}
	
	public void setChannelMap(Map<String, String> channelMap) {
		this.channelMap = channelMap;
	}

	public Map<String, Object> getSubInfo() {
		return subInfo;
	}
	public void setSubInfo(Map<String, Object> subInfo) {
		this.subInfo = subInfo;
	}
	public String getPrintChoice() {
		return printChoice;
	}
	public void setPrintChoice(String printChoice) {
		this.printChoice = printChoice;
	}
	public boolean isSec() {
		return sec;
	}
	public void setSec(boolean sec) {
		this.sec = sec;
	}
	public String getFaceCompareVal() {
		return faceCompareVal;
	}
	public void setFaceCompareVal(String faceCompareVal) {
		this.faceCompareVal = faceCompareVal;
	}
	public String getBillPath_rc() {
		return billPath_rc;
	}
	public void setBillPath_rc(String billPath_rc) {
		this.billPath_rc = billPath_rc;
	}
	public String getVoice() {
		return voice;
	}
	public void setVoice(String voice) {
		this.voice = voice;
	}
	public boolean isPrint() {
		return print;
	}
	public void setPrint(boolean print) {
		this.print = print;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public Set<String> getSelect2() {
		return select2;
	}
	public void setSelect2(Set<String> select2) {
		this.select2 = select2;
	}
	public String getBillPath_fc() {
		return billPath_fc;
	}
	public void setBillPath_fc(String billPath_fc) {
		this.billPath_fc = billPath_fc;
	}
	private Set<String>select = new HashSet<String>();//打印整存整取
	private Set select4=new HashSet<String>();//存单打印流水号
	
	public Set getSelect4() {
		return select4;
	}
	public void setSelect4(Set select4) {
		this.select4 = select4;
	}
	public Set<String> getSelect() {
		return select;
	}
	public void setSelect(Set<String> select) {
		this.select = select;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getChoose() {
		return choose;
	}
	public void setChoose(String choose) {
		this.choose = choose;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getSupTellerNo() {
		return supTellerNo;
	}
	public void setSupTellerNo(String supTellerNo) {
		this.supTellerNo = supTellerNo;
	}
	public String getInputOrderPwd() {
		return inputOrderPwd;
	}
	public void setInputOrderPwd(String inputOrderPwd) {
		this.inputOrderPwd = inputOrderPwd;
	}
	public String getSvrDate() {
		return svrDate;
	}
	public void setSvrDate(String svrDate) {
		this.svrDate = svrDate;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getIdCardName() {
		return idCardName;
	}
	public void setIdCardName(String idCardName) {
		this.idCardName = idCardName;
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
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	public String getAgentIdCardback() {
		return agentIdCardback;
	}
	public void setAgentIdCardback(String agentIdCardback) {
		this.agentIdCardback = agentIdCardback;
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
	public String getCameraCount() {
		return cameraCount;
	}
	public void setCameraCount(String cameraCount) {
		this.cameraCount = cameraCount;
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
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getAgentinspect() {
		return agentinspect;
	}
	public void setAgentinspect(String agentinspect) {
		this.agentinspect = agentinspect;
	}
	private Map<String,String> importMap = new HashMap<String,String>();// 重要字段
	public Map<String, String> getImportMap() {
		return importMap;
	}
	public void setImportMap(Map<String, String> importMap) {
		this.importMap = importMap;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getAgentIdCardNo() {
		return agentIdCardNo;
	}
	public void setAgentIdCardNo(String agentIdCardNo) {
		this.agentIdCardNo = agentIdCardNo;
	}
	public String getReadIdcard() {
		return readIdcard;
	}
	public void setReadIdcard(String readIdcard) {
		this.readIdcard = readIdcard;
	}
	public String getAgentIdCardName() {
		return agentIdCardName;
	}
	public void setAgentIdCardName(String agentIdCardName) {
		this.agentIdCardName = agentIdCardName;
	}
}
