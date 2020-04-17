package com.boomhope.tms.dao;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.PeripheralsMachine;
import com.boomhope.tms.entity.TCnapsBkinfo;

public interface ITCnapsBkinfoDao extends IBaseDao<TCnapsBkinfo>{
	
	public List<TCnapsBkinfo> findBankInfoList(Map params);
	
	public void addBankInfo(TCnapsBkinfo bkInfo);
	
	public void updateBankInfo(TCnapsBkinfo bkInfo);
	
	public void deleteBankInfo(List<String> bankNoList);

}
