package com.boomhope.tms.transaction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 读取config.properties配置文件中的值
 * @author zhang.m
 *
 */
public class ConfigReader {
	static Logger logger = Logger.getLogger(ConfigReader.class);
	
	public  static String getConfig(String config){
		InputStream is = null;
	    Properties prop = new Properties();   
	    BufferedReader br= null;
	    try{
	    	is = new FileInputStream(ConfigReader.class.getClassLoader().getResource("/config.properties").getPath());
	    	br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	    	prop.load(br); 
	    	return String.valueOf(prop.get(config));
	    }
	    catch(Exception e){
	    	logger.error(e);
	    }finally{
	    	try {
				is.close();
			} catch (IOException e) {
				logger.error(e);
			}
	    }
	    return null;
//	    try {
//	    	String str = "";
//	    	Properties properties = new Properties();
//	    	FileInputStream fileInputStream = new FileInputStream(new File(new ConfigReader().getClass().getClassLoader().getResource(".").getPath()+"/config.properties"));
//	    	properties.load(fileInputStream);
//	    	str = (String) properties.get(config);
//	    	return str;
//		} catch (Exception e) {
//			logger.error(e);
//		} 
//	    	return null;
	}
	
}
