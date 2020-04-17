package com.boomhope.tms.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role_menu_view")
public class RoleMenuView implements Serializable {
	private static final long serialVersionUID = 1L;
	private RoleMenuViewId roleMenuViewId;
	
	@Id
	public RoleMenuViewId getRoleMenuViewId() {
		return roleMenuViewId;
	}
	public void setRoleMenuViewId(RoleMenuViewId roleMenuViewId) {
		this.roleMenuViewId = roleMenuViewId;
	}
}

