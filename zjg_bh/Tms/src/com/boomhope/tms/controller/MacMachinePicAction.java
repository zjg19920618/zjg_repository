package com.boomhope.tms.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.MacMachinePic;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Manu;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.service.IMacMachinePicService;
import com.boomhope.tms.util.DateUtil;

/**
 *图片
 * 
 * @author gyw
 *
 */
@Controller
@Scope("prototype")
public class MacMachinePicAction  extends BaseAction{
	
	private static final Log log = LogFactory.getLog(SystemAction.class); 
	
	IMacMachinePicService macMachinePicService;
	
	 @Value("#{configProperties['Tms_connectIp']}")
	private String connectIp;
	 
	 @Value("#{configProperties['Tms_concatenateIP']}")
	private String concatenateIP; 
	
	@Resource(name = "macMachinePicService")
	public void setMacMachinePicService(IMacMachinePicService macMachinePicService) {
		this.macMachinePicService = macMachinePicService;
	}
	
	
	/**
	 * 图片查询
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findMacMachinePicList")
	public @ResponseBody Object findMacMachinePicList(HttpServletRequest request,Model model)  {
		JSONObject json = getReqData(request);
		Map<String,String> map = new HashMap<String,String>();
		// 图片
		if(json.get("machineCode") != null){
			map.put("machineCode", String.valueOf(json.get("machineCode")));
		}
		
		List<MacMachinePic> list = macMachinePicService.findMachinePicList(map);

		return list;
	}
	
	/**
	 * 删除图片
	 * @param request
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/delMacMachinePic")
	public @ResponseBody Object delMacMachinePic(HttpServletRequest request,Model model, MacMachinePic macMachinePic) {
		JSONObject json = getReqData(request);
		String machineCode = (String) json.get("machineCode");
		String Pic = (String) json.get("Pic");
		String pic = "/img/MachineToCheckThe/"+Pic;
		macMachinePicService.delMacMachinePic(machineCode,pic);

		return this.returnSucess();
	}
	
	/**
	 * 图片地址
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findMacMachinePicList1")
	public @ResponseBody Object findMacMachinePicList1(HttpServletRequest request,Model model, Integer page,Integer rows)  {
		MacMachinePic macMachinePic =new MacMachinePic();
		macMachinePic.setPic(connectIp);
		return macMachinePic;
	}
	
	/**
	 * 添加图片
	 * @param request
	 * @param manu
	 * @return
	 */
	
	 @SuppressWarnings("deprecation")
	@RequestMapping("/saveMacMachinePic")  
	 @ResponseBody
	    public JSONObject addUser(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request){  
		 JSONObject json = getReqData(request);
		 String machineCode = String.valueOf(json.get("machineCode"));
		 MacMachinePic macMachinePic = new MacMachinePic();
	        for(int i = 0;i<files.length;i++){  
	        	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	        	String	name = format.format(new Date());
	        	String  filename = files[i].getOriginalFilename();
	            String  imageName=  filename.substring(filename.lastIndexOf(".")+1);
	            String  path = name+"."+imageName;  
	            if(!files[i].isEmpty()){  
	         //     int pre = (int) System.currentTimeMillis();  
	                try {  
	                	//拿到输出流，同时重命名上传的文件  
	                	String realPath = request.getRealPath("./");
	                	FileOutputStream os = new FileOutputStream(realPath + "/img/MachineToCheckThe/"+ path);  
	                    //拿到上传文件的输入流  
	                    FileInputStream in = (FileInputStream) files[i].getInputStream();  
	                    //以写字节的方式写文件  
	                    int b = 0;  
	                    while((b=in.read()) != -1){  
	                        os.write(b);  
	                    } 
	                    os.flush();  
	                    os.close();  
	                    in.close();  
	             //       int finaltime = (int) System.currentTimeMillis();  
	               } catch (Exception e) {  
	                    e.printStackTrace();  
	                    return returnFail("上传失败！"); 
	                }  
	            }  
	            macMachinePic.setMachineCode(machineCode);
	            macMachinePic.setPic("/img/MachineToCheckThe/"+ path);
	            SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmss");
	            String creatDate = format1.format(new Date());
	            macMachinePic.setCreatDate(creatDate);
	            macMachinePicService.saveMacMachinePic(macMachinePic);
	        }  
	        return returnSucess();  
	    } 
	
}
