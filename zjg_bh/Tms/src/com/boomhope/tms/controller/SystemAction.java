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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boomhope.tms.entity.BaseDict;
import com.boomhope.tms.entity.BaseMenu;
import com.boomhope.tms.entity.BaseRole;
import com.boomhope.tms.entity.BaseRoleMenu;
import com.boomhope.tms.entity.BaseUnit;
import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.entity.BaseUserRoleMap;
import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.DeployMachineView;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.Peripherals;
import com.boomhope.tms.entity.UnitView;
import com.boomhope.tms.model.Tree;
import com.boomhope.tms.report.PeripheralsBean;
import com.boomhope.tms.report.PeripheralsExcel;
import com.boomhope.tms.report.UnitBean;
import com.boomhope.tms.report.UnitExcel;
import com.boomhope.tms.service.IDeployMachineService;
import com.boomhope.tms.service.ISystemService;
import com.boomhope.tms.util.DateUtil;
import com.boomhope.tms.util.Dict;
import com.boomhope.tms.util.MD5Util;

/**
 * 系统管理控制器
 * 
 * @author zy
 *
 */
@Controller
@Scope("prototype")
public class SystemAction extends BaseAction {  

	private static final Log log = LogFactory.getLog(SystemAction.class);

	private ISystemService systemService;   


	IDeployMachineService deployMachineService;
	
	@Resource(name = "systemService")
	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}
	
	@Resource(name = "deployMachineService")
	public void setDeployMachineService(IDeployMachineService deployMachineService) {
		this.deployMachineService = deployMachineService;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param request
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/system/getUserInfo")
	public @ResponseBody Object findBaseUserList(HttpServletRequest request,
			Model model, Integer page, Integer rows) {
		JSONObject json = getReqData(request);
		Map<String, String> map = new HashMap<String, String>();
		Object name = json.get("name");
		if (name != null && !"".equals(name)) {
			map.put("name", String.valueOf(name));
		}

		Page pageInfo = getPageInfo(page, rows);

		List<BaseUser> list = systemService.findBeseUserList(pageInfo, map);
		return returnResult(pageInfo, list);

	}

	/**
	 * 添加用户
	 * 
	 * @param request
	 * @param baseUser
	 * @return
	 */
	@RequestMapping(value = "/system/saveUser")
	public @ResponseBody Object saveOrUpdateUser(HttpServletRequest request) {
		BaseUser user = (BaseUser) request.getSession().getAttribute(
				"loginUser");
		JSONObject json = getReqData(request);
		String username = json.getString("username");
		String email = json.getString("email");
		String pwd = MD5Util.string2MD5("123456");
		String name = json.getString("name");
		
		List<BaseUser> list = systemService.findBuName(username);
		if (list.size()>0) {
			return returnFail("01");
		}
		List<BaseUser> list1 = systemService.findBuName1(email);
		if (list1.size()>0) {
			return returnFail("02");
		}
		BaseUser baseUser = new BaseUser();
		baseUser.setUsername(username);
		baseUser.setPwd(pwd);
		baseUser.setName(name);

		if (json.get("phone") != null) {
			baseUser.setPhone(String.valueOf(json.get("phone")));
		}
		if (json.get("email") != null) {
			baseUser.setEmail(String.valueOf(json.get("email")));
		}

		baseUser.setStatus(Dict.USER_STATUS_START);
		baseUser.setCreateDate(DateUtil.getDateTime("yyyyMMddHHmmss"));
		baseUser.setCreater(user.getUsername());
		systemService.saveBaseUser(baseUser);
		//添加默认角色
		BaseUserRoleMap baseUserRoleMap = new BaseUserRoleMap();
		baseUserRoleMap.setBaseUser(baseUser);
		List<BaseRole> all = systemService.findBaseRole();
		for (BaseRole baseRole: all) {
			if (baseRole.getRoleCode() == 2) {
				baseUserRoleMap.setBaseRole(baseRole);
			}
		}
		systemService.saveBaseRole(baseUserRoleMap);
		return this.returnSucess();
	}
	
	
	/**
	 * 更新用户
	 * @param request
	 * @param baseUser
	 * @return
	 */
	@RequestMapping(value = "/system/saveUser1")
	public @ResponseBody Object saveOrUpdateUser1(HttpServletRequest request,BaseUser baseUser) {
		BaseUser user = (BaseUser) request.getSession().getAttribute(
				"loginUser");
		List<BaseUser> list = systemService.findBeanEmail1(baseUser.getUsername(),baseUser.getEmail());
		if (list.size()>0) {
			return returnFail("02");
		}
		BaseUser base = systemService.findOne(baseUser.getUsername());
		base.setEmail(baseUser.getEmail());
		base.setPhone(baseUser.getPhone());
		base.setName(baseUser.getName());
		base.setStatus(Dict.USER_STATUS_START);
		base.setUpdateDate(DateUtil.getDateTime("yyyyMMddHHmmss"));
		base.setUpdater(user.getUsername());
		systemService.saveBaseUser(base);
		return this.returnSucess();
	}
	
	/**
	 * 删除用户
	 * 
	 * @param request
	 * @param model
	 * @param page
	 * @param rows
	 * @param baseUser
	 * @return
	 */
	@RequestMapping(value = "/system/del")
	public @ResponseBody Object delUser(HttpServletRequest request) {
		JSONObject json = getReqData(request);
		String username = json.getString("username");
		log.info("delUser username---->" + username);
		systemService.delBaseuser1(username);
		systemService.delBaseuser(username);
		return this.returnSucess();
	}

	/**
	 * 获取用户角色
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/system/getRole")
	public @ResponseBody Object getRoleList(HttpServletRequest request,Integer page, Integer rows) {
		JSONObject json = getReqData(request);
		Map<String,String> selmap = new HashMap<String,String>();
		if(json.get("roleName") != null){
			selmap.put("roleName", String.valueOf(json.get("roleName") ));
		}
		Page pageInfo = getPageInfo(page, rows);
		List<BaseRole> all = systemService.findBaseRoleList(pageInfo, selmap, "roleCode");
		Map<Object, Object> map = new HashMap<Object, Object>();
		//map.put("info", JSONArray.fromObject(all));
		//return returnSucess(map);
		return returnResult(pageInfo, all);
	}
	
	/**
	 * 获取用户角色
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/system/getUserRole")
	public @ResponseBody Object getUserRoleList(HttpServletRequest request) {
		JSONObject json = getReqData(request);
		String username = json.getString("username");
		log.info("getRoleList username---->" + username);

		// 获取全部角色
		List<BaseRole> all = systemService.findBaseRole();
		// 获取指定用户已选角色
		List<BaseRole> selected = systemService.getUserRole(username);
		List<String> list = new ArrayList<String>();
		for (BaseRole baseRole : selected) {
			list.add(String.valueOf(baseRole.getRoleCode()));
		}
		List<Tree> treeList = new ArrayList<Tree> ();
		if(all != null && all.size() >0 ){
			for (BaseRole baseRole : all) {
				Tree tree = new Tree();
				tree.setId(baseRole.getRoleCode()+"");
				tree.setName(baseRole.getRoleName());
				tree.setParentId("0");
				if(list.contains(String.valueOf(baseRole.getRoleCode()))){
					tree.setChecked(true);
				}else{
					tree.setChecked(false);
				}
				treeList.add(tree);
			}
		}
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("treeList", JSONArray.fromObject(treeList));
		return returnSucess(map);
	}

	/**
	 * 保存用户角色
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/system/saveUserRole")
	public @ResponseBody Object saveUserRole(HttpServletRequest request) {
		JSONObject json = getReqData(request);
		String username = json.getString("username");
		String perss = String.valueOf(json.get("per"));
		String [] pers = perss.split(",");
		systemService.saveUserRole(username, pers);
		return returnSucess();
	}
	/**
	 * 获取数据字典
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/system/getDict")
	public @ResponseBody Object getDict(HttpServletRequest request) {
		JSONObject json = getReqData(request);
		String groupName = json.getString("groupName");
		List<BaseDict> list = systemService.getDict(groupName);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("info", JSONArray.fromObject(list));
		return returnSucess(map);
	}

	/**
	 * 添加角色
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/system/addRole")
	public @ResponseBody Object addRole(HttpServletRequest request,
			BaseRole baseRole) {
		BaseUser user = (BaseUser) request.getSession().getAttribute("loginUser");
		baseRole.setCreateDate(DateUtil.getDateTime("yyyyMMddHHmmss"));
		baseRole.setCreater(user.getUsername());
		List<BaseRole> list = systemService.findBaseRoleTest(baseRole.getRoleName());
		if (list.size()>0) {
			return returnFail("01");
		}	
		systemService.saveRole(baseRole);
		//创建角色时添加的默认权限
	/*	BaseRoleMenu baseRoleMenu = new BaseRoleMenu();
		List<BaseMenu> roleMenuList = systemService.getBaseMenuList();
		for (BaseMenu baseMenu : roleMenuList) {
			if (baseMenu.getMenuPath().equals("chart.jsp")||baseMenu.getMenuPath().equals("map.jsp")) {
				baseRoleMenu.setBaseMenu(baseMenu);
			}
		}
		baseRoleMenu.setBaseRole(baseRole);
		systemService.saveBaseRoleMenu(baseRoleMenu);*/
		return returnSucess();
	}

	/**
	 * 编辑角色
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/system/editRole")
	public @ResponseBody Object editRole(HttpServletRequest request,
			BaseRole baseRole) {
		BaseUser user = (BaseUser) request.getSession().getAttribute("loginUser");
		JSONObject json = getReqData(request);
		int roleCode = baseRole.getRoleCode();
		List<BaseRole> list = systemService.findBaseRoleTest1(json.getString("roleName"),roleCode);
		if (list.size()>0) {
			return returnFail("01");
		}
		BaseRole role = new BaseRole();	
		role.setRoleCode(roleCode);
		role.setRoleName(json.getString("roleName"));
		role.setRoleDesc(json.getString("roleDesc"));
		role.setUpdateDate(DateUtil.getDateTime("yyyyMMddHHmmss"));
		role.setUpdater(user.getUsername());
		systemService.saveRole1(role);
		return returnSucess();
	}

	/**
	 * 删除角色
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/system/delRole")
	public @ResponseBody Object delRole(HttpServletRequest request) {
		JSONObject json = getReqData(request);
		String roleCode = json.getString("roleCode");
		if (roleCode != null && !"".equals(roleCode)) {
			systemService.delBaseRole(Integer.parseInt(roleCode));
			return returnSucess();
		} else {
			return returnFail("缺少参数");
		}
	}

	/**
	 * 获取角色菜单
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/system/getRoleMenu")
	public @ResponseBody Object getRoleMenu(HttpServletRequest request,
			BaseRole baseRole) {
		JSONObject json = getReqData(request);
		String roleCode = String.valueOf(json.get("roleCode"));
		if (roleCode != null && !"".equals(roleCode)) {
			// 获取角色对应的菜单
			List<BaseMenu> roleMenuList = systemService.getBaseMenuList(Integer.parseInt(roleCode));
			
			List treeList = new ArrayList();
			for (BaseMenu baseMenu : roleMenuList) {
				Tree tree = new Tree();
				tree.setId(String.valueOf(baseMenu.getMenuId()));
				tree.setName(baseMenu.getMenuName());
				tree.setParentId(String.valueOf(baseMenu.getParentId()));
				if(baseMenu.getSelectd() != null && baseMenu.getSelectd().equals("Y")){
					tree.setChecked(true);
				}else{
					tree.setChecked(false);
				}
				treeList.add(tree);
			}
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("roleMenu", JSONArray.fromObject(treeList));
			return returnSucess(map);
		} else {
			return returnFail("缺少参数！！！");
		}

	}
	
	/**
	 * 保存角色菜单
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/system/saveRoleMenu")
	public @ResponseBody Object saveRoleMenu(HttpServletRequest request) {
		JSONObject json = getReqData(request);
		String roleCode = String.valueOf(json.get("roleCode"));
		String menuIds = String.valueOf(json.get("menuIds"));
		String [] str = menuIds.split(",");
		systemService.saveRoleMenu(str, Integer.parseInt(roleCode));
		
		return this.returnSucess();
	}

	/**
	 * 机构查询
	 * @param request
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/system/findunit")
	public @ResponseBody Object findunit(HttpServletRequest request,
			Model model, Integer page, Integer rows) {
		JSONObject json = getReqData(request);
		Map<String, String> map = new HashMap<String, String>();
		Object unitName = json.get("unitName");
		if (unitName != null && !"".equals(unitName)) {
			map.put("unitName", String.valueOf(unitName));
		}

		Object parentName = json.get("parentName");
		if (parentName != null && !"".equals(parentName)) {
			map.put("parentName", String.valueOf(parentName));
		}
		Object unitType = json.get("unitType");
		if (unitType != null && !"".equals(unitType)) {
			map.put("unitType", String.valueOf(unitType));
		}

		Page pageInfo = getPageInfo(page, rows);
		List<UnitView> list = systemService.getUnitViewList(pageInfo, map);
		return returnResult(pageInfo, list);
	}
	
	/**
	 * 机构查询
	 * @param request
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/system/findParentUnit")
	public @ResponseBody Object findParentUnit(HttpServletRequest request,
			Model model, Integer page, Integer rows) {
		JSONObject json = getReqData(request);
		Map<String, String> selMap = new HashMap<String, String>();
		Object unitType = json.get("unitType");
		if (unitType != null && !"".equals(unitType)) {
			int i = Integer.parseInt(unitType+"");
			selMap.put("unitType", String.valueOf(i - 1));
		}
		List<UnitView> list = systemService.getUnitViewList(selMap);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("rows", JSONArray.fromObject(list));
		return returnSucess(map);
	}
	
	
	//添加机构
	@RequestMapping(value = "/system/saveUnit", produces = MediaType.APPLICATION_JSON_VALUE
	+ ";charset=utf-8")
	public @ResponseBody Object saveOrUpdateUnit(HttpServletRequest request) {
		BaseUser user = (BaseUser) request.getSession().getAttribute("loginUser");
		JSONObject json = getReqData(request);
		BaseUnit unit = new BaseUnit();
		String unitCode = String.valueOf(json.get("unitCode"));
		String unitName = String.valueOf(json.get("unitName"));
		List<BaseUnit> list = systemService.findBaseUnitCN(unitCode);
		if (list.size()>0) {
			return this.returnFail("01");
		}
		List<BaseUnit> list1 = systemService.findBaseUnitCN1(unitName);
		if (list1.size()>0) {
			return this.returnFail("02");
		}
		if(json.get("unitCode") != null){
			// 机构号
			unit.setUnitCode(String.valueOf(json.get("unitCode")));
		}
		if(json.get("unitName") != null){
			// 机构名称
			unit.setUnitName(String.valueOf(json.get("unitName")));
		}
		if(json.get("unitType") != null){
			// 机构类型
			unit.setUnitType(String.valueOf(json.get("unitType")));
		}
		if(json.get("address") != null){
			// 机构地址
			unit.setAddress(String.valueOf(json.get("address")));
		}
		if(json.get("tel") != null){
			// 机构电话
			unit.setTel(String.valueOf(json.get("tel")));
		}
		if(json.get("contactor") != null){
			// 机构负责人
			unit.setContactor(String.valueOf(json.get("contactor")));
		}
		if(json.get("email") != null){
			// email
			unit.setEmail(String.valueOf(json.get("email")));
		}
		if(json.get("contactorPhone") != null){
			// 手机
			unit.setContactorPhone(String.valueOf(json.get("contactorPhone")));
		}
		if(json.get("status") != null){
			// 状态
			unit.setStatus(String.valueOf(json.get("status")));
		}
		if(json.get("parentCode") != null){
			// 上级机构
			unit.setParentCode(String.valueOf(json.get("parentCode")));
		}
		if (json.get("cityS")!=null) {
			unit.setDistrictCounty(String.valueOf(json.get("cityS")));
		}
		unit.setCreateDate(DateUtil.getDateTime("yyyyMMddHHmmss"));
		unit.setCreater(user.getUsername());
		systemService.saveBaseUnit(unit);
		return returnSucess();
	}
	
	//编辑机构
	@RequestMapping(value = "/system/saveUnit1", produces = MediaType.APPLICATION_JSON_VALUE
	+ ";charset=utf-8")
	public @ResponseBody Object saveOrUpdateUnit1(HttpServletRequest request) {
		BaseUser user = (BaseUser) request.getSession().getAttribute("loginUser");
		JSONObject json = getReqData(request);
		BaseUnit unit = new BaseUnit();
		String unitCode = String.valueOf(json.get("unitCode"));
		String unitName = String.valueOf(json.get("unitName"));
		BaseUnit baseUnit = systemService.findcreatDate(unitCode);
		List<BaseUnit> list = systemService.findBaseUnitCN2(unitCode,unitName);
		if (list.size()>0) {
			return this.returnFail("02");
		}
		if(json.get("unitCode") != null){
			// 机构号
			unit.setUnitCode(String.valueOf(json.get("unitCode")));
		}
		if(json.get("unitName") != null){
			// 机构名称
			unit.setUnitName(String.valueOf(json.get("unitName")));
		}
		if(json.get("unitType") != null){
			// 机构类型
			unit.setUnitType(String.valueOf(json.get("unitType")));
		}
		if(json.get("address") != null){
			// 机构地址
			unit.setAddress(String.valueOf(json.get("address")));
		}
		if(json.get("tel") != null){
			// 机构电话
			unit.setTel(String.valueOf(json.get("tel")));
		}
		if(json.get("contactor") != null){
			// 机构负责人
			unit.setContactor(String.valueOf(json.get("contactor")));
		}
		if(json.get("email") != null){
			// email
			unit.setEmail(String.valueOf(json.get("email")));
		}
		if(json.get("contactorPhone") != null){
			// 手机
			unit.setContactorPhone(String.valueOf(json.get("contactorPhone")));
		}
		if(json.get("status") != null){
			// 状态
			unit.setStatus(String.valueOf(json.get("status")));
		}
		if(json.get("parentCode") != null){
			// 上级机构
			unit.setParentCode(String.valueOf(json.get("parentCode")));
		}
		if (json.get("cityS")!=null) {
			unit.setDistrictCounty(String.valueOf(json.get("cityS")));
		}
		unit.setCreateDate(baseUnit.getCreateDate());
		unit.setCreater(baseUnit.getCreater());
		unit.setUpdateDate(DateUtil.getDateTime("yyyyMMddHHmmss"));
		unit.setUpdater(user.getUsername());
		systemService.saveBaseUnit(unit);
		return returnSucess();
	}
	/**
	 * 删除机构
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/system/delUnit")
	public @ResponseBody Object delUnit(HttpServletRequest request) {
		BaseUnit baseUnit = new BaseUnit();
		JSONObject json = getReqData(request);
		String unitCode = (String) json.get("unitCode");
		
		baseUnit.setUnitCode(unitCode);
 		//查询机构是否已存在
		List<DeployMachineView> list = deployMachineService.DeployMachineLists(unitCode);
			if (list.size()>0) {
				//	baseUnit.setStatus("0");
				//	systemService.editBaseUnit1(baseUnit);
				return returnFail("此机构又机器正在投产中，无法删除");	
			}else{
					systemService.delBaseUnit(baseUnit);
				}
		return returnSucess();
	}
	
	/**
	 * 导出 - 机构数据
	 * @param request
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/outUnit")
	public @ResponseBody Object outUnit(HttpServletRequest request,
			Model model, Integer page, Integer rows) {
		JSONObject json = getReqData(request);
		Map<String, String> map = new HashMap<String, String>();
		Object unitName = json.get("unitName");
		if (unitName != null && !"".equals(unitName)) {
			map.put("unitName", String.valueOf(unitName));
		}

		Object parentName = json.get("parentName");
		if (parentName != null && !"".equals(parentName)) {
			map.put("parentName", String.valueOf(parentName));
		}
		Object unitType = json.get("unitType");
		if (unitType != null && !"".equals(unitType)) {
			map.put("unitType", String.valueOf(unitType));
		}
		List<UnitView> list = systemService.getUnitViewEXCEl(map);
		
		List<UnitBean> dataList = new ArrayList<>();
		if (list == null) {
			return returnFail("没有可导出的数据");
		}
		
		for (UnitView unit : list) {
			UnitBean ut = new UnitBean();
			String status = unit.getStatus();
			if ("0".equals(status)) {
				ut.setStatus("废弃");
			}
			if ("1".equals(status)) {
				ut.setStatus("正常");
			}
			String type = unit.getUnitType();
			if ("0".equals(type)) {
				ut.setUnitType("总行");
			}
			if ("1".equals(type)) {
				ut.setUnitType("分行");
			}
			if ("2".equals(type)) {
				ut.setUnitType("支行");
			}
			if ("3".equals(type)) {
				ut.setUnitType("网点");
			}
			ut.setUnitCode(unit.getUnitViewId().getUnitCode());
			ut.setUnitName(unit.getUnitName());
			ut.setAddress(unit.getAddress());
			ut.setContactor(unit.getContactor());
			ut.setEmail(unit.getEmail());
			ut.setParentName(unit.getParentName());
			ut.setTel(unit.getTel());
			dataList.add(ut);
		}
		//要生成的路径
		String path = request.getRealPath("./ReportTemplate/unitEXCEt.xls"); 
		//服务器中当前项目的路径
		String path2 = request.getRealPath("./");
		try {
			boolean isSave = new UnitExcel(path2).makeReport(dataList, path);
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
