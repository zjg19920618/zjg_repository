package com.boomhope.tms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IMacWarningViewDao;
import com.boomhope.tms.entity.MacWarningView;
import com.boomhope.tms.entity.Page;

@Repository(value="macWarningViewDao")
public class MacWarningViewDaoImpl  extends BaseDao<MacWarningView> implements IMacWarningViewDao {

	@Override
	public List<MacWarningView> findMacWarningViewList(Page page,Map<String, String> map) {
		String hql = " from MacWarningView as a where 1=1";
		String machineName = map.get("machineName");
		if(machineName != null && !"".equals(machineName)){
			hql +=" and a.machineName like'%"+machineName+"%' ";
		}
		String unitName = map.get("unitName");
		if(unitName != null && !"".equals(unitName)){
			hql +=" and a.unitName like '%"+unitName+"%' ";
		}
		String proStatus = map.get("proStatus");
		if(proStatus != null && !"".equals(proStatus)){
			hql +=" and a.proStatus='"+proStatus+"' ";
		}
		hql +=" order by a.proStatus asc";//a.createDate desc,
		return (List<MacWarningView>) this.findList(hql, new HashMap(), page, null);
	}
}
