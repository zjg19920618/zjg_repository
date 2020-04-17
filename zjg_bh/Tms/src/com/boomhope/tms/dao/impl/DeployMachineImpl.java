package com.boomhope.tms.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IDeployMachineDao;
import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.DeployMachineView;
import com.boomhope.tms.entity.MacControlPeri;
import com.boomhope.tms.entity.MacMachinePic;
import com.boomhope.tms.entity.MacWarning;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.Peripherals;
import com.boomhope.tms.entity.PeripheralsMachine;
import com.boomhope.tms.entity.StatisticsView;
import com.boomhope.tms.pojo.ControlPojo;
import com.boomhope.tms.pojo.DeployControlPojo;
import com.boomhope.tms.pojo.FindAllCountsPojo;
import com.boomhope.tms.pojo.UnitMachineStatusPojo;
import com.boomhope.tms.util.Dict;
@Repository(value="deployMachineDao")
public class DeployMachineImpl extends BaseDao<DeployMachine> implements IDeployMachineDao{
//查询
	@Override
	public List<DeployMachineView> findDeployMachineList(Page page,
			Map<String, String> map) {
	
		String hql = " from DeployMachineView m where 1=1 ";
		
		String machineNo = map.get("machineNo");
		if(machineNo != null && !"".equals(machineNo)){
			hql +="  and m.id.machineNo like'%"+machineNo+"%'";
		}
		
	    String repStatus = map.get("repStatus");
		if(repStatus != null && !"".equals(repStatus)){
			hql +=" and m.repStatus ='"+repStatus+"'";
		}
		
		String machineName = map.get("machineName");
		if(machineName != null && !"".equals(machineName)){
			hql +=" and m.machineName like'%"+machineName+"%'";
		}
		String macFacNum = map.get("macFacNum");
		if(macFacNum != null && !"".equals(macFacNum)){
			hql +=" and m.macFacNum like'%"+macFacNum+"%'";
		}
		
		String machineType = map.get("machineType");
		if(machineType != null && !"".equals(machineType)){
			hql +=" and m.machineType ='"+machineType+"'";
		}
		
		String manuName = map.get("manuName");
		if(manuName != null && !"".equals(manuName)){
			hql +=" and m.manuName like '%"+manuName+"%'";
		}
		
		String status = map.get("status");
		if(status != null && !"".equals(status)){
			hql +=" and m.status ='"+status+"'";
		}
		
		String unitName = map.get("unitName");
		if(unitName != null && !"".equals(unitName)){
			hql +=" and m.unitName like'%"+unitName+"%'";
		}
		hql +="order by createDate desc";
		@SuppressWarnings("unchecked")
		List<DeployMachineView> list =  (List<DeployMachineView>) this.findList(hql, new HashMap(), page, null);
		
		return list;
	}

	@Override
	public void saveDeployMachine(DeployMachine deployMachine) {
		this.save(deployMachine);
		
	}

	@Override
	public void delDeployMachine(String machineNo) {
		DeployMachine deployMachine = new DeployMachine();
		deployMachine.setMachineNo(machineNo);
	
		this.delete(deployMachine);
		
	}

	@Override
	public void editDeployMachine1(DeployMachine deployMachine) {
		String sql = "update DeployMachine  set managePassword='" + deployMachine.getManagePassword() + "' where machineNo='" + deployMachine.getMachineNo()
				+ "'";
		this.executeHql(sql);
	}

	@Override
	public void editDeployMachine2(DeployMachine deployMachine) {
		String sql = "update DeployMachine  set status='" + deployMachine.getStatus() + "' where machineNo='" + deployMachine.getMachineNo()
				+ "'";
		this.executeHql(sql);
		
	}


	@Override
	public List<DeployMachineView> findDeployMachineList(Map<String, String> map) {
		String hql = " from DeployMachineView m where 1=1 ";
		String status = map.get("status");
		if(status != null && !"".equals(status)){
			hql +="  and m.status ='"+status+"'";
		}
		String repStatus = map.get("repStatus");
		if(status != null && !"".equals(repStatus)){
			hql +="  and m.repStatus ='"+repStatus+"'";
		}
		
		return (List<DeployMachineView>) this.findList(hql);
	}

	@Override
	public List<DeployMachine> findDeployMachineMC(String machineNo) {
		String hql = "from DeployMachine where machineNo='"+machineNo+"'";
		return (List<DeployMachine>) this.findList(hql);
	}
	@Override
	public List<DeployMachine> findDeployMachineMC1(String ip) {
		String hql = "from DeployMachine where  ip = '"+ip+"'";
		return (List<DeployMachine>) this.findList(hql);
	}
	//1
	@Override
	public List<StatisticsView> queryDeployMachineType() {
		String hql = "select new com.boomhope.tms.entity.StatisticsView(dm.machine.machineName,count(1)) from DeployMachine dm group by dm.machine.machineName";
		return (List<StatisticsView>) this.findList(hql);
	}
	@Override
	public void editDeployMachineRemark(DeployMachine deployMachine) {
		String sql = "update DeployMachine  set remark='" + deployMachine.getRemark() + "' , status='" + deployMachine.getStatus() + "' where machineNo='" + deployMachine.getMachineNo()
				+ "'";
		this.executeHql(sql);
		
	}
	//2
	@Override
	public List<StatisticsView> queryDeployMachineRepStatus() {
		String hql = "select new com.boomhope.tms.entity.StatisticsView(dm.repStatus,count(1)) from DeployMachine dm where dm.status="+Dict.DEPLOY_USE+" group by dm.repStatus";
		return (List<StatisticsView>) this.findList(hql);
	}

	@Override
	public List<StatisticsView> queryDeployMachineStatus() {
		String hql = "select new com.boomhope.tms.entity.StatisticsView(dm.status,count(1)) from DeployMachine dm group by dm.status";
		return (List<StatisticsView>) this.findList(hql);
	}

	@Override
	public List<DeployMachineView> DeployMachineLists(String unitCode) {
		String hql  = " from DeployMachineView ms where ms.unitCode ='" + unitCode + "' ";
		return (List<DeployMachineView>) this.findList(hql);
	}

	@Override
	public List<StatisticsView> queryDeployMachineType1(String districtCounty) {
		String hql = "select new com.boomhope.tms.entity.StatisticsView(dm.machine.machineName,count(1)) from DeployMachine dm where dm.districtCounty = '"+districtCounty+"' group by dm.machine.machineName ";
		return (List<StatisticsView>) this.findList(hql);
	}

	@Override
	public List<StatisticsView> queryDeployMachineRepStatus1(
			String districtCounty) {
		String hql = "select new com.boomhope.tms.entity.StatisticsView(dm.repStatus,count(1)) from DeployMachine dm where dm.status="+Dict.DEPLOY_USE+" and dm.districtCounty = '"+districtCounty+"' group by dm.repStatus";
		return (List<StatisticsView>) this.findList(hql);
	}

	@Override
	public List<StatisticsView> queryDeployMachineStatus1(String districtCounty) {
		String hql = "select new com.boomhope.tms.entity.StatisticsView(dm.status,count(1)) from DeployMachine dm where dm.districtCounty = '"+districtCounty+"' group by dm.status";
		return (List<StatisticsView>) this.findList(hql);
	}

	@Override
	public List<StatisticsView> queryDeployMachineType2(String unitCode) {
		String hql = "select new com.boomhope.tms.entity.StatisticsView(dm.machine.machineName,count(1)) from DeployMachine dm where dm.baseUnit.unitCode = '"+unitCode+"' group by dm.machine.machineName ";
		return (List<StatisticsView>) this.findList(hql);
	}

	@Override
	public List<StatisticsView> queryDeployMachineType22(Map<String, String> map) {
		String hql = "select new com.boomhope.tms.entity.StatisticsView(dm.deployMachine.machine.machineName,count(1)) from BusFlow dm where 1=1 ";
		String unitCode = map.get("unitCode");
		if(unitCode != null && !"".equals(unitCode)){
			hql +=" and dm.baseUnit.unitCode ='"+unitCode+"'";
		}
		String kstime = map.get("kstime");
		if(kstime != null && !"".equals(kstime)){
			hql +=" and dm.createDate >='"+kstime+"'";
		}
		String jstime = map.get("jstime");
		if(jstime != null && !"".equals(jstime)){
			hql +=" and dm.createDate <= '"+jstime+"'";
		}
		
			hql += " group by dm.deployMachine.machine.machineName";
		return (List<StatisticsView>) this.findList(hql);
	}
	
	@Override
	public List<StatisticsView> queryDeployMachineType23(Map<String, String> map) {
		String hql = "select new com.boomhope.tms.entity.StatisticsView(dm.baseUnit.unitName,count(1)) from BusFlow dm where 1=1  ";
		String projectName = map.get("projectName");
		if(projectName != null && !"".equals(projectName)){
			hql +=" and dm.projectName ='"+projectName+"'";
		}
		String kstime = map.get("kstime");
		if(kstime != null && !"".equals(kstime)){
			hql +=" and dm.createDate >='"+kstime+"'";
		}
		String jstime = map.get("jstime");
		if(jstime != null && !"".equals(jstime)){
			hql +=" and dm.createDate <= '"+jstime+"'";
		}
		
			hql += " group by dm.baseUnit.unitName";
		return (List<StatisticsView>) this.findList(hql);
	}
	
	@Override
	public List<StatisticsView> queryDeployMachineType24(Map<String, String> map) {
		String hql = "select new com.boomhope.tms.entity.StatisticsView(dm.deployMachine.baseUnit.unitName,count(1)) from BusFlow dm where 1=1  ";
		String machineCode = map.get("machineCode");
		if(machineCode != null && !"".equals(machineCode)){
			hql +=" and dm.deployMachine.machine.machineCode ='"+machineCode+"'";
		}
		String kstime = map.get("kstime");
		if(kstime != null && !"".equals(kstime)){
			hql +=" and dm.createDate >='"+kstime+"'";
		}
		String jstime = map.get("jstime");
		if(jstime != null && !"".equals(jstime)){
			hql +=" and dm.createDate <= '"+jstime+"'";
		}
		
			hql += " group by dm.deployMachine.baseUnit.unitName";
		return (List<StatisticsView>) this.findList(hql);
	}
	
	@Override
	public List<StatisticsView> queryDeployMachineRepStatus2(String unitCode) {
		String hql = "select new com.boomhope.tms.entity.StatisticsView(dm.repStatus,count(1)) from DeployMachine dm where dm.status="+Dict.DEPLOY_USE+" and dm.baseUnit.unitCode = '"+unitCode+"' group by dm.repStatus";
		return (List<StatisticsView>) this.findList(hql);
	}

	@Override
	public List<StatisticsView> queryDeployMachineStatus2(String unitCode) {
		String hql = "select new com.boomhope.tms.entity.StatisticsView(dm.status,count(1)) from DeployMachine dm where dm.baseUnit.unitCode = '"+unitCode+"' group by dm.status";
		return (List<StatisticsView>) this.findList(hql);
	}

	@Override
	public List<DeployControlPojo> findDeployMachineList1(Page pageInfo,
			Map<String, String> map) {

		String hql = "select d.id.machineNo,d.machineCode,d.machineType,d.machineName,d.manuName,d.unitName,d.repTime,d.repStatus,d.repDesc from DeployMachineView as d where 1=1";
		String machineNo = map.get("machineNo");
		if(machineNo != null && !"".equals(machineNo)){
			hql +="  and d.id.machineNo like'%"+machineNo+"%'";
		}
		
		String machineType = map.get("machineType");
		if(machineType != null && !"".equals(machineType)){
			hql +="  and d.machineType like'%"+machineType+"%'";
		}
		
		String machineName = map.get("machineName");
		if(machineName != null && !"".equals(machineName)){
			hql +=" and d.machineName like'%"+machineName+"%'";
		}
		
	    String repStatus = map.get("repStatus");
		if(repStatus != null && !"".equals(repStatus)){
			hql +=" and d.repStatus ='"+repStatus+"'";
		}
		
		String manuName = map.get("manuName");
		if(manuName != null && !"".equals(manuName)){
			hql +=" and d.manuName like '%"+manuName+"%'";
		}
		
		String unitName = map.get("unitName");
		if(unitName != null && !"".equals(unitName)){
			hql +=" and d.unitName like'%"+unitName+"%'";
		}
		
		hql +="order by d.repTime,d.repStatus desc";
		@SuppressWarnings("unchecked")
		List<Object[]> os =  (List<Object[]>) this.findList(hql, new HashMap(), pageInfo, null);
		List<DeployControlPojo> list = new ArrayList<>();
		for (int i = 0; i < os.size(); i++) {
			Object[] objects = os.get(i);
			DeployControlPojo pojo = new DeployControlPojo(objects);
			list.add(pojo);
		}
		return list;
	}

/*	@Override
	public List<MacControlMachine> findDeployMachineList2(String machineNo) {
		String hql = "from MacControlMachine as mcm where mcm.deployMachine.machineNo = '"+machineNo+"'";
		return (List<MacControlMachine>) this.findList(hql);
	}
	@Override
	public Long findDeployMachineList3(String machineNo) {
		String hql = "SELECT count(*) FROM  MacControlMachine as mcm where (mcm.machineStatus = '2' or mcm.statusDesc = '') and mcm.deployMachine.machineNo  = '"+machineNo+"'";
		return (Long) this.findOne(hql);
	}*/

	@Override
	public Long findDeployMachineList3(String machineNo) {
		String hql = "SELECT count(*) FROM  MacWarning as m where m.deployMachine.machineNo  = '"+machineNo+"'";
		return (Long) this.findOne(hql);
	}

	@Override
	public List<ControlPojo> findMacControlMachineList(
			String machineNo) {
	//	String hql = "select mcm.reportDate,mcm.statusDesc,mcm.machineStatus from MacControlMachine as mcm where (mcm.machineStatus = '2') and mcm.deployMachine.machineNo  = '"+machineNo+"'";
		String hql = "select m.createDate,m.warDesc from MacWarning as m where m.warType = 'E' and m.deployMachine.machineNo  = '"+machineNo+"'";
		hql +="order by m.createDate desc";
	
		List<Object[]> os =  (List<Object[]>) this.findList(hql);
		List<ControlPojo> list = new ArrayList<>();
		for (int i = 0; i < os.size(); i++) {
			Object[] objects = os.get(i);
			ControlPojo pojo = new ControlPojo(objects);
			list.add(pojo);
		}
	/*	List<MacWarning> list = (List<MacWarning>) this.findList(hql);
		List<MacWarning> list2  = new ArrayList<MacWarning>();
		for (MacWarning macWarning : list) {
			MacWarning m = new MacWarning();
			m.setCreateDate(macWarning.getCreateDate());
			m.setWarDesc(macWarning.getWarDesc());
			list2.add(m);
		}*/
		
		return list;
	}

	@Override
	public List<ControlPojo> findeStatusEveryMouth(Map<String, String> map) {
		String machineNo = map.get("machineNo");
		String hql = "select m.createDate,m.warDesc from MacWarning as m where m.deployMachine.machineNo = '"+machineNo+"' and m.warType = 'E'";
		
		String startTime = map.get("startTime");
		if(startTime != null && !"".equals(startTime)){
			hql +="  and  m.createDate >= '"+startTime+"'";
		}
		String endTime = map.get("endTime");
		if(endTime != null && !"".equals(endTime)){
			hql +="  and  m.createDate <= '"+endTime+"'";
		}
		hql +="order by m.createDate desc";
		List<Object[]> findList = (List<Object[]>) this.findList(hql);
		List<ControlPojo> list = new ArrayList<>();
		for (int i = 0; i < findList.size(); i++) {
			Object[] objects = findList.get(i);
			ControlPojo pojo = new ControlPojo(objects);
			list.add(pojo);
		}
		return list;
	}
	
/*	@Override
	public List<MachineErrorCount> findeStatusEveryMouth(Map<String, String> map) {
		String machineNo = map.get("machineNo");
		String hql = "select mcm.reportDate from MacControlMachine as mcm where mcm.deployMachine.machineNo = '"+machineNo+"' and mcm.machineStatus = '2'";
		
		return (List<MachineErrorCount>) this.findList(hql);
	}*/

	@Override
	public List<UnitMachineStatusPojo> findUnitMachineStatus() {
		String hql = "select dv.districtCounty,dv.unitName,dv.id.machineNo,dv.machineType,dv.machineName,dv.machineCode,dv.repTime,dv.repStatus,dv.repDesc from DeployMachineView as dv where dv.repStatus='2' and dv.status='1' ";
		hql += " order by dv.repTime desc"; 
		List<Object[]> list1 = (List<Object[]>) this.findList(hql);
		if (list1.isEmpty()) {
			return null;
		}else {
			List<UnitMachineStatusPojo> list = new ArrayList<>();
			for (int i = 0; i < list1.size(); i++) {
				Object[] objects = list1.get(i);
				UnitMachineStatusPojo pojo = new UnitMachineStatusPojo(objects);
				list.add(pojo);
			}
			return list;
		}
	}

	@Override
	public String findUnitMachinePic(String machineCode) {
		String hql = "select min(m.creatDate) from MacMachinePic as m where m.machineCode = '"+machineCode+"'";
		return (String) this.findOne(hql);
	}

	@Override
	public String findUnitMachinePic2(String machineCode, String creatdate) {
		String hql = "select m.pic from MacMachinePic as m where m.machineCode = '"+machineCode+"'and m.creatDate = '"+creatdate+"'";
		return (String) this.findOne(hql);
	}

	@Override
	public List<PeripheralsMachine> findMachineDict(String machineCode) {
		String hql = "from PeripheralsMachine as pm where pm.machine.machineCode = '"+machineCode+"'";
		return  (List<PeripheralsMachine>) this.findList(hql);
	}
		
	@Override
	public void saveMacControlPeri(MacControlPeri macControlPeri) {
		this.save(macControlPeri);
	}

	@Override
	public List<DeployMachine> findDeployMachineMC3(String macFacNum) {
		String hql = "from DeployMachine d where d.macFacNum ='"+macFacNum+"'and  d.status = '1'";
		return (List<DeployMachine>) this.findList(hql);
	}

	@Override
	public List<DeployMachine> findDeployMachineMC4(String macFacNum) {
		String hql = "from DeployMachine as d where d.macFacNum ='"+macFacNum+"'";
		return (List<DeployMachine>) this.findList(hql);
	}
	
	@Override
	public List<DeployMachine> findDeployMachineMC22(DeployMachine deployMachine) {
		String hql = "from DeployMachine as d where  d.macFacNum ='"+deployMachine.getMacFacNum()+"'and  d.status = '1'";
		return (List<DeployMachine>) this.findList(hql);
	}

	@Override
	public List<DeployMachine> findDeployMachineMC33(String macFacNum) {
		String hql = "from DeployMachine as d where  d.macFacNum ='"+macFacNum+"'and  d.status = '1'";
		return (List<DeployMachine>) this.findList(hql);
	}

	@Override
	public List<DeployMachine> findDeployMachineList2(String machineNo) {
		String hql = "from MacControlMachine as mcm where mcm.deployMachine.machineNo = '"+machineNo+"'";
		return  (List<DeployMachine>) this.findList(hql);
	}

	@Override
	public void updateMachineStatus1(String machineNo, String repDesc,
			String createDate) {
		String hql = "update MacWarning as m set m.warDesc = '"+repDesc+"' where m.deployMachine.machineNo = '"+machineNo+"' and m.createDate = '"+createDate+"' ";
		this.executeHql(hql);
	}

	@Override
	public String findMaxCreateDate(String machineNo) {
		String hql = "select m.createDate from MacWarning as m where m.deployMachine.machineNo = '"+machineNo+"'";
		return (String) this.findOne(hql);
	}


	@Override
	public List<UnitMachineStatusPojo> findUnitMachineStatus1() {
		String hql = "select dv.districtCounty,dv.unitName,dv.id.machineNo,dv.machineType,dv.machineName,dv.machineCode,dv.repTime,dv.repStatus,dv.repDesc from DeployMachineView as dv where  dv.status='1'";
		hql += " order by dv.repStatus desc"; 
		List<Object[]> list1 = (List<Object[]>) this.findList(hql);
		if (list1.isEmpty()) {
			return null;
		}else {
			List<UnitMachineStatusPojo> list = new ArrayList<>();
			for (int i = 0; i < list1.size(); i++) {
				Object[] objects = list1.get(i);
				UnitMachineStatusPojo pojo = new UnitMachineStatusPojo(objects);
				list.add(pojo);
			}
			return list;
		}
	}

	@Override
	public List<UnitMachineStatusPojo> findUnitMachineStatus2() {
		String hql = "select dv.districtCounty,dv.unitName,dv.id.machineNo,dv.machineType,dv.machineName,dv.machineCode,dv.repTime,dv.repStatus,dv.repDesc from DeployMachineView as dv where dv.repStatus = '1' and dv.status='1' ";
		List<Object[]> list1 = (List<Object[]>) this.findList(hql);
		if (list1.isEmpty()) {
			return null;
		}else {
			List<UnitMachineStatusPojo> list = new ArrayList<>();
			for (int i = 0; i < list1.size(); i++) {
				Object[] objects = list1.get(i);
				UnitMachineStatusPojo pojo = new UnitMachineStatusPojo(objects);
				list.add(pojo);
			}
			return list;
		}
	}

	@Override
	public List<UnitMachineStatusPojo> findUnitMachineStatus3() {
		String hql = "select dv.districtCounty,dv.unitName,dv.id.machineNo,dv.machineType,dv.machineName,dv.machineCode,dv.repTime,dv.repStatus,dv.repDesc from DeployMachineView as dv where dv.repStatus = '2' and dv.status='1' ";
		List<Object[]> list1 = (List<Object[]>) this.findList(hql);
		if (list1.isEmpty()) {
			return null;
		}else {
			List<UnitMachineStatusPojo> list = new ArrayList<>();
			for (int i = 0; i < list1.size(); i++) {
				Object[] objects = list1.get(i);
				UnitMachineStatusPojo pojo = new UnitMachineStatusPojo(objects);
				list.add(pojo);
			}
			return list;
		}
	}

	@Override
	public List<DeployMachineView> findDeployMachineListExcel(
			Map<String, String> map) {
		String hql = " from DeployMachineView m where 1=1 ";
		
		String machineNo = map.get("machineNo");
		if(machineNo != null && !"".equals(machineNo)){
			hql +="  and m.id.machineNo like'%"+machineNo+"%'";
		}
		
	    String repStatus = map.get("repStatus");
		if(repStatus != null && !"".equals(repStatus)){
			hql +=" and m.repStatus ='"+repStatus+"'";
		}
		
		String machineName = map.get("machineName");
		if(machineName != null && !"".equals(machineName)){
			hql +=" and m.machineName like'%"+machineName+"%'";
		}
		String macFacNum = map.get("macFacNum");
		if(macFacNum != null && !"".equals(macFacNum)){
			hql +=" and m.macFacNum like'%"+macFacNum+"%'";
		}
		
		String machineType = map.get("machineType");
		if(machineType != null && !"".equals(machineType)){
			hql +=" and m.machineType ='"+machineType+"'";
		}
		
		String manuName = map.get("manuName");
		if(manuName != null && !"".equals(manuName)){
			hql +=" and m.manuName like '%"+manuName+"%'";
		}
		
		String status = map.get("status");
		if(status != null && !"".equals(status)){
			hql +=" and m.status ='"+status+"'";
		}
		
		String unitName = map.get("unitName");
		if(unitName != null && !"".equals(unitName)){
			hql +=" and m.unitName like'%"+unitName+"%'";
		}
		hql +="order by createDate desc";
		@SuppressWarnings("unchecked")
		List<DeployMachineView> list =  (List<DeployMachineView>) this.findList(hql);
		
		return list;
	}

	@Override
	public Long findMachineCounts() {
		String hql = "select count(*) from BaseUnit";
		return (Long) this.findOne(hql);
	}

	@Override
	public List<FindAllCountsPojo> findMachineTypeCounts() {
		String hql = "select d.machine.machineCode,count(*) from  DeployMachine as d group by d.machine.machineCode ";
		List<Object[]> list1 = (List<Object[]>) this.findList(hql);
		List<FindAllCountsPojo> list = new ArrayList<>();
		for (int i = 0; i < list1.size(); i++) {
			Object[] objects = list1.get(i);
			FindAllCountsPojo pojo = new FindAllCountsPojo(objects);
			list.add(pojo);
		}
		return list;
	}

	@Override
	public Long findMachineInitialCounts() {
		String hql = "select count(*) from DeployMachine as dm  where dm.status = '0'";
		return (Long) this.findOne(hql);
	}

	@Override
	public Long findMachineOperationCounts() {
		String hql = "select count(*) from DeployMachine as dm  where dm.status = '1'";
		return (Long) this.findOne(hql);
	}

	@Override
	public Long findMachineOfflineCounts() {
		String hql = "select count(*) from DeployMachine as dm  where dm.status = '2'";
		return (Long) this.findOne(hql);
	}

	@Override
	public Long findMachineNormalCounts() {
		String hql = "select count(*) from DeployMachine as dm  where dm.status = '1' and repStatus = '1'";
		return (Long) this.findOne(hql);
	}

	@Override
	public Long findMachineErrCounts() {
		String hql = "select count(*) from DeployMachine as dm  where dm.status = '1' and repStatus = '2'";
		return (Long) this.findOne(hql);
	}

	@Override
	public Long findMachineUnknownCounts() {
		String hql = "select count(*) from DeployMachine as dm  where dm.status = '1' and repStatus != '1' and repStatus != '2'";
		return (Long) this.findOne(hql);
	}

	//编辑
		@Override
		public void editDeployMachine(DeployMachine deployMachine) {
		//	String sql = "update DeployMachine as d  set d.machine.machineCode='"+deployMachine.getMachine().getMachineCode()+"',d.baseUnit.unitCode = '"+deployMachine.getBaseUnit().getUnitCode()+"',d.districtCounty = '"+deployMachine.getDistrictCounty()+"',d.ip = '"+deployMachine.getIp()+"',d.tellerNo = '"+deployMachine.getTellerNo()+"',d.majorkeyflag = '"+deployMachine.getMajorkeyflag()+"',d.workkeyflag = '"+deployMachine.getWorkkeyflag()+"',d.channel = '"+deployMachine.getChannel()+"',d.merNo = '"+deployMachine.getMerNo()+"'where d.machineNo='" + deployMachine.getMachineNo()+ "'";
			String sql = "update DeployMachine as d  set d.machine.machineCode='"+deployMachine.getMachine().getMachineCode()+"',d.baseUnit.unitCode = '"+deployMachine.getBaseUnit().getUnitCode()+"',d.districtCounty = '"+deployMachine.getDistrictCounty()+"',d.ip = '"+deployMachine.getIp()+"',d.tellerNo = '"+deployMachine.getTellerNo()+"',d.majorkeyflag = '"+deployMachine.getMajorkeyflag()+"',d.workkeyflag = '"+deployMachine.getWorkkeyflag()+"',d.channel = '"+deployMachine.getChannel()+"',d.merNo = '"+deployMachine.getMerNo()+"',d.macFacNum = '"+deployMachine.getMacFacNum()+"'where d.machineNo='" + deployMachine.getMachineNo()+ "'";
			this.executeHql(sql);
		}
}


