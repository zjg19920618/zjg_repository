package com.boomhope.tms.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.boomhope.tms.entity.Page;

public class BaseAction {
	
	public Page pageinfo;
	private String page;
	private String rows;
	private String sort;
	private String order;

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	
	
	/**
	 * 获取分页对象
	 * @param page
	 * @param rows
	 * @return
	 */
	public Page getPageInfo(Integer page,Integer rows){
		pageinfo=new Page();
		pageinfo.setPageSize(rows==null?0:rows);
		pageinfo.setPageNo(page==null?0:page);
		pageinfo.setSort(sort);
		if(order!=null && !"".equals(order.trim())){
			pageinfo.setOrder(order);
		}
		if(sort!=null && !"".equals(order.trim())){
			if(sort.toLowerCase().indexOf(" asc")!=-1 || sort.toLowerCase().indexOf(" desc")!=-1){
				pageinfo.setSortString(sort);
			}else{
				pageinfo.setSortString(sort + " " + pageinfo.getOrder());
			}
		}
		return pageinfo;
	}
	
	/**
	 * 分页返回查询记录
	 * @param pageInfo
	 * @param list
	 * @return
	 */
	public JSONObject returnResult(Page pageInfo,List<?> list){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", JSONArray.fromObject(list));
		result.put("total", pageInfo.getRowCount());
		result.put("success", true);
		return JSONObject.fromObject(result); 
	}
	
	
	/**
	 * 返回成功
	 * @return
	 */
	public JSONObject returnSucess(Map<Object,Object> map){
		Map<Object,Object> result = new HashMap<Object,Object>();
		result.put("success", true);
		result.putAll(map);
		return JSONObject.fromObject(result);
	}
	
	
	/**
	 * 返回成功
	 * @return
	 */
	public JSONObject returnSucess(){
		Map<Object,Object> result = new HashMap<Object,Object>();
		result.put("success", true);
		return JSONObject.fromObject(result);
	}
	
	
	/**
	 * 返回失败
	 * @return
	 */
	public JSONObject returnFail(String errmsg){
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("success", false);
		map.put("errmsg", errmsg);
		return JSONObject.fromObject(map);
	}
	
	/**
	 * 获取json中的数据
	 * @param request
	 * @return
	 */
	public JSONObject getReqData(HttpServletRequest request) {
		JSONObject jsonObj = new JSONObject();
		Enumeration<?> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			jsonObj.put(paraName, request.getParameter(paraName));
		}
		return jsonObj;
	}
	
}
