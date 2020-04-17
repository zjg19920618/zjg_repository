package com.boomhope.Bill.TransService.AccOpen.Bean;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;


/**
 *  4.11	如意存明细查询【55020】--前置03512  返回
 * @author Administrator
 *
 */
@Number(id = 1)
public class  SearchRYCDetail
{
//	账号||子账号||产品代码||产品名称||开户机构||开户日||起息日||到期日||存期||结存额

	@FieldSort(NO = 0)
	private String acctNo;		//账号
	@FieldSort(NO = 1)
	private String subAcctNo;		//子账号
	@FieldSort(NO = 2)
	private String productCode;			//产品代码
	@FieldSort(NO = 3)
	private String productCodeName;	//产品名称
	@FieldSort(NO = 4)
	private String openInst;	//开户机构
	@FieldSort(NO = 5)
	private String openDate;	//开户日
	@FieldSort(NO = 6)
	private String startDate;	//起息日
	@FieldSort(NO = 7)
	private String endDate;	//到期日
	@FieldSort(NO = 8)
	private String depTerm;	//存期(D,M,Y)
	@FieldSort(NO = 9)
	private String balance;		//结存额
	private String depTermStr;//存期(天,月,年)
	
	public SearchRYCDetail setDepTermStr(String depTermStr)
	{
		this.depTermStr = depTermStr;
		return this;
	}
	
	public String getDepTermStr(){
		String str=depTerm;
		try
		{
			str=str.trim().toUpperCase();
			if(str.indexOf("M")!=-1){
				str=str.replace("M", "个月");
			}else if(str.indexOf("D")!=-1){
				str=str.replace("D", "天");
			}else if(str.indexOf("Y")!=-1){
//				str=str.replace("Y", "年");
				Integer n=Integer.parseInt(str.replaceAll("\\D",""));
				str=n+"年";
			}else if(str.indexOf("366")!=-1||str.indexOf("367")!=-1){
				str="1年+1天";
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return str;
	}
	public String getAcctNo()
	{
		return acctNo;
	}
	public SearchRYCDetail setAcctNo(String acctNo)
	{
		this.acctNo = acctNo;
		return this;
	}
	public String getSubAcctNo()
	{
		return subAcctNo;
	}
	public SearchRYCDetail setSubAcctNo(String subAcctNo)
	{
		this.subAcctNo = subAcctNo;
		return this;
	}
	public String getProductCode()
	{
		return productCode;
	}
	public SearchRYCDetail setProductCode(String productCode)
	{
		this.productCode = productCode;
		return this;
	}
	public String getProductCodeName()
	{
		return productCodeName;
	}
	public SearchRYCDetail setProductCodeName(String productCodeName)
	{
		this.productCodeName = productCodeName;
		return this;
	}
	public String getOpenInst()
	{
		return openInst;
	}
	public SearchRYCDetail setOpenInst(String openInst)
	{
		this.openInst = openInst;
		return this;
	}
	public String getOpenDate()
	{
		return openDate;
	}
	public SearchRYCDetail setOpenDate(String openDate)
	{
		this.openDate = openDate;
		return this;
	}
	public String getStartDate()
	{
		return startDate;
	}
	public SearchRYCDetail setStartDate(String startDate)
	{
		this.startDate = startDate;
		return this;
	}
	public String getEndDate()
	{
		return endDate;
	}
	public SearchRYCDetail setEndDate(String endDate)
	{
		this.endDate = endDate;
		return this;
	}
	public String getDepTerm()
	{
		return depTerm;
	}
	public SearchRYCDetail setDepTerm(String depTerm)
	{
		this.depTerm = depTerm;
		return this;
	}
	public String getBalance()
	{
		return balance;
	}
	public SearchRYCDetail setBalance(String balance)
	{
		this.balance = balance;
		return this;
	}
	
	
	

}
