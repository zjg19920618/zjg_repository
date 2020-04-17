package com.boomhope.tms.dao;

import java.util.List;

import com.boomhope.tms.entity.PeripheralsMachine;

public interface IPeripheralsMachineDao extends IBaseDao<PeripheralsMachine>{
	
	/**
	 * 根据机器查外设
	 * @param machineCode
	 * @return
	 */
	public List<Integer> findPeripheralsMachineByMachineCode(String machineCode);
	
	
	/**
	 * 删除
	 * @param machineCode
	 */
	public void delPeripheralsMachineByMachineCode(String machineCode);
}
