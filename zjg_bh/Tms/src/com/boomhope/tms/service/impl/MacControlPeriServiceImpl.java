package com.boomhope.tms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.boomhope.tms.dao.IMacControlPeriDao;
import com.boomhope.tms.dao.IMacWarningDao;
import com.boomhope.tms.dao.IMacWarningViewDao;
import com.boomhope.tms.entity.BaseParameter;
import com.boomhope.tms.entity.MacControlPeri;
import com.boomhope.tms.entity.MacWarning;
import com.boomhope.tms.entity.MacWarningView;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.service.IMacControlPeriService;
@SuppressWarnings("unused")
@Service("macControlPeriService")
public class MacControlPeriServiceImpl implements IMacControlPeriService{

	private static final Log log = LogFactory.getLog(ManuServiceImpl.class);
	
	private IMacControlPeriDao macControlPeriDao ;
	private IMacWarningViewDao macWarningViewDao;
	private IMacWarningDao macWarningDao;

	@Resource(name="macWarningDao")
	public void setMacWarningDao(IMacWarningDao macWarningDao) {
		this.macWarningDao = macWarningDao;
	}

	@Resource(name="macWarningViewDao")
	public void setMacWarningViewDao(IMacWarningViewDao macWarningViewDao) {
		this.macWarningViewDao = macWarningViewDao;
	}


	@Resource(name="macControlPeriDao")
	public void setMacControlPeriDao(IMacControlPeriDao macControlPeriDao) {
		this.macControlPeriDao = macControlPeriDao;
	}


	@Override
	public List<MacControlPeri> findMacControlPeriList(Map<String, String> map) {
		return macControlPeriDao.findMacControlPeriList(map);
	}


	@Override
	public List<MacWarningView> findMacWarningViewList(Page page,
			Map<String, String> map) {
		return macWarningViewDao.findMacWarningViewList(page, map);
	}


	@Override
	public void updateMacWaring(MacWarning m) {
		MacWarning newm = macWarningDao.findOne(m.getId());
		newm.setProDate(m.getProDate());
		newm.setProDesc(m.getProDesc());
		newm.setProStatus(m.getProStatus());
		macWarningDao.update(newm);
	}

	@Override
	public void saveMacWaring(MacWarning m) {
		macWarningDao.save(m);
	}
	
	

	@Override
	public boolean ifRegiter(String machineNo) {
		return macWarningDao.ifRegiter(machineNo);
	}

	@Override
	public BaseParameter findMacWarByType() {
		
		return macWarningDao.findMacWarByType();
	}

	@Override
	public List<BaseParameter> findBaseParameterList() {
		
		return macWarningDao.findBaseParameterList();
	}

	@Override
	public void saveBaseParameter(BaseParameter baseParameter) {
		
		macWarningDao.save(baseParameter);
	}

	@Override
	public void updateBaseParameter(BaseParameter baseParameter) {
		
		macWarningDao.updateBaseParameter(baseParameter);
	}



}
