package com.boomhope.tms.service;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.BusBillMan;
import com.boomhope.tms.entity.BusFlow;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.orcl.CdjOrder;
import com.boomhope.tms.pojo.ReturnAccountPojo;
import com.boomhope.tms.pojo.ReturnCloseAccountPojo;
import com.boomhope.tms.pojo.ReturnForBusFlowPojo;

public interface IFlowService {

	List<BusFlow> findFlowList(Page pageInfo, Map<String, String> map);
	/***
	 * 新增业务流水
	 * @param busFlow
	 */
	public void addBusFlow(BusFlow busflow);
	
	public List<ReturnForBusFlowPojo> findStatisticsList(Page pageInfo, Map<String, String> map);
	
	public List<ReturnAccountPojo> findBusFlowBySEDate(Map<String, String> map);
	

	/**
	 * 不分页查询
	 * @param map
	 * @return
	 */
	 List<BusFlow> findFlowLists(Map<String,String> map);
		/**
		 * 不分页查询
		 * @param map
		 * @return
		 */
	List<ReturnForBusFlowPojo> findFlowLists1(Map<String,String> map);
	
	/**
	 * 凭证管理查询
	 * @param pageInfo
	 * @param map
	 * @return
	 */
	List<BusBillMan> findBusBillMan(Page pageInfo, Map<String, String> map);
	
	/**
	 * 查询凭证信息
	 * @param machineNo
	 */
	public BusBillMan findOneBusBill(String machineNo);
	
	/**
	 * 保存凭证信息
	 * @param busBillMan
	 */
	public void saveBusBill(BusBillMan busBillMan);
	
	/**
	 * 编辑凭证信息
	 * @param busBillMan
	 */
	public void editBusBill(BusBillMan busBillMan);
	
	/**
	 * 删除凭证信息
	 * @param machineNo
	 */
	public void delBusBill(String machineNo);
	/**
	 * 开户流水查询
	 * @param pageInfo
	 * @param map
	 * @return
	 */
	List<CdjOrder> findkhList(Page pageInfo, Map<String, String> map);
	/**
	 * 开户流水查询
	 * @param pageInfo
	 * @param map
	 * @return
	 */
	List<CdjOrder> findkhList( Map<String, String> map);
	
	/**
	 * 开户流水统计
	 * @param pageInfo
	 * @param map
	 * @return
	 */
	List<ReturnAccountPojo> findkhtjList(Page pageInfo, Map<String, String> map);
	
	/**
	 * 开户导出查询
	 * @param map
	 * @return
	 */
	List<ReturnAccountPojo> findkhtjListExcel(Map<String, String> map);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	List<ReturnCloseAccountPojo> outFlowList(Map<String, String> map);
	/**
	 * 
	 * @param map
	 * @return
	 */
	void addOrderCDJ(CdjOrder cdjOrder);
}
