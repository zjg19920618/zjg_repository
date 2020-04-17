package com.boomhope.tms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boomhope.tms.entity.OnOffState;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.service.IOnOffStateService;

/**
 * 业务管理
 * 
 * @author zjg
 *
 */
@Controller
@Scope("prototype")
public class onOffAction extends BaseAction{
	private static final Log log = LogFactory.getLog(onOffAction.class);

	IOnOffStateService onOffStateService;

	@Resource(name = "onOffStateService")
	public void setfaceStateService(IOnOffStateService onOffStateService) {
		this.onOffStateService = onOffStateService;
	}
	
	/**
	 * 利率开关状态查询
	 */
	@RequestMapping(value = "/findRateState")
	public @ResponseBody Object findRateState(HttpServletRequest request, Model model, Integer page, Integer rows) {
		JSONObject json = getReqData(request);
		String onOffName = (String) json.get("onOffName");
		Map<String, String> map = new HashMap<String, String>();
		Page pageInfo = getPageInfo(page, rows);
			if (onOffName !=null) {
				map.put("onOffName",onOffName);
			}
			List<OnOffState> list = onOffStateService.findOneStatePage(pageInfo,map);
			return returnResult(pageInfo, list);
	
	}
	
	
	/**
	 * 修改利率开关状态
	 */
	@RequestMapping(value = "/editRateState")
	public @ResponseBody Object editFaceState(HttpServletRequest request, Model model) {
		
		JSONObject json = getReqData(request);
		
		OnOffState fs = null;
		if(json.get("onOffId")!=null && json.get("onOffName")!=null && json.get("onOffState")!=null){
			fs = new OnOffState();
			fs.setOnOffId(Integer.parseInt((String)json.get("onOffId")));
			fs.setOnOffName((String)json.get("onOffName"));
			fs.setOnOffState((String)json.get("onOffState"));
		}
		try {
			onOffStateService.editOneState(fs);
			
		} catch (Exception e) {
			log.equals("修改利率开关状态失败"+e);
			return returnFail("利率开关更改失败");
		}
		return returnSucess();
	}
}
