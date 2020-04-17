package com.boomhope.tms.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

public class UpdateBankFile {
	protected static Logger log = Logger.getLogger(UpdateBankFile.class);
	public static void main(String[] args) {
		String KEY_FTP_IP="198.1.246.206";
		String KEY_FTP_PORT="21";
		String KEY_FTP_USER="ftp1";
		String KEY_FTP_PWD="ftp1";
		String KEY_FTP_LOCAL_PATH="D:\\bill\\";
		String BANKINFO_PATH_CS="/home/ftp1/bkinfo/";
		String date="20170421";
//		System.out.println(cf());
//		readBankInfo(KEY_FTP_LOCAL_PATH, date);
//		List<Map<String,String>> infos=dealCC00(KEY_FTP_LOCAL_PATH, date);
		downloadBankFile(KEY_FTP_IP, Integer.valueOf("21"), KEY_FTP_USER, KEY_FTP_PWD, BANKINFO_PATH_CS, KEY_FTP_LOCAL_PATH);
//		File[] file=new File(KEY_FTP_LOCAL_PATH).listFiles();
//		List<String> names=new ArrayList<String>();
//		for (int i = 0; i < file.length; i++) {
//			if(file[i].getName().startsWith("BANKINFO_017260")){
//				names.add(file[i].getName());
//			}
//		}
//		System.out.println(names.size());
//		if(names.size()>1){
//			dealNewFile();
//		}else{
//			cf();
//		}
//		System.out.println(getLocalInfos("1000").size());
//		System.out.println(readBankInfoCC01().size());
//		System.out.println(getInfos().size());
//		for (Map map : infos) {
//			System.out.println(	map.get("info"));
//		}
	}
	/**
	 * 下载文件
	 * @param ip
	 * @param port
	 * @param user
	 * @param pass
	 * @param ftpPath
	 * @param savePath
	 * @return
	 */
	public static File downloadBankFile( String ip, int port, String user, String pass, String ftpPath, String savePath){
		FTPClient ftpClient=null;
		FileOutputStream fos=null;
		try{
			ftpClient = new FTPClient();
			log.info("开始登录服务器...ip"+ip+"port"+port+"user"+user+"pass"+pass);
			ftpClient.connect(ip, port);
			log.info("connection success!");
			ftpClient.login(user, pass);
			log.info("login success!");
			ftpClient.changeWorkingDirectory(ftpPath);
			log.info("ftp路径："+ftpPath);
			FTPFile[] fs = ftpClient.listFiles();
			String name=getMaxFile(fs);
			if("".equals(name)){
				return null;
			}
			log.info("文件名"+name);
			String downPath=ftpPath+name;
			log.info("登陆成功，开始转换到文件目录："+downPath);
			File saveFile = new File(savePath+name);
			if (!saveFile.getParentFile().exists()){
				saveFile.getParentFile().mkdirs();
			}
			else if (saveFile.exists()) {
				log.info("文件已下载");
				return null;
			}
			ftpClient.setBufferSize(1024);
			fos = new FileOutputStream(saveFile);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.retrieveFile(downPath, fos);
			return saveFile;
		}catch(Exception ex){
			log.error("接受文件失败", ex);
		}finally{
			if(fos!=null){
				try {
					fos.close();
					IOUtils.closeQuietly(fos);
				} catch (IOException e) {
					log.error("关闭文件流失败", e);
				}
			}
			if(ftpClient!=null){
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					log.error("关闭ftp失败", e);
				}
			}
		}
		return null;
		
	}
	/**
	 * 获取最后一天文件名
	 * @param fs
	 * @return
	 */
	private static String getMaxFile(FTPFile[] fs){
		String[] names=new String[fs.length];
		for (int i = 0; i < fs.length; i++) {
			names[i]=fs[i].getName();
		}
		if(fs.length==0){
			return "";
		}
		String maxName=names[0];
		for (int j = 0; j < names.length; j++) {
			if(intName(names[j])>intName(maxName)){
				maxName=names[j];
			}
		}
		return maxName;
	}
	/**
	 * 转换名称
	 * @param name
	 * @return
	 */
	private static int intName(String name){
		try {
			if(name==null||name.length()<8){
				return 0;
			}
			return Integer.valueOf(name.substring(name.length()-8));
		} catch (Exception e) {
			log.error(name+":"+e);
			return 0;
		}
	}
}
