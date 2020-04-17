package com.boomhope.tms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IMachineDao;
import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Manu;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.PeripheralsMachine;
@Repository(value="machineDao")
public class MachineDaoImpl extends BaseDao<Machine> implements IMachineDao{

	//查询
	@Override
	public List<Machine> findMachineList(Page page, Map<String, String> map) {
		String hql =" from Machine as m where 1=1";
		String machineType = map.get("machineType");
		if(machineType != null && !"".equals(machineType)){
			hql +=" and m.machineType ='"+machineType+"'";
		}
		String machineName = map.get("machineName");
		if(machineName != null && !"".equals(machineName)){
			hql +=" and m.machineName like'%"+machineName+"%'";
		}
		String manuName = map.get("manuName");
		if(manuName != null && !"".equals(manuName)){
			hql +=" and m.manu.manuName like'%"+manuName+"%'";
		}
		hql +="order by createDate desc";
		List<Machine> list =  (List<Machine>) this.findList(hql, new HashMap(), page, null);
		return list;
	}

	//保存
	@Override
	public void saveMachine(Machine machine) {
		this.save(machine);
		
	}

	//编辑
	@Override
	public void editMachine(Machine machine) {
		Machine oldmanu = this.findOne(Machine.class, machine.getMachineCode());
		oldmanu.setMachineCode(machine.getMachineCode());
		oldmanu.setMachineDesc(machine.getMachineDesc());
		oldmanu.setMachineName(machine.getMachineName());
		oldmanu.setMachinePic(machine.getMachinePic());
		oldmanu.setMachineType(machine.getMachineType());
		Manu manu = new Manu();
		manu.setManuCode(machine.getManu().getManuCode());
		oldmanu.setManu(manu);
		this.save(oldmanu);
		
	}

	//删除
	@Override
	public void delMachine(String machineCode) {
		Machine machine = new Machine();
		machine.setMachineCode(machineCode);
		this.delete(machine);
		
	}
	
	@Override
	public List<PeripheralsMachine> findPeripherals(Page page,
			Map<String, String> map) {
		 //查询外设配置
//		String machine_code=map.get("machineCode");
//		String hql ="select * from Peripherals where Peripherals.peri_id in(select  Peripherals.peri_id  from PeripheralsMachine where Peripherals.machine_code="+machine_code+")";
		
			String hql =" from PeripheralsMachine m where 1=1";
//			String machineType = map.get("machineType");
//			if(machineType != null && !"".equals(machineType)){
//				hql +=" and m.machineType ='"+machineType+"'";
//			}
//			String machineName = map.get("machineName");
//			if(machineName != null && !"".equals(machineName)){
//				hql +=" and m.machineName like'%"+machineName+"%'";
//			}
			String machineCode = map.get("machineCode");
		
			if(machineCode != null && !"".equals(machineCode)){
				hql +=" and m.machine.machineCode ='"+machineCode+"'";
			}
			List<PeripheralsMachine> list =  (List<PeripheralsMachine>) this.findList(hql);
			return list;
		}

	@Override
	public List<Machine> findMachineList1(Map<String, String> map) {
		String hql  = " from Machine where 1=1";
		return (List<Machine>) this.findList(hql);
	}


	@Override
	public List<Machine> findMachine(String machineCode, String machineName) {

		String hql = "from Machine where machineCode <> '"+machineCode+"' and machineName = '"+machineName+"' ";
		return (List<Machine>) this.findList(hql);
	}
	
	@Override
	public List<Machine> findMachine1(String machineCode) {

		String hql = "from Machine where machineCode = '"+machineCode+"'";
		return (List<Machine>) this.findList(hql);
	}
	@Override
	public List<Machine> findMachine2(String machineName) {

		String hql = "from Machine where machineName = '"+machineName+"' ";
		return (List<Machine>) this.findList(hql);
	}
	
	
	@Override
	public List<Machine> findMachineLists(String manuCode) {
		String hql  = " from Machine ms where ms.manu.manuCode ='" + manuCode + "' ";
		return (List<Machine>) this.findList(hql);
	}

	@Override
	public List<Machine> findMachineListEXCEL(Map<String, String> map) {
		String hql =" from Machine as m where 1=1";
		String machineType = map.get("machineType");
		if(machineType != null && !"".equals(machineType)){
			hql +=" and m.machineType ='"+machineType+"'";
		}
		String machineName = map.get("machineName");
		if(machineName != null && !"".equals(machineName)){
			hql +=" and m.machineName like'%"+machineName+"%'";
		}
		String manuName = map.get("manuName");
		if(manuName != null && !"".equals(manuName)){
			hql +=" and m.manu.manuName like'%"+manuName+"%'";
		}
		hql +="order by createDate desc";
		List<Machine> list =  (List<Machine>) this.findList(hql);
		return list;
	}

	@Override
	public String findMachineListEXCELManuCode(String manuCode) {
		String hql = "select m.manuName from Manu as m where m.manuCode = '"+manuCode+"'";
		return (String) this.findOne(hql);
	}

	@Override
	public void delMacMachinePic(String machineCode) {
		String hql = "delete MacMachinePic as m where m.machineCode='" + machineCode + "'";
		this.executeHql(hql);
	}

	@Override
	public List<DeployMachine> findDeployMachineList(String machineCode) {
		String hql = "from DeployMachine as d where d.machine.machineCode = '"+machineCode+"'";
		return (List<DeployMachine>) findList(hql);
	}
	}


