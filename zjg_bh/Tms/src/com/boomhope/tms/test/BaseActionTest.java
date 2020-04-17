package com.boomhope.tms.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.boomhope.tms.entity.RoleMenuView;
import com.boomhope.tms.service.ILoginService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BaseActionTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	public ILoginService loginService;
	
	@Test
	public void autoBatchPay() throws Exception {
		
		
		String admin = "admin";
		int prent = 0;
		
		List<RoleMenuView> viewList = loginService.getMenu(admin, prent);
		System.out.println(viewList);
	}
}
