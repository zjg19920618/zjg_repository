package com.boomhope.tms.message.in.tms;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * @Description: 管理员登录校验
 * @author zjg   
 * @date 2016年11月16日 上午9:51:34
 */
@XStreamAlias("Body")  
public class Tms0004ReqBodyBean {
	private String isXiu; //是否修改密码
	private String username; //用户
	private String password; //密码
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIsXiu() {
		return isXiu;
	}
	public void setIsXiu(String isXiu) {
		this.isXiu = isXiu;
	}
	
}
