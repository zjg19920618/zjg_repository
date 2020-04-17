package com.boomhope.Bill.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.apache.log4j.Logger;

public class SFTPUtil {
	
	static Logger logger = Logger.getLogger(SFTPUtil.class);
	/**
	 * 连接sftp服务器
	 * 
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 * @throws JSchException
	 */
	public ChannelSftp connect(String host, int port, String username,String password) throws JSchException {
		ChannelSftp sftp = null;
		JSch jsch = new JSch();
		jsch.getSession(username, host, port);
		Session sshSession = jsch.getSession(username, host, port);
		System.out.println("Session created.");
		sshSession.setPassword(password);
		Properties sshConfig = new Properties();
		sshConfig.put("StrictHostKeyChecking", "no");
		sshSession.setConfig(sshConfig);
		sshSession.connect();
		System.out.println("Session connected.");
		System.out.println("Opening Channel.");
		Channel channel = sshSession.openChannel("sftp");
		channel.connect();
		sftp = (ChannelSftp) channel;
		System.out.println("Connected to " + host + ".");
		return sftp;
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 */
	public boolean upload(String directory, String uploadFile, ChannelSftp sftp) {
		boolean flag = true;
		try {
			sftp.cd(directory);
			File file = new File(uploadFile);
			FileInputStream fileInputStream = new FileInputStream(file);
			sftp.put(fileInputStream, file.getName());
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("sftp文件上传失败"+e);
			flag = false;
		}
		return flag;
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @param sftp
	 */
	public void download(String directory, String downloadFile,
			String saveFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			File file = new File(saveFile);
			sftp.get(downloadFile, new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("sftp文件下载失败"+e);
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * @param sftp
	 */
	public void delete(String directory, String deleteFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("删除文件失败"+e);
		}
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	public Vector listFiles(String directory, ChannelSftp sftp)
			throws SftpException {
		return sftp.ls(directory);
	}

	public static void main(String[] args) throws Exception {
		SFTPUtil sf = new SFTPUtil();
		String host = "198.1.245.93";
		int port = 22;
		String username = "root";
		String password = "rootroot";
		String directory = "/home/abc/";
		String uploadFile = "E:\\hehe.jpg";

		String downloadFile = "hehe.jpg";
		String saveFile = "/home/abc/";
		String deleteFile = "hehe.jpg";
		ChannelSftp sftp = sf.connect(host, port, username, password);
		sf.upload(directory, uploadFile, sftp);
		// sf.download(directory, downloadFile, saveFile, sftp);
		// sf.delete(directory, deleteFile, sftp);
	
		sftp.cd(directory);
		
		System.out.println("finished");

	}
}
