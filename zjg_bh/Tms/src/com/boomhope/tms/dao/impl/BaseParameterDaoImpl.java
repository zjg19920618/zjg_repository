package com.boomhope.tms.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IBaseParameterDao;
import com.boomhope.tms.entity.BaseParameter;

@Repository(value="baseParameterDao")
public class BaseParameterDaoImpl extends BaseDao<BaseParameter> implements
		IBaseParameterDao {

	@Override
	public List<BaseParameter> findBaseParameterByParentType(String type) {
		String hql = " from BaseParameter b where b.parameterType='"+type+"'";
		return (List<BaseParameter>) this.findList(hql);
	}
	
}
