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
public class ProductRateInfo1 {

	static Logger logger = Logger.getLogger(ProductRateInfo1.class);
//	查询类型为1时
//	查询类型为1时：产品代码||产品名称||最小到期利率||最大到期利率||最小存期||最小起存额\n
	
	@FieldSort(NO = 0)
	private String productCode;			//产品代码
	@FieldSort(NO = 1)
	private String productCodeName;		//产品名称
	@FieldSort(NO = 2)
	private String InterestRateRange;		//最小到期利率-最大到期利率
	@FieldSort(NO = 3)
	private String savingCountMin;			//存期
	@FieldSort(NO = 4)
	private String startMoney;			//起存金额
	
	private String savingCountMinStr;	//存期  40月、50月、60月、5年
	
	private String startMoneyStr;		//金额
	
	private String interestRateRangeStr;	//最小到期利率-最大到期利率
	
	public String getInterestRateRangeStr(){
		String str=InterestRateRange;
		try
		{
			if(str.indexOf("-")!=-1){
				String ss[]=str.split("-");
				float min=Float.parseFloat(ss[0]);
				float max=Float.parseFloat(ss[1]);
				DecimalFormat fnum  =  new  DecimalFormat("##0.0000");  
				str=fnum.format(min)+"%-"+fnum.format(max)+"%";
			}
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		return str;
	}
	
	public String getSavingCountMinStr(){
		String str=savingCountMin;
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
	
	
	public String getProductCode()
	{
		return productCode;
	}
	public ProductRateInfo1 setProductCode(String productCode)
	{
		this.productCode = productCode;
		return this;
	}
	public String getProductCodeName()
	{
		return productCodeName;
	}
	public ProductRateInfo1 setProductCodeName(String productCodeName)
	{
		this.productCodeName = productCodeName;
		return this;
	}
	
	public String getSavingCountMin()
	{
		return savingCountMin;
	}
	public ProductRateInfo1 setSavingCountMin(String savingCountMin)
	{
		this.savingCountMin = savingCountMin;
		return this;
	}
	public String getStartMoney()
	{
		return startMoney;
	}
	public ProductRateInfo1 setStartMoney(String startMoney)
	{
		this.startMoney = startMoney;
		return this;
	}
	public String getInterestRateRange()
	{
		return InterestRateRange;
	}
	public ProductRateInfo1 setInterestRateRange(String interestRateRange)
	{
		InterestRateRange = interestRateRange;
		return this;
	}

	
	
}
