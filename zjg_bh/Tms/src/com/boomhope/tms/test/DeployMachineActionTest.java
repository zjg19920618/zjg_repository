package com.boomhope.tms.test;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.StatisticsView;
import com.boomhope.tms.service.IDeployMachineService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DeployMachineActionTest extends AbstractJUnit4SpringContextTests{
	@Autowired
	public IDeployMachineService deployMachineService;
	
	//通过机构名称查询 机器名称和个数
	@Test
	public void queryDeployMachineType2() throws Exception {
		DeployMachine deployMachine=new DeployMachine();
		deployMachine.getBaseUnit().setUnitCode("02002");
		List<StatisticsView> list=deployMachineService.queryDeployMachineType2(deployMachine.getBaseUnit().getUnitCode());
		JSONArray jsonArray = new JSONArray();
		for (StatisticsView statisticsView : list) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", statisticsView.getSize());
			jsonObject.put("name", statisticsView.getName());
			jsonArray.add(jsonObject);
		}
		System.out.println(jsonArray.toString());
	}
	
	
}
