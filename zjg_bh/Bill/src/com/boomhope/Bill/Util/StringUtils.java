package com.boomhope.Bill.Util;

/** 
 * StringUtils 
 *  
 * 
*/  
public class StringUtils {  
  
/** 
     * 判断字符串是否为空或空字符串 
     * @param str 原字符串 
     * @return 
     */  
public static boolean isEmpty(String str) {  
        return str == null || "".equals(str);  
        }  

/** 
* 全角转半角: 
* @param fullStr 
* @return 
*/  
public static  String full2Half(String fullStr) {  
        if(isEmpty(fullStr)){  
            return fullStr;  
        }  
        char[] c = fullStr.toCharArray();  
        for (int i = 0; i < c.length; i++) {  
            if (c[i] >= 65281 && c[i] <= 65374) {  
                c[i] = (char) (c[i] - 65248);  
            } else if (c[i] == 12288) { // 空格  
                c[i] = (char) 32;  
            }  
        }  
        return new String(c);  
    }  
  
    /** 
     * 半角转全角 
     * @param halfStr 
     * @return 
     */  
    public static  String half2Full(String halfStr) {  
        if(isEmpty(halfStr)){  
            return halfStr;  
        }  
        char[] c = halfStr.toCharArray();  
        for (int i = 0; i < c.length; i++) {  
            if (c[i] == 32) {  
                c[i] = (char) 12288;  
            } else if (c[i] < 127) {  
                c[i] = (char) (c[i] + 65248);  
            }  
        }  
        return new String(c);  
   }
    
   
}  


