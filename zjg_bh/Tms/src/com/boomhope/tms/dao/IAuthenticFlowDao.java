package com.boomhope.tms.dao;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.AuthenticFlow;
import com.boomhope.tms.entity.BaseDict;
import com.boomhope.tms.entity.Page;


public interface IAuthenticFlowDao extends IBaseDao<AuthenticFlow>{
	
	public List<AuthenticFlow> getAuthenticFlowList(Map<String,String> params,Page page)throws Exception;
	
	public List<AuthenticFlow> getAuthenticFlowList(Map<String,String> params)throws Exception;
	
	public void addAuthenticFlow(AuthenticFlow flow)throws Exception;
}
