package com.boomhope.tms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IAuthenticFlowDao;
import com.boomhope.tms.dao.IBaseDictDao;
import com.boomhope.tms.entity.AuthenticFlow;
import com.boomhope.tms.entity.BaseDict;
import com.boomhope.tms.entity.Page;
@Repository(value="AuthenticFlowDaoImpl")
public class AuthenticFlowDaoImpl extends BaseDao<AuthenticFlow> implements IAuthenticFlowDao {

	@Override
	public List<AuthenticFlow> getAuthenticFlowList(Map<String, String> params,Page page)
			throws Exception {
		String hql="from AuthenticFlow where 1=1";
//		if(params.get("unitName")!=null&&!"".equals(params.get("unitName"))){
//			hql+=" and baseUnit.unitName like '%"+params.get("unitName")+"%'";
//		}
		if(params.get("machineNo")!=null&&!"".equals(params.get("machineNo"))){
			hql+=" and machineNo like '%"+params.get("machineNo")+"%'";
		}
		if(params.get("status")!=null&&!"".equals(params.get("status"))){
			hql+=" and status like '%"+params.get("status")+"%'";
		}
		List list=findList(hql, new HashMap(), page, null);
		return list;
	}

	@Override
	public void addAuthenticFlow(AuthenticFlow flow) throws Exception {
		save(flow);
	}

	@Override
	public List<AuthenticFlow> getAuthenticFlowList(Map<String, String> params)
			throws Exception {
		String hql="from AuthenticFlow where 1=1";
		if(params.get("machineNo")!=null&&!"".equals(params.get("machineNo"))){
			hql+=" and machineNo = '"+params.get("machineNo")+"'";
		}
		if(params.get("flowDate")!=null&&!"".equals(params.get("flowDate"))){
			hql+=" and flowDate = '"+params.get("flowDate")+"'";
		}
		if(params.get("unitCode")!=null&&!"".equals(params.get("unitCode"))){
			hql+=" and branchNo = '"+params.get("unitCode")+"'";
		}
		if(params.get("status")!=null&&!"".equals(params.get("status"))){
			hql+=" and status = '"+params.get("status")+"'";
		}
		List list=findList(hql);
		return list;
	}

	
}
