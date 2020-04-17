package com.boomhope.tms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.boomhope.tms.dao.IBaseUserDao;
import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.entity.RoleMenuView;
import com.boomhope.tms.service.ILoginService;
import com.boomhope.tms.util.MD5Util;

@SuppressWarnings("unused")
@Service("loginService")
public class LoginServiceImpl implements ILoginService {

	private static final Log log = LogFactory.getLog(LoginServiceImpl.class);
 	
	private IBaseUserDao baseUserDao;
    
	@Resource(name="baseUserDao")
	public void setUserDao(IBaseUserDao baseUserDao) {
		this.baseUserDao = baseUserDao;
	}

	
	/**
	 * 获取用户
	 */
	@Override
	public BaseUser getUser(String username, String pwd) {
		pwd = MD5Util.string2MD5(pwd);
		BaseUser user = baseUserDao.getUser(username, pwd);
//		User user= new User();
//		user.setLoginName("admin");
//		user.setLoginPass("123");
		return user;
	}


	@Override
	public List<RoleMenuView> getMenu(String username,int parentId) {
		return baseUserDao.getRoleMenuView(username, parentId);
	}


	@Override
	public void editPwd(BaseUser baseUser) {
		 baseUserDao.editPwd(baseUser);
	}


	@Override
	public void saveLastTime(BaseUser user) {
		baseUserDao.saveLastTime(user);
		
	}

}
