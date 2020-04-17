package com.boomhope.tms.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IManuDao;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Manu;
import com.boomhope.tms.entity.Page;
@Repository(value="manuDao")
public class ManuDaoImpl extends BaseDao<Manu> implements IManuDao{

	
	public List<Manu> findManuList(Page page,Map<String,String> map){
		
		
		String hql = "from Manu where 1=1 ";
		String manuName = map.get("manuName");
		if(manuName != null && !"".equals(manuName)){
			hql+="and manuName like '%"+manuName+"%'";
		}
		
		String manuStatus = map.get("manuStatus");
		if(manuStatus != null && !"".equals(manuStatus)){
			hql+=" and manuStatus = '"+manuStatus+"'";
		}
		hql +="order by createDate desc";
		
		
		List<Manu> list = (List<Manu>)findList(hql, map, page, null);
		return list;
		
	}
	
	@Override
	public void saveManu(Manu manu) {
		this.saveOrUpdate(manu);
	}

	@Override
	public void editManu(Manu manu) {
		
		Manu oldmanu = this.findOne(Manu.class, manu.getManuCode());
		oldmanu.setManuName(manu.getManuName());
		oldmanu.setManuDesc(manu.getManuDesc());
		oldmanu.setManuCode(manu.getManuCode());
		oldmanu.setManuStatus(manu.getManuStatus());
		oldmanu.setTel(manu.getTel());
		oldmanu.setPhone(manu.getPhone());
		oldmanu.setConPerson(manu.getConPerson());
		this.save(oldmanu);
	}

	@Override
	public void delManu(String manuCode) {
		Manu manu = new Manu();
		manu.setManuCode(manuCode);
		this.delete(manu);
		
	}

	@Override
	public List<Manu> findManuList(Map<String, String> map) {
		String hql  = " from Manu where 1=1";
		
		String manuName = map.get("manuName");
		if(manuName != null && !"".equals(manuName)){
			hql+="and manuName like '%"+manuName+"%'";
		}
		
		String manuStatus = map.get("manuStatus");
		if(manuStatus != null && !"".equals(manuStatus)){
			hql+=" and manuStatus = '"+manuStatus+"'";
		}
		hql +="order by createDate desc";
		
		return (List<Manu>) this.findList(hql);
	}

	@Override
	public List<Manu> findManu(String manuCode) {
		String hql = "from Manu where manuCode='"+manuCode+"'";
		return (List<Manu>) this.findList(hql);
	}

	@Override
	public List<Manu> findManu1(String manuName) {
		String hql = "from Manu where manuName = '"+manuName+"'";
		return (List<Manu>) this.findList(hql);
	}
	
	@Override
	public List<Manu> findManuList1(Map<String, String> map) {
		String hql  = " from Machine where 1=1";
		return (List<Manu>) this.findList(hql);
	}

	@Override
	public void editManu1(Manu manu) {

  String sql = "update Manu  set manuStatus='" + manu.getManuStatus() + "' where manuCode='" + manu.getManuCode()
				+ "'";
		this.executeHql(sql);
		
	}

	@Override
	public List<Manu> findManu2(String manuCode, String manuName) {
		String hql ="from Manu where manuCode <> '"+manuCode+"'and manuName = '"+manuName+"'"; 
		return (List<Manu>) this.findList(hql);
	}
}
