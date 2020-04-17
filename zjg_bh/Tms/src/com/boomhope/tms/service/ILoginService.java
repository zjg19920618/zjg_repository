package com.boomhope.tms.service;

import java.util.List;

import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.entity.RoleMenuView;



public interface ILoginService {
	
	
	/**
	 * 获取user
	 * @return
	 */
	public BaseUser getUser(String username,String pwd);
	
	/**
	 * 获取登录用户所对应的菜单
	 * @param username
	 * @param parentId
	 * @return
	 */
	public List<RoleMenuView> getMenu(String username,int parentId);
	
	/**
	 * 修改pwd
	 * @param username
	 * @param parentId
	 * @return
	 */

	public void editPwd(BaseUser baseUser);
	/**
	 * 添加最后一次登录时间
	 * */
	public void saveLastTime(BaseUser user);


	
}
