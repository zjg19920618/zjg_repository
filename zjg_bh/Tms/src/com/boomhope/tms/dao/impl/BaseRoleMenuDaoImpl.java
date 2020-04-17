package com.boomhope.tms.dao.impl;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IBaseRoleMenuDao;
import com.boomhope.tms.entity.BaseRole;

@Repository(value="baseRoleMenuDao")
public class BaseRoleMenuDaoImpl extends BaseDao<BaseRole> implements IBaseRoleMenuDao{

	@Override
	public void delRoleMenuByRoleCode(int roleCode) {
		String hql = "delete from BaseRoleMenu a where a.baseRole.roleCode="+roleCode;
		this.executeHql(hql);	
	}

}
