package com.boomhope.tms.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "mac_machine")
@SuppressWarnings("all")
public class Machine implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String machineCode; 
	private String machineType; 
	private String machineName; 
	private Manu manu; 
	private String machinePic; 
	private String machineDesc; 
	private String createDate; 
	private String creater;
	private String machineTypeName;
	
	
	@Id
	@Column(name = "machine_code", unique = true, nullable = false)
	public String getMachineCode() {
		return machineCode;
	}
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	@Column(name = "machine_type", length = 18)
	public String getMachineType() {
		return machineType;
	}
	
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	@Column(name = "machine_name", length = 255)
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "manu_code")
	public Manu getManu() {
		return manu;
	}
	public void setManu(Manu manu) {
		this.manu = manu;
	}
	
	@Column(name = "machine_pic", length = 255)
	public String getMachinePic() {
		return machinePic;
	}
	public void setMachinePic(String machinePic) {
		this.machinePic = machinePic;
	}
	@Column(name = "machine_desc", length = 255)
	public String getMachineDesc() {
		return machineDesc;
	}
	public void setMachineDesc(String machineDesc) {
		this.machineDesc = machineDesc;
	}
	@Column(name = "create_date", length = 14)
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	@Column(name = "creater", length = 255)
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	} 
	
	@Transient
	public String getMachineTypeName() {
		return machineTypeName;
	}
	
	public void setMachineTypeName(String machineTypeName) {
		this.machineTypeName = machineTypeName;
	}
	
	
}
