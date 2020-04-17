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

import com.boomhope.tms.entity.Manu;
import com.boomhope.tms.service.IManuService;
import com.boomhope.tms.socket.MoneyBoxSocketClient;
import com.boomhope.tms.util.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ManuTester extends AbstractJUnit4SpringContextTests{
	@Autowired
	public IManuService manuService;
	
	//通过厂商名 状态查询
	@Test
	public void testFindManuList() throws Exception {
		
		Manu manu = new Manu();
		/*manu.setManuName("华夏银行");
		manu.setManuStatus("2");*/
		Map<String,String> map = new HashMap<String,String>();
	/*	map.put("manuName", manu.getManuName());
		map.put("manuStatus", StringUtil.getSel(manu.getManuStatus()));*/
		
		List<Manu> response = manuService.findManuList(null, map);
				
		for (Manu responses : response) {
			System.out.println(responses.getConPerson());
			System.out.println(responses.getCreateDate());
			System.out.println(responses.getCreater());
			System.out.println(responses.getManuCode());
			System.out.println(responses.getManuDesc());
			System.out.println(responses.getManuName());
			System.out.println(responses.getManuStatus());
			System.out.println(responses.getPhone());
		}

	}
	
	//通过厂商代码删除
	@Test
	public void testDelManu() throws Exception {
		MoneyBoxSocketClient client=new MoneyBoxSocketClient();
		client.sendMsg(null);
//		manuService.delManu("10");

	}
	
	//通过ManuCode 编辑对应信息
	@Test
	public void testEditManu() throws Exception {
		Manu manu = new Manu();
		manu.setManuCode("10");
		manu.setManuDesc("11");
		manu.setManuName("农行");
		manu.setManuStatus("1");
		manu.setPhone("11");
		manu.setTel("11");
		manuService.editManu(manu);

	}
	
	//添加
	@Test
	public void testSaveManu() throws Exception {
		Manu manu = new Manu();
		manu.setManuCode("12");
		manu.setManuDesc("11");
		manu.setManuName("农行");
		manu.setManuStatus("2");
		manu.setPhone("11");
		manu.setTel("11");
		manu.setConPerson("12");
		manu.setCreateDate("2016");
		manu.setCreater("lal");
		manuService.saveManu(manu);

	}
	
	//通过厂商名 状态查询不分页
		@Test
		public void testFindManuList1() throws Exception {
			
		Manu manu = new Manu();
			/*manu.setManuName("1232ewqe");
			manu.setManuStatus("1");*/
			Map<String,String> map = new HashMap<String,String>();
			/*map.put("manuName", manu.getManuName());
			map.put("manuStatus", StringUtil.getSel(manu.getManuStatus()));
			*/
			List<Manu> response = manuService.findManuList(map);
			
			for (Manu responses : response) {
				System.out.println(responses.getConPerson());
				System.out.println(responses.getCreateDate());
				System.out.println(responses.getCreater());
				System.out.println(responses.getManuCode());
				System.out.println(responses.getManuDesc());
				System.out.println(responses.getManuName());
				System.out.println(responses.getManuStatus());
				System.out.println(responses.getPhone());
			}
			
			
			System.out.println(response.get(0).getManuStatus());
			
		System.out.println("00000000000000000000000"+response);
		

		}
}
