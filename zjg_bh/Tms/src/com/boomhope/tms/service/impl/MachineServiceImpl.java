package com.boomhope.tms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.boomhope.tms.dao.IMachineDao;
import com.boomhope.tms.dao.IPeripheralsDao;
import com.boomhope.tms.dao.IPeripheralsMachineDao;
import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.Peripherals;
import com.boomhope.tms.entity.PeripheralsMachine;
import com.boomhope.tms.service.IMachineService;

@SuppressWarnings("unused")
@Service("machineService")
public class MachineServiceImpl implements IMachineService{
	
	private static final Log log = LogFactory.getLog(ManuServiceImpl.class);
	
	private IMachineDao machineDao ;
	private IPeripheralsMachineDao peripheralsMachineDao;
	private IPeripheralsDao peripheralsDao;

	@Resource(name="peripheralsDao")
	public void setPeripheralsDao(IPeripheralsDao peripheralsDao) {
		this.peripheralsDao = peripheralsDao;
	}
	@Resource(name="peripheralsMachineDao")
	public void setPeripheralsMachineDao(
			IPeripheralsMachineDao peripheralsMachineDao) {
		this.peripheralsMachineDao = peripheralsMachineDao;
	}
	@Resource(name="machineDao")
	public void setMachineDao(IMachineDao machineDao) {
		this.machineDao = machineDao;
	}

	
	@Override
	public List<Machine> findMachineList(Page page, Map<String, String> map) {
		return machineDao.findMachineList(page,map);		
	}

	@Override
	public void editMachine(Machine machine) {
		machineDao.editMachine(machine);
		
	}

	@Override
	public void delMachine(String machineCode) {
		machineDao.delMachine(machineCode);
		
	}

	@Override
	public void saveMachine(Machine machine) {
		machineDao.saveMachine(machine);
		
	}


	@Override
	public List<PeripheralsMachine> findPeripherals(Page page,
			Map<String, String> map) {
		
		return machineDao.findPeripherals(page, map);
	}

	@Override
	public List<Integer> findPeripheralsMachineByMachineCode(
			String machineCode) {
		return peripheralsMachineDao.findPeripheralsMachineByMachineCode(machineCode);
	}
	@Override
	public List<Peripherals> findPeripheralsAll() {
		return peripheralsDao.findPeripheralsAll();
	}
	@Override
	public void savePeripheralsMachine(PeripheralsMachine peripheralsMachine) {
		peripheralsMachineDao.save(peripheralsMachine);
	}
	
	@Override
	public void savePeripheralsMachine(String[] per ,String machineCode) {
		// 删掉全部关系
		peripheralsMachineDao.delPeripheralsMachineByMachineCode(machineCode);
		// 保存关系
		for (String p : per) {
			if (!p.equals("")&& p!=null) {
			PeripheralsMachine pm = new PeripheralsMachine();
			Peripherals ph = new Peripherals();
			ph.setPeriId(Integer.parseInt(p));
			pm.setPeripherals(ph);
			
			Machine mh = new Machine();
			mh.setMachineCode(machineCode);
			pm.setMachine(mh);
			peripheralsMachineDao.save(pm);
			}
		}
		
	}
	@Override
	public List<Machine> findMachineList1(Map<String, String> map) {
		return machineDao.findMachineList1(map);
	}
	@Override
	public List<Machine> findMachine(String machineCode, String machineName) {
		return machineDao.findMachine(machineCode,machineName);
	}
	
	@Override
	public List<Machine> findMachine1(String machineCode) {
		return machineDao.findMachine1(machineCode);
	}
	@Override
	public List<Machine> findMachine2(String machineName) {
		return machineDao.findMachine2(machineName);
	}
	@Override
	public List<Machine> findMachineLists(String manuCode) {
		return machineDao.findMachineLists(manuCode);
	}
	@Override
	public List<Machine> findMachineListEXCEL(Map<String, String> map) {
		return machineDao.findMachineListEXCEL(map);
	}
	@Override
	public String findMachineListEXCELManuCode(String manuCode) {
		// TODO Auto-generated method stub
		return machineDao.findMachineListEXCELManuCode(manuCode);
	}
	@Override
	public void delMacMachinePic(String machineCode) {
		
		machineDao.delMacMachinePic(machineCode);
		
	}
	@Override
	public List<DeployMachine> findDeployMachineList(String machineCode) {
		
		return machineDao.findDeployMachineList(machineCode);
	}
	
}
