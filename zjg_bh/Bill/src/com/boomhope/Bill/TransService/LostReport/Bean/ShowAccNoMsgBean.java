package com.boomhope.Bill.TransService.LostReport.Bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.boomhope.Bill.Util.DateTool;
import com.jacob.com.Dispatch;


public class ShowAccNoMsgBean {
	
	private String cardOrAccOrAcc1;//银行卡/存单/存折标识(0-银行卡、1-存单个人账户、1_1-存单卡下子账户、1_2-存单电子子账户、2-存折)
	private String cardNo;//卡账号
	private String AccNo;//存单账号
	private String AccNo1;//存折账号
	private String openDate;//开户日期
	private String startDate;//起息日期
	private String endDate;//到期日
	private String depTerm;//存期
	private String depTermFY;//	存期翻译
	private String accType;//账户类型
	private String certNo;//凭证号码
	private String custName;//客户名称
	private String endAmt;////存单/存折结存额，卡及卡子帐号总结存额
	private String cardEndAmt;//卡结存额
	private String totalAmt;//存折余额（挂失销户用）
	private String proName;//产品名称
	private String proCode;//产品代码
	private String accState;//账户状态
	private String cardState;//卡状态
	private String cardState1;//卡状态1
	private String cardState2;//卡状态2
	private String accStateFY;//账户状态翻译
	private String printState;//打印状态(1-待打印，2-以打印)
	private String drawCond;//支付条件(0-无密码，1-凭密码，2-凭证件，3-凭印鉴)
	private String drawCondFY;//支付条件翻译
	private String openRate;//开户利率
	private String openChannel;//开户渠道
	private String preDate;//预约日期
	private String IsAgreedDep;//约定转存标志(0 未开通 1 开通)
	private String custNo;//客户号
	
	private boolean select = false;
	
	
	public String getOpenChannel() {
		return openChannel;
	}
	public void setOpenChannel(String openChannel) {
		this.openChannel = openChannel;
	}
	public String getCardOrAccOrAcc1() {
		return cardOrAccOrAcc1;
	}
	public void setCardOrAccOrAcc1(String cardOrAccOrAcc1) {
		this.cardOrAccOrAcc1 = cardOrAccOrAcc1;
	}
	public String getCardNo() {
		if(cardNo !=null && !"".equals(cardNo.trim())){
			cardNo = cardNo.trim();
		}else{
			cardNo = "";
		}
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getAccNo() {
		if(AccNo !=null && !"".equals(AccNo.trim())){
			AccNo = AccNo.trim();
		}else{
			AccNo = "";
		}
		return AccNo;
	}
	public void setAccNo(String accNo) {
		AccNo = accNo;
	}
	public String getAccNo1() {
		if(AccNo1 !=null && !"".equals(AccNo1.trim())){
			AccNo1 = AccNo1.trim();
		}else{
			AccNo1 = "";
		}
		return AccNo1;
	}
	public void setAccNo1(String accNo1) {
		AccNo1 = accNo1;
	}
	public String getOpenDate() {
		if(openDate !=null && !"".equals(openDate.trim())){
			if(openDate.contains("/")){
				openDate = openDate.trim().replaceAll("/", "");
			}else{
				openDate = openDate.trim();
			}
		}else{
			openDate = "";
		}
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getDepTerm() {
		if(depTerm !=null && !"".equals(depTerm.trim())){
			depTerm = depTerm.trim();
		}else{
			depTerm = "";
		}
		return depTerm;
	}
	public void setDepTerm(String depTerm) {
		this.depTerm = depTerm;
	}
	public String getAccType() {
		if(accType !=null && !"".equals(accType.trim())){
			accType = accType.trim();
		}else{
			accType = "";
		}
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	public String getCertNo() {
		if(certNo !=null && !"".equals(certNo.trim())){
			certNo = certNo.trim();
		}else{
			certNo = "";
		}
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getCustName() {
		if(custName !=null && !"".equals(custName.trim())){
			custName = custName.trim();
		}else{
			custName = "";
		}
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getEndAmt() {
		if(endAmt !=null && !"".equals(endAmt.trim())){
			endAmt = endAmt.trim();
		}else{
			endAmt = "";
		}
		return endAmt;
	}
	public void setEndAmt(String endAmt) {
		this.endAmt = endAmt;
	}
	public String getProName() {
		if(proName !=null && !"".equals(proName.trim())){
			proName = proName.trim();
		}else{
			proName = "";
		}
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
	public boolean isSelect() {
		return select;
	}
	public void setSelect(boolean select) {
		this.select = select;
	}
	public String getCardState() {
		if(cardState !=null && !"".equals(cardState.trim())){
			cardState = cardState.trim();
		}else{
			cardState = "";
		}
		return cardState;
	}
	public void setCardState(String cardState) {
		this.cardState = cardState;
	}
	public String getPrintState() {
		if(printState !=null && !"".equals(printState.trim())){
			printState = printState.trim();
		}else{
			printState = "";
		}
		return printState;
	}
	public void setPrintState(String printState) {
		this.printState = printState;
	}
	public String getDrawCond() {
		if(drawCond !=null && !"".equals(drawCond.trim())){
			drawCond = drawCond.trim();
		}else{
			drawCond = "";
		}
		return drawCond;
	}
	public void setDrawCond(String drawCond) {
		this.drawCond = drawCond;
	}
	public String getAccStateFY() {
		
		String[] strs = new String[31]; 
		StringBuffer str = new StringBuffer("");
		
		if(getCardState()!= null && getAccState()!= null ){
			
			if("0".equals(getCardOrAccOrAcc1())){//卡状态
				
				if("N".equals(getCardState().trim())){
					strs[0] = "未激活";
				}
				if("1".equals(getCardState().trim())){
					strs[0] = "未制卡";
				}
				if("2".equals(getCardState().trim())){
					strs[0] = "未发卡";
				}
				if("3".equals(getCardState().trim())){
					strs[0] = "未领卡";
				}
				if("4".equals(getCardState().trim())){
					strs[0] = "已没收";
				}
				if("*".equals(getCardState().trim())){
					strs[0] = "已作废";
				}
				if("B".equals(getCardState().trim())){
					strs[0] = "IC卡收回";
				}
				if(getCardState1()!=null && !"".equals(getCardState1())){
					strs[1] = getCardState1();
				}
				if(getCardState2()!=null && !"".equals(getCardState2())){
					strs[2] = getCardState2();
				}
				if("1".equals(String.valueOf(getAccState().charAt(1))) || "1".equals(String.valueOf(getAccState().charAt(2)))){
					strs[3] = "冻结";
				}
				if("1".equals(String.valueOf(getAccState().charAt(3)))){
					strs[4] = "只收不付";
				}
				if("2".equals(String.valueOf(getAccState().charAt(3)))){
					strs[5] = "电信诈骗全额止付";
				}
				if("3".equals(String.valueOf(getAccState().charAt(3)))){
					strs[6] = "电信诈骗全额冻结";
				}
				if("1".equals(String.valueOf(getAccState().charAt(4)))){
					strs[7] = "临时挂失";
				}
				if("2".equals(String.valueOf(getAccState().charAt(4)))){
					strs[8] = "正式挂失";
				}
				if("1".equals(String.valueOf(getAccState().charAt(11))) || "2".equals(String.valueOf(getAccState().charAt(11)))){
					strs[9] = "密码挂失";
				}
				if("1".equals(String.valueOf(getAccState().charAt(21))) || "2".equals(String.valueOf(getAccState().charAt(21)))){
					strs[10] = "止付";
				}
					
			}else {//个人存单/存折,卡下子账户/电子子账户状态
				
				//账户状态
				if("*".equals(String.valueOf(getAccState().charAt(0)))){
					strs[0] = "作废";
				}
				if("1".equals(String.valueOf(getAccState().charAt(0)))){
					strs[1] = "停用";
				}
				if("3".equals(String.valueOf(getAccState().charAt(0)))){
					strs[2] = "渠道限制";
				}
				if("1".equals(String.valueOf(getAccState().charAt(2))) || "2".equals(String.valueOf(getAccState().charAt(2)))){
					strs[3] = "冻结";
				}
				if("3".equals(String.valueOf(getAccState().charAt(2)))){
					strs[4] = "只收不付";
				}
				if("1".equals(String.valueOf(getAccState().charAt(3)))){
					strs[5] = "止付";
				}
				if("1".equals(String.valueOf(getAccState().charAt(4)))){
					strs[6] = "没收";
				}
				if("1".equals(String.valueOf(getAccState().charAt(7)))){
					strs[7] = "临时挂失";
				}
				if("2".equals(String.valueOf(getAccState().charAt(7)))){
					strs[8] = "正式挂失";
				}
			}
		}
		int center = 0;
		Map<Integer,String> map = new HashMap<Integer, String>();
 		for (int i = 0; i < strs.length; i++) {
			if( strs[i]!=null){
				map.put(center, strs[i]);
				center++;
			}
		}
		
		if(center==0){
			if("0".equals(getCardOrAccOrAcc1())){
				accStateFY = "正常";
			}else{
				if("1".equals(getCardOrAccOrAcc1()) || "2".equals(getCardOrAccOrAcc1())){
					accStateFY = "正常";
				}else{
					accStateFY = "正常";
				}
			}
		}else{
			
			String[] strs1 = new String[center]; 
			for (int i = 0; i < strs1.length; i++) {
				if(i==0){
					strs1[i] = map.get(i);
				}else{
					if(i==3){
						strs1[i] = "等";
					}else{
						strs1[i] = "+"+map.get(i);
					}
				}
				str.append(strs1[i]);
				if(i==3){
					break;
				}
			}
			accStateFY = str.toString();
		}
		return accStateFY;
	}
	public void setAccStateFY(String accStateFY) {
		this.accStateFY = accStateFY;
	}
	public String getDepTermFY() {
		
		if(getDepTerm()!=null && !"".equals(getDepTerm().trim())){
			
			Integer n=Integer.parseInt(getDepTerm().replaceAll("\\D",""));
			if(n==0){
				
				depTermFY = "无";
				
			}else if(getProCode()!=null){
				
				if(getProCode().startsWith("RJ")){
					
					try {
						String startdate=getStartDate().replace("/","");//起息日期
						String enddate=getEndDate().replace("/","");//到期日期
						SimpleDateFormat fommter = new SimpleDateFormat("yyyyMMdd");
						Date a1 = fommter.parse(startdate);
						Date b1 = fommter.parse(enddate);
					    int	date = DateTool.differentsDays(a1, b1);
					    depTermFY=String.valueOf(date)+"天";//如意存+ 的存期
					    
					} catch (ParseException e) {
						e.printStackTrace();
						depTermFY = "";
					}
					
				}else{
					
					String[] acc = new String[]{"","",""};
					if(getDepTerm().indexOf("D")!=-1){
						acc = new String[] { "D", n+"", n+"天" };
					}else if(getDepTerm().indexOf("M")!=-1){
						acc = new String[] { "M", n+"", n+"个月"};
					}else if(getDepTerm().indexOf("Y")!=-1){
						acc = new String[] { "Y", n+"", n+"年" };
					}else{
						acc = new String[] { "D", n+"", n+"天" };
					}
					depTermFY = acc[2];
				}
			}else{
				depTermFY = "无";
			}
			
		}else{
			depTermFY = "无";
		}
		return depTermFY;
	}
	public void setDepTermFY(String depTermFY) {
		this.depTermFY = depTermFY;
	}
	public String getProCode() {
		if(proCode !=null && !"".equals(proCode.trim())){
			proCode = proCode.trim();
		}else{
			proCode = "";
		}
		return proCode;
	}
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	public String getEndDate() {
		if(endDate !=null && !"".equals(endDate.trim())){
			endDate = endDate.trim();
		}else{
			endDate = "";
		}
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCardEndAmt() {
		return cardEndAmt;
	}
	public void setCardEndAmt(String cardEndAmt) {
		this.cardEndAmt = cardEndAmt;
	}
	public String getOpenRate() {
		return openRate;
	}
	public void setOpenRate(String openRate) {
		this.openRate = openRate;
	}
	public String getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}
	public String getDrawCondFY() {
		
		if(getDrawCond()!=null){
			
			if("0".equals(getDrawCond().trim())){
				drawCondFY = "无密码";
			}else if("1".equals(getDrawCond().trim())){
				drawCondFY = "凭密码";
			}else if("2".equals(getDrawCond().trim())){
				drawCondFY = "凭证件";
			}else if("3".equals(getDrawCond().trim())){
				drawCondFY = "凭印鉴";
			}
			
		}else{
			drawCondFY = "";
		}
		return drawCondFY;
	}
	public void setDrawCondFY(String drawCondFY) {
		this.drawCondFY = drawCondFY;
	}
	public String getCardState1() {
		return cardState1;
	}
	public void setCardState1(String cardState1) {
		this.cardState1 = cardState1;
	}
	public String getCardState2() {
		return cardState2;
	}
	public void setCardState2(String cardState2) {
		this.cardState2 = cardState2;
	}
	public String getPreDate() {
		return preDate;
	}
	public void setPreDate(String preDate) {
		this.preDate = preDate;
	}
	public String getIsAgreedDep() {
		return IsAgreedDep;
	}
	public void setIsAgreedDep(String isAgreedDep) {
		IsAgreedDep = isAgreedDep;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
}
