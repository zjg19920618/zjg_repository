package com.boomhope.tms.quartz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.boomhope.tms.entity.TCnapsBkinfo;
import com.boomhope.tms.service.ITCnapsBkinfoService;
import com.boomhope.tms.util.UpdateBankFile;

/**
 * 
 * Title:图片文件定时上传
 * Description:
 * @author mouchunyue
 * @date 2016年9月29日 上午10:09:18
 */
public class UploadBankInfoTask {

	Logger logger = Logger.getLogger(UploadBankInfoTask.class);
	@Value("#{configProperties['ftp.server.ip']}")
	private String ip;
	@Value("#{configProperties['ftp.server.port']}")
	private int port;
	@Value("#{configProperties['ftp.user.name']}")
	private String userName;
	@Value("#{configProperties['ftp.user.password']}")
	private String password;
	@Value("#{configProperties['BANKINFO_PATH_SC']}")
	private String serverPath;
	@Value("#{configProperties['FTP_LOCAL_PATH']}")
	private String localPath;
	
	private ITCnapsBkinfoService tCnapsBkinfoService;
	
	@Resource(name = "TCnapsBkinfoServiceImpl")
	public void settCnapsBkinfoService(ITCnapsBkinfoService tCnapsBkinfoService) {
		this.tCnapsBkinfoService = tCnapsBkinfoService;
	}
	public void uploadBankInfos(){
		File file=UpdateBankFile.downloadBankFile(ip, port, userName, password, serverPath, localPath);
		if(file==null){
			return;
		}
		logger.info("要解析的文件"+file);
		
		InputStreamReader reader=null;
		TCnapsBkinfo bankInfo=null;
		List<TCnapsBkinfo> list=new ArrayList<TCnapsBkinfo>();
		try{
		reader = new InputStreamReader(new FileInputStream(file), "GBK");
		logger.info("读取文件流"+reader);
		BufferedReader buffer = new BufferedReader(reader);
		String str = null;
		int i=1;
		while ((str = buffer.readLine()) != null) {
			if(i==1||"".equals(str)){
				i++;
				continue;
			}
			String[] read=str.split("\\|\\|");
			bankInfo = new TCnapsBkinfo();
			bankInfo.setBankNo(read[0].trim());
			bankInfo.setBankType(read[1].trim());
			bankInfo.setClrBankNo(read[2].trim());
			bankInfo.setNodeCode(read[3].trim());
			bankInfo.setSuprList(read[4].trim());
			bankInfo.setPbcCode(read[5].trim());
			bankInfo.setCenterCode(read[6].trim());
			bankInfo.setLname(read[7].trim());
			bankInfo.setSname(read[8].trim());
			bankInfo.setAddr(read[9].trim());
			bankInfo.setPostCode(read[10].trim());
			bankInfo.setTel(read[11].trim());
			bankInfo.setEmail(read[12].trim());
			bankInfo.setFlag(read[13].trim());
			bankInfo.setInstType(read[14].trim());
			bankInfo.setLegalPer(read[15].trim());
			bankInfo.setBearBankNo(read[16].trim());
			bankInfo.setSignFlag(read[17].trim());
			bankInfo.setIssueno(read[18].trim());
			bankInfo.setEffdate(read[19].trim());
			bankInfo.setInvdate(read[20].trim());
			bankInfo.setAlttype(read[21].trim());
			logger.info("银行信息"+bankInfo);
			list.add(bankInfo);
			
		}
	}catch(FileNotFoundException e) {
		logger.error("文件找不到"+e);
	}catch (IOException e) {
		logger.error("读取文件流异常"+e);
	}catch(Exception e1){
		logger.error(e1);
	}finally{
		
		try {
			reader.close();
		} catch (IOException e) {
			logger.error("关闭流失败"+e);
		}
		
	}
	try {
		tCnapsBkinfoService.updateBankInfoList(list);
	} catch (Exception e) {
		logger.error("更新文件失败"+e);
	}
}
	public static void main(String[] args) {
		UploadBankInfoTask uploadFilesServise = new UploadBankInfoTask();
		uploadFilesServise.uploadBankInfos();
	}
}
