package com.boomhope.Bill.TransService.LostReport.Bean;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;
/**
 * 按账号查全面信息【02884】-前置08223
 * @author Administrator
 *
 */
@Number(id = 1)
public class AccNoCheck08223 {
	/**
	 * 客户号（18）||客户名称（60）||基本证件种类（5）||证件号码（20）||法人代表（10）||组织机构代码（18）||国税代码（20）||
	 * 地税代码（20）||贷款证号（15）||地址（60）（30）||通讯信息（20）
	 * 账号（32）||协议账号（32）||产品代码（5）||开户日（10）||起息日（10）||
	 * 开户机构（9）||结清日期（10）|| 存期（通知期限）（3）||账户种类（3）(X-X)||科目号（12）||开户利率（10）||
	 * 转存次数（定期）（13）（注：0_否，非0_是）||存折金额（折户）（16）||明细笔数（折户）（5）||未登项（折户）（5）||
	 * 存折位置（折户）（）||凭证号码（30）||结存额（16.2）||利息积数（16）||留存额度（16）||冻结金额（16）||止付金额（16）||
	 * 圈存金额（16）||支付条件（1）||收付限制（活期）（1）| |账户状态（16）||开户柜员（5）||销户柜员（5）||到期日(10)||
	 * 贷款账号（32）||产品起存金额(16)||关联帐号(32) ||绑定账户（32）
	 */
	@FieldSort(NO =0)
	private String cust_no;//客户号
	@FieldSort(NO =1)
	private String cust_name;//客户名称（60）
	@FieldSort(NO = 2)
	private String id_type;//基本证件种类（1）
	@FieldSort(NO = 3)
	private String id_no;//证件号码（20）
	@FieldSort(NO = 4)
	private String farendaibao;//法人代表（10）
	@FieldSort(NO = 5)
	private String zuzijigou;//组织机构代码（18）
	@FieldSort(NO =6)
	private String guosuidaima;//国税代码（20）
	@FieldSort(NO = 7)
	private String disuidaima;//地税代码（20）
	@FieldSort(NO = 8)
	private String daikuanzhenghao;//贷款证号（15）
	@FieldSort(NO = 9)
	private String address;//地址（60）
	@FieldSort(NO = 10)
	private String phone;//通讯信息（20）
	@FieldSort(NO = 11)
	private String acc_no;// 账号（32）
	@FieldSort(NO = 12)
	private String xy_acc_no;//协议账号（32）
	@FieldSort(NO = 13)
	private String procode;//产品代码（5）
	@FieldSort(NO = 14)
	private String opendate;//开户日（10）
	@FieldSort(NO = 15)
	private String startdate;//起息日（10）
	@FieldSort(NO = 16)
	private String openInst;//开户机构（9）
	@FieldSort(NO = 17)
	private String endAmtDate;//结清日期（10）
	@FieldSort(NO = 18)
	private String depterm;//存期（通知期限）（3）
	@FieldSort(NO = 19)
	private String acc_type;//账户种类（3）(X-X)
	@FieldSort(NO = 20)
	private String kemuhao;//科目号（12）
	@FieldSort(NO = 21)
	private String open_rate;//开户利率（10）
	@FieldSort(NO = 22)
	private String zhuancun;//转存次数（定期）（13）
	@FieldSort(NO = 23)
	private String totalAmt;//存折金额（折户）（16）
	@FieldSort(NO = 24)
	private String mingxinum;//明细笔数（折户）（5）
	@FieldSort(NO = 25)
	private String weidengding;//未登项（折户）（5）
	@FieldSort(NO = 26)
	private String cunze;//存折位置（折户）（）
	@FieldSort(NO = 27)
	private String cert_no;// 凭证号码（30）
	@FieldSort(NO = 28)
	private String endAmt;// 结存额（16.2）
	@FieldSort(NO = 29)
	private String ratenum;// 利息积数（16）
	@FieldSort(NO = 30)
	private String liucunAmt; //留存额度（16）
	@FieldSort(NO = 31)
	private String dongjieAmt; //冻结金额（16）
	@FieldSort(NO = 32)
	private String zhifuAmt;// 止付金额（16）
	@FieldSort(NO = 33)
	private String quancunAmt;// 圈存金额（16）
	@FieldSort(NO = 34)
	private String pay_drawCond;// 支付条件（1）
	@FieldSort(NO = 35)
	private String shoufuxianzhi; //收付限制（活期）（1）
	@FieldSort(NO = 36)
	private String acc_stata; //账户状态（16）
	@FieldSort(NO = 37)
	private String open_teller; //开户柜员（5）
	@FieldSort(NO = 38)
	private String cancel_teller; //销户柜员（5）
	@FieldSort(NO = 39)
	private String endDate; //到期日(10)
	@FieldSort(NO = 40)
	private String daikuanAcc; //贷款账号（32）
	@FieldSort(NO = 41)
	private String procodeAmt; //产品起存金额(16)
	@FieldSort(NO = 42)
	private String guanlianAcc; //关联帐号(32)
	public String getCust_no() {
		return cust_no;
	}
	public void setCust_no(String cust_no) {
		this.cust_no = cust_no;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
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
	public String getFarendaibao() {
		return farendaibao;
	}
	public void setFarendaibao(String farendaibao) {
		this.farendaibao = farendaibao;
	}
	public String getZuzijigou() {
		return zuzijigou;
	}
	public void setZuzijigou(String zuzijigou) {
		this.zuzijigou = zuzijigou;
	}
	public String getGuosuidaima() {
		return guosuidaima;
	}
	public void setGuosuidaima(String guosuidaima) {
		this.guosuidaima = guosuidaima;
	}
	public String getDisuidaima() {
		return disuidaima;
	}
	public void setDisuidaima(String disuidaima) {
		this.disuidaima = disuidaima;
	}
	public String getDaikuanzhenghao() {
		return daikuanzhenghao;
	}
	public void setDaikuanzhenghao(String daikuanzhenghao) {
		this.daikuanzhenghao = daikuanzhenghao;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}
	public String getXy_acc_no() {
		return xy_acc_no;
	}
	public void setXy_acc_no(String xy_acc_no) {
		this.xy_acc_no = xy_acc_no;
	}
	public String getProcode() {
		return procode;
	}
	public void setProcode(String procode) {
		this.procode = procode;
	}
	public String getOpendate() {
		return opendate;
	}
	public void setOpendate(String opendate) {
		this.opendate = opendate;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getOpenInst() {
		return openInst;
	}
	public void setOpenInst(String openInst) {
		this.openInst = openInst;
	}
	public String getEndAmtDate() {
		return endAmtDate;
	}
	public void setEndAmtDate(String endAmtDate) {
		this.endAmtDate = endAmtDate;
	}
	public String getDepterm() {
		return depterm;
	}
	public void setDepterm(String depterm) {
		this.depterm = depterm;
	}
	public String getAcc_type() {
		return acc_type;
	}
	public void setAcc_type(String acc_type) {
		this.acc_type = acc_type;
	}
	public String getKemuhao() {
		return kemuhao;
	}
	public void setKemuhao(String kemuhao) {
		this.kemuhao = kemuhao;
	}
	public String getOpen_rate() {
		return open_rate;
	}
	public void setOpen_rate(String open_rate) {
		this.open_rate = open_rate;
	}
	public String getZhuancun() {
		return zhuancun;
	}
	public void setZhuancun(String zhuancun) {
		this.zhuancun = zhuancun;
	}
	public String getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}
	public String getMingxinum() {
		return mingxinum;
	}
	public void setMingxinum(String mingxinum) {
		this.mingxinum = mingxinum;
	}
	public String getWeidengding() {
		return weidengding;
	}
	public void setWeidengding(String weidengding) {
		this.weidengding = weidengding;
	}
	public String getCunze() {
		return cunze;
	}
	public void setCunze(String cunze) {
		this.cunze = cunze;
	}
	public String getCert_no() {
		return cert_no;
	}
	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}
	public String getEndAmt() {
		return endAmt;
	}
	public void setEndAmt(String endAmt) {
		this.endAmt = endAmt;
	}
	public String getRatenum() {
		return ratenum;
	}
	public void setRatenum(String ratenum) {
		this.ratenum = ratenum;
	}
	public String getLiucunAmt() {
		return liucunAmt;
	}
	public void setLiucunAmt(String liucunAmt) {
		this.liucunAmt = liucunAmt;
	}
	public String getDongjieAmt() {
		return dongjieAmt;
	}
	public void setDongjieAmt(String dongjieAmt) {
		this.dongjieAmt = dongjieAmt;
	}
	public String getZhifuAmt() {
		return zhifuAmt;
	}
	public void setZhifuAmt(String zhifuAmt) {
		this.zhifuAmt = zhifuAmt;
	}
	public String getQuancunAmt() {
		return quancunAmt;
	}
	public void setQuancunAmt(String quancunAmt) {
		this.quancunAmt = quancunAmt;
	}
	public String getPay_drawCond() {
		return pay_drawCond;
	}
	public void setPay_drawCond(String pay_drawCond) {
		this.pay_drawCond = pay_drawCond;
	}
	public String getShoufuxianzhi() {
		return shoufuxianzhi;
	}
	public void setShoufuxianzhi(String shoufuxianzhi) {
		this.shoufuxianzhi = shoufuxianzhi;
	}
	public String getAcc_stata() {
		return acc_stata;
	}
	public void setAcc_stata(String acc_stata) {
		this.acc_stata = acc_stata;
	}
	public String getOpen_teller() {
		return open_teller;
	}
	public void setOpen_teller(String open_teller) {
		this.open_teller = open_teller;
	}
	public String getCancel_teller() {
		return cancel_teller;
	}
	public void setCancel_teller(String cancel_teller) {
		this.cancel_teller = cancel_teller;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getDaikuanAcc() {
		return daikuanAcc;
	}
	public void setDaikuanAcc(String daikuanAcc) {
		this.daikuanAcc = daikuanAcc;
	}
	public String getProcodeAmt() {
		return procodeAmt;
	}
	public void setProcodeAmt(String procodeAmt) {
		this.procodeAmt = procodeAmt;
	}
	public String getGuanlianAcc() {
		return guanlianAcc;
	}
	public void setGuanlianAcc(String guanlianAcc) {
		this.guanlianAcc = guanlianAcc;
	}
	
}
