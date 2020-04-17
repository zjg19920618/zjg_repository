package com.boomhope.Bill.Util;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ClassPathResource {      
    
  public static boolean isMobileNO(String mobiles){  
    
  Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(177)|(18[0,5-9]))\\d{8}$");  
    
  Matcher m = p.matcher(mobiles);  
    
  System.out.println(m.matches()+"---");  
  return m.matches();  
  
    
  } 
    
  public static void main(String[] args) throws IOException{ 
	  boolean a=  ClassPathResource.isMobileNO("15204606498");
	  if(a==false){
		  System.out.println("11");
	  }else{
		  System.out.println("22");
	  }
	  System.out.println(a);
	
	
}   
} 



