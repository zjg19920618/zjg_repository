package com.boomhope.tms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.boomhope.tms.dao.IManuDao;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Manu;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.service.IManuService;

@SuppressWarnings("unused")
@Service("manuService")
public class ManuServiceImpl implements IManuService {

	private static final Log log = LogFactory.getLog(ManuServiceImpl.class);
	
	
	private IManuDao manuDao ; 

	@Resource(name="manuDao")
	public void setManuDao(IManuDao manuDao) {
		this.manuDao = manuDao;
	}

	@Override
	public List<Manu> findManuList(Page page,Map<String,String> map) {
		return manuDao.findManuList(page,map);
	}

	@Override
	public void editManu(Manu manu) {
		manuDao.editManu(manu);
		
	}

	@Override
	public void delManu(String manuCode) {
		manuDao.delManu(manuCode);
	}

	@Override
	public void saveManu(Manu manu) {
		manuDao.saveManu(manu);
		
	}

	@Override
	public List<Manu> findManuList(Map<String, String> map) {
		return manuDao.findManuList(map);
	}

	@Override
	public List<Manu> findManu(String manuCode) {
		
		return manuDao.findManu(manuCode);
	}

	@Override
	public List<Manu> findManu1(String manuName) {
		
		return manuDao.findManu1(manuName);
	}
	
	@Override
	public List<Manu> findManuList1(Map<String, String> map) {
		return manuDao.findManuList1(map);
	}

	@Override
	public void editManu1(Manu manu) {
		manuDao.editManu1(manu);
		
	}

	@Override
	public List<Manu> findManu2(String manuCode, String manuName) {
		return manuDao.findManu2(manuCode,manuName);
	}
 	
}
