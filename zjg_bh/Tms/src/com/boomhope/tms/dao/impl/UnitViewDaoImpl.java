package com.boomhope.tms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IUnitViewDao;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.UnitView;

@Repository(value="unitViewDao")
public class UnitViewDaoImpl extends BaseDao< UnitView> implements IUnitViewDao {

	@Override
	public List<UnitView> getUnitViewList(Page page, Map<String, String> map) {
		String hql = " from UnitView as v where 1=1";
		
		if(map.get("unitName") != null){
			hql +=" and v.unitName like '%"+String.valueOf(map.get("unitName") )+"%' ";
		}
		
		if(map.get("parentName") != null){
			hql +=" and v.parentName like '%"+String.valueOf(map.get("parentName") )+"%' ";
		}
		if(map.get("unitType") != null){
			hql +=" and v.unitType = '"+String.valueOf(map.get("unitType") )+"' ";
		}
		hql +="order by v.createDate desc";
		List<UnitView> list = (List<UnitView>) findList(hql, new HashMap(), page, null);
		return list;
	}

	@Override
	public List<UnitView> getUnitViewList(Map<String, String> map) {
		String hql = " from UnitView as v where 1=1";
		
		if(map.get("unitType") != null){
			hql +=" and v.unitType <= "+String.valueOf(map.get("unitType") )+" ";
		}
		
		if(map.get("unitName") != null){
			hql +=" and v.unitName like '%"+String.valueOf(map.get("unitName") )+"%' ";
		}
		
		hql +="order by v.createDate desc";
		List<UnitView> list = (List<UnitView>) this.findList(hql);
		return list;
	}
	

}
