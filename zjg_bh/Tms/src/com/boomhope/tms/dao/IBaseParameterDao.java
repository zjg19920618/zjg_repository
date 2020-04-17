package com.boomhope.tms.dao;

import java.util.List;

import com.boomhope.tms.entity.BaseParameter;

public interface IBaseParameterDao extends IBaseDao<BaseParameter>{

	/**
	 * 获取指定参数
	 * @param type
	 * @return
	 */
	public List<BaseParameter> findBaseParameterByParentType(String type);
}
