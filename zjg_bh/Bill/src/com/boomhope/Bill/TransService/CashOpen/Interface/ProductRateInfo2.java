package com.boomhope.Bill.TransService.CashOpen.Interface;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;
/***
 * 产品利率信息
 *
 */
@Number(id = 1)
public class ProductRateInfo2 {
	static Logger logger = Logger.getLogger(ProductRateInfo2.class);

	public ProductRateInfo2()
	{
		super();
	}
	
	public ProductRateInfo2(String productCode, String productCodeName, String subProductCode, String subProductName, String startMoney, String endMoney, String savingCount, String voucherType, String minimumBalance, String interestRate,String isPrint)
	{
		super();
		this.productCode = productCode;
		this.productCodeName = productCodeName;
		this.subProductCode = subProductCode;
		this.subProductName = subProductName;
		this.startMoney = startMoney;
		this.endMoney = endMoney;
		this.savingCount = savingCount;
		this.voucherType = voucherType;
		this.minimumBalance = minimumBalance;
		this.interestRate = interestRate;
		this.isPrint = isPrint;
	}
//	查询类型为2、3时
//	产品代码||产品名称||子产品代码||子产品名称||起存金额||止存金额||存期||凭证种类||最低结存||到期利率\n
	@FieldSort(NO = 0)
	private String productCode;			//产品代码
	@FieldSort(NO = 1)
	private String productCodeName;		//产品名称
	@FieldSort(NO = 2)
	private String subProductCode;		//子产品代码
	@FieldSort(NO = 3)
	private String subProductName;		//子产品名称
	@FieldSort(NO = 4)
	private String startMoney;			//起存金额
	@FieldSort(NO = 5)
	private String endMoney;			//止存金额
	@FieldSort(NO = 6)
	private String savingCount;			//存期
	@FieldSort(NO = 7)
	private String voucherType;			//凭证种类
	@FieldSort(NO = 8)
	private String minimumBalance ;		//最低结存
	@FieldSort(NO = 9)
	private String interestRate;		//到期利率
	@FieldSort(NO = 10)
	private String isPrint;             //是否打印
	
	private String savingCountStr;	//存期  40月、50月、60月、5年
	
	private String startMoneyStr;		//金额
	private String endMoneyStr;	
	
	private String interestRateStr;	//最小到期利率-最大到期利率
	
	public String getinterestRateStr(){
		String str=interestRate;
		try
		{
			float rate=Float.parseFloat(str);
			DecimalFormat fnum  =  new  DecimalFormat("##0.0000");  
			str=fnum.format(rate)+"%";
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return str;
	}
	
	public String getSavingCountStr(){
		String str=savingCount;
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
			logger.error(e);
		}
		return str;
	}
	
	
	public String getStartMoneyStr(){
		String str=startMoney;
		try
		{
			if(str.indexOf(".")!=-1)
				str=str.substring(0,str.indexOf("."));
			int money=Integer.parseInt(str);
			money=money/10000;
			str=money+"万";
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		return str;
	}
	
	public String getEndMoneyStr(){
		String str=endMoney;
		try
		{
			if(str.indexOf(".")!=-1)
				str=str.substring(0,str.indexOf("."));
			int money=Integer.parseInt(str);
			money=money/10000;
			str=money+"万";
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		return str;
	}
	
	
	
	public String getProductCode()
	{
		return productCode;
	}
	public ProductRateInfo2 setProductCode(String productCode)
	{
		this.productCode = productCode;
		return this;
	}
	public String getProductCodeName()
	{
		return productCodeName;
	}
	public ProductRateInfo2 setProductCodeName(String productCodeName)
	{
		this.productCodeName = productCodeName;
		return this;
	}
	public String getSubProductCode()
	{
		return subProductCode;
	}
	public ProductRateInfo2 setSubProductCode(String subProductCode)
	{
		this.subProductCode = subProductCode;
		return this;
	}
	public String getSubProductName()
	{
		return subProductName;
	}
	public ProductRateInfo2 setSubProductName(String subProductName)
	{
		this.subProductName = subProductName;
		return this;
	}
	public String getStartMoney()
	{
		return startMoney;
	}
	public ProductRateInfo2 setStartMoney(String startMoney)
	{
		this.startMoney = startMoney;
		return this;
	}
	
	public void setStartMoney2(String startMoney){
		this.startMoney = startMoney;
	}
	
	public String getEndMoney()
	{
		return endMoney;
	}
	public ProductRateInfo2 setEndMoney(String endMoney)
	{
		this.endMoney = endMoney;
		return this;
	}
	public String getSavingCount()
	{
		return savingCount;
	}
	public ProductRateInfo2 setSavingCount(String savingCount)
	{
		this.savingCount = savingCount;
		return this;
	}
	public String getVoucherType()
	{
		return voucherType;
	}
	public ProductRateInfo2 setVoucherType(String voucherType)
	{
		this.voucherType = voucherType;
		return this;
	}
	public String getMinimumBalance()
	{
		return minimumBalance;
	}
	public ProductRateInfo2 setMinimumBalance(String minimumBalance)
	{
		this.minimumBalance = minimumBalance;
		return this;
	}
	public String getInterestRate()
	{
		return interestRate;
	}
	public ProductRateInfo2 setInterestRate(String interestRate)
	{
		this.interestRate = interestRate;
		return this;
	}

	public String getIsPrint() {
		return isPrint;
	}

	public ProductRateInfo2 setIsPrint(String isPrint) {
		this.isPrint = isPrint;
		return this;
	}

	
	

}
