package com.boomhope.tms.dao;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.PeripheralsMachine;

public interface IMachineDao extends IBaseDao<Machine>{
	
	/**
	 * 查询机器维护信息
	 * @param page
	 * @param map
	 * @return
	 */
	public List<Machine> findMachineList(Page page,Map<String,String> map);
	
	/**
	 * 不分页查询
	 * @param page
	 * @param map
	 * @return
	 */
	public List<Machine> findMachineList1(Map<String,String> map);
	
	/**
	 * 保存机器维护信息
	 * @param manu
	 */
	public void saveMachine(Machine machine);
	
	/**
	 * 编辑机器维护信息
	 * @param manu
	 */
	public void editMachine(Machine machine);
	
	/**
	 * 删除机器维护信息
	 * @param manuCode
	 */
	public void delMachine(String machineCode);
	
	/**
	 * 查询外设配置
	 * @param page
	 * @param map
	 * @return
	 */
	public List<PeripheralsMachine> findPeripherals(Page page,Map<String,String> map);




	public List<Machine> findMachine(String machineCode, String machineName);
	public List<Machine> findMachine1(String machineCode);
	public List<Machine> findMachine2(String machineName);


	/**
	 * 不分页查询查询厂商
	 * @param page
	 * @param map
	 * @return
	 */
	public List<Machine> findMachineLists(String manuCode);

	/**
	 * 导出机器数据-查询
	 * @param map
	 * @return
	 */
	public List<Machine> findMachineListEXCEL(Map<String, String> map);

	/**
	 * 根据manuCode查询manuName
	 * @param manuCode
	 * @return
	 */
	public String findMachineListEXCELManuCode(String manuCode);

	/**
	 *根据机器编号删除榆次相关联的机器
	 * @param machineCode
	 */
	public void delMacMachinePic(String machineCode);

	/**
	 * 查询所有部署的机构
	 * @param machineCode
	 * @return
	 */
	public List<DeployMachine> findDeployMachineList(String machineCode);
	
	
	


}
