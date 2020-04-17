package com.boomhope.tms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.boomhope.tms.dao.IAuthenticFlowDao;
import com.boomhope.tms.dao.IBaseUnitDao;
import com.boomhope.tms.dao.IBaseUserDao;
import com.boomhope.tms.dao.IDeployMachineDao;
import com.boomhope.tms.dao.IFlowDao;
import com.boomhope.tms.dao.IUnitViewDao;
import com.boomhope.tms.entity.AuthenticFlow;
import com.boomhope.tms.entity.BaseUnit;
import com.boomhope.tms.entity.BusBillMan;
import com.boomhope.tms.entity.BusFlow;
import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.DeployMachineView;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.PeripheralsMachine;
import com.boomhope.tms.entity.UnitView;
import com.boomhope.tms.entity.orcl.CdjOrder;
import com.boomhope.tms.pojo.ReturnAccountPojo;
import com.boomhope.tms.pojo.ReturnCloseAccountPojo;
import com.boomhope.tms.pojo.ReturnForBusFlowPojo;
import com.boomhope.tms.service.IAuthenticFlowService;
import com.boomhope.tms.service.IFlowService;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
@SuppressWarnings("unused")
@Service("AuthenticFlowImpl")
public class AuthenticFlowImpl implements IAuthenticFlowService{

private static final Log log = LogFactory.getLog(AuthenticFlowImpl.class);
 	
	private IAuthenticFlowDao authenticFlowDao;
	
	private IDeployMachineDao deployMachineDao;
	
	private IUnitViewDao unitViewDao;
	
	@Resource(name="deployMachineDao")
	public void setDeployMachineDao(IDeployMachineDao deployMachineDao) {
		this.deployMachineDao = deployMachineDao;
	}

	@Resource(name="AuthenticFlowDaoImpl")
	public void setAuthenticFlowDao(IAuthenticFlowDao authenticFlowDao) {
		this.authenticFlowDao = authenticFlowDao;
	}

	@Resource(name="unitViewDao")
	public void setUnitViewDao(IUnitViewDao unitViewDao) {
		this.unitViewDao = unitViewDao;
	}

	@Override
	public List<Map<String, String>> getAuthenticFlowList(Map<String, String> params,
			Page page) throws Exception {
		List<UnitView> depList=unitViewDao.getUnitViewList(page, params);
		List<Map<String, String>> list1=new ArrayList<Map<String, String>>();
		for (UnitView deployMachineView : depList) {
			Map<String, String> resultMap=new HashMap<String, String>();
			resultMap.put("unitCode", deployMachineView.getUnitViewId().getUnitCode());
			resultMap.put("unitName", deployMachineView.getUnitName());
			Map map=new HashMap();
			map.put("unitCode", deployMachineView.getUnitViewId().getUnitCode());
			map.put("flowDate", params.get("flowDate"));
			List<AuthenticFlow> list=authenticFlowDao.getAuthenticFlowList(map);
			for (AuthenticFlow authenticFlow : list) {
				if(resultMap.get("status"+authenticFlow.getStatus())==null){
					resultMap.put("status"+authenticFlow.getStatus(), "1");
				}else{
					resultMap.put("status"+authenticFlow.getStatus(),(Integer.valueOf(resultMap.get("status"+authenticFlow.getStatus()))+1)+"");
				}
			}
			int statu0=0;
			int statu1=0;
			int statu2=0;
			int statu4=0;
			int statu6=0;
			if(resultMap.get("status0")==null){
				resultMap.put("status0", "0");
			}else{
				statu0=Integer.valueOf(resultMap.get("status0"));
			}
			if(resultMap.get("status1")==null){
				resultMap.put("status1", "0");
			}else{
				statu1=Integer.valueOf(resultMap.get("status1"));
			}
			if(resultMap.get("status2")==null){
				resultMap.put("status2", "0");
			}else{
				statu2=Integer.valueOf(resultMap.get("status2"));
			}
			if(resultMap.get("status4")==null){
				resultMap.put("status4", "0");
			}else{
				statu4=Integer.valueOf(resultMap.get("status4"));
			}
			if(resultMap.get("status6")!=null){
				statu6=Integer.valueOf(resultMap.get("status6"));
			}else{
				resultMap.put("status6", "0");
			}
			if((statu0)==0){
				resultMap.put("tgl", "0%");
				resultMap.put("tgl6", "0%");
			}else{
				String tgl=((((double)statu0)/(statu0+statu1+statu2+statu4+statu6))*100)+"";
				String tgl6=((((double)statu0)/(statu0+statu1+statu2+statu4))*100)+"";
				resultMap.put("tgl", tgl.substring(0,tgl.indexOf("."))+"%");
				resultMap.put("tgl6", tgl6.substring(0,tgl6.indexOf("."))+"%");
			}
			list1.add(resultMap);
		}
		return list1;
	}

	
	@Override
	public List<Map<String, String>> getAuthenticFlowList(Map<String, String> params) throws Exception {
		List<UnitView> depList=unitViewDao.getUnitViewList(params);
		List<Map<String, String>> list1=new ArrayList<Map<String, String>>();
		int status0=0;
		int status1=0;
		int status2=0;
		int status4=0;
		int status6=0;
		for (UnitView deployMachineView : depList) {
			Map<String, String> resultMap=new HashMap<String, String>();
			resultMap.put("unitCode", deployMachineView.getUnitViewId().getUnitCode());
			resultMap.put("unitName", deployMachineView.getUnitName());
			Map map=new HashMap();
			map.put("unitCode", deployMachineView.getUnitViewId().getUnitCode());
			map.put("flowDate", params.get("flowDate"));
			List<AuthenticFlow> list=authenticFlowDao.getAuthenticFlowList(map);
			for (AuthenticFlow authenticFlow : list) {
				if(resultMap.get("status"+authenticFlow.getStatus())==null){
					resultMap.put("status"+authenticFlow.getStatus(), "1");
				}else{
					resultMap.put("status"+authenticFlow.getStatus(),(Integer.valueOf(resultMap.get("status"+authenticFlow.getStatus()))+1)+"");
				}
			}
			int statu0=0;
			int statu1=0;
			int statu2=0;
			int statu4=0;
			int statu6=0;
			if(resultMap.get("status0")==null){
				resultMap.put("status0", "0");
			}else{
				statu0=Integer.valueOf(resultMap.get("status0"));
			}
			if(resultMap.get("status1")==null){
				resultMap.put("status1", "0");
			}else{
				statu1=Integer.valueOf(resultMap.get("status1"));
			}
			if(resultMap.get("status2")==null){
				resultMap.put("status2", "0");
			}else{
				statu2=Integer.valueOf(resultMap.get("status2"));
			}
			if(resultMap.get("status4")==null){
				resultMap.put("status4", "0");
			}else{
				statu4=Integer.valueOf(resultMap.get("status4"));
			}
			if(resultMap.get("status6")!=null){
				statu6=Integer.valueOf(resultMap.get("status6"));
			}else{
				resultMap.put("status6", "0");
			}
			status0+=statu0;
			status1+=statu1;
			status2+=statu2;
			status4+=statu4;
			status6+=statu6;
			if((statu0)==0){
				resultMap.put("tgl", "0%");
				resultMap.put("tgl6", "0%");
			}else{
				String tgl=((((double)statu0)/(statu0+statu1+statu2+statu4+statu6))*100)+"";
				String tgl6=((((double)statu0)/(statu0+statu1+statu2+statu4))*100)+"";
				resultMap.put("tgl", tgl.substring(0,tgl.indexOf("."))+"%");
				resultMap.put("tgl6", tgl6.substring(0,tgl6.indexOf("."))+"%");
			}
			list1.add(resultMap);
		}
		Map<String, String> resultMap=new HashMap<String, String>();
		resultMap.put("unitCode","-");
		resultMap.put("unitName","全行");
		resultMap.put("status0", status0+"");
		resultMap.put("status1", status1+"");
		resultMap.put("status2", status2+"");
		resultMap.put("status4", status4+"");
		resultMap.put("status6", status6+"");
		if((status0)==0){
			resultMap.put("tgl", "0%");
			resultMap.put("tgl6", "0%");
		}else{
			String tgl=((((double)status0)/(status0+status1+status2+status4+status6))*100)+"";
			String tgl6=((((double)status0)/(status0+status1+status2+status4))*100)+"";
			resultMap.put("tgl", tgl.substring(0,tgl.indexOf("."))+"%");
			resultMap.put("tgl6", tgl6.substring(0,tgl6.indexOf("."))+"%");
		}
		list1.add(resultMap);
		return list1;
	}
	
	@Override
	public void addAuthenticFlow(AuthenticFlow flow) throws Exception {
		authenticFlowDao.addAuthenticFlow(flow);
	}
}
