package com.boomhope.tms.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "sequence_manage")
@SequenceGenerator(name="tmsSeq",sequenceName="tms_sequence")
@SuppressWarnings("all")
public class SequenceManage implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer seqId;
	private String seqCode;
	private String seqValue;
	private String seqDate;
	
	@Id
	@Column(name = "seq_id", unique = true, nullable = false, length = 18)
	@GeneratedValue(generator="tmsSeq")
	public Integer getSeqId() {
		return seqId;
	}
	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}
	@Column(name = "seq_code",  length = 255)
	public String getSeqCode() {
		return seqCode;
	}
	public void setSeqCode(String seqCode) {
		this.seqCode = seqCode;
	}
	@Column(name = "seq_value",  length = 255)
	public String getSeqValue() {
		return seqValue;
	}
	public void setSeqValue(String seqValue) {
		this.seqValue = seqValue;
	}
	@Column(name = "SEQ_DATE",  length = 255)
	public String getSeqDate() {
		return seqDate;
	}
	public void setSeqDate(String seqDate) {
		this.seqDate = seqDate;
	}

}

