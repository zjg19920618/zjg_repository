package com.boomhope.Bill.TransService.Update.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class FtpUtils {
	
	static Logger logger = Logger.getLogger(FtpUtils.class);
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
			logger.debug("链接ip："+host+"port："+port);
			ftpClient.login(userName, password);
			logger.debug("链接user："+userName+"pwd："+password);
			//返回连接码
			int reply = ftpClient.getReplyCode();
			logger.debug("链接返回码："+reply);
			//如果不等于正确的连接码，则返回false
			if(!FTPReply.isPositiveCompletion(reply)){
				//断开连接
				ftpClient.disconnect();
				return result;
			}
			if(!ftpClient.changeWorkingDirectory(basePath+filePath)){
				String [] dirs = filePath.split("/");
				String  tempPath = basePath;
				for (String dir : dirs) {
					if(StringUtils.isBlank(dir)){
						continue;
					}
					tempPath += "/"+ dir;
					if(!ftpClient.changeWorkingDirectory(tempPath)){
						if(!ftpClient.makeDirectory(tempPath)){
							return result;
						}else{
							logger.debug("创建目录："+tempPath);
							ftpClient.changeWorkingDirectory(tempPath);
						}
					}
				}
				logger.debug("fpt工作目录："+tempPath);
			}else{
				logger.debug("fpt工作目录："+basePath+filePath);
			}
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			if(!ftpClient.storeFile(fileName, input)){
				return result;
			}
			logger.debug("上传文件："+fileName);
			result = true;
		} catch (IOException e) {
			logger.error("上传文件失败"+e);
			return result;
		}finally{
			try {
				input.close();
				ftpClient.logout();
			} catch (IOException e1) {
				logger.error("关闭ftp失败"+e1);
			}
			if(ftpClient.isConnected()){
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					logger.error("关闭ftp失败"+e);
				}
			}
		}
		return result;
	}
	
	/**
	 * 下载文件
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
			logger.debug("链接ip："+host+"port："+port);
			ftpClient.login(userName, password);
			logger.debug("链接user："+userName+"pwd："+password);
			//返回连接码
			int reply = ftpClient.getReplyCode();
			logger.debug("链接返回码："+reply);
			//如果不等于正确的连接码，则返回false
			if(!FTPReply.isPositiveCompletion(reply)){
				//断开连接
				ftpClient.disconnect();
				return result;
			}
			ftpClient.changeWorkingDirectory(remotePath);
			logger.debug("fpt工作目录："+remotePath);
			FTPFile[] fs = ftpClient.listFiles();
			for (FTPFile ftpFile : fs) {
				if(ftpFile.equals(fileName)){
					File localFile = new File(localPath + "/" + ftpFile.getName());
					OutputStream outputStream = new FileOutputStream(localFile);
					try {
						ftpClient.retrieveFile(ftpFile.getName(), outputStream);
						logger.debug("下载文件："+localPath + "/" + ftpFile.getName());
					} catch (IOException e) {
						logger.error("下载文件失败"+ftpFile.getName()+e);
						throw new IOException(e);
					}finally{
						outputStream.close();
					}
				}
			}
			result = true;
		} catch (IOException e) {
			logger.error("文件下载失败"+fileName+"."+e);
		}finally{
			try {
				ftpClient.logout();
			} catch (IOException e1) {
				logger.error("ftp关闭失败"+e1);
			}
			if(ftpClient.isConnected()){
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					logger.error("ftp关闭失败"+e);
				}
			}
		}
		return result;
	}
	/**
	 *  登录FTP
	 * @param host ip地址
	 * @param port 端口号
	 * @param userName 用户名
	 * @param password 密码
	 * @return
	 */
	public static boolean loginFtp(FTPClient ftpClient,String host,int port,String userName,String password)throws Exception{
		boolean result = false;
		ftpClient.connect(host, port);
		logger.debug("链接ip："+host+"port："+port);
		ftpClient.login(userName, password);
		logger.debug("链接user："+userName+"pwd："+password);
		//返回连接码
		int reply = ftpClient.getReplyCode();
		logger.debug("链接返回码："+reply);
		//如果不等于正确的连接码，则返回false
		if(!FTPReply.isPositiveCompletion(reply)){
			logger.debug("未链接成功");
			//断开连接
			ftpClient.disconnect();
			return result;
		}
		return true;
	}
	/**
	 * 上传文件
	 * @param basePath  基础目录
	 * @param filePath  文件要存放的目录
	 * @param fileName  存放的文件名称
	 * @param input     文件流
	 */
	public static boolean uploadFiles(FTPClient ftpClient,String basePath,String filePath,String fileName,InputStream input) throws Exception{
		boolean result = false;
		try {
			if(!ftpClient.changeWorkingDirectory(basePath+filePath)){
				String [] dirs = filePath.split("/");
				String  tempPath = basePath;
				for (String dir : dirs) {
					if(StringUtils.isBlank(dir)){
						continue;
					}
					tempPath += "/"+ dir;
					if(!ftpClient.changeWorkingDirectory(tempPath)){
						if(!ftpClient.makeDirectory(tempPath)){
							return result;
						}else{
							logger.debug("创建目录："+tempPath);
							ftpClient.changeWorkingDirectory(tempPath);
						}
					}
				}
				logger.debug("fpt工作目录："+tempPath);
			}else{
				logger.debug("fpt工作目录："+basePath+filePath);
			}
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			if(!ftpClient.storeFile(fileName, input)){
				return result;
			}
			logger.debug("上传文件："+fileName);
		} catch (Exception e) {
			logger.error("文件下载失败"+fileName+"."+e);
			throw new Exception(e);
		}finally{
			input.close();
		}
		return true;
	}
}
