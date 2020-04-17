package com.boomhope.tms.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boomhope.tms.entity.AuthenticFlow;
import com.boomhope.tms.entity.BusBillMan;
import com.boomhope.tms.entity.BusFlow;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.orcl.CdjOrder;
import com.boomhope.tms.pojo.ReturnAccountPojo;
import com.boomhope.tms.pojo.ReturnCloseAccountPojo;
import com.boomhope.tms.pojo.ReturnForBusFlowPojo;
import com.boomhope.tms.report.BckFlowReport;
import com.boomhope.tms.report.BckFlowReportBean;
import com.boomhope.tms.report.CdjAccountBean;
import com.boomhope.tms.report.CdjAccountExcel;
import com.boomhope.tms.report.CdjOrderExcel;
import com.boomhope.tms.report.ReturnCloseAccountBean;
import com.boomhope.tms.report.ReturnCloseAccountExcel;
import com.boomhope.tms.service.IAuthenticFlowService;
import com.boomhope.tms.service.IFlowService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 业务管理
 * 
 * @author zy
 *
 */
@Controller
@Scope("prototype")
public class AuthenticFlowAction extends BaseAction {

	private static final Log log = LogFactory.getLog(LoginAction.class);

	IAuthenticFlowService authenticFlowService;

	@Resource(name = "AuthenticFlowImpl")
	public void setAuthenticFlowService(IAuthenticFlowService authenticFlowService) {
		this.authenticFlowService = authenticFlowService;
	}

	/**
	 * 鉴伪流水查询
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findAuthenticFlowList")
	public @ResponseBody Object findFlowList(HttpServletRequest request, Model model, Integer page, Integer rows) {
		JSONObject json = getReqData(request);
		Map<String, String> map = new HashMap<String, String>();
		if (json.get("unitName") != null) {
			map.put("unitName", String.valueOf(json.get("unitName")));
		}

		if (json.get("status") != null) {
			map.put("status", String.valueOf(json.get("status")));
		}

		if (json.get("machineNo")!=null) {
			map.put("machineNo", String.valueOf(json.get("machineNo")));
		}
		
		if (json.get("flowDate")!=null) {
			map.put("flowDate", String.valueOf(json.get("flowDate")));
		}
		
		Page pageInfo = getPageInfo(page, rows);
		try {
			List list = authenticFlowService.getAuthenticFlowList(map, pageInfo);
			return returnResult(pageInfo, list);
		} catch (Exception e) {
			log.equals(e);
			return returnFail("信息查询失败");
		}
	}

	/**
	 * 导出鉴伪流水查询
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportAuthenticFlowList")
	public @ResponseBody Object exportFlowList(HttpServletRequest request, Model model, Integer page, Integer rows) {
		JSONObject json = getReqData(request);
		Map<String, String> map = new HashMap<String, String>();
		if (json.get("unitName") != null) {
			map.put("unitName", String.valueOf(json.get("unitName")));
		}
		if (json.get("flowDate")!=null) {
			map.put("flowDate", String.valueOf(json.get("flowDate")));
		}
		List<Map<String,String>> list=null;
		try {
			list = authenticFlowService.getAuthenticFlowList(map);
		} catch (Exception e) {
			log.equals(e);
			return returnFail("信息查询失败");
		}
		if(list==null||list.size()==0){
			return returnFail("信息查询位空");
		}
		Map m1=mapToMap(list.get(list.size()-1));
		m1.put("0","0");
		List<Map> dataList=new ArrayList<Map>();
		dataList.add(m1);
		list.remove(list.size()-1);
		for (int i = 0; i < list.size(); i++) {
			Map m=mapToMap(list.get(i));
			m.put("0", (i+1));
			dataList.add(m);
		}
		//要生成的路径
		String 	path = request.getRealPath("./ReportTemplate/AuthenticFlowExcel1.xls");
		//服务器中当前项目的路径
		String path2 = request.getRealPath("./")+"/ReportTemplate/AuthenticFlowExcel.xls";
		try {
			boolean isSave = new CdjOrderExcel(path2).makeReport(dataList, path);
			if (!isSave) {
				return returnFail("文件正在导出，请勿重复导出！");
			}
			Map<Object, Object> map2 = new HashMap<Object, Object>();
			map2.put("url", path);
			return returnSucess(map2);
		} catch (Exception e) {
			log.error(e);
			return returnFail("导出失败");
		}		
	}
	/**
	 * 转换数据格式
	 * @param map
	 * @return
	 */
	private Map mapToMap(Map<String,String> map){
		Map<String,String> dataMap=new HashMap<String,String>();
		dataMap.put("1", map.get("unitName"));
		dataMap.put("2", map.get("unitCode"));
		dataMap.put("3", map.get("status0"));
		dataMap.put("4", map.get("status1"));
		dataMap.put("5", map.get("status2"));
		dataMap.put("6", map.get("status4"));
		dataMap.put("7", map.get("status6"));
		dataMap.put("8", map.get("tgl"));
		dataMap.put("9", map.get("tgl6"));
		return dataMap;
	}
}
