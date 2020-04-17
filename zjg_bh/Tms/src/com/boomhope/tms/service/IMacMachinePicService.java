package com.boomhope.tms.service;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.MacMachinePic;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Page;

public interface IMacMachinePicService {
	/**
	 * 查询图片
	 * @param page
	 * @param map
	 * @return
	 */
	public List<MacMachinePic> findMachinePicList(Map<String,String> map);
	
	/**
	 * 保存图片
	 * @param manuCode
	 */
	public void saveMacMachinePic(MacMachinePic macMachinePic);
	

	/**
	 * 删除图片
	 * @param manuCode
	 */
	public void delMacMachinePic(String machineCode,String pic);
	
	/**
	 * 添加图片-图片名称重复验证
	 * @param macMachinePic
	 * @return
	 */
	public List<MacMachinePic> findMacMachinePic(MacMachinePic macMachinePic);
}
