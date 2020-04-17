package com.boomhope.tms.dao;

import com.boomhope.tms.entity.BaseRole;

public interface IBaseRoleMenuDao extends IBaseDao<BaseRole>{

	/**
	 * 删除角色菜单表中指定角色数据
	 * @param roleCode
	 */
	public void delRoleMenuByRoleCode(int roleCode);
	
	
	
}
