package com.boomhope.tms.dao;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.BaseRole;
import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.entity.Page;

public interface IBaseRoleDao extends IBaseDao<BaseRole>{
	
	/**
	 * 获取用户对应的角色
	 * @param username
	 * @return
	 */
	public List<BaseRole> getUserRole(String username);
	
	/**
	 * 获取角色
	 * @param page
	 * @param map
	 * @param sort
	 * @return
	 */
	public List<BaseRole> findBaseRoleList(Page page, Map<String, String> map,String sort) ;

	public void UpdateBaseRole(BaseRole baseRole);


	
}
