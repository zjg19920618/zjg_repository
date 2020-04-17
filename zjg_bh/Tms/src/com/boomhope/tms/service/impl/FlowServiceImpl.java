package com.boomhope.tms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.boomhope.tms.dao.IBaseUserDao;
import com.boomhope.tms.dao.IFlowDao;
import com.boomhope.tms.entity.BusBillMan;
import com.boomhope.tms.entity.BusFlow;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.PeripheralsMachine;
import com.boomhope.tms.entity.orcl.CdjOrder;
import com.boomhope.tms.pojo.ReturnAccountPojo;
import com.boomhope.tms.pojo.ReturnCloseAccountPojo;
import com.boomhope.tms.pojo.ReturnForBusFlowPojo;
import com.boomhope.tms.service.IFlowService;
@SuppressWarnings("unused")
@Service("flowService")
public class FlowServiceImpl implements IFlowService{

private static final Log log = LogFactory.getLog(LoginServiceImpl.class);
 	
	private IFlowDao flowDao;
    
	@Resource(name="flowDao")
	public void setFlowDao(IFlowDao flowDao) {
		this.flowDao = flowDao;
	}

	@Override
	public List<BusFlow> findFlowList(Page pageInfo, Map<String, String> map) {
		
		return flowDao.findFlowList(pageInfo,map);
	}

	@Override
	public void addBusFlow(BusFlow busflow) {
		this.flowDao.save(busflow);
		
	}

	@Override
	public List<ReturnForBusFlowPojo> findStatisticsList(Page pageInfo, Map<String, String> map) {
		
		return flowDao.findStatisticsList(pageInfo,map);
	}

	@Override
	public List<ReturnAccountPojo> findBusFlowBySEDate(Map<String, String> map) {
		return flowDao.findBusFlowBySEDate(map);
	}

	@Override
	public List<BusFlow> findFlowLists(Map<String, String> map) {
		return flowDao.findFlowList2(map);
	}
	@Override
	public List<ReturnForBusFlowPojo> findFlowLists1(Map<String, String> map) {
		return flowDao.findFlowList21(map);
	}

	@Override
	public List<BusBillMan> findBusBillMan(Page pageInfo,
			Map<String, String> map) {
		
		return flowDao.findBusBillMan(pageInfo,map);
	}

	
	
	
	
	
	@Override
	public BusBillMan findOneBusBill(String machineNo) {
			List<BusBillMan> findOneBusBill = flowDao.findOneBusBill(machineNo);
			if(findOneBusBill==null){
				return null;
			}else{
				return findOneBusBill.get(0);
			}
	}

	@Override
	public void saveBusBill(BusBillMan busBillMan) {
		flowDao.save(busBillMan);
		
	}

	@Override
	public void editBusBill(BusBillMan busBillMan) {
		flowDao.editBusBill(busBillMan);
	}

	@Override
	public void delBusBill(String machineNo) {
		flowDao.delBusBill(machineNo);
		
	}

	@Override
	public List<CdjOrder> findkhList(Page pageInfo, Map<String, String> map) {
		
		return flowDao.findkhList(pageInfo,map);
	}
	
	@Override
	public List<CdjOrder> findkhList( Map<String, String> map) {
		
		return flowDao.findkhList(map);
	}

	@Override
	public List<ReturnAccountPojo> findkhtjList(Page pageInfo,
			Map<String, String> map) {
		// TODO Auto-generated method stub
		return flowDao.findkhtjList(pageInfo,map);
	}

	@Override
	public List<ReturnAccountPojo> findkhtjListExcel(Map<String, String> map) {
		// TODO Auto-generated method stub
		return flowDao.findkhtjListExcel(map);
	}

	@Override
	public List<ReturnCloseAccountPojo> outFlowList(Map<String, String> map) {
		// TODO Auto-generated method stub
		return flowDao.outFlowList(map);
	}

	@Override
	public void addOrderCDJ(CdjOrder cdjOrder) {
		flowDao.save(cdjOrder);
	}
	
	
}
