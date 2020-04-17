package com.boomhope.Bill.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

/**
 * 文件解析类
 * @author Administrator
 *
 */
public class UtilPreFile {
	
	protected static Logger log = Logger.getLogger(UtilPreFile.class);
	
	/**
	 * 文件解析-客户信息
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static List  preCustInfo(String fileName) throws Exception{
		List<String> list = new ArrayList<String>();
		File  file = new File(fileName);
		InputStream is = null;
		BufferedReader br= null;
		String value = null;
		try {
			is = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(is, "GBK"));
			value = br.readLine();
		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}finally{
			if(br != null){
				br.close();
			}if(is != null){
				is.close();
			}if(file != null){
				is.close();
			}
		}
		
		if(value == null){
			return null;
		}
		String sbf = "";
		String[] into = value.split("");
		int flag = 0;
		for (int i = 0; i <into.length ; i++) {
			if(into[i].equals("|")){
				flag += 1;
			}
			if(flag == 2){
				flag = 0;
				list.add(sbf);
				sbf = "";
			}else if(flag == 0){
				sbf += (into[i]);
			}
			
		}
		return list;
	}
	
	/**
	 * 获取身份证照片
	 * @param fileName
	 * @param toFileName
	 * @throws Exception
	 */
	public static void getIdCardImage(String fileName,String toFileName) throws Exception{
		
		BASE64Decoder base64Decoder = new BASE64Decoder();

		List<String> list = new ArrayList<String>();
		File  file1 = new File(fileName);
		InputStream is = null;
		BufferedReader br= null;
		String value = null;
		try {
			is = new FileInputStream(file1);
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			if(br.readLine() == null){
				return ;
			}
			value = br.readLine();
			System.out.println(value);
			
			if(value != null){
				byte[] b = base64Decoder.decodeBuffer(value);
				for(int i=0;i<b.length;++i)  
	            {  
	                if(b[i]<0)  
	                {//调整异常数据  
	                    b[i]+=256;  
	                }  
	            }  
	            //生成jpeg图片  
	            //String imgFilePath = toFileName;//新生成的图片  
	            File  file_out = new File(toFileName);
	            if (!file_out.getParentFile().exists()){
	            	file_out.getParentFile().mkdirs();
				}
				else if (file_out.exists()) {
					file_out.delete();
					file_out.createNewFile();
				}
	            OutputStream out = new FileOutputStream(file_out);      
	            out.write(b);  
	            out.flush();  
	            out.close();
			}
		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}finally{
			if(br != null){
				br.close();
			}if(is != null){
				is.close();
			}if(file1 != null){
				is.close();
			}
		}
		
		
	
	}
	
	
	
	public static void main(String[] args){
		try {
			UtilPreFile.getIdCardImage("D:\\ftp\\sgle20160916162415000061","d://aa.png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
