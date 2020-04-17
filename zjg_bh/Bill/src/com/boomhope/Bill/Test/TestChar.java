package com.boomhope.Bill.Test;

import java.math.BigDecimal;

public class TestChar {
	public static void main(String[] args) {
		String aa="60.00";
	if(null == aa || "".equals(aa) ){
			String Td1="0";
			String aa1="60.00";
			String aa2="600.00";
			BigDecimal bd = new BigDecimal(aa1);
			BigDecimal bd1 = new BigDecimal(aa2);
			BigDecimal bd3 = new BigDecimal(Td1);
			//减去糖豆
			String str = String.valueOf(bd.add(bd1).subtract(bd3));
			String real = "本息合计"+str+"元   已转入";
	System.out.println("sdsad"+real);
	
		}else{			
			String  a="60.00";
			String aa1="60.00";
			String aa2="600.00";
			BigDecimal bd = new BigDecimal(aa1);
			BigDecimal bd1 = new BigDecimal(aa2);
			BigDecimal bd3 = new BigDecimal(a);
			//减去糖豆
			String str = String.valueOf(bd.add(bd1).subtract(bd3));
			String real = "本息合计"+str+"元   已转入";
			System.out.println("sdsad"+real);
		}
	}  
}
