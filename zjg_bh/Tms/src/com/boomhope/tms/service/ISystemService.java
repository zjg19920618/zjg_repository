package com.boomhope.tms.service;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.BaseDict;
import com.boomhope.tms.entity.BaseMenu;
import com.boomhope.tms.entity.BaseParameter;
import com.boomhope.tms.entity.BaseRole;
import com.boomhope.tms.entity.BaseRoleMenu;
import com.boomhope.tms.entity.BaseUnit;
import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.entity.BaseUserRoleMap;
import com.boomhope.tms.entity.Manu;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.UnitView;

public interface ISystemService {
	
	/**
	 * 获取用户信息
	 * @param page
	 * @param map
	 * @return
	 */
	public List<BaseUser> findBeseUserList(Page page,Map<String,String> map);
	
	/**
	 * 保存用户
	 * @param baseUser
	 */
	public void saveBaseUser(BaseUser baseUser);
	
	/**
	 * 删除用户
	 * @param username
	 */
	public void delBaseuser(String  username);
	
	/**
	 * 
	 * 获取全部角色
	 */
	public List<BaseRole> findBaseRole();
	
	/**
	 * 获取角色信息
	 * @param page
	 * @param map
	 * @return
	 */
	public List<BaseRole> findBaseRoleList(Page page,Map<String,String> map,String sort);
	
	/**
	 * 获取指定用户的角色
	 * @param username
	 * @return
	 */
	public List<BaseRole> getUserRole(String username);
	
	
	/**
	 * 保存用户角色
	 */
	public void saveUserRole(String userName,String[] roleCode);
	
	
	/**
	 * 获取某一分组字典
	 * @param groupName
	 * @return
	 */
	public List<BaseDict> getDict(String groupName);
	
	/**
	 * 保存角色
	 * @param baseRole
	 */
	public void saveRole(BaseRole baseRole);
	
	public void saveRole1(BaseRole baseRole);
	/**
	 * 获取角色对应的菜单
	 * @return
	 */
	public List<BaseMenu> getBaseMenuList(int roleCode);
	
	/**
	 * 获取全部菜单
	 * @return
	 */
	public List<BaseMenu> getBaseMenuList();
	
	/**
	 * 删除角色菜单
	 * @param roleCode
	 */
	public void delBaseRole(int roleCode);
	
	/**
	 * 获取机构信息表
	 * @param page
	 * @param map
	 * @return
	 */
	public List<UnitView> getUnitViewList(Page page,Map<String,String> map);
	
	/**
	 * 获取机构信息表
	 * @param map
	 * @return
	 */
	public List<UnitView> getUnitViewList(Map<String,String> map);
	
	/**
	 * 保存机构信息
	 * @param baseUnit
	 */
	public void saveBaseUnit(BaseUnit baseUnit);

	/**
	 * 删除机构信息
	 * @param baseUnit
	 */
	public void delBaseUnit(BaseUnit baseUnit);
	
	
	/**
	 * 保存角色菜单
	 * @param menuIds
	 * @param roleId
	 */
	public void saveRoleMenu(String[] menuIds,Integer roleId);
	
	
	/**
	 * 机构（编号、名称）判重
	 * @param unitCode
	 * @param unitName
	 */
	public List<BaseUnit> findBaseUnitCN(String unitCode);
	public List<BaseUnit> findBaseUnitCN1(String unitName);

	
	/**
	 * 获取指定参数
	 * @param type
	 * @return
	 */
	public List<BaseParameter> findBaseParameterByParentType(String type);

	
	
	/**
	 * 角色（名称）判重
	 * @param csbh
	 */
	public List<BaseRole> findBaseRoleTest(String roleName);
	
	/**
	 * 用户名判重
	 * @param username
	 */
	public List<BaseUser> findBuName(String username);
	public List<BaseUser> findBuName1(String email);
	
	/**
	 * 用户（邮箱）判重
	 * @param username
	 */
	public List<BaseUser> findBeanEmail(String username,String Email);

	/**
	 * 获取某一设备编号对应的机构信息
	 * @param machineNo
	 * @return
	 */
	public BaseUnit getBaseUintByMachineNo(String machineNo);
	
	

	/**
	 * 编辑unit
	 * @param unit
	 */
	public void editBaseUnit1(BaseUnit unit);

	/**
	 * 编辑用户
	 * @param baseUser
	 */
	public void saveBaseUser1(BaseUser baseUser);
	
	
	/**
	 * 根据条件查询所有用户
	 * 
	 */
	public List<BaseUser> findBeanEmail1(String username, String email);

	/**
	 * 根据条件查询所有角色
	 * 
	 */
	public List<BaseRole> findBaseRoleTest1(String roleName, int roleCode);
	
	/**
	 * 根据主键查询用户
	 * 
	 */
	public BaseUser findOne(String userName);
	
	/**
	 * 根据条件查询机械
	 * 机械管理判重用
	 */
	public List<BaseUnit> findBaseUnitCN2(String unitCode, String unitName);
	
	
	/**
	 * 根据型号查询机械
	 * 
	 */
	public BaseUnit findcreatDate(String unitCode);

	/**
	 * 添加角色
	 * 
	 */
	public void saveBaseRole(BaseUserRoleMap baseUserRoleMap);
	
	/**
	 * 给新建角色添加默认菜单
	 * 
	 */
	public void saveBaseRoleMenu(BaseRoleMenu baseRoleMenu);

	/**
	 * 根据区县查询本区银行
	 */
	public List<BaseUnit> getBaseUnitCityS(String districtCounty);

	/**
	 * 导出 - 机构查询
	 * @param map
	 * @return
	 */
	public List<UnitView> getUnitViewEXCEl(Map<String, String> map);

	/**
	 * 删除用户的同时删除用户角色
	 * @param username
	 */
	public void delBaseuser1(String username);
}
