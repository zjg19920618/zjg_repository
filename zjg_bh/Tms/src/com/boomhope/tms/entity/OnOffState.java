package com.boomhope.tms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "on_off_state")
@SequenceGenerator(name="onOffSeq",sequenceName="on_off_seq")
@SuppressWarnings("all")
public class OnOffState  implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private int onOffId;//开关序列
	private String onOffName;//开关名称 
	private String onOffState;//开关状态  （0：已启用；1：已关闭）
	
	@Id
	@Column(name = "on_off_id", unique = true, nullable = false, length = 18)
	@GeneratedValue(generator="onOffSeq")
	public int getOnOffId() {
		return onOffId;
	}
	
	public void setOnOffId(int onOffId) {
		this.onOffId = onOffId;
	}
	
	@Column(name = "on_off_name", length = 20)
	public String getOnOffName() {
		return onOffName;
	}

	public void setOnOffName(String onOffName) {
		this.onOffName = onOffName;
	}
	
	@Column(name = "on_off_state", length = 20)
	public String getOnOffState() {
		return onOffState;
	}

	public void setOnOffState(String onOffState) {
		this.onOffState = onOffState;
	}

	@Override
	public String toString() {
		return "OnOffState [onOffId=" + onOffId + ", onOffName=" + onOffName
				+ ", onOffState=" + onOffState + "]";
	}
	
}
