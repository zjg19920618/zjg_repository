package com.boomhope.tms.dao;

import java.util.List;

import com.boomhope.tms.entity.BaseMenu;

public interface IBaseMenuDao extends IBaseDao<BaseMenu>{

	/**
	 * 获取角色对应的菜单
	 * @return
	 */
	public List<Integer> getBaseMenuList(int roleCode) ;
	
	/**
	 * 获取全部菜单
	 * @return
	 */
	public List<BaseMenu> getBaseMenuList();
	
	/**
	 * 获取指定层级的菜单
	 * @param parentId
	 * @return
	 */
	public List<BaseMenu> getBaseMenuListByParent(int parentId);
}
