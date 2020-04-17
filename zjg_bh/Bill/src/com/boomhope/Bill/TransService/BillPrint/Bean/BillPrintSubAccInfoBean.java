package com.boomhope.Bill.TransService.BillPrint.Bean;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;

/***
 *子账户列表实体类
 */
@Number(id = 1)
public class BillPrintSubAccInfoBean {
	
	//卡号||子账号||实际账号||开户机构||开户日||到期日||开户金额|产品代码|||产品名称
	//||存期||开户利率||结存额||有效标志（0_正常 *_作废 Q_销户）||账号状态||渠道||凭证号
	//||打印状态（0/其它 不需要打印 1待打印 2已打印）
	
	@FieldSort(NO = 0)
	private String accNo;//卡号
	@FieldSort(NO = 1)
	private String subAccNo;//子账号
	@FieldSort(NO = 2)
	private String realAccno;//实际账户
	@FieldSort(NO = 3)
	private String openInstNo;//开户机构
	@FieldSort(NO = 4)
	private String openDate;//开户日
	@FieldSort(NO = 5)
	private String endIntDate;//到期日期
	@FieldSort(NO =6)
	private String openATM;//开户金额
	@FieldSort(NO =7)
	private String productCode;//产品代码
	@FieldSort(NO =8)
	private String productName;//产品名称
	@FieldSort(NO = 9)
	private String depTerm;//存期
	@FieldSort(NO = 10)
	private String openRate;//开户利率
	@FieldSort(NO = 11)
	private String balance;//结存额
	@FieldSort(NO = 12)
	private String mark;//有效标志
	@FieldSort(NO = 13)
	private String accNoState;//账户状态
	@FieldSort(NO = 14)
	private String channel;//渠道
	@FieldSort(NO = 15)
	private String bill;//凭证号
	@FieldSort(NO = 16)
	private String printState;//打印状态
	
	private boolean isChoice;//是否已经选中 true-已选中 false-未选中
	private boolean isHavePic=true;//是否保存了电子签名和电子印章 true-已经保存 false-未保存
	private String agreementEdition;//版本号
	private String createSvrNo;//开户时流水号
	private String qry_type;//关联账号查询类型
	private String cognateNo;//如意存+和积享存关联账号
	private String koukuanNo;//扣款账号
	private String getNo;//安享存和立得存收益账号
	private String channelInfo;//渠道信息
	private String PrintL52Str;//窗口期利率
	private String oldAccNo;//老卡号
	private String newAccNo;//存单换开后新账号
	
	
	public String getPrintL52Str() {
		return PrintL52Str;
	}
	public void setPrintL52Str(String printL52Str) {
		PrintL52Str = printL52Str;
	}
	public boolean isHavePic() {
		return isHavePic;
	}
	public void setHavePic(boolean isHavePic) {
		this.isHavePic = isHavePic;
	}
	public String getAgreementEdition() {
		return agreementEdition;
	}
	public void setAgreementEdition(String agreementEdition) {
		this.agreementEdition = agreementEdition;
	}
	public String getCreateSvrNo() {
		return createSvrNo;
	}
	public void setCreateSvrNo(String createSvrNo) {
		this.createSvrNo = createSvrNo;
	}
	public String getQry_type() {
		return qry_type;
	}
	public void setQry_type(String qry_type) {
		this.qry_type = qry_type;
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
	public String getChannelInfo() {
		return channelInfo;
	}
	public void setChannelInfo(String channelInfo) {
		this.channelInfo = channelInfo;
	}
	public boolean isChoice() {
		return isChoice;
	}
	public void setChoice(boolean isChoice) {
		this.isChoice = isChoice;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getSubAccNo() {
		return subAccNo;
	}
	public void setSubAccNo(String subAccNo) {
		this.subAccNo = subAccNo;
	}
	public String getRealAccno() {
		return realAccno;
	}
	public void setRealAccno(String realAccno) {
		this.realAccno = realAccno;
	}
	public String getOpenInstNo() {
		return openInstNo;
	}
	public void setOpenInstNo(String openInstNo) {
		this.openInstNo = openInstNo;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getEndIntDate() {
		return endIntDate;
	}
	public void setEndIntDate(String endIntDate) {
		this.endIntDate = endIntDate;
	}
	public String getOpenATM() {
		return openATM;
	}
	public void setOpenATM(String openATM) {
		this.openATM = openATM;
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
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getAccNoState() {
		return accNoState;
	}
	public void setAccNoState(String accNoState) {
		this.accNoState = accNoState;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getBill() {
		return bill;
	}
	public void setBill(String bill) {
		this.bill = bill;
	}
	public String getPrintState() {
		return printState;
	}
	public void setPrintState(String printState) {
		this.printState = printState;
	}
	public String getKoukuanNo() {
		return koukuanNo;
	}
	public void setKoukuanNo(String koukuanNo) {
		this.koukuanNo = koukuanNo;
	}
	public String getOldAccNo() {
		return oldAccNo;
	}
	public void setOldAccNo(String oldAccNo) {
		this.oldAccNo = oldAccNo;
	}
	public String getNewAccNo() {
		return newAccNo;
	}
	public void setNewAccNo(String newAccNo) {
		this.newAccNo = newAccNo;
	}
	
}
