package com.boomhope.tms.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IMacControlPeriDao;
import com.boomhope.tms.entity.MacControlPeri;
import com.boomhope.tms.entity.Page;
@Repository(value="macControlPeriDao")
public class MacControlPeriDaoImpl extends BaseDao<MacControlPeri> implements IMacControlPeriDao{
//终端查询详细
	@Override
	public List<MacControlPeri> findMacControlPeriList(
			Map<String, String> map) {
		String hql =" from MacControlPeri m where 1=1";

		String machineNo = map.get("machineNo");
		if(machineNo != null && !"".equals(machineNo)){
			hql+=" and m.deployMachine.machineNo = '"+machineNo+"'";
		}
		
		@SuppressWarnings("unchecked")
		List<MacControlPeri> list =  (List<MacControlPeri>) this.findList(hql);
		return list;
	}

}
