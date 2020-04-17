package com.boomhope.Bill.Util;


/** 
 * Created with IntelliJ IDEA. 
 * User: Administrator 
 * Date: 14-4-12 
 * Time: 上午11:18 
 * To change this template use File | Settings | File Templates. 
 */  
public class NumberZH{  
  
	private static final String UNIT = "万千佰拾亿仟佰拾万仟佰拾元角分";  
    private static final String DIGIT = "零壹贰叁肆伍陆柒捌玖"; 
    private static final double MAX_VALUE = 9999999999999.99D;  
    public static String change(double v) {  
     if (v < 0 || v > MAX_VALUE){  
         return "参数非法!";  
     }  
     long l = Math.round(v * 100);  
     if (l == 0){  
         return "零元整";  
     }  
     String strValue = l + "";  
     // i用来控制数  
     int i = 0;  
     // j用来控制单位  
     int j = UNIT.length() - strValue.length();  
     String rs = "";  
     boolean isZero = false;  
     for (; i < strValue.length(); i++, j++) {  
      char ch = strValue.charAt(i);  
      if (ch == '0') {  
       isZero = true;  
       if (UNIT.charAt(j) == '亿' || UNIT.charAt(j) == '万' || UNIT.charAt(j) == '元') {  
        rs = rs + UNIT.charAt(j);  
        isZero = false;  
       }  
      } else {  
       if (isZero) {  
        rs = rs + "零";  
        isZero = false;  
       }  
       rs = rs + DIGIT.charAt(ch - '0') + UNIT.charAt(j);  
      }  
     }  
     if (!rs.endsWith("分")) {  
      rs = rs + "整";  
     }  
     rs = rs.replaceAll("亿万", "亿");  
     return rs;  
    }  
      
    public static void main(String[] args){  
        System.out.println(NumberZH.change(99999999999.99));
        String str1 =",1";
        String str2 =",2";
        String str3 =",3";
        String str4 =",4";
        StringBuffer stb = new StringBuffer();
        stb.append(str1);
        stb.append(str2);
        stb.append(str3);
        stb.append(str4);
        String str= stb.reverse().toString();
        System.out.println(stb);
        System.out.println(str);
        String[] split = str.split(",");
        System.out.println(split.length);
        for (int i = 0; i < split.length; i++) {
			System.out.println(split[i]);
		}
        String strs = "000000001234034120";
        String newStr = strs.replaceAll("^(0+)", "");
        System.out.println(newStr); 
        
        String lo = "3535465667sasas".toUpperCase();
        System.out.println(lo);
    }  
}  