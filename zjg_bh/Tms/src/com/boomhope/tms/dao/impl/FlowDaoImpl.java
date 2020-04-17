package com.boomhope.tms.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IFlowDao;
import com.boomhope.tms.entity.BusBillMan;
import com.boomhope.tms.entity.BusFlow;
import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.orcl.CdjOrder;
import com.boomhope.tms.pojo.ReturnAccountPojo;
import com.boomhope.tms.pojo.ReturnCloseAccountPojo;
import com.boomhope.tms.pojo.ReturnForBusFlowPojo;

@Repository(value = "flowDao")
public class FlowDaoImpl extends BaseDao<BusFlow> implements IFlowDao {
	// 业务流水查询
	@Override
	public List<BusFlow> findFlowList(Page pageInfo, Map<String, String> map) {

		String hql = "select busFlow from BusFlow as busFlow where 1=1";

		String unitCode = map.get("unitCode");
		if (unitCode != null && !"".equals(unitCode)) {
			hql += " and busFlow.baseUnit.unitCode ='" + unitCode + "'";
		}

		String projectName = map.get("projectName");
		if (projectName != null && !"".equals(projectName)) {
			hql += " and busFlow.projectName like'%" + projectName + "%'";
		}

		String bankCardNo = map.get("bankCardNo");
		if (bankCardNo != null && !"".equals(bankCardNo)) {
			hql += " and busFlow.bankCardNo like'%" + bankCardNo + "%'";
		}

		String bankCardName = map.get("bankCardName");
		if (bankCardName != null && !"".equals(bankCardName)) {
			hql += " and busFlow.bankCardName like'%" + bankCardName + "%'";
		}

		String machineNo = map.get("machineNo");
		if (machineNo != null && !"".equals(machineNo)) {
			hql += " and busFlow.deployMachine.machineNo like'%" + machineNo
					+ "%'";
		}

		String billNo = map.get("billNo");
		if (billNo != null && !"".equals(billNo)) {
			hql += " and busFlow.billNo like'%" + billNo + "%'";
		}

		String certNo = map.get("certNo");
		if (certNo != null && !"".equals(certNo)) {
			hql += " and busFlow.certNo like'%" + certNo + "%'";
		}

		String checkStatus = map.get("checkStatus");
		if (checkStatus != null && !"".equals(checkStatus)) {
			hql += " and busFlow.checkStatus ='" + checkStatus + "'";
		}
		
		String kstime = map.get("kstime");
		if (kstime != null && !"".equals(kstime)) {
			hql += " and busFlow.createDate >='" + kstime + "'";
		}
		String jstime = map.get("jstime");
		if (jstime != null && !"".equals(jstime)) {
			hql += " and busFlow.createDate <= '" + jstime + "'";
		}
		hql += " order by createDate desc";
		List<BusFlow> list = (List<BusFlow>) this.findList(hql, new HashMap(),
				pageInfo, null);
		return list;
	}

	// 业务流水统计
	@Override
	public List<ReturnForBusFlowPojo> findStatisticsList(Page pageInfo,
			Map<String, String> map) {

		String hql = "select busFlow.baseUnit.unitCode,t.unitName,busFlow.projectName,count(busFlow.projectName), sum(busFlow.realPri)  from BusFlow as busFlow,BaseUnit as t where busFlow.baseUnit.unitCode = t.unitCode";

		String unitCode = map.get("unitCode");
		if (unitCode != null && !"".equals(unitCode)) {
			hql += " and busFlow.baseUnit.unitCode ='" + unitCode + "'";
		}

		String projectName = map.get("projectName");
		if (projectName != null && !"".equals(projectName)) {
			hql += " and busFlow.projectName like'%" + projectName + "%'";
		}
		String kstime = map.get("kstime");
		if (kstime != null && !"".equals(kstime)) {
			hql += " and busFlow.createDate >='" + kstime + "'";
		}
		String jstime = map.get("jstime");
		if (jstime != null && !"".equals(jstime)) {
			hql += " and busFlow.createDate <= '" + jstime + "'";
		}
		hql += " group by busFlow.projectName,busFlow.baseUnit.unitCode,t.unitName";

		@SuppressWarnings("unchecked")
		List<Object[]> os = (List<Object[]>) this.findList(hql, new HashMap(),
				pageInfo, null);
		List<ReturnForBusFlowPojo> list = new ArrayList<>();
		for (int i = 0; i < os.size(); i++) {
			Object[] objects = os.get(i);
			ReturnForBusFlowPojo pojo = new ReturnForBusFlowPojo(objects);
			pojo.setKsTime(kstime);
			pojo.setJsTime(jstime);
			list.add(pojo);
		}
		return list;
	}
	
	/**
	 * 销户导出
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReturnAccountPojo> findBusFlowBySEDate(
			Map<String, String> map) {

		String hql = "select busFlow.baseUnit.unitName,busFlow.projectName,count(busFlow.projectName), sum(busFlow.realPri),busFlow.projectCode  from BusFlow as busFlow where 1 = 1 ";

		String unitCode = map.get("unitCode");
		if (unitCode != null && !unitCode.equals("")) {
			hql += " and busFlow.baseUnit.unitCode ='" + unitCode + "'";
		}

		String projectName = map.get("projectName");
		if (projectName != null && !projectName.equals("")) {
			hql += " and busFlow.projectName like'%" + projectName + "%'";
		}

		String startDate = map.get("startDate");
		if (startDate != null && !startDate.equals("")) {
			hql += " and busFlow.createDate >='" + startDate + "'";
		}

		String endDate = map.get("endDate");
		if (endDate != null && !endDate.equals("")) {
			hql += "and busFlow.createDate <= '" + endDate + "'";
		}
		hql += " group by busFlow.projectName,busFlow.baseUnit.unitName,busFlow.projectCode";
		List<Object[]> os = (List<Object[]>) this.findList(hql);
		List<ReturnAccountPojo> list = new ArrayList<>();
		for (int i = 0; i < os.size(); i++) {
			Object[] objects = os.get(i);
			ReturnAccountPojo pojo = new ReturnAccountPojo(objects);
			list.add(pojo);
		}
		return list;
	}

	// 不分页查询
	@Override
	public List<BusFlow> findFlowList2(Map<String, String> map) {
		String hql = " from BusFlow where 1=1";
		return (List<BusFlow>) this.findList(hql);
	}

	// 不分页查询
	@Override
	public List<ReturnForBusFlowPojo> findFlowList21(Map<String, String> map) {
		String hql = "select busFlow.baseUnit.unitCode,t.unitName,busFlow.projectName,count(busFlow.projectName) as utnum, sum(busFlow.realPri+busFlow.realInte) as total from BusFlow as busFlow,BaseUnit as t where busFlow.baseUnit.unitCode = t.unitCode";

		String unitCode = map.get("unitCode");
		if (unitCode != null && !"".equals(unitCode)) {
			hql += " and busFlow.baseUnit.unitCode ='" + unitCode + "'";
		}

		String projectName = map.get("projectName");
		if (projectName != null && !"".equals(projectName)) {
			hql += " and busFlow.projectName like'%" + projectName + "%'";
		}
		String kstime = map.get("kstime");
		if (kstime != null && !"".equals(kstime)) {
			hql += " and busFlow.createDate >='" + kstime + "'";
		}
		String jstime = map.get("jstime");
		if (jstime != null && !"".equals(jstime)) {
			hql += " and busFlow.createDate <= '" + jstime + "'";
		}
		hql += " group by busFlow.projectName,busFlow.baseUnit.unitCode,t.unitName";
		return (List<ReturnForBusFlowPojo>) this.findList(hql);
	}


	@Override
	public List<BusBillMan> findBusBillMan(Page pageInfo,
			Map<String, String> map) {
		String hql = " from  BusBillMan as bbm where 1=1 ";
		String bnobno = map.get("bnobno");
		if (bnobno != null && !"".equals(bnobno)) {
			//int bnobno1 = Integer.parseInt(bnobno);
			hql += " and " + bnobno + " BETWEEN bbm.startBno AND bbm.endBno";
		}

		String machineNo = map.get("machineNo");
		if (machineNo != null && !"".equals(machineNo)) {
			hql += " and bbm.deployMachine.machineNo like'%" + machineNo
					+ "%' ";
		}
		hql += " order by bbm.createDate desc";
		List<BusBillMan> list = (List<BusBillMan>) this.findList(hql,
				new HashMap(), pageInfo, null);
		return list;
	}

	
	
	@Override
	public List<BusBillMan> findOneBusBill(String machineNo) {
		String hql = " from BusBillMan where deployMachine.machineNo = '" + machineNo + "'";
		List<BusBillMan> bus =  (List<BusBillMan>) this.findList(hql);
		if (bus.size()==0) {
			return null;
		}
		return bus;
	}

	@Override
	public void saveBusBill(BusBillMan busBillMan) {
		this.save(busBillMan);

	}

	@Override
	public void editBusBill(BusBillMan busBillMan) {
		this.update(busBillMan);

	}

	@Override
	public void delBusBill(String machineNo) {

		BusBillMan busBillMan = new BusBillMan();
		DeployMachine de = new DeployMachine();
		de.setMachineNo(machineNo);
		busBillMan.setDeployMachine(de);
		this.delete(busBillMan);

	}
	/**
	 * 开户流水查询
	 */
	@Override
	public List<CdjOrder> findkhList(Page pageInfo, Map<String, String> map) {
		String hql = "from CdjOrder as cdjOrder where 1 = 1 ";
		String unitCode = map.get("unitCode");
		if (unitCode != null && !"".equals(unitCode)) {
			hql += " and cdjOrder.baseUnit.unitCode = '" + unitCode + "'";
		}
		String customerName = map.get("customerName");
		if (customerName != null && !"".equals(customerName)) {
			hql += " and cdjOrder.customerName like'%" + customerName + "%'";
		}
		String cardNo = map.get("cardNo");
		if (cardNo != null && !"".equals(cardNo)) {
			hql += " and cdjOrder.cardNo like'%" + cardNo + "%'";
		}
		String productName = map.get("productName");
		if (productName != null && !"".equals(productName)) {
			hql += " and cdjOrder.productName like'%" + productName + "%'";
		}
		String machineNo = map.get("machineNo");
		if (machineNo != null && !"".equals(machineNo)) {
			hql += " and cdjOrder.deployMachine.machineNo like'%" + machineNo
					+ "%'";
		}
		String subAccountNo = map.get("subAccountNo");
		if (subAccountNo != null && !"".equals(subAccountNo)) {
			hql += " and cdjOrder.subAccountNo like'%" + subAccountNo + "%'";
		}
		String certNo = map.get("certNo");
		if (certNo != null && !"".equals(certNo)) {
			hql += " and cdjOrder.certNo like'%" + certNo + "%'";
		}
		String sDate = map.get("sDate");
		if (sDate != null && !"".equals(sDate)) {
			hql += " and cdjOrder.createDate >='" + sDate + "'";
		}
		String jDate = map.get("jDate");
		if (jDate != null && !"".equals(jDate)) {
			hql += " and cdjOrder.createDate <= '" + jDate + "'";
		}
		hql += " order by cdjOrder.createDate desc";
		List<CdjOrder> list = (List<CdjOrder>) this.findList(hql, new HashMap(),pageInfo, null);
		return list;
	}
	
	/**
	 * 开户流水查询
	 */
	@Override
	public List<CdjOrder> findkhList( Map<String, String> map) {
		String hql = "from CdjOrder as cdjOrder where 1 = 1 ";
		String unitCode = map.get("unitCode");
		if (unitCode != null && !"".equals(unitCode)) {
			hql += " and cdjOrder.baseUnit.unitCode = '" + unitCode + "'";
		}
		String customerName = map.get("customerName");
		if (customerName != null && !"".equals(customerName)) {
			hql += " and cdjOrder.customerName like'%" + customerName + "%'";
		}
		String cardNo = map.get("cardNo");
		if (cardNo != null && !"".equals(cardNo)) {
			hql += " and cdjOrder.cardNo like'%" + cardNo + "%'";
		}
		String productName = map.get("productName");
		if (productName != null && !"".equals(productName)) {
			hql += " and cdjOrder.productName like'%" + productName + "%'";
		}
		String machineNo = map.get("machineNo");
		if (machineNo != null && !"".equals(machineNo)) {
			hql += " and cdjOrder.deployMachine.machineNo like'%" + machineNo
					+ "%'";
		}
		String subAccountNo = map.get("subAccountNo");
		if (subAccountNo != null && !"".equals(subAccountNo)) {
			hql += " and cdjOrder.subAccountNo like'%" + subAccountNo + "%'";
		}
		String certNo = map.get("certNo");
		if (certNo != null && !"".equals(certNo)) {
			hql += " and cdjOrder.certNo like'%" + certNo + "%'";
		}
		String sDate = map.get("sDate");
		if (sDate != null && !"".equals(sDate)) {
			hql += " and cdjOrder.createDate >='" + sDate + "'";
		}
		String jDate = map.get("jDate");
		if (jDate != null && !"".equals(jDate)) {
			hql += " and cdjOrder.createDate <= '" + jDate + "'";
		}
		hql += " order by cdjOrder.createDate desc";
		List<CdjOrder> list = (List<CdjOrder>) this.findList(hql);
		return list;
	}

	/**
	 * 开户流水统计
	 */
	@Override
	public List<ReturnAccountPojo> findkhtjList(Page pageInfo,
			Map<String, String> map) {
		String hql = "select cdjOrder.unitName,cdjOrder.productName,count(cdjOrder.productName), sum(cdjOrder.depositAmt),cdjOrder.productCode  from CdjOrder as cdjOrder where 1 = 1";
		String unitCode = map.get("unitCode");
		if (unitCode != null && !"".equals(unitCode)) {
			hql += " and cdjOrder.baseUnit.unitCode = '"+ unitCode + "'";
		}
		String productName = map.get("productName");
		if (productName != null && !"".equals(productName)) {
			hql += " and cdjOrder.productName like'%" + productName + "%'";
		}
		String kstime = map.get("kstime");
		if (kstime != null && !"".equals(kstime)) {
			hql += " and cdjOrder.createDate >='" + kstime + "'";
		}
		String jstime = map.get("jstime");
		if (jstime != null && !"".equals(jstime)) {
			hql += " and cdjOrder.createDate <= '" + jstime + "'";
		}
		hql += " group by cdjOrder.productName,cdjOrder.unitName,cdjOrder.productCode";
		@SuppressWarnings("unchecked")
		List<Object[]> os = (List<Object[]>) this.findList(hql, new HashMap(),pageInfo, null);
		List<ReturnAccountPojo> list = new ArrayList<>();
		for (int i = 0; i < os.size(); i++) {
			Object[] objects = os.get(i);
			ReturnAccountPojo pojo = new ReturnAccountPojo(objects);
			pojo.setKsTime(kstime);
			pojo.setJsTime(jstime);
			list.add(pojo);
		}
		return list;
	}

	@Override
	public List<ReturnAccountPojo> findkhtjListExcel(Map<String, String> map) {
		String hql = "select cdjOrder.unitName,cdjOrder.productName,count(cdjOrder.productName), sum(cdjOrder.depositAmt),cdjOrder.productCode  from CdjOrder as cdjOrder where 1 = 1";
		String unitCode = map.get("unitCode");
		if (unitCode != null && !"".equals(unitCode)) {
			hql += " and cdjOrder.baseUnit.unitCode = '" + unitCode + "'";
		}
		String productName = map.get("productName");
		if (productName != null && !"".equals(productName)) {
			hql += " and cdjOrder.productName like'%" + productName + "%'";
		}
		String startDate = map.get("startDate");
		if (startDate != null && !"".equals(startDate)) {
			hql += " and cdjOrder.createDate >='" + startDate + "'";
		}
		String endDate = map.get("jstime");
		if (endDate != null && !"".equals(endDate)) {
			hql += " and cdjOrder.createDate <= '" + endDate + "'";
		}
		hql += " group by cdjOrder.productName,cdjOrder.unitName,cdjOrder.productCode";
		@SuppressWarnings("unchecked")
		List<Object[]> os = (List<Object[]>) this.findList(hql);
		List<ReturnAccountPojo> list = new ArrayList<>();
		for (int i = 0; i < os.size(); i++) {
			Object[] objects = os.get(i);
			ReturnAccountPojo pojo = new ReturnAccountPojo(objects);
			pojo.setKsTime(startDate);
			pojo.setJsTime(endDate);
			list.add(pojo);
		}
		return list;
	}

	/**
	 * 销户查询导出
	 */
	@Override
	public List<ReturnCloseAccountPojo> outFlowList(Map<String, String> map) {
		String hql = "select busFlow.baseUnit.unitCode,busFlow.baseUnit.unitName,busFlow.deployMachine.machineNo,busFlow.projectName,count(busFlow.checkStatus) from BusFlow as busFlow where 1=1";

		String unitCode = map.get("unitCode");
		if (unitCode != null && !"".equals(unitCode)) {
			hql += " and busFlow.baseUnit.unitCode ='" + unitCode + "'";
		}

		String projectName = map.get("projectName");
		if (projectName != null && !"".equals(projectName)) {
			hql += " and busFlow.projectName like'%" + projectName + "%'";
		}

		String bankCardNo = map.get("bankCardNo");
		if (bankCardNo != null && !"".equals(bankCardNo)) {
			hql += " and busFlow.bankCardNo like'%" + bankCardNo + "%'";
		}

		String bankCardName = map.get("bankCardName");
		if (bankCardName != null && !"".equals(bankCardName)) {
			hql += " and busFlow.bankCardName like'%" + bankCardName + "%'";
		}

		String machineNo = map.get("machineNo");
		if (machineNo != null && !"".equals(machineNo)) {
			hql += " and busFlow.deployMachine.machineNo like'%" + machineNo
					+ "%'";
		}

		String billNo = map.get("billNo");
		if (billNo != null && !"".equals(billNo)) {
			hql += " and busFlow.billNo like'%" + billNo + "%'";
		}

		String certNo = map.get("certNo");
		if (certNo != null && !"".equals(certNo)) {
			hql += " and busFlow.certNo like'%" + certNo + "%'";
		}

		String checkStatus = map.get("checkStatus");
		if (checkStatus != null && !"".equals(checkStatus)) {
			hql += " and busFlow.checkStatus ='" + checkStatus + "'";
		}
		
		String kstime = map.get("kstime");
		if (kstime != null && !"".equals(kstime)) {
			hql += " and busFlow.createDate >='" + kstime + "'";
		}
		String jstime = map.get("jstime");
		if (jstime != null && !"".equals(jstime)) {
			hql += " and busFlow.createDate <= '" + jstime + "'";
		}
		hql += " group by busFlow.baseUnit.unitCode,busFlow.baseUnit.unitName,busFlow.deployMachine.machineNo,busFlow.projectName Order by busFlow.baseUnit.unitCode DESC";
		@SuppressWarnings("unchecked")
		List<Object[]> os = (List<Object[]>) this.findList(hql);
		List<ReturnCloseAccountPojo> list = new ArrayList<>();
		for (int i = 0; i < os.size(); i++) {
			Object[] objects = os.get(i);
			ReturnCloseAccountPojo pojo = new ReturnCloseAccountPojo(objects);
			list.add(pojo);
		}
		return list;
	}

}
