package com.boomhope.tms.quartz;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.boomhope.tms.transaction.ConfigReader;
import com.boomhope.tms.util.DateUtil;
import com.boomhope.tms.util.FtpFileUtils;
import com.boomhope.tms.util.FtpUtils;
import com.boomhope.tms.util.HHShjdUtil;
import com.boomhope.tms.util.ShjdFileBean;
import com.boomhope.tms.util.TextFileReader;

/**
 * 
 * Title:图片文件定时上传
 * Description:
 * @author mouchunyue
 * @date 2016年9月29日 上午10:09:18
 */
public class UploadFilesTask {

	Logger logger = Logger.getLogger(UploadFilesTask.class);
	@Value("#{configProperties['Ftp.host']}")
	private String host;
	@Value("#{configProperties['Ftp.port']}")
	private int port;
	@Value("#{configProperties['Ftp.userName']}")
	private String userName;
	@Value("#{configProperties['Ftp.password']}")
	private String password;
	@Value("#{configProperties['Ftp.serverPath']}")
	private String serverPath;
	@Value("#{configProperties['Ftp.sysId']}")
	private String sysId;
	@Value("#{configProperties['Ftp.path']}")
	private String path;
	
	@Value("#{configProperties['FTP_SAVE_PATH_KH']}")
	private String FTP_SAVE_PATH_KH;
	@Value("#{configProperties['FTP_KH_sysId']}")
	private String FTP_KH_sysId;
	
	@Value("#{configProperties['ftp.huihuaSysId']}")
	private String huihuaSysId;
	@Value("#{configProperties['ftp.HHCXSysId']}")
	private String hhCXSysId;
	@Value("#{configProperties['ftp.huihuaPath']}")
	private String huihuaPath;
	@Value("#{configProperties['huihuaCiriId']}")
	private String huihuaCiriId;
	@Value("#{configProperties['ftp.cardkhsysId']}")
	private String cardKhsysId;
	@Value("#{configProperties['ftp.cardkhPath']}")
	private String cardKHPath;
	
	public void uploadFiles(){
		uploadXHFiles();
		uploadKHFiles();
		uploadHuiHuaFiles();
		uploadHHCXFiles();
		uploadCardKHFiles();
	}
	
	public boolean uploadXHFiles(){
		
		FTPClient ftpClient = new FTPClient();
		//如果参数为null,说明是定时任务调用，不为空，为博宏P端操作，
		String	dateTime = DateUtil.getDateTime("yyyyMMdd");
		//serverPath = serverPath+dateTime;  
		String p = path;
		p = p+dateTime+"/"; //本地文件路径
		boolean result = true;
		try {
			
			/**
			 * 连接FTP
			 */
			logger.info("开始连接FTP");
			result = FtpUtils.loginFtp(ftpClient, host, port, userName, password);
			if(result){
				logger.info("连接FTP成功！");
				logger.info("路径为---》"+p);
				File file = new File(p);//path
				File[] listFiles = file.listFiles();
				for (File file2 : listFiles) {
					if(!file2.isDirectory()){
						String fileName = file2.getName();
						if(!fileName.contains(".flg")){
							FileInputStream input = new FileInputStream(file2);
							result = FtpUtils.uploadFiles(ftpClient, serverPath, dateTime+"/"+sysId, fileName, input);
							if(!result){
								logger.error("文件"+fileName+"上传失败！");
								//createFile(path,dateTime,sysId);
								//break;
							}else{
								logger.info("文件"+fileName+"上传成功！");
							}
						}
					}
				}
			}else{
				createFile(p,dateTime,sysId);
				logger.error("连接FTP失败！");
				return result;
			}
			ftpClient.logout();
		} catch (Exception e) {
			logger.error("程序异常", e);
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
	
	/**
	 *银行卡开户事后图片监管
	 * @return
	 */
	public boolean uploadCardKHFiles(){
		FTPClient ftpClient = new FTPClient();
		//如果参数为null,说明是定时任务调用，不为空，为博宏P端操作，
		String	dateTime = DateUtil.getDateTime("yyyyMMdd");
		//serverPath = serverPath+dateTime;  
		String p = cardKHPath;
		p = p+dateTime+"/"+cardKhsysId+"/"; //本地文件路径
		boolean result = true;
		try {
			
			/**
			 * 连接FTP
			 */
			logger.info("开始连接FTP");
			result = FtpUtils.loginFtp(ftpClient, host, port, userName, password);
			if(result){
				logger.info("连接FTP成功！");
				logger.info("路径为---》"+p);
				File file = new File(p);//path
				File[] listFiles = file.listFiles();
				createFile(p,dateTime,sysId);
				for (File file2 : listFiles) {
					if(!file2.isDirectory()){
						String fileName = file2.getName();
//						if(!fileName.contains(".flg")){
							FileInputStream input = new FileInputStream(file2);
							result = FtpUtils.uploadFiles(ftpClient, serverPath, dateTime+"/"+sysId, fileName, input);
							if(!result){
								logger.error("文件"+fileName+"上传失败！");
								//createFile(path,dateTime,sysId);
								//break;
							}else{
								logger.info("文件"+fileName+"上传成功！");
							}
//						}
					}
				}
			}else{
				createFile(p,dateTime,sysId);
				logger.error("连接FTP失败！");
				return result;
			}
			ftpClient.logout();
		} catch (Exception e) {
			logger.error("程序异常", e);
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
	
	/**
	 *开户事后图片监管
	 * @return
	 */
	public boolean uploadKHFiles(){
		FTPClient ftpClient = new FTPClient();
		//如果参数为null,说明是定时任务调用，不为空，为博宏P端操作，
		String	dateTime = DateUtil.getDateTime("yyyyMMdd");
		//serverPath = serverPath+dateTime;  
		String p = FTP_SAVE_PATH_KH;
		p = p+dateTime+"/"; //本地文件路径
		boolean result = true;
		try {
			
			/**
			 * 连接FTP
			 */
			logger.info("开始连接FTP");
			result = FtpUtils.loginFtp(ftpClient, host, port, userName, password);
			if(result){
				logger.info("连接FTP成功！");
				logger.info("路径为---》"+p);
				File file = new File(p);//path
				File[] listFiles = file.listFiles();
				createFile(p,dateTime,sysId);
				for (File file2 : listFiles) {
					if(!file2.isDirectory()){
						String fileName = file2.getName();
//						if(!fileName.contains(".flg")){
							FileInputStream input = new FileInputStream(file2);
							result = FtpUtils.uploadFiles(ftpClient, serverPath, dateTime+"/"+sysId, fileName, input);
							if(!result){
								logger.error("文件"+fileName+"上传失败！");
								//createFile(path,dateTime,sysId);
								//break;
							}else{
								logger.info("文件"+fileName+"上传成功！");
							}
//						}
					}
				}
			}else{
				createFile(p,dateTime,sysId);
				logger.error("连接FTP失败！");
				return result;
			}
			ftpClient.logout();
		} catch (Exception e) {
			logger.error("程序异常", e);
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
	
	/**
	 * 汇划转账事后监督
	 */
	private boolean uploadHuiHuaFiles(){
		//下载非当日记账核心流水文件
		logger.info("开始下载非当日汇划业务记账核心流水文件");
		List<ShjdFileBean> list = null;
		String	dateTime = DateUtil.getDateTime("yyyyMMdd");
		logger.info("当前日期："+dateTime);
		
		try {
			//要下载的文件名全路径
			String fileName = "07497C"+dateTime;
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(ConfigReader.getConfig("ftp.server.ip"), Integer.parseInt(ConfigReader.getConfig("ftp.server.port")), ConfigReader.getConfig("ftp.user.name"),  ConfigReader.getConfig("ftp.user.password"), ConfigReader.getConfig("SHFILE_FTP_PATH")+fileName, ConfigReader.getConfig("SHFILE_LOCAL_PATH") + fileName);
			logger.info("下载非当日汇划业务记账核心流水文件成功");
			
			list = TextFileReader.paddingList(ConfigReader.getConfig("SHFILE_LOCAL_PATH") + fileName, ShjdFileBean.class);
			logger.info("核心流水文件数据转换实体类成功");
			
		} catch (Exception e) {
			
			logger.error("非当日汇划业务记账核心流水文件下载失败:"+e);
		}
		
		//修改图片流水并另存
		if(list!=null && list.size()>0){
			
			logger.info("汇划选择非当日到账笔数："+list.size());
			for (int i = 0; i < list.size(); i++) {
				try {
					
					logger.info("非当日汇划业务记账核心任务号:"+list.get(i).getTASK_IDTRNS());
					String state = "";
					if("0".equals(list.get(i).getState())){
						state="成功";
					}else if("1".equals(list.get(i).getState())){
						state="失败";
					}else if("2".equals(list.get(i).getState())){
						state="撤销";
					}
					logger.info("非当日汇划业务转账状态:"+list.get(i).getState()+state);
					
					String phototFileName = list.get(i).getTASK_IDTRNS();
					String date = dateTime.substring(0, 2) + phototFileName.substring(4,10);
					String taskId = list.get(i).getTASK_IDTRNS().substring(list.get(i).getTASK_IDTRNS().length()-5, list.get(i).getTASK_IDTRNS().length());
					String oldpath = FTP_SAVE_PATH_KH + date + "/" +huihuaSysId+"/"+huihuaCiriId+ "/"+phototFileName+".jpg";
					
					logger.info("汇划非当日原始事后图片路径："+oldpath);
					
					//判断文件是否存在
					File file = new File(oldpath);
					if(!file.exists()){
						//文件不存在
						logger.info("第" + i + "个原始事后图片不存在，生成新事后图片失败！");
						continue;
					}
					
					String newpath="";
					//修改并另存为新图片
					HHShjdUtil hp =new HHShjdUtil();
					if(list.get(i).getSvrJrnlNo()==null || "".equals(list.get(i).getSvrJrnlNo())){
						
						list.get(i).setSvrJrnlNo("");
						newpath = FTP_SAVE_PATH_KH + dateTime + "/" +huihuaSysId+"/"+dateTime+"_"+taskId+list.get(i).getTellerNo()+list.get(i).getBranchNo()+".jpg";
						logger.info("汇划非当日(无流水)新事后图片路径："+newpath);
					}else{
						
						newpath = FTP_SAVE_PATH_KH + dateTime + "/" +huihuaSysId+"/"+dateTime+list.get(i).getSvrJrnlNo()+".jpg";
						logger.info("汇划非当日(有流水)新事后图片路径："+newpath);
					}
					
					
					hp.updatePhotos(oldpath, newpath, list.get(i).getSvrJrnlNo());
					logger.info("第" + i + "个图片修改并保存成功");
				
				} catch (Exception e) {
					
					logger.error("修改第" + i + "个次日到账事后图片流水失败:"+e);
					continue;
				}

			}
		}else{
			
			logger.info("当前没有非当日转账流水记录");
		}
		
		
		logger.info("开始上传汇划转账事后监督图片");
		FTPClient ftpClient = new FTPClient();
		//如果参数为null,说明是定时任务调用，不为空，为博宏P端操作，
		//serverPath = serverPath+dateTime;  
		String p = huihuaPath;
		p = p+dateTime+"/"+huihuaSysId+"/"; //本地文件路径
		boolean result = true;
		try {
			
			/**
			 * 连接FTP
			 */
			logger.info("开始连接FTP");
			result = FtpUtils.loginFtp(ftpClient, host, port, userName, password);
			if(result){
				logger.info("连接FTP成功！");
				logger.info("路径为---》"+p);
				File file = new File(p);//path
				File[] listFiles = file.listFiles();
				createFile(p,dateTime,sysId);
				for (File file2 : listFiles) {
					if(!file2.isDirectory()){
						String fileName = file2.getName();
//						if(!fileName.contains(".flg")){
							FileInputStream input = new FileInputStream(file2);
							result = FtpUtils.uploadFiles(ftpClient, serverPath, dateTime+"/"+sysId, fileName, input);
							if(!result){
								logger.error("文件"+fileName+"上传失败！");
								//createFile(path,dateTime,sysId);
								//break;
							}else{
								logger.info("文件"+fileName+"上传成功！");
							}
//						}
					}
				}
			}else{
				createFile(p,dateTime,sysId);
				logger.error("连接FTP失败！");
				return result;
			}
			ftpClient.logout();
		} catch (Exception e) {
			logger.error("程序异常", e);
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
	
	/**
	 * 汇划撤销事后监督
	 */
	private boolean uploadHHCXFiles(){
		FTPClient ftpClient = new FTPClient();
		//如果参数为null,说明是定时任务调用，不为空，为博宏P端操作，
		String	dateTime = DateUtil.getDateTime("yyyyMMdd");
		String p = huihuaPath;
		p = p+dateTime+"/"+hhCXSysId+"/"; //本地文件路径
		boolean result = true;
		try {
			
			/**
			 * 连接FTP
			 */
			logger.info("开始连接FTP");
			result = FtpUtils.loginFtp(ftpClient, host, port, userName, password);
			if(result){
				logger.info("连接FTP成功！");
				logger.info("路径为---》"+p);
				File file = new File(p);//path
				File[] listFiles = file.listFiles();
				createFile(p,dateTime,hhCXSysId);
				for (File file2 : listFiles) {
					if(!file2.isDirectory()){
						String fileName = file2.getName();
						FileInputStream input = new FileInputStream(file2);
						result = FtpUtils.uploadFiles(ftpClient, serverPath, dateTime+"/"+hhCXSysId, fileName, input);
						if(!result){
							logger.error("文件"+fileName+"上传失败！");
						}else{
							logger.info("文件"+fileName+"上传成功！");
						}	
					}
				}
			}else{
				createFile(p,dateTime,hhCXSysId);
				logger.error("连接FTP失败！");
				return result;
			}
			ftpClient.logout();
		} catch (Exception e) {
			logger.error("程序异常", e);
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
	
	/**
	 * 创建事后监督.flg文件
	 * @param path
	 * @param dateTime
	 * @param sysId
	 * @throws Exception
	 */
	private void createFile(String path,String dateTime,String sysId) throws Exception{
		File file = new File(path+dateTime+"_"+sysId+".flg");
		if(!file.exists()){
			file.createNewFile();
		}
	}
	public static void main(String[] args) {
		UploadFilesTask uploadFilesServise = new UploadFilesTask();
		uploadFilesServise.uploadFiles();
	}
}
