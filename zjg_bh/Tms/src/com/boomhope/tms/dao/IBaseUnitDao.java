package com.boomhope.tms.dao;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.BaseUnit;
import com.boomhope.tms.entity.UnitView;

public interface IBaseUnitDao extends IBaseDao<BaseUnit>{

	/**
	 * 保存机构
	 * @param page
	 * @param map
	 * @return
	 */
	public void  saveBaseUnit(BaseUnit unit);
	
	/**
	 * 获取某设备编号所在机构信息
	 * @param machineNo
	 * @return
	 */
	public BaseUnit getBaseUintByMachineNo(String machineNo);
	
	/**
	 * 更改
	 * @param manu
	 */
	public void editBaseUnit1(BaseUnit unit);

	/**
	 * 根据区县名称查询本区银行
	 * @param manu
	 */
	public List<BaseUnit> getBaseUnitCityS(String districtCounty);

	/**
	 * 导出 - 机构查询
	 * @param map
	 * @return
	 */
	public List<UnitView> getUnitViewEXCEl(Map<String, String> map);
}
