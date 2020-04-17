package com.boomhope.tms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IMacMachinePicDao;
import com.boomhope.tms.entity.MacMachinePic;
import com.boomhope.tms.entity.Page;

@Repository(value="macMachinePicDao")
public class MacMachinePicDaoImpl extends BaseDao<MacMachinePic> implements IMacMachinePicDao{

	@Override
	public List<MacMachinePic> findMachinePicList(
			Map<String, String> map) {
		String hql =" from MacMachinePic m where 1=1";
		String machineCode = map.get("machineCode");
		if(machineCode != null && !"".equals(machineCode)){
			hql +=" and m.machineCode ='"+machineCode+"'";
		}
		
		List<MacMachinePic> list =  (List<MacMachinePic>) this.findList(hql);
		return list;
	}

	@Override
	public void saveMacMachinePic(MacMachinePic macMachinePic) {
		this.save(macMachinePic);
		
	}

	@Override
	public void delMacMachinePic(String machineCode,String pic) {
		String hql = " delete MacMachinePic as m where m.machineCode='" + machineCode + "'and m.pic = '"+pic+"'";
		this.executeHql(hql);
	}

	@Override
	public List<MacMachinePic> findMacMachinePic(MacMachinePic macMachinePic) {
		String hql = "from MacMachinePic as m where m.machineCode = '"+macMachinePic.getMachineCode()+"'and m.pic = '"+macMachinePic.getPic()+"'";
		return (List<MacMachinePic>) this.findList(hql);
	
	}
}
