package com.boomhope.tms.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boomhope.tms.entity.BaseDict;
import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Manu;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.Peripherals;
import com.boomhope.tms.entity.PeripheralsMachine;
import com.boomhope.tms.model.Tree;
import com.boomhope.tms.report.MacManufacturer;
import com.boomhope.tms.report.MacManufacturerBean;
import com.boomhope.tms.report.MachineBean;
import com.boomhope.tms.report.MachineExcel;
import com.boomhope.tms.service.IMachineService;
import com.boomhope.tms.service.ISystemService;
import com.boomhope.tms.util.DateUtil;
import com.boomhope.tms.util.StringUtil;

/**
 *机器
 * 
 * @author gyw
 *
 */
@Controller
@Scope("prototype")
public class MachineAction extends BaseAction{
	
	private static final Log log = LogFactory.getLog(SystemAction.class);  
	
	IMachineService machineService;
	
	private ISystemService systemService;
	
	@Resource(name = "machineService")
	public void setMachineService(IMachineService machineService) {
		this.machineService = machineService;
	}
	
	@Resource(name = "systemService")
	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}


	/**
	 * 机器维护信息查询
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findMachineList")
	public @ResponseBody Object findMachineList(HttpServletRequest request,Model model, Integer page,Integer rows,Machine machine)  {
		JSONObject json = getReqData(request);
		Map<String,String> map = new HashMap<String,String>();
		if(json.get("machineType") != null){
 			map.put("machineType", String.valueOf(json.get("machineType")));
		}
		
 		if(json.get("manuName") != null){
			map.put("manuName", String.valueOf(json.get("manuName")));
		}
		
		if(json.get("machineName") != null){
			map.put("machineName", String.valueOf(json.get("machineName")));
		}
		
		Page pageInfo = getPageInfo(page,rows);
		
		List<Machine> list =  machineService.findMachineList(pageInfo, map);
	//	logger.debug("lll000"+list.size());
		if(list != null && list.size() > 0){
			Map<String,String> dictMap = getDict();
			for (Machine m : list) {
				m.setMachineTypeName(dictMap.get(m.getMachineType()));
			}
		}
		return returnResult(pageInfo, list);
	}
	
	/**
	 * 编辑机器维护信息
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/editMachine")
	public @ResponseBody Object editMachine(HttpServletRequest request,Model model, Machine machine) {
		JSONObject json = getReqData(request);
		String maueCode = String.valueOf(json.get("manuCode"));
		String machineCode = String.valueOf(json.get("machineCode"));
		String machineName = String.valueOf(json.get("machineName"));
		List<Machine> list  = machineService.findMachine(machineCode,machineName);
		if (list.size()>0) {
			 return this.returnFail("02");
		}
			Manu manu = new Manu();
			manu.setManuCode(maueCode);
			machine.setManu(manu);
			machineService.editMachine(machine);
			return this.returnSucess();
	}
	
	/**
	 * 删除机器维护信息
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/delMachine")
	public @ResponseBody Object delMachine(HttpServletRequest request,Model model, Machine machine) {
		List<DeployMachine> list = machineService.findDeployMachineList(machine.getMachineCode());
		if (list.size()>0) {
		return 	returnFail("此机器有所部署，无法删除");
		}else{	
		machineService.delMachine(machine.getMachineCode());
		//删除此机器关联的所有机器图片
		machineService.delMacMachinePic(machine.getMachineCode());
		return this.returnSucess();
		}
	}

	/**
	 * 添加机器
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/saveMachine")
	public @ResponseBody Object saveMachine(HttpServletRequest request,Model model, Machine machine) {
		BaseUser user = (BaseUser) request.getSession().getAttribute("loginUser");
		JSONObject json = getReqData(request);
		String maueCode = String.valueOf(json.get("manuCode"));	
		String machineCode = String.valueOf(json.get("machineCode"));
		String machineName = String.valueOf(json.get("machineName"));
		List<Machine> list  = machineService.findMachine1(machineCode);
		if (list.size()>0) {
			return this.returnFail("01");
		}
		List<Machine> list1  = machineService.findMachine2(machineName);
		if (list1.size()>0) {
			return this.returnFail("02");
		}
		Manu manu = new Manu();
		manu.setManuCode(maueCode);
		machine.setManu(manu);
		machine.setCreateDate(DateUtil.getDateTime("yyyyMMddHHmmss"));
		machine.setCreater(user.getUsername());
		machineService.saveMachine(machine);
		return this.returnSucess();
	}
	
	/**
	 * 机器维护
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findMachine")
	public @ResponseBody Object findMachine(HttpServletRequest request,Model model, Integer page,Integer rows,Machine machine)  {
		
		Map<String,String> map = new HashMap<String,String>();	
		map.put("manuCode", StringUtil.getSel(machine.getManu().getManuCode()));
		Page pageInfo = getPageInfo(page,rows);
		List<PeripheralsMachine> list =  machineService.findPeripherals(pageInfo, map);

		return returnResult(pageInfo, list);
	}
	
	/**
	 * 获取机器对应的外设
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findMacPer")
	public @ResponseBody Object findMacPer(HttpServletRequest request,Model model, Integer page,Integer rows,Machine machine)  {
		JSONObject json = getReqData(request);
		String machineCode = String.valueOf(json.get("machineCode"));
		// 获取已分配外设
		List<Integer> perM = machineService.findPeripheralsMachineByMachineCode(machineCode);
		// 获取全部外设
		List<Peripherals> per = machineService.findPeripheralsAll();
		
		List<Tree> treeList = new ArrayList<Tree> ();
		if(per != null && per.size() >0 ){
			Integer perId = null;
			for (Peripherals peripherals : per) {
				Tree tree = new Tree();
				tree.setId(peripherals.getPeriId()+"");
				tree.setName(peripherals.getPeriName());
				tree.setParentId("0");
				perId = peripherals.getPeriId();
				if(perM.indexOf(perId) != -1){
					tree.setChecked(true);
				}else{
					tree.setChecked(false);
				}
				treeList.add(tree);
			}
		}
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("treeList", JSONArray.fromObject(treeList));
		return this.returnSucess(map);
	}
	
	@RequestMapping(value = "/saveMacPer")
	public @ResponseBody Object saveMacPer(HttpServletRequest request,Model model, Machine machine) {
		JSONObject json = getReqData(request);
		String machineCode = String.valueOf(json.get("machineCode"));
		String perss = String.valueOf(json.get("per"));
		String [] pers = perss.split(",");
		machineService.savePeripheralsMachine(pers, machineCode);
		return this.returnSucess();
	}
	
	/**
	 * 获取机器类型对应的字典
	 * @return
	 */
	public Map<String,String> getDict(){
		Map<String,String> map = new HashMap<String,String>();
		List<BaseDict> dictList = systemService.getDict("machineType");
		for (BaseDict baseDict : dictList) {
			map.put(baseDict.getValueName(), baseDict.getValueDesc());
		}
		return map;
	}
	
	/**
	 * 不分页查询机器信息
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findMachineList1")
	public @ResponseBody Object findMachineList1(HttpServletRequest request,Model model, Integer page,Integer rows,Machine machine)  {
		List<Machine> list = machineService.findMachineList1(new HashMap());
		//查询机器类型
		if(list != null && list.size() > 0){
			Map<String,String> dictMap = getDict();
			for (Machine m : list) {
				m.setMachineTypeName(dictMap.get(m.getMachineType()));
				log.debug(m.getMachineTypeName());
				
			}
		}
		Map<Object, Object> map = new HashMap<Object, Object>();
		for (Machine responses : list) {
			log.debug(responses.getMachineTypeName());
			
		}
		map.put("rows", JSONArray.fromObject(list));		
		return returnSucess(map);
	}
	
	/**
	 * 导出 - 机器维护信息
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/outMachine")
	public @ResponseBody Object outMachine(HttpServletRequest request,Model model, Integer page,Integer rows,Machine machine)  {
		JSONObject json = getReqData(request);
		Map<String,String> map = new HashMap<String,String>();
		if(json.get("machineType") != null){
 			map.put("machineType", String.valueOf(json.get("machineType")));
		}
		
 		if(json.get("manuName") != null){
			map.put("manuName", String.valueOf(json.get("manuName")));
		}
		
		if(json.get("machineName") != null){
			map.put("machineName", String.valueOf(json.get("machineName")));
		}
		
		List<Machine> list =  machineService.findMachineListEXCEL(map);
		
		List<MachineBean> dataList = new ArrayList<>();
		if (list == null) {
			return returnFail("没有数据");
		}
		if (list.size()>0) {
			for (Machine mc : list) {
				MachineBean bean = new MachineBean();
				String machineType = mc.getMachineType();
				if ("01".equals(machineType)) {
					bean.setMachineType("填单机");
				}
				if ("02".equals(machineType)) {
					bean.setMachineType("发卡机");
				}
				//转换（code转换成那么打印）
				String manuCode = mc.getManu().getManuCode();
				String mu = machineService.findMachineListEXCELManuCode(manuCode);
				
				bean.setMachineCode(mc.getMachineCode());
				bean.setMachineDesc(mc.getMachineDesc());
				bean.setMachineName(mc.getMachineName());
				bean.setManuCode(mu);
				dataList.add(bean);
			}
		}
	//要生成的路径
		String 	path = request.getRealPath("./ReportTemplate/MachineEXCEt.xls");
			//服务器中当前项目的路径
			String path2 = request.getRealPath("./");
			try {
				boolean isSave = new MachineExcel(path2).makeReport(dataList, path);
				if (!isSave) {
					return returnFail("文件正在导出，请勿重复导出！");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		Map<Object, Object> map2 = new HashMap<Object, Object>();
		map2.put("url", path);
		return returnSucess(map2);
	
	}
	
}
