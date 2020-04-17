package com.boomhope.tms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.boomhope.tms.dao.IPeripheralsDao;
import com.boomhope.tms.dao.IManuDao;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Peripherals;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.PeripheralsMachine;
import com.boomhope.tms.service.IPeripheralsService;
import com.boomhope.tms.service.IManuService;
@SuppressWarnings("unused")
@Service("peripheralsService")
public class PeripheralsServiceImpl implements IPeripheralsService{

	private IPeripheralsDao peripheralsDao ;

	@Resource(name="peripheralsDao")
	public void setPeripheralsDao(IPeripheralsDao peripheralsDao) {
		this.peripheralsDao = peripheralsDao;
	}
	
	@Override
	public List<Peripherals> findPeripheralsList(Page page,
			Map<String, String> map) {
		return peripheralsDao.findPeripheralsList(page, map);
	}

	@Override
	public void editPeripherals(Peripherals peripherals) {
		peripheralsDao.editPeripherals(peripherals);
		
	}

	@Override
	public void delPeripherals(int periId) {
	
		peripheralsDao.delPeripherals(periId);
		
		
	}

	@Override
	public void savePeripherals(Peripherals peripherals) {
		peripheralsDao.savePeripherals(peripherals);
		
	}

	@Override
	public List<Peripherals> findPeripherals( String periCode) {
		
		return peripheralsDao.findPeripherals(periCode);
	}

	@Override
	public List<Peripherals> findPeripherals1( String periName) {
		
		return peripheralsDao.findPeripherals(periName);
	}
	
	@Override
	public List<Peripherals> findperiCod(String periId, String periCode) {
		return peripheralsDao.findperiCod(periId,periCode);
	}

	@Override
	public void updatePeripherals(Peripherals pp) {
		peripheralsDao.updatePeripherals(pp);
		
	}

	@Override
	public List<Peripherals> findperiName(String periId, String periName) {
		return peripheralsDao.findperiCod(periId,periName);
	}

	@Override
	public List<Peripherals> findPeripheralsList(Map<String, String> map) {
		
		return peripheralsDao.findPeripheralsList(map);
	}

	@Override
	public List<PeripheralsMachine> findMachineList(int periId) {
		// TODO Auto-generated method stub
		return peripheralsDao.findMachineList(periId);
	}


}
