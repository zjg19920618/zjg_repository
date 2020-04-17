package com.boomhope.Bill.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class FtpUtils {
	protected static Logger log = Logger.getLogger(FtpFileUtils.class);
	/**
	 * 文件上传
	 * @param host		ip地址
	 * @param port		端口号
	 * @param userName	用户名
	 * @param password	密码
	 * @param basePath	基础目录
	 * @param filePath	文件存放目录
	 * @param fileName	要存放的文件名
	 * @param input		文件输入流
	 * @return
	 */
	public static boolean uploadFile(String host,int port,String userName,String password,String basePath,String filePath,String fileName,InputStream input){
		boolean result = false;
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(host, port);
			ftpClient.login(userName, password);
			//返回连接码
			int reply = ftpClient.getReplyCode();
			//如果不等于正确的连接码，则返回false
			if(!FTPReply.isPositiveCompletion(reply)){
				//断开连接
				ftpClient.disconnect();
				return result;
			}
//			if(!ftpClient.changeWorkingDirectory(basePath+filePath)){
//				String [] dirs = filePath.split("/");
//				String  tempPath = basePath;
//				for (String dir : dirs) {
//					if(StringUtils.isBlank(dir)){
//						continue;
//					}
//					tempPath += "/"+ dir;
//					if(!ftpClient.changeWorkingDirectory(tempPath)){
//						if(!ftpClient.makeDirectory(tempPath)){
//							return result;
//						}else{
//							ftpClient.changeWorkingDirectory(tempPath);
//						}
//					}
//				}
//			}
			
			if(!ftpClient.changeWorkingDirectory(basePath)){
							ftpClient.changeWorkingDirectory(basePath);
			}
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			if(!ftpClient.storeFile(fileName, input)){
				return result;
			}
			input.close();
			ftpClient.logout();
			result = true;
		} catch (IOException e) {
			log.error("", e);
		}finally{
			if(ftpClient.isConnected()){
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					log.error("", e);
				}
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param host			ip地址
	 * @param port			端口号
	 * @param userName		用户名
	 * @param password		密码
	 * @param remotePath	路径
	 * @param fileName		文件名称
	 * @param localPath		本地路径
	 * @return
	 */
	public static boolean downloadFile(String host,int port,String userName,String password,String remotePath,String fileName,String localPath){
		boolean result = false;
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(host, port);
			ftpClient.login(userName, password);
			//返回连接码
			int reply = ftpClient.getReplyCode();
			//如果不等于正确的连接码，则返回false
			if(!FTPReply.isPositiveCompletion(reply)){
				//断开连接
				ftpClient.disconnect();
				return result;
			}
			ftpClient.changeWorkingDirectory(remotePath);
			FTPFile[] fs = ftpClient.listFiles();
			for (FTPFile ftpFile : fs) {
				if(ftpFile.equals(fileName)){
					File localFile = new File(localPath + "/" + ftpFile.getName());
					OutputStream outputStream = new FileOutputStream(localFile);
					ftpClient.retrieveFile(ftpFile.getName(), outputStream);
					outputStream.close();
				}
			}
			ftpClient.logout();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(ftpClient.isConnected()){
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
}
