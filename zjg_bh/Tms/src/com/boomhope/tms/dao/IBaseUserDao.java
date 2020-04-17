package com.boomhope.tms.dao;



import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.BaseRole;
import com.boomhope.tms.entity.BaseUnit;
import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.RoleMenuView;


@SuppressWarnings("all")
public interface IBaseUserDao extends IBaseDao<BaseUser>{
	

	/**
	 * 获取用户
	 * @param username
	 * @param pwd
	 * @return
	 */
	public BaseUser getUser(String username,String pwd);
	
	/**
	 * 获取登录用户对应的菜单树
	 * @param username
	 * @param parentId
	 * @return
	 */
	public List<RoleMenuView> getRoleMenuView(String username,Integer parentId);
	
	
	/**
	 * 查询用户信息
	 * @param page
	 * @param map
	 * @return
	 */
	public List<BaseUser> findBaseuser(Page page,Map<String,String> map);
	
	/**
	 * 删除用户信息
	 * @param username
	 */
	public void delBaseuser(String  username) ;
	
	/**
	 * 密码修改
	 * @param username
	 */
	public void editPwd(BaseUser baseUser);

	/**
	 * 更新用户信息
	 * @param username
	 */
	public void saveBaseUser1(BaseUser baseUser);

	public List<BaseUser> findBeanEmail1(String username, String email);

	public List<BaseRole> findBaseRoleTest1(String roleName, int roleCode);

	public void saveLastTime(BaseUser user);

	public List<BaseUnit> findBaseUnitCN2(String unitCode, String unitName);

	public BaseUnit findcreatDate(String unitCode);
	
	/**
	 * 删除用户的同时删除用户角色
	 * @param username
	 */
	public void delBaseuser1(String username);
	
	
}
