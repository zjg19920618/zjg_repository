package com.boomhope.Bill.TransService.LostReport.Bean;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;
/*
 * 按客户查询账户信息【20103】-前置07527
 * 
*/@Number(id = 1)
public class BillChangeInfoBean {
//	公共信息内容：
//	客户号（18）||客户名称（60）||证件种类（1）||证件号码（20）||地址（公共信息）（60）
	
	/*账号（32）||协议账户（32）||产品代码（5）||开户日（10）||起息日（10）||开户机构（9）||结清日期（10）|| 存期（通知期限）（3）
	 * ||开户利率（10）||是否转存（定期）（1）||存折金额（折户）（16）||明细笔数（折户）（5）||未登项（折户）（5）||存折位置（折户）（4）
	 * ||凭证号码（30）||结存额（16.2）||冻结金额（16）||支付条件（1）
	 * ||收付限制（活期）（1）||账户状态（17）||开户柜员（5）
	 * ||销户柜员（5）||到期日(10)||可用余额||币种||账户种类||最近存取日期*/
	
	@FieldSort(NO = 0)
	private String cust_no;//客户号(18)
	@FieldSort(NO =1)
	private String cust_name;//客户名称（60）
	@FieldSort(NO = 2)
	private String id_type;//证件种类（1）
	@FieldSort(NO = 3)
	private String id_no;//证件号码（20）
	@FieldSort(NO = 4)
	private String addrres;//地址（60）
	@FieldSort(NO = 5)
	private String acct_no;//账号（32）
	@FieldSort(NO =6)
	private String prl_acct;//协议账户（32）
	@FieldSort(NO = 7)
	private String product_code;//产品代码（5）
	@FieldSort(NO = 8)
	private String open_date;//开户日（10)
	@FieldSort(NO = 9)
	private String start_date;//起息日10
	@FieldSort(NO = 10)
	private String open_inst;//开户机构（9）
	@FieldSort(NO = 11)
	private String clear_date;//结清日期（10）
	@FieldSort(NO = 12)
	private String dep_term;//存期（通知期限）（3）
	@FieldSort(NO = 13)
	private String open_rate;//开户利率(10)
	@FieldSort(NO = 14)
	private String zc_flag;//是否转存（定期）（1）
	@FieldSort(NO = 15)
	private String un_use_amt;//存折金额（折户）（16）
	@FieldSort(NO = 16)
	private String item_num;//明细笔数（折户）（5）
	@FieldSort(NO = 17)
	private String no_due;//未登项（折户）
	@FieldSort(NO = 18)
	private String book_add;//存折位置（折户）（4）
	@FieldSort(NO = 19)
	private String cert_no;//凭证号码（30）
	@FieldSort(NO = 20)
	private String end_amt;//结存额（16.2）
	@FieldSort(NO = 21)
	private String cold_amt;//冻结金额（16）
	@FieldSort(NO = 22)
	private String pay_cond;//支付条件（1）
	@FieldSort(NO = 23)
	private String limit;//收付限制（活期）（1）
	@FieldSort(NO = 24)
	private String acct_stat;//账户状态（1）
	@FieldSort(NO = 25)
	private String open_teller;//开户柜员（5）
	@FieldSort(NO = 26)
	private String acct_teller;//销户柜员（5）
	@FieldSort(NO = 27)
	private String due_date;// 到期日(10)
	@FieldSort(NO = 28)
	private String play_amt;// 可用余额
	@FieldSort(NO = 29)
	private String amt_type;// 币种
	@FieldSort(NO = 30)
	private String acct_thing; //账户分类
	@FieldSort(NO = 31)
	private String openacc_data; //最近存取日期
	
	
	private String selectFlag;//选中标记
	private boolean flag = false; 
	
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
	public String getAddrres() {
		return addrres;
	}
	public void setAddrres(String addrres) {
		this.addrres = addrres;
	}
	public String getPlay_amt() {
		return play_amt;
	}
	public void setPlay_amt(String play_amt) {
		this.play_amt = play_amt;
	}
	public String getAmt_type() {
		return amt_type;
	}
	public void setAmt_type(String amt_type) {
		this.amt_type = amt_type;
	}

	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getSelectFlag() {
		return selectFlag;
	}
	public void setSelectFlag(String selectFlag) {
		this.selectFlag = selectFlag;
	}
	public String getAcct_no() {
		return acct_no;
	}
	public void setAcct_no(String acct_no) {
		this.acct_no = acct_no;
	}
	public String getPrl_acct() {
		return prl_acct;
	}
	public void setPrl_acct(String prl_acct) {
		this.prl_acct = prl_acct;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getOpen_inst() {
		return open_inst;
	}
	public void setOpen_inst(String open_inst) {
		this.open_inst = open_inst;
	}
	public String getOpen_date() {
		return open_date;
	}
	public void setOpen_date(String open_date) {
		this.open_date = open_date;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getClear_date() {
		return clear_date;
	}
	public void setClear_date(String clear_date) {
		this.clear_date = clear_date;
	}
	public String getDep_term() {
		return dep_term;
	}
	public void setDep_term(String dep_term) {
		this.dep_term = dep_term;
	}
	public String getOpen_rate() {
		return open_rate;
	}
	public void setOpen_rate(String open_rate) {
		this.open_rate = open_rate;
	}
	public String getZc_flag() {
		return zc_flag;
	}
	public void setZc_flag(String zc_flag) {
		this.zc_flag = zc_flag;
	}
	public String getUn_use_amt() {
		return un_use_amt;
	}
	public void setUn_use_amt(String un_use_amt) {
		this.un_use_amt = un_use_amt;
	}
	public String getItem_num() {
		return item_num;
	}
	public void setItem_num(String item_num) {
		this.item_num = item_num;
	}
	public String getNo_due() {
		return no_due;
	}
	public void setNo_due(String no_due) {
		this.no_due = no_due;
	}
	public String getBook_add() {
		return book_add;
	}
	public void setBook_add(String book_add) {
		this.book_add = book_add;
	}
	public String getCert_no() {
		return cert_no;
	}
	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}
	public String getEnd_amt() {
		return end_amt;
	}
	public void setEnd_amt(String end_amt) {
		this.end_amt = end_amt;
	}
	public String getCold_amt() {
		return cold_amt;
	}
	public void setCold_amt(String cold_amt) {
		this.cold_amt = cold_amt;
	}
	public String getPay_cond() {
		return pay_cond;
	}
	public void setPay_cond(String pay_cond) {
		this.pay_cond = pay_cond;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getAcct_stat() {
		return acct_stat;
	}
	public void setAcct_stat(String acct_stat) {
		this.acct_stat = acct_stat;
	}
	public String getOpen_teller() {
		return open_teller;
	}
	public void setOpen_teller(String open_teller) {
		this.open_teller = open_teller;
	}
	public String getAcct_teller() {
		return acct_teller;
	}
	public void setAcct_teller(String acct_teller) {
		this.acct_teller = acct_teller;
	}
	public String getDue_date() {
		return due_date;
	}
	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}
	public String getAcct_thing() {
		return acct_thing;
	}
	public void setAcct_thing(String acct_thing) {
		this.acct_thing = acct_thing;
	}
	
}
