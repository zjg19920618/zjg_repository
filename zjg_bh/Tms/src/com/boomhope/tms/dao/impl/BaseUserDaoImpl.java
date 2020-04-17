package com.boomhope.tms.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IBaseUserDao;
import com.boomhope.tms.entity.BaseRole;
import com.boomhope.tms.entity.BaseUnit;
import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.RoleMenuView;

@Repository(value = "baseUserDao")
public class BaseUserDaoImpl extends BaseDao<BaseUser> implements IBaseUserDao {

	@Override
	public BaseUser getUser(String username, String pwd) {
		String hql = " from BaseUser user where user.username ='" + username + "' and user.pwd ='" + pwd + "'";
		BaseUser user = (BaseUser) this.findOne(hql);
		return user;
	}

	@Override
	public List<RoleMenuView> getRoleMenuView(String username, Integer parentId) {
		String hql = " from RoleMenuView as view where 1=1";
		if (username != null && !"".equals(username)) {
			hql += " and view.roleMenuViewId.username='" + username + "'";
		}
		if (parentId != null && !"".equals(parentId)) {
			hql += " and view.roleMenuViewId.parentId=" + parentId;
		}
		hql += " order by view.roleMenuViewId.ordBy";
		List<RoleMenuView> list = (List<RoleMenuView>) findList(hql);
		return list;
	}

	@Override
	public List<BaseUser> findBaseuser(Page page, Map<String, String> map) {
		String hql = "from BaseUser where 1=1 ";
		String name = map.get("name");
		if (name != null && !"".equals(name)) {
			hql += "and name like '%" + name + "%'";
		}
		hql += " order by createDate desc";
		List<BaseUser> list = (List<BaseUser>) findList(hql, map, page, null);
		return list;
	}

	@Override
	public void delBaseuser(String username) {
		String hql = " delete from BaseUser as user where user.username='" + username + "'";
		this.executeHql(hql);
	}

	@Override
	public void delBaseuser1(String username) {
		String hql = " delete from BaseUserRoleMap as user where user.baseUser.username='" + username + "'";
		this.executeHql(hql);
	}
	
	@Override
	public void editPwd(BaseUser baseUser) {
		String hql = "update BaseUser  set pwd='" + baseUser.getPwd() + "' where username='" + baseUser.getUsername()
				+ "'";
		this.executeHql(hql);
	}

	@Override
	public void saveBaseUser1(BaseUser baseUser) {
		String hql = "update BaseUser  set name='" + baseUser.getName() + "' ,email = '"+baseUser.getEmail()+"',phone = '"+baseUser.getPhone()+"', status = '"+baseUser.getStatus()+"', updateDate = '"+baseUser.getUpdateDate()+"' where username='" + baseUser.getUsername()
		+ "'";
		this.executeHql(hql);
	}

	@Override
	public List<BaseUser> findBeanEmail1(String username, String email) {
		String hql = "from BaseUser where email = '"+email+"'and username <> '"+username+"' ";
		return (List<BaseUser>) this.findList(hql);
	}

	@Override
	public List<BaseRole> findBaseRoleTest1(String roleName, int roleCode) {
		String hql = "from BaseRole where roleName = '"+roleName+"' and roleCode <> '"+roleCode+"'";
		return (List<BaseRole>) this.findList(hql);
	}

	@Override
	public void saveLastTime(BaseUser user) {
		String hql = "update BaseUser set lastTime = '"+user.getLastTime()+"' where username = '"+user.getUsername()+"'";
		this.executeHql(hql);
	}

	@Override
	public List<BaseUnit> findBaseUnitCN2(String unitCode, String unitName) {
		String hql = "from BaseUnit where unitName = '"+unitName+"' and  unitCode <> '"+unitCode+"'";
		return (List<BaseUnit>) this.findList(hql);
	}

	@Override
	public BaseUnit findcreatDate(String unitCode) {
		String hql = "from BaseUnit where unitCode = '"+unitCode+"'";
		return (BaseUnit) this.findOne(hql);
	}

}
