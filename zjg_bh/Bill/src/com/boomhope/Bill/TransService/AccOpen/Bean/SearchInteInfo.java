package com.boomhope.Bill.TransService.AccOpen.Bean;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;


/**
 * 4.8	产品预计利息(24小时)-03510  返回
 * @author Administrator
 *
 */
@Number(id = 1)
public class SearchInteInfo{
//	产品代码||金额||档次||利率||定期天数||定期利息||活期天数||活期利息

	@FieldSort(NO = 0)
	private String productCode;			//产品代码
	@FieldSort(NO = 1)
	private String money;			//金额
	@FieldSort(NO = 2)
	private String level;			//档次
	@FieldSort(NO = 3)
	private String rate;			//利率
	@FieldSort(NO = 4)
	private String regularDay;		//定期天数
	@FieldSort(NO = 5)
	private String regularInte;		//定期利息
	@FieldSort(NO = 6)
	private String currentDay;		//活期天数
	@FieldSort(NO = 7)
	private String currentInte;		//活期利息
	
	
	
	public String getProductCode()
	{
		return productCode;
	}
	public SearchInteInfo setProductCode(String productCode)
	{
		this.productCode = productCode;
		return this;
	}
	public String getMoney()
	{
		return money;
	}
	public SearchInteInfo setMoney(String money)
	{
		this.money = money;
		return this;
	}
	public String getLevel()
	{
		return level;
	}
	public SearchInteInfo setLevel(String level)
	{
		this.level = level;
		return this;
	}
	public String getRate()
	{
		return rate;
	}
	public SearchInteInfo setRate(String rate)
	{
		this.rate = rate;
		return this;
	}
	public String getRegularDay()
	{
		return regularDay;
	}
	public SearchInteInfo setRegularDay(String regularDay)
	{
		this.regularDay = regularDay;
		return this;
	}
	public String getRegularInte()
	{
		return regularInte;
	}
	public SearchInteInfo setRegularInte(String regularInte)
	{
		this.regularInte = regularInte;
		return this;
	}
	public String getCurrentDay()
	{
		return currentDay;
	}
	public SearchInteInfo setCurrentDay(String currentDay)
	{
		this.currentDay = currentDay;
		return this;
	}
	public String getCurrentInte()
	{
		return currentInte;
	}
	public SearchInteInfo setCurrentInte(String currentInte)
	{
		this.currentInte = currentInte;
		return this;
	}

}
