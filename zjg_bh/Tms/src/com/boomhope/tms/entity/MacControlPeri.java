package com.boomhope.tms.entity;

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

@Entity
@Table(name = "mac_control_peri")
@SequenceGenerator(name="tmsSeq",sequenceName="tms_sequence")
@SuppressWarnings("all")
public class MacControlPeri implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;//主键
	private DeployMachine deployMachine;//机器编号
	private Peripherals peripherals;//外设类型
	private String periStatus;
	private String statusDesc;

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 18)
	@GeneratedValue(generator="tmsSeq")
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id=id;
	}
	@Column(name = "peri_status", length = 30)
	public String getPeriStatus(){
		return periStatus;
	}
	public void setPeriStatus(String periStatus){
		this.periStatus=periStatus;
	}
	@Column(name = "status_desc", length = 255)
	public String getStatusDesc(){
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc){
		this.statusDesc=statusDesc;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "machine_no")
	public DeployMachine getDeployMachine() {
		return deployMachine;
	}
	public void setDeployMachine(DeployMachine deployMachine) {
		this.deployMachine = deployMachine;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "peri_id")
	public Peripherals getPeripherals() {
		return peripherals;
	}
	public void setPeripherals(Peripherals peripherals) {
		this.peripherals = peripherals;
	}
}

