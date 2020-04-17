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

import com.boomhope.tms.entity.Peripherals;
import com.boomhope.tms.entity.PeripheralsMachine;
import com.boomhope.tms.service.IPeripheralsService;
import com.boomhope.tms.util.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PeripheralsTester extends AbstractJUnit4SpringContextTests{
	@Autowired
	public IPeripheralsService peripheralsService;
	
	//添加
		@Test
		public void testSavePeripherals() throws Exception {
			Peripherals peripherals = new Peripherals();
			peripherals.setCreateDate("1");
			peripherals.setCreater("1");
//			peripherals.setMachineCode("2");
			peripherals.setPeriCode("3");
			peripherals.setPeriDesc("备注");
//			peripherals.setPeriId("1");
			peripherals.setPeriName("发卡机");
			peripherals.setPeriType("0");
			peripheralsService.savePeripherals(peripherals);
		}
		
		//通过厂商代码删除
		@Test
		public void testDelPeripherals() throws Exception {

			peripheralsService.delPeripherals(4);
			

		}
		
		//通过PeriId 编辑对应信息
		@Test
		public void testEditPeripherals() throws Exception {
			Peripherals peripherals = new Peripherals();
			peripherals.setCreateDate("3");
			peripherals.setCreater("3");
//			peripherals.setMachineCode("1");
			peripherals.setPeriCode("3");
			peripherals.setPeriDesc("备注1");
			peripherals.setPeriId(4);
			peripherals.setPeriName("发卡机2");
			peripherals.setPeriType("1");
			peripheralsService.editPeripherals(peripherals);
		}
		
		//通过外设名称 外设型号 外设类型查询
		@Test
		public void testFindManuList() throws Exception {
			
			Peripherals peripherals = new Peripherals();
			peripherals.setPeriName("发卡机2");
			peripherals.setPeriType("1");
			peripherals.setPeriCode("3");
			Map<String,String> map = new HashMap<String,String>();
			map.put("periName", peripherals.getPeriName());
			map.put("periType", StringUtil.getSel(peripherals.getPeriType()));
			map.put("periCode", peripherals.getPeriCode());
			
			List<Peripherals> response = peripheralsService
					.findPeripheralsList(null, map);
					
			for (Peripherals responses : response) {
				System.out.println(responses.getCreateDate());
				System.out.println(responses.getCreater());
//				System.out.println(responses.getMachineCode());
				System.out.println(responses.getPeriCode());
				System.out.println(responses.getPeriDesc());
				System.out.println(responses.getPeriId());
				System.out.println(responses.getPeriName());
				System.out.println(responses.getPeriType());
				
			}

		}
		
		
		

}
