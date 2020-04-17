package com.boomhope.tms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RoleMenuViewId implements Serializable {
	private static final long serialVersionUID = 1L;
	private int menuId;
	private String menuName;
	private String menuPath;
	private int parentId;
	private int ordBy;
	private String username;

	@Column(name = "menu_id", length = 18)
	public int getMenuId(){
		return menuId;
	}
	public void setMenuId(int menuId){
		this.menuId=menuId;
	}
	@Column(name = "menu_name", length = 18)
	public String getMenuName(){
		return menuName;
	}
	public void setMenuName(String menuName){
		this.menuName=menuName;
	}
	@Column(name = "menu_path", length = 18)
	public String getMenuPath(){
		return menuPath;
	}

	public void setMenuPath(String menuPath){
		this.menuPath=menuPath;
	}
	@Column(name = "parent_id", length = 18)
	public int getParentId(){
		return parentId;
	}
	public void setParentId(int parentId){
		this.parentId=parentId;
	}
	@Column(name = "ord_by", length = 18)
	public int getOrdBy(){
		return ordBy;
	}
	public void setOrdBy(int ordBy){
		this.ordBy=ordBy;
	}
	@Column(name = "username", length = 255)
	public String getUsername(){
		return username;
	}
	public void setUsername(String username){
		this.username=username;
	}
}

