package com.boomhope.tms.service;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Peripherals;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.PeripheralsMachine;

public interface IPeripheralsService {
	/**
	 * 外设维护查询
	 * @param page
	 * @param map
	 * @return
	 */
	public List<Peripherals> findPeripheralsList(Page page,Map<String,String> map);
	
	/**
	 * 编辑
	 * @param manu
	 */
	public void editPeripherals(Peripherals peripherals);
	
	/**
	 * 删除
	 * @param periId
	 */
	public void delPeripherals(int periId);
	
	
	/**
	 * 保存
	 * @param manuCode
	 */
	public void savePeripherals(Peripherals peripherals);


	/**
	 * 
	 * @param periCode
	 * @return
	 */
	public List<Peripherals> findPeripherals(String periCode);
	
	/**
	 * 
	 * @param periName
	 * @return
	 */
	public List<Peripherals> findPeripherals1(String periName);

	/**
	 * 
	 * @param periId
	 * @param periCode
	 * @return
	 */
	public List<Peripherals> findperiCod(String periId, String periCode);

	/**
	 * 修改外设
	 * @param pp
	 */
	public void updatePeripherals(Peripherals pp);

	/**
	 * 
	 * @param periId
	 * @param periName
	 * @return
	 */
	public List<Peripherals> findperiName(String periId, String periName);

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
