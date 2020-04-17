package com.boomhope.Bill.TransService.LostReport.Bean;

import java.text.DecimalFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Util.DateTool;
import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;


/**
 * 4.9.1	产品利率信息查询--02864 返回
 * @author Administrator
 *
 */
@Number(id = 1)
public class SearchProducRateInfo02864
{
	
	static Logger logger = Logger.getLogger(SearchProducRateInfo02864.class);
//	产品代码||金额||存期||档次||利率||支取月||窗口开始日期||窗口结束日期||锁定期开始日期||锁定期结束日期
	@FieldSort(NO = 0)
	private String productCode;			//产品代码
	@FieldSort(NO = 1)
	private String money;			//金额
	@FieldSort(NO = 2)
	private String level;			//档次
	@FieldSort(NO = 3)
	private String savingCount;		//存期
	@FieldSort(NO = 4)
	private String rate;			//利率
	@FieldSort(NO = 5)
	private String drawMonth;		//支取月
	@FieldSort(NO = 6)
	private String benDateStr;		//窗口开始日期
	@FieldSort(NO = 7)
	private String endDateStr;		//窗口结束日期
	@FieldSort(NO = 8)
	private String lockStarDate;  //锁定期开始日期
	@FieldSort(NO = 9)
	private String lockEndDate;  //锁定期结束日期
	
	private String inteDate;	//存款日
	
	private String savingCountStr;	//支持取日期
	private String rateStr;
	
	private String l51Str;
	
	public StringBuffer getL51Str(float floatRet){
		StringBuffer savingCountStr=getSavingCountStr();
		StringBuffer rateStr=getRateStr(floatRet);
		if(savingCountStr.toString().trim().length()!=0 && rateStr.toString().trim().length()!=0){
			return new StringBuffer(savingCountStr).append(",利率").append(rateStr);
		}
		return new StringBuffer();
	}
	
	/**
	 * 只有窗口期
	 * @return
	 */
	public StringBuffer getL51Str2(float floatRet){
		StringBuffer savingCountStr=getSavingCountStr();
		StringBuffer rateStr=getRateStr(floatRet);
		if(savingCountStr.toString().trim().length()!=0 && rateStr.toString().trim().length()!=0){
			return new StringBuffer(savingCountStr);
		}
		return new StringBuffer();
	}
	
	public StringBuffer getSavingCountStr(){
		StringBuffer str=new StringBuffer();
		try
		{
			String saveing=savingCount.trim();
			if(saveing.indexOf("-")!=-1){
				String[] savingCounts=saveing.split("-");
				if(savingCounts.length==2){
					String ben=savingCounts[0],end=savingCounts[1];
					ben=dateStr(ben);
					end=dateStr(end);
					str.append(ben).append("-").append(end);
				}
			}
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		return str;
	}
	
	public StringBuffer getRateStr(float floatRet){
		StringBuffer str=new StringBuffer();
		try
		{
			float rt=Float.parseFloat(rate);
			DecimalFormat fnum  =  new  DecimalFormat("##0.0000");  
			str.append(fnum.format(rt+floatRet)).append("%");
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		return str;
	}
	
	private String dateStr(String ben){
		String str=ben;
		try
		{
			str=str.trim().toUpperCase();
			Calendar now =Calendar.getInstance();
			now.setTime(DateTool.stringToUtilDate(inteDate, "yyyyMMdd"));
			Integer n=Integer.parseInt(str.replaceAll("\\D",""));
			if(str.indexOf("D")!=-1){
				now.add(Calendar.DATE, n);	//一天
			}else if(str.indexOf("M")!=-1){
				now.add(Calendar.MONTH,n);	//一天
			}else if(str.indexOf("Y")!=-1){
				now.add(Calendar.YEAR, n);	//一年
			}
			return DateTool.DateTimeToString(now.getTime(), "yyyy/MM/dd");
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
	public SearchProducRateInfo02864 setProductCode(String productCode)
	{
		this.productCode = productCode;
		return this;
	}
	public String getMoney()
	{
		return money;
	}
	public SearchProducRateInfo02864 setMoney(String money)
	{
		this.money = money;
		return this;
	}
	public String getLevel()
	{
		return level;
	}
	public SearchProducRateInfo02864 setLevel(String level)
	{
		this.level = level;
		return this;
	}
	public String getSavingCount()
	{
		return savingCount;
	}
	public SearchProducRateInfo02864 setSavingCount(String savingCount)
	{
		this.savingCount = savingCount;
		return this;
	}
	public String getRate()
	{
		return rate;
	}
	public SearchProducRateInfo02864 setRate(String rate)
	{
		this.rate = rate;
		return this;
	}
	public String getDrawMonth()
	{
		return drawMonth;
	}
	public SearchProducRateInfo02864 setDrawMonth(String drawMonth)
	{
		this.drawMonth = drawMonth;
		return this;
	}
	public String getInteDate()
	{
		return inteDate;
	}
	public void setInteDate(String inteDate)
	{
		this.inteDate = inteDate;
	}

	public String getBenDateStr()
	{
		return benDateStr;
	}

	public SearchProducRateInfo02864 setBenDateStr(String benDateStr)
	{
		this.benDateStr = benDateStr;
		return this;
	}

	public String getEndDateStr()
	{
		return endDateStr;
	}

	public SearchProducRateInfo02864 setEndDateStr(String endDateStr)
	{
		this.endDateStr = endDateStr;
		return this;
	}

	public String getLockStarDate() {
		return lockStarDate;
	}

	public void setLockStarDate(String lockStarDate) {
		this.lockStarDate = lockStarDate;
	}

	public String getLockEndDate() {
		return lockEndDate;
	}

	public void setLockEndDate(String lockEndDate) {
		this.lockEndDate = lockEndDate;
	}
	
}
