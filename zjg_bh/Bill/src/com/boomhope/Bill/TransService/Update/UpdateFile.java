package com.boomhope.Bill.TransService.Update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.boomhope.Bill.TransService.Update.Util.FtpUtils;
import com.boomhope.Bill.TransService.Update.Util.LocalUpdateInfo;
import com.boomhope.Bill.TransService.Update.Util.PropertiesUtil;
import com.boomhope.Bill.TransService.Update.Util.RemotUpdateInfo;
import com.boomhope.Bill.TransService.Update.Util.UpdFile;
import com.boomhope.Bill.Util.FileUtil;


/**
 * 
 * Title:下载文件
 * Description:
 * @author wxm
 * @date 2016年10月12日 下午7:24:49
 */
public class UpdateFile {

	Logger logger = Logger.getLogger(UpdateFile.class);
	private FTPClient ftpClient = null;
	private LocalUpdateInfo config = LocalUpdateInfo.getInstance();//获取本地版本信息
	@SuppressWarnings("rawtypes")
	private HashMap cFiles = new HashMap();//本地配置文件中的所有文件信息
	private RemotUpdateInfo serverVerParser = null;
	
	private boolean isUpdate=false;
	/**
	 * 更新文件入口
	 * @return
	 */
	public boolean uploadFile(){
		// 加载日志
		PropertyConfigurator.configure( "config\\log4j.properties" );
		ftpClient = new FTPClient();
		boolean result = false;
		try {
			logger.info("解析配置文件config.properties!");
			String ip=PropertiesUtil.getPropertyByKey("ip");
			String port=PropertiesUtil.getPropertyByKey("port");
			String userName=PropertiesUtil.getPropertyByKey("userName");
			String password=PropertiesUtil.getPropertyByKey("password");
			logger.info("开始执行连接FTP！ip:"+ip+"port:"+port+"userName:"+userName+"password:"+password);
			//设置超时时间
			ftpClient.setConnectTimeout(30*1000);
			result = FtpUtils.loginFtp(ftpClient, ip, Integer.parseInt(port), userName, password);
			if(!result){
				//连接FTP失败
				logger.error("连接FTP失败！");
				return result;
			}
			logger.info("连接FTP成功！");
		} catch (Exception e) {
			logger.error("在连接ftp时报错："+e);
			return false;
		}
		OutputStream outputStream = null;
		boolean flag ;
		try {
			/**
			 * 服务器上的版本信息在/home/autoupdate.xml，
			 * 将服务器上的版本下载到本地D:/autoupdate.xml，，
			 * 然后将C和D上的文件对比，并获得版本信息；执行相应的更新
			 */
			//获取本地版本信息
			String clientVerstion = config.getVerstion();
			logger.info("本地版本号--->"+clientVerstion);
			if(clientVerstion==null||"".equals(clientVerstion)){
				return false;
			}
			//获取服务器版本信息
			File localFile = new File(PropertiesUtil.getPropertyByKey("downloadUpdate"));
			outputStream = new FileOutputStream(localFile);
			flag = ftpClient.retrieveFile(PropertiesUtil.getPropertyByKey("serverUpdate"), outputStream);
			if(!flag){
				//获取版本服务器失败
				logger.error("获取服务器版本文件失败！");
				return flag;
			}else{
				//获取服务器版本信息
				serverVerParser = new RemotUpdateInfo(localFile);
				String serverVerstion = serverVerParser.getVerstion();
				logger.info("服务器版本号--->"+serverVerstion);
//				String default1 = serverVerParser.getDefault();//该字段判断是否需要重启
				
				//比较版本信息
				boolean b = sendUpdateFile(serverVerstion, clientVerstion);
				if(!isUpdate){
					return true;
				}
				if (!b) {
					return b;
				}
			}
			result = true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}catch(Throwable t){
			logger.error(t);
			return false;
		}finally{
			try {
				if(outputStream!=null){
					outputStream.close();
				}
			} catch (IOException e2) {
				logger.error(e2);
				return false;
			}
			try {
				ftpClient.logout();
			} catch (IOException e1) {
				logger.error("ftp关闭失败："+e1);
				return false;
			}
			if(ftpClient.isConnected()){
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					logger.error("ftp关闭失败："+e);
					return false;
				}
			}
		}
		// 进行文件拷贝
		updateXmlFile();
		return result;
	}
	
	//递归下载文件夹及内容
	private boolean getFiles(FTPClient ftpClient,String projectPath,String rootPath) throws IOException{
		boolean flag = true;
		ftpClient.changeWorkingDirectory(projectPath);
		logger.info("进入文件夹-->" + projectPath);
		FTPFile[] fs = ftpClient.listFiles();
		for (FTPFile ftpFile : fs) {
			if(ftpFile.isDirectory()){
				getFiles(ftpClient,projectPath +"/"+ ftpFile.getName(),rootPath +File.separator+ ftpFile.getName());
			}else{
				File file = new File(rootPath);
				if(!file.exists()){
					file.mkdirs();
					logger.info("创建目录："+rootPath);
				}
				File localFile = new File(rootPath + File.separator + ftpFile.getName());
				String serverPath=projectPath + "/" +ftpFile.getName();
				
				OutputStream outputStream = null;
				try {
					outputStream = new FileOutputStream(localFile);
					boolean result = ftpClient.retrieveFile(serverPath, outputStream);
					if(!result){
						logger.error("服务器文件更新到本地失败-->"+serverPath);
						flag = false;
						break;
					}else{
						logger.info("服务器文件更新到本地-->"+serverPath);
					}
				} catch (IOException e) {
					logger.error("服务器文件更新到本地失败-->"+serverPath);
					throw new IOException(e);
				}finally{
					if(outputStream!=null){
						outputStream.close();
					}
				}
				
				logger.info("更新文件成功-->"+projectPath + "/" + ftpFile.getName());
			}
		}
		return flag;
	}
	/**
	 * 比较版本信息，并判断
	 * @return
	 */
	private boolean sendUpdateFile(String serverVerstion,String clientVerstion) {
		boolean isSuccess = true;
		// 检查服务器和客户端版本号是否一致，如果一致辞，则无需升级
		logger.info(clientVerstion+"VS"+serverVerstion);
		if (serverVerstion==null||serverVerstion.equals(clientVerstion)) {
			logger.info("版本一致，无需更新！");
			isUpdate=false;
			return isSuccess;
		}
		isUpdate=true;
		String operation=serverVerParser.getOperation();
		logger.info("判断是否回滚操作："+operation);
		if("return".equals(operation)){
			returnClientFile("Info/Backup", serverVerstion);
			return true;
		}
		
		//备份本地文件
		backupClientFile("Info/Backup", clientVerstion);
		
		//版本比较
		try {
			if(comperaVersions(serverVerstion, clientVerstion)){
				//全部更新
				logger.info("如果版本号有间隔，全量升级");
				updateAllFiles("Info/whole");
			}else{
				logger.info("增量升级");
				updateFiles("Info/Files");
			}
		} catch (Exception e) {
			logger.error("版本比较失败！"+e);
			return false;
		}
		
		return true;
		
	}
	
	/**
	 * 判断是否跨版本
	 * @param serverVerstion
	 * @param clientVerstion
	 * @return
	 */
	public boolean comperaVersions(String serverVerstion,String clientVerstion)throws Exception{
		logger.info("开始版本比较");
		String[] server = serverVerstion.split("\\.");
		String[] client = clientVerstion.split("\\.");
		for (int i = 0; i < server.length; i++) {
			if(client[i]==null||isNotNum(server[i])||isNotNum(client[i])){
				return true;
			}
			int c=Integer.valueOf(client[i]);
			int s=Integer.valueOf(server[i]);
			if(i>0){
				int c1=Integer.valueOf(client[i-1]);
				int s1=Integer.valueOf(server[i-1]);
				if((s-c)>1){
					return true;
				}else if((s1-c1)!=0&&s!=0){
					return true;
				}
			}else{
				if((s-c)!=0){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 判断是否数字
	 * @param str
	 * @return
	 */
	public boolean isNotNum(String str){
		Pattern pat=Pattern.compile("[0-9]*");
		Matcher isNum=pat.matcher(str);
		return (!isNum.matches());
	}
	
	/**
	 * 更新升级文件
	 * @param default1
	 * @return
	 */
	private boolean updateXmlFile(){
		try {
			// 发送版本信息文件，发送更新信息文件
			logger.info("开始更新版本文件到本地");
			FileUtil.copyFileUsingJava7Files(new File(PropertiesUtil.getPropertyByKey("downloadUpdate")), new File(PropertiesUtil.getPropertyByKey("clientUpdate")));
			logger.info("版本更新完毕！");
			
			// 发送更新信息
			return true;
		} catch (Exception e) {
			logger.error("更新文件失败！"+ e);
			return false;
		}
	}
	
	/**
	 * 下载文件
	 * @param nodePath
	 */
	private void updateAllFiles(String nodePath){
		logger.info("文件节点路径"+nodePath);
		// 开始进行处理
		UpdFile srvFiles[] = serverVerParser.getFiles(nodePath);
		for (int i = 0; i < srvFiles.length; i++) {
			String serverPath = srvFiles[i].getPath() + srvFiles[i].getName();
			try {

				/**
				 * 执行FTP下载文件
				 * 1.获取服务器上的路径
				 * 2.获取本地路径
				 * serverPath 服务器上的全路径
				 */
				logger.info("更新服务器文件路径-->"+serverPath);
				String clientPath = srvFiles[i].getClientPath() + srvFiles[i].getName();
				logger.info("更新到本地文件路径-->"+clientPath);
				//File sPath = new File(serverPath);
				File localFile = new File(clientPath);
				//判断更新的是否是文件。或者是文件夹
				ftpClient.changeWorkingDirectory(srvFiles[i].getPath());
				FTPFile[] fs = ftpClient.listFiles();
				for (FTPFile ftpFile : fs) {
					if(srvFiles[i].getName().equals(ftpFile.getName())){
						if(ftpFile.isDirectory()){
							getFiles(ftpClient, serverPath, clientPath);
							break;
						}else{
							OutputStream outputStream = null;
							try {
								outputStream = new FileOutputStream(localFile);
								boolean result = ftpClient.retrieveFile(serverPath, outputStream);
								if(!result){
									logger.error("服务器文件更新到本地失败-->"+serverPath);
								}else{
									logger.error("服务器文件更新到本地-->"+serverPath);
								}
							} catch (Exception e) {
								logger.error("服务器文件更新到本地失败-->"+serverPath);
								throw new Exception(e);
							}finally{
								if(outputStream!=null){
									outputStream.close();
								}
							}
							break;
						}
					}
				}
			} catch (Exception e) {
				logger.error("更新失败："+serverPath+":"+e);
			}
		}
	}
	
	//更新文件
	@SuppressWarnings("unchecked")
	private void updateFiles(String nodePath){
		//获取本地配置文件中的信息
		UpdFile files[] = config.getFiles(nodePath);
		for (int i = 0; i < files.length; i++) {
			cFiles.put(files[i].getName(), files[i]);
		}
		// 开始进行处理
		UpdFile srvFiles[] = serverVerParser.getFiles(nodePath);
		for (int i = 0; i < srvFiles.length; i++) {
			UpdFile cf = (UpdFile) cFiles.get(srvFiles[i].getName());
			// 文件不存在或版本号不一致则需要更新该文件
			if (cf == null || !cf.getVersion().equals(srvFiles[i].getVersion())) {
				String serverPath = srvFiles[i].getPath() + srvFiles[i].getName();
				try {

					/**
					 * 执行FTP下载文件
					 * 1.获取服务器上的路径
					 * 2.获取本地路径
					 * serverPath 服务器上的全路径
					 */
					logger.info("更新服务器文件路径-->"+serverPath);
					String clientPath = srvFiles[i].getClientPath() + srvFiles[i].getName();
					logger.info("更新到本地文件路径-->"+clientPath);
					//File sPath = new File(serverPath);
					File localFile = new File(clientPath);
					//判断更新的是否是文件。，或者是文件夹
					ftpClient.changeWorkingDirectory(srvFiles[i].getPath());
					FTPFile[] fs = ftpClient.listFiles();
					for (FTPFile ftpFile : fs) {
						if(srvFiles[i].getName().equals(ftpFile.getName())){
							if(ftpFile.isDirectory()){
								getFiles(ftpClient, serverPath, clientPath);
								break;
							}else{
								OutputStream outputStream = new FileOutputStream(localFile);
								boolean result = ftpClient.retrieveFile(serverPath, outputStream);
								outputStream.close();
								if(!result){
									logger.error("服务器文件更新到本地失败-->"+serverPath);
								}else{
									logger.error("服务器文件更新到本地-->"+serverPath);
								}
								break;
							}
						}
					}
				} catch (Exception e) {
					logger.error("更新失败："+serverPath+":"+e);
				}
			}
		}
	}
	
	/**
	 * 备份本地文件
	 * @param nodePath
	 * @return
	 */
	private boolean backupClientFile(String nodePath,String clientVerstion){
		// 开始进行处理
		UpdFile srvFiles[] = serverVerParser.getFiles(nodePath);
		for (int i = 0; i < srvFiles.length; i++) {
			String clientPath = srvFiles[i].getClientPath() + srvFiles[i].getName();
			try {
				logger.info("本地文件路径-->"+clientPath);
				//File sPath = new File(serverPath);
				File localFile = new File(clientPath);
				if(!localFile.exists()){
					logger.error("备份本地文件不存在："+clientPath);
				}
				String backupPath=PropertiesUtil.getPropertyByKey("backupPath")+File.separator+clientVerstion;
				logger.info("备份文件路径："+backupPath);
				copyFiles(clientPath, backupPath);
			} catch (Exception e) {
				logger.error("备份失败："+clientPath+":"+e);
			}
		}
		return true;
	}
	
	/**
	 * 还原本地文件
	 * @param nodePath
	 * @return
	 */
	private boolean returnClientFile(String nodePath,String serverVerstion){
		// 开始进行处理
		UpdFile srvFiles[] = serverVerParser.getFiles(nodePath);
		for (int i = 0; i < srvFiles.length; i++) {
			String clientPath = srvFiles[i].getClientPath() + srvFiles[i].getName();
			String backupPath=PropertiesUtil.getPropertyByKey("backupPath")+File.separator+serverVerstion+File.separator+srvFiles[i].getName();
			try {
				logger.info("回滚本地文件路径-->"+clientPath);
				logger.info("回滚备份文件路径："+backupPath);
				File backupFile = new File(backupPath);
				if(!backupFile.exists()){
					logger.error("回滚备份本地文件不存在："+backupPath);
					return false;
				}
				copyFiles(backupPath,srvFiles[i].getClientPath());
			} catch (Exception e) {
				logger.error("回滚失败："+clientPath+":"+e);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 文件复制
	 * @param oldPath
	 * @param newPath
	 */
	private void copyFiles(String oldPath,String newPath){
		try {
			File oldFile=new File(oldPath);
			if(oldFile.isDirectory()){
				File[] files=oldFile.listFiles();
				for (File file : files) {
					logger.info("进入子文件："+file.getPath());
					copyFiles(file.getPath(), newPath+File.separator+oldFile.getName());
				}
			}else{
				File newFile=new File(newPath);
				if(!newFile.exists()){
					newFile.mkdirs();
					logger.info("创建文件夹："+newPath);
				}
				File file=new File(newPath+File.separator+oldFile.getName());
				if(file.exists()){
					logger.info("删除文件："+file.getPath());
					file.delete();
				}
//				BufferedInputStream in=null;
//				BufferedOutputStream out=null;
//				try {
//					in=new BufferedInputStream(new FileInputStream(oldFile));
//					out=new BufferedOutputStream(new FileOutputStream(file));
//					byte[] b=new byte[2048];
//					int len = 0;
//					while ((len = in.read(b))!=-1) {
//						out.write(b, 0, len);						
//					}
//					logger.info("复制文件："+oldPath);
//				} catch (Exception e) {
//					logger.error("读写文件失败："+oldPath+e);
//					throw new Exception(e);
//				}finally{
//					if(out!=null){
//						out.close();
//					}
//					if(in!=null){
//						in.close();
//					}
//				}
				FileUtil.copyFileUsingJava7Files(oldFile, file);
				logger.info("复制文件："+oldPath);
			}
		} catch (Exception e) {
			logger.error("备份失败："+oldPath+":"+e);
		}
	}
	public static void main(String[] args) {
		UpdateFile updateFile = new UpdateFile();
		updateFile.uploadFile();
		System.out.println("更新完毕");
//		try {
//			Runtime rn = Runtime.getRuntime();
//			String updatePath=PropertiesUtil.getPropertyByKey("updatePath");
//			System.out.println("打开程序"+updatePath);
////			rn.exec(updatePath);
//			Desktop.getDesktop().open(new File(updatePath));
//		} catch (Exception e) {
//			System.out.println("打开程序失败"+e);
//		}
	}

	public boolean isUpdate() {
		return isUpdate;
	}

	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
}
