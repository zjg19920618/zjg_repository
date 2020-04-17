package com.boomhope.tms.entity;

import java.io.Serializable;

public class StatisticsView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Long size;
	private String money;
	
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public StatisticsView() {
		super();
	}
	public StatisticsView(String name, Long size) {
		super();
		this.name = name;
		this.size = size;
	}
	
}
