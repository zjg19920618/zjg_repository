package com.boomhope.tms.dao;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.MacWarningView;
import com.boomhope.tms.entity.Page;

public interface IMacWarningViewDao extends IBaseDao<MacWarningView>{

	/**
	 * 查询
	 * @param page
	 * @param map
	 * @return
	 */
	public List<MacWarningView> findMacWarningViewList(Page page,Map<String,String> map);
}
