package com.boomhope.tms.entity;

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
@Table(name = "mac_peripherals_machine")
@SequenceGenerator(name="tmsSeq",sequenceName="tms_sequence")
@SuppressWarnings("all")
public class PeripheralsMachine implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Peripherals peripherals;
	//机器型号
	private Machine machine;
	@Id
 //   @GeneratedValue(strategy=GenerationType.AUTO)
	@GeneratedValue(generator="tmsSeq")
	@Column(name = "id", unique = true, nullable = false, length = 18)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "peri_id")
	public Peripherals getPeripherals() {
		return peripherals;
	}
	public void setPeripherals(Peripherals peripherals) {
		this.peripherals = peripherals;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "machine_code")
	public Machine getMachine() {
		return machine;
	}
	public void setMachine(Machine machine) {
		this.machine = machine;
	}
}
