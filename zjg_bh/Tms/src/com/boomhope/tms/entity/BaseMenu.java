package com.boomhope.tms.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "base_menu")
@SequenceGenerator(name="tmsSeq",sequenceName="tms_sequence")
@SuppressWarnings("all")
public class BaseMenu implements Serializable {
	private static final long serialVersionUID = 1L;
	private int menuId;
	private BaseRole baseRole;
	private String menuName;
	private String menuPath;
	private int parentId;
	private int ordBy;
	private String selectd;// 非数据库字段： Y-选中  N-未选中


	@Id
	@Column(name = "menu_id", unique = true, nullable = false, length = 18)
	@GeneratedValue(generator="tmsSeq")
	public int getMenuId(){
		return menuId;
	}
	public void setMenuId(int menuId){
		this.menuId=menuId;
	}
	public void setBaseRole(BaseRole baseRole) {
		this.baseRole = baseRole;
	}
	
	@Column(name = "menu_name", length = 255)
	public String getMenuName(){
		return menuName;
	}
	public void setMenuName(String menuName){
		this.menuName=menuName;
	}
	@Column(name = "menu_path", length = 255)
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
	
	@Transient
	public String getSelectd() {
		return selectd;
	}
	public void setSelectd(String selectd) {
		this.selectd = selectd;
	}
}

