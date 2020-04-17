package com.boomhope.Bill.TransService.Update.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 解析配置文件
 * @author wxm
 *
 */
public class PropertiesUtil {

	static Logger logger = Logger.getLogger(PropertiesUtil.class);
	/**
	 * 通过键获取值
	 * @param key
	 * @return
	 */
	public static String getPropertyByKey(String key){
		Properties properties = new Properties();
		FileInputStream input=null;
		try {
			logger.debug("开始解析；‘config\\update.properties’文件");
			input=new FileInputStream("config\\update.properties");
			properties.load(input);
		} catch (IOException e) {
			logger.error("解析；‘config\\update.properties’文件失败："+e);
		}finally{
			try {
				if(input!=null){
					input.close();
				}
			} catch (IOException e) {
				logger.error("关闭文件流失败："+e);
			}
		}
		return properties.getProperty(key);
	}
	
	public static void main(String[] args) {
		
		System.out.println(getPropertyByKey("clientUpdate"));
	}
}
