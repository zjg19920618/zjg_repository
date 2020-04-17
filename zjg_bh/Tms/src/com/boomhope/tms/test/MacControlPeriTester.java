package com.boomhope.tms.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.boomhope.tms.entity.MacControlPeri;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.PeripheralsMachine;
import com.boomhope.tms.service.IMacControlPeriService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MacControlPeriTester extends AbstractJUnit4SpringContextTests{
	
	@Autowired
	public IMacControlPeriService macControlPeriService;
	
	
	
	//通过机器名称 机器类型 厂商查询
	@Test
	public void testMacControlPeriLists() throws Exception {
		
		
		Map<String,String> map = new HashMap<String,String>();				
		map.put("machineNo ", "1");	
		
		List<MacControlPeri> response = macControlPeriService.findMacControlPeriList( map);	
		for (MacControlPeri responses : response) {
			System.out.println(responses.getId());
			System.out.println(responses.getDeployMachine().getMachineNo());
			System.out.println(responses.getPeriStatus());
			System.out.println(responses.getPeripherals().getPeriType());
			System.out.println(responses.getStatusDesc());
		}

	}
	@Test
	public void findMacWarningViewList(){
		Page pageInfo = new Page();
		pageInfo.setPageNo(1);
		pageInfo.setPageSize(10);
		macControlPeriService.findMacWarningViewList(pageInfo, new HashMap());
	}

	
}
