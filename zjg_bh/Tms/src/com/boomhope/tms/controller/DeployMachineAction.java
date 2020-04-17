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
import javax.servlet.http.HttpServletResponse;

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

import com.boomhope.tms.entity.BaseUnit;
import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.DeployMachineView;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.StatisticsView;
import com.boomhope.tms.pojo.FindAllCountsPojo;
import com.boomhope.tms.pojo.UnitMachineStatusPojo;
import com.boomhope.tms.report.DeployMachineBean;
import com.boomhope.tms.report.DeployMachineExcel;
import com.boomhope.tms.service.IDeployMachineService;
import com.boomhope.tms.service.ISystemService;
import com.boomhope.tms.util.DateUtil;
import com.boomhope.tms.util.Dict;
import com.boomhope.tms.util.MD5Util;

@Controller
@Scope("prototype")
public class DeployMachineAction extends BaseAction {
	private static final Log log = LogFactory.getLog(SystemAction.class);

	private ISystemService systemService;

	IDeployMachineService deployMachineService;

	private Object[] objects;

	@Resource(name = "systemService")
	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}
	
	@Resource(name = "deployMachineService")
	public void setDeployMachineService(
			IDeployMachineService deployMachineService) {
		this.deployMachineService = deployMachineService;
	}

	/**
	 * 部署维护信息查询
	 * 
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findDeployMachineList")
	public @ResponseBody Object findDeployMachineList(
			HttpServletRequest request, Model model, Integer page, Integer rows) {
		JSONObject json = getReqData(request);
		Map<String, String> map = new HashMap<String, String>();
		// 机器编号
		if (json.get("machineNo") != null) {
			map.put("machineNo", String.valueOf(json.get("machineNo")));
		}
		// 机器名称
		if (json.get("machineName") != null) {
			map.put("machineName", String.valueOf(json.get("machineName")));
		}
		// 机器类型
		if (json.get("machineType") != null) {
			map.put("machineType", String.valueOf(json.get("machineType")));
		}
		// 所属厂商
		if (json.get("manuName") != null) {
			map.put("manuName", String.valueOf(json.get("manuName")));
		}
		// 所属机构
		if (json.get("unitName") != null) {
			map.put("unitName", String.valueOf(json.get("unitName")));
		}
		// 上报状态
		if (json.get("repStatus") != null) {
			map.put("repStatus", String.valueOf(json.get("repStatus")));
		}
		// 设备状态
		if (json.get("status") != null) {
			map.put("status", String.valueOf(json.get("status")));
		}
		//出厂编号
		if (json.get("macFacNum") != null) {
			map.put("macFacNum", String.valueOf(json.get("macFacNum")));
		}
		Page pageInfo = getPageInfo(page, rows);
		List<DeployMachineView> list = deployMachineService.findDeployMachineList(pageInfo, map);
		return returnResult(pageInfo, list);
	}

	/**
	 * 删除部署维护信息
	 * 
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/delDeployMachine")
	public @ResponseBody Object delDeployMachine(HttpServletRequest request,
			Model model, DeployMachine deployMachine) {
		List<DeployMachine> list = deployMachineService.findDeployMachineMC(deployMachine.getMachineNo());
		for (DeployMachine deployMachine2 : list) {
			String status = deployMachine2.getStatus();
			if (status.equals("1")) {
				return returnFail("设备投产中，不能删除");
			}
		}
		deployMachineService.delDeployMachine(deployMachine.getMachineNo());
		return this.returnSucess();
	}

	/**
	 * 添加部署信息
	 * 
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/saveDeployMachine", produces = MediaType.APPLICATION_JSON_VALUE
	+ ";charset=utf-8")
	public @ResponseBody Object saveDeployMachine(HttpServletRequest request,
			Model model) {
		BaseUser user = (BaseUser) request.getSession().getAttribute(
				"loginUser");
		JSONObject json = getReqData(request);
		String tellerNo = String.valueOf(json.get("tellerNo"));
		String machineCode = String.valueOf(json.get("machineCode"));
		String machineNo = String.valueOf(json.get("machineNo"));
		String ip = String.valueOf(json.get("ip"));
		String majorkeyflag = String.valueOf(json.get("keyboard_major_keyflag"));
		String workkeyflag = String.valueOf(json.get("keyboard_work_keyflag"));
		String keyflag = String.valueOf(json.get("key_flag"));
		String mackeyflag = String.valueOf(json.get("mac_keyflag"));
		String macFacNum = String.valueOf(json.get("macFacNum"));
		String districtCounty = (String) json.get("cityS");
		String unitCode = (String) json.get("unitCode");
		String channel = (String) json.get("channel");
		String merNo = (String) json.get("merNo");
		List<DeployMachine> list = deployMachineService.findDeployMachineMC(machineNo);
		if (list.size() > 0) {
			return returnFail("01");//机器部署编号重复
		}
		List<DeployMachine> list1 = deployMachineService.findDeployMachineMC1(ip);
		if (list1.size() > 0) {
			return returnFail("02");//ip地址重复
		}
		List<DeployMachine> list2 = deployMachineService.findDeployMachineMC3(macFacNum);
		if (list2.size() > 0) {
			return returnFail("03");//正在投产的机器不能部署到别的银行
		}
		DeployMachine deployMachine = new DeployMachine();
		Machine machine = new Machine();
		machine.setMachineCode(machineCode);
		deployMachine.setMachine(machine);

		BaseUnit unit = new BaseUnit();
		unit.setUnitCode(unitCode);
		deployMachine.setBaseUnit(unit);
		deployMachine.setMachineNo(machineNo);
		deployMachine.setMackeyflag(mackeyflag);
		deployMachine.setMajorkeyflag(majorkeyflag);
		deployMachine.setWorkkeyflag(workkeyflag);
		deployMachine.setKeyflag(keyflag);
		deployMachine.setMacFacNum(macFacNum);
		deployMachine.setManagePassword(MD5Util.string2MD5("123456"));
		deployMachine.setIp(ip);
		deployMachine.setTellerNo(tellerNo);
		deployMachine.setCreateDate(DateUtil.getDateTime("yyyyMMddHHmmss"));
		deployMachine.setUpdateDate(DateUtil.getDateTime("yyyyMMddHHmmss"));
		deployMachine.setStatus("0");
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = format.format(new Date());
		deployMachine.setRepTime(time);//机器刚部署时的默认服务器时间
		deployMachine.setRepStatus("1");//机器初始部署时的及其状态默认为正常（"1"）
		deployMachine.setRepDesc("");//默认正常状态下的机器回复信息
		deployMachine.setUpdater(user.getUsername());
		deployMachine.setCreater(user.getUsername());
		deployMachine.setDistrictCounty(districtCounty);
		deployMachine.setMerNo(merNo);
		deployMachine.setChannel(channel);
		deployMachineService.saveDeployMachine(deployMachine);
		return this.returnSucess();
	}

	/**
	 * 根据区县查询本区地址
	 * 
	 * @param request
	 * @param model
	 * @param baseUnit
	 * @return
	 */
	@RequestMapping(value = "/findUnitName")
	public @ResponseBody Object findUnitName(HttpServletRequest request,
			Model model, BaseUnit baseUnit) {
		BaseUser user = (BaseUser) request.getSession().getAttribute(
				"loginUser");
		JSONObject json = getReqData(request);
		String districtCounty = String.valueOf(json.get("cityS"));
		List<BaseUnit> list = systemService.getBaseUnitCityS(districtCounty);
		return list;
	}

	/**
	 * 编辑部署维护信息
	 * 
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/editDeployMachine", produces = MediaType.APPLICATION_JSON_VALUE
	+ ";charset=utf-8" )
	public @ResponseBody Object editDeployMachine(HttpServletRequest request,
			Model model) {
		BaseUser user = (BaseUser) request.getSession().getAttribute(
				"loginUser");
		DeployMachine deployMachine = new DeployMachine();
		JSONObject json = getReqData(request);
		deployMachine.setTellerNo((String)json.get("tellerNo"));
		deployMachine.setMachineNo((String)json.get("machineNo"));
		deployMachine.setIp((String)json.get("ip"));
		deployMachine.setMajorkeyflag((String)json.get("keyboard_major_keyflag"));
		deployMachine.setWorkkeyflag((String)json.get("keyboard_work_keyflag"));
		deployMachine.setMackeyflag((String)json.get("mac_keyflag"));
		deployMachine.setKeyflag((String)json.get("key_flag"));
		deployMachine.setMacFacNum((String)(json.get("macFacNum")));
		deployMachine.setDistrictCounty((String) json.get("cityS"));
		deployMachine.setChannel((String) json.get("channel"));
		deployMachine.setMerNo((String) json.get("merNo"));
		Machine mc = new Machine();
		mc.setMachineCode((String)json.get("machineCode"));
		deployMachine.setMachine(mc);
		BaseUnit unit = new BaseUnit();
		unit.setUnitCode((String) json.get("unitCode"));
		deployMachine.setBaseUnit(unit);
		deployMachine.setUpdateDate(DateUtil.getDateTime("yyyyMMddHHmmss"));
		deployMachine.setUpdater(user.getUsername());
		String status = ""; 
		List<DeployMachine> list = deployMachineService.findDeployMachineMC(deployMachine.getMachineNo());
		for (DeployMachine dm : list) {
			status = dm.getStatus();
		}
		List<DeployMachine> list1 = deployMachineService.findDeployMachineMC1(deployMachine.getIp());
		if (list1.size()>1) {
			return returnFail("02");
		}
		List<DeployMachine> list2 = deployMachineService.findDeployMachineMC33(deployMachine.getMacFacNum());
		if (list2.size() > 0) {  
			return returnFail("03");
		}
		deployMachine.setStatus(status);
		deployMachineService.editDeployMachine(deployMachine);
		return this.returnSucess();
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/editDeployMachine1")
	public @ResponseBody Object editDeployMachine1(HttpServletRequest request,
			Model model, DeployMachine deployMachine) {
		BaseUser user = (BaseUser) request.getSession().getAttribute(
				"loginUser");
		deployMachine.setManagePassword(MD5Util.string2MD5(deployMachine
				.getManagePassword()));
		deployMachineService.editDeployMachine1(deployMachine);
		return this.returnSucess();
	}

	/**
	 * 修改状态
	 * 
	 * @param request
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/editDeployMachine2")
	public @ResponseBody Object editDeployMachine2(HttpServletRequest request,
			Model model, DeployMachine deployMachine) {
		BaseUser user = (BaseUser) request.getSession().getAttribute(
				"loginUser");
		//判断这台机器状态
		List<DeployMachine> list = deployMachineService.editDeployMachine22(deployMachine);
		if (list.size()>0) {
			return returnFail("投产失败，本机器已在其他银行投产");
		}
		deployMachineService.editDeployMachine2(deployMachine);
		return this.returnSucess();
	}

	/**
	 * 按照机器类型来统计各个类型的数量
	 * 
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/queryDeployMachineType", produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	public @ResponseBody Object queryDeployMachineType(
			HttpServletRequest request, HttpServletResponse response) {
		List<StatisticsView> list = deployMachineService.queryDeployMachineType();
		JSONArray jsonArray = new JSONArray();
		for (StatisticsView statisticsView : list) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", statisticsView.getSize());
			jsonObject.put("name", statisticsView.getName());
			jsonArray.add(jsonObject);
		}
		log.debug(jsonArray.toString());
		return jsonArray.toString();
	}

	/**
	 * 按照机器的上报状态来统计数量
	 * 
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/queryDeployMachineRepStatus", produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	public @ResponseBody Object queryDeployMachineRepStatus(
			HttpServletRequest request, HttpServletResponse response) {
		List<StatisticsView> list = deployMachineService
				.queryDeployMachineRepStatus();
		JSONArray jsonArray = new JSONArray();
		for (StatisticsView statisticsView : list) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", statisticsView.getSize());
			if (statisticsView.getName() == null) {
				jsonObject.put("name", "未知");
			} else if (statisticsView.getName().equals(Dict.REP_STATUS_NORMAL)) {
				jsonObject.put("name", "正常");
			} else if (statisticsView.getName().equals(Dict.REP_STATUS_ERROR)) {
				jsonObject.put("name", "异常");
			}
			jsonArray.add(jsonObject);
		}
		log.debug(jsonArray.toString());
		return jsonArray.toString();
	}

	/**
	 * 按照机器的使用状态来统计数量
	 * 
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/queryDeployMachineStatus", produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	public @ResponseBody Object queryDeployMachineStatus(
			HttpServletRequest request, HttpServletResponse response) {
		List<StatisticsView> list = deployMachineService
				.queryDeployMachineStatus();
		JSONArray jsonArray = new JSONArray();
		for (StatisticsView statisticsView : list) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", statisticsView.getSize());
			if (statisticsView.getName().equals(Dict.DEPLOY_INI)) {
				jsonObject.put("name", "初始");
			} else if (statisticsView.getName().equals(Dict.DEPLOY_USE)) {
				jsonObject.put("name", "已投产");
			} else if (statisticsView.getName().equals(Dict.DEPLOY_DEL)) {
				jsonObject.put("name", "已下线");
			}
			jsonArray.add(jsonObject);
		}
		log.debug(jsonArray.toString());
		return jsonArray.toString();
	}

	/**
	 * 修改状态和备注
	 * 
	 * @param request
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/editDeployMachineRemark")
	public @ResponseBody Object editDeployMachineRemark(
			HttpServletRequest request, Model model, DeployMachine deployMachine) {
		BaseUser user = (BaseUser) request.getSession().getAttribute(
				"loginUser");
		log.debug(deployMachine.getStatus());
		deployMachineService.editDeployMachineremark(deployMachine);
		return this.returnSucess();
	}

	/**
	 * 按照机器类型来统计各个类型的数量（区县）
	 * 
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/queryDeployMachineType1", produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	public @ResponseBody Object queryDeployMachineType1(
			HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = getReqData(request);
		String districtCounty = (String) json.get("districtCounty");
		List<StatisticsView> list = deployMachineService
				.queryDeployMachineType1(districtCounty);
		JSONArray jsonArray = new JSONArray();
		for (StatisticsView statisticsView : list) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", statisticsView.getSize());
			jsonObject.put("name", statisticsView.getName());
			jsonArray.add(jsonObject);
		}
		log.debug(jsonArray.toString());
		return jsonArray.toString();
	}

	/**
	 * 按照机器的上报状态来统计数量（区县）
	 * 
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/queryDeployMachineRepStatus1", produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	public @ResponseBody Object queryDeployMachineRepStatus1(
			HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = getReqData(request);
		String districtCounty = (String) json.get("districtCounty");
		List<StatisticsView> list = deployMachineService
				.queryDeployMachineRepStatus1(districtCounty);
		JSONArray jsonArray = new JSONArray();
		for (StatisticsView statisticsView : list) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", statisticsView.getSize());
			if (statisticsView.getName() == null) {
				jsonObject.put("name", "未知");
			} else if (statisticsView.getName().equals(Dict.REP_STATUS_NORMAL)) {
				jsonObject.put("name", "正常");
			} else if (statisticsView.getName().equals(Dict.REP_STATUS_ERROR)) {
				jsonObject.put("name", "异常");
			}
			jsonArray.add(jsonObject);
		}
		log.debug(jsonArray.toString());
		return jsonArray.toString();
	}

	/**
	 * 按照机器的使用状态来统计数量（区县）
	 * 
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/queryDeployMachineStatus1", produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	public @ResponseBody Object queryDeployMachineStatus1(
			HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = getReqData(request);
		String districtCounty = (String) json.get("districtCounty");
		List<StatisticsView> list = deployMachineService
				.queryDeployMachineStatus1(districtCounty);
		JSONArray jsonArray = new JSONArray();
		for (StatisticsView statisticsView : list) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", statisticsView.getSize());
			if (statisticsView.getName().equals(Dict.DEPLOY_INI)) {
				jsonObject.put("name", "初始");
			} else if (statisticsView.getName().equals(Dict.DEPLOY_USE)) {
				jsonObject.put("name", "已投产");
			} else if (statisticsView.getName().equals(Dict.DEPLOY_DEL)) {
				jsonObject.put("name", "已下线");
			}
			jsonArray.add(jsonObject);
		}
		log.debug(jsonArray.toString());
		return jsonArray.toString();
	}

	/**
	 * 按照机器类型来统计各个类型的数量（分行、支行）通过机构名称查询 机器名称和个数
	 * 
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/queryDeployMachineType2", produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	public @ResponseBody Object queryDeployMachineType2(
			HttpServletRequest request, HttpServletResponse response,
			DeployMachine deployMachine) {
		JSONObject json = getReqData(request);
		String unitCode = (String) json.get("unitCode");
		List<StatisticsView> list = deployMachineService
				.queryDeployMachineType2(unitCode);
		JSONArray jsonArray = new JSONArray();
		for (StatisticsView statisticsView : list) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", statisticsView.getSize());
			jsonObject.put("name", statisticsView.getName());
			jsonArray.add(jsonObject);
		}
		log.debug(jsonArray.toString());
		return jsonArray.toString();
	}

	/**
	 * 通过机构名称查询 机器名称和使用次数
	 * 
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/queryDeployMachineType22", produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	public @ResponseBody Object queryDeployMachineType22(
			HttpServletRequest request, HttpServletResponse response,
			DeployMachine deployMachine) {
		JSONObject json = getReqData(request);
		Map<String, String> map = new HashMap<String, String>();
		String unitCode = (String) json.get("unitCode");
		map.put("unitCode", unitCode);
		String kstime1 = (String) json.get("kstime");
		if (kstime1 != null && !"".equals(kstime1)) {
			String kstime = kstime1 + "000000";
			if (kstime != null && !"".equals(kstime)) {
				map.put("kstime", kstime);
			}
		}

		String jstime1 = (String) json.get("jstime");
		if (jstime1 != null && !"".equals(jstime1)) {
			String jstime = jstime1 + "235959";
			if (jstime != null && !"".equals(jstime)) {
				map.put("jstime", jstime);
			}
		}
		List<StatisticsView> list1 = deployMachineService
				.queryDeployMachineType22(map);
		JSONArray jsonArray = new JSONArray();
		for (StatisticsView statisticsView : list1) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", statisticsView.getSize());
			jsonObject.put("name", statisticsView.getName());
			jsonArray.add(jsonObject);
		}
		log.debug(jsonArray.toString());
		return jsonArray.toString();
	}

	/**
	 * 通过业务名称查询 机构名称和使用次数
	 * 
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/queryDeployMachineType23", produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	public @ResponseBody Object queryDeployMachineType23(
			HttpServletRequest request, HttpServletResponse response,
			DeployMachine deployMachine) {
		JSONObject json = getReqData(request);
		Map<String, String> map = new HashMap<String, String>();
		String projectName = (String) json.get("projectName");
		map.put("projectName", projectName);
		String kstime1 = (String) json.get("kstime");
		if (kstime1 != null && !"".equals(kstime1)) {
			String kstime = kstime1 + "000000";
			if (kstime != null && !"".equals(kstime)) {
				map.put("kstime", kstime);
			}
		}
		String jstime1 = (String) json.get("jstime");
		if (jstime1 != null && !"".equals(jstime1)) {
			String jstime = jstime1 + "235959";
			if (jstime != null && !"".equals(jstime)) {
				map.put("jstime", jstime);
			}
		}
		List<StatisticsView> list1 = deployMachineService
				.queryDeployMachineType23(map);
		JSONArray jsonArray = new JSONArray();
		for (StatisticsView statisticsView : list1) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", statisticsView.getSize());
			jsonObject.put("name", statisticsView.getName());
			jsonArray.add(jsonObject);
		}
		log.debug(jsonArray.toString());
		return jsonArray.toString();
	}

	/**
	 * 通过机器型号查询 机构名称和使用次数
	 * 
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/queryDeployMachineType24", produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	public @ResponseBody Object queryDeployMachineType24(
			HttpServletRequest request, HttpServletResponse response,
			DeployMachine deployMachine) {
		JSONObject json = getReqData(request);
		Map<String, String> map = new HashMap<String, String>();
		String machineCode = (String) json.get("machineCode");
		map.put("machineCode", machineCode);
		String kstime1 = (String) json.get("kstime");
		if (kstime1 != null && !"".equals(kstime1)) {
			String kstime = kstime1 + "000000";
			if (kstime != null && !"".equals(kstime)) {
				map.put("kstime", kstime);
			}
		}
		String jstime1 = (String) json.get("jstime");
		if (jstime1 != null && !"".equals(jstime1)) {
			String jstime = jstime1 + "235959";
			if (jstime != null && !"".equals(jstime)) {
				map.put("jstime", jstime);
			}
		}
		List<StatisticsView> list1 = deployMachineService
				.queryDeployMachineType23(map);
		JSONArray jsonArray = new JSONArray();
		for (StatisticsView statisticsView : list1) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", statisticsView.getSize());
			jsonObject.put("name", statisticsView.getName());
			jsonArray.add(jsonObject);
		}
		log.debug(jsonArray.toString());
		return jsonArray.toString();
	}

	/**
	 * 按照机器的上报状态来统计数量（分行、支行）
	 * 
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/queryDeployMachineRepStatus2", produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	public @ResponseBody Object queryDeployMachineRepStatus2(
			HttpServletRequest request, HttpServletResponse response,
			DeployMachine deployMachine) {
		JSONObject json = getReqData(request);
		String unitCode = (String) json.get("unitCode");
		List<StatisticsView> list = deployMachineService
				.queryDeployMachineRepStatus2(unitCode);
		JSONArray jsonArray = new JSONArray();
		for (StatisticsView statisticsView : list) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", statisticsView.getSize());
			if (statisticsView.getName() == null) {
				jsonObject.put("name", "未知");
			} else if (statisticsView.getName().equals(Dict.REP_STATUS_NORMAL)) {
				jsonObject.put("name", "正常");
			} else if (statisticsView.getName().equals(Dict.REP_STATUS_ERROR)) {
				jsonObject.put("name", "异常");
			}
			jsonArray.add(jsonObject);
		}
		log.debug(jsonArray.toString());
		return jsonArray.toString();
	}

	/**
	 * 按照机器的使用状态来统计数量（分行、支行）
	 * 
	 * @param request
	 * @param manu
	 * @return
	 */
	@RequestMapping(value = "/queryDeployMachineStatus2", produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	public @ResponseBody Object queryDeployMachineStatus2(
			HttpServletRequest request, HttpServletResponse response,
			DeployMachine deployMachine) {
		JSONObject json = getReqData(request);
		String unitCode = (String) json.get("unitCode");
		List<StatisticsView> list = deployMachineService
				.queryDeployMachineStatus2(unitCode);
		JSONArray jsonArray = new JSONArray();
		for (StatisticsView statisticsView : list) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", statisticsView.getSize());
			if (statisticsView.getName().equals(Dict.DEPLOY_INI)) {
				jsonObject.put("name", "初始");
			} else if (statisticsView.getName().equals(Dict.DEPLOY_USE)) {
				jsonObject.put("name", "已投产");
			} else if (statisticsView.getName().equals(Dict.DEPLOY_DEL)) {
				jsonObject.put("name", "已下线");
			}
			jsonArray.add(jsonObject);
		}
		log.debug(jsonArray.toString());
		return jsonArray.toString();
	}

	// /**
	// * 通过unit_code 查询machine_no
	// * @param request
	// * @param page
	// * @param rows
	// * @return
	// * @throws Exception findDeployMachineList
	// */
	// @RequestMapping(value = "/findDeployMachineList11")
	// public @ResponseBody Object findMachineList1(HttpServletRequest
	// request,Model model, Integer page,Integer rows,DeployMachine
	// deployMachine) {
	// List<DeployMachineView> list =
	// deployMachineService.findDeployMachineList(new HashMap());
	// Map<Object, Object> map = new HashMap<Object, Object>();
	// map.put("rows", JSONArray.fromObject(list));
	// return returnSucess(map);
	// }

	/**
	 * 网点地图机器状态标记 - 默认显示（只显示当前异常机器的数据）
	 * @param request
	 * @param manu
	 * @return
	 */

	@RequestMapping(value = "/findUnitMachineStatus", produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	public @ResponseBody Object findUnitMachineStatus(
			HttpServletRequest request, HttpServletResponse response,
			Model model, Integer page, Integer rows) {
		List<UnitMachineStatusPojo> list1 = deployMachineService.findUnitMachineStatus();
		List<UnitMachineStatusPojo> list = new ArrayList<>();
		if (list1 == null) {
			 List<UnitMachineStatusPojo> list2 = deployMachineService.findUnitMachineStatus2();
			 for (UnitMachineStatusPojo unPojo2 : list2) {
				 UnitMachineStatusPojo pojo = new UnitMachineStatusPojo();
				 	String machineCode = unPojo2.getMachineCode();
					String creatdate = deployMachineService.findUnitMachinePic(machineCode);
					String pic = deployMachineService.findUnitMachinePic2(machineCode, creatdate);
				 
				 pojo.setDistrictCounty(unPojo2.getDistrictCounty());
				 pojo.setUnitName(unPojo2.getUnitName());
				 pojo.setMachineNo(unPojo2.getMachineNo());
				 pojo.setMachineType(unPojo2.getMachineType());
				 pojo.setMachineName(unPojo2.getMachineName());
				 pojo.setRepStatus(unPojo2.getRepStatus());
				 pojo.setMachineCode(machineCode);
				 pojo.setRepTime(unPojo2.getRepTime());
				 pojo.setRepDesc(unPojo2.getRepDesc());
				 pojo.setPic(pic);
				 SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				 String dqTime = format.format(new Date());
				 pojo.setDqTime(dqTime);
				 list.add(pojo);
			}
			return list;
		}else {
		if (list1.size() > 0) {
			for (UnitMachineStatusPojo unPojo : list1) {

				String machineCode = unPojo.getMachineCode();
				String creatdate = deployMachineService.findUnitMachinePic(machineCode);
				String pic = deployMachineService.findUnitMachinePic2(machineCode, creatdate);

				UnitMachineStatusPojo pojo = new UnitMachineStatusPojo();
				
				pojo.setDistrictCounty(unPojo.getDistrictCounty());
				pojo.setUnitName(unPojo.getUnitName());
				pojo.setMachineNo(unPojo.getMachineNo());
				pojo.setMachineType(unPojo.getMachineType());
				pojo.setMachineName(unPojo.getMachineName());
				pojo.setRepStatus(unPojo.getRepStatus());
				pojo.setMachineCode(machineCode);
				pojo.setRepTime(unPojo.getRepTime());
				pojo.setRepDesc(unPojo.getRepDesc());
				pojo.setPic(pic);
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				String dqTime = format.format(new Date());
				pojo.setDqTime(dqTime);
				list.add(pojo);
			}
		}
		return list;
		}
	}
	
	/**
	 * 网点地图机器状态标记 = 下拉框 - 全部数据（正常+异常）
	 * @param request
	 * @param manu
	 * @return
	 */

	@RequestMapping(value = "/findUnitMachineStatus01", produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	public @ResponseBody Object findUnitMachineStatus1(
			HttpServletRequest request, HttpServletResponse response,
			Model model, Integer page, Integer rows) {
		List<UnitMachineStatusPojo> list1 = deployMachineService.findUnitMachineStatus1();
		List<UnitMachineStatusPojo> list = new ArrayList<>();
		if (list1.isEmpty()) {
			return null;
		}else {
		if (list1.size() > 0) {
			for (UnitMachineStatusPojo unPojo : list1) {

				String machineCode = unPojo.getMachineCode();
				String creatdate = deployMachineService.findUnitMachinePic(machineCode);
				String pic = deployMachineService.findUnitMachinePic2(machineCode, creatdate);

				UnitMachineStatusPojo pojo = new UnitMachineStatusPojo();
				
				pojo.setDistrictCounty(unPojo.getDistrictCounty());
				pojo.setUnitName(unPojo.getUnitName());
				pojo.setMachineNo(unPojo.getMachineNo());
				pojo.setMachineType(unPojo.getMachineType());
				pojo.setMachineName(unPojo.getMachineName());
				pojo.setRepStatus(unPojo.getRepStatus());
				pojo.setMachineCode(machineCode);
				pojo.setRepTime(unPojo.getRepTime());
				pojo.setRepDesc(unPojo.getRepDesc());
				pojo.setPic(pic);
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				String dqTime = format.format(new Date());
				pojo.setDqTime(dqTime);
				list.add(pojo);
			}
		}
		return list;
		}
	}
	
	/**
	 * 网点地图机器状态标记 = 下拉框-正常机器数据
	 * @param request
	 * @param manu
	 * @return
	 */

	@RequestMapping(value = "/findUnitMachineStatus02", produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	public @ResponseBody Object findUnitMachineStatus2(
			HttpServletRequest request, HttpServletResponse response,
			Model model, Integer page, Integer rows) {
		List<UnitMachineStatusPojo> list1 = deployMachineService.findUnitMachineStatus2();
		List<UnitMachineStatusPojo> list = new ArrayList<>();
		if (list1.isEmpty()) {
			return null;
		}else {
		if (list1.size() > 0) {
			for (UnitMachineStatusPojo unPojo : list1) {

				String machineCode = unPojo.getMachineCode();
				String creatdate = deployMachineService.findUnitMachinePic(machineCode);
				String pic = deployMachineService.findUnitMachinePic2(machineCode, creatdate);

				UnitMachineStatusPojo pojo = new UnitMachineStatusPojo();
				
				pojo.setDistrictCounty(unPojo.getDistrictCounty());
				pojo.setUnitName(unPojo.getUnitName());
				pojo.setMachineNo(unPojo.getMachineNo());
				pojo.setMachineType(unPojo.getMachineType());
				pojo.setMachineName(unPojo.getMachineName());
				pojo.setRepStatus(unPojo.getRepStatus());
				pojo.setMachineCode(machineCode);
				pojo.setRepTime(unPojo.getRepTime());
				pojo.setRepDesc(unPojo.getRepDesc());
				pojo.setPic(pic);
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				String dqTime = format.format(new Date());
				pojo.setDqTime(dqTime);
				list.add(pojo);
			}
		}
		return list;
		}
	}
	
	/**
	 * 网点地图机器状态标记 = 下拉框-异常机器数据
	 * @param request
	 * @param manu
	 * @return
	 */

	@RequestMapping(value = "/findUnitMachineStatus03", produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	public @ResponseBody Object findUnitMachineStatus3(
			HttpServletRequest request, HttpServletResponse response,
			Model model, Integer page, Integer rows) {
		List<UnitMachineStatusPojo> list1 = deployMachineService.findUnitMachineStatus3();
		List<UnitMachineStatusPojo> list = new ArrayList<>();
		if (list1.isEmpty()) {
			return null;
		}else {
		if (list1.size() > 0) {
			for (UnitMachineStatusPojo unPojo : list1) {

				String machineCode = unPojo.getMachineCode();
				String creatdate = deployMachineService.findUnitMachinePic(machineCode);
				String pic = deployMachineService.findUnitMachinePic2(machineCode, creatdate);

				UnitMachineStatusPojo pojo = new UnitMachineStatusPojo();
				
				pojo.setDistrictCounty(unPojo.getDistrictCounty());
				pojo.setUnitName(unPojo.getUnitName());
				pojo.setMachineNo(unPojo.getMachineNo());
				pojo.setMachineType(unPojo.getMachineType());
				pojo.setMachineName(unPojo.getMachineName());
				pojo.setRepStatus(unPojo.getRepStatus());
				pojo.setMachineCode(machineCode);
				pojo.setRepTime(unPojo.getRepTime());
				pojo.setRepDesc(unPojo.getRepDesc());
				pojo.setPic(pic);
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				String dqTime = format.format(new Date());
				pojo.setDqTime(dqTime);
				list.add(pojo);
			}
		}
		return list;
		}
	}
	
	/**
	 * 导出Excel - 部署维护
	 */
	/**
	 * 部署维护信息查询
	 * 
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/outDeployMachineExcel")
	public @ResponseBody Object outDeployMachineExcel(
			HttpServletRequest request, Model model, Integer page, Integer rows) {
		JSONObject json = getReqData(request);
		Map<String, String> map = new HashMap<String, String>();
		// 机器编号
		if (json.get("machineNo") != null) {
			map.put("machineNo", String.valueOf(json.get("machineNo")));
		}
		// 机器名称
		if (json.get("machineName") != null) {
			map.put("machineName", String.valueOf(json.get("machineName")));
		}
		// 机器类型
		if (json.get("machineType") != null) {
			map.put("machineType", String.valueOf(json.get("machineType")));
		}
		// 所属厂商
		if (json.get("manuName") != null) {
			map.put("manuName", String.valueOf(json.get("manuName")));
		}
		// 所属机构
		if (json.get("unitName") != null) {
			map.put("unitName", String.valueOf(json.get("unitName")));
		}
		// 上报状态
		if (json.get("repStatus") != null) {
			map.put("repStatus", String.valueOf(json.get("repStatus")));
		}
		// 设备状态
		if (json.get("status") != null) {
			map.put("status", String.valueOf(json.get("status")));
		}
		//出厂编号
		if (json.get("macFacNum") != null) {
			map.put("macFacNum", String.valueOf(json.get("macFacNum")));
		}
		
		List<DeployMachineView> list = deployMachineService.findDeployMachineListExcel(map);
		
		List<DeployMachineBean> dataList = new ArrayList<>();
		if (list == null) {
			return returnFail("没有可导出的数据");
		}
		if (list.size()>0) {
			for (DeployMachineView dv : list) {
				DeployMachineBean db = new DeployMachineBean();
				String status = dv.getStatus();
				if ("0".equals(status)) {
					db.setStatus("初始");//设备状态
				}
				if ("1".equals(status)) {
					db.setStatus("投产");//设备状态
				}
				if ("2".equals(status)) {
					db.setStatus("下线");//设备状态
				}
				String machineType = dv.getMachineType();
				if ("01".equals(machineType)) {
					db.setMachineType("存单回收机");//机器类型
				}
				if ("02".equals(machineType)) {
					db.setMachineType("存单机");//机器类型
				}
				db.setManuName(dv.getManuName());//所属厂商
				db.setDistrictCounty(dv.getDistrictCounty());
				db.setMacFacNum(dv.getMacFacNum());
				db.setMachineName(dv.getMachineName());
				db.setMachineNo(dv.getId().getMachineNo());
				db.setUnitName(dv.getUnitName());
				dataList.add(db);
			}
		}
		//要生成的路径
		String path = request.getRealPath("./ReportTemplate/DeployMachineExcel.xls"); 
		//服务器中当前项目的路径
		String path2 = request.getRealPath("./");
		try {
			boolean isSave = new DeployMachineExcel(path2).makeReport(dataList, path);
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
	
	
	/**
	 * 信息轮播接口
	 * WU CHAO
	 */
	@RequestMapping(value = "/findAllCounts")
	public @ResponseBody Object findAllCounts(HttpServletRequest request, HttpServletResponse response,Model model) {
		List<FindAllCountsPojo> list = new ArrayList<FindAllCountsPojo>();
		FindAllCountsPojo pojo = new FindAllCountsPojo();
		Long machineCounts = deployMachineService.findMachineCounts();
		List<FindAllCountsPojo> list1 = deployMachineService.findMachineTypeCounts();
		int size = list1.size();
		Long machineInitialCounts = deployMachineService.findMachineInitialCounts();
		Long machineOperationCounts = deployMachineService.findMachineOperationCounts();
		Long machineOfflineCounts = deployMachineService.findMachineOfflineCounts();
		Long machineNormalCounts = deployMachineService.findMachineNormalCounts();
		Long machineErrCounts = deployMachineService.findMachineErrCounts();
		Long machineUnknownCounts = deployMachineService.findMachineUnknownCounts();
		
		pojo.setMachineCounts(machineCounts+"");
		pojo.setMachineErrCounts(machineErrCounts+"");
		pojo.setMachineInitialCounts(machineInitialCounts+"");
		pojo.setMachineNormalCounts(machineNormalCounts+"");
		pojo.setMachineOfflineCounts(machineOfflineCounts+"");
		pojo.setMachineOperationCounts(machineOperationCounts+"");
		pojo.setMachineTypeCounts(size+"");
		pojo.setMachineUnknownCounts(machineUnknownCounts+"");
		
		list.add(pojo);
		
		return list;
	}
	
}
