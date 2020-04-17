package com.boomhope.tms.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IBaseUserRoleMapDao;
import com.boomhope.tms.entity.BaseRole;
import com.boomhope.tms.entity.BaseUnit;
import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.entity.BaseUserRoleMap;

@Repository(value="baseUserRoleMapDao")
public class BaseUserRoleMapDaoImpl extends BaseDao<BaseUserRoleMap> implements IBaseUserRoleMapDao{

	@Override
	public void saveUserRole(BaseUserRoleMap userRole) {
		this.save(userRole);
		
	}

	@Override
	public void delUserRoleMapByRoleCode(int roleCode) {
		String hql = "delete from BaseRoleMenu a where a.baseRole.roleCode="+roleCode;
		executeHql(hql);
	}

	@Override
	public List<BaseUnit> findBaseUnitCN(String unitCode) {
		String hql = "from BaseUnit where unitCode='"+unitCode+"'";
		return (List<BaseUnit>) this.findList(hql);
	}

	@Override
	public List<BaseUnit> findBaseUnitCN1(String unitName) {
		String hql = "from BaseUnit where  unitName = '"+unitName+"'";
		return (List<BaseUnit>) this.findList(hql);
	}
	
	@Override
	public List<BaseRole> findBaseRoleTest(String roleName) {
		String hql = "from BaseRole where roleName='"+roleName+"'";
		return (List<BaseRole>) this.findList(hql);
	}

	@Override
	public List<BaseUser> findBuName(String username) {
		String hql = "from BaseUser where username = '"+username+"'" ;
		return (List<BaseUser>) this.findList(hql);
	}
	
	@Override
	public List<BaseUser> findBuName1(String email) {
		String hql = "from BaseUser where email = '"+email+"'" ;
		return (List<BaseUser>) this.findList(hql);
	}
	
	@Override
	public List<BaseUser> findBeanEmail(String username,String Email) {
		String hql = "from BaseUser where email = '"+Email+"' or username = '"+username+"'";
		return (List<BaseUser>) this.findList(hql);
	}

	@Override
	public void delUserRoleMapByUserName(String userName) {
		String hql = "delete from BaseUserRoleMap a where a.baseUser.username='"+userName+"'";
		executeHql(hql);
	}

	
}
