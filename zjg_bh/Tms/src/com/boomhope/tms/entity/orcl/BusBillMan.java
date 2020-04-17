package com.boomhope.tms.entity.orcl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.boomhope.tms.entity.DeployMachine;

//@Entity
//@Table(name = "bus_bill_man")
//@SequenceGenerator(name="tmsSeq",sequenceName="tms_sequence")
//@SuppressWarnings("all")
public class BusBillMan implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private DeployMachine deployMachine; 
	private String startBno;//开始凭证号
	private String endBno;//结束凭证号
	private String nowBno;//当前凭证号
	private String updateDate;//当前时间
	private String createDate;//创建时间
	
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
	@JoinColumn(name = "machine_no")
	public DeployMachine getDeployMachine() {
		return deployMachine;
	}
	public void setDeployMachine(DeployMachine deployMachine) {
		this.deployMachine = deployMachine;
	}
	
	@Column(name = "start_bno", length = 18)
	public String getStartBno() {
		return startBno;
	}
	public void setStartBno(String startBno) {
		this.startBno = startBno;
	}
	
	@Column(name = "end_bno", length = 18)
	public String getEndBno() {
		return endBno;
	}
	public void setEndBno(String endBno) {
		this.endBno = endBno;
	}
	
	@Column(name = "now_bno", length = 18)
	public String getNowBno() {
		return nowBno;
	}
	public void setNowBno(String nowBno) {
		this.nowBno = nowBno;
	}
	
	@Column(name = "update_date", length = 18)
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
	@Column(name = "create_date", length = 18)
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
}
