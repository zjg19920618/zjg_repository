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
public class FlowAction extends BaseAction {

	private static final Log log = LogFactory.getLog(LoginAction.class);

	IFlowService flowService;

	@Resource(name = "flowService")
	public void setFlowService(IFlowService flowService) {
		this.flowService = flowService;
	}

	/**
	 * 销户流水查询
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findFlowList")
	public @ResponseBody Object findFlowList(HttpServletRequest request, Model model, Integer page, Integer rows) {
		JSONObject json = getReqData(request);
		Map<String, String> map = new HashMap<String, String>();
		if (json.get("unitCode") != null) {
			map.put("unitCode", String.valueOf(json.get("unitCode")));
		}

		if (json.get("projectName") != null) {
			map.put("projectName", String.valueOf(json.get("projectName")));
		}

		if (json.get("bankCardNo") != null) {
			map.put("bankCardNo", String.valueOf(json.get("bankCardNo")));
		}

		if (json.get("bankCardName") != null) {
			map.put("bankCardName", String.valueOf(json.get("bankCardName")));
		}
		
		if (json.get("machineNo")!=null) {
			map.put("machineNo", String.valueOf(json.get("machineNo")));
		}
		
		if (json.get("billNo")!=null) {
			map.put("billNo", String.valueOf(json.get("billNo")));
		}
		
		if (json.get("certNo")!=null) {
			map.put("certNo", String.valueOf(json.get("certNo")));
		}
		
		if (json.get("checkStatus")!=null) {
			map.put("checkStatus", String.valueOf(json.get("checkStatus")));
		}
		
		String kstime1 = (String) json.get("kstime");
		if (kstime1!= null&&!"".equals(kstime1)) {
			String kstime = kstime1+"000000";
			if (kstime!= null&&!"".equals(kstime)) {
				map.put("kstime", kstime);
			}
		}
		String jstime1 = (String) json.get("jstime");
		if (jstime1 != null && !"".equals(jstime1)) {
			String jstime = jstime1+"235959";
			if (jstime != null && !"".equals(jstime)) {
				map.put("jstime", jstime);
			}
		}
		Page pageInfo = getPageInfo(page, rows);
		List<BusFlow> list = flowService.findFlowList(pageInfo, map);
		return returnResult(pageInfo, list);
	}
	
	/**
	 * 销户查询导出 - 业务销户次数以及销户方式
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/outFlowList")
	public @ResponseBody Object outFlowList(HttpServletRequest request) {
		JSONObject json = getReqData(request);
		Map<String, String> map = new HashMap<String, String>();
		if (json.get("unitCode") != null) {
			map.put("unitCode", String.valueOf(json.get("unitCode")));
		}

		if (json.get("projectName") != null) {
			map.put("projectName", String.valueOf(json.get("projectName")));
		}

		if (json.get("bankCardNo") != null) {
			map.put("bankCardNo", String.valueOf(json.get("bankCardNo")));
		}

		if (json.get("bankCardName") != null) {
			map.put("bankCardName", String.valueOf(json.get("bankCardName")));
		}
		
		if (json.get("machineNo")!=null) {
			map.put("machineNo", String.valueOf(json.get("machineNo")));
		}
		
		if (json.get("billNo")!=null) {
			map.put("billNo", String.valueOf(json.get("billNo")));
		}
		
		if (json.get("certNo")!=null) {
			map.put("certNo", String.valueOf(json.get("certNo")));
		}
		//销户方式 - 为空 则默认导出全自动销户数据
		String checkStatu = (String) json.get("checkStatus");
		if (checkStatu!=null && !"".equals(checkStatu)) {
			map.put("checkStatus", checkStatu);
		}
		
		//开始时间
		String kstime1 = (String) json.get("kstime");
		if (kstime1!= null&&!"".equals(kstime1)) {
			String kstime = kstime1+"000000";
			if (kstime!= null&&!"".equals(kstime)) {
				map.put("kstime", kstime);
			}
		}
		//结束时间
		String jstime1 = (String) json.get("jstime");
		if (jstime1 != null && !"".equals(jstime1)) {
			String jstime = jstime1+"235959";
			if (jstime != null && !"".equals(jstime)) {
				map.put("jstime", jstime);
			}
		}
		List<ReturnCloseAccountPojo> list = flowService.outFlowList(map);
		List<ReturnCloseAccountBean> dataList = new ArrayList<>();
		if (list == null) {
			return returnFail("没有可导出的数据");
		}
		if (list.size()>0) {
			for (ReturnCloseAccountPojo pojo : list) {
				ReturnCloseAccountBean bean = new ReturnCloseAccountBean();
				bean.setUnitCode(pojo.getUnitCode());
				bean.setUnitName(pojo.getUnitName());
				bean.setMachineNo(pojo.getMachineNo());
				bean.setProjectName(pojo.getProjectName());
				bean.setNumber(pojo.getNumber());
				dataList.add(bean);
			}
		}
		//要生成的路径
		String path = request.getRealPath("./ReportTemplate/colseAccountExcel.xls"); 
		//服务器中当前项目的路径
		String path2 = request.getRealPath("./");
		try {
			boolean isSave = new ReturnCloseAccountExcel(path2).makeReport(dataList, path);
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
	 * 销户流水统计
	 * 
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findStatisticsList")
	public @ResponseBody Object findStatisticsList(HttpServletRequest request, Model model, Integer page,
			Integer rows) {
		JSONObject json = getReqData(request);
		Map<String, String> map = new HashMap<String, String>();
		if (json.get("unitCode") != null) {
			map.put("unitCode", String.valueOf(json.get("unitCode")));
		}
		if (json.get("projectName") != null) {
			map.put("projectName", String.valueOf(json.get("projectName")));
		}
		String kstime1 = String.valueOf(json.get("kstime"));
		String kstime2 = kstime1.replaceAll("-", "");
		if (kstime1!=null&&!"".equals(kstime1)) {
			String kstime = kstime2+"000000";
			if (kstime!= null) {
				map.put("kstime", kstime);
			}
		}
		String jstime1 = String.valueOf(json.get("jstime"));
		String jstime2 = jstime1.replaceAll("-", "");
		if (jstime1!=null&&!"".equals(jstime1)) {
			String jstime = jstime2+"235959";
			if (jstime != null) {
				map.put("jstime", jstime);
			}
		}
		Page pageInfo = getPageInfo(page, rows);
		List<ReturnForBusFlowPojo> list = flowService.findStatisticsList(pageInfo, map);
		return returnResult(pageInfo, list);
	}

	/**
	 * 销户统计导出
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	@RequestMapping(value = "/outExcel")
	public @ResponseBody Object outExcel(HttpServletRequest request, Model model, Integer page, Integer rows) {
	
		JSONObject json = getReqData(request);
		String startDate = "";
		String endDate = "";
		String projectName = (String) json.get("projectName");
		String unitCode = (String) json.get("unitCode");
		
		Map<String, String> map = new HashMap<String, String>();
		// 开始
		String startDate1 = (String) json.get("ksTime");
		String startDate2 = startDate1.replaceAll("-", "");
		if (startDate2!= null&&!"".equals(startDate2)) {
			 startDate = startDate2+"000000";
			if (startDate!= null&&!"".equals(startDate)) {
				map.put("startDate", startDate);
			}
		}
		// 结束
		String endDate1 = (String) json.get("jsTime");
		String endDate2 = endDate1.replaceAll("-", "");
		if (endDate2!= null&&!"".equals(endDate2) ) {
			endDate = endDate2+"235959";
			if (endDate!= null&&!"".equals(endDate) ) {
				map.put("endDate", endDate);
			}
		}else{
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String	date = format.format(new Date());
			endDate = date +"235959";
			map.put("endDate", endDate);
			
		}
		if (projectName!= null ) {
				map.put("projectName", projectName);
		}
		
		if (unitCode!= null ) {
			map.put("unitCode", unitCode);
		}
		
		List<ReturnAccountPojo> list = flowService.findBusFlowBySEDate(map);
		
		if (list == null || list.isEmpty()) {
			return returnSucess();
		}
		//根据机构名称 对项目名称以及数目进行分组
		Map<String, CdjAccountBean> temp = new HashMap<>();
		
		for (ReturnAccountPojo pojo : list) {
			Set<String> branchNames = temp.keySet();
			String branchName = pojo.getUnitName();//机构名称
			CdjAccountBean bean = new CdjAccountBean();
			if (branchNames.contains(branchName)) {
				bean = temp.get(branchName);
			}else{
				bean.setAllAmt(0);
				bean.setAllNum(0);
			}
			//更新
			bean = getAccountBean(pojo, bean);
			if(bean == null){
				continue;
			}
			bean.setBranchName(branchName);
			temp.put(branchName, bean);
		}
		List<CdjAccountBean> dataList = new ArrayList<>();
		
		//将所有机构合并为集合
		for (String branchName : temp.keySet()) {
			dataList.add(temp.get(branchName));
		}
		//要生成的路径
		String path = request.getRealPath("./ReportTemplate/BckReport.xls"); 
		//服务器中当前项目的路径
		String path2 = request.getRealPath("./");
		try {
			boolean isSave = new CdjAccountExcel(path2).makeReport(startDate, endDate, dataList, path);
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
	 * 销户不分页查询业务流水
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findFlowLists")
	public @ResponseBody Object findFlowLists(HttpServletRequest request,Model model, Integer page,Integer rows,Machine machine)  {
		List<BusFlow> list = flowService.findFlowLists(new HashMap());
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("rows", JSONArray.fromObject(list));		
		return returnSucess(map);
	}
	
	/**
	 *销户不分页查询业务流水
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findFlowLists1")
	public @ResponseBody Object findFlowLists1(HttpServletRequest request,Model model, Integer page,Integer rows,Machine machine)  {
		JSONObject json = getReqData(request);		
		Map<String,String> map = new HashMap<String,String>();			
		String projectName = (String) json.get("projectName");
		map.put("projectName", projectName);
		String kstime1 = (String) json.get("kstime");
		if (kstime1!= null&&!"".equals(kstime1)) {
			String kstime = kstime1+"000000";
			if (kstime!= null&&!"".equals(kstime)) {
				map.put("kstime", kstime);
			}
		}
		String jstime1 = (String) json.get("jstime");
		if (jstime1 != null && !"".equals(jstime1)) {
			String jstime = jstime1+"235959";
			if (jstime != null && !"".equals(jstime)) {
				map.put("jstime", jstime);
			}
		}
		List<ReturnForBusFlowPojo> list1 = flowService.findFlowLists1(map);
		Map<Object, Object> map1 = new HashMap<Object, Object>();
		map1.put("rows", JSONArray.fromObject(list1));		
		return returnSucess(map1);
	}
	
	/**
	 * 凭证管理查询
	 */
	@RequestMapping(value = "/findBusBillMan")
	public @ResponseBody Object findBusBillMan(HttpServletRequest request, Model model, Integer page, Integer rows) {
		JSONObject json = getReqData(request);
		String machineNo = (String) json.get("machineNo");
		String bnobno = (String) json.get("bnobno");
		Map<String, String> map = new HashMap<String, String>();
		Page pageInfo = getPageInfo(page, rows);
			if (machineNo!=null) {
				map.put("machineNo",machineNo);
			}
			if (bnobno !=null) {
				map.put("bnobno",bnobno);
			}
			List<BusBillMan> list = flowService.findBusBillMan(pageInfo,map);
			return returnResult(pageInfo, list);
	
	}
	
	/**
	 * 开户流水查询
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findkhList")
	public @ResponseBody Object findkhList(HttpServletRequest request, Model model, Integer page, Integer rows) {
		JSONObject json = getReqData(request);
		Map<String, String> map = new HashMap<String, String>();
		if (json.get("unitName") != null) {
			map.put("unitCode", String.valueOf(json.get("unitName")));
		}
		if (json.get("productName") != null) {
			map.put("productName", String.valueOf(json.get("productName")));
		}
		if (json.get("cardNo") != null) {
			map.put("cardNo", String.valueOf(json.get("cardNo")));
		}
		if (json.get("customerName") != null) {
			map.put("customerName", String.valueOf(json.get("customerName")));
		}
		if (json.get("subAccountNo")!=null) {
			map.put("subAccountNo", String.valueOf(json.get("subAccountNo")));
		}
		if (json.get("certNo")!=null) {
			map.put("certNo", String.valueOf(json.get("certNo")));
		}
		if (json.get("machineNo")!=null) {
			map.put("machineNo", String.valueOf(json.get("machineNo")));
		}
		String ksDate = (String) json.get("sDate");
		if (ksDate!= null&&!"".equals(ksDate)) {
			String sDate = ksDate+"000000";
			if (sDate!= null&&!"".equals(sDate)) {
				map.put("sDate", sDate);
			}
		}
		String jsDate = (String) json.get("jDate");
		if (jsDate != null && !"".equals(jsDate)) {
			String jDate = jsDate+"235959";
			if (jDate != null && !"".equals(jDate)) {
				map.put("jDate", jDate);
			}
		}
		Page pageInfo = getPageInfo(page, rows);
		List<CdjOrder> list = flowService.findkhList(pageInfo, map);
		return returnResult(pageInfo, list);
	}
	
	/**
	 * 开户流水统计
	 * 
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findkhtjList")
	public @ResponseBody Object findkhtjList(HttpServletRequest request, Model model, Integer page,
			Integer rows) {
		JSONObject json = getReqData(request);
		Map<String, String> map = new HashMap<String, String>();
		if (json.get("unitCode") != null) {
			map.put("unitCode", String.valueOf(json.get("unitCode")));
		}
		if (json.get("productName") != null) {
			map.put("productName", String.valueOf(json.get("productName")));
		}
		String kstime1 = String.valueOf(json.get("kstime"));
		String kstime2 = kstime1.replaceAll("-", "");
		if (kstime1!=null&&!"".equals(kstime1)) {
			String kstime = kstime2+"000000";
			if (kstime!= null) {
				map.put("kstime", kstime);
			}
		}
		String jstime1 = String.valueOf(json.get("jstime"));
		String jstime2 = jstime1.replaceAll("-", "");
		if (jstime1!=null&&!"".equals(jstime1)) {
			String jstime = jstime2+"235959";
			if (jstime != null) {
				map.put("jstime", jstime);
			}
		}
		Page pageInfo = getPageInfo(page, rows);
		List<ReturnAccountPojo> list = flowService.findkhtjList(pageInfo, map);
		return returnResult(pageInfo, list);
	}
	
	
	/**
	 * 开户流水查询导出
	 * 
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportkhList")
	public @ResponseBody Object exportkhList(HttpServletRequest request, Model model, Integer page,
			Integer rows) {
		JSONObject json = getReqData(request);
		Map<String, String> map = new HashMap<String, String>();
		if (json.get("unitName") != null) {
			map.put("unitCode", String.valueOf(json.get("unitName")));
		}
		if (json.get("productName") != null) {
			map.put("productName", String.valueOf(json.get("productName")));
		}
		if (json.get("cardNo") != null) {
			map.put("cardNo", String.valueOf(json.get("cardNo")));
		}
		if (json.get("customerName") != null) {
			map.put("customerName", String.valueOf(json.get("customerName")));
		}
		if (json.get("subAccountNo")!=null) {
			map.put("subAccountNo", String.valueOf(json.get("subAccountNo")));
		}
		if (json.get("certNo")!=null) {
			map.put("certNo", String.valueOf(json.get("certNo")));
		}
		if (json.get("machineNo")!=null) {
			map.put("machineNo", String.valueOf(json.get("machineNo")));
		}
		String ksDate = (String) json.get("sDate");
		if (ksDate!= null&&!"".equals(ksDate)) {
			String sDate = ksDate+"000000";
			if (sDate!= null&&!"".equals(sDate)) {
				map.put("sDate", sDate);
			}
		}
		String jsDate = (String) json.get("jDate");
		if (jsDate != null && !"".equals(jsDate)) {
			String jDate = jsDate+"235959";
			if (jDate != null && !"".equals(jDate)) {
				map.put("jDate", jDate);
			}
		}
		Page pageInfo = getPageInfo(page, rows);
		List<CdjOrder> list = flowService.findkhList( map);
		List<Map> dataList = new ArrayList<>();
		if (list == null) {
			return returnFail("没有数据");
		}
		if (list.size()>0) {
			for (CdjOrder cdjOrder : list) {
				Map<String, String> bean=new JSONObject();
				//键和excel列的位置匹配
				if(cdjOrder.getDeployMachine()!=null){
					bean.put("0", cdjOrder.getDeployMachine().getMachineNo());
				}else{
					bean.put("0", "");
				}
				if(cdjOrder.getBaseUnit()!=null){
					bean.put("1", cdjOrder.getBaseUnit().getUnitName());
				}else{
					bean.put("1", "");
				}
				bean.put("2", cdjOrder.getCertNo());
				bean.put("3", cdjOrder.getCustomerName());
				bean.put("4", cdjOrder.getProductName());
				if(cdjOrder.getPayType()!=null&&cdjOrder.getPayType().equals("C")){
					bean.put("5","银行卡开户");
				}else{
					bean.put("5", "现金开户");
				}
				bean.put("6", cdjOrder.getSubAccountNo());
				bean.put("7", cdjOrder.getDepositPeriod());
				bean.put("8", cdjOrder.getDepositAmt());
				bean.put("9", cdjOrder.getStatus());
				SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMddHHmmss");
				try {
					Date date=sdf1.parse(cdjOrder.getCreateDate());
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					bean.put("10", sdf.format(date));
				} catch (ParseException e) {
					log.error(e);
				}
				dataList.add(bean);
			}
		}
	//要生成的路径
		String 	path = request.getRealPath("./ReportTemplate/CdjOrderExcel1.xls");
		//服务器中当前项目的路径
		String path2 = request.getRealPath("./")+"/ReportTemplate/CdjOrderExcel.xls";
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
	 * 开户导出
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	@RequestMapping(value = "/accountExcel")
	public @ResponseBody Object accountExcel(HttpServletRequest request, Model model, Integer page, Integer rows) {
	
		JSONObject json = getReqData(request);
		String startDate = "";
		String endDate = "";
		String productName = (String) json.get("productName");
		String unitCode = (String) json.get("unitCode");
		
		Map<String, String> map = new HashMap<String, String>();
		// 开始
		String startDate1 = (String) json.get("ksTime");
		String startDate2 = startDate1.replaceAll("-", "");
		if (startDate2!= null&&!"".equals(startDate2)) {
			 startDate = startDate2+"000000";
			if (startDate!= null&&!"".equals(startDate)) {
				map.put("startDate", startDate);
			}
		}
		// 结束
		String endDate1 = (String) json.get("jsTime");
		String endDate2 = endDate1.replaceAll("-", "");
		if (endDate2!= null&&!"".equals(endDate2) ) {
			endDate = endDate2+"235959";
			if (endDate!= null&&!"".equals(endDate) ) {
				map.put("endDate", endDate);
			}
		}else{
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String date = format.format(new Date());
			endDate = date+"235959";
			map.put("endDate", endDate);
		}
		
		if (productName!= null ) {
				map.put("productName", productName);
		}
		
		if (unitCode!= null ) {
			map.put("unitCode", unitCode);
		}
		
		List<ReturnAccountPojo> list = flowService.findkhtjListExcel(map);
		if (list == null || list.isEmpty()) {
			return returnFail("没有可导出文件");
		}
		//根据机构名称 对项目名称以及数目进行分组
		Map<String, CdjAccountBean> temp = new HashMap<>();
		
		for (ReturnAccountPojo pojo : list) {
			Set<String> branchNames = temp.keySet();
			String branchName = pojo.getUnitName();//机构名称
			CdjAccountBean bean = new CdjAccountBean();
			if (branchNames.contains(branchName)) {
				bean = temp.get(branchName);
			}else{
				bean.setAllNum(0);
				bean.setAllAmt(0);;
			}
			//更新
			bean = getAccountBean(pojo, bean);
			if(bean == null){
				continue;
			}
			bean.setBranchName(branchName);
			temp.put(branchName, bean);
		}
		List<CdjAccountBean> dataList = new ArrayList<>();
		
		//将所有机构合并为集合
		for (String branchName : temp.keySet()) {
			dataList.add(temp.get(branchName));
		}
		//要生成的路径
		String path = request.getRealPath("./ReportTemplate/BckReport.xls"); 
		//服务器中当前项目的路径
		String path2 = request.getRealPath("./");
		try {
			boolean isSave = new CdjAccountExcel(path2).makeReport(startDate, endDate, dataList, path);
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
	 * 开户获取单个机构的产品合计
	 * @param pojo
	 * @param bean
	 * @return 
	 */
	private CdjAccountBean getAccountBean(ReturnAccountPojo pojo, CdjAccountBean bean) {
		//合计笔数以及合计金额统计
		int totalCount = 0;
		double totalAmount = 0.0;
		
		try {
			totalCount = bean.getAllNum();
			totalAmount = bean.getAllAmt();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		String productCode = pojo.getProductCode();
		
		if (StringUtils.isBlank(productCode)) {
			return null;
		}
		if (productCode.equals("0010")) {//整存争取
			bean.setZcNum(Integer.parseInt((pojo.getNumber()+"")));
			bean.setZcAmt(Double.parseDouble(String.valueOf(Double.parseDouble(pojo.getTotal())/10000).replaceAll("0+?$", "").replaceAll("[.]$", "")));
		}else if (productCode.startsWith("RY")) {//如意存
			bean.setRycNum(Integer.parseInt((pojo.getNumber()+"")));
			bean.setRycAmt(Double.parseDouble(String.valueOf(Double.parseDouble(pojo.getTotal())/10000).replaceAll("0+?$", "").replaceAll("[.]$", "")));
		}else if (productCode.startsWith("Y")) {//约享存
			bean.setYxcNum(Integer.parseInt((pojo.getNumber()+"")));
			bean.setYxcAmt(Double.parseDouble(String.valueOf(Double.parseDouble(pojo.getTotal())/10000).replaceAll("0+?$", "").replaceAll("[.]$", "")));
		}else if (productCode.startsWith("L")) {//立得存
			bean.setLdcNum(Integer.parseInt((pojo.getNumber()+"")));
			bean.setLdcAmt(Double.parseDouble(String.valueOf(Double.parseDouble(pojo.getTotal())/10000).replaceAll("0+?$", "").replaceAll("[.]$", "")));
		}else if (productCode.startsWith("XF")) {
			bean.setXfNum(Integer.parseInt((pojo.getNumber()+"")));
			bean.setXfAmt(Double.parseDouble(String.valueOf(Double.parseDouble(pojo.getTotal())/10000).replaceAll("0+?$", "").replaceAll("[.]$", "")));
		}else if (productCode.startsWith("RJ")) {//如意存+
			bean.setRjNum(Integer.parseInt((pojo.getNumber()+"")));
			bean.setRjAmt(Double.parseDouble(String.valueOf(Double.parseDouble(pojo.getTotal())/10000).replaceAll("0+?$", "").replaceAll("[.]$", "")));
		}else if (productCode.startsWith("JX")) {//积享存
			bean.setJxNum(Integer.parseInt((pojo.getNumber()+"")));
			bean.setJxAmt(Double.parseDouble(pojo.getTotal()));
			bean.setZcAmt(Double.parseDouble(String.valueOf(Double.parseDouble(pojo.getTotal())/10000).replaceAll("0+?$", "").replaceAll("[.]$", "")));
		}else if (productCode.equals("QXA40006")) {//喜迎千禧
			bean.setQxNum(Integer.parseInt((pojo.getNumber()+"")));
			bean.setQxAmt(Double.parseDouble(String.valueOf(Double.parseDouble(pojo.getTotal())/10000).replaceAll("0+?$", "").replaceAll("[.]$", "")));
		}else if (productCode.equals("QXA50006")) {//福临千禧
			bean.setFlNum(Integer.parseInt((pojo.getNumber()+"")));
			bean.setFlAmt(Double.parseDouble(String.valueOf(Double.parseDouble(pojo.getTotal())/10000).replaceAll("0+?$", "").replaceAll("[.]$", "")));
		}else if (productCode.equals("QXA70006")) {//溢彩千禧
			bean.setYcNum(Integer.parseInt((pojo.getNumber()+"")));
			bean.setYcAmt(Double.parseDouble(String.valueOf(Double.parseDouble(pojo.getTotal())/10000).replaceAll("0+?$", "").replaceAll("[.]$", "")));
		}else if (productCode.equals("QXA60006")) {//合赢千禧
			bean.setHyNum(Integer.parseInt((pojo.getNumber()+"")));
			bean.setHyAmt(Double.parseDouble(String.valueOf(Double.parseDouble(pojo.getTotal())/10000).replaceAll("0+?$", "").replaceAll("[.]$", "")));
		}else if (productCode.equals("QXA80006")) {//惠德千禧
			bean.setHdNum(Integer.parseInt((pojo.getNumber()+"")));
			bean.setHdAmt(Double.parseDouble(String.valueOf(Double.parseDouble(pojo.getTotal())/10000).replaceAll("0+?$", "").replaceAll("[.]$", "")));
		}else if (productCode.equals("QXA90006")) {//祥瑞千禧
			bean.setXrNum(Integer.parseInt((pojo.getNumber()+"")));
			bean.setXrAmt(Double.parseDouble(String.valueOf(Double.parseDouble(pojo.getTotal())/10000).replaceAll("0+?$", "").replaceAll("[.]$", "")));
		}
		double d = Double.parseDouble(String.valueOf(Double.parseDouble(pojo.getTotal())/10000).replaceAll("0+?$", "").replaceAll("[.]$", ""));
		totalCount += pojo.getNumber();
		totalAmount += d;
		
		bean.setAllNum(totalCount);
		bean.setAllAmt(totalAmount);
		return bean;
	}
}
