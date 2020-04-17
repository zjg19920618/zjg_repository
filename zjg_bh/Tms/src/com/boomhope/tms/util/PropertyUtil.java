package com.boomhope.tms.util;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 配置文件读取
 *
 */
public class PropertyUtil {
	public static String qz_socket_ip;
	public static int qz_socket_port;
	/**
	 * 初始化
	 */
    public static void initProperty() { 
    	Properties prop =  getProperties();
    	qz_socket_ip = (String) prop.get("qz_socket_ip");
    	qz_socket_port = (int) prop.get("qz_socket_port");
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
        	in = new FileInputStream(PropertyUtil.class.getClassLoader().getResource("/config.properties").getPath());
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