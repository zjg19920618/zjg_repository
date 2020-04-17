package com.boomhope.tms.dao;

import java.util.List;

import com.boomhope.tms.entity.BaseRole;
import com.boomhope.tms.entity.BaseUnit;
import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.entity.BaseUserRoleMap;

public interface IBaseUserRoleMapDao extends IBaseDao<BaseUserRoleMap>{

	/**
	 * 保存角色
	 * @param userRole
	 */
	public void saveUserRole(BaseUserRoleMap userRole);
	
	/**
	 * 删除用户角色表中指定的角色
	 * @param roleCode
	 */
	public void delUserRoleMapByRoleCode(int roleCode);
	/**
	 * 根据userName删除用户角色表中所有的角色
	 * @param roleCode
	 */
	public void delUserRoleMapByUserName(String userName);
	
	/**
	 * 机构判重
	 * @param unitCode
	 * @param unitName
	 */
	public List<BaseUnit> findBaseUnitCN(String unitCode);
	public List<BaseUnit> findBaseUnitCN1(String unitName);
	
	/**
	 * 角色（名称）判重
	 * @param csbh
	 */
	public List<BaseRole> findBaseRoleTest(String roleName);

	/**
	 * 用户（邮箱）判重
	 * @param username
	 */
	public List<BaseUser> findBeanEmail(String username,String Email);

	/**
	 * 用户名/邮箱(添加时)判重
	 * @param username
	 */
	public List<BaseUser> findBuName(String username);
	public List<BaseUser> findBuName1(String email);
}
