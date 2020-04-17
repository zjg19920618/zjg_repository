package com.boomhope.tms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IBaseUnitDao;
import com.boomhope.tms.entity.BaseUnit;
import com.boomhope.tms.entity.UnitView;
@Repository(value="baseUnitDao")
public class BaseUnitDaoImpl extends BaseDao<BaseUnit> implements IBaseUnitDao {

	@Override
	public void saveBaseUnit(BaseUnit unit) {
		saveOrUpdate(unit);
	}

	@Override
	public BaseUnit getBaseUintByMachineNo(String machineNo) {
		String hql = " from BaseUnit as b where b.unitCode in(select d.unitCode from DeployMachine as d where d.machineNo='"+machineNo+"')";
		return (BaseUnit) this.findOne(hql);
	}

	@Override
	public void editBaseUnit1(BaseUnit unit) {
		  String hql = "update BaseUnit  set status='" + unit.getStatus() + "' where unitCode='" + unit.getUnitCode()
					+ "'";
			this.executeHql(hql);
		
	}

	@Override
	public List<BaseUnit> getBaseUnitCityS(String districtCounty) {
		String hql = "from BaseUnit as bs where bs.districtCounty ='"+districtCounty+"' ";
		return (List<BaseUnit>) this.findList(hql);
	}

	@Override
	public List<UnitView> getUnitViewEXCEl(Map<String, String> map) {
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
		List<UnitView> list = (List<UnitView>) findList(hql);
		return list;
	}
	
}
