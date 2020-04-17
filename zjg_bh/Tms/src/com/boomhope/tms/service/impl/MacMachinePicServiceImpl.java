package com.boomhope.tms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.boomhope.tms.dao.IMacMachinePicDao;
import com.boomhope.tms.dao.IMachineDao;
import com.boomhope.tms.entity.MacMachinePic;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.service.IMacMachinePicService;
import com.boomhope.tms.service.IMachineService;
@SuppressWarnings("unused")
@Service("macMachinePicService")
public class MacMachinePicServiceImpl implements IMacMachinePicService{
	
	private static final Log log = LogFactory.getLog(ManuServiceImpl.class);
	
	private IMacMachinePicDao macMachinePicDao ;
	
	@Resource(name="macMachinePicDao")
	public void setMacMachinePicDao(IMacMachinePicDao macMachinePicDao) {
		this.macMachinePicDao = macMachinePicDao;
	}

	@Override
	public List<MacMachinePic> findMachinePicList(
			Map<String, String> map) {
		return macMachinePicDao.findMachinePicList(map);	
	}

	@Override
	public void saveMacMachinePic(MacMachinePic macMachinePic) {
		macMachinePicDao.saveMacMachinePic(macMachinePic);
		
	}

	@Override
	public void delMacMachinePic(String machineCode,String pic) {
		macMachinePicDao.delMacMachinePic(machineCode,pic);
		
	}

	@Override
	public List<MacMachinePic> findMacMachinePic(MacMachinePic macMachinePic) {
		return	macMachinePicDao.findMacMachinePic(macMachinePic);
		 
	}

	
	
}
