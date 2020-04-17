package com.boomhope.tms.dao;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.Peripherals;
import com.boomhope.tms.entity.PeripheralsMachine;

public interface IPeripheralsDao extends IBaseDao<Peripherals>{
	/**
	 * 查询外设维护信息
	 * @param page
	 * @param map
	 * @return
	 */
	public List<Peripherals> findPeripheralsList(Page page,Map<String,String> map);
	
	/**
	 * 保存外设维护信息
	 * @param machine
	 */
	public void savePeripherals(Peripherals peripherals);
	
	/**
	 * 编辑
	 * @param machine
	 */
	public void editPeripherals(Peripherals peripherals);
	
	/**
	 * 删除
	 * @param periId
	 */
	public void delPeripherals(int periId);
	

	/**
	 * 查询全部
	 * @return
	 */
	public List<Peripherals> findPeripheralsAll();

	public List<Peripherals> findPeripherals(String periCode);
	
	public List<Peripherals> findPeripherals1(String periName);

	public List<Peripherals> findperiCod(String periId, String periCode);
	
	public List<Peripherals> findperiName(String periId, String periName);

	public void updatePeripherals(Peripherals pp);

	/**
	 * 数据导出查询
	 * @param map
	 * @return
	 */
	public List<Peripherals> findPeripheralsList(Map<String, String> map);

	/**
	 * 查询此外设关联的所有机器
	 * @param periId
	 * @return
	 */
	public List<PeripheralsMachine> findMachineList(int periId);
	
}
