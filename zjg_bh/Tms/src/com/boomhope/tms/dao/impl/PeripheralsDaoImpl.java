package com.boomhope.tms.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IPeripheralsDao;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.Peripherals;
import com.boomhope.tms.entity.PeripheralsMachine;

@Repository(value="peripheralsDao")
public class PeripheralsDaoImpl extends BaseDao<Peripherals> implements IPeripheralsDao{

	@Override
	public List<Peripherals> findPeripheralsList(Page page,
			Map<String, String> map) {
		
		String hql = "from Peripherals where 1=1 ";
		String periName = map.get("periName");
		if(periName != null && !"".equals(periName)){
			hql+="and periName like '%"+periName+"%'";
		}
		
		String periCode = map.get("periCode");
		if(periCode != null && !"".equals(periCode)){
			hql+=" and periCode like '%"+periCode+"%'";
		}
		
		String periType = map.get("periType");
		if(periType != null && !"".equals(periType)){
			hql+=" and periType = '"+periType+"'";
		}
		hql +="order by createDate desc";
		List<Peripherals> list = (List<Peripherals>)findList(hql, map, page, null);
		return list;
	}

	@Override
	public void savePeripherals(Peripherals peripherals) {
		this.save(peripherals);
		
	}

	@Override
	public void editPeripherals(Peripherals peripherals) {
		Peripherals oldmanu = this.findOne(Peripherals.class, peripherals.getPeriId());
	
		oldmanu.setCreateDate(peripherals.getCreateDate());
		oldmanu.setCreater(peripherals.getCreater());
//		oldmanu.setMachineCode(peripherals.getMachineCode());
		oldmanu.setPeriCode(peripherals.getPeriCode());
//		oldmanu.setPeriDesc(peripherals.getPeriDesc());
		oldmanu.setPeriId(peripherals.getPeriId());
		oldmanu.setPeriName(peripherals.getPeriName());
		oldmanu.setPeriType(peripherals.getPeriType());
		this.save(oldmanu);
		
	}

	@Override
	public void delPeripherals(int periId) {
		Peripherals peripherals = new Peripherals();
		peripherals.setPeriId(periId);
		this.delete(peripherals);
		
	}

	@Override
	public List<Peripherals> findPeripheralsAll() {
		return findAll();
	}

	@Override
	public List<Peripherals> findPeripherals(String periCode) {
		String hql = "from Peripherals where periCode= '"+periCode+"'";
		return (List<Peripherals>) this.findList(hql);
	}
	@Override
	public List<Peripherals> findPeripherals1(String periName) {
		String hql = "from Peripherals where periName= '"+periName+"'";
		return (List<Peripherals>) this.findList(hql);
	}

	@Override
	public List<Peripherals> findperiCod(String periId, String periCode) {
		String hql ="from Peripherals where  periCode= '"+periCode+"'and periId <>'"+periId+"'";
		return (List<Peripherals>) this.findList(hql);
	}
	
	@Override
	public List<Peripherals> findperiName(String periId, String periName) {
		String hql ="from Peripherals where  periName= '"+periName+"'and periId <>'"+periId+"'";
		return (List<Peripherals>) this.findList(hql);
	}

	@Override
	public void updatePeripherals(Peripherals pp) {
		String hql = "update Peripherals set periName = '"+pp.getPeriName()+"',periType = '"+pp.getPeriType()+"',periCode = '"+pp.getPeriCode()+"'where periId = '"+pp.getPeriId()+"'";
		this.executeHql(hql);
	}

	@Override
	public List<Peripherals> findPeripheralsList(Map<String, String> map) {
		String hql = "from Peripherals where 1=1 ";
		String periName = map.get("periName");
		if(periName != null && !"".equals(periName)){
			hql+="and periName like '%"+periName+"%'";
		}
		
		String periCode = map.get("periCode");
		if(periCode != null && !"".equals(periCode)){
			hql+=" and periCode like '%"+periCode+"%'";
		}
		
		String periType = map.get("periType");
		if(periType != null && !"".equals(periType)){
			hql+=" and periType = '"+periType+"'";
		}
		hql +="order by createDate desc";
		List<Peripherals> list = (List<Peripherals>)findList(hql);
		return list;
	}

	@Override
	public List<PeripheralsMachine> findMachineList(int periId) {
		String hql = "from PeripheralsMachine as pm where pm.peripherals.periId  = '"+periId+"'";
		return (List<PeripheralsMachine>) findList(hql);
	}

	}



