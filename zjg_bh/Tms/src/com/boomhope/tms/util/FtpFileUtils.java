package com.boomhope.tms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;




/**
 * FTP
 * @author Administrator
 *
 */
public class FtpFileUtils {
	protected static Logger log = Logger.getLogger(FtpFileUtils.class);

	
	/***
	 * 下载文件
	 * 
	 * @param ftpPath
	 * @param savePath
	 * @return
	 */
	public static boolean downloadFile(String ftpPath, String savePath) {
		InputStream in = null;
		FileOutputStream fileOutputStream = null;
		
		try {
			File file = new File(savePath);
			if (!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			
			fileOutputStream = new FileOutputStream(file);
			
			in = new URL(ftpPath).openConnection().getInputStream();
			
			byte[] buffer = new byte[512];
			int len;
			while ((len = in.read(buffer)) > 0) {
				fileOutputStream.write(buffer, 0, len);
			}
			return true;
		} catch (Exception e) {
			log.error("", e);
		} finally {
			if (null != fileOutputStream)
				try {
					fileOutputStream.close();
				} catch (IOException e) {
				}
			if (null != in)
				try {
					in.close();
				} catch (IOException e) {
				}
		}

		return false;
	}

	public static boolean uploadFile(String ftpPath, File file) {
		OutputStream out = null;
		FileInputStream fileInputStream = null;
		
		try {
			if (!file.exists()) {
				new IOException("not found file");
				return false;
			}
			fileInputStream = new FileInputStream(file);
			
			out = new URL(ftpPath).openConnection().getOutputStream();
			
			byte[] buffer = new byte[512];
			int len;
			while ((len = fileInputStream.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.flush();
			
			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fileInputStream)
				try {
					fileInputStream.close();
				} catch (IOException e) {
				}
			if (null != out)
				try {
					out.close();
				} catch (IOException e) {
				}
		}
		return false;
	}

	public static boolean uploadFile(String ftpPath, byte[] bytes) {
		OutputStream out = null;
		
		try {
			out = new URL(ftpPath).openConnection().getOutputStream();
			out.write(bytes);
			out.flush();
			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != out)
				try {
					out.close();
				} catch (IOException e) {
				}
		}
		return false;
	}

	public static boolean downloadFileByFTPClient(String ip, int port, String user, String pass, String ftpPath, String savePath) {
		try{
			FTPClient ftpClient = new FTPClient();
			ftpClient.connect(ip, port);
			ftpClient.login(user, pass);
			String downPath = ftpPath;
			File saveFile = new File(savePath);
			if (!saveFile.getParentFile().exists()){
				saveFile.getParentFile().mkdirs();
			}
			else if (saveFile.exists()) {
				saveFile.delete();
				saveFile.createNewFile();
			}
			ftpClient.setBufferSize(1024);
			FileOutputStream fos = new FileOutputStream(saveFile);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.retrieveFile(downPath, fos);
			IOUtils.closeQuietly(fos);
			ftpClient.disconnect();
		}
		catch(Exception ex){
			
			throw new RuntimeException(ex);
		}
		return false;
	}
	

	public static void main(String[] args) throws UnsupportedEncodingException {
		
		String FTP_IP="198.1.246.206";
		String FTP_USER="ftp1";
		String FTP_PAS="ftp1";
		String LOCAL_FOLDER_ADDRESS="d://abc";
		String fileName="/home/files/ftefile/qzfile/sgle20160917120157000110";
		FtpFileUtils.downloadFileByFTPClient(FTP_IP, 21, FTP_USER, FTP_PAS, fileName, LOCAL_FOLDER_ADDRESS);
		
		
	}
}
