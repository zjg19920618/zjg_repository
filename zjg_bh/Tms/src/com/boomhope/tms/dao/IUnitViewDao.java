package com.boomhope.tms.dao;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.UnitView;

public interface IUnitViewDao extends IBaseDao<UnitView>{

	/**
	 * 获取机构信息
	 * @param page
	 * @param map
	 * @return
	 */
	public List<UnitView> getUnitViewList(Page page,Map<String,String> map);
	
	/**
	 * 获取机构信息
	 * @param map
	 * @return
	 */
	public List<UnitView> getUnitViewList(Map<String,String> map);
}
