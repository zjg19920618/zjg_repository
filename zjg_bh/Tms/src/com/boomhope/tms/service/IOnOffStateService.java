package com.boomhope.tms.service;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.OnOffState;
import com.boomhope.tms.entity.Page;

public interface IOnOffStateService {

	/**
	 * 查询某个开关启用状态,带分页
	 * @param pageInfo
	 * @param map
	 * @return
	 */
	List<OnOffState> findOneStatePage(Page pageInfo, Map<String, String> map);
	
	/**
	 * 查询某个开关启用状态
	 * @param String
	 * @return
	 */
	OnOffState findOneState(String onOffName);
	
	/**
	 * 修改开关状态
	 * @param OnOffState
	 * @return
	 */
	public void editOneState(OnOffState state);
}
