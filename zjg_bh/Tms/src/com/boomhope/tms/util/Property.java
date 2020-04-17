package com.boomhope.tms.util;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 配置文件读取
 * @author zy
 *
 */
public class Property {
	
	public static String moneyBoxSocketIp="";
	
	public static String moneyBoxSocketPort="";
	
	
	/**
	 * 初始化
	 */
    public static void initProperty() { 
    	Properties prop =  getProperties();
        
    	moneyBoxSocketIp=(String)prop.get("moneyBoxSocketIp");
    	
    	moneyBoxSocketPort=(String)prop.get("moneyBoxSocketPort");

    } 
    
    /**
     * 加载配置文件数据
     * @return
     */
    public static Properties getProperties(){
    	InputStream in = null;
        Properties prop = new Properties();   
        BufferedReader br= null;
        try{
        	String classPath=Properties.class.getResource("/").getPath().replaceFirst("/","");
        	in = new FileInputStream(classPath+"config.properties");
            //in = new BufferedInputStream (new FileInputStream("config\\config.properties"));
        	br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        	prop.load(br); 
           
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
        	try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return prop;
    }
}