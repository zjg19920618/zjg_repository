package com.boomhope.tms.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.service.ILoginService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class LoginTester extends AbstractJUnit4SpringContextTests {

	@Autowired
	public ILoginService loginService;
	
	@Test
	public void autoBatchPay() throws Exception {
		
		
		String loginName = "admin";
		String loginPass = "123";
		BaseUser response = loginService.getUser(loginName, loginPass);
				
	String name= response.getName();
		String pass=response.getPhone();
	
		System.out.println(454);
		System.out.println(name);
		System.out.println(pass);
	
	}
}
