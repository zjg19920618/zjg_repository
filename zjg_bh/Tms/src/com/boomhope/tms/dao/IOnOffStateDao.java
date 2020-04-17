package com.boomhope.tms.dao;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.OnOffState;
import com.boomhope.tms.entity.Page;
public interface IOnOffStateDao extends IBaseDao<OnOffState>{
	
	List<OnOffState> findFlowList(Page pageInfo, Map<String, String> map);

	/**
	 * 查询某个开关启用状态,带分页
	 * @param pageInfo
	 * @param map
	 * @return
	 */
	List<OnOffState> findOneStatePage(Page pageInfo, Map<String, String> map);
	
	/**
	 * 查询某个开关启用状态
	 * @param pageInfo
	 * @param map
	 * @return
	 */
	List<OnOffState> findOneState( String onOffName);

	
	/**
	 * 修改开关状态
	 */
	public void editOneState(OnOffState state);
}
