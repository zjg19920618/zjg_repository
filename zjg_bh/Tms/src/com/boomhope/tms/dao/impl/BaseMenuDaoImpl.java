package com.boomhope.tms.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IBaseMenuDao;
import com.boomhope.tms.entity.BaseMenu;

@Repository(value="baseMenuDao")
public class BaseMenuDaoImpl extends BaseDao<BaseMenu> implements IBaseMenuDao{

	@Override
	public List<Integer> getBaseMenuList(int roleCode) {
		String hql = "select menu.menuId from BaseMenu as menu where menu.menuId in (select m.baseMenu.menuId from BaseRoleMenu as m where m.baseRole.roleCode="+roleCode+")";
		List<Integer> list = (List<Integer>) findList(hql);
		return list;
	}

	@Override
	public List<BaseMenu> getBaseMenuList() {
		return this.findAll();
	}

	@Override
	public List<BaseMenu> getBaseMenuListByParent(int parentId) {
		String hql = " from BaseMenu as a where a.parentId="+parentId+" order by a.ordBy asc";
		List<BaseMenu> list = (List<BaseMenu>) findList(hql);
		return list;
	}
}
