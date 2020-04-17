package com.boomhope.tms.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.Peripherals;
import com.boomhope.tms.entity.PeripheralsMachine;
import com.boomhope.tms.pojo.ReturnForBusFlowPojo;
import com.boomhope.tms.report.BckFlowReport;
import com.boomhope.tms.report.BckFlowReportBean;
import com.boomhope.tms.report.PeripheralsBean;
import com.boomhope.tms.report.PeripheralsExcel;
import com.boomhope.tms.service.IPeripheralsService;
import com.boomhope.tms.util.DateUtil;
import com.boomhope.tms.util.StringUtil;

import net.sf.json.JSONObject;

/**
 * 外设维护
 * 
 * @author gyw
 *
 */
@Controller
@Scope("prototype")
public class PeripheralsAction extends BaseAction {

	public IPeripheralsService peripheralsService;

	@Resource(name = "peripheralsService")
	public void setPeripheralsService(IPeripheralsService peripheralsService) {
		this.peripheralsService = peripheralsService;
	}

	/**
	 * 外设维护信息查询
	 * 
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findPeripherals")
	public @ResponseBody Object findPeripherals(HttpServletRequest request, Model model, Integer page, Integer rows,
			Peripherals peripherals) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("periName", peripherals.getPeriName());
		map.put("periType", StringUtil.getSel(peripherals.getPeriType()));
		map.put("periCode", peripherals.getPeriCode());
		Page pageInfo = getPageInfo(page, rows);

		List<Peripherals> list = peripheralsService.findPeripheralsList(pageInfo, map);

		return returnResult(pageInfo, list);

	}

	/**
	 * 编辑外设维护信息
	 * 
	 * @param request
	 * @param peripherals
	 * @return
	 */
	@RequestMapping(value = "/editPeripherals")
	public @ResponseBody Object editPeripherals(HttpServletRequest request, Model model, Peripherals peripherals) {
		JSONObject json = getReqData(request);
		BaseUser user = (BaseUser) request.getSession().getAttribute("loginUser");
		String periId = String.valueOf(json.get("periId"));
		List<Peripherals> list = peripheralsService.findperiCod(periId, peripherals.getPeriCode());
		if (list.size() > 0) {
			return this.returnFail("01");
		}
		List<Peripherals> list1 = peripheralsService.findperiName(periId, peripherals.getPeriName());
		if (list1.size() > 0) {
			return this.returnFail("02");
		}

		Peripherals pp = new Peripherals();
		pp.setPeriId(peripherals.getPeriId());
		pp.setPeriCode(peripherals.getPeriCode());
		pp.setPeriName(peripherals.getPeriName());
		pp.setPeriType(peripherals.getPeriType());
		peripheralsService.updatePeripherals(pp);
		return this.returnSucess();
	}

	/**
	 * 删除外设维护信息
	 * 
	 * @param request
	 * @param peripherals
	 * @return
	 */
	@RequestMapping(value = "/delPeripherals")
	public @ResponseBody Object delPeripherals(HttpServletRequest request, Model model, Peripherals peripherals) {
		List<PeripheralsMachine> list = peripheralsService.findMachineList(peripherals.getPeriId());
		if (list.size()>0) {
			return returnFail("此外设已被机器关联，无法删除");
		}
		peripheralsService.delPeripherals(peripherals.getPeriId());
		return this.returnSucess();
	}

	/**
	 * 添加外设维护信息
	 * 
	 * @param request
	 * @param peripherals
	 * @return
	 */
	@RequestMapping(value = "/savePeripherals")
	public @ResponseBody Object savePeripherals(HttpServletRequest request, Model model, Peripherals peripherals) {
		BaseUser user = (BaseUser) request.getSession().getAttribute("loginUser");
		peripherals.setCreateDate(DateUtil.getDateTime("yyyyMMddHHmmss"));
		JSONObject json = getReqData(request);
		String periCode = String.valueOf(json.get("periCode"));
		String periName = String.valueOf(json.get("periName"));
		List<Peripherals> list = peripheralsService.findPeripherals(periCode);
		if (list.size() > 0) {
			return this.returnFail("01");
		}
		List<Peripherals> list1 = peripheralsService.findPeripherals(periName);
		if (list1.size() > 0) {
			return this.returnFail("02");
		}
		peripherals.setCreater(user.getUsername());
		peripheralsService.savePeripherals(peripherals);

		return this.returnSucess();
	}

	/**
	 * 导出 - 外设维护数据
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/outPeripherals")
	public @ResponseBody Object outPeripherals(HttpServletRequest request, Model model, Integer page, Integer rows) {
		JSONObject json = getReqData(request);
		Map<String, String> map = new HashMap<String, String>();
		if (json.get("periName")!= null ) {
			map.put("periName", String.valueOf(json.get("periName")));
		}
		
		if (json.get("periType")!= null ) {
			map.put("periType", String.valueOf(json.get("periType")));
		}
		
		if (json.get("periCode")!= null ) {
			map.put("periCode", String.valueOf(json.get("periCode")));
		}
		List<Peripherals> list = peripheralsService.findPeripheralsList(map);
	
		List<PeripheralsBean> dataList = new ArrayList<>();
		if (list == null) {
			return returnFail("没有可导出的数据");
		}
		
		for (Peripherals peripherals : list) {
			PeripheralsBean ph = new PeripheralsBean();
			ph.setPeriId(peripherals.getPeriId());
			ph.setPeriName(peripherals.getPeriName());
			ph.setPeriType(peripherals.getPeriType());
			ph.setPeriCode(peripherals.getPeriCode());
			ph.setCreateDate(peripherals.getCreateDate());
			ph.setCreater(peripherals.getCreater());
			dataList.add(ph);
		}
		
	
		//要生成的路径
		String path = request.getRealPath("./ReportTemplate/Peripherals.xls"); 
		//服务器中当前项目的路径
		String path2 = request.getRealPath("./");
		try {
			boolean isSave = new PeripheralsExcel(path2).makeReport(dataList, path);
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
