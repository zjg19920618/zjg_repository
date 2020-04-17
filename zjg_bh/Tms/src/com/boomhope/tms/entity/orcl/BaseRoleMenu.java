package com.boomhope.tms.entity.orcl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.boomhope.tms.entity.BaseMenu;
import com.boomhope.tms.entity.BaseRole;

//@Entity
//@Table(name = "base_role_menu")
//@SequenceGenerator(name="tmsSeq",sequenceName="tms_sequence")
//@SuppressWarnings("all")
public class BaseRoleMenu  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private BaseMenu baseMenu; 
	private BaseRole baseRole;
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 18)
	@GeneratedValue(generator="tmsSeq")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "menu_id")
	public BaseMenu getBaseMenu() {
		return baseMenu;
	}
	public void setBaseMenu(BaseMenu baseMenu) {
		this.baseMenu = baseMenu;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_code")
	public BaseRole getBaseRole() {
		return baseRole;
	}
	public void setBaseRole(BaseRole baseRole) {
		this.baseRole = baseRole;
	}
	
}
	