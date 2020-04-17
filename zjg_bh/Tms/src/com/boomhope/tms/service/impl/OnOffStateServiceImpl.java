package com.boomhope.tms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.boomhope.tms.dao.IOnOffStateDao;
import com.boomhope.tms.entity.OnOffState;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.service.IOnOffStateService;
@Service("onOffStateService")
public class OnOffStateServiceImpl implements IOnOffStateService {

	
	private IOnOffStateDao onOffStateDao;
    
	@Resource(name="onOffStateDao")
	public void setOnOffStateDao(IOnOffStateDao onOffStateDao) {
		this.onOffStateDao = onOffStateDao;
	}
	
	@Override
	public List<OnOffState> findOneStatePage(Page pageInfo,Map<String, String> map) {
		
		return onOffStateDao.findOneStatePage(pageInfo, map);
	}

	@Override
	public OnOffState findOneState(String onOffName) {
		
		List<OnOffState> findOneFaceState = onOffStateDao.findOneState(onOffName);
		if(findOneFaceState==null){
			return null;
		}else{
			return findOneFaceState.get(0);
		}
	}
	
	@Override
	public void editOneState(OnOffState state) {
		onOffStateDao.editOneState(state);

	}

}
