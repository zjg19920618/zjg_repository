package com.boomhope.tms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.boomhope.tms.dao.IDeployMachineDao;
import com.boomhope.tms.dao.IMachineDao;
import com.boomhope.tms.entity.BaseUnit;
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
import com.boomhope.tms.service.IDeployMachineService;
import com.boomhope.tms.service.IMachineService;
@SuppressWarnings("unused")
@Service("deployMachineService")
public class DeployMachineServiceImpl implements IDeployMachineService{

	private static final Log log = LogFactory.getLog(ManuServiceImpl.class);
	
	private IDeployMachineDao deployMachineDao ;
	
	@Resource(name="deployMachineDao")
	public void setDeployMachineDao(IDeployMachineDao deployMachineDao) {
		this.deployMachineDao = deployMachineDao;
	}

	@Override
	public List<DeployMachineView> findDeployMachineList(Page page,
			Map<String, String> map) {
	
		return deployMachineDao.findDeployMachineList(page, map);
	}

	@Override
	public void editDeployMachine(DeployMachine deployMachine) {
		deployMachineDao.editDeployMachine(deployMachine);
		
	}

	@Override
	public void saveDeployMachine(DeployMachine deployMachine) {
		deployMachineDao.saveDeployMachine(deployMachine);
		
	}

	@Override
	public void saveOrUpdateDeployMachine(DeployMachine deployMachine) {
		deployMachineDao.saveOrUpdate(deployMachine);
	}

	@Override
	public void delDeployMachine(String machineNo) {
		deployMachineDao.delDeployMachine(machineNo);
		
	}

	@Override
	public void editDeployMachine1(DeployMachine deployMachine) {
		deployMachineDao.editDeployMachine1(deployMachine);
		
	}

	@Override
	public void editDeployMachine2(DeployMachine deployMachine) {
		deployMachineDao.editDeployMachine2(deployMachine);
		
	}


	@Override
	public List<DeployMachineView> findDeployMachineList(Map<String, String> map) {
		return deployMachineDao.findDeployMachineList(map);
	}


	@Override
	public List<DeployMachine> findDeployMachineMC(String machineNo) {
		
		return deployMachineDao.findDeployMachineMC( machineNo);
	}

	@Override
	public List<DeployMachine> findDeployMachineMC1(String ip) {
		
		return deployMachineDao.findDeployMachineMC1(ip);
	}
	
	@Override
	public List<StatisticsView> queryDeployMachineType() {
		return deployMachineDao.queryDeployMachineType();
	}
	@Override
	public void editDeployMachineremark(DeployMachine deployMachine) {
		deployMachineDao.editDeployMachineRemark(deployMachine);
		
	}

	@Override
	public List<StatisticsView> queryDeployMachineRepStatus() {
		return deployMachineDao.queryDeployMachineRepStatus();
	}

	@Override
	public List<StatisticsView> queryDeployMachineStatus() {
		return deployMachineDao.queryDeployMachineStatus();
	}

	@Override
	public DeployMachine getDeployMachineById(String machineId) {
		return this.deployMachineDao.findOne(machineId);
	}

	@Override
	public List<DeployMachineView> DeployMachineLists(String unitCode) {
		return deployMachineDao.DeployMachineLists(unitCode);
	}

	@Override
	public List<StatisticsView> queryDeployMachineType1(String districtCounty) {
		return deployMachineDao.queryDeployMachineType1(districtCounty);
	}

	@Override
	public List<StatisticsView> queryDeployMachineRepStatus1(
			String districtCounty) {
		
		return deployMachineDao.queryDeployMachineRepStatus1(districtCounty);
	}

	@Override
	public List<StatisticsView> queryDeployMachineStatus1(String districtCounty) {
		return deployMachineDao.queryDeployMachineStatus1(districtCounty);
	}

	@Override
	public List<StatisticsView> queryDeployMachineType2(String unitCode) {
		
		return deployMachineDao.queryDeployMachineType2(unitCode);
	}

	@Override
	public List<StatisticsView> queryDeployMachineType22(Map<String, String> map) {
		
		return deployMachineDao.queryDeployMachineType22(map);
	}
	
	@Override
	public List<StatisticsView> queryDeployMachineType23(Map<String, String> map) {
		
		return deployMachineDao.queryDeployMachineType23(map);
	}
	@Override
	public List<StatisticsView> queryDeployMachineType24(Map<String, String> map) {
		
		return deployMachineDao.queryDeployMachineType24(map);
	}
	@Override
	public List<StatisticsView> queryDeployMachineRepStatus2(String unitCode) {
		return deployMachineDao.queryDeployMachineRepStatus2(unitCode);
	}

	@Override
	public List<StatisticsView> queryDeployMachineStatus2(String unitCode) {
		return deployMachineDao.queryDeployMachineStatus2(unitCode);
	}

	@Override
	public List<DeployControlPojo> findDeployMachineList1(Page pageInfo,
			Map<String, String> map) {
		return deployMachineDao.findDeployMachineList1(pageInfo, map);
	}

	/*@Override
	public List<MacControlMachine> findDeployMachineList2(String machineNo) {
		return deployMachineDao.findDeployMachineList2(machineNo);
	}*/


	@Override
	public Long findDeployMachineList3(String machineNo) {
		return deployMachineDao.findDeployMachineList3(machineNo);
	}

	@Override
	public List<ControlPojo> findMacControlMachineList(
			String machineNo) {
		
		return deployMachineDao.findMacControlMachineList(machineNo);
	
	}

	@Override
	public List<ControlPojo> findeStatusEveryMouth(Map<String, String> map) {
		
		return deployMachineDao.findeStatusEveryMouth(map);
	}

	@Override
	public List<UnitMachineStatusPojo> findUnitMachineStatus() {
		return deployMachineDao.findUnitMachineStatus();
	}

	@Override
	public String findUnitMachinePic(String machineCode) {
		
		return deployMachineDao.findUnitMachinePic(machineCode);
	}

	@Override
	public String findUnitMachinePic2(String machineCode, String creatdate) {
		
		return deployMachineDao.findUnitMachinePic2(machineCode,creatdate);
	}

	public List<PeripheralsMachine> findMachineDict(String machineCode) {
		
		return (List<PeripheralsMachine>) deployMachineDao.findMachineDict(machineCode);
	}
	@Override
	public void saveMacControlPeri(MacControlPeri macControlPeri) {
		
		deployMachineDao.saveMacControlPeri(macControlPeri);
	}

	@Override
	public List<DeployMachine> findDeployMachineMC3(String macFacNum) {
		
		return deployMachineDao.findDeployMachineMC3(macFacNum);
	}

	@Override
	public List<DeployMachine> findDeployMachineMC4(String macFacNum) {
		
		return deployMachineDao.findDeployMachineMC4(macFacNum);
	}

	@Override
	public List<DeployMachine> editDeployMachine22(DeployMachine deployMachine) {
		
		return deployMachineDao.findDeployMachineMC22(deployMachine);
	}

	@Override
	public List<DeployMachine> findDeployMachineMC33(String macFacNum) {
		
		return deployMachineDao.findDeployMachineMC33(macFacNum);
	}

	@Override
	public void updateMachineStatus1(String machineNo, String repDesc,
			String createDate) {
		deployMachineDao.updateMachineStatus1(machineNo,repDesc,createDate);
	}

	@Override
	public String findMaxCreateDate(String machineNo) {
		
		return deployMachineDao.findMaxCreateDate(machineNo);
	}

	@Override
	public List<UnitMachineStatusPojo> findUnitMachineStatus1() {
		
		return deployMachineDao.findUnitMachineStatus1();
	}

	@Override
	public List<UnitMachineStatusPojo> findUnitMachineStatus2() {
		return deployMachineDao.findUnitMachineStatus2();
	}

	@Override
	public List<UnitMachineStatusPojo> findUnitMachineStatus3() {
		return deployMachineDao.findUnitMachineStatus3();
	}

	@Override
	public List<DeployMachineView> findDeployMachineListExcel(
			Map<String, String> map) {
		return deployMachineDao.findDeployMachineListExcel(map);
	}

	@Override
	public Long findMachineCounts() {
		// TODO Auto-generated method stub
		return deployMachineDao.findMachineCounts();
	}

	@Override
	public  List<FindAllCountsPojo> findMachineTypeCounts() {
		// TODO Auto-generated method stub
		return (List<FindAllCountsPojo>) deployMachineDao.findMachineTypeCounts();
	}

	@Override
	public Long findMachineInitialCounts() {
		// TODO Auto-generated method stub
		return deployMachineDao.findMachineInitialCounts();
	}

	@Override
	public Long findMachineOperationCounts() {
		// TODO Auto-generated method stub
		return deployMachineDao.findMachineOperationCounts();
	}

	@Override
	public Long findMachineOfflineCounts() {
		// TODO Auto-generated method stub
		return deployMachineDao.findMachineOfflineCounts();
	}

	@Override
	public Long findMachineNormalCounts() {
		// TODO Auto-generated method stub
		return deployMachineDao.findMachineNormalCounts();
	}

	@Override
	public Long findMachineErrCounts() {
		// TODO Auto-generated method stub
		return deployMachineDao.findMachineErrCounts();
	}

	@Override
	public Long findMachineUnknownCounts() {
		// TODO Auto-generated method stub
		return deployMachineDao.findMachineUnknownCounts();
	}

}
