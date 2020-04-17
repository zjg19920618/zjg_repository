package com.boomhope.tms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IBaseRoleDao;
import com.boomhope.tms.entity.BaseRole;
import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.entity.Page;
@Repository(value="baseRoleDao")
public class BaseRoleDaoImpl extends BaseDao<BaseRole> implements IBaseRoleDao{

	@Override
	public List<BaseRole> getUserRole(String username) {
		String hql = "  from BaseRole as role where role.roleCode in(select user.baseRole.roleCode from BaseUserRoleMap as user where user.baseUser.username='"+username+"')";
		
		List<BaseRole>  list = (List<BaseRole>) findList(hql);
		return list;
	}

	@Override
	public List<BaseRole> findBaseRoleList(Page page, Map<String, String> map,
			String sort) {
		String hql = " from BaseRole where 1=1";
		String roleName = map.get("roleName");
		if(roleName != null && !"".equals(roleName)){
			hql += "and roleName like '%" + roleName + "%'";
		}
		
		return (List<BaseRole>) this.findList(hql, new HashMap(), page, sort);
	}

	@Override
	public void UpdateBaseRole(BaseRole baseRole) {
		String hql = "update BaseRole set roleName = '"+baseRole.getRoleName()+"',roleDesc = '"+baseRole.getRoleDesc()+"',updateDate='"+baseRole.getUpdateDate()+"',updater='"+baseRole.getUpdater()+"'where roleCode='"+baseRole.getRoleCode()+"' ";
		this.executeHql(hql);
		
	}
	
}
