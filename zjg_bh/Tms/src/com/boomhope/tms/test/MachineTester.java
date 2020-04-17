package com.boomhope.tms.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.boomhope.tms.entity.BaseDict;
import com.boomhope.tms.entity.BusFlow;
import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Manu;
import com.boomhope.tms.entity.Peripherals;
import com.boomhope.tms.entity.PeripheralsMachine;
import com.boomhope.tms.entity.UnitView;
import com.boomhope.tms.service.IFlowService;
import com.boomhope.tms.service.IMachineService;
import com.boomhope.tms.service.ISystemService;
import com.boomhope.tms.util.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MachineTester extends AbstractJUnit4SpringContextTests{
	
	@Autowired
	public IMachineService machineService;
	@Autowired
	public ISystemService systemService;
	@Autowired
	public IFlowService flowService;
	//添加
		@Test
		public void testSaveManu() throws Exception {
			Machine machine = new Machine();
			machine.setCreateDate("1");
			machine.setCreater("1");
			machine.setMachineCode("5");
			machine.setMachineDesc("4");
			machine.setMachineName("1");
			machine.setMachinePic("1");
			machine.setMachineType("1");
		
			
			Manu manu = new Manu();
			manu.setManuCode(machine.getManu().getManuCode());
			machine.setManu(manu);
			
		
			machineService.saveMachine(machine);
		}
		
		//通过厂商代码删除
		@Test
		public void testDelManu() throws Exception {
			machineService.delMachine("4");
		
		}
		
		//通过machineCode 编辑对应信息
		@Test
		public void testEditManu() throws Exception {
			Machine machine = new Machine();
			machine.setCreateDate("2");
			machine.setCreater("2");
			machine.setMachineCode("1");
			machine.setMachineDesc("2");
			machine.setMachineName("2");
			machine.setMachinePic("2");
			machine.setMachineType("2");
			Manu manu = new Manu();
			manu.setManuCode(machine.getManu().getManuCode());
			machine.setManu(manu);
//			machine.getManu().setManuCode("2");
		}
		
		//通过机器名称 机器类型 厂商查询
				@Test
				public void testFindManuList() throws Exception {
					
					
					Map<String,String> map = new HashMap<String,String>();
//					map.put("machineType", StringUtil.getSel("250"));
//					map.put("manuCode", StringUtil.getSel("2"));
//					map.put("machineName", "发卡");
					
					List<Machine> response = machineService.findMachineList(null, map);
					if(response != null && response.size() > 0){
						Map<String,String> dictMap = getDict();
						for (Machine m : response) {
							m.setMachineTypeName(dictMap.get(m.getMachineType()));
							System.out.println(m.getMachineTypeName());
						}
					}
							
//					for (Machine responses : response) {
//						System.out.println(responses.getCreateDate());
//						System.out.println(responses.getCreater());
//						System.out.println(responses.getMachineCode());
//						System.out.println(responses.getMachineDesc());
//						System.out.println(responses.getMachineName());
//						System.out.println(responses.getMachinePic());
//						System.out.println(responses.getMachineType());
//						
//					}

				}
				
				//通过机器名称 机器类型 厂商查询
				@Test
				public void testFindManuLists() throws Exception {
					
					
					Map<String,String> map = new HashMap<String,String>();				
					map.put("machineCode ", "1");				
					List<PeripheralsMachine> response = machineService.findPeripherals(null, map);		
					for (PeripheralsMachine responses : response) {
						System.out.println(responses.getId());
						System.out.println(responses.getPeripherals().getPeriName());
						System.out.println(responses.getPeripherals().getCreateDate());
						System.out.println(responses.getPeripherals().getCreater());
						System.out.println(responses.getPeripherals().getPeriCode());
						System.out.println(responses.getPeripherals().getPeriDesc());
						System.out.println(responses.getPeripherals().getPeriId());
						System.out.println(responses.getPeripherals().getPeriName());
						System.out.println(responses.getPeripherals().getPeriType());
					}

				}
				
				
				//通过machineCode 编辑对应信息
				@Test
				public void findSelectPer() throws Exception {
					machineService.findPeripheralsMachineByMachineCode("CDJ0002");
				}
				
				@Test
				public void save(){
					String [] str = {"23"};
					machineService.savePeripheralsMachine(str, "CDJ0002");
				}
				
				//添加部署信息
				@Test
				public void testMachine2() throws Exception {
					List<Machine> list = machineService.findMachineList1(new HashMap());
					Map<Object, Object> map = new HashMap<Object, Object>();
					map.put("rows", JSONArray.fromObject(list));
					
					System.out.println(map);
				}
				
				
				
				//通过厂商编码查询
				@Test
				public void testFindManuLists1() throws Exception {
					
			/*	Machine manuCode= new Machine();*/
							
					/*List<PeripheralsMachine> response = machineService.findMachineLists("111");*/	
					Manu manu =new Manu();
					manu.setManuCode("CC0001");
					List<Machine> list = 	machineService.findMachineLists("CC0001");
					
					
					for (Machine responses : list) {
						System.out.println(responses.getMachineName());
						
					}
				}
				
				
				//查询机器类型
				@Test
				public void testMachine21() throws Exception {
					List<Machine> list = machineService.findMachineList1(new HashMap());
					if(list != null && list.size() > 0){
						Map<String,String> dictMap = getDict();
						for (Machine m : list) {
							m.setMachineTypeName(dictMap.get(m.getMachineType()));
							System.out.println(m.getMachineTypeName());
						}
					}
					for (Machine responses : list) {
						System.out.println(responses.getMachineTypeName());
						
					}
				}
				/**
				 * 获取机器类型对应的字典
				 * @return
				 */
				public Map<String,String> getDict(){
					Map<String,String> map = new HashMap<String,String>();
					List<BaseDict> dictList = systemService.getDict("macType");
					for (BaseDict baseDict : dictList) {
						map.put(baseDict.getValueName(), baseDict.getValueDesc());
					}
					return map;
				}
				
				//业务类型
				@Test
				public void testMachine211() throws Exception {
					Map<String,String> map = new HashMap<String,String>();
//					map.put("machine_no", StringUtil.getSel("13123"));
					List<BusFlow> list = flowService.findFlowLists(new HashMap<String, String>());
					for (BusFlow responses : list) {
						System.out.println(responses.getProjectName());
						
					}
				}
				//业务类型
				@Test
				public void testMachine2111() throws Exception {
					Map<String,String> map = new HashMap<String,String>();
//					map.put("machine_no", StringUtil.getSel("13123"));
					List<BusFlow> list = flowService.findFlowList(null, new HashMap<String, String>());
					for (BusFlow responses : list) {
						System.out.println(responses.getProjectName());
						
					}
				}
}
