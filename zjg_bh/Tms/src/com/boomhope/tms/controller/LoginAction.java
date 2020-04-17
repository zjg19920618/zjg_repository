package com.boomhope.tms.controller;


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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.entity.RoleMenuView;
import com.boomhope.tms.service.ILoginService;
import com.boomhope.tms.util.DateUtil;
import com.boomhope.tms.util.MD5Util;
/**
 * 登录控制器
 * @author zy
 *
 */
@Controller
@Scope("prototype")
public class LoginAction extends BaseAction {
	
    private static final Log log = LogFactory.getLog(LoginAction.class);
    
    ILoginService loginService;
    
    @Resource(name="loginService")
    public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}

    /**
     * 登录验证
     * @param request
     * @param model
     * @param response
     * @return
     * @throws Exception
     */
	@RequestMapping("/login")
    public @ResponseBody Object login(HttpServletRequest request,Model model,HttpServletResponse response) throws Exception {
		
    	String pwd = request.getParameter("pwd");
    	String username = request.getParameter("username");
    	BaseUser user = loginService.getUser(username,pwd);
    	if(user != null){
    		request.getSession().setAttribute("loginUser", user);
    		user.setLastTime(DateUtil.getDateTime("yyyyMMddHHmmss"));
    		loginService.saveLastTime(user);
    		return returnSucess();
    		
    	}else{
    		return returnFail("用户名或密码错误！");
    	}
		
    	
    }
	
	/**
	 * 获取菜单
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping("/getMenu")
    public @ResponseBody Object getMenu(HttpServletRequest request,Model model,HttpServletResponse response)  {
		BaseUser user = (BaseUser) request.getSession().getAttribute("loginUser");
		JSONObject jsonobj = getReqData(request);
		String parentId = (String) jsonobj.get("parentId");
		int prent = 0;
		if(parentId != null && !"".equals(parentId)){
			prent = Integer.parseInt(parentId);
		}
		List<RoleMenuView> viewList = loginService.getMenu(user.getUsername(), prent);
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("info", JSONArray.fromObject(viewList));
		return returnSucess(map);	
    }
	/**
	 *  修改密码
	 * @param request
	 * @return
	 */
	@RequestMapping("/getEditPwd")
	public @ResponseBody Object exitPwd(HttpServletRequest request){
		BaseUser user = (BaseUser) request.getSession().getAttribute("loginUser");
		JSONObject json = getReqData(request);
		String username = user.getUsername();
		String pwd = (String) json.get("pwd");
		String nepwd1 = (String) json.get("nepwd");
		String nepwd2 = MD5Util.string2MD5(nepwd1);
			if(!user.getPwd().equals(MD5Util.string2MD5(pwd))){
				return this.returnFail("原密码错误！");
			}
		
		BaseUser baseUser = new BaseUser();
		baseUser.setUsername(username);
		baseUser.setPwd(nepwd2);
		loginService.editPwd(baseUser);
		return returnSucess();
		
		
	}
	
	/**
	 *  重置密码
	 * @param request
	 * @return
	 */
	@RequestMapping("/editpeoplepwd")
	public @ResponseBody Object editpeoplepwd(HttpServletRequest request){
	//	BaseUser user = (BaseUser) request.getSession().getAttribute("loginUser");
	//	logger.debug(user);
		JSONObject json = getReqData(request);
		String username = String.valueOf(json.get("username"));
		String pwd = (String) json.get("pwd");
		String pwd5 = MD5Util.string2MD5(pwd);
		BaseUser baseUser = new BaseUser();
		baseUser.setPwd(pwd5);
		baseUser.setUsername(username);
		loginService.editPwd(baseUser);
		return returnSucess();
		
		
	}
}