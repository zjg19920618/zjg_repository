package com.boomhope.tms.dao;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.MacControlPeri;
import com.boomhope.tms.entity.Page;

public interface IMacControlPeriDao  extends IBaseDao<MacControlPeri>{

	/**
	 * 终端查询
	 * @param page
	 * @param map
	 * @return
	 */
	public List<MacControlPeri> findMacControlPeriList(Map<String,String> map);
}
