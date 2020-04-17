package com.boomhope.Bill.Test;

import java.io.File;
import java.util.Arrays;

import com.boomhope.Bill.Util.Property;

public class FileNameTest {
	/** 
     * @param args 
     */  
    public static void main(String[] args) {  
//      举例：  
        String fName =" G:\\Java_Source\\navigation_tigra_menu\\demo1\\img\\lev1_arrow.gif ";  
  
//      方法一：  
  
        File tempFile =new File( fName.trim());  
          
        String fileName = tempFile.getName();  
          
        System.out.println("方法一：fileName = " + fileName);  
  System.out.println(Property.BILL_ID_SELF_JUST);
//      方法二：  
  
        fName = fName.trim();  
  
//      fileName = fName.substring(fName.lastIndexOf("/")+1);  
//      或者  
        fileName = fName.substring(fName.lastIndexOf("\\")+1);  
          
        System.out.println("方法二：fileName = " + fileName);  
  
//      方法三：  
  
        fName = fName.trim();  
  
        String temp[] = fName.split("\\\\"); /**split里面必须是正则表达式，"\\"的作用是对字符串转义*/  
  
        //temp[] = [G:, Java_Source, navigation_tigra_menu, demo1, img, lev1_arrow.gif]  
        System.out.println("temp[] = " + Arrays.toString(temp));  
        fileName = temp[temp.length-1];  
          
        System.out.println("方法三：fileName = " + fileName);  
  
    	String str = "A102-密码不符-，错误次数[0]";
		String jieguo1 = str.substring(str.indexOf("A")+0,str.indexOf("-"));
		System.out.println(jieguo1);
    }  
}
