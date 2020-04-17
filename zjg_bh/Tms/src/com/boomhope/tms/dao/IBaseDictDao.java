package com.boomhope.tms.dao;

import java.util.List;

import com.boomhope.tms.entity.BaseDict;


public interface IBaseDictDao extends IBaseDao<BaseDict>{
	
	/**
	 * 获取某一分组字典
	 * @param groupName
	 * @return
	 */
	public List<BaseDict> getDict(String groupName);
}
