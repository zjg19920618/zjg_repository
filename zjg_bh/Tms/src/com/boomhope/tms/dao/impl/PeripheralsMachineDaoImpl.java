package com.boomhope.tms.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IPeripheralsMachineDao;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Peripherals;
import com.boomhope.tms.entity.PeripheralsMachine;

@Repository(value="peripheralsMachineDao")
public class PeripheralsMachineDaoImpl extends BaseDao<PeripheralsMachine> implements IPeripheralsMachineDao {

	@Override
	public List<Integer> findPeripheralsMachineByMachineCode(
			String machineCode) {
		String hql = "select p.periId from Peripherals as p where p.periId in(select a.peripherals.periId from PeripheralsMachine as  a where a.machine.machineCode='"+machineCode+"')";
		return (List<Integer>) this.findList(hql);
	}



	@Override
	public void delPeripheralsMachineByMachineCode(String machineCode) {
		String hql = " delete from PeripheralsMachine as d where d.machine.machineCode='"+machineCode+"'";
		this.executeHql(hql);
	}


}
