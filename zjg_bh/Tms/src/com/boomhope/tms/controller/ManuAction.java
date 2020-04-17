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

import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Manu;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.report.MacManufacturer;
import com.boomhope.tms.report.MacManufacturerBean;
import com.boomhope.tms.service.IMachineService;
import com.boomhope.tms.service.IManuService;
import com.boomhope.tms.util.DateUtil;

/**
 * 厂商管理控制器
 * 
 * @author zy
 *
 */
@Controller
@Scope("prototype")
public class ManuAction extends BaseAction{
	
	private static final Log log = LogFactory.getLog(SystemAction.class);
	IManuService manuService;
	IMachineService machineService;
	
	@Resource(name = "manuService")
	public void setManuService(IManuService manuService) {
		this.manuService = manuService;
	}

	@Resource(name = "machineService")
	public void setmachineService(IMachineService machineService) {
		this.machineService = machineService;
	}
	/**
	 * 厂商信息查询
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findManu")
	public @ResponseBody Object findManu(HttpServletRequest request,Model model, Integer page,Integer rows)  {
		JSONObject json = getReqData(request);
		Map<String,String> map = new HashMap<String,String>();
		
		if (json.get("manuName") != null) {
			map.put("manuName", String.valueOf(json.get("manuName")));
		}
		if (json.get("manuStatus") != null) {
			map.put("manuStatus", String.valueOf(json.get("manuStatus")));
		}
		
	//	map.put("manuName", manu.getManuName());
	//	map.put("manuStatus", StringUtil.getSel(manu.getManuStatus()));
		Page pageInfo = getPageInfo(page,rows);
		
		List<Manu> list =  manuService.findManuList(pageInfo, map);
		
		return returnResult(pageInfo, list);
	}
	
	/**
	 * 不分页查询厂商信息
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findManuList")
	public @ResponseBody Object findManuList(HttpServletRequest request,Model model, Integer page,Integer rows,Manu manu)  {
		List<Manu> list = manuService.findManuList(new HashMap());
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("rows", JSONArray.fromObject(list));
		return returnSucess(map);
	}
	
	
	
	/**
	 * 编辑厂商信息
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/editManu")
	public @ResponseBody Object editManu(HttpServletRequest request,Model model, Manu manu) {
		BaseUser user = (BaseUser) request.getSession().getAttribute("loginUser");
		JSONObject json = getReqData(request);
		String manuCode = String.valueOf(json.get("manuCode"));
		String manuName = String.valueOf(json.get("manuName"));
		List<Manu> list  = manuService.findManu2(manuCode,manuName);
		if (list.size()>0) {
			 return this.returnFail("02");
		}
		manu.setCreater(user.getUsername());
		manuService.editManu(manu);
		return this.returnSucess();
	}
	
	/**
	 * 删除厂商信息
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/delManu")
	public @ResponseBody Object delManu(HttpServletRequest request,Model model, Manu manu) {
		BaseUser user = (BaseUser) request.getSession().getAttribute("loginUser");
		log.debug(manu.getManuCode());
		JSONObject json = getReqData(request);		
		String maueCode = String.valueOf(json.get("manuCode"));

		//查询机器表是否使用该厂商
		List<Machine> list = machineService.findMachineLists(maueCode);	
		if (list.size()>0) {
			manu.setManuStatus("2");
			manuService.editManu1(manu);
			return 	returnFail("此厂商废弃");
		}else{
		manuService.delManu(manu.getManuCode());
		}
		return returnSucess();
	
	}
	/**
	 * 添加厂商信息
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/saveManu")
	public @ResponseBody Object saveManu(HttpServletRequest request,Model model, Manu manu) {
		JSONObject json = getReqData(request);
		String flag = String.valueOf(json.get("flag"));
		BaseUser user = (BaseUser) request.getSession().getAttribute("loginUser");	
		String manuCode = String.valueOf(json.get("manuCode"));
		String manuName = String.valueOf(json.get("manuName"));
		List<Manu> list  = manuService.findManu(manuCode);
		if (list.size()>0) {
			return this.returnFail("01");
		}	
		List<Manu> list1  = manuService.findManu1(manuName);
		if (list1.size()>0) {
			return this.returnFail("02");
		}
		manu.setCreateDate(DateUtil.getDateTime("yyyyMMddHHmmss"));
		manu.setCreater(user.getUsername());
		manuService.saveManu(manu);
		return this.returnSucess();
	}
	/**
	 * 不分页查询机器信息
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findManuList1")
	public @ResponseBody Object findManuList1(HttpServletRequest request,Model model, Integer page,Integer rows,Manu manu)  {
		List<Manu> list = manuService.findManuList(new HashMap());
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("rows", JSONArray.fromObject(list));
		return returnSucess(map);
	}
	
	@RequestMapping("/organ")
    public String organIndex(HttpServletRequest request,Integer page,Integer rows)  {
		
    		return "redirect:view/manu/organ.jsp";
    }
	
	
	/**
	 * 导出厂商信息
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	@RequestMapping(value = "/outManuEXCE")
	public @ResponseBody Object outManuEXCE(HttpServletRequest request,Model model)  {
		JSONObject json = getReqData(request);
		/*SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String createTime = format.format(new Date());//当前服务器时间
*/		Map<String,String> map = new HashMap<String,String>();
		
		if (json.get("manuName") != null) {
			map.put("manuName", String.valueOf(json.get("manuName")));
		}
		if (json.get("manuStatus") != null) {
			map.put("manuStatus", String.valueOf(json.get("manuStatus")));
		}
		
		List<Manu> list =  manuService.findManuList(map);
		
		List<MacManufacturerBean> dataList = new ArrayList<>();
			if (list == null) {
				return returnFail("没有数据");
			}
			if (list.size()>0) {
				for (Manu manu : list) {
					MacManufacturerBean bean = new MacManufacturerBean();
					String string = manu.getManuStatus();
					if ("1".equals(string)) {
						bean.setManuStatus("正常");
					}
					if("2".equals(string)){
						bean.setManuStatus("废弃");
					}
					bean.setManuCode(manu.getManuCode());
					bean.setConPerson(manu.getConPerson());
					bean.setManuDesc(manu.getManuDesc());
					bean.setManuName(manu.getManuName());
					bean.setPhone(manu.getPhone());
					bean.setTel(manu.getTel());
					dataList.add(bean);
				}
			}
		//要生成的路径
			String 	path = request.getRealPath("./ReportTemplate/ManuEXCEt.xls");
				//服务器中当前项目的路径
				String path2 = request.getRealPath("./");
				try {
					boolean isSave = new MacManufacturer(path2).makeReport2( dataList, path);
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
