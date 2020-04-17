package com.boomhope.tms.service;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.entity.RoleMenuView;
import com.boomhope.tms.entity.TCnapsBkinfo;



public interface ITCnapsBkinfoService {
	
	public void updateBankInfoList(List<TCnapsBkinfo> list)throws Exception;
	
	public List<TCnapsBkinfo> getBankInfoList(Map params)throws Exception;
	
}
