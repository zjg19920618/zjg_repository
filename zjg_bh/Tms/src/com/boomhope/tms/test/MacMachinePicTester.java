package com.boomhope.tms.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.MacMachinePic;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Manu;
import com.boomhope.tms.service.IMacMachinePicService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MacMachinePicTester extends AbstractJUnit4SpringContextTests{
	
	 @Value("#{configProperties['Tms_connectIp']}")
	private String connectIp;
	 
	 @Value("#{configProperties['Tms_concatenateIP']}")
	private String concatenateIP; 
	 
	@Autowired
	public IMacMachinePicService macMachinePicService;
	
	//查询图片路径
	@Test
	public void testFindMachineList() throws Exception {
		
		Map<String,String> map = new HashMap<String,String>();

			map.put("machineCode", "CDJ0002");
			MacMachinePic a =new MacMachinePic();
			
			List<MacMachinePic> response = macMachinePicService.findMachinePicList(map);

			for (MacMachinePic responses : response) {
				System.out.println(responses.getPic());
				/*responses.setPic(connectIp+responses.getPic());
				System.out.println(responses.getPic());*/
				
				
			}
		

	}
	
	//添加图片
	@Test
	public void testSaveDeployMachine() throws Exception {
		MacMachinePic macMachinePic = new MacMachinePic();
		macMachinePic.setMachineCode("50");
		macMachinePic.setPic("666.jpg");
		/*macMachinePic.setPic("6");*/
		macMachinePic.setPic(connectIp+macMachinePic.getPic());
		macMachinePicService.saveMacMachinePic(macMachinePic);
		
	}
	
	//删除图片
			@Test
			public void testDelMacMachinePic() throws Exception {
			//	macMachinePicService.delMacMachinePic("121");
			
			}
			
			@Test
			public  void  sdsad(){
				
				File ss= new  File("E:\\cs\\qq.jpg");
				File ss1= new  File("E:\\cs\\qq1.jpg");
				
				FileInputStream fis =null;
				FileOutputStream fos=null;
				
				try {
					fis=new FileInputStream(ss);
					fos=new FileOutputStream(ss1);
					byte [] b =new byte[20];
					int len;
					while((len=fis.read(b)) !=-1){
						fos.write(b, 0, len);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					
					if(fos !=null){
						try {
							fos.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}if(fis !=null){
						try {
							fis.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
}
