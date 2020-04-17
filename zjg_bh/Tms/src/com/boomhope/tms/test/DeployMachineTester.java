package com.boomhope.tms.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.DeployMachineView;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Manu;
import com.boomhope.tms.entity.StatisticsView;
import com.boomhope.tms.pojo.ReturnForBusFlowPojo;
import com.boomhope.tms.service.IDeployMachineService;
import com.boomhope.tms.service.IFlowService;
import com.boomhope.tms.service.IMachineService;
import com.boomhope.tms.util.Dict;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DeployMachineTester extends AbstractJUnit4SpringContextTests{
	@Autowired
	public IDeployMachineService deployMachineService;
	
	@Autowired
	public IMachineService machineService;
	
	@Autowired
	public IFlowService flowService;
	//添加部署信息
			@Test
			public void testSaveDeployMachine() throws Exception {
				DeployMachine deployMachine = new DeployMachine();
		
				
				
				deployMachine.setCloseDesc("1");
				deployMachine.setCreateDate("1");
				deployMachine.setCreater("1");
				deployMachine.setIp("123");
		
				Machine machine = new Machine();
				machine.setMachineType("2");
				machine.setMachineCode("FKJ0001");
				machine.setMachineName("发卡1");
				deployMachine.setMachine(machine);
				
			
				deployMachine.setMachineNo("600");
				deployMachine.setManagePassword("123");
				deployMachine.setStatus("1");
				deployMachine.setTellerNo("1");
				deployMachine.getBaseUnit().setUnitCode("1");
				deployMachine.setUpdateDate("1");
				deployMachine.setUpdater("22");
/*				deployMachine.setRepDesc("1");
				deployMachine.setRepStatus("1");
				deployMachine.setRepTime("20160823");*/
				deployMachine.setRepDesc(deployMachine.getRepDesc());
				
				deployMachineService.saveDeployMachine(deployMachine);
			}
			
			//通过机器编号删除
			@Test
			public void testDeployMachine() throws Exception {
				
				deployMachineService.delDeployMachine("20");
			
			}
			
			//查询部署
			//终端查询
			@Test
			public void testFindDeployMachineList() throws Exception {
							
				Map<String,String> map = new HashMap<String,String>();
			/*	map.put("machineNo", "TS000001");
				map.put("machineName", "发卡机");
				map.put("machineType", "02");*/
		/*		map.put("manuCode", "CC0001");*/
				/*map.put("unitCode", "1");*/
				map.put("status", "2");
		
				List<DeployMachineView> response = deployMachineService.findDeployMachineList(null, map);
		
				for (DeployMachineView responses : response) {
					System.out.println(responses.getId().getMachineNo());
					System.out.println(responses.getManuCode());
					System.out.println(responses.getMachineName());
					System.out.println(responses.getManuCode());
					System.out.println(responses.getUnitCode());
					System.out.println(responses.getTellerNo());
					System.out.println(responses.getIp());
					System.out.println(responses.getUnitName());
					System.out.println(responses.getManuCode());
					System.out.println(responses.getMachineType());
				
					System.out.println(responses.getRepDesc());
					System.out.println(responses.getRepStatus());
					System.out.println(responses.getRepTime());
				}

			}
			
			//编译
			@Test
			public void testEditManu() throws Exception {
				
				DeployMachine deployMachine = new DeployMachine();
				deployMachine.setMachineNo("62");

				
				Machine machine = new Machine();
//				machine.setMachineType("01");
				machine.setMachineCode("TDJ0001");
//				machine.setMachineName("发卡机2");
				
//				Manu manu = new Manu();
//				manu.setManuCode("CC0001");
//				machine.setManu(manu);
//				
//				
				deployMachine.setMachine(machine);
				
				deployMachine.getBaseUnit().setUnitCode("00001");
				deployMachine.setIp("2211");
				deployMachine.setTellerNo("1");
				deployMachineService.editDeployMachine(deployMachine);
//				machineService.editMachine(machine);
				
			}
			
			
			
			//修改密码
			@Test
			public void testEditManu1() throws Exception {
				
				DeployMachine deployMachine = new DeployMachine();
				deployMachine.setMachineNo("30");
				deployMachine.setManagePassword("6661");
				deployMachineService.editDeployMachine1(deployMachine);

				
			}
			
			//修改状态
			@Test
			public void testEditManu2() throws Exception {
				
				DeployMachine deployMachine = new DeployMachine();
				deployMachine.setMachineNo("25");
				deployMachine.setStatus("1");
				deployMachine.setRemark("666");
				deployMachineService.editDeployMachineremark(deployMachine);

				
			}
			//测试根据机器类型来查询数量（供图表使用）
			@Test
			public void testqueryDeployMachineType() throws Exception{
				List<StatisticsView> list = deployMachineService.queryDeployMachineType();
				JSONArray jsonArray = new JSONArray();
				for (StatisticsView statisticsView : list) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("value", statisticsView.getSize());
					jsonObject.put("name", statisticsView.getName());
					jsonArray.add(jsonObject);
				}
				System.out.println(jsonArray.toString());
			}
			//按照机器的上报状态来统计数量
			@Test
			public void testqueryDeployMachineRepStatus() throws Exception{
				List<StatisticsView> list = deployMachineService.queryDeployMachineRepStatus();
				JSONArray jsonArray = new JSONArray();
				for (StatisticsView statisticsView : list) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("value", statisticsView.getSize());
					if(statisticsView.getName().equals(Dict.REP_STATUS_NORMAL)){
						jsonObject.put("name", "正常");
					}else if (statisticsView.getName().equals(Dict.REP_STATUS_ERROR)) {
						jsonObject.put("name", "异常");
					}
					jsonArray.add(jsonObject);
				}
				System.out.println(jsonArray.toString());
			}
			//按照机器的使用状态来统计数量
			@Test
			public void testqueryDeployMachineStatus() throws Exception{
				List<StatisticsView> list = deployMachineService.queryDeployMachineStatus();
				JSONArray jsonArray = new JSONArray();
				for (StatisticsView statisticsView : list) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("value", statisticsView.getSize());
					if(statisticsView.getName().equals(Dict.DEPLOY_INI)){
						jsonObject.put("name", "初始");
					}else if (statisticsView.getName().equals(Dict.DEPLOY_USE)) {
						jsonObject.put("name", "已投产");
					}else if (statisticsView.getName().equals(Dict.DEPLOY_DEL)) {
						jsonObject.put("name", "已下线");
					}
					jsonArray.add(jsonObject);
				}
				System.out.println(jsonArray.toString());
			}
			
			
			//通过厂商编码查询
			@Test
			public void testFindManuLists11() throws Exception {
				
		/*	Machine manuCode= new Machine();*/
						
				/*List<PeripheralsMachine> response = machineService.findMachineLists("111");*/	
				
				
				
				
				List<DeployMachineView> list = 	deployMachineService.DeployMachineLists("00001");
				
				
				for (DeployMachineView responses : list) {
					System.out.println(responses.getUnitCode());
					
				}
			}
			
			//部署维护查询不分页
			@Test
			public void testFindDeployMachineList1() throws Exception {
							
				Map<String,String> map = new HashMap<String,String>();
				map.put("unitCode", "01001");
		
				List<DeployMachineView> response = deployMachineService.findDeployMachineList(map);
		
				for (DeployMachineView responses : response) {
					System.out.println(responses.getId().getMachineNo());
					System.out.println(responses.getManuCode());
					System.out.println(responses.getMachineName());
					System.out.println(responses.getManuCode());
					System.out.println(responses.getUnitCode());
					System.out.println(responses.getTellerNo());
					System.out.println(responses.getIp());
					System.out.println(responses.getUnitName());
					System.out.println(responses.getManuCode());
					System.out.println(responses.getMachineType());
				
					System.out.println(responses.getRepDesc());
					System.out.println(responses.getRepStatus());
					System.out.println(responses.getRepTime());
				}

			}
			
			//查询对应机器使用次数
			@Test
			public void testFindDeployMachineList11() throws Exception {
							
//				Map<String,String> map = new HashMap<String,String>();
//				map.put("unitCode", "01001");
			
//				String machineCode="cs001";
				Map<String,String> map = new HashMap<String,String>();
				map.put("unitCode", "01001");
				map.put("kstime", "20160101223344");
				map.put("jstime", "20161312223344");
//				String unitCode="01001";
//				
//				String kstime1 = "20161001223344";
//				if (kstime1!= null&&!"".equals(kstime1)) {
//					String kstime = kstime1+"000000";
//					if (kstime!= null&&!"".equals(kstime)) {
//						map.put("kstime", kstime);
//					}
//				}
//				
//				
//				String jstime1 = "20161012223344";
//				if (jstime1 != null && !"".equals(jstime1)) {
//					String jstime = jstime1+"235959";
//					if (jstime != null && !"".equals(jstime)) {
//						map.put("jstime", jstime);
//					}
//				}
				List<StatisticsView> list=deployMachineService.queryDeployMachineType22(map);
				JSONArray jsonArray = new JSONArray();
				for (StatisticsView statisticsView : list) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("value", statisticsView.getSize());
					jsonObject.put("name", statisticsView.getName());
					jsonArray.add(jsonObject);
				}
				System.out.println(jsonArray.toString());

			}
			
			//查询对应机器使用次数（机构名称）
			@Test
			public void testFindDeployMachineList12() throws Exception {
							
//				Map<String,String> map = new HashMap<String,String>();
//				map.put("unitCode", "01001");
			
//				String unitCode="01001";
				String projectName="幸福1+2";
//				String machineNo="cc001";
				
				Map<String,String> map = new HashMap<String,String>();
				map.put("projectName", "幸福1+2");
//				map.put("kstime", "20161101223344");
//				map.put("jstime", "20161312223344");
				List<StatisticsView> list=deployMachineService.queryDeployMachineType23(map);
				JSONArray jsonArray = new JSONArray();
				for (StatisticsView statisticsView : list) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("value", statisticsView.getSize());
					jsonObject.put("name", statisticsView.getName());
					jsonArray.add(jsonObject);
				}
				System.out.println(jsonArray.toString());

			}
			
			//查询对应机器使用次数（机构名称）
			@Test
			public void testFindDeployMachineList32() throws Exception {
							
//				Map<String,String> map = new HashMap<String,String>();
//				map.put("unitCode", "01001");
			
//				String unitCode="01001";
				String projectName="幸福1+2";
//				String machineNo="cc001";
				String machineCode="cc001";
				
				Map<String,String> map = new HashMap<String,String>();
				map.put("machineCode", "cs001");
//				map.put("kstime", "20161101223344");
//				map.put("jstime", "20161312223344");
				List<StatisticsView> list=deployMachineService.queryDeployMachineType24(map);
				JSONArray jsonArray = new JSONArray();
				for (StatisticsView statisticsView : list) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("value", statisticsView.getSize());
					jsonObject.put("name", statisticsView.getName());
					jsonArray.add(jsonObject);
				}
				System.out.println(jsonArray.toString());

			}
			
			
			//通过业务名称查询 对应机构次数和总金额
			@Test
			public void testFindDeployMachineList121() throws Exception {
				String projectName="幸福1+2";
				Map<String,String> map = new HashMap<String,String>();
				map.put("projectName", "幸福1+1");
//				map.put("kstime", "20161101223344");
//				map.put("jstime", "20161312223344");
				List<ReturnForBusFlowPojo> list = flowService.findFlowLists1(map);
				JSONArray jsonArray = new JSONArray();
//				for (ReturnForBusFlowPojo statisticsView : list) {
//					JSONObject jsonObject = new JSONObject();
//					jsonObject.put("value", statisticsView.getNumber());
//					jsonObject.put("name", statisticsView.getUnitName());
//					jsonObject.put("total", statisticsView.getTotal());
//					jsonArray.add(jsonObject);
//				}
				
				Map<Object, Object> map1 = new HashMap<Object, Object>();
				map1.put("rows", JSONArray.fromObject(list));	
				
				System.out.println(map1);
			}
			// 获取机器部署信息
			@Test
			public void getDeployMachineById() {
				DeployMachine deployMachineById = deployMachineService.getDeployMachineById("000C0011");
			
				System.out.println(deployMachineById.getMachineNo());
			}
}
