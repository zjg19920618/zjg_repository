package com.boomhope.Bill.Test;

import net.sf.json.JSONObject;

import com.boomhope.Bill.Util.HttpClientUtil;

public class MoDeployMachineTest{
	private String url = "http://198.1.244.254:8080/Tms";
	
	
	public static void main(String[] args) {
		MoDeployMachineTest test = new MoDeployMachineTest();
		test.editDeployMachine3Test();
	}

	//添加设备
	public void saveDeployMachineListTest(){
		try {
			url = url + "/saveDeployMachine.do.goo";
			
			JSONObject map = new JSONObject();
			map.put("tellerNo", "C0028");
			map.put("machineCode", "BH-0001");
			map.put("machineNo", "000C0028");
			map.put("ip", "1.1.1.1");
			map.put("keyboard_major_keyflag", "CODM.000C0028.zmk");
			map.put("keyboard_work_keyflag", "CODM.000C0028.zpk");
			map.put("key_flag", "ZZQZ.00000001.zpk");
			map.put("mac_keyflag", "ZZQZ.00000001.zak");
			map.put("macFacNum", "12312313");
			map.put("cityS", "路北区");
			map.put("unitCode", "999999");
			map.put("channel", "0035");
			map.put("merNo", "123456789012345");
			map.put("doPostUser","ZRRT");
			
			HttpClientUtil ee = new HttpClientUtil();
			String result = ee.httpPost(url, map);
			
			
			System.out.println(result);
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//编辑设备信息
	public void editDeployMachineTest(){
		url = url + "/editDeployMachine.do.goo";
		
		JSONObject map = new JSONObject();
		map.put("tellerNo", "C0028");
		map.put("machineCode", "BH-0001");
		map.put("machineNo", "000C0028");
		map.put("ip", "1.1.1.1");
		map.put("keyboard_major_keyflag", "CODM.000C0028.zmk");
		map.put("keyboard_work_keyflag", "CODM.000C0028.zpk");
		map.put("key_flag", "ZZQZ.00000001.zpk");
		map.put("mac_keyflag", "ZZQZ.00000001.zak");
		map.put("macFacNum", "987654");
		map.put("cityS", "路北区");
		map.put("unitCode", "050300120");
		map.put("channel", "0035");
		map.put("merNo", "123456789012345");
		
		HttpClientUtil ee = new HttpClientUtil();
		String result = ee.httpPost(url, map);
		
		System.out.println(result);
		
	}
	
	//删除设备
	public void delDeployMachineTest(){
		url = url + "/delDeployMachine.do.goo";
		JSONObject map = new JSONObject();
		map.put("machineNo", "000C0028");
		map.put("doPostUser","ZRRT");
		HttpClientUtil ee = new HttpClientUtil();
		String result = ee.httpPost(url, map);
		
		System.out.println(result);
	}
	
	//修改状态（投产或者下线）
	public void editDeployMachine2Test(){
		url = url + "/editDeployMachine2.do.goo";
		JSONObject map = new JSONObject();
		map.put("machineNo", "000C0020");
//		map.put("macFacNum", "12312313");
		map.put("status", "2");
		map.put("doPostUser","ZRRT");
		HttpClientUtil ee = new HttpClientUtil();
		String result = ee.httpPost(url, map);
		
		System.out.println(result);
	}
	
	//修改密码
	public void editDeployMachine3Test(){
		url = url + "/editDeployMachine3.do.goo";
		JSONObject map = new JSONObject();
		map.put("machineNo", "000C0020");
		map.put("doPostUser","ZRRT");
		HttpClientUtil ee = new HttpClientUtil();
		String result = ee.httpPost(url, map);
		System.out.println(result);
	}
	
	//添加机构
	public void saveOrUpdateUnitTest(){
		url = url + "/system/saveUnit.do.goo";
		JSONObject map = new JSONObject();
		map.put("unitCode", "999999");
		map.put("unitName", "新测试支行");
		map.put("unitType", "2");
		map.put("address", "机场路研发中心");
		map.put("tel", "999999999999");
		map.put("contactor", "研发");
		map.put("email", "yanfaceshi@163.com");
		map.put("contactorPhone", "");
		map.put("status", "1");
		map.put("parentCode", "0500");
		map.put("cityS", "路北区");
		map.put("doPostUser","ZRRT");
		
		
		HttpClientUtil ee = new HttpClientUtil();
		String result = ee.httpPost(url, map);
		
		System.out.println(result);
		
		
	}
	
	//编辑机构信息
	public void saveUnit1(){
		url = url + "/system/saveUnit1.do.goo";
		JSONObject map = new JSONObject();
		map.put("unitCode", "999999");
		map.put("unitName", "新测试支行");
		map.put("unitType", "3");
		map.put("address", "机场路研发中心");
		map.put("tel", "999999999999");
		map.put("contactor", "研发");
		map.put("email", "yanfaceshi@163.com");
		map.put("contactorPhone", "");
		map.put("status", "1");
		map.put("parentCode", "0500");
		map.put("cityS", "路北区");
		map.put("doPostUser","ZRRT");
		
		
		HttpClientUtil ee = new HttpClientUtil();
		String result = ee.httpPost(url, map);
		
		System.out.println(result);
		
		
	}
	
	
	//删除机构
	public void delUnit(){
		url = url + "/system/delUnit.do.goo";
		JSONObject map = new JSONObject();
		map.put("unitCode", "999999");
		map.put("doPostUser","ZRRT");
		
		
		HttpClientUtil ee = new HttpClientUtil();
		String result = ee.httpPost(url, map);
		
		System.out.println(result);
		
		
	}
	
}
