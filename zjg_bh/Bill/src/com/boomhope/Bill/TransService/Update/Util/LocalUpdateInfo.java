package com.boomhope.Bill.TransService.Update.Util;

import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 * Title:本地版本信息类
 * Description:
 * @author wxm
 * @date 2016年9月24日 下午3:25:46
 */
public class LocalUpdateInfo {
	//linux采用     PropertiesUtil.getInstance().projectPath+"/WEB-INF/classes/autoupdate.xml";
	//windows 采用  "C:/autoupdate.xml"
	public static String cfgFile = PropertiesUtil.getPropertyByKey("clientUpdate");
	private static LocalUpdateInfo config = null;
	Logger logger = Logger.getLogger(LocalUpdateInfo.class);

	/** xml的document */
	private Document doc = null;
	
	/**
	 * 初始化
	 * @return
	 */
	public static LocalUpdateInfo getInstance() {
		if (config == null) {
			config = new LocalUpdateInfo();
		}
		return config;
	}
	/**
	 * 解析文件
	 */
	private LocalUpdateInfo() {
		try {
			SAXReader reader = new SAXReader();
			doc = reader.read(cfgFile);
			logger.debug(cfgFile+"文件解析通过");
		} catch (Exception e) {
			logger.error("读取本地文件报错。");
		}
	}
	
	/**
	 * 刷新文件
	 */
	public void refresh() {
		config = new LocalUpdateInfo();
		logger.debug("刷新LocalUpdateInfo类");
	}
	
	/**
	 * 获取版本号
	 * @return
	 */
	public String getVerstion() {
		if (config == null) {
			logger.debug("未加载：getInstance方法");
			return "";
		}
		try {
			List lst = doc.selectNodes("Info/Version");
			Element el = (Element) lst.get(0);
			String version=el.getText();
			logger.debug("获取本地版本号："+version);
			return version;
		} catch (Exception e) {
			logger.error("获取版本号失败"+e);
			return null;
		}
	}
	
	/**
	 * 获取升级文件
	 * @return
	 */
	public UpdFile[] getFiles(String nodePath) {
		if (config == null) {
			logger.debug("未加载：getInstance方法");
			return null;
		}
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
