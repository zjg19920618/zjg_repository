package com.boomhope.tms.service;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.AuthenticFlow;
import com.boomhope.tms.entity.BusBillMan;
import com.boomhope.tms.entity.BusFlow;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.orcl.CdjOrder;
import com.boomhope.tms.pojo.ReturnAccountPojo;
import com.boomhope.tms.pojo.ReturnCloseAccountPojo;
import com.boomhope.tms.pojo.ReturnForBusFlowPojo;

public interface IAuthenticFlowService {

	public List<Map<String,String>> getAuthenticFlowList(Map<String,String> params,Page page)throws Exception;
	
	public List<Map<String,String>> getAuthenticFlowList(Map<String,String> params)throws Exception;
	
	public void addAuthenticFlow(AuthenticFlow flow)throws Exception;
}
