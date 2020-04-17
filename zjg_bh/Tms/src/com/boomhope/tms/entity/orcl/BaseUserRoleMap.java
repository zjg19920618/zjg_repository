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

import com.boomhope.tms.entity.BaseRole;
import com.boomhope.tms.entity.BaseUser;

//@Entity
//@Table(name = "base_user_role_map")
//@SequenceGenerator(name="tmsSeq",sequenceName="tms_sequence")
//@SuppressWarnings("all")
public class BaseUserRoleMap  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private BaseUser baseUser; 
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
	@JoinColumn(name = "username")
	public BaseUser getBaseUser() {
		return baseUser;
	}
	public void setBaseUser(BaseUser baseUser) {
		this.baseUser = baseUser;
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
	