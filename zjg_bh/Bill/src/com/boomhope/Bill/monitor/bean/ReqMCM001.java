package com.boomhope.Bill.monitor.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.monitor.sendInterface.InterfaceName;

/**
 * 4.1交易流水上送【MCM001】
 * @author Administrator
 *
 */
public class ReqMCM001 extends MonitorReqHeadBean{

	private String trjnno;//任务号
	private String transcode;//交易代码
	private String transdate;//交易开始日期
	private String transtime;//交易开始时间
	private String transresult;//交易结果
	private String intetrjnno;//报文流水号
	private String intename;//前置交易名称
	private String intecode;//前置交易码
	private String intedate;//交易日期
	private String intetime;//交易时间
	private String inteinstno;//机构号
	private String intetellerno;//柜员号
	private String interesult;//调用结果
	private String authemp1;//授权柜员1
	private String authmethod1;//授权方式1
	private String authemp2;//授权柜员2
	private String authmethod2;//授权方式2
	private String intereturndate;//应答日期
	private String intereturntime;//应答时间
	private String intereturncode;//前置返回码
	private String intereturnmsg;//前置返回信息
	private String centertrjnno;//核心流水号
	private String customername;//客户名称
	private String account;//账号
	private String lendirection;//借贷方向
	private String transamt;//交易金额
	private String turnflag;//现转标志
	private String tonouns;//对方名称
	private String toaccount;//对方账号
	private String topenbankno;//对方开户行号
	private String topenbankname;//对方开户行名
	private String rvouchertype;//回收凭证类型
	private String rvoucherno;//回收凭证号码
	private String usevouchertype;//使用凭证类型
	private String usevoucherno;//使用凭证号码
	private String interest;//利息
	private String grantpea;//派发唐豆
	private String recoverypea;//收回唐豆
	private String reportlossid;//挂失申请书号
	private String transreserve0;//备用
	private String transreserve1;//备用
	private String transreserve2;//备用
	private String transreserve3;//备用
	private String transreserve4;//备用
	
	String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	
	
	//设计交易前信息
	public ReqMCM001(){
		transdate=dateTime.substring(0,8);//交易开始日期
		transtime=dateTime.substring(8);//交易开始时间
		trjnno=GlobalParameter.machineNo+dateTime;//任务号
		
		inteinstno=GlobalParameter.branchNo;//机构号
		intetellerno=GlobalParameter.tellerNo;//柜员号
		
		version=Property.channel_monitor_req_version;
		type="MCM001";
		deviceno = GlobalParameter.machineNo;
		devicetype = "11";
		interesult = "1";
		transresult ="1";
		
		//默认为未选择业务
		transcode="10000";
	}
	
	//接口数据准备
	public void setReqBefor(String interfaceNo){
		if(interfaceNo==null || "".equals(interfaceNo)){
			intedate = "";
			intetime = "";
			intecode = "";
			intename = "";
			intereturndate ="";
			intereturntime ="";
			intereturncode ="";
		}else{
			String startTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			intedate = startTime.substring(0,8);
			intetime = startTime.substring(8);
			intecode = interfaceNo;
			intename = InterfaceName.getInterfaceName(interfaceNo);
		}
	}
	
	//接口结束数据
	public void setReqAfter(String resCode,String resMsg){
		//开户结束结果流水信息上送准备
		String overTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		intereturndate = overTime.substring(0, 8);
		intereturntime = overTime.substring(8);
		intereturncode = resCode;
		intereturnmsg = (resMsg==null||"".equals(resMsg))?"成功":resMsg;
	}
	
	
	
	public String getReportlossid() {
		return reportlossid;
	}

	public void setReportlossid(String reportlossid) {
		this.reportlossid = reportlossid;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getGrantpea() {
		return grantpea;
	}

	public void setGrantpea(String grantpea) {
		this.grantpea = grantpea;
	}

	public String getRecoverypea() {
		return recoverypea;
	}

	public void setRecoverypea(String recoverypea) {
		this.recoverypea = recoverypea;
	}

	public String getTrjnno() {
		return trjnno;
	}
	public void setTrjnno(String trjnno) {
		this.trjnno = trjnno;
	}
	public String getTranscode() {
		return transcode;
	}
	public void setTranscode(String transcode) {
		this.transcode = transcode;
	}
	public String getTransdate() {
		return transdate;
	}
	public String getTranstime() {
		return transtime;
	}
	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}
	public void setTranstime(String transtime) {
		this.transtime = transtime;
	}
	public String getTransresult() {
		return transresult;
	}
	public void setTransresult(String transresult) {
		this.transresult = transresult;
	}
	public String getIntetrjnno() {
		return intetrjnno;
	}
	public void setIntetrjnno(String intetrjnno) {
		this.intetrjnno = intetrjnno;
	}
	public String getIntename() {
		return intename;
	}
	public void setIntename(String intename) {
		this.intename = intename;
	}
	public String getIntecode() {
		return intecode;
	}
	public void setIntecode(String intecode) {
		this.intecode = intecode;
	}
	public String getIntedate() {
		return intedate;
	}
	public void setIntedate(String intedate) {
		this.intedate = intedate;
	}
	public String getIntetime() {
		return intetime;
	}
	public void setIntetime(String intetime) {
		this.intetime = intetime;
	}
	public String getInteinstno() {
		return inteinstno;
	}
	public void setInteinstno(String inteinstno) {
		this.inteinstno = inteinstno;
	}
	public String getIntetellerno() {
		return intetellerno;
	}
	public void setIntetellerno(String intetellerno) {
		this.intetellerno = intetellerno;
	}
	public String getInteresult() {
		return interesult;
	}
	public void setInteresult(String interesult) {
		this.interesult = interesult;
	}
	public String getAuthemp1() {
		return authemp1;
	}
	public void setAuthemp1(String authemp1) {
		this.authemp1 = authemp1;
	}
	public String getAuthmethod1() {
		return authmethod1;
	}
	public void setAuthmethod1(String authmethod1) {
		this.authmethod1 = authmethod1;
	}
	public String getAuthemp2() {
		return authemp2;
	}
	public void setAuthemp2(String authemp2) {
		this.authemp2 = authemp2;
	}
	public String getAuthmethod2() {
		return authmethod2;
	}
	public void setAuthmethod2(String authmethod2) {
		this.authmethod2 = authmethod2;
	}
	public String getIntereturndate() {
		return intereturndate;
	}
	public void setIntereturndate(String intereturndate) {
		this.intereturndate = intereturndate;
	}
	public String getIntereturntime() {
		return intereturntime;
	}
	public void setIntereturntime(String intereturntime) {
		this.intereturntime = intereturntime;
	}
	public String getIntereturncode() {
		return intereturncode;
	}
	public void setIntereturncode(String intereturncode) {
		this.intereturncode = intereturncode;
	}
	public String getIntereturnmsg() {
		return intereturnmsg;
	}
	public void setIntereturnmsg(String intereturnmsg) {
		this.intereturnmsg = intereturnmsg;
	}
	public String getCentertrjnno() {
		return centertrjnno;
	}
	public void setCentertrjnno(String centertrjnno) {
		this.centertrjnno = centertrjnno;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getLendirection() {
		return lendirection;
	}
	public void setLendirection(String lendirection) {
		this.lendirection = lendirection;
	}
	public String getTransamt() {
		return transamt;
	}
	public void setTransamt(String transamt) {
		this.transamt = transamt;
	}
	public String getTurnflag() {
		return turnflag;
	}
	public void setTurnflag(String turnflag) {
		this.turnflag = turnflag;
	}
	public String getTonouns() {
		return tonouns;
	}
	public void setTonouns(String tonouns) {
		this.tonouns = tonouns;
	}
	public String getToaccount() {
		return toaccount;
	}
	public void setToaccount(String toaccount) {
		this.toaccount = toaccount;
	}
	public String getTopenbankno() {
		return topenbankno;
	}
	public void setTopenbankno(String topenbankno) {
		this.topenbankno = topenbankno;
	}
	public String getTopenbankname() {
		return topenbankname;
	}
	public void setTopenbankname(String topenbankname) {
		this.topenbankname = topenbankname;
	}
	public String getRvouchertype() {
		return rvouchertype;
	}
	public void setRvouchertype(String rvouchertype) {
		this.rvouchertype = rvouchertype;
	}
	public String getRvoucherno() {
		return rvoucherno;
	}
	public void setRvoucherno(String rvoucherno) {
		this.rvoucherno = rvoucherno;
	}
	public String getUsevouchertype() {
		return usevouchertype;
	}
	public void setUsevouchertype(String usevouchertype) {
		this.usevouchertype = usevouchertype;
	}
	public String getUsevoucherno() {
		return usevoucherno;
	}
	public void setUsevoucherno(String usevoucherno) {
		this.usevoucherno = usevoucherno;
	}
	public String getTransreserve0() {
		return transreserve0;
	}
	public void setTransreserve0(String transreserve0) {
		this.transreserve0 = transreserve0;
	}
	public String getTransreserve1() {
		return transreserve1;
	}
	public void setTransreserve1(String transreserve1) {
		this.transreserve1 = transreserve1;
	}
	public String getTransreserve2() {
		return transreserve2;
	}
	public void setTransreserve2(String transreserve2) {
		this.transreserve2 = transreserve2;
	}
	public String getTransreserve3() {
		return transreserve3;
	}
	public void setTransreserve3(String transreserve3) {
		this.transreserve3 = transreserve3;
	}
	public String getTransreserve4() {
		return transreserve4;
	}
	public void setTransreserve4(String transreserve4) {
		this.transreserve4 = transreserve4;
	}
	
	
	
	
	
}
