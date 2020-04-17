package com.boomhope.tms.dao;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.MacMachinePic;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Page;

public interface IMacMachinePicDao extends IBaseDao<MacMachinePic>{
	/**
	 * 查看图片信息
	 * @param page
	 * @param map
	 * @return
	 */
	public List<MacMachinePic> findMachinePicList(Map<String,String> map);
	
	
	/**
	 * 保存机器维护信息
	 * @param manu
	 */
	public void saveMacMachinePic(MacMachinePic macMachinePic);
	
	/**
	 * 删除机器维护信息
	 * @param manuCode
	 */
	public void delMacMachinePic(String machineCode,String pic);

	/**
	 * 图片上传-图片名称判重
	 * @param macMachinePic
	 * @return
	 */
	public List<MacMachinePic> findMacMachinePic(MacMachinePic macMachinePic);
	
}
