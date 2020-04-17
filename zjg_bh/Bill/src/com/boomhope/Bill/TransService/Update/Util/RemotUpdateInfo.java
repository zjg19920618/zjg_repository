package com.boomhope.Bill.TransService.Update.Util;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
/**
 * 
 * Title:读取服务端版本文件，将信息放入该类，与LocalUpdateInfo类（本地版本信息类）作比较
 * Description:
 * @author wxm
 * @date 2016年9月24日 下午3:25:22
 */
public class RemotUpdateInfo {

	private File file = null;
	/** xml的document */
	private Document doc = null;
	
	Logger logger = Logger.getLogger(RemotUpdateInfo.class);
	
	/**
	 * 初始化
	 * @param f
	 */
	public RemotUpdateInfo(File f) {
		file = f;
		parse();
	}
	
	/**
	 * 解析文件
	 */
	private void parse() {
		try {
			SAXReader reader = new SAXReader();
			doc = reader.read(file);
			logger.error("解析："+file.getName());
		} catch (Exception e) {
			logger.error("解析失败："+file.getName()+"."+e);
		}
	}
	
	/**
	 * 获取版本
	 * @return
	 */
	public String getVerstion() {
		try {
			List lst = doc.selectNodes("Info/Version");
			Element el = (Element) lst.get(0);
			String version=el.getText();
			logger.debug("获取服务器版本号："+version);
			return version;
		} catch (Exception e) {
			logger.error("获取服务器版本号失败"+e);
			return null;
		}
	}
	
	/**
	 * 获取操作类型 update 升级  return 回滚
	 * @return
	 */
	public String getOperation() {
		try {
			List lst = doc.selectNodes("Info/Operation");
			Element el = (Element) lst.get(0);
			String operation=el.getText();
			logger.debug("获取服务器操作："+operation);
			return operation;
		} catch (Exception e) {
			logger.error("获取服务器操作失败"+e);
			return null;
		}
	}
	
	/**
	 * 获取是否关机信息0-不关，1-关闭
	 * @return
	 */
//	public String getDefault() {
//		try {
//			List lst = doc.selectNodes("Info/Version");
//			Element el = (Element) lst.get(0);
//			String version=el.getText();
//			logger.debug("获取本地版本号："+version);
//			return version;
//		} catch (Exception e) {
//			logger.error("获取版本号失败"+e);
//			return null;
//		}
//	}
	
	/**
	 * 获取子文件
	 * @return
	 */
	public UpdFile[] getFiles(String nodePath) {
		try {
			List file = doc.selectNodes(nodePath);
			if(file.size()==0){
				logger.debug(nodePath+"节点不存在");
				return null;
			}
			List lst = ((Element) file.get(0)).elements();
			if (lst.size() == 0) {
				logger.debug(nodePath+"节点中获取0个文件");
				return null;
			}
			UpdFile files[] = new UpdFile[lst.size()];
			for (int i = 0; i < lst.size(); i++) {
				Element el = (Element) lst.get(i);
				List childs = el.elements();
				Element name = (Element) childs.get(0);// Name
				Element path = (Element) childs.get(1);// Path
				Element clientPath = (Element) childs.get(2);// clientPath
				Element ver = (Element) childs.get(3);// Version
				files[i] = new UpdFile(name.getText());
				if ("File".equals(el.getName())) {
					files[i].setType(0);// 文件
				} else {
					files[i].setType(1);// 目录
				}
				files[i].setPath(path.getText());
				files[i].setClientPath(clientPath.getText());
				files[i].setVersion(ver.getText());
			}
			return files;
		} catch (Exception e) {
			logger.error(nodePath+"获取节点下的文件失败"+e);
			return null;
		}
	}

}
