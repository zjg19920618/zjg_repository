package com.boomhope.tms.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IBaseDictDao;
import com.boomhope.tms.entity.BaseDict;
@Repository(value="baseDictDao")
public class BaseDictDaoImpl extends BaseDao<BaseDict> implements IBaseDictDao {

	@Override
	public List<BaseDict> getDict(String groupName) {
		String hql = " from BaseDict as dict where dict.groupName='"+groupName+"'";
		List<BaseDict> list = (List<BaseDict>) findList(hql);
		return list;
	}
	
}
