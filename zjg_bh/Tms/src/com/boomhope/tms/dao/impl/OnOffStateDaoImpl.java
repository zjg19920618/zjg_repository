package com.boomhope.tms.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IOnOffStateDao;
import com.boomhope.tms.entity.OnOffState;
import com.boomhope.tms.entity.Page;

@Repository(value = "onOffStateDao")
public class OnOffStateDaoImpl extends BaseDao<OnOffState> implements IOnOffStateDao {

	@Override
	public Integer executeHql(String hql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OnOffState> findFlowList(Page pageInfo, Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OnOffState> findOneStatePage(Page pageInfo, Map<String, String> map) {
		
		String onOffName = map.get("onOffName");
		
		String hql = "select onOffState from OnOffState as onOffState where onOffState.onOffName='"+onOffName+"'";
		
		return (List<OnOffState>)findList(hql, map, pageInfo, null);
	}

	@Override
	public List<OnOffState> findOneState(String onOffName) {
		String hql = "select onOffState from OnOffState as onOffState where onOffState.onOffName='"+onOffName+"'";
		List<OnOffState> onOffState =  (List<OnOffState>) this.findList(hql);
		if (onOffState.size()==0) {
			return null;
		}
		return onOffState;
	}

	@Override
	public void editOneState(OnOffState state) {
		this.update(state);
	}

}
