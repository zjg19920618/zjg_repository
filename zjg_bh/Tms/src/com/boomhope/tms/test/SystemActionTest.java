package com.boomhope.tms.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.boomhope.tms.entity.BaseRole;
import com.boomhope.tms.entity.BaseUnit;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Manu;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.UnitView;
import com.boomhope.tms.model.Email;
import com.boomhope.tms.service.IMailService;
import com.boomhope.tms.service.ISystemService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml",  
"classpath*:mail-context.xml" })
public class SystemActionTest extends AbstractJUnit4SpringContextTests {

	
	@Autowired
	public ISystemService systemService;
	@Autowired
	public IMailService mailService;
	
	public IMailService getMailService() {
		return mailService;
	}


	public void setMailService(IMailService mailService) {
		this.mailService = mailService;
	}


	/**
	 * 查询指定用户角色
	 * @throws Exception
	 */
	@Test
	public void getUserRole() throws Exception {	
		String username = "admin";
		List<BaseRole> viewList = systemService.getUserRole(username);
		System.out.println(viewList);
	}
	

	/**
	 * 获取全部角色
	 * @throws Exception
	 */
	@Test
	public void getAllRole() throws Exception {
		List<BaseRole> viewList = systemService.findBaseRole();
		System.out.println(viewList);
	}
	
	/**
	 * 获取用户角色
	 * @throws Exception
	 */
	@Test
	public void getSelectRole() throws Exception {
		String username = "admin";
		List<BaseRole> selected = systemService.getUserRole(username);
		System.out.println(selected);
	}
	
	/**
	 * 保存用户角色
	 * @throws Exception
	 */
	@Test
	public void saveUserRole() throws Exception {
		String[] str = {"1","2"};
		systemService.saveUserRole("111", str);
		
	}
	
	/**
	 * 删除角色
	 * @throws Exception
	 */
	@Test
	public void delRole() throws Exception {
		
		systemService.delBaseRole(3);
	}
	
	
	/**
	 * 获取字典
	 * @throws Exception
	 */
	@Test
	public void getDict() throws Exception {
		List list = systemService.getDict("macType");
		System.out.println("Dict---->"+list);
	}
	
	/**
	 * 获取角色菜单
	 * @throws Exception
	 */
	@Test
	public void getRoleMenu() throws Exception {
		List list1 = systemService.getBaseMenuList(2);
	}
	
	/**
	 * 获取角色菜单
	 * @throws Exception
	 */
	@Test
	public void getUnitList() throws Exception {
		Page page = new Page();
		page.setPageNo(0);
		page.setPageSize(10);;
		Map map = new HashMap();
		//map.put("unitName", "唐山银行总行");
		map.put("unitType", "1");
		List list1 = systemService.getUnitViewList(page, map);
		System.out.println(list1);
	}
	
	/**
	 * 获取角色菜单
	 * @throws Exception
	 */
	@Test
	public void saveUnit() throws Exception {
		BaseUnit baseUnit = new BaseUnit();
		baseUnit.setUnitCode("test");
		baseUnit.setAddress("北京啊1");	
		systemService.saveBaseUnit(baseUnit);
		
	}
	
	/**
	 * 删除角色菜单
	 * @throws Exception
	 */
	@Test
	public void delUnit() throws Exception {
		BaseUnit baseUnit = new BaseUnit();
		baseUnit.setUnitCode("test");
		systemService.delBaseUnit(baseUnit);
		
	}
	
	@Test
	public void aaa()throws Exception{
		Email email = new Email();
		email.setContent("test!!!");
		email.setSubject("this is test");
		email.setToAddress("47242445@qq.com");
		email.setFromAddress("zhaoyu@hunanbohong.com");
		mailService.sendMailBySynchronizationMode(email);
	}
	
	
	
	
	@Test
	public void aaa1()throws Exception{
		BaseUnit a = new BaseUnit();
		a.setStatus("1");
		a.setUnitCode("1232132");
		systemService.editBaseUnit1(a);
		
		
		System.out.println();
	}
	
	
	//业务流水分析页面 点击查询机构名称 查出对应支行
	@Test
	public void testFindManuLists1() throws Exception {
		
/*	Machine manuCode= new Machine();*/
				
		/*List<PeripheralsMachine> response = machineService.findMachineLists("111");*/	
		Map<String, String> selMap = new HashMap<String, String>();
		List<UnitView> list = systemService.getUnitViewList(selMap);
			
		
		
		for (UnitView responses : list) {
			System.out.println(responses.getUnitName());
			
		}
	}
}
