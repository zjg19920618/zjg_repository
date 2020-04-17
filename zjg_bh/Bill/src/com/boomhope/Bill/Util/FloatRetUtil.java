package com.boomhope.Bill.Util;

public class FloatRetUtil {
	
	public float getFloatRet(String area,String chl,String birth,String time,String comb) throws Exception{
		return toFloat(area)+toFloat(chl)+toFloat(birth)+toFloat(time)+toFloat(comb);
	}
	public float toFloat(String f) throws Exception{
		if(f!=null){
			try
			{
				f=f.trim();
				return Float.parseFloat(f);
			}
			catch (Exception e){
				throw new Exception("约享存利率转换失败!");
			}
		}
		return 0.000f;
	}
}
